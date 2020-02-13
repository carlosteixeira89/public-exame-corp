#language:pt
#encoding UTF-8

@geral
Funcionalidade: Realizar Pesquisa no site da Exame
                Como usuario da Pagina
                desejo acessar a home page para realizar uma busca
                	
@pesquisa
Esquema do Cenario: Realizar Pesquisa no site da Exame
        Dado  que o usuario acessa a home page
        E Realizada uma busca por "<titulo>"
        Entao a pesquisa realizada com sucess
 
Exemplos:
|titulo|
|Bolsa |