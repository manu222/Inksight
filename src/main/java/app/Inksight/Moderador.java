package app.Inksight;

public class Moderador extends Persona {
    public Moderador(String nombreUser,String correo,String pass ,String name, String surname, String location) {
        super(nombreUser,correo,pass,name, surname, location);
        authLevel = "moderador";
    }
    public Moderador(String nombreUser,String correo,String pass ,String first_name, String last_name, String location, boolean online, int followers,Stats stats) {
        super(nombreUser,correo,pass, first_name, last_name, location, online, followers,stats);
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
