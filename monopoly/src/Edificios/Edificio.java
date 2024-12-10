package Edificios;
import Monopoly.*;
import Casillas.*;

public abstract class Edificio {
    private final String tipo;
    private String id;
    private int coste;
    private final Solar casilla;
    public Edificio(String tipo,Solar s,Jugador j){
        casilla=s;
        this.tipo = tipo;
    }



    public String getTipo() {
        return tipo;
    }

    public String getId() {
        return id;
    }

    public int getCoste() {
        return coste;
    }

    public Solar getCasilla() {
        return casilla;
    }


    public void setCoste(int coste) {
        this.coste = coste;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString(){
        return String.format ("""
                                {
                                    id: %s
                                    propietario: %s
                                    casilla: %s
                                    grupo: %s
                                    coste: %s
                                }
                                                    
                                """.formatted(this.getId(), casilla.getDuenho().getNombre(), casilla.getNombre(), this.getCasilla().getGrupo(), this.getCoste()));

    }

}
