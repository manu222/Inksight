package app.Inksight;
import com.google.gson.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Main {
    public static void main(String[] args) {

        String ruta = "src/main/java/app/Inksight/books.json";
        // Create a File object for the JSON file
        File file = new File(ruta);
        Gson gson = new Gson();
        Gson gsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        JsonParser parser = new JsonParser();

        try (FileReader reader = new FileReader(ruta)) {
            // Parsear el archivo JSON
            JsonElement rootElement = parser.parse(reader);

            // Obtener el array de objetos
            JsonArray jsonArray = rootElement.getAsJsonArray();

            // Recorrer los objetos del array
            for (JsonElement objElement : jsonArray) {
                // Convertir el valor de bookID a int
                double average_rating = objElement.getAsJsonObject().get("average_rating").getAsDouble();
                // Actualizar el valor de bookID en el objeto
                objElement.getAsJsonObject().addProperty("average_rating", average_rating);
                // Obtener el valor actual de LanguageCode
                JsonElement publisherElement = objElement.getAsJsonObject().get("publisher;;;");
                if (publisherElement != null && publisherElement.isJsonPrimitive()) {
                    String publisher = publisherElement.getAsString().replaceAll(";;;", "");
                    objElement.getAsJsonObject().addProperty("publisher", publisher);
                }
                objElement.getAsJsonObject().remove("publisher;;;");

            }

            // Escribir el archivo JSON con el campo bookID convertido a int
            try (FileWriter writer = new FileWriter(ruta)) {
                gsonBuilder.toJson(jsonArray, writer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

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