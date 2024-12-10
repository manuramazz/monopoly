package Casillas;

import Cartas.*;
import Monopoly.*;

import java.util.ArrayList;

public final class AccionSuerte extends Accion{
    public AccionSuerte(int numero){
        super(numero,"Suerte");

    }
    public void accion(int numero, Tablero tablero, Jugador jugadorTurno, ArrayList<Jugador> jugadoresPartida, Juego juego){
        CartaSuerte suerte = new CartaSuerte();
        try {
            suerte.accion(numero, tablero, jugadorTurno, jugadoresPartida, juego);
        }catch(ExcepcionesDinero| ExcepcionesEdificio e){
            Juego.c.escribir(e.getMessage());
        }
    }

}
