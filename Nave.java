/**
 * Se encarga de manejar naves que disparan
 * 
 * @author Johan de Bruin
 * @version 1.0
 */

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Nave extends Sprite
{
    protected ArrayList<Disparo> disparos;
    
    /**
     * Constructor, se dedica a incializar una nave con capacidad de gestionar
     * multiples disparos
     */
    public Nave()
    {
        disparos = new ArrayList<Disparo>();
    }
    
    /**
     * Devuelve true en caso de que alguno de los disparos de la nave intersecta con
     * el Sprite pasado por el parametro.
     * @param victima La victima que queremos ver si hemos conseguido dispararla.
     * @return True en caso de que algún disparo intersecte con la victima, sino false
     */
    public boolean instersectaDisparos(Sprite victima)
    {
        //Comprobamos cada uno de los disparos, si intersecta lo eliminamos y return true
        for(int i = 0; i < disparos.size(); i++) {
            if(disparos.get(i).intersects(victima)) {
                disparos.remove(i);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Crea un objeto disparo en la posición de la nave
     */    
    public void disparar() {
        disparos.add(new Disparo(this));
    }
    
    /**
     * Actualizar
     * @param tiempoTranscurrido tiempo desde la ultima actualizacion (milisegundos)
     */
    public void actualizar(long tiempoTranscurrido)
    {
        super.actualizar(tiempoTranscurrido);
        actualizarDisparos(tiempoTranscurrido);
    }
    
    /**
     * Dubja la nave en el grafico
     * @param g Graphics2D sobre el que se va ha dibujar
     */
    public void dibujar(Graphics2D g) 
    {
        //Los invaders tienen su propia forma específica de dibujarse
        if(this instanceof Jugador)
            super.dibujar(g);
        for(Disparo disparo : disparos) {
            disparo.dibujar(g);
        }
    }
    
    /**
     * Metodo especial para actualizar los disparos únicamente
     */
    public void actualizarDisparos(long tiempoTranscurrido)
    {
        ArrayList<Integer> disparosADestruir = new ArrayList<Integer>();
        for(int i = 0; i < disparos.size(); i++) {
            if(disparos.get(i).y < SpaceInvader.inicioY 
                || disparos.get(i).y > SpaceInvader.inicioY + SpaceInvader.altoJuego - disparos.get(i).getHeight())
                disparosADestruir.add(i);
            else 
                disparos.get(i).actualizar(tiempoTranscurrido);
        }
        for(int disparoADestruir : disparosADestruir) {
            disparos.remove(disparoADestruir);
        }
    }
}
