/**
 * Crea una flota de Invaders.
 * 
 * @author Johan de Bruin
 * @version 1.0
 */

import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics2D;

public class Flota
{
    /**
     * Valores que definen a la flota
     */
    private int tropasFila = 10;
    private int tropasColumna = 6;
    private int espacioMovimiento = 20;
    private int velocidadMovimiento = 300;
    private int tiempoMovimiento = 1000;
    
    /**
     * Variables para gestionar a la flota
     */
    private int tiempoAcumulado;
    private int anchoInvader;
    private int altoInvader;
    
    private boolean moviendoDerecha;
    private String movimiento;
    
    private ArrayList<Invader> invaders;
    
    /**
     * Constructor por defecto construye una flota con las opciones de los
     * campos especificados anteriormente.
     */
    public Flota()
    {
        invaders = new ArrayList<Invader>();
        anchoInvader = Sprite.imagenes.get("Invader").getWidth(null);
        altoInvader = Sprite.imagenes.get("Invader").getHeight(null);
        
        for(int i = 0; i < tropasFila; i++) {
            for(int e = 0; e < tropasColumna; e++) {
                invaders.add( new Invader(
                    SpaceInvader.inicioX + i * (anchoInvader + anchoInvader / 4) + 20,
                    SpaceInvader.inicioY + e * (altoInvader + altoInvader / 4) + 20));
            }
        }
        moviendoDerecha = true;
    }
    
    /**
     * Actualizar, gestiona el tiempo de tal manera que solo actualizara cada tiempoMovimiento
     * @param tiempoTranscurrido tiempo transcurrido desde la última actualización del juego
     * @jugador el objeto del jugador para comprobar si alguno de sus disparos intersecta con 
     * los invaders de la flota.
     */
    public void actualizar(long tiempoTranscurrido, Jugador jugador)
    {
        //Actualmente solo funciona con un disparo... cambiar a una matriz para varios
        int naveDestruida = -1;
        //Comprobamos los disparos
        for(int i = 0; i < invaders.size(); i++) {
            if(jugador.instersectaDisparos(invaders.get(i))) {
                    naveDestruida = i;
            }
        }
        if(naveDestruida != -1) {
            invaders.remove(naveDestruida);
        }
        //Si no quedan naves se gano el juego <-D
        if(invaders.size() == 0) {
            SpaceInvader.juegoGanado = true;
        }
        //1000 milisegundos es lo mismo que un segundo... 
        if(tiempoAcumulado >= tiempoMovimiento && invaders.size() > 0)
        {
            //Comprobamos el movimiento
            comprobarMovimiento();
            for(Invader invader : invaders) {
                invader.mover(movimiento);                
                invader.actualizar(espacioMovimiento);
            }
            tiempoAcumulado = 0;
            //Una de cada dos disparán algun disparillo...
            Random generador = new Random();
            if(generador.nextBoolean()) {
                int invaderADisparar = generador.nextInt(invaders.size());
                invaders.get(invaderADisparar).disparar();
            }
        }
        else {
            for(Invader invader : invaders) {
                invader.actualizarDisparos(tiempoTranscurrido);
                if(invader.getY() > SpaceInvader.inicioY + SpaceInvader.altoJuego)
                    SpaceInvader.juegoPerdido = true;
            }
            tiempoAcumulado += tiempoTranscurrido;
        }
    }
    
    /**
     * Dibuja en la pantalla a todos los invaders de la flota
     */
    public void dibujar(Graphics2D g)
    {
        for(Invader invader : invaders) {
            invader.dibujar(g);   
        }
    }
    
    /**
     * Comprueba si alguno de los disparos de la flota intersecta con la victima
     * @sprite victima en la que queremos comproba si intersectan los disparos
     * @return True si algun disparo intersecta, sino false.
     */
    public boolean intersectaDisparos(Sprite sprite)
    {
        for(Invader invader : invaders) {
            if(invader.instersectaDisparos(sprite))
                return true;
        }
        return false;
    }
    
    private void comprobarMovimiento()
    {
        if(moviendoDerecha && invaders.get(invaders.size() - 1).x + 
            espacioMovimiento + anchoInvader >= SpaceInvader.inicioX + SpaceInvader.anchoJuego) {
                movimiento = "Abajo"; 
                moviendoDerecha = false;
        } else if(!moviendoDerecha && invaders.get(0).x - 
            espacioMovimiento <= SpaceInvader.inicioX) {
                movimiento = "Abajo";
                moviendoDerecha = true;
        } else if(moviendoDerecha) { movimiento = "Derecha";
        } else if(!moviendoDerecha) { movimiento = "Izquierda"; }     
    }
}
