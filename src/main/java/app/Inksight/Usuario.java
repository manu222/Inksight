package app.Inksight;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * The type Usuario.
 */
public class Usuario extends Persona {
    /**
     * The Lista amigos.
     */
    HashSet<String> listaAmigos;
    /**
     * The Lista reviews.
     */
    List<Review> listaReviews;
    /**
     * The Lista recomendados.
     */
    Set<Libro> listaRecomendados;
    private List<Challenge> desafios;
    /**
     * The Stats.
     */
    Stats stats;
    private Date lastChallenge;
    private Date lastLogin;
    /**
     * The Gestion colecciones.
     */
    public GestionColecciones gestionColecciones;


    /**
     * Instantiates a new Usuario.
     *
     * @param nombreUser     the nombre user
     * @param correo         the correo
     * @param pass           the pass
     * @param first_name     the first name
     * @param last_name      the last name
     * @param location       the location
     * @param listaAmigos    the lista amigos
     * @param daysUntilUnban the days until unban
     */
    public Usuario(String nombreUser, String correo, String pass, String first_name, String last_name, String location,
                   HashSet<String> listaAmigos,int daysUntilUnban) {
        super(nombreUser, correo, pass, first_name, last_name, location);
        this.desafios = new LinkedList<>();
        this.listaAmigos = new HashSet<>();
        this.daysUntilUnban=daysUntilUnban;
        this.stats = new Stats();
        listaReviews=new ArrayList<>();
        gestionColecciones = new GestionColecciones();
        lastChallenge = new Date(System.currentTimeMillis());
        listaRecomendados = new HashSet<>();
        authLevel = "user";
    }

    /**
     * Instantiates a new Usuario.
     *
     * @param nombreUser     the nombre user
     * @param correo         the correo
     * @param pass           the pass
     * @param first_name     the first name
     * @param last_name      the last name
     * @param location       the location
     * @param online         the online
     * @param followers      the followers
     * @param stats          the stats
     * @param listaAmigos    the lista amigos
     * @param desafios       the desafios
     * @param daysUntilUnban the days until unban
     */
    public Usuario(String nombreUser, String correo, String pass, String first_name, String last_name, String location,
                   boolean online, int followers, Stats stats, HashSet<String> listaAmigos, List<Challenge> desafios,int daysUntilUnban) {
        super(nombreUser, correo, pass, first_name, last_name, location, online, followers, stats);
        this.desafios = desafios;
        this.listaAmigos = new HashSet<>();
        this.daysUntilUnban=daysUntilUnban;
        this.stats = stats;
        listaReviews=new ArrayList<>();
        gestionColecciones = new GestionColecciones();
        lastChallenge = new Date(System.currentTimeMillis());
        listaRecomendados = new HashSet<>();
        authLevel = "user";
    }

    /**
     * Serialize to json.
     */
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
                fw.close();
            } catch (IOException e) {
                System.out.println("Error al crear el archivo del usuario");
            }
        }
    }

    /**
     * Gets stats.
     *
     * @return the stats
     */
    public Stats getStats() {
        return this.stats;
    }

    /**
     * Gets last login.
     *
     * @return the last login
     */
    public Date getLastLogin() {
        return lastLogin;
    }

    /**
     * Sets last login.
     *
     * @param lastLogin the last login
     */
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * Make challenge challenge.
     *
     * @param title       the title
     * @param description the description
     * @param target      the target
     * @param type        the type
     * @param reward      the reward
     * @return the challenge
     */
// <desafios>
    public Challenge makeChallenge(String title, String description, int target, String type, float reward) {
        Challenge challenge = new Challenge("" + (desafios.size() + 1), title, description, target, type, reward, false,
                0);
        this.desafios.add(challenge);
        setLastChallenge(new Date(System.currentTimeMillis()));
        return challenge;
    }

    /**
     * Make challenge challenge.
     *
     * @param title         the title
     * @param description   the description
     * @param target        the target
     * @param type          the type
     * @param reward        the reward
     * @param timeSensitive the time sensitive
     * @param timeLimit     the time limit
     * @return the challenge
     */
    public Challenge makeChallenge(String title, String description, int target, String type, float reward,
                                   boolean timeSensitive, int timeLimit) {
        Challenge challenge = new Challenge("" + (desafios.size() + 1), title, description, target, type, reward,
                timeSensitive, timeLimit);
        this.desafios.add(challenge);
        setLastChallenge(new Date(System.currentTimeMillis()));
        return challenge;
    }

    /**
     * Add challenge.
     *
     * @param challenge the challenge
     */
    public void addChallenge(Challenge challenge) {
        this.desafios.add(challenge);
    }

    /**
     * Mark as completed.
     *
     * @param challengeID the challenge id
     */
    public void markAsCompleted(String challengeID) {
        for (Challenge challenge : desafios) {
            if (challenge.getChallengeID().equals(challengeID)) {
                challenge.markAsCompleted();
                this.stats.addXp(challenge.getReward());
            }
        }
    }

    /**
     * Gets desafios.
     *
     * @return the desafios
     */
    public List<Challenge> getDesafios() {
        return this.desafios;
    }

    /**
     * Gets last challenge.
     *
     * @return the last challenge
     */
    public Date getLastChallenge() {
        return lastChallenge;
    }

    /**
     * Sets last challenge.
     *
     * @param now the now
     */
    public void setLastChallenge(Date now) {
        this.lastChallenge = now;
    }
    // </desafios>

    /**
     * Make recomendacion.
     */
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

    /**
     * Add amigo boolean.
     *
     * @param nombre the nombre
     * @return the boolean
     */
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

    /**
     * Sort by puntuacion review.
     *
     * @param reviewsList the reviews list
     * @return the review
     */
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

    /**
     * Add review boolean.
     *
     * @param sc the sc
     * @return the boolean
     * @throws IOException the io exception
     */
    public boolean addReview(Scanner sc) throws IOException {
        List<Review> reviews = this.getListaReviews();

            Review r = Review.hacerReview(sc);
        if (r != null && !r.getLibro().getTitle().equalsIgnoreCase("error") && reviews.add(r)){
            for(Challenge c : desafios){
                if(c.getType().equals("review")){
                    if(c.addProgress(1)){
                        this.stats.addXp(c.getReward());
                    }
                }
            }
            return  true;
        }else {
            System.out.println("No se encontro el libro");
        }
        return false;
    }


    /**
     * Gets lista amigos.
     *
     * @return the lista amigos
     */
    public HashSet<String> getListaAmigos() {
        return listaAmigos;
    }

    /**
     * Print lista amigos.
     */
    public void printListaAmigos(){
        if (getListaAmigos().size()==0 || listaAmigos==null){
            System.out.println("No tienes amigos");
        }
        for (String s : listaAmigos){
            System.out.println(s);
        }
    }

    /**
     * Gets amigos.
     *
     * @return the amigos
     */
    public int getnAmigos() {
        try {
            return listaAmigos.size();
        } catch (Exception e) {
            return 0;
        }
    }
    // </amigos>

    /**
     * Gets lista reviews.
     *
     * @return the lista reviews
     */
// <reviews>
    public List<Review> getListaReviews() {
        return listaReviews;
    }

    /**
     * Borrar amigo boolean.
     *
     * @param amigo the amigo
     * @return the boolean
     */
    public boolean borrarAmigo(String amigo){
        DB db = new DB();
        Persona user =  db.buscarUser(amigo);
        boolean contains = false;
        if(user != null){
            for(String s : this.getListaAmigos()){
                if(s.equalsIgnoreCase(amigo)){
                    this.listaAmigos.remove(s);
                    contains = true;
                }
            }
            return contains;
        }
        return false;
    }



    /**
     * Buscar amigo string.
     *
     * @param amigo the amigo
     * @return the string
     */
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

    /**
     * Eliminar review.
     *
     * @param review the review
     */
    public void eliminarReview(Review review) {
        listaReviews.remove(review);
    }

    /**
     * Borrar review boolean.
     *
     * @param review the review
     * @return the boolean
     */
    public boolean borrarReview(Review review) {
        return this.listaReviews.remove(review);
    }
    // </reviews>

    /**
     * Libro terminado int.
     *
     * @param libro the libro
     * @return the int
     */
// <libros>
    public int libroTerminado(Libro libro) {
        // 1. actualizar stats
        stats.addBook(libro);
        // 2. mover a la lista de libros leídos y eliminar de la lista de leyendo
        try{
            if (gestionColecciones.obtenerColeccion("leyendo").contiene(libro)) {
                gestionColecciones.moverLibroDeColeccion("leyendo", "leidos", libro.getTitle());
            }else {
                gestionColecciones.obtenerColeccion("leidos").anadir(libro.getTitle());
            }
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
                    if(challenge.isCompleted()){
                                this.stats.addXp(challenge.getReward());
                            }
                    break;
                case "libros":
                    completedCount += challenge.addProgress(1) ? 1 : 0;
                    if(challenge.isCompleted()){
                                this.stats.addXp(challenge.getReward());
                            }
                    break;
                case "accept":
                    for (Libro l : listaRecomendados) {
                        if (libro.equals(l)) {
                            completedCount += challenge.addProgress(1) ? 1 : 0;
                            if(challenge.isCompleted()){
                                this.stats.addXp(challenge.getReward());
                            }
                        }
                    }
                    break;
                    case "book_length":
                    if(libro.getNumPages()>=challenge.getTarget()){
                        challenge.markAsCompleted();
                        completedCount++;
                    }
                    break;
                    // se pueden añadir más tipos
            }
        }
        //valor máximo de 3
        return completedCount;
    }

    /**
     * Set is banned.
     *
     * @param b the b
     */
// </libros>
    public void setIsBanned(boolean b){
        this.isBanned=b;
    }
}