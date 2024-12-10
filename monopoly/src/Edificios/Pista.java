package Edificios;
import Casillas.*;
import Monopoly.*;
public final class Pista extends Edificio{
    private static int idPista=0;
    public Pista(Solar s,Jugador j){
        super("pista",s,j);
        super.setCoste((int) (1.25 * s.getValor()));
        super.setId("Pista" + "-" + String.valueOf(idPista));;
        idPista++;
    }
}
