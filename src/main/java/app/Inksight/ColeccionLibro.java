package app.Inksight;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * The type Coleccion libro.
 */
public class ColeccionLibro {

	private String nombreColeccion;
	private List<LibroAmpliado> listaLibros;

    /**
     * Instantiates a new Coleccion libro.
     *
     * @param nombreColeccion the nombre coleccion
     */
    public ColeccionLibro(String nombreColeccion) {
		this.nombreColeccion = nombreColeccion;
		listaLibros = new ArrayList<>();
	}

    /**
     * Anadir int.
     *
     * @param titulo the titulo
     * @return the int
     * @throws IOException the io exception
     */
    public int anadir(String titulo) throws IOException {

		Date hoy = new Date(System.currentTimeMillis());
		boolean yaexistiaellibro = false;
		
		// el metodo de la bbdd debe buscar un libro por su titulo
		// si lo encuentra, me devuelve el OBJETO LIBRO que ha encontrado
		// si no lo encuentra, me devuelve null
		DB db = new DB();
		Libro librofinal;
		
		librofinal = db.buscarUnLibro(titulo);
		
		if(librofinal==null) {
			return GestionColecciones.LIBRO_NO_EXISTE;
		}
		
		LibroAmpliado libroampliadofinal = new LibroAmpliado(librofinal, hoy);
		
		for (int i = 0; i < listaLibros.size(); i++) {
			LibroAmpliado libroactual = listaLibros.get(i);
		     if (libroactual.getbookID() == libroampliadofinal.getbookID()) {
		//	if (libroactual..getTitulo().equals(libroQueAñado.getTitulo())) {
				yaexistiaellibro = true;
			}
		}

		if (yaexistiaellibro == false) {
			listaLibros.add(libroampliadofinal);
			return GestionColecciones.LIBRO_AGREGADO_CORRECTAMENTE;
		} else {
			// avisar que ya existe el titulo
			return GestionColecciones.LIBRO_YA_EXISTE;
		}
		

	}

    /**
     * Consultar libros en lista list.
     *
     * @return the list
     */
    public List<Libro> consultarLibrosEnLista(){
		List<Libro> librosEnLista = new ArrayList<>(listaLibros.size());
		for(int i = 0; i < librosEnLista.size();i++) {
			librosEnLista.add((Libro)listaLibros.get(i));
		}
		return librosEnLista;
		
	}

    /**
     * Contiene boolean.
     *
     * @param chk the chk
     * @return the boolean
     */
    public boolean contiene(Libro chk){
		boolean out= false;
		for (Libro l: listaLibros) {
			if (l.equals(chk)){
				return true;
			}
		}
		return false;
	}

    /**
     * Eliminar int.
     *
     * @param tituloAEliminar the titulo a eliminar
     * @return the int
     */
    public int eliminar(String tituloAEliminar) {
		Iterator<LibroAmpliado> copia = listaLibros.iterator();

		while (copia.hasNext()) {
			LibroAmpliado cadalibro = copia.next();
			if (cadalibro.getTitle().equals(tituloAEliminar)) {
				copia.remove();
				return GestionColecciones.LIBRO_ELIMINADO_CORRECTAMENTE;
			}
		}

		return GestionColecciones.LIBRO_NO_EXISTE;
	}


    /**
     * Mover int.
     *
     * @param coleccionNueva the coleccion nueva
     * @param titulo         the titulo
     * @return the int
     */
    public int mover(ColeccionLibro coleccionNueva, String titulo) {

		for (int i = 0; i < listaLibros.size(); i++) {
			LibroAmpliado libroactual = listaLibros.get(i);
			if (libroactual.getTitle().equals(titulo)) {
				try{
					int respuesta = coleccionNueva.anadir(libroactual.getTitle());
					if (respuesta == GestionColecciones.LIBRO_YA_EXISTE) {
						return GestionColecciones.LIBRO_YA_EXISTE;
					} else {
						listaLibros.remove(libroactual);
						return GestionColecciones.COLECCION_MODIFICADA_CORRECTAMENTE;
					}
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		return GestionColecciones.LIBRO_NO_EXISTE;
	}

    /**
     * Gets nombre coleccion.
     *
     * @return the nombre coleccion
     */
//metodos por defecto
	public String getNombreColeccion() {
		return nombreColeccion;
	}

    /**
     * Sets nombre coleccion.
     *
     * @param nombreColeccion the nombre coleccion
     */
    public void setNombreColeccion(String nombreColeccion) {
		this.nombreColeccion = nombreColeccion;
	}

    /**
     * Gets lista libros.
     *
     * @return the lista libros
     */
    public List<LibroAmpliado> getListaLibros() {
		return listaLibros;
	}

    /**
     * Sets lista libros.
     *
     * @param listaLibros the lista libros
     */
    public void setListaLibros(List<LibroAmpliado> listaLibros) {
		this.listaLibros = listaLibros;
	}

}

//hacer intercambio en las colecciones, selecciona la coleccion en la que quiere que vaya el libro y lo traspasa a la otra,
//eliminandolo en una y añadiendolo en la otra
//porleer si mete el libro en leyendo tiene que desaparecer de poleer
//leyendo si termina el libro desaparece de leyendo y va a leidos
//leidos