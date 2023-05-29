package app.Inksight;

/**
 * The type Admin.
 */
public class Admin extends Persona {
    /**
     * Instantiates a new Admin.
     *
     * @param nombreUser the nombre user
     * @param correo     the correo
     * @param pass       the pass
     * @param name       the name
     * @param surname    the surname
     * @param location   the location
     */
    public Admin(String nombreUser,String correo,String pass ,String name, String surname, String location) {
        super(nombreUser,correo,pass,name, surname, location);
        authLevel = "admin";
    }

    /**
     * Instantiates a new Admin.
     *
     * @param nombreUser the nombre user
     * @param correo     the correo
     * @param pass       the pass
     * @param first_name the first name
     * @param last_name  the last name
     * @param location   the location
     * @param online     the online
     * @param followers  the followers
     * @param stats      the stats
     */
    public Admin(String nombreUser,String correo,String pass ,String first_name, String last_name, String location, boolean online, int followers,Stats stats) {
        super(nombreUser,correo,pass, first_name, last_name, location, online, followers,stats);
        authLevel = "admin";
    }

    /**
     * Eliminar reseña.
     *
     * @param r the r
     */
    public void eliminarReseña(Review r) {
        r = null;
    }

    /**
     * Ban user.
     *
     * @param p the p
     */
    public void banUser(Persona p) {
        if (!(p instanceof Admin)) {
            p.ban();
        }
    }

    /**
     * Unban user.
     *
     * @param p the p
     */
    public void unbanUser(Persona p) {
        if (!(p instanceof Admin)) {
            p.unban();
        }
    }

    /**
     * Suspend user.
     *
     * @param p    the p
     * @param dias the dias
     */
    public void suspendUser(Persona p, int dias) {
        if (!(p instanceof Admin)) {
            p.suspend(dias);
        }
    }

    /**
     * Add ban duration.
     *
     * @param p    the p
     * @param dias the dias
     */
    public void addBanDuration(Persona p, int dias) {
        if (!(p instanceof Admin)) {
            p.addBanDuration(dias);
        }
    }
}
