package Monopoly;

public class ExcepcionesMonopoly extends Exception {
    public ExcepcionesMonopoly(){}

    public ExcepcionesMonopoly(String mensaje_error){
        super(mensaje_error);
    }
}
