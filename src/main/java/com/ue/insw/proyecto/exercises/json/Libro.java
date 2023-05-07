package com.ue.insw.proyecto.exercises.json;

public class Libro {




    public String bookID;
    public String title;
    public String authors;
    public int numPages;
    public String publication_date;
    public String languageCode;

    public Libro(String bookID, String title, String authors, int numPages, String publication_date, String languageCode) {
        this.bookID = bookID;
        this.title = title;
        this.authors = authors;
        this.numPages = numPages;
        this.publication_date = publication_date;
        this.languageCode = languageCode;
    }
    public Libro(Libro base) {
        this.bookID = base.bookID;
        this.title = base.title;
        this.authors = base.authors;
        this.numPages = base.numPages;
        this.publication_date = base.publication_date;
        this.languageCode = base.languageCode;
    }


    public void setID(String bookID) {
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

    public String getbookID() {
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

}
