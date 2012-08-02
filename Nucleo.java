import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 
 * <p>Nucleo de un juego sencillo en Java, emplea GestorPantalla para mantener una resolución
 * a pantlla completa compatible y proporciona métodos claves para ejecutar juegos.
 * Crea un Loop de Juego el cual establece unos 20 milisegundos de espera entre actualización.</p>
 * <p>{@link #ejecutar} Ejecuta el juego</p>
 * <p>{@link #iniciar} Instrucciones que debe seguir el juego al iniciarse</p>
 * <p>{@link #actualizar} Este método incorpora una llamada para establecer la forma en la que se comportan
 * los objetos en cada actualización del juego. Es llamado en cada iteración del loop del juego.</p>
 * <p>{@link #dibujar} También llamado cada iteración del loop del juego.</p>
 * 
 * @author Johan de Bruin
 * @version 1.0
 * <br />
 * 1.1 Capacidad al nucleo de forma nativa de escuchar teclas, aportamos el metodo teclaPresionada(int)
 * y teclaLiberada(int)
 */

public abstract class Nucleo implements KeyListener
{
    /**
     * Por defecto nuestros juegos funcionaran con 1024x768 a 32 bits
     * podemos aumentar los elementos de los modos por si acaso no 
     * fuera compatible
     */
    private DisplayMode modos[] = {
        new DisplayMode(1024, 768, 32, 0),
        new DisplayMode(1024, 768, 24, 0),
        new DisplayMode(800,600,32,0),
        new DisplayMode(800,600,24,0),
    };
    private boolean ejecutando;
    protected GestorPantalla pantalla;
    
   /**
    * Detiene la actividad del juego
    */
   public void parar() {
       ejecutando = false;
   }
   
   /**
    * Comienza a ejecutar los distintos elementos del juego, este método es el que hay
    * que crear para que actualizar y dibujar comiencen su actividad.
    * Una vez finalizado restaura la pantalla para poder finalizar la ejecución del juego.
    */
   public void ejecutar() {
        try {
            iniciar();
            loop();
        } finally {
            pantalla.restaurarPantalla();
        }
   }
   
   /**
    * Método que activa los componentes, crea una ventana a pantalla completa,
    * establece un fondo y una fuente por defecto también.
    */
   public void iniciar() {
        pantalla = new GestorPantalla();
        DisplayMode modo = pantalla.primerModoCompatible(modos);
        pantalla.setPantallaCompleta(modo);
        
        Window ventana = pantalla.getPantallaCompleta();
        ventana.setFont(new Font("LucidaSans", Font.PLAIN, 30));
        ventana.setBackground(Color.WHITE);
        ventana.setForeground(Color.BLACK);
        ventana.setFocusTraversalKeysEnabled(false);
        ventana.addKeyListener(this);
        ejecutando = true;
   }
   
   /**
    * La logica del juego, detecta el tiempo transcurrido entre actualización
    * y actualización y llama a los metodos actualizar() y dibujar()
    */
   public void loop() {
        long tiempoComienzo = System.currentTimeMillis();
        long tiempoAcumulado = tiempoComienzo;
        
        while(ejecutando) {
            long tiempoTranscurrido = System.currentTimeMillis() - tiempoAcumulado;
            tiempoAcumulado += tiempoTranscurrido;
            
            actualizar(tiempoTranscurrido);
            
            Graphics2D g = pantalla.getGraficos();
            dibujar(g);
            g.dispose();
            pantalla.actualizar();
            
            try {
                Thread.sleep(20);
            } catch (Exception e) {}
        }
   }
   
   /**
    * Método para actualizar los distintos elementos del juego
    * @param tiempoTranscurrido el tiempo que ha transcurrido desde la ultima actualización
    */
   public void actualizar(long tiempoTranscurrido) {
   }
   
   /**
    * Para gestionar cuando una tecla es presionada
    */
   public abstract void teclaPresionada(int codigoTecla);
   
   /**
    * Para gestionar cuando una tecla es liberada
    */
   public abstract void teclaLiberada(int intcodigoTecla);
   
   /**
    * Dibuja en el lienzo cada iteración del juego. La forma de tratar el grafico del parámetro
    * es la siguiente
    */
   public abstract void dibujar(Graphics2D g);

   public void keyPressed(KeyEvent evento) {
       int codigoTecla = evento.getKeyCode();
       teclaPresionada(codigoTecla);
       evento.consume();
   }
    
   public void keyReleased(KeyEvent evento) {
       int codigoTecla = evento.getKeyCode();
       teclaLiberada(codigoTecla);
       evento.consume();
   }
    
   public void keyTyped(KeyEvent evento) {
       evento.consume();
   }
}