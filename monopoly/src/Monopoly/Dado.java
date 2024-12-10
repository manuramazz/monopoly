package Monopoly;

import java.util.Scanner;

public class Dado {
    private int dado1, dado2, contador_dobles, contador_coche;
    private boolean carcel,puedeTirar;

    public int getContador_coche() {
        return contador_coche;
    }

    public void setContador_coche(int contador_coche) {
        this.contador_coche = contador_coche;
    }

    public int getDado1() {
        return dado1;
    }
    public int sumaDados(){
        return dado2 + dado1;
    }

    public int getDado2() {
        return dado2;
    }

    public Dado(){
        dado1 = 0;
        dado2 = 0;
        contador_dobles = 0;
        carcel = false;
        puedeTirar = true;
    }

    public int lanzarDados(){
        int dados = tirada(0);
        if (comp_dobles() == 1){
            contador_dobles++;

            if (contador_dobles == 3){
                carcel = true;
            }
        }
        else{
            puedeTirar = false;
        }
        return dados;
    }

    public int comp_dobles(){
        if(dado1==dado2){
            return 1;
        }
        return 0;
    }
    //devuelve 1 si saco dobles y 0 si no saco dobles

    //Tirada en la que puedo decidir el valor de los dados
    public int tirada(int i){
        Scanner lectura = new Scanner(System.in);
        System.out.print("Valores de dados: ");
        dado1 = lectura.nextInt();
        dado2 = lectura.nextInt();
        return dado1+dado2;
    }
    //Tirada con dados aleatorios
    public int tirada(){
        dado1 = (int)(1+Math.random()*6);
        dado2 = (int)(1+Math.random()*6);
        System.out.println("Dado 1: " + dado1 + " Dado 2: " + dado2);
        return dado1+dado2;
    }
    public boolean isCarcel() {
        return carcel;
    }

    public void setCarcel(boolean carcel) {
        this.carcel = carcel;
    }

    public int lanzarDadosCarcel(){
        int dados = tirada(1);
        if(comp_dobles()==1){
            return 1; //se sacaron dobles
        }
        return 0; //no se sacaron
    }
}
