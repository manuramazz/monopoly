package Avatares;
import Casillas.*;
import Monopoly.*;
import java.util.*;

public abstract class Avatar {
    private String ficha;
    private String id;
    private int posicion;
    private Jugador jugador;
    private int estado, bloqueado;
    private int ronda, turnosEnCarcel;
    private static ArrayList<String> ids = new ArrayList<String>();
    private Tablero tablero;
    private boolean modo;
    private ArrayList<Integer> contadorCasillas;

    public Avatar(Jugador jugador, String ficha, Tablero tablero) {
        bloqueado = 0;
        posicion = 0;
        estado = 1;
        turnosEnCarcel = 0;
        modo = false;
        Random random = new Random();
        int i = random.nextInt(26) + 65;
        char c = (char) i;
        String cc = Character.toString(c);
        id = comp_ids(cc, c);
        ids.add(id);
        this.jugador = jugador;
        ronda = 0;
        this.ficha = ficha;
        this.tablero = tablero;
        tablero.getColeccionCasillas().get(posicion).anadirAvatar(this);
        contadorCasillas = new ArrayList<Integer>();
        for (int e = 0; e < 40; e++) {
            contadorCasillas.add(0);
        }
    }

    private String comp_ids(String cc, char c) {
        for (String d : ids) {
            if (d.equals(cc)) {
                cc = Character.toString((char) c + 1);
                if (cc.equals("91")) {
                    cc = "65";
                }
                comp_ids(cc, c);
            }
        }
        return cc;
    }

    public int getPosicion() {
        return posicion;
    }

    public ArrayList<Integer> getContadorCasillas() {
        return contadorCasillas;
    }

    public int getTurnosEnCarcel() {
        return turnosEnCarcel;
    }

    public int getEstado() {
        return estado;
    }

    public int getBloqueado() {
        return bloqueado;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setBloqueado(int bloqueado) {
        this.bloqueado += bloqueado;
    }

    public int getRonda() {
        return ronda;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public String getId() {
        return id;
    }

    public String getFicha() {
        return ficha;
    }

    public void setModo() {
        if (modo == true) {
            modo = false;
        } else {
            modo = true;
        }
    }

    public boolean isModo() {
        return modo;
    }

    public void setRonda(int ronda) {
        this.ronda = ronda;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setTurnosEnCarcel(int turnosEnCarcel) {
        this.turnosEnCarcel = turnosEnCarcel;
    }

    public void setPosicion(int posiciones) {
        getCasilla().eliminarAvatar(this);
        this.posicion += posiciones;
        if (this.posicion >= 40) {
            ronda++;
            this.posicion -= 40;
            jugador.setFortuna(tablero.getValorTotalSolares() / 22, "suma");
            jugador.setPasarPorCasillaDeSalida(tablero.getValorTotalSolares() / 22);
            Juego.c.escribir("El jugador " + jugador.getNombre() + " ha cobrado " + tablero.getValorTotalSolares() / 22 + " euros al pasar por la casilla de Salida.");
            jugador.setVueltas();
        }

        getCasilla().anadirAvatar(this);
    }

    public void setPosicion(int posiciones, String marchaAtras) {
        getCasilla().eliminarAvatar(this);
        this.posicion -= posiciones;
        if (this.posicion < 0) {
            ronda--;
            this.posicion += 40;
            jugador.setFortuna(tablero.getColeccionCasillas().get(0).getValor(), "resta");
            Juego.c.escribir("El jugador " + jugador.getNombre() + " pierde " + tablero.getValorTotalSolares() / 22 + " euros al pasar por la casilla de Salida marcha atrás.");
        }
        getCasilla().anadirAvatar(this);
    }

    public void setPosicion(int posicion, int carcel) {
        getCasilla().eliminarAvatar(this);
        this.posicion = posicion;
        getCasilla().anadirAvatar(this);
        estado = estado - 1;
    }

    public Casilla getCasilla() {
        return tablero.getColeccionCasillas().get(posicion);
    }

    public String toStringAvatar() {
        return String.format("""
                {
                    id: %s
                    tipo: %s
                    casilla: %s
                    jugador: %s
                }
                """.formatted(id, ficha, getCasilla().getNombre(), jugador.getNombre()));

    }

    public abstract void moverEnAvanzado(Dado dadoTurno, Juego juego) throws ExcepcionesAccion;

    public void moverEnBasico(Dado dadoTurno, int posiciones, Juego juego) {
        Casilla origen = getCasilla();
        Casilla destino = null;
    //VAS A LA CARCEL POR 3 DOBLES
        if (dadoTurno.isCarcel()) {
            //hay un constructor diferente para ir a la carcel (para evitar q complete vueltas al tablero) se le manda a la posicion 10 simplemente
            setPosicion(10, 0);
            jugador.setVecesEnLaCarcel();
            getCasilla().setFrecuencia();
            Juego.c.escribir(String.format("Estas en la %s por sacar tres dobles consecutivos\n", getCasilla().getNombre()));
            jugador.setHaberTirado(true);
            try {
                juego.comandoAcabarTurno();
            }catch (ExcepcionesAccion e){
                Juego.c.escribir(e.getMessage());
            }
            return;
        }
        //TE MUEVES
        else {
            //en set posicion normal se suma posiciones a la posicion actual y se comprueba si completa una vuelta
            setPosicion(posiciones);
            destino = getCasilla();
            getCasilla().setFrecuencia();
            Juego.c.escribir(String.format("El avatar %s avanza %d posiciones, desde %s a %s\n", getId(), posiciones, origen.getNombre(), destino.getNombre()));

        }
        getContadorCasillas().set(destino.getNumero(),getContadorCasillas().get(destino.getNumero())+1);

    }
}

//String p --> p.chars().allMatch.(Character::isDigit)