package Casillas;
import Monopoly.*;

import java.util.ArrayList;

public class Grupo{
    String color;
    private final ArrayList<Solar> casillasGrupo = new ArrayList<Solar>();
    private int casasgrupo,hotelesgrupo,piscinasgrupo,pistasgrupo,maxcasasgrupo,maxhotelesgrupo,maxpiscinasgrupo,maxpistasgrupo;
    public Grupo(String nombre, int solaresGrupo){
        this.color=nombre;
        casasgrupo=0;
        hotelesgrupo=0;
        piscinasgrupo=0;
        pistasgrupo=0;
        maxcasasgrupo=solaresGrupo;
        maxhotelesgrupo=solaresGrupo;
        maxpiscinasgrupo=solaresGrupo;
        maxpistasgrupo=solaresGrupo;
    }
    public String getColor() {
        return color;
    }
    public ArrayList<Solar> getCasillasGrupo() {
        return casillasGrupo;
    }

    public int getMaxpiscinasgrupo() {
        return maxpiscinasgrupo;
    }

    public int getMaxhotelesgrupo() {
        return maxhotelesgrupo;
    }

    public int getMaxpistasgrupo() {
        return maxpistasgrupo;
    }

    public int getMaxcasasgrupo() {
        return maxcasasgrupo;
    }

    public int getPistasgrupo() {
        return pistasgrupo;
    }

    public int getPiscinasgrupo() {
        return piscinasgrupo;
    }

    public int getHotelesgrupo() {
        return hotelesgrupo;
    }

    public int getCasasgrupo() {
        return casasgrupo;
    }

    public void setPistasgrupo(int pistasgrupo) {
        this.pistasgrupo = pistasgrupo;
    }

    public void setCasasgrupo(int casasgrupo) {
        this.casasgrupo = casasgrupo;
    }

    public void setPiscinasgrupo(int piscinasgrupo) {
        this.piscinasgrupo = piscinasgrupo;
    }

    public void setHotelesgrupo(int hotelesgrupo) {
        this.hotelesgrupo = hotelesgrupo;
    }
}
