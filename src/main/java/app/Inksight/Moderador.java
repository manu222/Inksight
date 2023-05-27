package app.Inksight;

public class Moderador extends Persona {
    public Moderador(String name, String surname, String location) {
        super(name, surname, location);
        authLevel = "moderador";
    }
    public Moderador(String first_name, String last_name, String location, boolean online, int followers,Stats stats) {
        super(first_name, last_name, location, online, followers,stats);
        authLevel= "moderador";
    }
    public void eliminarReseña(Review r){
        r = null;
    }
    public void banUser(Usuario p){
        p.ban();
    }
    public void unbanUser(Usuario p){
        p.unban();
    }
    public void suspendUser(Usuario p,int dias){
        p.suspend(dias);
    }
    public void addBanDuration(Usuario p,int dias){
        p.addBanDuration(dias);
    }
}
