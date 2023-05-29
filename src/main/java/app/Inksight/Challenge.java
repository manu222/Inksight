package app.Inksight;

/**
 * The type Challenge.
 */
public class Challenge {
    /**
     * The Challenge id.
     */
    public final String challengeID;
    private String title;
    private String description;
    private String type;
    private int target;
    private int progress;
    private float reward;
    private boolean completed;
    private boolean timeSensitive;
    private int timeLimit;
    private int timeRemaining;

    /**
     * Instantiates a new Challenge.
     *
     * @param challengeID   the challenge id
     * @param title         the title
     * @param description   the description
     * @param target        the target
     * @param type          the type
     * @param reward        the reward
     * @param timeSensitive the time sensitive
     * @param timeLimit     the time limit
     */
    public Challenge(String challengeID, String title, String description, int target,String type,  float reward, boolean timeSensitive, int timeLimit) {
        this.challengeID = challengeID;
        this.title = title;
        this.description = description;
        this.target = target;
        this.progress = 0;
        this.reward = reward;
        this.completed = false;
        this.type = type;
        this.timeSensitive = timeSensitive;
        this.timeLimit = timeLimit;
        

    }

    /**
     * Instantiates a new Challenge.
     *
     * @param inProgress    the in progress
     * @param challengeID   the challenge id
     * @param title         the title
     * @param description   the description
     * @param target        the target
     * @param type          the type
     * @param reward        the reward
     * @param timeSensitive the time sensitive
     * @param timeLimit     the time limit
     * @param timeRemaining the time remaining
     * @param progress      the progress
     */
    public Challenge(boolean inProgress,String challengeID, String title, String description, int target,String type, float reward, boolean timeSensitive,int timeLimit, int timeRemaining, int progress) {
        this.challengeID = challengeID;
        this.title = title;
        this.description = description;
        this.target = target;
        this.progress = progress;
        this.reward = reward;
        this.completed = false;
        this.type = type;
        this.timeSensitive = timeSensitive;
        this.timeLimit = timeLimit;
        this.timeRemaining = timeRemaining;

    }

    /**
     * Add progress boolean.
     *
     * @param progress the progress
     * @return the boolean
     */
    public boolean addProgress(int progress){
        this.progress += progress;
        if(this.progress >= this.target){
            this.markAsCompleted();
            return true;
        }
        return false;
    }

    /**
     * Mark as completed.
     */
    public void markAsCompleted(){
        this.completed = true;
    }

    /**
     * Gets challenge id.
     *
     * @return the challenge id
     */
//getters
    public String getChallengeID() {
        return challengeID;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets target.
     *
     * @return the target
     */
    public int getTarget() {
        return target;
    }

    /**
     * Gets progress.
     *
     * @return the progress
     */
    public int getProgress() {
        return progress;
    }

    /**
     * Gets reward.
     *
     * @return the reward
     */
    public float getReward() {
        return reward;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Is completed boolean.
     *
     * @return the boolean
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Get time remaining int.
     *
     * @return the int
     */
    public int getTimeRemaining(){
        return timeRemaining;
    }

    /**
     * Is time sensitive boolean.
     *
     * @return the boolean
     */
    public boolean isTimeSensitive(){
        return timeSensitive;
    }

    /**
     * Get time limit int.
     *
     * @return the int
     */
    public int getTimeLimit(){
        return timeLimit;
    }

    /**
     * Set progress.
     *
     * @param progress the progress
     */
//setters
    public void setProgress(int progress){
        this.progress = progress;
    }

    /**
     * Set time remaining.
     *
     * @param timeRemaining the time remaining
     */
    public void setTimeRemaining(int timeRemaining){
        this.timeRemaining = timeRemaining;
    }

    @Override
    public String toString(){
        return "Desafío: " + this.title + "\n" +
                "Descripción: " + this.description + "\n" +
                "Progreso: " + this.progress + "/" + this.target + "\n"+
                "Recompensa: " + this.reward + "\n" +
                ((this.timeRemaining<(24*60*60*1000))?("Tiempo restante: " + this.timeRemaining/(24*60*60*1000) + " días"):(""));
    }
}
