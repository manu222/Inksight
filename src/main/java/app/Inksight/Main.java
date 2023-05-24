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
        //String ruta = "src/main/java/app/Inksight/books.json";
        // Create a File object for the JSON file
        //File file = new File(ruta);
        //Gson gson = new Gson();
        //Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        //JsonParser parser = new JsonParser();

        menuDBLibro();



    }
}