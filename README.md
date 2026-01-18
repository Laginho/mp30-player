# 🎵 MP30 Player

Player de mídia desenvolvido em Java para a disciplina de Programação Orientada a Objetos.

## 📋 Funcionalidades
- Reprodução de arquivos MP3
- Biblioteca de músicas com metadados
- Modos de reprodução: Sequencial, Aleatório, Repetir
- Fila de prioridade

## 🏗️ Arquitetura
O projeto segue o padrão **MVC** e utiliza:
- **Strategy Pattern** para modos de reprodução
- **Observer Pattern** para notificação de fim de mídia
- **Exceções customizadas** para tratamento de erros

## 📐 Diagrama de Classes (Simplificado)

```
                    ┌─────────────────────┐
                    │   «interface»       │
                    │    Reproduzivel     │
                    ├─────────────────────┤
                    │ + reproduzir()      │
                    │ + parar()           │
                    └──────────┬──────────┘
                               │ implements
                               ▼
                    ┌─────────────────────┐
                    │   «abstract»        │
                    │      Midia          │
                    ├─────────────────────┤
                    │ - titulo: String    │
                    │ - duracao: int      │
                    │ - caminho: String   │
                    ├─────────────────────┤
                    │ + reproduzir()      │
                    │ + parar()           │
                    │ + notificarFim()    │
                    └──────────┬──────────┘
                               │ extends
              ┌────────────────┴────────────────┐
              ▼                                 ▼
   ┌─────────────────────┐           ┌─────────────────────┐
   │      Musica         │           │       Audio         │
   ├─────────────────────┤           ├─────────────────────┤
   │ - artista: String   │           │ - autor: String     │
   │ - album: String     │           ├─────────────────────┤
   ├─────────────────────┤           │ + reproduzir()      │
   │ + reproduzir()      │           │ + parar()           │
   │ + parar()           │           └─────────────────────┘
   └─────────────────────┘

   ┌─────────────────────┐
   │   «interface»       │
   │ EstrategiaReproducao│
   ├─────────────────────┤
   │ + proximaMusica()   │
   │ + musicaAnterior()  │
   └──────────┬──────────┘
              │ implements
   ┌──────────┼──────────────────────┐
   ▼          ▼                      ▼
┌────────┐ ┌────────────┐ ┌─────────────────┐
│Sequen- │ │ Aleatoria  │ │    Repetir      │
│  cial  │ │            │ │                 │
└────────┘ └────────────┘ └─────────────────┘
```

## 📁 Estrutura
```
src/main/java/br/ufc/poo/
├── controle/          # Controllers e estratégias
│   ├── estrategias/   # Implementações do Strategy Pattern
│   ├── LeitorMetadados.java
│   └── PlayerController.java
├── modelo/            # Classes de domínio
│   ├── interfaces/    # Interfaces do sistema
│   ├── Midia.java     # Classe abstrata base
│   ├── Musica.java    # Mídia musical
│   └── Audio.java     # Áudio genérico (podcasts, etc)
├── visao/             # Interface gráfica (Swing)
│   ├── JanelaPrincipal.java
│   ├── TelaBiblioteca.java
│   └── ...
└── excecoes/          # Exceções customizadas
    ├── MidiaNaoEncontradaException.java
    ├── MidiaJaTocandoException.java
    └── MidiaInvalidaException.java
```

## 🚀 Como Executar

### Via IDE
Importe o projeto e execute a classe `Main.java`

### Via Terminal
```bash
# Compilar
javac -d bin -cp "lib/*" -sourcepath src/main/java src/main/java/br/ufc/poo/Main.java

# Executar (Windows)
java -cp "bin;lib/*" br.ufc.poo.Main

# Executar (Linux/Mac)
java -cp "bin:lib/*" br.ufc.poo.Main
```

## 🎨 Screenshots

> *Adicione screenshots da aplicação aqui*

## 📚 Conceitos de POO Aplicados

| Conceito | Implementação |
|----------|---------------|
| **Herança** | `Musica` e `Audio` estendem `Midia` |
| **Polimorfismo** | Métodos `reproduzir()` e `parar()` sobrescritos |
| **Abstração** | Classe `Midia` é abstrata |
| **Encapsulamento** | Atributos privados com getters/setters |
| **Interfaces** | `Reproduzivel`, `EstrategiaReproducao`, `OnMidiaFinalizadaListener` |

## 🧩 Design Patterns Utilizados

- **Strategy**: Permite trocar o modo de reprodução em tempo de execução
- **Observer**: Notifica quando uma mídia termina de tocar
- **MVC**: Separação entre Model, View e Controller

## 👨‍💻 Autores
Bruno Lage, Raul Falcão, Álvaro Campelo, Noah Martins - UFC - 2026

## 📄 Licença
Projeto acadêmico - Universidade Federal do Ceará