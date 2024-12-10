package Casillas;
import Avatares.Avatar;
import Monopoly.*;
import java.util.ArrayList;

public abstract class Casilla {
    private String nombre, tipo;
    private ArrayList<Avatar> avataresEnCasilla;
    private int frecuencia,numero,valor;


    public Casilla(int numero, String nombre, String tipo){
        this.numero = numero;
        this.nombre=nombre;
        this.tipo=tipo;
        this.avataresEnCasilla = new ArrayList<Avatar>();
    }

    public ArrayList<Avatar> getAvataresEnCasilla() {
        return avataresEnCasilla;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public int getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }
    public void anadirAvatar(Avatar avatar){
        this.avataresEnCasilla.add(avatar);
    }
    public void eliminarAvatar(Avatar avatar){
        this.avataresEnCasilla.remove(avatar);
    }
    public String tostringCasilla(){
        String cadenaAvs = "";
        for(Avatar elemento: this.avataresEnCasilla){
            cadenaAvs = cadenaAvs + "&" + elemento.getId();
        }
        return String.format("%-12s%12s",this.nombre,cadenaAvs);
    }
    public String stringJugadoresEnCasilla(){

        String cadenaJs = "[";
        for(Avatar elemento: this.avataresEnCasilla){
            cadenaJs = cadenaJs + elemento.getJugador().getNombre() + " ";
        }
        cadenaJs += "]";
        return cadenaJs;
    }
    public String stringJugadoresEnCasilla(int i){//PARA DESCRIBIR LA CARCEL (es diferente porq se ponen los jugadores y los turno que llevan)

        String cadenaJs = "";
        for(Avatar elemento: this.avataresEnCasilla){
            cadenaJs = cadenaJs + "[" + elemento.getJugador().getNombre() + "," + elemento.getTurnosEnCarcel() + "] ";
        }

        return cadenaJs;
    }

    public boolean estaAvatar(Avatar m){
        for (Avatar av : avataresEnCasilla){
            if(av.getId().equals(m.getId())){
                return true;
            }
        }
        return false;
    }
    public void setValor(int valor,String suma) {
        this.valor += valor;
    }

    public void setFrecuencia() {
        this.frecuencia++;
    }

    public abstract String toString();
}
