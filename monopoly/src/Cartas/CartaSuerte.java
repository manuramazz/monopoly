package Cartas;
import Monopoly.*;
import Casillas.*;
import java.util.*;
import Edificios.*;

public final class CartaSuerte extends Carta{
    public void accion(int numero,Tablero tablero, Jugador jugadorTurno, ArrayList<Jugador> jugadoresPartida,Juego juego) throws ExcepcionesEdificio, ExcepcionesDinero{
        List<Integer> cartas = getCartas();
        Collections.shuffle(cartas);
        switch (cartas.get(numero-1)){
            case 1:
                Juego.c.escribir("\n¡Has ganado el bote de la lotería! Recibe 100000€.\n");
                jugadorTurno.setFortuna(100000, "suma");
                jugadorTurno.setPremiosInversionesOBote(10000);
                break;
            case 2:
                Juego.c.escribir("\nTe multan por usar el móvil mientras conduces. Paga 15000€.\n");
                jugadorTurno.setFortuna(15000, "resta");
                if (!jugadorTurno.getEstarPobre()){
                    tablero.getColeccionCasillas().get(20).setValor(15000, "suma");
                    jugadorTurno.setPagoTasasEImpuestos(15000);
                }else{
                    throw new ExcepcionesDinero("El jugador no tiene dinero suficiente.",jugadorTurno.getNombre(),jugadorTurno.getFortuna());
                }
                break;
            case 3:
                Juego.c.escribir("\nBeneficio por la venta de tus acciones. Recibe 15000€.\n");
                jugadorTurno.setFortuna(15000, "suma");
                jugadorTurno.setPremiosInversionesOBote(15000);
                break;
            case 4:
                Casilla casilla = jugadorTurno.getAvatar().getCasilla();
                int posInicial = jugadorTurno.getAvatar().getCasilla().getNumero();
                boolean pasarSalida = false;

                Juego.c.escribir("\nAvanza hasta la casilla de transporte más cercana. Si no tiene dueño, puedes comprársela a la banca. Si tiene dueño, paga al dueño el doble de la operación indicada.\n");

                for (int i = posInicial; !casilla.getTipo().equals("Transporte"); i++){
                    if(tablero.getColeccionCasillas().get(i).getTipo().equals("Transporte")){
                        casilla = tablero.getColeccionCasillas().get(i);
                        break;
                    }
                    if(i == 40){
                        i -= 40;
                        pasarSalida = true;
                        jugadorTurno.getAvatar().setRonda( jugadorTurno.getAvatar().getRonda()+1);
                        juego.comprobarRondaPartida();
                    }
                }
                if (pasarSalida){
                    Juego.c.escribir("El jugador " + jugadorTurno.getNombre() + " cobra " + tablero.getColeccionCasillas().get(0).getValor() + "€ pasar por la Salida.");
                    jugadorTurno.setFortuna(tablero.getColeccionCasillas().get(0).getValor(), "suma");
                }
                jugadorTurno.getAvatar().setPosicion(casilla.getNumero()-jugadorTurno.getAvatar().getCasilla().getNumero());
                try {
                    juego.moverseCartas(casilla);
                }catch(ExcepcionesDinero|ExcepcionesCasilla e){
                    Juego.c.escribir(e.getMessage());
                }
                break;
            case 5:
                Juego.c.escribir("\nHas sido elegido presidente de la junta directiva. Paga a cada jugador 25000€.");
                for(Jugador jugador:jugadoresPartida){
                    if(!jugador.getNombre().equals(jugadorTurno.getNombre())){
                        jugadorTurno.setFortuna(25000, "resta");
                        if (!jugadorTurno.getEstarPobre()) {
                            jugador.setFortuna(25000, "suma");
                            jugadorTurno.setPagoTasasEImpuestos(25000);
                            jugador.setPremiosInversionesOBote(25000);
                            Juego.c.escribir(String.format("%s recibe 25000€.\n",jugador.getNombre()));
                        }else{
                            throw new ExcepcionesDinero("El jugador no tiene dinero suficiente.",jugadorTurno.getNombre(),jugadorTurno.getFortuna());
                        }
                    }
                }
                break;
            case 6:
                Juego.c.escribir("\nEl aumento del impuesto sobre bienes inmuebles afecta a todas tus propiedades. Paga 10000€ por casa, 35000€ por hotel, 20000€ por piscina y 25000€ por pista de deportes.\n");
                if (!jugadorTurno.getEdificios().isEmpty()) {
                    for (Edificio edificio : jugadorTurno.getEdificios()) {
                        if (!jugadorTurno.getEstarPobre()) {
                            switch (edificio.getTipo()) {
                                case "casa":
                                    jugadorTurno.setFortuna(10000, "resta");
                                    if(!jugadorTurno.getEstarPobre()){
                                        Juego.c.escribir(String.format("Pagas 10000 euros por tu casa en %s\n", edificio.getCasilla().getNombre()));
                                        tablero.getColeccionCasillas().get(20).setValor(10000, "suma");
                                        jugadorTurno.setPagoTasasEImpuestos(10000);
                                    }else{
                                        throw new ExcepcionesDinero("El jugador no tiene dinero suficiente.",jugadorTurno.getNombre(),jugadorTurno.getFortuna());
                                    }
                                    break;
                                case "hotel":
                                    jugadorTurno.setFortuna(35000, "resta");
                                    if(!jugadorTurno.getEstarPobre()) {
                                        Juego.c.escribir(String.format("Pagas 35000 euros por tu hotel en %s\n", edificio.getCasilla().getNombre()));
                                        tablero.getColeccionCasillas().get(20).setValor(35000, "suma");
                                        jugadorTurno.setPagoTasasEImpuestos(35000);
                                    }else{
                                        throw new ExcepcionesDinero("El jugador no tiene dinero suficiente.",jugadorTurno.getNombre(),jugadorTurno.getFortuna());
                                    }
                                    break;
                                case "piscina":
                                    jugadorTurno.setFortuna(25000, "resta");
                                    if(!jugadorTurno.getEstarPobre()) {
                                        Juego.c.escribir(String.format("Pagas 20000 euros por tu piscina en %s\n", edificio.getCasilla().getNombre()));
                                        tablero.getColeccionCasillas().get(20).setValor(25000, "suma");
                                        jugadorTurno.setPagoTasasEImpuestos(25000);
                                    }else{
                                        throw new ExcepcionesDinero("El jugador no tiene dinero suficiente.",jugadorTurno.getNombre(),jugadorTurno.getFortuna());
                                    }
                                    break;
                                case "pista":
                                    jugadorTurno.setFortuna(25000, "resta");
                                    if(!jugadorTurno.getEstarPobre()) {
                                        Juego.c.escribir(String.format("Pagas 25000 euros por tu pista en %s\n", edificio.getCasilla().getNombre()));
                                        tablero.getColeccionCasillas().get(20).setValor(25000, "suma");
                                        jugadorTurno.setPagoTasasEImpuestos(25000);
                                    }else{
                                        throw new ExcepcionesDinero("El jugador no tiene dinero suficiente.",jugadorTurno.getNombre(),jugadorTurno.getFortuna());
                                    }
                                    break;
                            }
                        } else {
                            break;
                        }
                    }
                }else{
                    throw new ExcepcionesEdificio("El jugador "+ jugadorTurno.getNombre() + " no tiene edificios.");
                }
                break;
        }

    }


}
