package Casillas;

import Monopoly.Jugador;
import Monopoly.Juego;
import Monopoly.Tablero;

import java.util.ArrayList;

public abstract class Propiedad extends Casilla{
    private boolean hipotecado;
    private int alquiler,hipoteca,valorCompra;
    private Jugador duenho;

    private final ArrayList<Integer> turnosSinPagar = new ArrayList<>();

    public Propiedad(int numero, String nombre, String tipo, Jugador banca){
        super(numero,nombre,tipo);
        hipotecado=false;
        duenho = banca;
    }
    public void setTurnossinpagar(){
        turnosSinPagar.add(0);
    }
    public void setTurnossinpagar(int e,int turnos){
        turnosSinPagar.set(e,turnosSinPagar.get(e)+turnos);
    }

    public int getTurnosSinPagar(int index) {
        return turnosSinPagar.get(index);
    }

    public boolean getHipotecado() {
        return hipotecado;
    }

    public void setHipotecado(boolean hipotecado) {
        this.hipotecado = hipotecado;
    }

    public Jugador getDuenho() {
        return duenho;
    }
    public void setValorCompra(int valor){ this.valorCompra = valor;}

    public int getValorCompra() {
        return valorCompra;
    }

    public int getAlquiler() {
        return alquiler;
    }

    public int getHipoteca() {
        return hipoteca;
    }

    public void setAlquiler(int alquiler) {
        this.alquiler = alquiler;
    }

    public void setHipoteca() {hipoteca = this.getValor()/2;
    }

    @Override
    public String toString() {
        return String.format("""
                        {
                            nombre: %s
                            tipo: %s
                            valor: %d
                        }
                        """.formatted(this.getNombre(), this.getTipo(), this.getValor()));
    }

    public abstract int alquiler(Tablero t, int modo, Casilla destino, Jugador jugador,Jugador jugadorTurno);
    public boolean perteneceAJugador(Jugador jugador){
        if(jugador.getNombre().equals(duenho.getNombre())){
            return true;
        }
        return false;
    }

    public void setDuenho(Jugador duenho) {
        this.duenho = duenho;
    }


    public void comprar(Jugador jugadorTurno, Jugador banca, ArrayList<Jugador> jugadoresPartida){
        if(getDuenho().getNombre().equals(jugadorTurno.getNombre())){
            Juego.c.escribir(String.format("%s ya es el dueño de la casilla %s, no puede comprarla\n",jugadorTurno.getNombre(),getNombre()));
        }
        //compras la casilla
        else if(getDuenho().getNombre().equals("banca")){
            if(jugadorTurno.getFortuna()>=getValor()){
                jugadorTurno.setFortuna(getValor(),"resta");
                jugadorTurno.getPropiedades().add(this);
                banca.getPropiedades().remove(this);
                setDuenho(jugadorTurno);
                setValorCompra(-getValor());
                Juego.c.escribir(String.format("%s compra la casilla %s por %d euros. Su fortuna actual es de %d euros\n",jugadorTurno.getNombre(),getNombre(),getValor(),jugadorTurno.getFortuna()));
                jugadorTurno.setDineroInvertido(getValor());
            }
        }
        else{
            for(Jugador jugadorDueno : jugadoresPartida) {
                if(jugadorDueno.getNombre().equals(getDuenho().getNombre())){
                    Juego.c.escribir(String.format("%s no puede comprar la casilla %s, %s ya es el dueño\n",jugadorTurno.getNombre(),getNombre(),jugadorDueno.getNombre()));
                }
            }
        }
    }
}
