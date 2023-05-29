package app.Inksight;

/**
 * The type Libro.
 */
public class Libro {


    /**
     * The Book id.
     */
    public int bookID;
    /**
     * The Title.
     */
    public String title;
    /**
     * The Authors.
     */
    public String authors;
    /**
     * The Num pages.
     */
    public int numPages;
    /**
     * The Publication date.
     */
    public String publication_date;
    /**
     * The Language code.
     */
    public String languageCode;

    /**
     * Instantiates a new Libro.
     *
     * @param bookID           the book id
     * @param title            the title
     * @param authors          the authors
     * @param numPages         the num pages
     * @param publication_date the publication date
     * @param languageCode     the language code
     */
    public Libro(int bookID, String title, String authors, int numPages, String publication_date, String languageCode) {
        this.bookID = bookID;
        this.title = title;
        this.authors = authors;
        this.numPages = numPages;
        this.publication_date = publication_date;
        this.languageCode = languageCode;
    }

    /**
     * Instantiates a new Libro.
     *
     * @param base the base
     */
    public Libro(Libro base) {
        this.bookID = base.bookID;
        this.title = base.title;
        this.authors = base.authors;
        this.numPages = base.numPages;
        this.publication_date = base.publication_date;
        this.languageCode = base.languageCode;
    }

    /**
     * Sets id.
     *
     * @param bookID the book id
     */
    public void setID(int bookID) {
        this.bookID= bookID;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets authors.
     *
     * @param authors the authors
     */
    public void setAuthors(String authors) {
        this.authors = authors;
    }

    /**
     * Sets num pages.
     *
     * @param num_pages the num pages
     */
    public void setNumPages(int num_pages) {
        this.numPages= num_pages;
    }

    /**
     * Sets publication date.
     *
     * @param publication_date the publication date
     */
    public void setPublication_date(String publication_date) {
        this.publication_date = publication_date;
    }

    /**
     * Sets language code.
     *
     * @param languageCode the language code
     */
    public void setLanguageCode(String languageCode) {this.languageCode = languageCode;}

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getbookID() {
        return bookID;
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
     * Gets authors.
     *
     * @return the authors
     */
    public String getAuthors() {
        return authors;
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
     * Gets publication date.
     *
     * @return the publication date
     */
    public String getPublication_date() {
        return publication_date;
    }

    /**
     * Gets language code.
     *
     * @return the language code
     */
    public String getLanguageCode() {return languageCode;}

    @Override
    public String toString() {
        return  "\nbookID=" + bookID +
                "title=" + title + "\n" +
                "authors=" + authors + "\n" +
                "numPages=" + numPages + "\n" +
                "publication_date=" + publication_date + "\n" +
                "languageCode=" + languageCode + "\n";
    }
    @Override
    public boolean equals(Object other){
        if (other==this){
            return true;
        }
        if (other instanceof Libro){
            Libro l=(Libro) other;
            return l.getbookID()==this.getbookID();
        }
        return  false;
    }

}