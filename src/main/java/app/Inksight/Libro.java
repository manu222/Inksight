package app.Inksight;

public class Libro {




    public int bookID;
    public String title;
    public String authors;
    public int numPages;
    public String publication_date;
    public String languageCode;

    public Libro(int bookID, String title, String authors, int numPages, String publication_date, String languageCode) {
        this.bookID = bookID;
        this.title = title;
        this.authors = authors;
        this.numPages = numPages;
        this.publication_date = publication_date;
        this.languageCode = languageCode;
    }

    public void setID(int bookID) {
        this.bookID= bookID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setNumPages(int num_pages) {
        this.numPages= num_pages;
    }

    public void setPublication_date(String publication_date) {
        this.publication_date = publication_date;
    }

    public void setLanguageCode(String languageCode) {this.languageCode = languageCode;}

    public int getbookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public int getNumPages() {
        return numPages;
    }

    public String getPublication_date() {
        return publication_date;
    }

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