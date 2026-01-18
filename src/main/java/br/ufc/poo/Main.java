package br.ufc.poo;

import br.ufc.poo.controle.LeitorMetadados;
import br.ufc.poo.controle.PlayerController;
import br.ufc.poo.controle.estrategias.ReproducaoAleatoria;
import br.ufc.poo.excecoes.MidiaNaoEncontradaException;
import br.ufc.poo.modelo.Midia;
import br.ufc.poo.visao.JanelaPrincipal;

// Se a IDE reclamar do ambiente Java, faça de forma manual:
// 1. Abra o terminal na pasta do projeto
// 2. javac -d bin -cp "lib/*" -sourcepath src/main/java src/main/java/br/ufc/poo/Main.java
// 3. java -cp "bin:lib/*" br.ufc.poo.Main

public class Main {
    public static void main(String[] args) {

        final boolean DEBUG = false;

        if (!DEBUG) {
            new JanelaPrincipal();
            return;
        }

        else {
            executarTestesCLI();
        }
    }

    public static void executarTestesCLI() {
        System.out.println("=========================================");
        System.out.println("   MP30 PLAYER - TESTE DE BACKEND (CLI)   ");
        System.out.println("=========================================\n");

        PlayerController player = new PlayerController();

        // em modo debug
        // Musica md1 = new Musica("Bohemian Rhapsody", 355, "Queen", "A Night at the
        // Opera");
        // Musica md2 = new Musica("Hotel California", 390, "Eagles", "Hotel
        // California");
        // Musica md3 = new Musica("Stairway to Heaven", 480, "Led Zeppelin", "Led
        // Zeppelin IV");
        // Musica md4 = new Musica("Billie Jean", 294, "Michael Jackson", "Thriller");

        // em modo arquivo
        Midia ma1 = LeitorMetadados.lerMidia("testeMusicas/teste1.mp3");
        Midia ma2 = LeitorMetadados.lerMidia("testeMusicas/teste2.mp3");
        Midia ma3 = LeitorMetadados.lerMidia("testeMusicas/teste3.mp3");

        System.out.println("[SETUP] Adicionando músicas à biblioteca...");
        // player.adicionarNaPlaylist(md1);
        // player.adicionarNaPlaylist(md2);
        // player.adicionarNaPlaylist(md3);
        if (ma1 != null)
            player.adicionarNaPlaylist(ma1);
        if (ma2 != null)
            player.adicionarNaPlaylist(ma2);
        if (ma3 != null)
            player.adicionarNaPlaylist(ma3);

        // -----------------------------------------------------------------------

        System.out.println("\n--- TESTE 1: Sequencial ---");

        System.out.println(">> Comando: Próxima (Esperado: md1)");
        try {
            player.proxima();
        } catch (MidiaNaoEncontradaException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
        dormir(5);

        // System.out.println(">> Comando: Próxima (Esperado: md2)");
        // player.proxima();
        // dormir(5);
        // -----------------------------------------------------------------------

        System.out.println("\n--- TESTE 2: Fila de Prioridade ---");
        System.out.println("[USER] Adicionando músicas à fila");
        // player.adicionarNaFila(md4);
        player.adicionarNaFila(ma3);

        // System.out.println(">> Comando: Próxima (Esperado: md4 - da fila)");
        // player.proxima();
        // dormir(5);

        System.out.println(">> Comando: Próxima (Esperado: ma3 - da fila)");
        try {
            player.proxima();
        } catch (MidiaNaoEncontradaException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
        dormir(5);

        // System.out.println(">> Comando: Próxima (Esperado: md3 - da fila)");
        // player.proxima();
        // dormir(5);
        // -----------------------------------------------------------------------

        System.out.println("\n--- TESTE 3: Modo Aleatório ---");
        player.setEstrategia(new ReproducaoAleatoria());

        System.out.println(">> Comando: Próxima");
        try {
            player.proxima();
        } catch (MidiaNaoEncontradaException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
        dormir(5);

        // System.out.println(">> Comando: Próxima");
        // player.proxima();
        // dormir(5);

        System.out.println("\n=========================================");
        System.out.println("   FIM DO TESTE - ENCERRANDO PLAYER      ");
        System.out.println("=========================================");

        player.parar();
        System.exit(0);
    }

    // Método auxiliar só para pausar o teste e dar tempo de ouvir
    private static void dormir(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}