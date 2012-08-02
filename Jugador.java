
/**
 * Clase del jugador, es un sprite que se extiende de nave (incorpora
 * la capacidad de disparar)
 * 
 * @author Johan de Bruin
 * @version 2.0
 */

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Jugador extends Nave
{
    /**
     * La velocidad del jugador, es constante dado que no hay objetos que la alteren
     */
    private final float velocidad = 0.3f;
    
    /**
     * El constructor depende de las variables estaticas SpaceInvader.inicioX y SpaceInvader.inicioY
     * para colocarse en el extremo izquierdo de la pantalla
     */
    public Jugador()
    {
        x = SpaceInvader.inicioX;
        //Convertimos el valor de getHeight double, a int
        y = SpaceInvader.inicioY + SpaceInvader.anchoJuego - (int) getHeight();
    }
    
    /**
     * Actualizar modificado para comprobar si la flota pasada por parametro intersecta
     * alguno de sus disparos
     * @param tiempoTranscurrido el tiempo transcurrido en milisegundos
     */
    public void actualizar(long tiempoTranscurrido, Flota flota)
    {
        super.actualizar(tiempoTranscurrido);
        //Sea como sea nunca podra sobresalir de los límites laterales
        if(x < SpaceInvader.inicioX)
            x = SpaceInvader.inicioX;
        if(x > SpaceInvader.inicioX + SpaceInvader.anchoJuego - (int) getWidth())
            x = SpaceInvader.inicioX + SpaceInvader.anchoJuego - (int) getWidth();
        //Si le disparan ha perdido
        if(flota.intersectaDisparos(this)) {
            SpaceInvader.juegoPerdido = true;
        }
    }
    
    /**
     * Mueve al jugador
     * @param codigoTecla el numero que corresponde a los distintos codigos de las teclas
     * hasta ahora gestiona VK_LEFT VK_RIGHT y VK_SPACE
     */
    public void mover(int codigoTecla)
    {
        if(codigoTecla == KeyEvent.VK_LEFT)
            moverIzquierda(true);
        else if(codigoTecla == KeyEvent.VK_RIGHT)
            moverDerecha(true);
        else if(codigoTecla == KeyEvent.VK_SPACE)
            disparar();
    }
    
    /**
     * Dira cuando debe dejar de moverse
     * @param codigoTecla gestiona VK_LEFT o VK_RIGHT
     */
    public void dejarMover(int codigoTecla)
    {
        if(codigoTecla == KeyEvent.VK_LEFT)
            moverIzquierda(false);
        else if(codigoTecla == KeyEvent.VK_RIGHT)
            moverDerecha(false);
    }
    
    /**
     * Sobreescribimos el metodo de nave para que solo se pueda disparar un
     * disparo a la vez.
     */
    public void disparar() {
        if(disparos.size() == 0) {
            super.disparar();
        }
    }
    
    private void moverIzquierda(boolean mover)
    {
        if(mover) {
            velocidadX = -velocidad;
        } else {
            velocidadX = 0;
        }
    }
    
    private void moverDerecha(boolean mover)
    {
        if(mover) {
            velocidadX = velocidad;
        } else {
            velocidadX = 0;
        }
    }

}
