package Casillas;
import Monopoly.*;
public final class Transporte extends Propiedad{
    public Transporte(int numero, String nombre, String tipo, Jugador banca){
        super(numero,nombre,tipo,banca);

    }

    @Override
    public String toString() {
        return String.format("""
                        {
                            nombre: %s
                            tipo: %s
                            valor: %d
                            propietario: %s
                            operacion de transporte: %d
                            jugadores: %s
                        }
                        """.formatted(getNombre(), getTipo(), getValor(), getDuenho().getNombre(), getAlquiler(), stringJugadoresEnCasilla()));

    }

    @Override
    public int alquiler(Tablero t, int modo, Casilla destino, Jugador jugador,Jugador jugadorTurno) {
        int TransProp = 0;
        int alquilerTrans = 0;
        //RECORRE LAS CASILLA DE TRANSPORTE PARA VER CUANTAS TIENE
        for (Casilla casilla : t.getColeccionCasillas()) {
            if (casilla.getTipo().equals("Transporte")) {
                Propiedad propiedad = (Propiedad) casilla;
                if (propiedad.getDuenho().getNombre().equals(jugador.getNombre())) {
                    TransProp++;
                }

            }
        }
        alquilerTrans = (int) (TransProp * t.getColeccionCasillas().get(0).getValor() * 0.25);
        setAlquiler(alquilerTrans);
        return alquilerTrans;
    }

}
