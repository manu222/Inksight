package app.Inksight;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.*;

public class Persona {

    private String first_name;
    private String last_name;
    private String location;
    private boolean online;
    private int followers;

    HashSet<Persona> listaAmigos;

    List<reviews> listaReviews;
    Set<Libro> listaRecomendados;
    private List <Challenge> desafios;
    private Stats stats;
    private Date lastChallenge;

    public GestionColecciones gestionColecciones;

    public Persona(String first_name, String last_name, String location, boolean online, int followers,Stats stats) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.location = location;
        this.online = online;
        this.followers = followers;
        this.desafios = new LinkedList<>();
        this.stats = stats;
        gestionColecciones=new GestionColecciones();
        lastChallenge = new Date(System.currentTimeMillis());
        listaRecomendados = new HashSet<>();
    }
    public Persona(String first_name, String last_name, String location, boolean online, int followers) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.location = location;
        this.online = online;
        this.followers = followers;
        this.desafios = new LinkedList<>();
        this.stats = new Stats();
        gestionColecciones=new GestionColecciones();
        lastChallenge = new Date(System.currentTimeMillis());
        listaRecomendados = new HashSet<>();
    }
    public int libroTerminado(Libro libro){
        //1. actualizar stats
        stats.addBook(libro);
        //2. mover a la lista de libros leídos y eliminar de la lista de leyendo
        //parte de paula :smile:
        //3.actualizar desafíos
        int completedCount = 0;
        for(Challenge challenge : desafios){
            switch(challenge.getType()){
                case "paginas":
                completedCount+=challenge.addProgress(libro.getNumPages())?1:0;
                break;
                case "libros":
                completedCount+=challenge.addProgress(1)?1:0;
                break;
                case "rec":
                    for(Libro l: listaRecomendados){
                        if(libro.equals(l)){
                            completedCount+=challenge.addProgress(1)?1:0;
                        }
                    }
                //se pueden añadir más tipos
            }
        }
        return completedCount;
    }
    //<desafios>
    public Challenge makeChallenge(String title, String description, int target, String type, float reward){
        Challenge challenge = new Challenge(""+(desafios.size()+1),title, description, target, type, reward);
        this.desafios.add(challenge);
        return challenge;
    }
    public void addChallenge(Challenge challenge){
        this.desafios.add(challenge);
    }
    public void markAsCompleted(String challengeID){
        for (Challenge challenge : desafios) {
            if(challenge.getChallengeID().equals(challengeID)){
                challenge.markAsCompleted();
                this.stats.addXp(challenge.getReward());
            }
        }
    }
    //</desafios>

    public List<Challenge> getDesafios(){
        return this.desafios;
    }
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public void anyadirAmigo(Scanner teclado, Persona usuario){
        listaAmigos.add(usuario);//hacer esta wea un hashset
    }

    public HashSet<Persona> getListaAmigos() {
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
    public List<reviews> getListaReviews(){return listaReviews;}

    public Persona buscarAmigo(Persona usuario) {
        Persona actual = new Persona("Error","Error","Error",false,0);
        Iterator<Persona> it = usuario.getListaAmigos().iterator();

        if (usuario.getnAmigos() != 0) {
            int contador = 0;
            while (it.hasNext() && contador < usuario.getnAmigos()) {
                if(it.next() == usuario){
                    actual = it.next();
                }
            }
        }
        return actual;

    }

    public static reviews sortByPuntuacion(List<reviews> reviewsList) {
        int n = reviewsList.size();

        // Bubble sort algorithm
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (reviewsList.get(j).getPuntuacion() < reviewsList.get(j + 1).getPuntuacion()) {
                    // swap reviews
                    reviews temp = reviewsList.get(j);
                    reviewsList.set(j, reviewsList.get(j + 1));
                    reviewsList.set(j + 1, temp);
                }
            }
        }
        return reviewsList.get(0);
    }

    public void recomendacion (Scanner teclado, Persona usuario){//libros leidos por un amigo
        Random rand = new Random();
        HashSet<Persona> listaAmigos = usuario.getListaAmigos();

        int numeroAmigo = rand.nextInt(usuario.getListaAmigos().size() + 1);

        Iterator<Persona> it = listaAmigos.iterator();

        if (listaAmigos.size() != 0) {
            int contador = 0;
            while (it.hasNext() && contador < numeroAmigo) {
                Persona actual = it.next();
            }

            reviews recomendado = usuario.sortByPuntuacion(usuario.getListaReviews());
            listaRecomendados.add(recomendado.getLibro());
            System.out.println("Esta es una review recomendada por tu amigo");
            System.out.println("Titulo:" + recomendado.getLibro().getTitle());
            System.out.println("Review:" + recomendado.getDescripcion());
            System.out.println("Puntuacion:" + recomendado.getPuntuacion());


        } else {
            System.out.println("El usuario no tiene amigos, por lo que no recibe recomendaciones");

        }

        //hazerlo de un amigo al azar/uno que yo escsoga
        //amigo al azar
        //checkear si tiene algo en la lista de reviews, si esta vacia otro amigo random, si no dame tu mejor review

    }


}

/*
 * Date lastChallenge;
 * if(lastChallenge.getTime() < new Date().getTime() - 604800000){
 * //pasaron 7 días desde el último desafío
 * int tipo = (int) (Math.random() * 3);
 * switch(tipo){
 * //permite agregar todas las opciones de desafios que quieras, simplemente agrega un nuevo case(permite ajustar pesos repitiendo numeros)
 * case 0:
 * makeChallenge("Leer 5 libros", "Leer 5 libros en una semana", 5, "libros", 100);
 * break;
 * case 1:
 * makeChallenge("Leer 100 páginas", "Leer 100 páginas en una semana", 100, "paginas", 100);
 * break;
 * case 2:
 * makeChallenge("Leer 10 libros", "Leer 10 libros en una semana", 10, "libros", 200);
 * break;
 * case 3,4:
 * makeChallenge("Aceptar 1 recomendacion","lee un libro que un amigo te haya recomendado",1,"rec",40
 * 
 * }
 */