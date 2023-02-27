package com.ue.insw.proyecto.exercises.json;

import com.google.gson.Gson;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {

        String ruta = "src/main/java/com/ue/insw/proyecto/exercises/json/books.json";
        // Create a File object for the JSON file
        File file = new File(ruta);
        Gson gson = new Gson();

        // Check if the file exists and if we have permission to read it
        if (file.exists() && file.canRead()) {
            try {
                // Read the JSON file into a string
                Reader reader = Files.newBufferedReader(Paths.get(ruta));

                // Convert the JSON string to a Java object
                Libro[] obj = gson.fromJson(reader, Libro[].class);
                // Print the data from the Java object
                int contador=0;
                for (Libro dato : obj) {
                    System.out.println("ID : " + dato.getbookID());
                    System.out.println("Titulo : " + dato.getTitle());
                    System.out.println("Autores : " + dato.getAuthors());
                    System.out.println("Publicacion : " + dato.getPublication_date());
                    System.out.println("Code : " + dato.getLanguageCode());
                    System.out.println("Páginas : " + dato.getNumPages() + "\n");
                    contador++;
                }
                long startTime = System.nanoTime();
                long endTime = System.nanoTime();
                System.out.println("Duración: " + (endTime-startTime)/1e6 + " ms");
                System.out.println("LIBROS---> "+contador);
            } catch (IOException e) {
                // Handle the IOException
            }
        } else {
            System.out.println("Error: Este programa no ha hecho nada");
            // Handle the case where the file does not exist or cannot be read
        }
    }
}