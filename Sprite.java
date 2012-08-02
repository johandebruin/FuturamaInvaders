/**
 * Todos los elementos que incorporen cierto dinamismo deberán ser herencia de esta clase.
 * Algunas variables que hereda son height, width, x, y (con sus correspondientes metodos de acceso)
 * y el método intersects(Rectangle) que nos será útil para comprobar colisiones, setLocation(x,y)
 * 
 * @author Johan de Bruin
 * @version 2.0 Base
 * @version 2.1 Refactorizado el sistema de iniciar, ahora las imagenes son un HashMap con el nombre de la clase
 */

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.util.HashMap;

public class Sprite extends Rectangle
{
    public static HashMap<String, Image> imagenes;
    
    /**
     * Este metodo por desgracia aumenta el acoplamiento, teniendo que ser redefinido en cada juego,
     * pero es la mejor estrategia a la hora de gestionar las imagenes del juego de forma sencilla
     */
    public static void iniciar()
    {
        imagenes = new HashMap<String, Image>();
        imagenes.put("Jugador", new ImageIcon("imagenes\\jugador.png").getImage());
        imagenes.put("Disparo", new ImageIcon("imagenes\\disparo.png").getImage());
        imagenes.put("Invader", new ImageIcon("imagenes\\invader0.png").getImage());
        imagenes.put("Invader0", new ImageIcon("imagenes\\invader0.png").getImage());
        imagenes.put("Invader1", new ImageIcon("imagenes\\invader1.png").getImage());
    }
    
    protected float velocidadX;
    protected float velocidadY;
    
    /**
     * Constructor del sprite, debe de haberse incializado su método estático para que así disponga
     * de alguna imagen asociada.
     */
    public Sprite() {
        super(0, 0, 0, 0);
        setSize(imagenes.get(this.getClass().getName()).getWidth(null),
                    imagenes.get(this.getClass().getName()).getHeight(null));
    }
    
    /**
     * Redifine la posición del sprite en función a la velocidad y el tiempo transcurrido
     * @param tiempoTranscurrido valor del tiempo que ha transcurrido desde la última actualización
     */
    public void actualizar(Long tiempoTranscurrido) {
        x += velocidadX * tiempoTranscurrido;
        y += velocidadY * tiempoTranscurrido;
    }
    
    /**
     * Dibuja la imagen actual del sprite en el grafico pasado por parametro
     * @param g Graphics2D en el cual queremos dibujar el sprite
     */
    public void dibujar(Graphics2D g)
    {
        g.drawImage(imagenes.get(this.getClass().getName()), x, y, null);
    }
}