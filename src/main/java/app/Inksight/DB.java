package app.Inksight;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DB {
    private Libro libro;

    final private Libro libroError;
    static final String ruta = "src/main/java/app/Inksight/books.json";
    static final String rutaData = "src/main/data";
    static final String rutaUsers = rutaData + "/Users";
    Gson gson = new Gson();

    public DB() {
        this.libro = libro;
        this.libroError= new Libro(-1,"error","error",-1,"error","error");
    }

    public static void addLibro() {

        Scanner sc = new Scanner(System.in);
        File file = new File(ruta);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        int id;
        System.out.print("Titulo:");
        String titulo = sc.nextLine();

        System.out.print("authors:");
        String autores = sc.nextLine();

        System.out.print("Numero Hojas:");
        int pages = sc.nextInt();
        sc.nextLine();

        System.out.print("Fecha publicacion dd/mm/aaaa:");
        String date = sc.nextLine();

        System.out.print("Codigo xx-xx:");
        String code = sc.nextLine();

        if (file.exists() && file.canRead() && file.canWrite()) {
            try {
                // Read the JSON file into a string
                Reader reader = Files.newBufferedReader(Paths.get(ruta));
                Type listType = new TypeToken<List<Libro>>() {}.getType();
                List<Libro> Libros = gson.fromJson(new FileReader(ruta), listType);

                // Convert the JSON string to a Java object
                Libro[] obj = gson.fromJson(reader, Libro[].class);
                // Print the data from the Java object
                int contador = 0;
                for (Libro dato : obj) {
                    contador++;
                }
                id = contador;
                Libro libro = new Libro(id, titulo, autores, pages, date, code);
                Libros.add(libro);


                FileWriter writer = new FileWriter(ruta);
                gson.toJson(Libros, writer);
                writer.close();

            } catch (IOException e) {
                // Handle the IOException
            }
        } else {
            System.out.println("Error: Este programa no ha hecho nada");
            // Handle the case where the file does not exist or cannot be read
        }

    }

    public static void updateLibro() throws IOException {
        Scanner sc = new Scanner(System.in);
        File file = new File(ruta);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.print("Introduce la id del libro a modificar:");
        int id = sc.nextInt();

        if (file.exists() && file.canRead() && file.canWrite()) {
            Reader reader = Files.newBufferedReader(Paths.get(ruta));
            try {
                Type listType = new TypeToken<List<Libro>>() {}.getType();
                List<Libro> libros = gson.fromJson(new FileReader(ruta), listType);

                System.out.println("Que quieres editar");
                System.out.println("1) ID");
                System.out.println("2) Titulo");
                System.out.println("3) Autores");
                System.out.println("4) Pages");
                System.out.println("5) Date");
                System.out.println("6) Lang code");
                int opcion = sc.nextInt();

                switch (opcion) {
                    case 1:
                        System.out.println("introduce nueva ID:");
                        int newId = sc.nextInt();
                        for (Libro libro : libros) {
                            if (libro.getbookID()==id){
                                libro.setID(newId);
                                break;
                            }
                        }
                        break;
                    case 2:
                        System.out.println("introduce nuevo titulo:");
                        String newTitle = sc.nextLine();
                        for (Libro libro : libros) {
                            if (libro.getbookID()==id){
                                libro.setTitle(newTitle);
                                break;
                            }
                        }
                        break;
                    case 3:
                        System.out.println("introduce autor/es:");
                        String newAuthors = sc.nextLine();
                        for (Libro libro : libros) {
                            if (libro.getbookID()==id){
                                libro.setAuthors(newAuthors);
                                break;
                            }
                        }
                    case 4:
                        System.out.println("introduce paginas del libro:");
                        int newPages = sc.nextInt();
                        for (Libro libro : libros) {
                            if (libro.getbookID()==id){
                                libro.setNumPages(newPages);
                                break;
                            }
                        }
                    case 5:
                        System.out.println("introduce fecha de publicación:");
                        String newFecha = sc.nextLine();
                        for (Libro libro : libros) {
                            if (libro.getbookID()==id){
                                libro.setPublication_date(newFecha);
                                break;
                            }
                        }
                    case 6:
                        System.out.println("introduce codigo de idioma Xx-Xx:");
                        String newCode = sc.nextLine();
                        for (Libro libro : libros) {
                            if (libro.getbookID()==id){
                                libro.setLanguageCode(newCode);
                                break;
                            }
                        }

                    default:
                        break;
                }

                FileWriter writer = new FileWriter(ruta);
                gson.toJson(libros, writer);
                writer.close();

            } catch (IOException e) {
                // Handle the IOException
            }

        } else {
            System.out.println("Error: Este programa no ha hecho nada");
        }
    }

    public static void eliminarLibro() throws IOException {

        Scanner sc = new Scanner(System.in);
        File file = new File(ruta);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.print("Introduce la id del libro a eliminar:");
        int id = sc.nextInt();

        if (file.exists() && file.canRead() && file.canWrite()) {
            Reader reader = Files.newBufferedReader(Paths.get(ruta));
            try {
                Type listType = new TypeToken<List<Libro>>() {}.getType();
                List<Libro> libros = gson.fromJson(new FileReader(ruta), listType);

                for (Libro libro : libros) {
                    if (libro.getbookID() == id) {
                        libros.remove(libro);
                        break;
                    }
                }

                String listaUpdate = gson.toJson(libros);
                FileWriter writer = new FileWriter(ruta);
                writer.write(listaUpdate);
                writer.close();

            }catch (IOException e) {
                // Handle the IOException
            }

        } else {
            System.out.println("Error: Este programa no ha hecho nada");
        }
    }

    public Libro buscarLibro(String query) throws IOException {
        Gson gson = new Gson();
        query = query.toLowerCase(); // convertimos la consulta a minúsculas
        Type listType = new TypeToken<List<Libro>>() {}.getType();
        List<Libro> libros = gson.fromJson(new FileReader(ruta), listType);
        List<Libro> queryReturn = new ArrayList<>();

        System.out.println("Resultados de la búsqueda para '" + query + "':");
        for (Libro libro : libros) {
            // convertimos el título del libro a minúsculas para hacer la comparación
            if (Integer.toString(libro.getbookID()).equals(query) || libro.getTitle().toLowerCase().matches(".*" + query + ".*")) {
                return libro;
            }
        }
        return this.libroError;
    }
    public List<Libro> buscarLibros(String query) throws IOException {
        Gson gson = new Gson();
        query = query.toLowerCase(); // convertimos la consulta a minúsculas
        Type listType = new TypeToken<List<Libro>>() {}.getType();
        List<Libro> libros = gson.fromJson(new FileReader(ruta), listType);
        List<Libro> queryReturn = new ArrayList<>();

        System.out.println("Resultados de la búsqueda para '" + query + "':");
        for (Libro libro : libros) {
            // convertimos el título del libro a minúsculas para hacer la comparación
            if (Integer.toString(libro.getbookID()).equals(query) || libro.getTitle().toLowerCase().matches(".*" + query + ".*")) {
                queryReturn.add(libro);
            }
        }
        return queryReturn;
    }
    public Libro buscarUnLibro(String query) throws IOException {
        return buscarLibros(query).get(0);
    }
    public static void leerLibros() throws IOException {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Libro>>() {}.getType();
        List<Libro> libros = gson.fromJson(new FileReader(ruta), listType);
        int contador = 0;
        for (Libro libro : libros) {
            System.out.println("ID: " + libro.getbookID());
            System.out.println("Título: " + libro.getTitle());
            System.out.println("Autores: " + libro.getAuthors());
            System.out.println("Número de páginas: " + libro.getNumPages());
            System.out.println("Fecha de publicación: " + libro.getPublication_date());
            System.out.println("Código de idioma: " + libro.getLanguageCode());
            System.out.println();
            contador++;
        }
        System.out.println("Numero de libros: "+contador+ "\n");
    }
    public static String leerArchivo(String rutaArchivo) throws IOException {
        Path ruta = Paths.get(rutaArchivo);
        byte[] contenidoBytes = Files.readAllBytes(ruta);
        return new String(contenidoBytes);
    }

    public Persona PersonaCast(Persona p ){
        if(p instanceof Admin){
            return(Admin)p;
        }
        if(p instanceof Moderador){
            return(Moderador)p;
        }
        else return (Usuario)p;
    }
    public void createPersona() {

        Scanner sc = new Scanner(System.in);

        // Comprobar si la carpeta data existe, si no existe, crearla
        File dataDir = new File(rutaData);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }

        // Comprobar si la carpeta Users existe, si no existe, crearla
        File usersDir = new File(rutaUsers);
        if (!usersDir.exists()) {
            usersDir.mkdir();
        }

        System.out.println("Usuario a crear:");
        String user = sc.nextLine();
        File userDir = new File(rutaUsers + "/" + user);

        // Comprobar si el usuario ya existe
        if (userDir.exists()) {
            System.out.println("El usuario ya existe");
        } else {
            // Si el usuario no existe, crear su carpeta y el archivo "nombreUserData.json"
            userDir.mkdir();
            File userJson = new File(userDir, user + "Data.json");

            Gson gson = new GsonBuilder().setPrettyPrinting().create();


            System.out.print("Nombre:");
            String fn = sc.nextLine();

            System.out.print("Apellido:");
            String ln = sc.nextLine();

            System.out.print("ciudad:");
            String ciudad = sc.next();
            sc.nextLine();

            Stats Stats=new Stats();
            Persona newUser = new Usuario(fn,ln,ciudad,false,0,Stats);
            
            try {
                userJson.createNewFile();
                FileWriter fw = new FileWriter(userJson);
                gson.toJson(newUser,fw);
                fw.close();
                try {
                    String contenido = leerArchivo(String.valueOf(userJson));
                    System.out.println(contenido);
                    Usuario personaObj = gson.fromJson(contenido, Usuario.class);
                    System.out.println(personaObj.getFirst_name());
                    System.out.println(personaObj.getLast_name());
                    System.out.println(personaObj.getLocation());
                    System.out.println(personaObj.getnAmigos());
                    System.out.println(personaObj.getListaReviews());


                } catch (IOException e) {
                    System.out.println("Ocurrió un error al leer el archivo: " + e.getMessage());
                }
                } catch (IOException e) {
                System.out.println("Error al crear el archivo del usuario");
            }

        }
    }
}

