package Monopoly;
import Casillas.*;
import Edificios.*;
import java.util.*;

public class Juego implements Comandos{
    private Jugador jugadorTurno;
    public final Jugador banca;

    public static ConsolaNormal c=new ConsolaNormal();

    private final Tablero tablero;
    private final ArrayList<Jugador> jugadoresPartida;
    private int indiceJugador;
    private int rondaPartida;
    private Dado dadoTurno;
    private int comprobacionCarcelPelota;

    public Juego() {
        banca = new Jugador(5);
        jugadoresPartida = new ArrayList<>();
        rondaPartida = 0;
        tablero = new Tablero(this);
        indiceJugador = 0;
        dadoTurno = new Dado();
    }
    public void setComprobacionCarcelPelota(int comprobacionCarcelPelota) {
        this.comprobacionCarcelPelota = comprobacionCarcelPelota;
    }

    public Jugador getBanca() {
        return banca;
    }
    public boolean hacerTurno(String[] comando) throws IllegalArgumentException, ExcepcionesCasilla, ExcepcionesDinero, ExcepcionesAccion, ExcepcionesEdificio, ExcepcionesElementosJuego {
        boolean partida = true;
            switch (comando[0]) {
                case "bancarrota":
                    if (comandoBancarrota()) {
                        partida = false;
                    }
                    break;
                case "hipotecar":
                    if (comando.length == 2) {
                        comandoHipotecar(comando[1]);
                    } else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case "deshipotecar":
                    if (comando.length == 2) {
                        comandoDesHipotecar(comando[1]);
                    } else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case "eliminar":
                    if (comando.length == 2) {
                        comandoEliminarTrato(comando[1]);
                    }
                    break;
                case "aceptar":
                    if (comando.length == 2) {
                        comandoAceptarTrato(comando[1]);
                    }
                    break;
                case "vender":
                    if (comando.length == 4) {
                        if (comando[1].equals("casa") || comando[1].equals("hotel") || comando[1].equals("piscina") || comando[1].equals("pista")) {
                            comandoVenderEdificio(comando[1], comando[2], comando[3]); //utilizo el comando[1] para saber si es casa, edificio o piscina dentro de la funcion
                        } else {
                            throw new IllegalArgumentException();
                        }
                    } else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case "edificar":
                    if (comando.length == 1) {
                        throw new IllegalArgumentException();
                    }
                    if (comando[1].equals("casa") || comando[1].equals("hotel") || comando[1].equals("piscina") || comando[1].equals("pista")) {
                        comandoEdificar(comando[1]); //utilizo el comando[1] para saber si es casa, edificio o piscina dentro de la funcion
                    } else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case "jugador":
                    comandoJugador();
                    break;
                case "cambiar":
                    if (comando.length == 2 && comando[1].equals("modo")) {
                        comandoCambiarModo(); //utilizo la ficha del avatar de JugadorTurno
                    } else {
                        comandoTratos(comando);
                    }
                    break;
                case "estadisticas":
                    if (comando.length == 1) {
                        comandoEstadisticasJuego();
                    } else if (comando.length == 2) {
                        comandoEstadisticasJugador(comando[1]);
                    } else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case "listar":
                    if (comando.length == 2) {
                        switch (comando[1]) {
                            case "jugadores":
                                comandoListarJugadores();
                                break;
                            case "avatares":
                                comandoListarAvatares();
                                break;
                            case "enventa":
                                comandoListarEnVenta();
                                break;
                            case "edificios":
                                comandoListarEdificios();
                                break;
                            case "tratos":
                                comandoListarTratos();
                                break;
                            default:
                                throw new IllegalArgumentException();
                        }
                    } else if (comando.length == 3 && comando[1].equals("edificios")) {
                        comandoListarEdificiosGrupo(comando[2]);
                    } else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case "ver":
                    comandoVerTablero();
                    break;
                case "lanzar":
                    if (comando.length != 2) {
                        throw new IllegalArgumentException();
                    } else if (comando[1].equals("tablero")) {
                        Juego.c.escribir(String.format("%s SE ENFADÓ MUCHO y tiró el tablero. Se acaba la partida!!!! A %s le ha caído un hotel en la cabeza, se va al hospital\n", jugadorTurno.getNombre(), jugadoresPartida.get(0).getNombre()));
                        partida = false;
                    } else {
                        comandoLanzarDadosMoverse();
                    }
                    break;
                case "acabar":
                    if (comando.length == 2) {
                        if (comando[1].equals("turno")) {
                            comandoAcabarTurno();
                        } else if (comando[1].equals("partida")) {
                            c.escribir("Terminando partida...");
                            partida = false;
                        }
                    } else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case "comprar":
                    if (comando.length == 2) {
                        comandoComprar(comando[1]);
                    } else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case "salir":
                    comandoSalircarcel();
                    break;
                case "describir":
                    if (comando.length == 3) {
                            if (comando[1].equals("jugador")) {
                                comandoDescribirJugador(comando[2]);
                            } else if (comando[1].equals("avatar")) {
                                comandoDescribirAvatar(comando[2]);
                            } else {
                                throw new IllegalArgumentException();
                            }
                    } else if (comando.length == 2) {
                        comandoDescribirCasilla(comando[1]);
                    } else {
                        throw new IllegalArgumentException();
                    }
                    break;
                case "help":
                    comandoHelp();
                    break;
            }

        return partida;
    }
/*TIPOS DE TRATOS:
    1: TRATO JUGADOR PROPIEDAD POR PROPIEDAD
    2: TRATO JUGADOR PROPIEDAD POR DINERO
    3: TRATO JUGADOR DINERO POR PRIPIEDAD
    4: TRATO JUGADOR PROPIEDAD Y DINERO POR PROPIEDAD
    5: TRATO JUGADOR PROPIEDAD POR PROPIEDAD Y DINERO
    6: TRATO JUGADOR PROPIEDAD POR PROPIEDAD Y NOALQUILER PROPIEDAD NUMRONDAS

    */
    @Override
    public void comandoTratos(String[] comando) throws ExcepcionesAccion, ExcepcionesCasilla, IllegalArgumentException{
        if (comando.length <5 || comando.length > 9){
            throw new IllegalArgumentException("Error en la sintaxis del comando trato, revisa con el comando help como funciona.");
        }
        Jugador recibeTrato = null;
        Propiedad daProp=null, recibeProp=null, noalquiler=null;
        int tipo = 0;
        for(Jugador jugador : jugadoresPartida){
            if(jugador.getNombre().equals(comando[1])){
                 recibeTrato = jugador;
                if(jugador.equals(jugadorTurno)){
                    throw new ExcepcionJugadorIncorrecto("No puedes negociar contigo mismo espabilao.");
                }
            }

        }
    if(recibeTrato==null){
        throw new ExcepcionJugadorIncorrecto("Este jugador no existe.");
    }
        tipo=tipoTrato(comando);
        switch(tipo){
            case 1:
                for(Casilla casilla : tablero.getColeccionCasillas()){
                    if(casilla instanceof Propiedad propiedad){
                        if(comando[2].equals(casilla.getNombre())){
                            if(!propiedad.getDuenho().equals(jugadorTurno)){
                                throw new ExcepcionesCasilla("No eres dueño de esa casilla, no puedes hacer tratos con ella.");
                            }
                            daProp = propiedad;
                        }

                        if(comando[4].equals(casilla.getNombre())){
                            if(!propiedad.getDuenho().equals(recibeTrato)){
                                throw new ExcepcionesCasilla("El jugador con el que quieres negociar no es dueño de la casilla que indicas.");
                            }
                            recibeProp = propiedad;
                        }
                    }
                }
                if(daProp==null){
                    throw new ExcepcionCasillaNoExiste("La casilla: " + comando[2] + " no existe.");
                }
                if(recibeProp==null){
                    throw new ExcepcionCasillaNoExiste("La casilla: " + comando[4]+" no existe.");
                }
                recibeTrato.getTratosPorAceptar().add(new Trato(jugadorTurno,recibeTrato,1,daProp,recibeProp,0,0,this));
                c.escribir(recibeTrato.getNombre()+": Te doy "+daProp.getNombre()+" y tu me das "+recibeProp.getNombre()+"?");
                break;
            case 2:
                for(Casilla casilla : tablero.getColeccionCasillas()){
                    if(casilla instanceof Propiedad propiedad){
                        if(comando[2].equals(propiedad.getNombre())){
                            daProp = propiedad;
                            if(!propiedad.getDuenho().equals(jugadorTurno)){
                                throw new ExcepcionesCasilla("No eres dueño de esa casilla, no puedes hacer tratos con ella.");
                            }
                        }
                    }
                }
                if(daProp==null){
                    throw new ExcepcionCasillaNoExiste("La casilla: " + comando[2] + " no existe.");
                }
                recibeTrato.getTratosPorAceptar().add(new Trato(jugadorTurno,recibeTrato,2,daProp,null,0,Integer.parseInt(comando[4]),this));
                c.escribir(recibeTrato.getNombre()+": Te doy "+daProp.getNombre()+" y tu me das "+comando[4]+"?");
                break;

            case 3:
                for(Casilla casilla : tablero.getColeccionCasillas()){
                    if(casilla instanceof Propiedad propiedad){
                        if(comando[4].equals(propiedad.getNombre())){
                            recibeProp = propiedad;
                            if(!propiedad.getDuenho().equals(recibeTrato)){
                                throw new ExcepcionesCasilla("El jugador con el que quieres negociar no es dueño de la casilla que indicas.");
                            }
                        }

                    }
                }
                if(recibeProp==null){
                    throw new ExcepcionCasillaNoExiste("La casilla: " + comando[4]+" no existe.");
                }
                recibeTrato.getTratosPorAceptar().add(new Trato(jugadorTurno,recibeTrato,3,null,recibeProp,Integer.parseInt(comando[2]),0,this));
                c.escribir(recibeTrato.getNombre()+": Te doy "+comando[2]+" y tu me das "+comando[4]+"?");
                break;
            case 4:
                for(Casilla casilla : tablero.getColeccionCasillas()){
                    if(casilla instanceof Propiedad propiedad){
                        if(comando[2].equals(propiedad.getNombre())){
                            daProp = propiedad;
                            if(!propiedad.getDuenho().equals(jugadorTurno)){
                                throw new ExcepcionesCasilla("No eres dueño de esa casilla, no puedes hacer tratos con ella.");
                            }
                        }
                        if(comando[6].equals(propiedad.getNombre())){
                            if(!propiedad.getDuenho().equals(recibeTrato)){
                                throw new ExcepcionesCasilla("El jugador con el que quieres negociar no es dueño de la casilla que indicas.");
                            }
                            recibeProp = propiedad;
                        }
                    }
                }
                if(daProp==null){
                    throw new ExcepcionCasillaNoExiste("La casilla: " + comando[2] + " no existe.");
                }
                if(recibeProp==null){
                    throw new ExcepcionCasillaNoExiste("La casilla: " + comando[6]+" no existe.");
                }
                recibeTrato.getTratosPorAceptar().add(new Trato(jugadorTurno,recibeTrato,4,daProp,recibeProp,Integer.parseInt(comando[4]),0,this));
                c.escribir(recibeTrato.getNombre()+": Te doy "+comando[2]+" y "+comando[4]+" por "+comando[6]+"?");
                break;
            case 5:
                for(Casilla casilla : tablero.getColeccionCasillas()){
                    if(casilla instanceof Propiedad propiedad){
                        if(comando[2].equals(propiedad.getNombre())){
                            if(!propiedad.getDuenho().equals(jugadorTurno)){
                                throw new ExcepcionesCasilla("No eres dueño de esa casilla, no puedes hacer tratos con ella.");
                            }
                            daProp = propiedad;
                        }
                        if(comando[4].equals(propiedad.getNombre())){
                            if(!propiedad.getDuenho().equals(recibeTrato)){
                                throw new ExcepcionesCasilla("El jugador con el que quieres negociar no es dueño de la casilla que indicas.");
                            }
                            recibeProp = propiedad;
                        }
                    }
                }
                if(daProp==null){
                    throw new ExcepcionCasillaNoExiste("La casilla: " + comando[2] + " no existe.");
                }
                if(recibeProp==null){
                    throw new ExcepcionCasillaNoExiste("La casilla: " + comando[4]+" no existe.");
                }
                recibeTrato.getTratosPorAceptar().add(new Trato(jugadorTurno,recibeTrato,5,daProp,recibeProp,0,Integer.parseInt(comando[6]),this));
                c.escribir(recibeTrato.getNombre()+": Te doy "+comando[2]+" por "+comando[4]+" y "+comando[6]+"?");
                break;
            case 6:
                for(Casilla casilla : tablero.getColeccionCasillas()){
                    if(casilla instanceof Propiedad propiedad){
                        if(comando[2].equals(propiedad.getNombre())){
                            if(!propiedad.getDuenho().equals(jugadorTurno)){
                                throw new ExcepcionesCasilla("No eres dueño de esa casilla, no puedes hacer tratos con ella.");
                            }
                            daProp = propiedad;
                        }
                        if(comando[4].equals(propiedad.getNombre())){
                            if(!propiedad.getDuenho().equals(recibeTrato)){
                                throw new ExcepcionesCasilla("El jugador con el que quieres negociar no es dueño de la casilla que indicas.");
                            }
                            recibeProp = propiedad;
                        }
                        if(comando[7].equals(propiedad.getNombre())){
                            if(!propiedad.getDuenho().equals(recibeTrato)){
                                throw new ExcepcionesCasilla("El jugador con el que quieres negociar no es dueño de la casilla que indicas.");
                            }
                            noalquiler = propiedad;
                        }
                    }
                }
                if(daProp==null){
                    throw new ExcepcionCasillaNoExiste("La casilla: " + comando[2] + " no existe.");
                }
                if(recibeProp==null){
                    throw new ExcepcionCasillaNoExiste("La casilla: " + comando[4]+" no existe.");
                }
                if(noalquiler==null){
                    throw new ExcepcionCasillaNoExiste("La casilla: " + comando[7]+" no existe.");
                }
                recibeTrato.getTratosPorAceptar().add(new Trato(jugadorTurno,recibeTrato,6,daProp,recibeProp,noalquiler,Integer.parseInt(comando[8]),this));
                c.escribir(recibeTrato.getNombre()+": Te doy "+comando[2]+" por "+comando[4]+" y no pagar alquiler en "+comando[7]+" "+comando[8]+" rondas?");
                break;

        }
    }

    public int tipoTrato(String[] comando) throws ExcepcionesAccion{
        if(comando.length==5){
            if(comando[2].chars().allMatch(Character::isDigit)){
                return 3;
            }else if(comando[4].chars().allMatch(Character::isDigit)) {
                return 2;
            }else {
                return 1;
            }
        }else if(comando.length==7 && (comando[6].chars().allMatch(Character::isDigit))){
            return 5;
        }else if(comando.length==7 && (comando[4].chars().allMatch(Character::isDigit))){
            return 4;
        }else if(comando.length==9 && (comando[8].chars().allMatch(Character::isDigit)) && comando[6].equals("noalquiler")) {
            return 6;
        }else{
            throw new ExcepcionesAccion("Error con lel tipo de trato indicado.");
        }
    }

    @Override
    public void comandoEliminarTrato(String comando) {
        Trato trat = null;
        Jugador juga = null;
        for(Jugador j :jugadoresPartida){
            for(Trato t: j.getTratosPorAceptar()){
                if(t.getProponeJ().equals(jugadorTurno)){
                    if(t.getId().equals(comando)){
                        juga=j;
                        trat=t;
                        c.escribir("trato eliminado");
                    }
                }
            }
        }
        if(trat!=null && juga!=null){
            juga.getTratosPorAceptar().remove(trat);

        }
    }

    @Override
    public void comandoAceptarTrato(String comando) {
        boolean e = false;
        for(Trato t: jugadorTurno.getTratosPorAceptar()){
            if(t.getId().equals(comando)){
                switch(t.getTipo()){
                    case 1:
                        if(t.aceptarTipo1()){
                            e=true;
                        }
                        break;
                    case 2:
                        if(t.aceptarTipo2()){
                            e=true;
                        }
                        break;
                    case 3:
                        if(t.aceptarTipo3()){
                            e=true;
                        }
                        break;
                    case 4:
                        if(t.aceptarTipo4()){
                            e=true;
                        }
                        break;
                    case 5:
                        if(t.aceptarTipo5()){
                            e=true;
                        }
                        break;
                    case 6:
                        if(t.aceptarTipo6()){
                            e=true;
                        }
                        break;

                }
                if(e){
                    jugadorTurno.getTratosPorAceptar().remove(t);
                }
                return;
            }

        }

        c.escribir("No puedes aceptar ese trato");

    }
public int indiceJugador(Jugador j){
        return jugadoresPartida.indexOf(j);
}
    @Override
    public void comandoEdificar(String tipo) throws ExcepcionesEdificio{
        Casilla casill = jugadorTurno.getAvatar().getCasilla();
        if (!(casill instanceof Solar casilla)){
            //c.escribir("No puedes edificar en esta casilla");
            throw new ExcepcionesEdificio("No puedes edificar en esta casilla");
        }
        casilla.edificar(tipo, jugadorTurno, tablero);
    }

    @Override
    public void comandoVenderEdificio(String tipo, String casillastr, String numero) throws ExcepcionesCasilla, ExcepcionesAccion, ExcepcionesDinero {
        Solar casilla=null;
        int num=0;
        //comprobaciones
        try {
            num = Integer.parseInt(numero);
        } catch (NumberFormatException e) {
            c.escribir("Número no válido");
            return;
        }
        if(num<=0 || num>4){
            throw new ExcepcionesCasilla("El numero introducido tiene que estar comprendido en [1,4]");
        }
        for(Casilla casillaAux : tablero.getColeccionCasillas()){
            if(casillaAux.getNombre().equals(casillastr) && casillaAux.getTipo().equals("Solar")){
                casilla= (Solar) casillaAux;
                break;
            }
        }
        if(casilla==null){
            throw new ExcepcionCasillaNoExiste("Esa casilla no existe.");
        }
        int eliminados=0;
        Grupo grupo = null;
        for(Grupo grup: tablero.getGrupos()){
            if(grup.getColor().equals(casilla.getGrupo())){
                grupo = grup;
                break;
            }
        }
        if(grupo==null){
            throw new ExcepcionesCasilla("Grupo incorrecto");
        }
        //VENDE EL EDIFICIO
        switch (tipo){
            case "casa":
                if(casilla.getCasas()<num){
                    throw new ExcepcionesCasilla("No tienes tantas casas para vender");
                }
                while(eliminados<num){
                    for(Edificio edificio: casilla.getEdificiosEnCasilla()){
                        if(edificio.getTipo().equals(tipo)){
                            casilla.getEdificiosEnCasilla().remove(edificio);
                            break;
                        }
                    }
                    for(Edificio edificio: jugadorTurno.getEdificios()){
                        if(edificio.getTipo().equals(tipo)){
                            jugadorTurno.getEdificios().remove(edificio);
                            eliminados ++;
                            break;
                        }
                    }
                }

                casilla.setCasas(casilla.getCasas()-num);
                grupo.setCasasgrupo(grupo.getCasasgrupo()-num);
                jugadorTurno.setFortuna((int) (0.6 * casilla.getValor() * num/2), "suma");
                c.escribir(("El jugador " + jugadorTurno.getNombre() + " vende " + num + " casas y gana " + (int) (0.6 * casilla.getValor() * num/2) + " euros. " +
                        "Su fortuna actual es: " + jugadorTurno.getFortuna() + " euros."));
                break;
            case "hotel":
                if(casilla.getHoteles()<num){
                    throw new ExcepcionesCasilla("No tienes tantos hoteles para vender");
                }
                while(eliminados<num){
                    for(Edificio edificio: casilla.getEdificiosEnCasilla()){
                        if(edificio.getTipo().equals(tipo)){
                            casilla.getEdificiosEnCasilla().remove(edificio);
                            break;
                        }
                    }
                    for(Edificio edificio: jugadorTurno.getEdificios()){
                        if(edificio.getTipo().equals(tipo)){
                            jugadorTurno.getEdificios().remove(edificio);
                            eliminados ++;
                            break;
                        }
                    }
                }

                casilla.setHoteles(casilla.getHoteles()-num);
                grupo.setHotelesgrupo(grupo.getHotelesgrupo()-num);
                jugadorTurno.setFortuna((int) (0.6 * casilla.getValor() * num/2), "suma");
                c.escribir(("El jugador " + jugadorTurno.getNombre() + " vende " + num + " hoteles y gana " + (int) (0.6 * casilla.getValor() * num/2) + " euros. " +
                        "Su fortuna actual es: " + jugadorTurno.getFortuna() + " euros."));
                break;
            case "piscina":
                if(casilla.getPiscinas()<num){
                    throw new ExcepcionesCasilla("No tienes tantas piscinas para vender");
                }
                while(eliminados<num){
                    for(Edificio edificio: casilla.getEdificiosEnCasilla()){
                        if(edificio.getTipo().equals(tipo)){
                            casilla.getEdificiosEnCasilla().remove(edificio);
                            break;
                        }
                    }
                    for(Edificio edificio: jugadorTurno.getEdificios()){
                        if(edificio.getTipo().equals(tipo)){
                            jugadorTurno.getEdificios().remove(edificio);
                            eliminados ++;
                            break;
                        }
                    }
                }

                casilla.setPiscinas(casilla.getPiscinas()-num);
                grupo.setPiscinasgrupo(grupo.getPiscinasgrupo()-num);
                jugadorTurno.setFortuna((int) (0.4 * casilla.getValor() * num/2), "suma");
                c.escribir(("El jugador " + jugadorTurno.getNombre() + " vende " + num + " piscinas y gana " + (int) (0.4 * casilla.getValor() * num/2) + " euros. " +
                        "Su fortuna actual es: " + jugadorTurno.getFortuna() + " euros."));
                break;
            case "pista":
                if(casilla.getPistas()<num){
                    throw new ExcepcionesCasilla( "No tienes tantas pistas para vender");
                }
                while(eliminados<num){
                    for(Edificio edificio: casilla.getEdificiosEnCasilla()){
                        if(edificio.getTipo().equals(tipo)){
                            casilla.getEdificiosEnCasilla().remove(edificio);
                            break;
                        }
                    }
                    for(Edificio edificio: jugadorTurno.getEdificios()){
                        if(edificio.getTipo().equals(tipo)){
                            jugadorTurno.getEdificios().remove(edificio);
                            eliminados ++;
                            break;
                        }
                    }
                }

                casilla.setPistas(casilla.getPistas()-num);
                grupo.setPistasgrupo(grupo.getPistasgrupo()-num);
                jugadorTurno.setFortuna((int) (1.25 * casilla.getValor() * num/2), "suma");
                c.escribir(("El jugador " + jugadorTurno.getNombre() + " vende " + num + " pistas y gana " + (int) (1.25 * casilla.getValor() * num/2) + " euros. " +
                        "Su fortuna actual es: " + jugadorTurno.getFortuna() + " euros."));
                break;
        }
        if (jugadorTurno.getEstarPobre()){
            caerEnCasilla(jugadorTurno.getAvatar().getCasilla(), dadoTurno.sumaDados());
        }
    }

    @Override
    public boolean comandoBancarrota() throws ExcepcionesAccion{
        Jugador jugadorEliminado=null;
        boolean tieneDueno = false;
        if(jugadorTurno.getEstarPobre()) {
                if (jugadorTurno.getAvatar().getCasilla() instanceof Propiedad) {
                    Jugador jugador = ((Propiedad) jugadorTurno.getAvatar().getCasilla()).getDuenho();
                    jugador.setFortuna(jugadorTurno.getFortuna(), "suma");
                    for (Propiedad casilla : jugadorTurno.getPropiedades()) {
                        jugador.getPropiedades().add(casilla);
                    }
                    jugadorTurno.getPropiedades().clear();
                    c.escribir("El jugador " + jugadorTurno.getNombre() + " se ha declarado en bancarrota. Sus propiedades y fortuna pasan al jugador " + jugador.getNombre() + " .");
                    tieneDueno = true;
                }
        }
        if(!tieneDueno || !jugadorTurno.getEstarPobre()){
            for (Propiedad casilla : jugadorTurno.getPropiedades()) {
                if(casilla instanceof Solar){
                    Solar solar = (Solar) casilla;
                    solar.getEdificiosEnCasilla().clear();
                }
                banca.getPropiedades().add(casilla);
            }
            jugadorTurno.getPropiedades().clear();
            c.escribir("El jugador " + jugadorTurno.getNombre() + " se ha declarado en bancarrota. Sus propiedades pasan a estar de nuevo en venta al precio al que estaban.");
        }
        jugadorEliminado = jugadorTurno;
        jugadorEliminado.setHaberTirado(true);
        jugadorEliminado.setEstarPobre(false);
        comandoAcabarTurno();
        jugadoresPartida.remove(jugadorEliminado);
        jugadorEliminado.getAvatar().getCasilla().getAvataresEnCasilla().remove(jugadorEliminado.getAvatar());
        if(jugadoresPartida.size() == 1){
            c.escribir("\nEl jugador " + jugadorTurno.getNombre() + " ha ganado la partida.");
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void comandoHipotecar(String nombreCasilla) throws ExcepcionesCasilla{
        for (Propiedad casilla : jugadorTurno.getPropiedades()) {
            if (casilla.getNombre().equals(nombreCasilla) && casilla instanceof Solar) {
                Solar s = (Solar) casilla;
                if(!s.getEdificiosEnCasilla().isEmpty()){
                    throw new ExcepcionesCasilla("No puedes hipotecar esta propiedad porque no has vendido todos sus edificios.");
                }else{
                    jugadorTurno.getHipotecas().add(casilla);
                    casilla.setHipotecado(true);
                    jugadorTurno.setFortuna(casilla.getHipoteca(),"suma");
                    c.escribir(jugadorTurno.getNombre() + " ha hipotecado la casilla " + nombreCasilla);
                    jugadorTurno.setEstarPobre(false);
                    return;
                }

            } else if (casilla.getNombre().equals(nombreCasilla) && (casilla.getTipo().equals("Transporte") || casilla.getTipo().equals("Servicio"))) {
                jugadorTurno.getHipotecas().add(casilla);
                casilla.setHipotecado(true);
                jugadorTurno.setFortuna(casilla.getHipoteca(), "suma");
                c.escribir(jugadorTurno.getNombre() + " ha hipotecado la casilla " + nombreCasilla);
                jugadorTurno.setEstarPobre(false);
                return;
            }
        }
        throw new ExcepcionesCasilla(String.format("%s no puede hipotecar esta casilla\n",jugadorTurno.getNombre()));
    }

    @Override
    public void comandoDesHipotecar(String nombreCasilla) throws ExcepcionesDinero, ExcepcionesCasilla{
        for (Propiedad casilla : jugadorTurno.getHipotecas()) {
            if (casilla.getNombre().equals(nombreCasilla)) {
                if (jugadorTurno.getFortuna() >= (int) (casilla.getHipoteca() * 1.1)) {
                    jugadorTurno.setFortuna((int) (casilla.getHipoteca() * 1.1), "resta");
                    jugadorTurno.getHipotecas().remove(casilla);
                    casilla.setHipotecado(false);
                    c.escribir(jugadorTurno.getNombre() + " ha deshipotecado la casilla " + casilla.getNombre());
                    return;
                } else {
                    throw new ExcepcionesDinero("No tienes dinero suficiente para deshipotecar esa propiedad, necesitas %d " + (int) (casilla.getHipoteca() * 1.1) + " euros",jugadorTurno.getNombre(),jugadorTurno.getFortuna());
                }
            }
        }
        throw new ExcepcionesCasilla("No se puede deshipotecar esta casilla");
    }

    @Override
    public void comandoCrearJugador(String[] comando) throws ExcepcionesAccion{
        if (comando.length != 4 || !comando[1].equals("jugador")) {
            throw new ExcepcionesAccion("Comando CrearJugador incorrecto, la estructura del mismo es: crear jugador <nombre_jugador> <ficha_jugador>");
        } else if (!comando[3].equals("pelota") && !comando[3].equals("coche") && !comando[3].equals("esfinge") && !comando[3].equals("sombrero")) {
            throw new ExcepcionesAccion("Comando CrearJugador incorrecto. La ficha del jugador no es correcta. Las fichas disponibles son: coche, sombrero, esfinge y pelota");
        } else {
            jugadoresPartida.add(new Jugador(comando[2], comando[3], tablero));
            for(Casilla c : tablero.getColeccionCasillas()){
                if(c instanceof Propiedad p){
                    p.setTurnossinpagar();
                }
            }
            Juego.c.escribir(String.format("""
                            {
                                 Nombre: %s
                                 Avatar: %s
                            {
                             """,
                    jugadoresPartida.get(jugadoresPartida.size() - 1).getNombre(),
                    jugadoresPartida.get(jugadoresPartida.size() - 1).getAvatar().getId()));
            if (jugadoresPartida.size() == 1) {
                jugadorTurno = jugadoresPartida.get(0);
            }
        }
    }

    @Override
    public void comandoJugador() {
            c.escribir("""
                {
                    nombre: %s
                    avatar: %s
                }
                """.formatted(jugadorTurno.getNombre(), jugadorTurno.getAvatar().getId()));
    }

    @Override
    public void comandoListarTratos() {
            for(Trato trato : jugadorTurno.getTratosPorAceptar()){
                c.escribir(trato.toString());
            }
    }

    @Override
    public void comandoListarEdificios() {
            String string="";
            for(Grupo g : tablero.getGrupos()){
                for(Solar casilla : g.getCasillasGrupo()) {
                    for (Edificio edificio : casilla.getEdificiosEnCasilla()) {
                        c.escribir(edificio.toString());}
                }
            }
            c.escribir(string);
    }

    @Override
    public void comandoListarJugadores() {
        for (int i = 0; i < jugadoresPartida.size(); i++) {
            c.escribir("""
                    {
                        nombre: %s
                        avatar: %s
                        fortuna: %d
                        propiedades: %s
                        hipotecas: %s
                        edificios: %s
                    }
                    """.formatted(jugadoresPartida.get(i).getNombre(), jugadoresPartida.get(i).getAvatar().getId(), jugadoresPartida.get(i).getFortuna(), jugadoresPartida.get(i).arrayPropiedadesImprimir(), jugadoresPartida.get(i).arrayHipotecasImprimir(),jugadoresPartida.get(i).arrayEdificios()));
        }
    }

    @Override
    public void comandoListarEdificiosGrupo(String comando) {
        for (Grupo grupo : tablero.getGrupos()) {
            if (grupo.getColor().equals(comando)) {
                for (Solar casilla : grupo.getCasillasGrupo()) {
                    c.escribir("""
                            {
                                propiedad: %s
                                casas: %s
                                hoteles: %s
                                piscinas: %s
                                pistas: %s
                                alquiler: %d
                            }
                            """.formatted(casilla.getNombre(),casilla.arrayCasasCasilla("casa"),casilla.arrayCasasCasilla("hotel"),casilla.arrayCasasCasilla("piscina"),casilla.arrayCasasCasilla("pista"),casilla.alquiler(tablero,1,casilla,null,jugadorTurno)));
                }
                Juego.c.escribir(String.format("Edificios restantes por construir en el grupo:"));
                Juego.c.escribir(String.format("Casas: %d\n",(grupo.getMaxcasasgrupo() - grupo.getCasasgrupo() + 4*(grupo.getMaxhotelesgrupo() - grupo.getHotelesgrupo()))));
                Juego.c.escribir(String.format("Hoteles: %d\n",(grupo.getMaxhotelesgrupo() - grupo.getHotelesgrupo())));
                Juego.c.escribir(String.format("Piscinas: %d\n",(grupo.getMaxpiscinasgrupo() - grupo.getPiscinasgrupo())));
                Juego.c.escribir(String.format("Pistas: %d\n",(grupo.getMaxpistasgrupo() - grupo.getPistasgrupo())));
            }
        }
    }

    @Override
    public void comandoListarAvatares() {
        for (int i = 0; i < jugadoresPartida.size(); i++) {
            c.escribir("""
                    {
                        id: %s
                        tipo: %s
                        casilla: %s
                        jugador: %s
                    }
                    """.formatted(jugadoresPartida.get(i).getAvatar().getId(), jugadoresPartida.get(i).getAvatar().getFicha(),
                    jugadoresPartida.get(i).getAvatar().getCasilla().getNombre(), jugadoresPartida.get(i).getAvatar().getJugador().getNombre()));
        }
    }

    @Override
    public void comandoListarEnVenta() throws ExcepcionesCasilla{
        if (banca.getPropiedades().isEmpty()){
            throw new ExcepcionesCasilla("No hay propiedades en venta.");
        }
        for (int i = 0; i < banca.getPropiedades().size(); i++) {
            if (banca.getPropiedades().get(i).getTipo().equals("Solar")) {
                Solar s= (Solar) banca.getPropiedades().get(i);
                c.escribir(s.toStringCasilla());
            } else {
                Propiedad p = banca.getPropiedades().get(i);
               }
        }
    }

    @Override
    public void comandoDescribirJugador(String comando) throws ExcepcionesElementosJuego{
        for (Jugador j : jugadoresPartida) {
            if (j.getNombre().equals(comando)) {
                c.escribir(String.format("""
                                {
                                    nombre: %s
                                    avatar: %s
                                    fortuna: %d
                                    propiedades: %s
                                    hipotecas: %s
                                    edificios: %s
                                }
                                """, j.getNombre(), j.getAvatar().getId(), j.getFortuna(), j.arrayPropiedadesImprimir(),
                        j.arrayHipotecasImprimir(),j.arrayEdificios()));
                return;
            }
        }
        throw new ExcepcionesElementosJuego(String.format("%s no está jugando\n", comando));
    }

    @Override
    public void comandoDescribirAvatar(String comando) throws ExcepcionesElementosJuego{
        for (Jugador j : jugadoresPartida) {
            if (j.getAvatar().getId().equals(comando)) {
                c.escribir(j.getAvatar().toStringAvatar());
                return;
            }
        }
        throw new ExcepcionesElementosJuego(String.format("%s no está jugando", comando));
    }

    @Override
    public void comandoDescribirCasilla(String comando) {
        int valorSalilda = tablero.getColeccionCasillas().get(0).getValor();
        for (Casilla casilla : tablero.getColeccionCasillas()) {
            if ((comando.equals("DeLujo")||comando.equals("Reintegro")) && casilla instanceof Impuesto) {
                Impuesto i=(Impuesto)casilla;
                c.escribir(i.toString());
            }else if (comando.equals("Parking") && casilla.getNombre().equals("Parking")) {
                Accion accion = (Accion) casilla;
                c.escribir(accion.toString());
            } else if (comando.equals("Salida") && casilla.getNombre().equals("Salida")) {
                Especial especial = (Especial) casilla;
                c.escribir(especial.toString());
            } else if (comando.equals("Carcel") && casilla.getNombre().equals("Carcel")) {
                Especial especial = (Especial) casilla;
                c.escribir(especial.toString());
                //TRANSPORTES
            } else if (comando.equals(casilla.getNombre()) && casilla.getTipo().equals("Transporte")) {
                Transporte transporte = (Transporte) casilla;
                c.escribir(transporte.toString());
            }
            //SERVICIO
            else if (comando.equals(casilla.getNombre()) && casilla.getTipo().equals("Servicio")) {
                Impuesto impuesto = (Impuesto) casilla;
                c.escribir(impuesto.toString());
                //SOLARES
            } else if (casilla.getTipo().equals("Solar") && casilla.getNombre().equals(comando)) {
                Solar solar = (Solar) casilla;
                c.escribir(solar.toString());
            }
        }
    }

    @Override
    public void comandoEmpezarPartida() {
        c.escribir("Utiliza el comando crear jugador para crear minimo 2 jugadores y hasta un maximo de 6 jugadores");
        c.escribir("No podrás volver a usar el comando crear jugador una vez empezada la partida");
        String comando = "";
        String opcion = "s";
        while (!opcion.equals("n") || jugadoresPartida.size() < 2) {
            if (opcion.equals("s")) {
                c.escribir("$> ",0);
                comando = c.leer();
                String[] partes = comando.split(" ");
                boolean flag = true;
                while(flag) {
                    try {
                        comandoCrearJugador(partes);
                        flag = false;
                    }catch (ExcepcionesAccion e){
                        c.escribir(e.getMessage());
                    }
                }
            }
            if (jugadoresPartida.size() < 6 && jugadoresPartida.size() >= 2) {
                c.escribir("Crear otro jugador? (s/n):  ",0);
                opcion = c.leer();
            } else {
                if (jugadoresPartida.size() > 5) {
                    opcion = "n";
                }
            }
        }
        c.escribir("Empezando partida...");
    }
    public void comandoEmpezarPartida(int e){
        try {
            String comando = "crear jugador manu pelota";
            String[] partes = comando.split(" ");
            comandoCrearJugador(partes);
            comando = "crear jugador mati pelota";
            partes = comando.split(" ");
            comandoCrearJugador(partes);
            comando = "crear jugador carla coche";
            partes = comando.split(" ");
            comandoCrearJugador(partes);
        }catch (ExcepcionesAccion error){
            c.escribir(error.getMessage());
        }
        c.escribir("Empezando partida...");
    }

    @Override
    public void comandoSalircarcel() throws ExcepcionesAccion, ExcepcionesDinero{
        jugadorTurno.salirCarcel();
        if (jugadorTurno.getAvatar().getEstado() == 0) {
            jugadorTurno.setHaberTirado(true);
            comandoAcabarTurno();
        }
    }

    @Override
    public void comandoLanzarDadosMoverse() throws ExcepcionesAccion, ExcepcionesDinero{
        int posiciones = 0;
        Casilla destino = jugadorTurno.getAvatar().getCasilla();
        if(jugadorTurno.getEstarPobre()) {
            throw new ExcepcionesDinero(String.format("%s no puede tirar los dados, debe declararse en bancarrota, hipotecar propiedades o vender edificios.\n",jugadorTurno.getNombre()),jugadorTurno.getNombre(),jugadorTurno.getFortuna());
        }
        if(jugadorTurno.getHaberTirado()){
            throw new ExcepcionesAccion("No puedes tirar los dados, ya has tirado");
        }
        else if(jugadorTurno.getAvatar().getBloqueado()>0){
            throw new ExcepcionesAccion("No puedes tirar los dados, estás bloqueado. Estarás bloqueado "+jugadorTurno.getAvatar().getBloqueado()+" rondas más");
        }
        if(jugadorTurno.getAvatar().getEstado()==0){
            throw new ExcepcionesAccion("No puedes tirar los dados, estás en la cárcel");
        }


        //TIRADA NORMAL
        if(!jugadorTurno.getAvatar().isModo()) {
            //te mueves int posiciones (dentro de la funcion lanzar dados actualiza la ronda del jugador y despues la de la partida en la siguiente funcion)

            posiciones = dadoTurno.lanzarDados();
            jugadorTurno.setNumeroTiradas();
            comprobarRondaPartida();
            jugadorTurno.getAvatar().moverEnBasico(dadoTurno,posiciones,this);

        }

        //TIRADA AVANZADA
        else {
            jugadorTurno.getAvatar().moverEnAvanzado(dadoTurno,this);
            if(comprobacionCarcelPelota==1){
                comprobacionCarcelPelota=0;
                return;
            }

        }
        destino = jugadorTurno.getAvatar().getCasilla();
        caerEnCasilla(destino,posiciones);

        //ACABAS LA TIRADA
        if(dadoTurno.getDado1() != dadoTurno.getDado2()){
            jugadorTurno.setHaberTirado(true);
        }
        if(jugadorTurno.getAvatar().isModo()){
            jugadorTurno.setHaberTirado(true);
        }

    }

    @Override
    public void comandoComprar(String comando) throws ExcepcionesCasilla{
        Casilla casilla = jugadorTurno.getAvatar().getCasilla();
        if(!comando.equals(casilla.getNombre())){
            throw new ExcepcionesCasilla(String.format("%s no está en la casilla %s, no puede comprarla\n",jugadorTurno.getNombre(),comando));
        }
        else if(!(casilla instanceof Propiedad)){
            throw new ExcepcionesCasilla("Esa casilla ya es propiedad de alguien, no se puede usar este comando para comprarla.");
        }
        Propiedad p = (Propiedad) casilla;
        p.comprar(jugadorTurno,banca,jugadoresPartida);
    }

    @Override
    public void comandoCambiarModo() {
        jugadorTurno.getAvatar().setModo();
        if(jugadorTurno.getAvatar().isModo()){
            Juego.c.escribir(String.format("El avatar %s se moverá en modo avanzado a partir de ahora\n",jugadorTurno.getAvatar().getId()));
        }
        else{
            Juego.c.escribir(String.format("El avatar %s se moverá en modo normal a partir de ahora\n",jugadorTurno.getAvatar().getId()));
        }
    }

    @Override
    public void comandoVerTablero() {
        tablero.imprimirTablero();
    }

    @Override
    public void comandoAcabarTurno() throws ExcepcionesAccion{
        if(jugadorTurno.getEstarPobre()){
            throw new ExcepcionesAccion("No puedes acabar tu turno, vende edificios, hipoteca propiedades o declárate en bancarrota");
        }
        if(jugadorTurno.getHaberTirado() || jugadorTurno.getAvatar().getBloqueado()>0) {
            jugadorTurno.setHaberTirado(false);
            dadoTurno = new Dado();
            indiceJugador = (indiceJugador + 1) % jugadoresPartida.size();
            jugadorTurno = jugadoresPartida.get(indiceJugador);
            if(jugadorTurno.getAvatar().getBloqueado()>0){
                jugadorTurno.getAvatar().setBloqueado(-1);
            }
            c.escribir(String.format("El jugador actual es %s\n", jugadorTurno.getNombre()));
        }else{
            throw new ExcepcionesAccion("No puedes acabar tu turno sin haber tirado");
        }
    }

    @Override
    public void comandoEstadisticasJugador(String comando) {

    }

    @Override
    public void comandoEstadisticasJuego() {

    }


    //throws ExcepcionesDinero ??
    public void caerEnCasilla(Casilla destino, int posiciones) throws ExcepcionesAccion,ExcepcionesDinero{
        //CAES EN LA CARCEL
        if (destino.getNombre().equals("Carcel") || destino.getNombre().equals("IrCarcel")) {
            jugadorTurno.getAvatar().setPosicion(10, 0);
            jugadorTurno.setVecesEnLaCarcel();
            if (jugadorTurno.getAvatar().getEstado() == 0) {
                Juego.c.escribir("Estás encerrado en la cárcel");
            }
            jugadorTurno.setHaberTirado(true);
            comandoAcabarTurno();
            return;
        }
        //CAES EN UN SOLAR
        int c = 1;
        if (destino.getTipo().equals("Solar")) {
            Solar solar = (Solar) destino;
            if(!solar.getHipotecado()){
            for (Jugador jugador : jugadoresPartida) {
                if (solar.perteneceAJugador(jugador)) {
                    if (!solar.perteneceAJugador(jugadorTurno)) {
                        if(solar.getTurnosSinPagar(indiceJugador)>=1){
                            solar.setTurnossinpagar(indiceJugador,-1);
                            Juego.c.escribir("Te libras de pagar alquiler, te quedan "+solar.getTurnosSinPagar(indiceJugador)+" comodines más en "+solar.getNombre());
                            return;
                        }

                        int alquiler = solar.alquiler(tablero, 0, destino, jugador, jugadorTurno);
                        jugadorTurno.setFortuna(alquiler, "resta");
                        if (!jugadorTurno.getEstarPobre()) {
                            jugadorTurno.setPagoDeAlquileres(alquiler);
                            jugador.setFortuna(alquiler, "suma");
                            solar.setValorCompra(solar.getValorCompra() + alquiler);
                            jugador.setCobroDeAlquileres(alquiler);
                            Juego.c.escribir(String.format("%s le paga %d euros de alquiler a %s\n", jugadorTurno.getNombre(), alquiler, jugador.getNombre()));
                        }else{
                            throw new ExcepcionesDinero("El jugador no tiene dinero suficiente.",jugadorTurno.getNombre(),jugadorTurno.getFortuna());
                        }
                    }
                }
            }
        }
        }
        //HIPOTECADO
        if(destino instanceof Propiedad) {
            if (((Propiedad)destino).getHipotecado()) {
                Juego.c.escribir(String.format("%s está hipotecado, el dueño es %s\n", destino.getNombre(), ((Propiedad)destino).getDuenho().getNombre()));
            }
        }
        //TRANSPORTE
        if (destino.getTipo().equals("Transporte")) {
            Transporte transporte = (Transporte) destino;
            if(!transporte.getHipotecado()){
            for (Jugador jugador : jugadoresPartida) {
                if (transporte.perteneceAJugador(jugador)) {
                    if (!transporte.perteneceAJugador(jugadorTurno)) {
                        if(transporte.getTurnosSinPagar(indiceJugador)>=1){
                            transporte.setTurnossinpagar(indiceJugador,-1);
                            Juego.c.escribir("Te libras de pagar alquiler, te quedan "+transporte.getTurnosSinPagar(indiceJugador)+" comodines más en "+transporte.getNombre());
                            return;
                        }
                        int alquilerTrans = transporte.alquiler(tablero,0,transporte,jugador,jugadorTurno);
                        transporte.setAlquiler(alquilerTrans);
                        jugadorTurno.setFortuna(alquilerTrans, "resta");
                        if (!jugadorTurno.getEstarPobre()){
                            jugadorTurno.setPagoDeAlquileres(alquilerTrans);
                            jugador.setFortuna(alquilerTrans, "suma");
                            jugador.setCobroDeAlquileres(alquilerTrans);
                            Juego.c.escribir(String.format("%s le paga %d euros a %s por operación de transporte\n", jugadorTurno.getNombre(), alquilerTrans, jugador.getNombre()));
                        }else{
                            throw new ExcepcionesDinero("El jugador no tiene dinero suficiente.",jugadorTurno.getNombre(),jugadorTurno.getFortuna());
                        }
                    }
                }
            }
            }
        }
        //SERVICIO
        else if (destino.getTipo().equals("Servicio")){
            Servicio servicio = (Servicio) destino;
            if(!servicio.getHipotecado()) {
            for (Jugador jugador : jugadoresPartida) {
                if (servicio.perteneceAJugador(jugador)) {
                    if (!servicio.perteneceAJugador(jugadorTurno)) {
                        if(servicio.getTurnosSinPagar(indiceJugador)>=1){
                            servicio.setTurnossinpagar(indiceJugador,-1);
                            Juego.c.escribir("Te libras de pagar alquiler, te quedan "+servicio.getTurnosSinPagar(indiceJugador)+" comodines más en "+servicio.getNombre());
                            return;
                        }
                        int alquilerServ = servicio.alquiler(tablero,posiciones,servicio,jugador,jugadorTurno);
                        servicio.setAlquiler(alquilerServ);
                        jugadorTurno.setFortuna(alquilerServ, "resta");
                        if (!jugadorTurno.getEstarPobre()){
                            jugadorTurno.setPagoDeAlquileres(alquilerServ);
                            jugador.setFortuna(alquilerServ, "suma");
                            jugador.setCobroDeAlquileres(alquilerServ);
                            Juego.c.escribir(String.format("%s le paga %d euros a %s por operación de servicio\n", jugadorTurno.getNombre(), alquilerServ, jugador.getNombre()));
                        }else{
                            throw new ExcepcionesDinero("El jugador no tiene dinero suficiente.",jugadorTurno.getNombre(),jugadorTurno.getFortuna());
                        }
                    }
                }
            }
         }
        }
        //CAES EN PARKING (TE LLEVAS EL BOTE Y SE RESETEA)
        else if (destino.getTipo().equals("Parking")) {
            jugadorTurno.setFortuna(destino.getValor(), "suma");
            jugadorTurno.setPremiosInversionesOBote(destino.getValor());
            Juego.c.escribir(String.format("%s gana %d euros del bote\n", jugadorTurno.getNombre(), destino.getValor()));
            destino.setValor(0);
        }
        //CAES EN IMPUESTOS (PAGAS A LA BANCA Y SE LLENA EL BOTE)
        else if (destino instanceof Impuesto) {
            jugadorTurno.setFortuna(destino.getValor(), "resta");
            if(!jugadorTurno.getEstarPobre()){
                jugadorTurno.setPagoTasasEImpuestos(destino.getValor());
                tablero.getColeccionCasillas().get(20).setValor(destino.getValor(), "suma");
                Juego.c.escribir(String.format("%s le paga %d euros a la banca\n", jugadorTurno.getNombre(), destino.getValor()));
            }else{
                throw new ExcepcionesDinero("El jugador no tiene dinero suficiente.",jugadorTurno.getNombre(),jugadorTurno.getFortuna());
            }
        } else if (destino.getNombre().equals("Caja") || destino.getNombre().equals("Suerte")) {
            int numero = -1;
            while(numero<1 || numero>6) {
                Juego.c.escribir("Escoge una de las 6 cartas posibles (del 1 al 6): ");
                numero = Juego.c.leer(0);
            }
            if(destino instanceof AccionCaja caja){
                caja.accion(numero,tablero,jugadorTurno,jugadoresPartida,this);
            }
            if(destino instanceof AccionSuerte suerte){
                suerte.accion(numero,tablero,jugadorTurno,jugadoresPartida,this);
            }

        }
        jugadorTurno.getAvatar().getCasilla().setFrecuencia(); //creo que se puede hacer aqui
    }

    public void comprobarRondaPartida(){
        int minimo=1000;
        for(Jugador jugador : jugadoresPartida){
            if(jugador.getAvatar().getRonda()<=minimo){
                minimo = jugador.getAvatar().getRonda();
            }
        }
        rondaPartida = minimo;
        //al llegar a 4 rondas resetea las rondas de la partida, le resta cuatro a la de los jugadores y revaloriza los solares (trans,imp,serv,salida¿¿)
        if(rondaPartida == 4){
            rondaPartida=0;
            for(Jugador jugador:jugadoresPartida){
                jugador.getAvatar().setRonda(jugador.getAvatar().getRonda()-4);
            }
            //actualizas el valor de los solares y el atributo del tablero
            for(Casilla casilla: tablero.getColeccionCasillas()){
                if(casilla.getTipo().equals("Solar")){
                    casilla.setValor((int) (casilla.getValor()*1.05));
                }
            }
            tablero.setValorTotalSolares((int)(tablero.getValorTotalSolares()*1.05));
            //actualizo valores de lo que ganas al completar la vuelta, y de los impuestos (que son lo que ganas al completar la vuelta y el otro la mitad)
            actualizarValores();
        }
    }
    private void actualizarValores(){
        tablero.getColeccionCasillas().get(0).setValor(tablero.getValorTotalSolares()/22);
        tablero.getColeccionCasillas().get(4).setValor((int) (tablero.getValorTotalSolares()*0.5/22));
        tablero.getColeccionCasillas().get(38).setValor(tablero.getValorTotalSolares()/22);
    }

    public void moverseCartas(Casilla destin) throws ExcepcionesCasilla, ExcepcionesDinero{
        boolean tieneDueño = false;
        if (destin.getTipo().equals("Transporte")){
            Transporte destino = (Transporte) destin;
            if(!destino.getHipotecado()) {
                for (Jugador jugador : jugadoresPartida) {
                    if (destino.getDuenho().getNombre().equals(jugador.getNombre())) {
                        if (!destino.getDuenho().getNombre().equals(jugadorTurno.getNombre())) {
                            tieneDueño = true;
                            Transporte transporte = (Transporte) destino;
                            int alquilerTrans = transporte.alquiler(tablero,0,transporte,jugador,jugadorTurno);
                            destino.setAlquiler(alquilerTrans);
                            jugadorTurno.setFortuna(alquilerTrans, "resta");
                            if (!jugadorTurno.getEstarPobre()) {
                                jugadorTurno.setPagoDeAlquileres(alquilerTrans);
                                jugador.setFortuna(alquilerTrans, "suma");
                                jugador.setCobroDeAlquileres(alquilerTrans);
                                Juego.c.escribir(String.format("%s le paga %d euros a %s por operación de transporte\n", jugadorTurno.getNombre(), alquilerTrans, jugador.getNombre()));
                            }else{
                                throw new ExcepcionesDinero("El jugador no tiene dinero suficiente.",jugadorTurno.getNombre(),jugadorTurno.getFortuna());
                            }
                            break;
                        }else{
                            throw new ExcepcionesCasilla("Ya eres dueño de esta casilla.");
                        }
                    }
                }
                if (!tieneDueño){
                    c.escribir("¿Quieres comprar esta casilla? (si/no) ",9);
                    String respuesta = c.leer();
                    if (respuesta.equals("si")){
                        comandoComprar(destino.getNombre());
                    }
                }
            }else{
                throw new ExcepcionesCasilla("Esta casilla ya tiene dueño y esta hipotecada");
            }
        }else{
            throw new ExcepcionesCasilla("Casilla de tipo incorrecto. ");
        }
    }
    @Override
    public void comandoHelp() {
        c.escribir("""
                Comandos del juego:
                    -crear jugador <nombre_jugador> <ficha> --> crea un jugador
                    -jugador --> indica el jugador que tiene el turno
                    -listar jugadores --> lista los jugadores de la partida
                    -listar avatares --> lista los avatares de la partida
                    -listar enventa --> lista la propiedades en venta
                    -lanzar dados --> lanzas los dados para moverte
                    -acabar turno
                    -salir carcel
                    -describir <nombre_casilla> --> describe la casilla asociada con el nombre que se le indique
                    -describir jugador <nombre_jugador> --> describe el jugador asociado con el nombre que se le indique
                    -describir avatar <id_avatar> --> describe el avatar asociado con el id que se le indique
                    -comprar <nombre_casilla> --> compras la casilla indicada
                    -ver tablero --> printea el tablero 
                    -help --> printea los comandos disponibles
                """);
    }
}
