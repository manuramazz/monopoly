package Monopoly;

public class Menu {

    public Menu(){
    }
    public void metodoMenu() {
        boolean seguirPartida = true;
        String comando = help();
        Juego.c.escribir(comando);
        Juego partida = new Juego();
        partida.comandoEmpezarPartida(1);
        //!comando.equals("acabar partida")
        while (seguirPartida) {
            Juego.c.escribir("$> ",0);
            comando = Juego.c.leer();
            String[] partes = comando.split(" ");
            try {
                seguirPartida = partida.hacerTurno(partes);
            }catch (ExcepcionesDinero e) {
                Juego.c.escribir(e.getMessage());
                Juego.c.escribir("El jugador "+ e.getNom_jugador() + " tiene " + e.getFortunaExcepcion() + " euros.");
            }catch (ExcepcionJugadorIncorrecto e) {
                Juego.c.escribir(e.getMessage() + " Esta es la lista de jugadores:");
                partida.comandoListarJugadores();
            }catch (ExcepcionCasillaNoExiste e) {
                Juego.c.escribir(e.getMessage() + " Este es el tablero, busca la casilla que deseas:");
                partida.comandoVerTablero();
            }catch (ExcepcionesAccion | ExcepcionesElementosJuego e) {
                Juego.c.escribir(e.getMessage());
            }catch (IllegalArgumentException e){
                Juego.c.escribir("Comando incorrecto. Estos son los comandos posibles: ");
                partida.comandoHelp();
            }
        }


    }
    private String help() {
        return """
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
                    """;

    }

}
