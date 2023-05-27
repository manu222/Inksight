package app.Inksight;

public class Challenge {
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
    public boolean addProgress(int progress){
        this.progress += progress;
        if(this.progress >= this.target){
            this.markAsCompleted();
            return true;
        }
        return false;
    }
    public void markAsCompleted(){
        this.completed = true;
    }
    //getters
    public String getChallengeID() {
        return challengeID;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public int getTarget() {
        return target;
    }
    public int getProgress() {
        return progress;
    }
    public float getReward() {
        return reward;
    }
    public String getType() {
        return type;
    }
    public boolean isCompleted() {
        return completed;
    }
    public int getTimeRemaining(){
        return timeRemaining;
    }
    public boolean isTimeSensitive(){
        return timeSensitive;
    }
    public int getTimeLimit(){
        return timeLimit;
    }
    //setters
    public void setProgress(int progress){
        this.progress = progress;
    }
    public void setTimeRemaining(int timeRemaining){
        this.timeRemaining = timeRemaining;
    }

}
