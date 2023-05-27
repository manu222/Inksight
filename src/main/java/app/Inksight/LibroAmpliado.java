package app.Inksight;
import java.util.Date;

public class LibroAmpliado extends Libro {
	private Date fechaInclusion;
	private boolean recomendado;

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
	public boolean isRecomendado(){return recomendado;}
}
