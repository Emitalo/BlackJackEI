# BlackJackEI

O **BlackJackEI** trata-se do jogo de cartas _Blackjack ou 21_ com algumas adaptações.


### Executando o *BlackJackEI*

O jogo foi desenvolvido no paradigma MultiAgentes utilizando a plataforma JADE. Portanto, é necessário ter o Jade instalado e configurado na sua IDE de desenvolvimento Java. Essas instruções consideram o uso do Eclipse.

Baixe o código do ***BlackJackEI*** e importe o projeto no Eclipse.

* Adicione no Build Path do projeto os _jars_ referentes a plataforma JADE. 
* Configure a execução de um projeto:
    * Clique na opção 'Run configurations'
    * Dê um nome para essa execução. Ex: `StartGame`
    * Preencha na aba **Main**:
        * _Project_ : `BlackJackEI` 
        * _Main class_ : `jade.Boot`
    * Preencha na aba **Arguments**:
        * _Program arguments_ : `-gui init:agent.InitAgent`
* Execute a configuração criada

## Autores do ***BlackJackEI***

[@italopaiva](https://github.com/italopaiva)

[@Emiliemorais](https://github.com/Emiliemorais)
