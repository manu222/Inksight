package app.Inksight;

import java.util.*;

public class Usuario extends Persona {

    HashSet<Usuario> listaAmigos;
     private String nombreUser;
    private String correo;
    private String pass;
    List<Review> listaReviews;
    Set<Libro> listaRecomendados;
    private List<Challenge> desafios;
    private static Stats stats;
    private Date lastChallenge;
private Date lastLogin;
    public GestionColecciones gestionColecciones;

    public Usuario(String nombreUser,String correo,String pass ,String first_name, String last_name, String location,HashSet<Usuario> listaAmigos){
        super(nombreUser,correo,pass, first_name,last_name,location);
        this.desafios = new LinkedList<>();
        this.listaAmigos=new HashSet<>();
        this.stats = new Stats();
        gestionColecciones=new GestionColecciones();
        lastChallenge = new Date(System.currentTimeMillis());
        listaRecomendados = new HashSet<>();
        authLevel = "user";
    }
    public Usuario(String nombreUser,String correo,String pass ,String first_name, String last_name, String location, boolean online, int followers,Stats stats,HashSet<Usuario> listaAmigos){
        super(nombreUser,correo,pass,first_name,last_name,location,online,followers,stats);
        this.desafios = new LinkedList<>();
        this.listaAmigos=new HashSet<>();
        this.stats = stats;
        gestionColecciones=new GestionColecciones();
        lastChallenge = new Date(System.currentTimeMillis());
        listaRecomendados = new HashSet<>();
        authLevel = "user";
    }

    public String getPass() {
        return pass;
    }
    public static Stats getStats(Persona u) {
        return stats;
    }
    public Date getLastLogin(){
        return lastLogin;
    }
    public void setLastLogin(Date lastLogin){
        this.lastLogin = lastLogin;
    }
    // <desafios>
    public Challenge makeChallenge(String title, String description, int target, String type, float reward) {
        Challenge challenge = new Challenge("" + (desafios.size() + 1), title, description, target, type, reward,false,0);
        this.desafios.add(challenge);
        return challenge;
    }
    public Challenge makeChallenge(String title, String description, int target, String type, float reward,boolean timeSensitive,int timeLimit) {
        Challenge challenge = new Challenge("" + (desafios.size() + 1), title, description, target, type, reward,timeSensitive,timeLimit);
        this.desafios.add(challenge);
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
    // </desafios>

    // <recomendaciones>
    public void makeRecomendacion(Scanner teclado, Usuario usuario) {// libros leidos por un amigo
        Random rand = new Random();
        HashSet<Usuario> listaAmigos = usuario.getListaAmigos();

        int numeroAmigo = rand.nextInt(usuario.getListaAmigos().size() + 1);

        Iterator<Usuario> it = listaAmigos.iterator();

        if (listaAmigos.size() != 0) {
            int contador = 0;
            while (it.hasNext() && contador < numeroAmigo) {
                Persona actual = it.next();
            }

            Review recomendado = usuario.sortByPuntuacion(usuario.getListaReviews());
            listaRecomendados.add(recomendado.getLibro());
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
    public void addAmigo(Scanner teclado, Usuario usuario) {
        listaAmigos.add(usuario);// hacer esta wea un hashset
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

    public HashSet<Usuario> getListaAmigos() {
        if (listaAmigos.size() <= 0) {
            System.out.println("No hay amigos");
            return null;
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

    public Usuario buscarAmigo(Usuario usuario) {
       // Usuario actual = new Usuario("Error", "Error", "Error","","","",);
        Iterator<Usuario> it = usuario.getListaAmigos().iterator();

        if (usuario.getnAmigos() != 0) {
            int contador = 0;
            while (it.hasNext() && contador < usuario.getnAmigos()) {
                if (it.next() == usuario) {
                //    actual = it.next();
                }
            }
        }
        return null;

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
        // parte de paula :smile:
        // 3.actualizar desafíos
        int completedCount = 0;
        for (Challenge challenge : desafios) {
            switch (challenge.getType()) {
                case "paginas":
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
        return completedCount;
    }
    // </libros>

    // hazerlo de un amigo al azar/uno que yo escsoga
    // amigo al azar
    // checkear si tiene algo en la lista de reviews, si esta vacia otro amigo
    // random, si no dame tu mejor review

}
