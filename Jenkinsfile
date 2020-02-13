 /*======================================================================================*
 * Pipeline para execução dos testes de regressão automatizados no Jenkins CI/CD
 * Author: Carlos Almeida 	
 * Job Execution: once a day on the 1st and 15th of every month except December 
 *=======================================================================================*/
pipeline {
 
  agent { 
    kubernetes {
      label 'qa-slave'
      defaultContainer 'jnlp'
      yaml """
apiVersion: v1
kind: Pod
metadata:
  labels:
    some-label: qa-slave
spec:
  containers:
  - name: qa-slave
    image: 507208215022.dkr.ecr.us-east-1.amazonaws.com/ecr-jenkins-slave-qa:latest
    resources:
      limits:
        memory: "1000Gi"
        cpu: "1"
      requests:
        memory: "1024Mi"
        cpu: 500m
    tty: true
    command:
    - cat
    env:
    - name: AWS_DEFAULT_REGION
      value: sa-east-1
"""      
    }
  }
  

  stages {
        stage('Configurar  selenium-cucumber-js ') {
      steps {
        echo "Branch is ${env.BRANCH_NAME}..."
       
          echo "Projeto de testes com Selenium - Copiar chromedriver de /usr/local/bin para Direatório do Projeto de Testes /driver "
        container("qa-slave") {
         
          script {
            
             sh (script: "npm install selenium-cucumber-js –save; npm install chai –save;")
             
          }
        }
      }
    }
      stage('Listar arquivos do diretorio') {
      steps {
        echo "Branch is ${env.BRANCH_NAME}..."
       
          echo "Projeto de testes com Selenium - Copiar chromedriver de /usr/local/bin para Direatório do Projeto de Testes /driver "
        container("qa-slave") 
        {
         
            script {
            
             sh (script: "")
             
          }
        }
      }
    }
    
     stage('Criar Diretorio para Evidencias') {
      steps {
        echo "Branch is ${env.BRANCH_NAME}..."
       
          echo "Projeto de testes com Selenium - /RelatorioTest/ArquivoPDFRelatorio/Evidences/ "
        container("qa-slave") {
         
          script {
            
             sh (script: "mkdir -p /RelatorioTest/ArquivoPDFRelatorio/Evidences/ cd /RelatorioTest/ArquivoPDFRelatorio/Evidences/; ls")
             
          }
        }
      }
    }
     
    stage('QA-Selenium - Regression Test Plataforma PI') {
      steps {
        echo "Branch is ${env.BRANCH_NAME}..."
        container("qa-slave") {
          echo "Projeto de testes com Selenium"
          script {
    sh (script: "node ./node_modules/selenium-cucumber-js/index.js -f ./features/fundos.feature -Dplatform=WEB-CHROME -DinputType=EXCEL -DautomationTool=SELENIUM test; ")
            
          }
        }
      }
    }
    
       
  }
  
  post {
        
           always {
          		echo 'Publicar Evidências da execução dos testes automatizados no Jenkins '
              
            script {
            
            sh (script: " ")
          }
          
          archiveArtifacts artifacts: '**/Evidencias.tar.gz', fingerprint: true 
       	 
       	 }
       	 
           failure {
           echo 'I failed :('
              script: sendMessage("Existem falhas nos Testes do Job ${env.JOB_NAME} - Build Number: ${env.BUILD_NUMBER} Verificar Evidências publicadas no Jenkins")
         
           script {
            
            sh (script: "")
          }
           archiveArtifacts artifacts: '**/Evidencias.tar.gz', fingerprint: true 
       	 
       	 }
       	 
       	 success {
           echo ' Build Success!! - Testes executados com sucesso!'
              
       	            
            script {
            
             sh (script: "")
             
          }
       	 
       	 }
       	 
       	
    }
  }
  
 /*================================================================*
 * Scripts Utilitarios								               * 
 *================================================================*/
   
    def sendMessage(msg) {
  		 try {
     	withCredentials([string(credentialsId: 'gchat-token', variable: 'GTOKEN')]) {
        googlechatnotification url: "https://chat.googleapis.com/v1/spaces/AAAASaW7Wy8/messages?key=AIzaSyDdI0hCZtE6vySjMm-WEfRq3CPzqKqqsHI&token=$GTOKEN", message: msg + "(<${env.BUILD_URL}|Mais Informacoes>)" 
     	                             
     	}
   		}
   		
   		catch (Exception e) {
                          
   }
	
	}