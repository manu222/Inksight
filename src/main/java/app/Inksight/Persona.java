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

    public Persona(String first_name, String last_name, String location, boolean online, int followers, Stats stats) {
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

    // --------------------Moderation--------------------
    public void ban() {
        isBanned = true;
        daysUntilUnban = -1;
    }

    public void unban() {
        isBanned = false;
        daysUntilUnban = 0;
    }

    public void suspend(int dias) {
        daysUntilUnban += dias;
    }

    public Boolean isBanned() {
        return isBanned;
    }

    public int getDaysUntilUnban() {
        return daysUntilUnban;
    }

    public void addBanDuration(int dias) {
        daysUntilUnban += dias;
    }
    public void setDaysUntilUnban(int dias){
        daysUntilUnban = dias;
    }
    public boolean dayPassed(){
        if(daysUntilUnban>0){
            daysUntilUnban--;
        
        }
        return daysUntilUnban==0;
    }


}
