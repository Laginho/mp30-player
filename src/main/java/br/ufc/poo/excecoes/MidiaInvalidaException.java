package br.ufc.poo.excecoes;

/**
 * Exceção lançada quando uma mídia é inválida ou corrompida.
 * Utilizada para arquivos que não podem ser lidos ou processados.
 * 
 * @author Bruno Lage
 * @version 1.0
 */
public class MidiaInvalidaException extends Exception {

    /**
     * Cria uma nova exceção com a mensagem especificada.
     * 
     * @param mensagem a mensagem descritiva do erro
     */
    public MidiaInvalidaException(String mensagem) {
        super(mensagem);
    }
}
