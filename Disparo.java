/**
 * Esta clase maneja los disparos de las naves Jugador y Invader
 * 
 * @author Johan de Bruin
 * @version 1.0
 */

public class Disparo extends Sprite
{
    /**
     * Emplea el poliformismo para detectar si la construlle un Jugador o un invader,
     * en cuyo caso la dirección del disparo será hacia arriba o hacia abajo respectivamente.
     */
    public Disparo(Sprite sherif)
    {
        if(sherif instanceof Jugador) {
            velocidadY = -SpaceInvader.velocidadDisparos;
        } else if(sherif instanceof Invader) {
            velocidadY = SpaceInvader.velocidadDisparosInvader;
        }
        x = (int) (sherif.getX() + sherif.getWidth() / 2);
        y = (int) (sherif.getY() - this.getHeight() / 2);
    }
}
