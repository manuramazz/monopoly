package Edificios;
import Casillas.*;
import Monopoly.*;
public final class Piscina extends Edificio{
    private static int idPiscina=0;
    public Piscina(Solar s,Jugador j){
        super("piscina",s,j);
        super.setCoste((int) (0.4 * s.getValor()));
        super.setId("Piscina" + "-" + String.valueOf(idPiscina));;
        idPiscina++;
    }
}
