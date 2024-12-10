package Casillas;

public class Accion extends Casilla{
    private int bote=0;
    public Accion(int numero, String nombre){
        super(numero,nombre,nombre);

    }

    @Override
    public String toString() {
        if(getNombre().equals("Parking")){
            return String.format("""
                        {
                            bote: %d
                            jugadores: %s
                        }
                        """.formatted(getValor(), stringJugadoresEnCasilla()));
        }
    return null;
    }
}
