package Monopoly;

public class ExcepcionesDinero extends ExcepcionesMonopoly {

    private String nom_jugador;
    private int fortuna;

    public ExcepcionesDinero(){}

    public ExcepcionesDinero(String mensaje_error, String nom_jugador, int fortuna){
        super(mensaje_error);
        this.fortuna = fortuna;
        this.nom_jugador = nom_jugador;
    }

    public String getNom_jugador(){
        return nom_jugador;
    }
    public int getFortunaExcepcion(){
        return fortuna;
    }
}
