package Casillas;
import Edificios.*;
import Monopoly.*;
import java.util.ArrayList;

public final class Solar extends Propiedad{
    private ArrayList<Edificio> edificiosEnCasilla;
    private String grupo;
    private int casas,hoteles,piscinas,pistas;

    //public Grupo getGrupo() {
      //  return grupo;
    //}

    public Solar(int numero, String nombre, String grupo, String tipo, Jugador banca, int numeroGrupo){
        super(numero,nombre,tipo,banca);
        this.grupo=grupo;
        this.edificiosEnCasilla = new ArrayList<Edificio>();
        casas=0;
        hoteles=0;
        piscinas=0;
        pistas=0;
    }
    public String getGrupo() {
        return grupo;
    }

    public ArrayList<Edificio> getEdificiosEnCasilla() {
        return edificiosEnCasilla;
    }

    public int getPistas() {
        return pistas;
    }

    public int getPiscinas() {
        return piscinas;
    }

    public int getHoteles() {
        return hoteles;
    }

    public int getCasas() {
        return casas;
    }

    public void setCasas(int casas) {
        this.casas = casas;
    }



    public void setHoteles(int hoteles) {
        this.hoteles = hoteles;
    }


    public void setPiscinas(int piscinas) {
        this.piscinas = piscinas;
    }


    public void setPistas(int pistas) {
        this.pistas = pistas;
    }

    public void setEdificiosEnCasilla(ArrayList<Edificio> edificiosEnCasilla) {
        this.edificiosEnCasilla = edificiosEnCasilla;
    }

    //@Override
    public String toStringCasilla() {
        return String.format("""
                        {
                            nombre: %s
                            tipo: %s
                            grupo: %s
                            valor: %d
                        }
                        """.formatted(this.getNombre(), this.getTipo(), this.getGrupo(),
                this.getValor()));
    }
    public String arrayCasasCasilla(String tipo){
        String string = "";
        for (Edificio edificio: edificiosEnCasilla){
            if(tipo.equals(edificio.getTipo())){
                string += "[" + edificio.getId() + "], ";
            }

        }
        return string;
    }

    public String arrayEdificiosCasilla(){
        String string = "";
        for (Edificio edificio: edificiosEnCasilla){
                string += "[" + edificio.getId() + "], ";

        }
        return string;
    }
    public int alquiler(Tablero t, int modo, Casilla destino, Jugador jugador, Jugador jugadorTurno){
        int c = 1;
        for (Casilla casillaColor : t.getColeccionCasillas()) {
            if(casillaColor instanceof Solar){
                Solar s = (Solar) casillaColor;
                if (s.getGrupo().equals(grupo)){
                    if (!s.getDuenho().getNombre().equals(getDuenho().getNombre())) {
                        c = 0;
                    }
                }
            }
        }
        int alquiler = getAlquiler();
        //c es 1 si es dueño del grupo, 0 si no lo es
        if (c == 1) {
            if(modo==0){
                Juego.c.escribir(String.format("%s es dueño de todo el grupo %s\n", jugador.getNombre(), grupo));
            }
            //el alquiler pasa a ser el doble
            alquiler *= 2;
        }
        switch (casas){
            case 1:
                alquiler += 5 * getAlquiler();
                break;
            case 2:
                alquiler += 15 * getAlquiler();
                break;
            case 3:
                alquiler += 35 * getAlquiler();
                break;
            case 4:
                alquiler += 50 * getAlquiler();
                break;
        }

        alquiler += (getAlquiler() * hoteles * 70 + getAlquiler() * piscinas * 25 + getAlquiler() * pistas * 25);
        return alquiler;
    }

    public void puedeConstruir(Jugador jugador, String tipo,ArrayList<Grupo> grupos) throws ExcepcionesEdificio{
        puedeConstruirGeneral(jugador);
        Grupo g=null;
        for(Grupo grup: grupos){
            if(grup.getColor().equals(getGrupo())){
                g = grup;
                break;
            }
        }
        switch (tipo){
            case "casa":
                if(casas==4){
                    throw new ExcepcionesEdificio("No puedes construir más casas ahora mismo en esta casilla");
                }
                if((g.getCasasgrupo()==g.getMaxcasasgrupo())){
                    if(g.getHotelesgrupo()==g.getMaxhotelesgrupo()){
                        throw new ExcepcionesEdificio("Ya has construido el máximo de casas en este grupo");
                    }
                }
                if(jugador.getFortuna() < 0.6*getValor()){
                    throw new ExcepcionesEdificio(String.format("%s no tiene suficiente dinero para construir una casa en %s\n",jugador.getNombre(),getNombre()));
                }
                break;
            case "hotel":
                if(g.getHotelesgrupo()==g.getMaxhotelesgrupo()){
                    throw new ExcepcionesEdificio("Ya has construido el máximo de hoteles en este grupo");
                }
                if(casas<4){
                    throw new ExcepcionesEdificio("Necesitas cuatro casas en esta casilla para construir un hotel");

                }
                if(jugador.getFortuna() < 0.6*getValor()){
                    throw new ExcepcionesEdificio(String.format("%s no tiene suficiente dinero para construir un hotel en %s\n",jugador.getNombre(),getNombre()));
                }
                break;
            case "piscina":
                if(g.getPiscinasgrupo()==g.getMaxpiscinasgrupo()){
                    throw new ExcepcionesEdificio("Ya has construido el máximo de piscinas en este grupo");
                }
                if(casas<2 || hoteles<1){
                    throw new ExcepcionesEdificio("Necesitas dos casas y un hotel en esta casilla para construir una piscina");
                }
                if(jugador.getFortuna() < 0.4*getValor()){
                    throw new ExcepcionesEdificio(String.format("%s no tiene suficiente dinero para construir una piscina en %s\n",jugador.getNombre(),getNombre()));
                }
                break;
            case "pista":
                if(g.getPistasgrupo()==g.getMaxpistasgrupo()){
                    throw new ExcepcionesEdificio("Ya has construido el máximo de pistas en este grupo");
                }
                if(hoteles<2){
                    throw new ExcepcionesEdificio("Necesitas dos hoteles en esta casilla para construir una piscina");
                }
                if(jugador.getFortuna() < 1.25*getValor()){
                    throw new ExcepcionesEdificio(String.format("%s no tiene suficiente dinero para construir una pista en %s\n",jugador.getNombre(),getNombre()));
                }
                break;
        }
    }

    public void puedeConstruirGeneral(Jugador jugador) throws ExcepcionesEdificio{
        //el jugadorturno es el dueño
        if(!jugador.getNombre().equals(getDuenho().getNombre())){
            throw new ExcepcionesEdificio(String.format("%s no es dueño de la casilla\n",jugador.getNombre()));
        }
        //todos los solares del grupo tienen el mismo dueño o cayo mas de dos veces
        int c=1;
        for (Casilla casillaGrupo : jugador.getAvatar().getTablero().getColeccionCasillas()) {
            if(casillaGrupo instanceof Solar) {
                Solar s = (Solar) casillaGrupo;
                if (s.getGrupo().equals(grupo)) {
                    if (!s.getDuenho().getNombre().equals(getDuenho().getNombre())) {
                        c = 0;
                    }
                }
            }
        }
        if(jugador.getAvatar().getContadorCasillas().get(getNumero())<=2 && c==0){
            throw new ExcepcionesEdificio(String.format("%s no es dueño del grupo ni ha pasado más de dos veces por la casilla %s, no puede edificar\n",jugador.getNombre(),getNombre()));
        }
    }

    public void edificar(String tipo, Jugador jugadorTurno, Tablero tablero) throws IllegalArgumentException, ExcepcionesEdificio {
        Solar casilla = this;
        casilla.puedeConstruir(jugadorTurno, tipo, tablero.getGrupos());

        Grupo grupo = null;
        for (Grupo grup : tablero.getGrupos()) {
            if (grup.getColor().equals(casilla.getGrupo())) {
                grupo = grup;
                break;
            }
        }

        switch (tipo) {
            case "casa":
                Edificio casa = new Casa(casilla, jugadorTurno);
                jugadorTurno.setFortuna(casa.getCoste(), "resta");
                jugadorTurno.setDineroInvertido(casa.getCoste());
                casilla.getEdificiosEnCasilla().add(casa);
                jugadorTurno.getEdificios().add(casa);
                casilla.setCasas(casilla.getCasas() + 1);
                grupo.setCasasgrupo(grupo.getCasasgrupo() + 1);
                casilla.setValorCompra(casilla.getValorCompra() - casa.getCoste());
                Juego.c.escribir(String.format("%s ha edificado una casa en %s por %s euros. Su fortuna actual es: %d euros.\n", jugadorTurno.getNombre(), casilla.getNombre(), casa.getCoste(), jugadorTurno.getFortuna()));
                break;
            case "hotel":
                Edificio hotel = new Hotel(casilla, jugadorTurno);
                jugadorTurno.setFortuna(hotel.getCoste(), "resta");
                jugadorTurno.setDineroInvertido(hotel.getCoste());
                casilla.getEdificiosEnCasilla().add(hotel);
                jugadorTurno.getEdificios().add(hotel);
                casilla.setHoteles(casilla.getHoteles() + 1);
                casilla.setCasas(0);
                grupo.setHotelesgrupo(grupo.getHotelesgrupo() + 1);
                grupo.setCasasgrupo(grupo.getCasasgrupo() - 4);
                ArrayList<Edificio> edificiosAux = new ArrayList<Edificio>();
                ArrayList<Edificio> edificiosAuxCasilla = new ArrayList<Edificio>();
                for (Edificio edificio : jugadorTurno.getEdificios()) {
                    if (!edificio.getTipo().equals("casa") || !casilla.getNombre().equals(edificio.getCasilla().getNombre())) {
                        edificiosAux.add(edificio);
                    }
                }
                jugadorTurno.setEdificios(edificiosAux);
                for (Edificio edificio : casilla.getEdificiosEnCasilla()) {
                    if (!edificio.getTipo().equals("casa")) {
                        edificiosAuxCasilla.add(edificio);
                    }
                }
                casilla.setEdificiosEnCasilla(edificiosAuxCasilla);
                casilla.setValorCompra(casilla.getValorCompra() - hotel.getCoste());
                Juego.c.escribir(String.format("%s ha edificado un hotel en %s por %s euros. Su fortuna actual es: %d euros.\n", jugadorTurno.getNombre(), casilla.getNombre(), hotel.getCoste(), jugadorTurno.getFortuna()));
                break;
            case "piscina":
                Edificio piscina = new Piscina(casilla, jugadorTurno);
                jugadorTurno.setFortuna(piscina.getCoste(), "resta");
                jugadorTurno.setDineroInvertido(piscina.getCoste());
                casilla.getEdificiosEnCasilla().add(piscina);
                jugadorTurno.getEdificios().add(piscina);
                casilla.setPiscinas(casilla.getPiscinas() + 1);
                grupo.setPiscinasgrupo(grupo.getPiscinasgrupo() + 1);
                casilla.setValorCompra(casilla.getValorCompra() - piscina.getCoste());
                Juego.c.escribir(String.format("%s ha edificado una piscina en %s por %s euros. Su fortuna actual es: %d euros.\n", jugadorTurno.getNombre(), casilla.getNombre(), piscina.getCoste(), jugadorTurno.getFortuna()));
                break;
            case "pista":
                Edificio pista = new Pista(casilla, jugadorTurno);
                jugadorTurno.setFortuna(pista.getCoste(), "resta");
                jugadorTurno.setDineroInvertido(pista.getCoste());
                casilla.getEdificiosEnCasilla().add(pista);
                jugadorTurno.getEdificios().add(pista);
                casilla.setPistas(casilla.getPistas() + 1);
                grupo.setPistasgrupo(grupo.getPistasgrupo() + 1);
                casilla.setValorCompra(casilla.getValorCompra() - pista.getCoste());
                Juego.c.escribir(String.format("%s ha edificado una pista en %s por %s euros. Su fortuna actual es: %d euros.\n", jugadorTurno.getNombre(), casilla.getNombre(), pista.getCoste(), jugadorTurno.getFortuna()));
                break;
            default:
                throw new IllegalArgumentException("Tipo de construcción incorrecto");
        }
    }


    @Override
    public String toString() {
        return String.format("""
                        {
                                tipo: %s
                                grupo: %s
                                propietario: %s
                                valor: %d
                                alquiler: %d
                                jugadores: %s
                                valor hotel: %d
                                valor casa: %d
                                valor piscina: %d
                                valor pista de deporte: %d
                                alquiler una casa: %d
                                alquiler dos casas: %d
                                alquiler tres casas: %d
                                alquiler cuatro casas: %d
                                alquiler hotel: %d
                                alquiler piscina: %d
                                alquiler pista de deporte: %d
                                edificios: %s
                        }
                        """.formatted(getTipo(), getGrupo(), getDuenho().getNombre(), getValor(), getAlquiler(), stringJugadoresEnCasilla(),(int) (getValor()*0.6),(int)(getValor()*0.6),(int)(getValor()*0.40),(int)(getValor()*1.25),(getAlquiler()*5),(getAlquiler()*15),getAlquiler()*35,getAlquiler()*50,getAlquiler()*70,getAlquiler()*25,getAlquiler()*25,arrayEdificiosCasilla()));

    }
}
