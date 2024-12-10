package Cartas;

import Casillas.Casilla;
import Monopoly.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Carta{
    private Integer[] intArray = { 1, 2, 3, 4, 5, 6};
    private List<Integer> cartas = Arrays.asList(intArray);
    public Carta(){
    }
    public abstract void accion(int numero, Tablero tablero, Jugador jugadorTurno, ArrayList<Jugador> jugadoresPartida, Juego juego) throws ExcepcionesEdificio, ExcepcionesDinero;
    public List<Integer> getCartas() {
        return cartas;
    }

}
