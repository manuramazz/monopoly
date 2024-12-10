package Edificios;
import Casillas.*;
import Monopoly.*;
public final class Hotel extends Edificio{
    private static int idHotel=0;
    public Hotel(Solar s,Jugador j){
        super("hotel",s,j);
        super.setCoste((int) (0.6 * s.getValor()));
        super.setId("Hotel" + "-" + String.valueOf(idHotel));;
        idHotel++;
    }
}
