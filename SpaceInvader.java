/**
 * Clase principal del juego, incluye el procedimiento main e incializa los graficos
 * y los objetos flota y jugador. Además de calcular las variables globales del juego.
 * 
 * @author Johan de Bruin
 * @version 1.0
 */


import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.Image;
import javax.swing.ImageIcon;

public class SpaceInvader extends Nucleo
{
    //Variables globales del juego
    public static int anchoJuego = 600;
    public static int altoJuego = 600;
    
    public static int inicioX;
    public static int inicioY;
    
    public static boolean juegoPerdido;
    public static boolean juegoGanado;
    
    public static final float velocidadDisparos = 0.4f;
    public static final float velocidadDisparosInvader = 0.2f;
    
    private Image wallpaper;
    private Image fondo;
    private Jugador jugador;
    private Flota flota;
    
    public static void main(String[] args) 
    {
         new SpaceInvader().ejecutar();
    }
    
    /**
     * Con este metodo inciaremos a todas las clases, principalmente será para
     * cargar todas las imagenes y además crear los elementos del juego.
     */
    public void iniciar()
    {
        super.iniciar();
        inicioX = pantalla.getAncho() / 2 - anchoJuego / 2;
        inicioY = pantalla.getAlto() / 2 - altoJuego / 2;
        juegoPerdido = juegoGanado = false;
        //Establecemos el fondo
        wallpaper = new ImageIcon("imagenes\\wallpaper.jpg").getImage();
        fondo = new ImageIcon("imagenes\\fondo.jpg").getImage();
        //iniciar en Sprite definira todas las imagenes que necesitara el juego
        Sprite.iniciar();
        //Iniciamos los elementos del juego
        jugador = new Jugador();
        flota = new Flota();
        
    }
    
    /**
     * Primer metodo del loop del juego
     */
    public void actualizar(long tiempoTranscurrido)
    {
        if(!juegoPerdido) {
            jugador.actualizar(tiempoTranscurrido, flota);
            flota.actualizar(tiempoTranscurrido, jugador);
        }
    }
    
    /**
     * Segun metodo del loop del juego
     */
    public void dibujar(Graphics2D g)
    {
        g.drawImage(wallpaper, 0, 0, null);
        g.drawImage(fondo, inicioX, inicioY, null);
        jugador.dibujar(g);
        flota.dibujar(g);
        if(juegoPerdido) {
            g.drawString("HAS PERDIDO (Escape para salir)", inicioX + (int) anchoJuego / 7, inicioY + altoJuego / 2);
        } else if(juegoGanado) {
            g.drawString("HAS GANADO (Escape para salir)", inicioX + (int) anchoJuego / 7, inicioY + altoJuego / 2);
        }
        
    }

    /**
     * Procedimiento para gestionar las teclas pulsadas.
     */
    public void teclaPresionada(int codigoTecla)
    {
        //Se sale del juego al pulsar la telca escape
        if(codigoTecla == KeyEvent.VK_ESCAPE)
            parar();
        else if(!juegoPerdido)
            jugador.mover(codigoTecla);
    }
    
    /**
     * Procedimiento para gestionar las teclas liberadas
     */
    public void teclaLiberada(int codigoTecla)
    {
        if(!juegoPerdido) {
            jugador.dejarMover(codigoTecla);
        }
    }
}
