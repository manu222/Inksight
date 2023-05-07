package com.ue.insw.proyecto.exercises.json;

public class Challenge {
    public final String challengeID;
    private String title;
    private String description;
    private String type;
    private int target;
    private int progress;
    private float reward;
    private boolean completed;

    public Challenge(String challengeID, String title, String description, int target,String type,  float reward) {
        this.challengeID = challengeID;
        this.title = title;
        this.description = description;
        this.target = target;
        this.progress = 0;
        this.reward = reward;
        this.completed = false;
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
    public void setProgress(int progress){
        this.progress = progress;
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
    

}
