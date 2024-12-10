package Edificios;
import Casillas.*;
import Monopoly.*;
public final class Casa extends Edificio{
    private static int idCasa=0;
    public Casa(Solar s,Jugador j){
        super("casa",s,j);
        super.setCoste((int) (0.6 * s.getValor()));
        super.setId("Casa" + "-" + String.valueOf(idCasa));
        idCasa++;
    }
}
