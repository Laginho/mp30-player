package br.ufc.poo.excecoes;

/**
 * Exceção lançada quando se tenta reproduzir uma mídia que já está tocando.
 * Evita reproduções duplicadas da mesma mídia.
 * 
 * @author Bruno Lage
 * @version 1.0
 */
public class MidiaJaTocandoException extends Exception {

    /**
     * Cria uma nova exceção com a mensagem especificada.
     * 
     * @param mensagem a mensagem descritiva do erro
     */
    public MidiaJaTocandoException(String mensagem) {
        super(mensagem);
    }
}