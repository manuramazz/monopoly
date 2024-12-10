package Monopoly;
import Casillas.*;
import java.util.ArrayList;

public class Tablero {
    private final ArrayList<Casilla> coleccionCasillas;
    private int valorTotalSolares;
    private final ArrayList<Grupo> grupos;
    public ArrayList<Casilla> getColeccionCasillas() {
        return coleccionCasillas;
    }

    public int getValorTotalSolares() {
        return valorTotalSolares;
    }

    public void setValorTotalSolares(int valorTotalSolares) {
        this.valorTotalSolares = valorTotalSolares;
    }
    public Tablero(Juego turno) {
        this.coleccionCasillas = new ArrayList<>();
        int vtotal=0,v=12000,i=0;
        grupos= new ArrayList<>();
        Grupo negro = new Grupo("Negro",2);
        grupos.add(negro);
        Grupo cyan = new Grupo("Cyan",3);
        grupos.add(cyan);
        Grupo morado = new Grupo("Morado",3);
        grupos.add(morado);
        Grupo amarillo = new Grupo("Amarillo",3);
        grupos.add(amarillo);
        Grupo rojo = new Grupo("Rojo",3);
        grupos.add(rojo);
        Grupo gris = new Grupo("Gris",3);
        grupos.add(gris);
        Grupo verde = new Grupo("Verde",3);
        grupos.add(verde);
        Grupo azul = new Grupo("Azul",2);
        grupos.add(azul);

        coleccionCasillas.add(new Especial(0, "Salida"));
        coleccionCasillas.add(new Solar(1, "Oviedo", "Negro", "Solar", turno.getBanca(),2));
        coleccionCasillas.add(new AccionCaja(2));
        coleccionCasillas.add(new Solar(3, "Gijon", "Negro", "Solar", turno.getBanca(),2));
        coleccionCasillas.add(new Impuesto(4, "Reintegro"));
        coleccionCasillas.add(new Transporte(5, "Puerto", "Transporte", turno.getBanca()));
        coleccionCasillas.add(new Solar(6, "Santiago", "Cyan", "Solar", turno.getBanca(),3));
        coleccionCasillas.add(new AccionSuerte(7));
        coleccionCasillas.add(new Solar(8, "Coruña", "Cyan", "Solar", turno.getBanca(),3));
        coleccionCasillas.add(new Solar(9, "Vigo", "Cyan", "Solar", turno.getBanca(),3));
        coleccionCasillas.add(new Especial(10, "Carcel"));
        coleccionCasillas.add(new Solar(11, "Bilbao","Morado","Solar", turno.getBanca(),3));
        coleccionCasillas.add(new Servicio(12, "Electricidad", "Servicio", turno.getBanca()));
        coleccionCasillas.add(new Solar(13, "Vitoria", "Morado", "Solar", turno.getBanca(),3));
        coleccionCasillas.add(new Solar(14, "Donostia", "Morado", "Solar", turno.getBanca(),3));
        coleccionCasillas.add(new Transporte(15, "Aeropuerto", "Transporte", turno.getBanca()));
        coleccionCasillas.add(new Solar(16, "Teruel", "Amarillo", "Solar", turno.getBanca(),3));
        coleccionCasillas.add(new AccionCaja(17));
        coleccionCasillas.add(new Solar(18, "Zaragoza", "Amarillo", "Solar", turno.getBanca(),3));
        coleccionCasillas.add(new Solar(19, "Huesca", "Amarillo", "Solar", turno.getBanca(),3));
        coleccionCasillas.add(new Accion(20, "Parking"));
        coleccionCasillas.add(new Solar(21, "Valencia", "Rojo", "Solar", turno.getBanca(),3));
        coleccionCasillas.add(new AccionSuerte(22));
        coleccionCasillas.add(new Solar(23, "Castellon", "Rojo", "Solar", turno.getBanca(),3));
        coleccionCasillas.add(new Solar(24, "Alicante", "Rojo", "Solar", turno.getBanca(),3));
        coleccionCasillas.add(new Transporte(25, "Ferrocarril","Transporte", turno.getBanca()));
        coleccionCasillas.add(new Solar(26, "Granada", "Gris", "Solar", turno.getBanca(),3));
        coleccionCasillas.add(new Solar(27, "Almeria", "Gris", "Solar", turno.getBanca(),3));
        coleccionCasillas.add(new Servicio(28, "Agua", "Servicio", turno.getBanca()));
        coleccionCasillas.add(new Solar(29, "Sevilla", "Gris", "Solar", turno.getBanca(),3));
        coleccionCasillas.add(new Especial(30, "IrCarcel"));
        coleccionCasillas.add(new Solar(31, "Girona", "Verde", "Solar", turno.getBanca(),3));
        coleccionCasillas.add(new Solar(32, "Barcelona", "Verde", "Solar", turno.getBanca(),3));
        coleccionCasillas.add(new AccionCaja(33));
        coleccionCasillas.add(new Solar(34, "Tarragona", "Verde", "Solar", turno.getBanca(),3));
        coleccionCasillas.add(new Transporte(35, "NaveEspacial", "Transporte", turno.getBanca()));
        coleccionCasillas.add(new AccionSuerte(36));
        coleccionCasillas.add(new Solar(37, "Madrid", "Azul", "Solar", turno.getBanca(),2));
        coleccionCasillas.add(new Impuesto(38, "DeLujo"));
        coleccionCasillas.add(new Solar(39, "Gibraltar", "Azul", "Solar", turno.getBanca(),2));
        //Primer recorrido
        for(Casilla casill : coleccionCasillas){

            if(i==1 || i==3){
                Propiedad casilla = (Propiedad) casill;
                casilla.setValor(v);
                casilla.setAlquiler(v/10);
                casilla.setHipoteca();
                vtotal+=v;
            }
            if(i==6){
                Propiedad casilla = (Propiedad) casill;
                v = (int) (v *1.3);
                casilla.setValor(v);
                casilla.setAlquiler(v/10);
                casilla.setHipoteca();
                vtotal+=v;
            }
            if(i==8 || i==9){
                Propiedad casilla = (Propiedad) casill;
                casilla.setValor(v);
                casilla.setAlquiler(v/10);
                casilla.setHipoteca();
                vtotal+=v;
            }
            if(i==11){
                Propiedad casilla = (Propiedad) casill;
                v = (int) (v *1.3);
                casilla.setValor(v);
                casilla.setAlquiler(v/10);
                casilla.setHipoteca();
                vtotal+=v;
            }
            if(i==13 || i==14){
                Propiedad casilla = (Propiedad) casill;
                casilla.setValor(v);
                casilla.setAlquiler(v/10);
                casilla.setHipoteca();
                vtotal+=v;
            }
            if(i==16){
                Propiedad casilla = (Propiedad) casill;
                v = (int) (v *1.3);
                casilla.setValor(v);
                casilla.setAlquiler(v/10);
                casilla.setHipoteca();
                vtotal+=v;
            }
            if(i==18 || i==19){
                Propiedad casilla = (Propiedad) casill;
                casilla.setValor(v);
                casilla.setAlquiler(v/10);
                casilla.setHipoteca();
                vtotal+=v;
            }
            if(i==21){
                Propiedad casilla = (Propiedad) casill;
                v = (int) (v *1.3);
                casilla.setValor(v);
                casilla.setAlquiler(v/10);
                casilla.setHipoteca();
                vtotal+=v;
            }
            if(i==23 || i==24){
                Propiedad casilla = (Propiedad) casill;
                casilla.setValor(v);
                casilla.setAlquiler(v/10);
                casilla.setHipoteca();
                vtotal+=v;
            }
            if(i==26){
                Propiedad casilla = (Propiedad) casill;
                v = (int) (v *1.3);
                casilla.setValor(v);
                casilla.setAlquiler(v/10);
                casilla.setHipoteca();
                vtotal+=v;
            }
            if(i==27 || i==29){
                Propiedad casilla = (Propiedad) casill;
                casilla.setValor(v);
                casilla.setAlquiler(v/10);
                casilla.setHipoteca();
                vtotal+=v;
            }
            if(i==31){
                Propiedad casilla = (Propiedad) casill;
                v = (int) (v *1.3);
                casilla.setValor(v);
                casilla.setAlquiler(v/10);
                casilla.setHipoteca();
                vtotal+=v;
            }
            if(i==32 || i==34){
                Propiedad casilla = (Propiedad) casill;
                casilla.setValor(v);
                casilla.setAlquiler(v/10);
                casilla.setHipoteca();
                vtotal+=v;
            }
            if(i==37){
                Propiedad casilla = (Propiedad) casill;
                v = (int) (v *1.3);
                casilla.setValor(v);
                casilla.setAlquiler(v/10);
                casilla.setHipoteca();
                vtotal+=v;
            }
            if(i==39){
                Propiedad casilla = (Propiedad) casill;
                casilla.setValor(v);
                casilla.setAlquiler(v/10);
                casilla.setHipoteca();
                vtotal+=v;
            }
            i++;
        }
        v = vtotal /22;
        valorTotalSolares =vtotal;
        i=0;
        //Segundo recorrido (PARA TRANS, SERV, IMP, ya sabiendo el valor total de los solares)
        for(Casilla casilla : coleccionCasillas){
            //casilla de salida
            if(i==0){
                casilla.setValor(v);
            }
            //casillas de transporte
            if(i==5||i==15||i==25||i==35){
                Propiedad p = (Propiedad) casilla;
                p.setValor(v);
                p.setAlquiler(v);
                p.setHipoteca();
            }
            //casillas de servicio
            if(i==12 || i==28){
                Propiedad p = (Propiedad) casilla;
                p.setValor((int)(0.75*v));
                p.setAlquiler(v/200);
                p.setHipoteca();
            }
            //casilla de reintegro
            if(i==4){
                casilla.setValor((int)(0.5*v));
            }
            //casilla de impuesto delujo
            if(i==38){
                casilla.setValor(v);
            }
            //casilla de carcel
            if(i==10){
                casilla.setValor((int)(0.25* v));
            }

            i++;
        }
        InicializarGrupos();
    }

    public void imprimirTablero(){
        ArrayList<String> arrayListCasillas = new ArrayList<>();
        for(Casilla casilla : coleccionCasillas){
            arrayListCasillas.add(casilla.tostringCasilla());
        }
        String reset = "\u001B[0m";
        String negro="\033[4;30m";
        String rojo="\033[4;31m";
        String verde="\033[4;32m";
        String amarillo="\033[4;33m";
        String azul="\033[4;34m";
        String morado="\033[4;35m" ;
        String cyan="\033[4;36m";
        String gris="\033[4;37m";
        String blanco="\033[4;39m";
        String spaceArriba = "                                                                                                                                                                                                                                                                                    ";
        String spaceArribaFormat = String.format(blanco+spaceArriba+reset);
        String space = "                                                                                                                                                                                                                                ";
        String lineaInf = String.format(blanco+"|%s|"+reset+cyan+"%s|%s"+reset+blanco+"|%s|"+reset+cyan+"%s"+reset+blanco+"|%s|%s|"+reset+negro+"%s"+reset+blanco+"|%s|"+reset+negro+"%s"+reset+blanco+"|%s|"+reset,arrayListCasillas.get(10),arrayListCasillas.get(9),arrayListCasillas.get(8),arrayListCasillas.get(7),arrayListCasillas.get(6),arrayListCasillas.get(5),arrayListCasillas.get(4),arrayListCasillas.get(3),arrayListCasillas.get(2),arrayListCasillas.get(1),arrayListCasillas.get(0));
        String lineaSup = String.format(blanco+"|%s|"+reset+rojo+"%s"+reset+blanco+"|%s|"+reset+rojo+"%s|%s"+reset+blanco+"|%s|"+reset+gris+"%s|%s"+reset+blanco+"|%s|"+reset+gris+"%s"+reset+blanco+"|%s|"+reset,arrayListCasillas.get(20),arrayListCasillas.get(21),arrayListCasillas.get(22),arrayListCasillas.get(23),arrayListCasillas.get(24),arrayListCasillas.get(25),arrayListCasillas.get(26),
                arrayListCasillas.get(27),arrayListCasillas.get(28),arrayListCasillas.get(29),arrayListCasillas.get(30));
        String lineasLat = String.format(amarillo+"|%s|"+reset+space+verde+"|%s|"+reset+"\n"+amarillo+"|%s|"+reset+space+verde+"|%s|"+reset+"\n"+blanco+"|%s|"+reset+space+blanco+"|%s|"+reset+"\n"+amarillo+"|%s|"+reset+space+verde+"|%s|"+reset+"\n"+blanco+"|%s|"+reset+space+blanco+"|%s|"+reset+"\n"+morado+"|%s|"+reset+space+blanco+"|%s|"+reset+"\n"+morado+"|%s|"+reset+space+azul+"|%s|"+reset+"\n"+blanco+"|%s|"+reset+space+blanco+"|%s|"+reset+"\n"+morado+"|%s|"+reset+blanco+space+reset+azul+"|%s|"+reset,
                arrayListCasillas.get(19),arrayListCasillas.get(31),arrayListCasillas.get(18),arrayListCasillas.get(32),arrayListCasillas.get(17),arrayListCasillas.get(33),arrayListCasillas.get(16), arrayListCasillas.get(34),arrayListCasillas.get(15), arrayListCasillas.get(35),arrayListCasillas.get(14), arrayListCasillas.get(36),arrayListCasillas.get(13), arrayListCasillas.get(37),arrayListCasillas.get(12),arrayListCasillas.get(38),arrayListCasillas.get(11),arrayListCasillas.get(39));
        //System.out.format("%s\n",lineaInf);
        String s = String.format("""
        %s
        %s
        %s                                                                                                                                                                                                         
        %s


        """,spaceArribaFormat,lineaSup,lineasLat,lineaInf);
        Juego.c.escribir(s);
    }

    public void InicializarGrupos(){
        for(Casilla casilla: coleccionCasillas){
            if(casilla instanceof Solar s) {
                for (Grupo grupo : grupos) {
                    if (s.getGrupo().equals(grupo.getColor())) {
                        grupo.getCasillasGrupo().add(s);
                    }
                }
            }
        }
    }

    public ArrayList<Grupo> getGrupos(){
        return grupos;
    }
}
