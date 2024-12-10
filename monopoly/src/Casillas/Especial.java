package Casillas;

public final class Especial extends Casilla{
private int valor;
    public Especial(int numero, String nombre){
        super(numero,nombre,nombre);

    }

    @Override
    public String toString() {
        if(getNombre().equals("Carcel")){
            return String.format("""
                        {
                            salir: %d
                            jugadores: %s
                        }
                        """.formatted(getValor(), stringJugadoresEnCasilla(0)));
        }
        if(getNombre().equals("Salida")){
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
