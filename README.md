# Trabalho de conclusão de Curso
Projeto final do Curso de Sistemas de Informação, pela Universidade Federal de Viçosa. 

### Descrição do Problema
* O projeto desenvolvido foi para resolver um problema de roteamento de veículos heterogêneos considerando a emissão de carbono (PRVHCEC), 
neste problemas temos um armázem de onde parte cada veículos que faz sua rotas atendendo a demanda de cada cidade; 
* a frota de veículos contém uma determindada quantidade de veículos com capacidade de transporte e os gastos variáveis e de emissão de carbono,
para cada unidade de distância percorrida. 
* Foi desenvolvido um algoritmo que buscou trazer a solução mais otimizada, onde para cada solução foi criada as rotas com os veículos atendendo 
todas as cidades e respeitando as restrições de quantidade e capacidade dos veículos. 
* O algoritmo desenvolvido utilizou metahéuristicas, com duas versões de algoritmos genéticos: a primeira um RKGA e o segunda BRKGA

### Parte escrita 
* Uma descrição mais detalhada do problema, está na parte escrita do projeto, disponível no [link do projeto](https://drive.google.com/drive/folders/1sZ6ML3JZgilBWcxnGQK43SVNO8FiCI3I?usp=sharing)

### Linguagens utilizadas 
* Java

### Para executar o código deve utilizar 
* Etapa 1
  * baixar jdk
  * configurar variáveis de ambiente e sistema

* Etapa 2
  * Para compilar o projeto deve entrar pelo terminal até a pasta src, 
  * depois execute este comando para compilar os arquivos
  ```
  javac projeto \*.java
  ```
  * mova os arquivos com terminação .class para a pasta build\classes\projeto
  * fazer a excecução do projeto: conforme o exemplo abaixo
  ```
  java projeto.Projeto c50_13mix.txt 150 150 normal 0 > saida_rkga_c50_13mix_9.txt
  ```

* Argumentos:
  * nome da instância:
  ```
  c50_13mix.txt
  c50_14mix.txt
  c50_15mix.txt
  c50_16mix.txt
  c75_17mix.txt
  c75_18mix.txt
  c100_19mix.txt
  c100_20mix.txt
  ```
  * quantidade de soluções que o serão criadas para o problema
  ``` 
  A população de soluções foram definidas da seguinte forma
  50 clientes  => 150 soluções
  75 clientes  => 225 soluções
  100 clientes => 300 soluções
  ```  
  * máximo de gerações: quantidade de gerações utilizadas para evoluir o conjunto de soluções
  ```
  A quantidade de gerações foi definida da seguinte forma
  50 clientes  => 150 soluções
  75 clientes  => 225 soluções
  100 clientes => 300 soluções
  ```
  * qual dos algoritmos criados será utilizado na etapa de evolução das soluções
  ```
  rkga colocar  => normal  
  brkga colocar => brkga  
  ```
  * semente de números aleatórios
  ```
  0 até 10
  ```
* salva o resultado em um arquivo



#### Estado
Projeto finalizado


#### Licença
MIT License

Copyright (c) 2020 José Geraldo

