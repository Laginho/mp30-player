package br.ufc.poo.modelo.interfaces;

/**
 * Interface que define o contrato para objetos reproduzíveis.
 * Qualquer classe que implemente esta interface deve fornecer
 * implementações para iniciar e parar a reprodução.
 * 
 * @author Bruno Lage
 * @version 1.0
 */
public interface Reproduzivel {

    /**
     * Inicia a reprodução do objeto.
     */
    void reproduzir();

    /**
     * Para a reprodução do objeto.
     */
    void parar();
}
