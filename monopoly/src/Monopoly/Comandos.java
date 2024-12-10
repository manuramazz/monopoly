package Monopoly;

public interface Comandos {
    void comandoTratos(String[] comando) throws ExcepcionesAccion, ExcepcionesCasilla, IllegalArgumentException;
    void comandoEdificar(String tipo)  throws ExcepcionesEdificio;
    void comandoVenderEdificio(String tipo, String casilla, String numero) throws ExcepcionesCasilla, ExcepcionesAccion, ExcepcionesDinero;
    boolean comandoBancarrota() throws ExcepcionesAccion;
    void comandoHipotecar(String nombreCasilla) throws ExcepcionesCasilla;
    void comandoDesHipotecar(String nombreCasilla) throws ExcepcionesDinero, ExcepcionesCasilla;
    void comandoCrearJugador(String[] comando) throws ExcepcionesAccion;
    void comandoJugador();
    void comandoListarTratos();
    void comandoEliminarTrato(String comando);
    void comandoAceptarTrato(String comando);
    void comandoListarEdificios();
    void comandoListarJugadores();
    void comandoListarEdificiosGrupo(String grupo);
    void comandoListarAvatares();
    void comandoListarEnVenta() throws ExcepcionesCasilla;
    void comandoDescribirJugador(String comando) throws ExcepcionesElementosJuego;
    void comandoDescribirAvatar(String comando) throws ExcepcionesElementosJuego;
    void comandoDescribirCasilla(String comando);
    void comandoEmpezarPartida();
    void comandoSalircarcel() throws ExcepcionesAccion, ExcepcionesDinero;
    void comandoLanzarDadosMoverse() throws ExcepcionesAccion, ExcepcionesDinero;
    void comandoComprar(String comando) throws ExcepcionesCasilla;
    void comandoCambiarModo();
    void comandoVerTablero();
    void comandoAcabarTurno() throws ExcepcionesAccion;
    void comandoEstadisticasJugador(String comando);
    void comandoEstadisticasJuego();
    void comandoHelp();










}
