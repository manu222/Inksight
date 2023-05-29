package app.Inksight;

/**
 * The type Persona.
 */
public class Persona {
    private String nombreUser;
    private String correo;
    private String pass;
    private String first_name;
    private String last_name;
    private String location;
    private boolean online;
    private int followers;
    /**
     * The Auth level.
     */
    protected String authLevel;

    /**
     * The Is banned.
     */
    Boolean isBanned;
    /**
     * The Days until unban.
     */
    int daysUntilUnban;

    /**
     * Instantiates a new Persona.
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
    public Persona(String nombreUser,String correo,String pass,String first_name, String last_name, String location, boolean online, int followers, Stats stats) {
        this.first_name = first_name;
        this.nombreUser = nombreUser;
        this.correo = correo;
        this.pass = pass;
        this.last_name = last_name;
        this.location = location;
        this.online = online;
        this.followers = followers;
        isBanned = false;
        daysUntilUnban = 0;
    }

    /**
     * Instantiates a new Persona.
     *
     * @param nombreUser the nombre user
     * @param correo     the correo
     * @param pass       the pass
     * @param first_name the first name
     * @param last_name  the last name
     * @param location   the location
     */
    public Persona(String nombreUser,String correo,String pass,String first_name, String last_name, String location) {
        this.pass=pass;
        this.nombreUser = nombreUser;
        this.correo = correo;
        this.first_name = first_name;
        this.last_name = last_name;
        this.location = location;
        this.online = true;
        this.followers = 0;
        isBanned = false;
        daysUntilUnban = 0;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * Gets pass.
     *
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * Gets nombre user.
     *
     * @return the nombre user
     */
    public String getNombreUser() {
        return nombreUser;
    }

    /**
     * Sets first name.
     *
     * @param first_name the first name
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * Sets last name.
     *
     * @param last_name the last name
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Is online boolean.
     *
     * @return the boolean
     */
    public boolean isOnline() {
        return online;
    }

    /**
     * Sets online.
     *
     * @param online the online
     */
    public void setOnline(boolean online) {
        this.online = online;
    }

    /**
     * Gets followers.
     *
     * @return the followers
     */
    public int getFollowers() {
        return followers;
    }

    /**
     * Sets followers.
     *
     * @param followers the followers
     */
    public void setFollowers(int followers) {
        this.followers = followers;
    }

    /**
     * Gets correo.
     *
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Ban.
     */
// --------------------Moderation--------------------
    public void ban() {
        isBanned = true;
        daysUntilUnban = -1;
    }

    /**
     * Unban.
     */
    public void unban() {
        isBanned = false;
        daysUntilUnban = 0;
    }

    /**
     * Suspend.
     *
     * @param dias the dias
     */
    public void suspend(int dias) {
        daysUntilUnban += dias;
    }

    /**
     * Is banned boolean.
     *
     * @return the boolean
     */
    public Boolean isBanned() {
        return isBanned;
    }

    /**
     * Gets days until unban.
     *
     * @return the days until unban
     */
    public int getDaysUntilUnban() {
        return daysUntilUnban;
    }

    /**
     * Add ban duration.
     *
     * @param dias the dias
     */
    public void addBanDuration(int dias) {
        daysUntilUnban += dias;
    }

    /**
     * Set days until unban.
     *
     * @param dias the dias
     */
    public void setDaysUntilUnban(int dias){
        daysUntilUnban = dias;
    }

    /**
     * Day passed boolean.
     *
     * @return the boolean
     */
    public boolean dayPassed(){
        if(daysUntilUnban>0){
            daysUntilUnban--;

        }
        return daysUntilUnban==0;
    }

    /**
     * Gets auth level.
     *
     * @return the auth level
     */
    public String getAuthLevel() {
        return authLevel;
    }
}
