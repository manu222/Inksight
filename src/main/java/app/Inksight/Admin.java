package app.Inksight;

public class Admin extends Persona {
    public Admin(String name, String surname, String location) {
        super(name, surname, location);
        authLevel = "admin";
    }
    public Admin(String first_name, String last_name, String location, boolean online, int followers,Stats stats) {
        super(first_name, last_name, location, online, followers,stats);
        authLevel = "admin";
    }

    public void eliminarReseña(Review r) {
        r = null;
    }

    public void banUser(Persona p) {
        if (!(p instanceof Admin)) {
            p.ban();
        }
    }

    public void unbanUser(Persona p) {
        if (!(p instanceof Admin)) {
            p.unban();
        }
    }

    public void suspendUser(Persona p, int dias) {
        if (!(p instanceof Admin)) {
            p.suspend(dias);
        }
    }

    public void addBanDuration(Persona p, int dias) {
        if (!(p instanceof Admin)) {
            p.addBanDuration(dias);
        }
    }
}
