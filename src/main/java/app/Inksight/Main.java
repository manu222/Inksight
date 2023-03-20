package app.Inksight;
import com.google.gson.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Vector;
import java.util.*;



public class Main {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner teclado = new Scanner(System.in);
        PruebaClase Fea = new PruebaClase();
        PruebaClase Jose = new PruebaClase();
        Fea.nombre = "Paula";
        Jose.nombre = "Jose";
        Fea.listaAmigos = new HashSet<PruebaClase>();
        Fea.anyadirAmigo(teclado, Jose);
        Fea.anyadirAmigo(teclado, Jose);


    }
}