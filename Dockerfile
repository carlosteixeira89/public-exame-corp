
FROM jenkins/slave:3.26-1
FROM node:8
USER root
MAINTAINER Carlos Almeida <carlosteixeira@brq.com>
LABEL Description="This is a base image, which allows connecting Jenkins agents via JNLP protocols" Vendor="Jenkins project" Version="3.23"

RUN apt-get update && apt-get install -y \
    software-properties-common \
    unzip \
    curl \
    xvfb 
    
RUN apt -y install libgconf2-4

#==============
# VNC and Xvfb
#==============
RUN apt-get update -y \
  && apt-get -y install \
    xvfb \
  && rm -rf /var/lib/apt/lists/* /var/cache/apt/*
  
 
# Chrome browser to run the tests
RUN curl https://dl-ssl.google.com/linux/linux_signing_key.pub -o /tmp/google.pub \
    && cat /tmp/google.pub | apt-key add -; rm /tmp/google.pub \
    && echo 'deb http://dl.google.com/linux/chrome/deb/ stable main' > /etc/apt/sources.list.d/google.list \
    && mkdir -p /usr/share/desktop-directories \
    && apt-get -y update && apt-get install -y google-chrome-stable
# Disable the SUID sandbox so that chrome can launch without being in a privileged container
RUN dpkg-divert --add --rename --divert /opt/google/chrome/google-chrome.real /opt/google/chrome/google-chrome \
    && echo "#!/bin/bash\nexec /opt/google/chrome/google-chrome.real --no-sandbox --headless  \"\$@\"" > /opt/google/chrome/google-chrome \
    && chmod 755 /opt/google/chrome/google-chrome

#Configuração para rodar os testes nodeJS selenium


# Create app directory
WORKDIR /usr/src/app


# If you are building your code for production
# RUN npm ci --only=production

# Bundle app source
COPY . .
    

# Chrome Driver
RUN mkdir -p /opt/selenium \
    && curl http://chromedriver.storage.googleapis.com/2.44/chromedriver_linux64.zip -o /opt/selenium/chromedriver_linux64.zip \
    && cd /opt/selenium; unzip /opt/selenium/chromedriver_linux64.zip; rm -rf chromedriver_linux64.zip; ln -fs /opt/selenium/chromedriver /usr/local/bin/chromedriver \
    && wget --no-verbose https://selenium-release.storage.googleapis.com/3.1/selenium-server-standalone-3.1.0.jar -O /opt/selenium/selenium-server-standalone.jar

RUN chmod +x /usr/local/bin/chromedriver

RUN mkdir -p /RelatorioTest/ArquivoPDFRelatorio

# Firefox browser to run the tests
#RUN apt-get install -y firefox


################################################################################################################
# Install Maven
# https://github.com/carlossg/docker-maven
################################################################################################################


ENV MAVEN_VERSION 3.3.9

RUN apt-get update && apt-get install -y curl git && rm -rf /var/lib/apt/lists/*

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz \
    | tar -xzC /usr/share/maven --strip-components=1 \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG /home/seluser/.m2
ENV COPY_REFERENCE_FILE_LOG $MAVEN_CONFIG/copy_reference_file.log


ENTRYPOINT ["jenkins-slave"]
CMD ["npm", "start", "--headless", "--disable-gpu", "--no-sandbox"]
# Autorun chrome headless with no GPU
ENTRYPOINT ["google-chrome", "--headless", "--disable-gpu", "--disable-software-rasterizer", "--disable-dev-shm-usage"]
