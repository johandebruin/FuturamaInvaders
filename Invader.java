/**
 * Logidca de como funciona el Invader extendido de Nave
 * 
 * @author Johan de Bruin
 * @version 1.0
 */

import java.awt.Graphics2D;

public class Invader extends Nave
{
    private boolean imagenActual;
    
    /**
     * Constructor del invader
     * @param posX posicion de la coordenada X donde queramos construir el invader
     * @param posY posicion de la coordenada Y donde queramos construir el invader
     */
    public Invader(int posX, int posY)
    {
        super();
        x = posX;
        y = posY;
        imagenActual = true;
        
    }
    
    /**
     * Sobreescribimos el metodo actualizar para que cada vez que se actualice
     * las imagenes cambien. Dado que la velocidad de movimiento es 1 en este parametro
     * no será el tiempo de actualización sino la distancia en pixeles.
     * @param distancia Entero con la distancia que queramos que recorra el invader.
     */
    public void actualizar(int distancia)
    {
        super.actualizar(distancia);
        imagenActual = (imagenActual) ? false : true;
    }
    
    /**
     * Pasamos como parametro el movimiento en forma de texto para deficir como
     * se mueve el invader. Notar que la velocidad de movimiento siempore será de 1.
     * @param movimiento Gestiona los siguientes literales: Abajo Derecha Izquierda.
     */
    public void mover(String movimiento)
    {
        if(movimiento.equals("Abajo")) moverAbajo();
        else if(movimiento.equals("Derecha")) moverDerecha();
        else if(movimiento.equals("Izquierda")) moverIzquierda();
    }
    
    private void moverDerecha()
    {
        velocidadX = 1;
        velocidadY = 0;
    }
    
    private void moverIzquierda()
    {
        velocidadX = -1;
        velocidadY = 0;
    }
    
    private void moverAbajo()
    {
        velocidadX = 0;
        velocidadY = 1;
    }
    
    /**
     * Sobreescribimos dibujar para que cambie la imagen del invader en cada actualizacion
     * dandole la sensación de animación.
     */
    public void dibujar(Graphics2D g)
    {
        super.dibujar(g);
        if(imagenActual)
            g.drawImage(imagenes.get("Invader0"), x, y, null);
        else
            g.drawImage(imagenes.get("Invader1"), x, y, null);
    }
}
