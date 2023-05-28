package app.Inksight;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Usuario extends Persona {
    HashSet<String> listaAmigos;
    List<Review> listaReviews;
    Set<Libro> listaRecomendados;
    private List<Challenge> desafios;
    Stats stats;
    private Date lastChallenge;
    private Date lastLogin;
    public GestionColecciones gestionColecciones;

    public Usuario(String nombreUser, String correo, String pass, String first_name, String last_name, String location,
                   HashSet<String> listaAmigos) {
        super(nombreUser, correo, pass, first_name, last_name, location);
        this.desafios = new LinkedList<>();
        this.listaAmigos = new HashSet<>();
        this.stats = new Stats();
        gestionColecciones = new GestionColecciones();
        lastChallenge = new Date(System.currentTimeMillis());
        listaRecomendados = new HashSet<>();
        authLevel = "user";
    }

    public Usuario(String nombreUser, String correo, String pass, String first_name, String last_name, String location,
                   boolean online, int followers, Stats stats, HashSet<String> listaAmigos, List<Challenge> desafios) {
        super(nombreUser, correo, pass, first_name, last_name, location, online, followers, stats);
        this.desafios = desafios;
        this.listaAmigos = new HashSet<>();
        this.stats = stats;
        gestionColecciones = new GestionColecciones();
        lastChallenge = new Date(System.currentTimeMillis());
        listaRecomendados = new HashSet<>();
        authLevel = "user";
    }

    public void serializeToJson() {
        // Comprobar si la carpeta data existe, si no existe, crearla
        File dataDir = new File(DB.rutaData);
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }

        // Comprobar si la carpeta Users existe, si no existe, crearla
        File usersDir = new File(DB.rutaUsers);
        if (!usersDir.exists()) {
            usersDir.mkdir();
        }
        File userDir = new File(DB.rutaUsers + "/" + this.getNombreUser());
        if (userDir.exists()) {
            File userJson = new File(userDir, this.getNombreUser() + "Data.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try {
                FileWriter fw = new FileWriter(userJson, false);
                gson.toJson(this, fw);
                System.out.println(userJson);
                fw.close();
            } catch (IOException e) {
                System.out.println("Error al crear el archivo del usuario");
            }
        }
    }

    public Stats getStats() {
        return this.stats;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    // <desafios>
    public Challenge makeChallenge(String title, String description, int target, String type, float reward) {
        Challenge challenge = new Challenge("" + (desafios.size() + 1), title, description, target, type, reward, false,
                0);
        this.desafios.add(challenge);
        setLastChallenge(new Date(System.currentTimeMillis()));
        return challenge;
    }

    public Challenge makeChallenge(String title, String description, int target, String type, float reward,
                                   boolean timeSensitive, int timeLimit) {
        Challenge challenge = new Challenge("" + (desafios.size() + 1), title, description, target, type, reward,
                timeSensitive, timeLimit);
        this.desafios.add(challenge);
        setLastChallenge(new Date(System.currentTimeMillis()));
        return challenge;
    }

    public void addChallenge(Challenge challenge) {
        this.desafios.add(challenge);
    }

    public void markAsCompleted(String challengeID) {
        for (Challenge challenge : desafios) {
            if (challenge.getChallengeID().equals(challengeID)) {
                challenge.markAsCompleted();
                this.stats.addXp(challenge.getReward());
            }
        }
    }

    public List<Challenge> getDesafios() {
        return this.desafios;
    }

    public Date getLastChallenge() {
        return lastChallenge;
    }
    public void setLastChallenge(Date now) {
        this.lastChallenge = now;
    }
    // </desafios>

    // <recomendaciones>
    public void makeRecomendacion (){//Recibes como recomendacion la mejor review de un amigo al azar, y se añade esa review a tu lista de recomendados
        Random rand = new Random();
        HashSet<String> listaAmigos = getListaAmigos();

        int numeroAmigo = rand.nextInt(getListaAmigos().size() + 1);
        DB db = new DB();
        Iterator<String> it = listaAmigos.iterator();
        String actual = "";
        if (listaAmigos.size() != 0) {
            int contador = 0;
            while (it.hasNext() && contador < numeroAmigo) {
                actual = it.next();
            }

            Usuario  usuario = (Usuario)db.buscarUser(actual);
            Review recomendado = this.sortByPuntuacion(getListaReviews());
            this.listaRecomendados.add(recomendado.getLibro());
            System.out.println("Esta es una review recomendada por tu amigo");
            System.out.println("Titulo:" + recomendado.getLibro().getTitle());
            System.out.println("Review:" + recomendado.getDescripcion());
            System.out.println("Puntuacion:" + recomendado.getPuntuacion());


        } else {
            System.out.println("El usuario no tiene amigos, por lo que no recibe recomendaciones");

        }
    }
    // </recomendaciones>

    // <amigos>
    public boolean addAmigo(String nombre) {//añade amigo a lista de amigos


        DB db = new DB();
        Persona user =  db.buscarUser(nombre);
        if(user  != null){
            this.listaAmigos.add(user.getNombreUser());

            return true;
        }
        else{

            return false;
        }

    }

    public static Review sortByPuntuacion(List<Review> reviewsList) {
        int n = reviewsList.size();
        Review temp;
        // Bubble sort algorithm
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (reviewsList.get(j).getPuntuacion() < reviewsList.get(j + 1).getPuntuacion()) {
                    // swap reviews
                    temp = reviewsList.get(j);
                    reviewsList.set(j, reviewsList.get(j + 1));
                    reviewsList.set(j + 1, temp);
                }
            }
        }
        return reviewsList.get(0);
    }

    public boolean addReview(Scanner sc) throws IOException {
        List<Review> reviews = this.getListaReviews();
        if (reviews.size() == 0) {
            reviews.add(Review.hacerReview(sc));
            return reviews.add(Review.hacerReview(sc));
        } else {
            Review primerReviewVacio = null;
            for (Review review : reviews) {
                if (review == null) {
                    reviews.add(Review.hacerReview(sc));
                    return reviews.add(Review.hacerReview(sc));

                }
            }
        }
        return false;
    }



    public HashSet<String> getListaAmigos() {
        if (listaAmigos.size() <= 0) {
            System.out.println("No hay amigos");
            return null;
        }
        HashSet<String> listaAmigos = this.getListaAmigos();
        Iterator<String> it = listaAmigos.iterator();
        int n = 1;
        while(it.hasNext()){
            System.out.println("Amigo n:" + n + " Username: " + it.next());
        }
        return listaAmigos;
    }

    public int getnAmigos() {
        try {
            return listaAmigos.size();
        } catch (Exception e) {
            return 0;
        }
    }
    // </amigos>

    // <reviews>
    public List<Review> getListaReviews() {
        return listaReviews;
    }

    public boolean borrarAmigo(String amigo){
        DB db = new DB();
        Persona user =  db.buscarUser(amigo);
        if(user != null){
            HashSet<String> listaAmigos = this.getListaAmigos();
            Iterator<String> it = listaAmigos.iterator();
            while(it.hasNext()){
                String str = it.next();
                if(str.equals(amigo)){
                    it.remove();
                }
            }
            return true;
        }
        return false;
    }



    public String buscarAmigo(String amigo) {//pide string de lo que buscas

        String actual = "";
        Iterator<String> it = this.getListaAmigos().iterator();

        if (getnAmigos() != 0) {
            int contador = 0;
            while (it.hasNext() && contador < this.getnAmigos()) {
                if(it.next() == amigo){
                    actual = it.next();
                }
            }
        }
        return actual;

    }

    public void eliminarReview(Review review) {
        listaReviews.remove(review);
    }
    // </reviews>

    // <libros>
    public int libroTerminado(Libro libro) {
        // 1. actualizar stats
        stats.addBook(libro);
        // 2. mover a la lista de libros leídos y eliminar de la lista de leyendo
        try{
            gestionColecciones.moverLibroDeColeccion("leyendo", "leidos", libro.getTitle());
        }catch(Exception e){
            System.out.println("No se ha podido marcar el libro como leído. Intentelo de nuevo más tarde");
        }
        // 3.actualizar desafíos
        int completedCount = 0;
        for (Challenge challenge : desafios) {
            switch (challenge.getType()) {
                case "paginas":
                    //si el progreso añadido completa el desafío, devuelve true y suma 1 al contador
                    completedCount += challenge.addProgress(libro.getNumPages()) ? 1 : 0;
                    break;
                case "libros":
                    completedCount += challenge.addProgress(1) ? 1 : 0;
                    break;
                case "rec":
                    for (Libro l : listaRecomendados) {
                        if (libro.equals(l)) {
                            completedCount += challenge.addProgress(1) ? 1 : 0;
                        }
                    }
                    // se pueden añadir más tipos
            }
        }
        //valor máximo de 3
        return completedCount;
    }

    // </libros>

}