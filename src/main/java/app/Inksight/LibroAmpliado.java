package app.Inksight;
import java.util.Date;

/**
 * The type Libro ampliado.
 */
public class LibroAmpliado extends Libro {
	private Date fechaInclusion;
	private boolean recomendado;

    /**
     * Instantiates a new Libro ampliado.
     *
     * @param base           the base
     * @param fechaInclusion the fecha inclusion
     */
    public LibroAmpliado(Libro base, Date fechaInclusion) {
		super(base);
		this.fechaInclusion = fechaInclusion;
	}

    /**
     * Gets fecha inclusion.
     *
     * @return the fecha inclusion
     */
    public Date getFechaInclusion() {
		return fechaInclusion;
	}

    /**
     * Sets fecha inclusion.
     *
     * @param fechaInclusion the fecha inclusion
     */
    public void setFechaInclusion(Date fechaInclusion) {
		this.fechaInclusion = fechaInclusion;
	}

    /**
     * Is recomendado boolean.
     *
     * @return the boolean
     */
    public boolean isRecomendado(){return recomendado;}
}
