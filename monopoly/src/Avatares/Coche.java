package Avatares;

import Monopoly.*;
import Casillas.*;

import java.util.Scanner;

public final class Coche extends Avatar{
    public Coche(Jugador jugador, String ficha, Tablero tablero){
        super(jugador,ficha,tablero);
    }

    @Override
    public void moverEnAvanzado(Dado dadoTurno, Juego juego) throws ExcepcionesAccion {
        Casilla origen = getCasilla();
        Casilla destino;
        int posiciones = dadoTurno.tirada(0);
        getJugador().setNumeroTiradas();
        juego.comprobarRondaPartida();
        int posEstatico = posiciones;
        if(posiciones>=5 && dadoTurno.getContador_coche() <=3){
            setPosicion(posiciones);
            destino = getCasilla();
            Juego.c.escribir(String.format("El avatar %s avanza %d posiciones, desde %s a %s\n", getId(), posiciones, origen.getNombre(), destino.getNombre()));

            if(dadoTurno.getContador_coche() ==3){
                dadoTurno.setContador_coche(0);
                return;
            }

            Juego.c.escribir("Quieres tirar otra vez?");

            String respuesta = Juego.c.leer();
            try {
                if (respuesta.equals("si")) {
                    juego.caerEnCasilla(destino, posEstatico);
                    if (destino instanceof Solar s) {
                        try {
                            s.puedeConstruirGeneral(getJugador());
                        }catch(ExcepcionesEdificio e){
                            Juego.c.escribir(e.getMessage());
                            return;
                        }
                        Juego.c.escribir("Quieres edificar antes de moverte otra vez?");
                        String respuestaEdf = Juego.c.leer();
                        if (respuestaEdf.equals("si")) {
                            //comprobar si es edificable y construir
                            Juego.c.escribir("Qué edificio?");
                            String respuestaEdificio2 = Juego.c.leer();
                            juego.comandoEdificar(respuestaEdificio2);
                        }
                    }
                    dadoTurno.setContador_coche(dadoTurno.getContador_coche() + 1);
                    if (dadoTurno.getContador_coche() <= 3) {
                        moverEnAvanzado(dadoTurno, juego);
                    }
                }
            }catch (ExcepcionesEdificio|ExcepcionesDinero e) {
                Juego.c.escribir(e.getMessage());
                return;
            }

        }
        else if(posiciones<=4 && dadoTurno.getContador_coche()==0){
            setPosicion(posiciones,"atras");
            destino = getCasilla();
            setBloqueado(2);
            Juego.c.escribir(String.format("El avatar %s retrocede %d posiciones, desde %s a %s y no puede lanzar los dados en los siguientes dos turnos\n", getId(), posiciones, origen.getNombre(), destino.getNombre()));
        }else{
            setPosicion(posiciones);
            destino = getCasilla();
            Juego.c.escribir(String.format("El avatar %s avanza %d posiciones, desde %s a %s\n", getId(), posiciones, origen.getNombre(), destino.getNombre()));
        }
        dadoTurno.setContador_coche(0);
    }
}
