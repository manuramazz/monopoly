package Monopoly;

import java.util.Scanner;

public class ConsolaNormal implements Consola{
    @Override
    public String leer(){
        Scanner l = new Scanner(System.in);
        return l.nextLine();
    }
    public int leer(int e){
        Scanner l = new Scanner(System.in);
        return l.nextInt();
    }
    @Override
    public void escribir(String s) {
        System.out.println(s);
    }
    public void escribir(String s,int e) {
        System.out.print(s);
    }
}