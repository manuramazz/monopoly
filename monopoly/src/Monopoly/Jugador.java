package Monopoly;
import Casillas.*;
import Avatares.*;
import Edificios.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Jugador {
    public static final String banca = "banca";
    private Avatar avatar;
    private final String nombre;
    private int fortuna;
    private boolean haberTirado, estarPobre;
    private final ArrayList<Propiedad> propiedades;
    private final ArrayList<Propiedad> hipotecas;
    private ArrayList <Edificio> edificios;
    private Tablero tablero;
    private int dineroInvertido; //atributos para las estaditicas
    private int pagoTasasEImpuestos;
    private int pagoDeAlquileres;
    private int cobroDeAlquileres;
    private int pasarPorCasillaDeSalida;
    private int premiosInversionesOBote;
    private int vecesEnLaCarcel;
    private int vueltas;
    private int numeroTiradas;
    private int fortunaTotal;
    private ArrayList<Trato> tratosPorAceptar;


    //Constructor de los jugadores
    public Jugador(String nombre, String ficha,Tablero tablero){
        tratosPorAceptar = new ArrayList<>();
        this.nombre=nombre;
        haberTirado = false;
        estarPobre = false;
        this.fortuna= tablero.getValorTotalSolares()/3;
        this.hipotecas= new ArrayList<>();
        this.propiedades= new ArrayList<>();
        this.edificios= new ArrayList<>();
        this.dineroInvertido = 0;
        this.pagoTasasEImpuestos = 0;
        this.pagoDeAlquileres = 0;
        this.cobroDeAlquileres = 0;
        this.pasarPorCasillaDeSalida = 0;
        this.vecesEnLaCarcel = 0;
        switch(ficha){
            case "coche":
                this.avatar=new Coche(this, ficha,tablero);
                break;
            case "pelota":
                this.avatar=new Pelota(this, ficha,tablero);
                break;
        }

        this.tablero = tablero;
        this.vueltas = 0;
        this.numeroTiradas = 0;
        this.fortunaTotal = fortuna;

    }
    public Jugador(int e){
        this.nombre=banca;
        this.fortuna=10000000;
        this.hipotecas=new ArrayList<Propiedad>();
        this.propiedades=new ArrayList<Propiedad>();
    }

    public ArrayList<Trato> getTratosPorAceptar() {
        return tratosPorAceptar;
    }
    public boolean getHaberTirado(){
        return  haberTirado;
    }
    public void setHaberTirado(boolean accion){
        haberTirado = accion;
    }
    public boolean getEstarPobre() {return estarPobre;}
    public void setEstarPobre(boolean accion){
        estarPobre = accion;
    }
    public int getFortuna() {
        return fortuna;
    }
    public ArrayList<Propiedad> getHipotecas() {
        return hipotecas;
    }
    public ArrayList<Propiedad> getPropiedades() {
        return propiedades;
    }

    public ArrayList<Edificio> getEdificios(){return edificios;}
    public void setEdificios(Edificio edificio){ //acordarnos de llamarlo cuando se edifica
        edificios.add(edificio);
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setDineroInvertido(int valor){
        this.dineroInvertido += valor;
    }
    public int getDineroInvertido(){
        return dineroInvertido;
    }

    public void setPagoTasasEImpuestos(int valor){
        this.pagoTasasEImpuestos += valor;
    }
    public int getPagoTasasEImpuestos(){return pagoTasasEImpuestos;}

    public void setPagoDeAlquileres(int valor){
        this.pagoDeAlquileres += valor;
    }
    public int getPagoDeAlquileres(){return pagoDeAlquileres;}

    public void setCobroDeAlquileres(int valor){
        this.cobroDeAlquileres += valor;
    }
    public int getCobroDeAlquileres(){return cobroDeAlquileres;}

    public void setPasarPorCasillaDeSalida(int valor){
        this.pasarPorCasillaDeSalida += valor;
    }
    public int getPasarPorCasillaDeSalida(){return pasarPorCasillaDeSalida;}

    public void setPremiosInversionesOBote(int valor){
        this.premiosInversionesOBote += valor;
    }
    public int getPremiosInversionesOBote(){return premiosInversionesOBote;}

    public void setVecesEnLaCarcel(){
        vecesEnLaCarcel += 1;
    }
    public int getVecesEnLaCarcel(){return vecesEnLaCarcel;}

    public void setVueltas(){
        this.vueltas++;
    }
    public int getVueltas(){return vueltas;}

    public void setNumeroTiradas(){
        this.numeroTiradas++;
    }
    public int getNumeroTiradas(){return numeroTiradas;}

    public void setEdificios(ArrayList<Edificio> edificios) {
        this.edificios = edificios;
    }

    public void setFortunaTotal(int valor){
        this.fortunaTotal += valor;
    }
    public int getFortunaTotal(){return fortunaTotal;}

    public void setFortuna(int fortuna) {
        this.fortuna = fortuna;
    }

    public void setFortuna(int fortuna,String opcion) throws IllegalArgumentException{
        if(opcion.equals("suma")){
            this.fortuna += fortuna;
        }else if (opcion.equals("resta")){
            this.fortuna -= fortuna;
            if(fortuna>=0){
                setEstarPobre(false);
            }
            //eliminar jugador de la partida
            if(this.fortuna < 0){
                this.fortuna += fortuna;
                setEstarPobre(true);
                Juego.c.escribir("El jugador " + nombre + " no tiene dinero para pagar, debe declararse en bancarrota, hipotecar propiedades o vender edificios.");
            }

        }else{
            throw new IllegalArgumentException("La opcion es incorrecta.");
        }
    }
    public void anhadirPropiedad(Propiedad casilla){
        (this.propiedades).add(casilla);
    }
    public void salirCarcel() throws ExcepcionesDinero, ExcepcionesAccion{
        if (avatar.getEstado()>=1) {
            throw new ExcepcionesAccion("No estás en la cárcel");
        }

        else{
            //definimos los dados (clase dado) y preguntamos si quiere salir (tiene q pagar el pago) o si tira los dados
            Dado dados=new Dado();
            int pago = tablero.getColeccionCasillas().get(0).getValor()/4;
            Juego.c.escribir("¿Quieres salir de la cárcel por "+pago+" euros?");
            String respuesta = Juego.c.leer();
            if(respuesta.equals("si")){
                if(fortuna>=pago){
                    fortuna-=pago;
                    avatar.setEstado(1);
                    avatar.setTurnosEnCarcel(0);
                    dados.setCarcel(false);
                    Juego.c.escribir(String.format("%s sale de la cárcel. Puede tirar los dados\n",nombre));
                    return;
                }
                else{
                    throw new ExcepcionesDinero("No tienes suficiente dinero para salir de la carcel", nombre, fortuna);
                }
            }

            //si e es 1 es que saco dobles, si es 0 es que no

            Juego.c.escribir("Saca dobles para salir.");

            if(dados.lanzarDadosCarcel()==1){
                Juego.c.escribir(String.format("Valor del dado 1: %d\nValor del dado 2: %d\n",dados.getDado1(),dados.getDado2()));
                avatar.setEstado(1);
                avatar.setTurnosEnCarcel(0);
                dados.setCarcel(false);
                Juego.c.escribir(String.format("%s saca dobles y sale de la cárcel. Puede tirar los dados\n",nombre));
            }
            else{
                Juego.c.escribir(String.format("Valor del dado 1: %d\nValor del dado 2: %d\n",dados.getDado1(),dados.getDado2()));
                avatar.setTurnosEnCarcel(avatar.getTurnosEnCarcel()+1);
                if(avatar.getTurnosEnCarcel()==3){
                    Juego.c.escribir("Llevas 3 turnos en la cárcel, tienes que pagar y salir");
                    if(fortuna>=pago){
                        fortuna-=pago;
                        avatar.setEstado(1);
                        avatar.setTurnosEnCarcel(0);
                        dados.setCarcel(false);
                        Juego.c.escribir(nombre +" paga " + pago + " euros y sale de la cárcel. Puede tirar los dados");
                    }
                    else{
                        throw new ExcepcionesDinero("El jugador " + nombre + " no tiene dinero para pagar.", nombre, fortuna);
                    }
                }
                else{
                    Juego.c.escribir("Te quedas en la cárcel");
                }
            }

        }
    }
    public String arrayPropiedadesImprimir(){
        String s = "[";
        for(Propiedad propiedad : propiedades){
            s += propiedad.getNombre() + " - ";
        }
        s += "]";
        return s;
    }
    public String arrayHipotecasImprimir(){
        String s = "[";
        for(Propiedad hipoteca : hipotecas){
            s += hipoteca.getNombre() + " - ";
        }
        s += "]";
        return s;
    }

    public  String arrayEdificios(){
        String string = "[";
        for(Edificio edificio:edificios){
            string += edificio.getCasilla().getNombre() + ":" +edificio.getId() + " ";
        }
        string += "]";
        return string;
    }
}
