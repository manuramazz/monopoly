package Monopoly;

public class ExcepcionJugadorIncorrecto extends ExcepcionesAccion{
    public ExcepcionJugadorIncorrecto(){}

    public ExcepcionJugadorIncorrecto(String mensaje_error){
        super(mensaje_error);
    }
}
