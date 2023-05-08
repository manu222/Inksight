package app.Inksight;

import java.util.Date;

public class LibroAmpliado extends Libro {
    private Date fechaInclusion;

    public LibroAmpliado(Libro base, Date fechaInclusion) {
        super(base);
        this.fechaInclusion = fechaInclusion;
    }

    public Date getFechaInclusion() {
        return fechaInclusion;
    }

    public void setFechaInclusion(Date fechaInclusion) {
        this.fechaInclusion = fechaInclusion;
    }

}