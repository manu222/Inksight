package app.Inksight;

import java.util.LinkedList;
import java.util.List;

public class Persona {

    private String first_name;
    private String last_name;
    private String location;
    private boolean online;
    private int followers;
    private List <Challenge> desafios;
    private Stats stats;

    public Persona(String first_name, String last_name, String location, boolean online, int followers) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.location = location;
        this.online = online;
        this.followers = followers;
        this.desafios = new LinkedList<>();
        this.stats = new Stats();
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
                //se pueden añadir más tipos
            }
        }
        return completedCount;
    }
    //<desafios>
    public Challenge makeChallenge(String title, String description, int target, String type, float reward){
        Challenge challenge = new Challenge("id",title, description, target, type, reward);
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
 * makeChallenge("Leer 10 libros", "Leer 10 libros en una semana", 10, "libros", 100);
 * 
 * }
 */