package app.Inksight;
import com.google.gson.*;
import java.io.*;
import java.nio.channels.Pipe;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static app.Inksight.DB.*;
import static app.Inksight.Funciones.menuDBLibro;

public class Main {
    public static void main(String[] args) throws IOException {
        DB db= new DB();
      //  Scanner menu = new Scanner(System.in);
        Interfaz interfaz = new Interfaz();
       // interfaz.menu_Principal();
    //    db.createPersona();
        //menuDBLibro();



    }
}