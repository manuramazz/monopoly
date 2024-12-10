package Cartas;

import Monopoly.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class CartaCaja extends Carta{
    public void accion(int numero, Tablero tablero, Jugador jugadorTurno, ArrayList<Jugador> jugadoresPartida, Juego juego)  throws ExcepcionesDinero{
        List<Integer> cartas = getCartas();
        Collections.shuffle(cartas);
        switch (cartas.get(numero-1)){
            case 1:
                Juego.c.escribir("\nDevolución de Hacienda. Cobra 20000€.\n");
                jugadorTurno.setFortuna(20000, "suma");
                jugadorTurno.setPremiosInversionesOBote(20000);
                break;
            case 2:
                Juego.c.escribir("\nTu compañía de Internet obtiene beneficios. Recibe 50000€.\n");
                jugadorTurno.setFortuna(50000, "suma");
                jugadorTurno.setPremiosInversionesOBote(50000);
                break;
            case 3:
                Juego.c.escribir("\nRecibe 100000€ de beneficios por alquilar los servicios de tu jet privado.\n");
                jugadorTurno.setFortuna(100000, "suma");
                jugadorTurno.setPremiosInversionesOBote(10000);
                break;
            case 4:
                Juego.c.escribir("\nTe investigan por fraude de identidad. Ve a la Cárcel. Ve directamente sin pasar por la casilla de Salida y sin cobrar.\n");
                jugadorTurno.getAvatar().setPosicion(10, 0);
                jugadorTurno.setVecesEnLaCarcel();
                jugadorTurno.setHaberTirado(true);
                try {
                    juego.comandoAcabarTurno();
                }catch(ExcepcionesAccion e){
                    Juego.c.escribir(e.getMessage());
                }
                break;
            case 5:
                Juego.c.escribir("\nAlquilas a tus compañeros una villa en Cannes durante una semana. Paga 5000€ a cada jugador.\n");
                for(Jugador jugador:jugadoresPartida){
                    if(!jugador.getNombre().equals(jugadorTurno.getNombre())){
                        jugadorTurno.setFortuna(5000, "resta");
                        jugadorTurno.setPagoTasasEImpuestos(5000);
                        if (!jugadorTurno.getEstarPobre()) {
                            jugador.setFortuna(5000, "suma");
                            jugador.setPremiosInversionesOBote(5000);
                            Juego.c.escribir(String.format("%s recibe 5000€.\n",jugador.getNombre()));
                        }else{
                            throw new ExcepcionesDinero("El jugador no tiene dinero suficiente.", jugadorTurno.getNombre(), jugadorTurno.getFortuna());
                        }
                    }
                }
                break;
            case 6:
                Juego.c.escribir("\nColócate en la casilla de Salida. Cobra " + tablero.getColeccionCasillas().get(0).getValor() + "€.\n");
                jugadorTurno.getAvatar().setPosicion(40-jugadorTurno.getAvatar().getPosicion());
                juego.comprobarRondaPartida();
                break;
        }
    }
}
