package Casillas;
import Monopoly.*;
public final class Servicio extends Propiedad{
    public Servicio(int numero, String nombre, String tipo,Jugador banca){
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
                             factor de servicio: %s
                             jugadores: %s
                         }
                         """.formatted(getNombre(), getTipo(), getValor(), getDuenho().getNombre(), getAlquiler(), stringJugadoresEnCasilla()));

    }
    public int alquiler(Tablero t, int modo, Casilla destino, Jugador jugador, Jugador jugadorTurno) {
        int ServProp = 0;
        int alquilerServ = 0;
        int multiplicador;
        //RECORRE LAS CASILLA DE SERVICIO PARA VER SI TIENE LAS DOS O UNA
        for (Casilla casilla : t.getColeccionCasillas()) {
            if (casilla instanceof Servicio) {
                Servicio servicio = (Servicio) casilla;
                if(servicio.getDuenho().getNombre().equals(jugador.getNombre())){
                    ServProp++;
                }
            }
        }
        //ALQUILER SERVICIO: VALOR DE LA CASILLA DE SALIDA /200 * TIRADA DE DADOS * 4 ó 10 DEPENDIENDO DE SI TIENES LAS DOS O UNA
        //DEPENDIENDO DE SI LA VARIABLE ES 1 O 2 SE MULTIPLICA POR 4 O 10
        //esto para hacerlo bien habría que actualizarlo en actualizarValores
        if (ServProp == 2) {
            multiplicador = 10;
        } else {
            multiplicador = 4;
        }
        alquilerServ = t.getColeccionCasillas().get(0).getValor() / 200 * multiplicador * modo;

        return alquilerServ;
    }

}
