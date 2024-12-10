package Casillas;
import Cartas.*;
import Monopoly.ExcepcionesDinero;
import Monopoly.Juego;
import Monopoly.Jugador;
import Monopoly.Tablero;

import java.util.ArrayList;

public final class AccionCaja extends Accion{
    public AccionCaja(int numero){
        super(numero,"Caja");

    }
    public void accion(int numero, Tablero tablero, Jugador jugadorTurno, ArrayList<Jugador> jugadoresPartida, Juego juego){
        CartaCaja caja = new CartaCaja();
        try {
            caja.accion(numero, tablero, jugadorTurno, jugadoresPartida, juego);
        }catch(ExcepcionesDinero e){
            Juego.c.escribir(e.getMessage());
        }
    }
}
