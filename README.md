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

## 📁 Estrutura
```
src/main/java/br/ufc/poo/
├── controle/          # Controllers e estratégias
├── modelo/            # Classes de domínio
├── visao/             # Interface gráfica (Swing)
└── excecoes/          # Exceções customizadas
```

## 🚀 Como Executar
```bash
javac -d bin -cp "lib/*" -sourcepath src/main/java src/main/java/br/ufc/poo/Main.java
java -cp "bin;lib/*" br.ufc.poo.Main
```

## 👨‍💻 Autores
Bruno Lage, Raul Falcão, Álvaro Campelo, Noah Martins - UFC - 2026