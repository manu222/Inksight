package app.Inksight;
import com.google.gson.*;
import java.io.*;
import java.nio.channels.Pipe;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static app.Inksight.DB.*;

public class Main {
    public static void main(String[] args) throws IOException {

        //String ruta = "src/main/java/app/Inksight/books.json";
        // Create a File object for the JSON file
        //File file = new File(ruta);
        //Gson gson = new Gson();
        //Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        //JsonParser parser = new JsonParser();
        DB db= new DB();

        Scanner sc = new Scanner(System.in);
        int opcion;

        System.out.println("¿Qué acción deseas realizar?");
        System.out.println("1) Añadir un libro");
        System.out.println("2) Actualizar un libro");
        System.out.println("3) Eliminar un libro");
        System.out.println("4) Buscar un libro por título");
        System.out.println("5) Salir");
        opcion = sc.nextInt();

        switch (opcion) {
            case 1:
                addLibro();
                break;
            case 2:
                updateLibro();
                break;
            case 3:
                eliminarLibro();
                break;
            case 4:

                String query = "Harry potter";
                for (Libro libro : db.buscarLibros(query)) {
                    System.out.println("ID: " + libro.getbookID());
                    System.out.println("Título: " + libro.getTitle());
                    System.out.println("Autores: " + libro.getAuthors());
                    System.out.println("Número de páginas: " + libro.getNumPages());
                    System.out.println("Fecha de publicación: " + libro.getPublication_date());
                    System.out.println("Código de idioma: " + libro.getLanguageCode());
                    System.out.println();
                }
                //System.out.println(db.buscarLibro(query));
                break;
            case 5:
                System.out.println("¡Hasta luego!");
                break;
            default:
                System.out.println("Opción no válida, por favor intenta de nuevo.");
                break;

        }while (opcion != 5);



    }
}
