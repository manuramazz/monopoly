package Casillas;

public final class Impuesto extends Casilla{

    public Impuesto(int numero, String nombre){
        super(numero,nombre,nombre);

    }

    @Override
    public String toString() {
        return String.format("""
                        {
                            tipo: impuesto
                            Cantidad a pagar: %d
                            jugadores: %s
                        }
                        """, getValor(), stringJugadoresEnCasilla());
    }

}
