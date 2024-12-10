package Avatares;

import Monopoly.*;
import Casillas.*;

import java.util.Scanner;

public final class Pelota extends Avatar{
    public Pelota(Jugador jugador, String ficha, Tablero tablero){
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

        if(posiciones>=5){
            setPosicion(5);
            destino = getCasilla();
            posiciones-=5;
            Juego.c.escribir(String.format("El avatar %s se para en %s\n", getId(),destino.getNombre()));
            try{
                juego.caerEnCasilla(destino,posEstatico);
            }catch(ExcepcionesDinero e){
                Juego.c.escribir(e.getMessage());
                return;
            }
            if(getEstado()==0){
                juego.setComprobacionCarcelPelota(1);
                return;
            }
            String respuestaPelota="";
            if(destino instanceof Solar){
                Juego.c.escribir("Quieres comprar la casilla?");
                respuestaPelota = Juego.c.leer();
                if(respuestaPelota.equals("si")){
                    try {
                        juego.comandoComprar(destino.getNombre());
                    }catch(ExcepcionesCasilla e){
                        Juego.c.escribir(e.getMessage());
                        return;
                    }
                }
            }
            while(posiciones>2){
                setPosicion(2);
                destino = getCasilla();
                posiciones-=2;
                Juego.c.escribir(String.format("El avatar %s se para en %s\n", getId(),destino.getNombre()));
                try{
                    juego.caerEnCasilla(destino,posEstatico);
                }catch(ExcepcionesDinero e){
                    Juego.c.escribir(e.getMessage());
                    return;
                }
                if(destino.getNombre().equals("Carcel") || destino.getNombre().equals("IrCarcel")){
                    return;
                }
                if(destino instanceof Propiedad){
                    Juego.c.escribir("Quieres comprar la casilla?");
                    respuestaPelota = Juego.c.leer();
                    if(respuestaPelota.equals("si")){
                        try {
                            juego.comandoComprar(destino.getNombre());
                        }catch(ExcepcionesCasilla e){
                            Juego.c.escribir(e.getMessage());
                            return;
                        }
                    }
                }
            }
            setPosicion(posiciones);
            destino = getCasilla();
            //moverse(destino,posiciones);
            Juego.c.escribir(String.format("El avatar %s avanza %d posiciones en total con el movimiento especial pelota, desde %s a %s\n", getId(), posEstatico, origen.getNombre(), destino.getNombre()));

        }
        else{
            setPosicion(posiciones,"atras");
            destino = getCasilla();
            Juego.c.escribir(String.format("El avatar %s retrocede %d posiciones, desde %s a %s\n", getId(), posiciones, origen.getNombre(), destino.getNombre()));
        }
    }
}
