package br.ufc.poo.excecoes;

/**
 * Exceção lançada quando uma mídia solicitada não é encontrada.
 * Utilizada quando a playlist está vazia ou quando não há próxima/anterior
 * mídia.
 * 
 * @author Bruno Lage
 * @version 1.0
 */
public class MidiaNaoEncontradaException extends Exception {

    /**
     * Cria uma nova exceção com a mensagem especificada.
     * 
     * @param mensagem a mensagem descritiva do erro
     */
    public MidiaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
