import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.GraphicsConfiguration;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;

/**
 * Clase que gestiona los potenciales graficos del juego y optimiza
 * las imagenes usando la técnica del double buffer
 * 
 * @author Johan de Bruin
 * @version 1.0.
 */
public class GestorPantalla
{
    private GraphicsDevice dispositivoGrafico;
    
    /**
     * Constructor, obtiene del entorno los dispositivos gráficos
     * disponibles y los almacena en la clase.
     */
    public GestorPantalla() {
        GraphicsEnvironment e =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        dispositivoGrafico =  e.getDefaultScreenDevice();
    }
    
    /**
     * Obtiene los diferentes dispositivos gráficos compatibles
     * @return Matriz DisplayMode de los distintos dispositivos compatibles
     * del ordenador ejecutador del programa.
     */
    public DisplayMode[] getModosCompatible() {
        return dispositivoGrafico.getDisplayModes();
    }

    /**
     * Compara cada uno de los modos insertados con los modos nativos del
     * ordenador y devuelve el primero compatible
     * @param modos[] Distintos modelos gráficos que el juego puede permitir
     */
    public DisplayMode primerModoCompatible(DisplayMode modos[]) {
        DisplayMode modosBuenos[] = dispositivoGrafico.getDisplayModes();
        for(int x = 0; x < modos.length; x++) {
            for(int y = 0; y < modosBuenos.length; y++) {
                if(coincidenModoPantalla(modos[x], modosBuenos[y])) {
                    return modos[x];
                }
            }
        }
        return null;
    }
    
    /**
     * Obtiene el modo gráfico que se está empleando actualmente
     */
    public DisplayMode obtenerModoActual() {
       return dispositivoGrafico.getDisplayMode();
    }

    /**
     * Comprueba si dos modos de pantalla coinciden según su resolución,
     * los bits que muestra y la reproducción de frames.
     * @param m1 Primer modo a comparar
     * @param m2 Segundo modo a comparar
     */
    public boolean coincidenModoPantalla(DisplayMode m1, DisplayMode m2) {
        //Comprueba la resolucion
        if(m1.getWidth() != m2.getWidth() || m1.getHeight() != m2.getHeight())
        {
            return false;
        }
        //Colores de bis
        if(m1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
                m2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
                m1.getBitDepth() != m2.getBitDepth()) {
            return false;
        }
        //Comprueba frames
        if(m1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN &&
                m2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN &&
                m1.getRefreshRate() != m2.getRefreshRate()) {
            return false;
        }
        return true;
    }
    
    /**
     * Sometemos a JFrame a ocupar la pantalla completa
     */
    public void setPantallaCompleta(DisplayMode dm) {
        JFrame f = new JFrame();
        f.setUndecorated(true);
        f.setIgnoreRepaint(true);
        f.setResizable(false);
        dispositivoGrafico.setFullScreenWindow(f);

        if(dm != null && dispositivoGrafico.isDisplayChangeSupported()) {
            try {
                dispositivoGrafico.setDisplayMode(dm);
            } catch(Exception e) {}
        }
        f.createBufferStrategy(2);
    }

    /**
     * Obtenemos el objeto Graphics2D que nos permitirá dibujar sobre el JFrame
     * más adelante
     * @return El objeto Graphics2D si detecta la pantalla completa, sino null
     */
    public Graphics2D getGraficos() {
        Window w = dispositivoGrafico.getFullScreenWindow();
        if(w != null) {
            BufferStrategy s = w.getBufferStrategy();
            return (Graphics2D) s.getDrawGraphics();
        } else {
            return null;
        }
    }

    /**
     * Actualizar lo que muestra la pantalla
     */
    public void actualizar() {
        Window w = dispositivoGrafico.getFullScreenWindow();
        if(w != null) {
            BufferStrategy s = w.getBufferStrategy();
            //Con esto evitamos los parpadeos de las animaciones!
            if(!s.contentsLost()) {
                s.show();
            }
        }
    }

    /**
     * Obtener la pantalla completa
     * @return Objeto Window con la pantalla completa
     */
    public Window getPantallaCompleta() {
        return dispositivoGrafico.getFullScreenWindow();
    }
    
    /**
     * Obtiene el ancho utilizado para la pantalla completa
     * @return Entero con el ancho de la pantalla 
     */
    public int getAncho() {
        Window w = dispositivoGrafico.getFullScreenWindow();
        if(w != null) {
            return w.getWidth();
        } else {
            return 0;
        }
    }

    /**
     * Obtiene el alto utilizado para la pantalla completa
     * @return Entero con el alto de la pantalla 
     */
    public int getAlto() {
        Window w = dispositivoGrafico.getFullScreenWindow();
        if(w != null) {
            return w.getHeight();
        } else {
            return 0;
        }
    }

    /**
     * Método para salir de la pantalla completa y reestablecer al modo original
     */
    public void restaurarPantalla() {
        Window w = dispositivoGrafico.getFullScreenWindow();
        if(w != null) {
            w.dispose();
        }
        dispositivoGrafico.setFullScreenWindow(null);
    }

    /**
     * Crea una imagen compatible con las características del monitor
     * @return BufferedImage
     */
    private BufferedImage crearImagenCompatible(int w, int h, int t) {
        Window win = dispositivoGrafico.getFullScreenWindow();
        if(win != null) {
            //Obtiene las caracteristicas del monitor
            GraphicsConfiguration gc = win.getGraphicsConfiguration();
            return gc.createCompatibleImage(w, h, t);
        }
        return null;
    }

}