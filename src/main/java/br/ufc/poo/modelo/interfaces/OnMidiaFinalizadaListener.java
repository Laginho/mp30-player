package br.ufc.poo.modelo.interfaces;

/**
 * Interface de callback para notificação de término de mídia.
 * Implementa o padrão Observer para permitir que objetos sejam
 * notificados quando uma mídia termina sua reprodução.
 * 
 * @author Bruno Lage
 * @version 1.0
 * @see br.ufc.poo.modelo.Midia
 */
public interface OnMidiaFinalizadaListener {

    /**
     * Método chamado quando a mídia finaliza sua reprodução naturalmente.
     */
    void onMidiaFinalizada();
}