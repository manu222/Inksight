package app.Inksight;


abstract class Persona {

    private String first_name;
    private String last_name;
    private String location;
    private boolean online;
    private int followers;
    protected String authLevel;

    Boolean isBanned;
    int daysUntilUnban;

    public Persona(String first_name, String last_name, String location, boolean online, int followers,Stats stats) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.location = location;
        this.online = online;
        this.followers = followers;
        isBanned = false;
        daysUntilUnban = 0;
    }
    public Persona(String first_name, String last_name, String location) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.location = location;
        this.online = true;
        this.followers = 0;
        isBanned = false;
        daysUntilUnban = 0;
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

    //--------------------Moderation--------------------
    public void ban(){
        isBanned = true;
        daysUntilUnban=-1;
    }
    public void unban(){
        isBanned = false;
        daysUntilUnban = 0;
    }
    public void suspend(int dias){
        daysUntilUnban = dias;
    }
    public Boolean getIsBanned(){
        return isBanned;
    }
    public int getDaysUntilUnban(){
        return daysUntilUnban;
    }
    public void addBanDuration(int dias){
        daysUntilUnban += dias;
    }

}

/*
 * Date lastChallenge= usuario.getLastChallenge();
 * if(lastChallenge.getTime() < (new Date().getTime() - 604800000)){
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