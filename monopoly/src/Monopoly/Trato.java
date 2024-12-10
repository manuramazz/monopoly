package Monopoly;
import Casillas.*;
import Edificios.*;
public class Trato {
    private Juego juego;
    private int tipo,rondas;
    private Jugador proponeJ, recibeJ;
    private static int numeroId = 1;
    private String id;
    private Propiedad daProp,recibeProp,noalquiler;
    private int daDinero,recibeDinero;
     public Trato(Jugador proponeJ, Jugador recibeJ, int tipo, Propiedad daProp, Propiedad recibeProp, int daDinero, int recibeDinero,Juego juego){
         this.proponeJ = proponeJ;
         this.recibeJ = recibeJ;
         this.juego=juego;
         this.daProp=daProp;
         this.recibeProp=recibeProp;
         this.daDinero=daDinero;
         this.recibeDinero=recibeDinero;
         this.tipo=tipo;
         id = "trato" + String.valueOf(numeroId);
         numeroId++;
     }
    public Trato(Jugador proponeJ, Jugador recibeJ, int tipo, Propiedad daProp, Propiedad recibeProp, Propiedad noalquiler,int rondas, Juego juego){
        this.proponeJ = proponeJ;
        this.recibeJ = recibeJ;
        this.daProp=daProp;
        this.recibeProp=recibeProp;
        this.tipo=tipo;
        this.noalquiler=noalquiler;
        this.rondas=rondas;
        this.juego=juego;
        id = "trato" + String.valueOf(numeroId);
        numeroId++;
    }

    public String getId() {
        return id;
    }

    public int getDaDinero() {
        return daDinero;
    }

    public static int getNumeroId() {
        return numeroId;
    }

    public int getRecibeDinero() {
        return recibeDinero;
    }

    public int getTipo() {
        return tipo;
    }

    public Jugador getProponeJ() {
        return proponeJ;
    }

    public Jugador getRecibeJ() {
        return recibeJ;
    }

    public Propiedad getDaProp() {
        return daProp;
    }

    public Propiedad getRecibeProp() {
        return recibeProp;
    }
    public String toString(){// throws ExcepcionesAccion{
        return switch (tipo) {
            case 1 -> String.format("""
                    {
                        %s
                        proponeTrato: %s
                        trato: cambiar(%s , %s)
                    }
                    """.formatted(id, proponeJ.getNombre(), daProp.getNombre(), recibeProp.getNombre()));
            case 2 -> String.format("""
                    {
                        %s
                        proponeTrato: %s
                        trato: cambiar(%s , %d)
                    }
                    """.formatted(id, proponeJ.getNombre(), daProp.getNombre(), recibeDinero));
            case 3 -> String.format("""
                    {
                        %s
                        proponeTrato: %s
                        trato: cambiar(%d , %s)
                    }
                    """.formatted(id, proponeJ.getNombre(), daDinero, recibeProp.getNombre()));
            case 4 -> String.format("""
                    {
                        %s
                        proponeTrato: %s
                        trato: cambiar(%s + %d, %s)
                    }
                    """.formatted(id, proponeJ.getNombre(), daProp.getNombre(), daDinero, recibeProp.getNombre()));
            case 5 -> String.format("""
                    {
                        %s
                        proponeTrato: %s
                        trato: cambiar(%s , %s + %d)
                    }
                    """.formatted(id, proponeJ.getNombre(), daProp.getNombre(), recibeProp.getNombre(), recibeDinero));
            case 6 -> String.format("""
                    {
                        %s
                        proponeTrato: %s
                        trato: cambiar(%s , %s + %d rondas libre en %s)
                    }
                    """.formatted(id, proponeJ.getNombre(), daProp.getNombre(), recibeProp.getNombre(), rondas,noalquiler.getNombre()));
            default -> null; //throw new ExcepcionesAccion("Tipo de trato incorrecto.");
        };
    }
    //cambiar el dueño de las casillas
    //añadir a la lista de propiedades, eliminar de la lista de propiedades, añadir edificios del jugador, eliminar edificios del otro jugador
    //si esta hipotecado añadir y eliminar de la lista de eliminados
    //comprobar que el dinero es mayor de lo que das
    public boolean aceptarTipo1(){
         cambiosCasillaPropone();
         cambiosCasillaRecibe();
        Juego.c.escribir(proponeJ.getNombre()+" le cambió a "+recibeJ.getNombre()+" "+daProp.getNombre()+" por "+recibeProp.getNombre());
        return true;

    }
    public boolean aceptarTipo2(){
        if(cambiosDineroRecibe()){
            cambiosCasillaPropone();
            Juego.c.escribir(proponeJ.getNombre()+" le cambió a "+recibeJ.getNombre()+" "+daProp.getNombre()+" por "+recibeDinero);
            return true;

        }
        return false;
    }
    public boolean aceptarTipo3(){
        if(cambiosDineroPropone()){
            cambiosCasillaRecibe();
            Juego.c.escribir(proponeJ.getNombre()+" le cambió a "+recibeJ.getNombre()+" "+recibeDinero+" por "+recibeProp.getNombre());
            return true;
        }
        return false;

    }
    public boolean aceptarTipo4(){
         if(cambiosDineroPropone()) {
             cambiosCasillaPropone();
             cambiosCasillaRecibe();
             Juego.c.escribir(proponeJ.getNombre()+" le cambió a "+recibeJ.getNombre()+" "+daProp.getNombre()+" y "+recibeDinero+" por "+recibeProp.getNombre());
             return true;

         }
        return false;

    }
    public boolean aceptarTipo5(){
         if(cambiosDineroRecibe()){
             cambiosCasillaPropone();
             cambiosCasillaRecibe();
             Juego.c.escribir(proponeJ.getNombre()+" le cambió a "+recibeJ.getNombre()+" "+daProp.getNombre()+" por "+recibeProp.getNombre()+" y "+recibeDinero);
             return true;
         }
        return false;
    }
    public boolean aceptarTipo6(){
        cambiosCasillaPropone();
        cambiosCasillaRecibe();
        noalquiler.setTurnossinpagar(juego.indiceJugador(proponeJ),rondas);
        Juego.c.escribir(proponeJ.getNombre()+" le cambió a "+recibeJ.getNombre()+" "+daProp.getNombre()+" por "+recibeProp.getNombre()+" y no le va a pagar alquiler en "+noalquiler.getNombre()+" "+rondas+" veces");
        return true;

    }

    private void cambiosCasillaPropone(){
        //CASILLA QUE DA EL QUE PROPONE
        daProp.setDuenho(recibeJ);
        recibeJ.getPropiedades().add(daProp);
        proponeJ.getPropiedades().remove(daProp);
        if(daProp.getHipotecado()){
            proponeJ.getHipotecas().remove(daProp);
            recibeJ.getHipotecas().add(daProp);
        }
        if(daProp instanceof Solar s){
            for(Edificio e: s.getEdificiosEnCasilla()){
                proponeJ.getEdificios().remove(e);
                recibeJ.getEdificios().add(e);
            }
        }
    }
    private void cambiosCasillaRecibe(){
        //CASILLA QUE RECIBE EL QUE PROPONE
        recibeProp.setDuenho(proponeJ);
        recibeJ.getPropiedades().remove(recibeProp);
        proponeJ.getPropiedades().add(recibeProp);
        if(recibeProp.getHipotecado()){
            recibeJ.getHipotecas().remove(recibeProp);
            proponeJ.getHipotecas().add(recibeProp);
        }
        if(recibeProp instanceof Solar s){
            for(Edificio e: s.getEdificiosEnCasilla()){
                recibeJ.getEdificios().remove(e);
                proponeJ.getEdificios().add(e);
            }
        }
    }
    private boolean cambiosDineroPropone(){
         if(proponeJ.getFortuna()>=daDinero){
             proponeJ.setFortuna(daDinero,"resta");
             recibeJ.setFortuna(daDinero,"suma");
             return true;
         }
         Juego.c.escribir(proponeJ.getNombre()+"no tiene dinero suficiente para el trato, no se puede aceptar");
         return false;
    }
    private boolean cambiosDineroRecibe(){
        if(recibeJ.getFortuna()>=recibeDinero){
            proponeJ.setFortuna(recibeDinero,"suma");
            recibeJ.setFortuna(recibeDinero,"resta");
            return true;
        }else {
            Juego.c.escribir(recibeJ.getNombre() + "no tiene dinero suficiente para el trato, no se puede aceptar");
            return false;
        }
    }
}
