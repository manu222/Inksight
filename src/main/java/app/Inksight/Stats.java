package app.Inksight;

/**
 * The type Stats.
 */
public class Stats {
    private int numBooks;
    private int numPages;
    private int level;
    private float xp;
    private float xpToNextLevel;
    private int quiereLeerHistorico;
    private float tasaFinalizacion;

    /**
     * Instantiates a new Stats.
     */
    public Stats(){
        this.numBooks = 0;
        this.numPages = 0;
        this.level = 0;
        this.xp = 0;
        this.xpToNextLevel = 1000;
    }

    /**
     * Instantiates a new Stats.
     *
     * @param numBooks      the num books
     * @param numPages      the num pages
     * @param level         the level
     * @param xp            the xp
     * @param xpToNextLevel the xp to next level
     */
    public Stats(int numBooks, int numPages, int level, float xp, float xpToNextLevel) {
        this.numBooks = numBooks;
        this.numPages = numPages;
        this.level = level;
        this.xp = xp;
        this.xpToNextLevel = xpToNextLevel;
        tasaFinalizacion = (numBooks/quiereLeerHistorico)*100;

    }

    /**
     * Add book.
     *
     * @param libro the libro
     */
    public void addBook(LibroAmpliado libro){
        numBooks++;
        numPages += libro.getNumPages();
        addXp(libro.getFechaInclusion().getTime() / 1000000000f);
    }

    /**
     * Add book.
     *
     * @param libro the libro
     */
//si no se sabe la fecha de inclusión, se debe llamar addXp con la experiencia calculada
    public void addBook(Libro libro){
        numBooks++;
        numPages += libro.numPages;
    }

    /**
     * Add xp boolean.
     *
     * @param add the add
     * @return the boolean
     */
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

    /**
     * Gets num books.
     *
     * @return the num books
     */
//getters
    public int getNumBooks() {
        return numBooks;
    }

    /**
     * Gets num pages.
     *
     * @return the num pages
     */
    public int getNumPages() {
        return numPages;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets xp.
     *
     * @return the xp
     */
    public float getXp() {
        return xp;
    }

    /**
     * Gets xp to next level.
     *
     * @return the xp to next level
     */
    public float getXpToNextLevel() {
        return xpToNextLevel;
    }

    public String toString() {
        return "Estadisticas:" +
                "\nLibros leidos: " + numBooks +
                "\nPaginas leidas: " + numPages +
                "\nnivel: " + level +
                "\nxp: " + xp +
                "/" + xpToNextLevel;
    }
}
