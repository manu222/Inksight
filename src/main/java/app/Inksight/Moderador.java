package app.Inksight;

/**
 * The type Moderador.
 */
public class Moderador extends Persona {
    /**
     * Instantiates a new Moderador.
     *
     * @param nombreUser the nombre user
     * @param correo     the correo
     * @param pass       the pass
     * @param name       the name
     * @param surname    the surname
     * @param location   the location
     */
    public Moderador(String nombreUser,String correo,String pass ,String name, String surname, String location,boolean banned) {
        super(nombreUser,correo,pass,name, surname, location);
        authLevel = "moderador";
        this.isBanned=banned;
    }

    /**
     * Instantiates a new Moderador.
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
    public Moderador(String nombreUser,String correo,String pass ,String first_name, String last_name, String location, boolean online, int followers,Stats stats) {
        super(nombreUser,correo,pass, first_name, last_name, location, online, followers,stats);
        authLevel= "moderador";
    }

    /**
     * Eliminar reseña.
     *
     * @param r the r
     */
    public void eliminarReseña(Review r){
        r = null;
    }

    /**
     * Ban user.
     *
     * @param p the p
     */
    public void banUser(Usuario p){
        p.ban();
    }

    /**
     * Unban user.
     *
     * @param p the p
     */
    public void unbanUser(Usuario p){
        p.unban();
    }

    /**
     * Suspend user.
     *
     * @param p    the p
     * @param dias the dias
     */
    public void suspendUser(Usuario p,int dias){
        p.suspend(dias);
    }

    /**
     * Add ban duration.
     *
     * @param p    the p
     * @param dias the dias
     */
    public void addBanDuration(Usuario p,int dias){
        if (dias>0){
            p.setIsBanned(true);
        }
        p.addBanDuration(dias);
    }
}
