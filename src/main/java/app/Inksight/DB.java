package app.Inksight;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.*;

/**
 * The type Db.
 */
public class DB {
    private Libro libro;

    /**
     * The Libro error.
     */
    static public Libro libroError = new Libro(-1,"error","error",-1,"error","error");
    /**
     * The Ruta.
     */
    static final String ruta = "src/main/java/app/Inksight/books.json";
    /**
     * The Ruta data.
     */
    static final String rutaData = "src/main/data";
    /**
     * The Ruta users.
     */
    static final String rutaUsers = rutaData + "/Users";
    /**
     * The Gson.
     */
    Gson gson = new Gson();

    /**
     * Instantiates a new Db.
     */
    public DB() {

    }

    /**
     * Prompt add libro.
     *
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public static void promptAddLibro() throws NoSuchAlgorithmException {

        Scanner sc = new Scanner(System.in);
        int id;
        int pages=0;
        System.out.print("Titulo:");
        String titulo = sc.nextLine();

        System.out.print("authors:");
        String autores = sc.nextLine();
        boolean valid= false;
        while (!valid) {
            System.out.print("Numero Hojas:");
            try {
                pages = sc.nextInt();
                valid = pages>0;
            } catch (InputMismatchException e) {

            }
            sc.nextLine();
        }
        valid=false;
        String in="";
        int[] cantDias = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        while (!valid) {
            Interfaz.clearConsole();
            System.out.print("Fecha publicacion dd/mm/aaaa:");
             in = sc.nextLine();
            String[] parts = in.split("/");
            if (parts.length==3){
                try {
                    int[] partsNum = {Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),Integer.parseInt(parts[2])};
                    if (partsNum[0]>0 && partsNum[0]<=31 && partsNum[1]>0 && partsNum[1]<=12 && partsNum[2]>0 && partsNum[0]<=cantDias[partsNum[1]-1]){
                        valid=true;
                    }
                }catch (Exception e){}
            }

        }
        System.out.print("Codigo xx-xx:");
        String code = sc.nextLine();
        addLibro(titulo, autores, pages, in, code);
        System.out.println("Libro Añadido \n");
        Interfaz i = new Interfaz();
        i.menu_Administrador();
          

    }


    /**
     * Add libro libro.
     *
     * @param titulo  the titulo
     * @param autores the autores
     * @param pages   the pages
     * @param date    the date
     * @param code    the code
     * @return the libro
     */
    public static Libro addLibro(String titulo, String autores, int pages, String date, String code){

        File file = new File(ruta);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (file.exists() && file.canRead() && file.canWrite()) {
            try {
                // Read the JSON file into a string
                Reader reader = Files.newBufferedReader(Paths.get(ruta));
                Type listType = new TypeToken<List<Libro>>() {}.getType();
                List<Libro> Libros = gson.fromJson(new FileReader(ruta), listType);

                // Convert the JSON string to a Java object
                Libro[] obj = gson.fromJson(reader, Libro[].class);
                // Print the data from the Java object
                int id = obj.length+1;
                Libro libro = new Libro(id, titulo, autores, pages, date, code);
                Libros.add(libro);


                FileWriter writer = new FileWriter(ruta);
                gson.toJson(Libros, writer);
                writer.close();

            } catch (IOException e) {
                return libroError;
                // Handle the IOException
            }
        } else {
            System.out.println("Error: Este programa no ha hecho nada");
            return libroError;
            // Handle the case where the file does not exist or cannot be read
        }
        return libroError; 
    }

    /**
     * Update libro.
     *
     * @throws IOException the io exception
     */
    public static void updateLibro() throws IOException {
        Scanner sc = new Scanner(System.in);
        File file = new File(ruta);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.print("Introduce la id del libro a modificar:");
        int id = sc.nextInt();

        if (file.exists() && file.canRead() && file.canWrite()) {
            try {
                Type listType = new TypeToken<List<Libro>>() {}.getType();
                List<Libro> libros = gson.fromJson(new FileReader(ruta), listType);
                boolean seguir = true;
                while (seguir) {
                System.out.println("¿Qué quieres editar?");

                System.out.println("1) Título");
                System.out.println("2) Autores");
                System.out.println("3) Páginas");
                System.out.println("4) Fecha");
                System.out.println("5) Código de idioma");
                System.out.println("OTRO) SALIR");
                int opcion = sc.nextInt();

                    switch (opcion) {

                        case 1:
                            System.out.println("Introduce nuevo título:");
                            sc.nextLine();
                            String newTitle = sc.nextLine();
                            for (Libro libro : libros) {
                                if (libro.getbookID() == id) {
                                    libro.setTitle(newTitle);
                                    break;
                                }
                            }
                            break;
                        case 2:
                            System.out.println("Introduce autor/es:");
                            sc.nextLine();
                            String newAuthors = sc.nextLine();
                            for (Libro libro : libros) {
                                if (libro.getbookID() == id) {
                                    libro.setAuthors(newAuthors);
                                    break;
                                }
                            }
                            break;
                        case 3:
                            int newPages = 0;
                            boolean valid= false;
                            while (!valid) {
                                System.out.println("Introduce páginas del libro:");
                                try {
                                    newPages = sc.nextInt();
                                    valid = newPages>0;
                                } catch (InputMismatchException e) {

                                }
                                sc.nextLine();
                            }
                            for (Libro libro : libros) {
                                if (libro.getbookID() == id) {
                                    libro.setNumPages(newPages);
                                    break;
                                }
                            }
                            break;
                        case 4:
                            String newFecha = "";
                            valid=false;

                            int[] cantDias = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
                            while (!valid) {
                                Interfaz.clearConsole();
                                System.out.print("Fecha publicacion dd/mm/aaaa:");
                                newFecha = sc.nextLine();
                                String[] parts = newFecha.split("/");
                                if (parts.length==3){
                                    try {
                                        int[] partsNum = {Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),Integer.parseInt(parts[2])};
                                        if (partsNum[0]>0 && partsNum[0]<=31 && partsNum[1]>0 && partsNum[1]<=12 && partsNum[2]>0 && partsNum[0]<=cantDias[partsNum[1]-1]){
                                            valid=true;
                                        }
                                    }catch (Exception e){}
                                }

                            }
                            for (Libro libro : libros) {
                                if (libro.getbookID() == id) {
                                    libro.setPublication_date(newFecha);
                                    break;
                                }
                            }
                            break;
                        case 5:
                            System.out.println("Introduce código de idioma (XX-XX):");
                            sc.nextLine();
                            String newCode = sc.nextLine();
                            for (Libro libro : libros) {
                                if (libro.getbookID() == id) {
                                    libro.setLanguageCode(newCode);
                                    break;
                                }
                            }
                            break;
                        default:
                            seguir=false;
                            break;
                    }

                    FileWriter writer = new FileWriter(ruta);
                    gson.toJson(libros, writer);
                    writer.close();
                }
            } catch (IOException e) {
                // Handle the IOException
            }
        } else {
            System.out.println("Error: No se pudo leer/escribir en el archivo.");
        }



    }


    /**
     * Eliminar libro.
     *
     * @throws IOException the io exception
     */
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

    /**
     * Buscar libro libro.
     *
     * @param query the query
     * @return the libro
     * @throws IOException the io exception
     */
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

    /**
     * Buscar un libro libro.
     *
     * @param query the query
     * @return the libro
     * @throws IOException the io exception
     */
    public Libro buscarUnLibro(String query) throws IOException {
        List<Libro> res = buscarLibros(query);
        return (res.equals(null)||res.size()==0)?null:res.get(0);
    }

    /**
     * Leer libros.
     *
     * @throws IOException the io exception
     */
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

    /**
     * Leer archivo string.
     *
     * @param rutaArchivo the ruta archivo
     * @return the string
     * @throws IOException the io exception
     */
    public static String leerArchivo(String rutaArchivo) throws IOException {
        Path ruta = Paths.get(rutaArchivo);
        byte[] contenidoBytes = Files.readAllBytes(ruta);
        return new String(contenidoBytes);
    }

    /**
     * Persona cast persona.
     *
     * @param u the u
     * @return the persona
     */
    public static Persona personaCast(Usuario u){
        if(u==null)return null;
        if(u.getAuthLevel().equalsIgnoreCase("admin")){
            return new Admin(u.getNombreUser(),u.getCorreo(),u.getPass(),u.getFirst_name(),u.getLast_name(),u.getLocation());
        }
        if(u.getAuthLevel().equalsIgnoreCase("moderador")){
            return new Moderador(u.getNombreUser(),u.getCorreo(),u.getPass(),u.getFirst_name(),u.getLast_name(),u.getLocation(),u.isBanned());
        }
        else return u;
    }

    /**
     * Create persona persona.
     *
     * @param userName the user name
     * @param correo   the correo
     * @param pass     the pass
     * @param type     the type
     * @return the persona
     */
    public Persona createPersona(String userName, String correo, String pass, String type) {


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


        File userDir = new File(rutaUsers + "/" + userName);

        // Comprobar si el usuario ya existe
        if (userDir.exists()) {
            System.out.println("El usuario ya existe");
            return null;
        } else {
            // Si el usuario no existe, crear su carpeta y el archivo "nombreUserData.json"
            userDir.mkdir();
            File userJson = new File(userDir, userName + "Data.json");

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            System.out.print("Nombre:");
            String fn = sc.nextLine();

            System.out.print("Apellido:");
            String ln = sc.nextLine();

            System.out.print("ciudad:");
            String ciudad = sc.next();
            sc.nextLine();

            Stats stats = new Stats();
            HashSet<String> listaAmigos = new HashSet<>();
            List<Challenge> desafios = new LinkedList<>();

            Persona newUser;

            if (type.equalsIgnoreCase("admin")) {
                newUser = new Admin(userName, correo, pass, fn, ln, ciudad);
            } else if (type.equalsIgnoreCase("moderador")) {
                newUser = new Moderador(userName, correo, pass, fn, ln, ciudad,false);
            } else {
                newUser = new Usuario(userName, correo, pass, fn, ln, ciudad, false, 0, stats, listaAmigos, desafios,0);
            }

            try {
                userJson.createNewFile();
                FileWriter fw = new FileWriter(userJson);
                gson.toJson(newUser, fw);
                fw.close();
            } catch (IOException e) {
                System.out.println("Error al crear el archivo del usuario");
            }

            return newUser;

        }

    }

    /**
     * Buscar user usuario.
     *
     * @param userName the user name
     * @return the usuario
     */
    public static Usuario buscarUser(String userName){
        Gson gson = new Gson();

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


        File userDir = new File(rutaUsers + "/" + userName);
        File userData = new File(rutaUsers + "/" + userName + "/" + userName + "Data.json");
        if (userDir.exists()){
            //System.out.println("existe el usuario");
            if (userData.exists()){
             //   System.out.println("tiene datos");
                try {
                 //   System.out.println("contenido:");
                    String contenidoUser = leerArchivo(String.valueOf(userData));
                    // System.out.println(contenidoUser);
                    // Comprobar si el usuario ya existe

                    Usuario user = gson.fromJson(contenidoUser, Usuario.class);
                    System.out.println(user.getFirst_name());
                   // Usuario userConvert= (Usuario)user;
                    //test
                    // System.out.println(userConvert.getDesafios().get(0).getProgress());
                    return user;

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        }
        return null;
    }

    /**
     * Bytes to hex string.
     *
     * @param hash the hash
     * @return the string
     */
    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

