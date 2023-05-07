package com.ue.insw.proyecto.exercises.json;

public class Stats {
    private int numBooks;
    private int numPages;
    private int level;
    private float xp;
    private float xpToNextLevel;

    public Stats(){
        this.numBooks = 0;
        this.numPages = 0;
        this.level = 0;
        this.xp = 0;
        this.xpToNextLevel = 1000;
    }
    public void addBook(LibroAmpliado libro){
        numBooks++;
        numPages += libro.getNumPages();
        addXp(libro.getFechaInclusion().getTime() / 1000000000);
    }

    //si no se sabe la fecha de inclusión, se debe llamar addXp con la experiencia calculada
    public void addBook(Libro libro){
        numBooks++;
        numPages += libro.numPages;
    }
    public boolean addXp(float add){
        xp += add;
        if(xp >= xpToNextLevel){
            level++;
            xp -= xpToNextLevel;
            xpToNextLevel *= 1.5;
            return true;
        }
        return false;
    }
    //getters
    public int getNumBooks() {
        return numBooks;
    }
    public int getNumPages() {
        return numPages;
    }
    public int getLevel() {
        return level;
    }
    public float getXp() {
        return xp;
    }
    public float getXpToNextLevel() {
        return xpToNextLevel;
    }

}
