package app.Inksight;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ColeccionLibro {

	private String nombreColeccion;
	private List<LibroAmpliado> listaLibros;
	
	public ColeccionLibro(String nombreColeccion) {
		this.nombreColeccion = nombreColeccion;
		listaLibros = new ArrayList<>();
	}

	public int anadir(String titulo) throws IOException {

		Date hoy = Calendar.getInstance().getTime();
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

	public List<LibroAmpliado> consultarLibrosEnLista(){
		List<LibroAmpliado> librosEnLista = new ArrayList<>();
		for(int i = 0; i < librosEnLista.size();i++) {
			LibroAmpliado visibilizar = librosEnLista.get(i);
			librosEnLista.add(visibilizar);
		}
		return librosEnLista;
		
	}	
	
	
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

	//metodos por defecto
	public String getNombreColeccion() {
		return nombreColeccion;
	}

	public void setNombreColeccion(String nombreColeccion) {
		this.nombreColeccion = nombreColeccion;
	}

	public List<LibroAmpliado> getListaLibros() {
		return listaLibros;
	}

	public void setListaLibros(List<LibroAmpliado> listaLibros) {
		this.listaLibros = listaLibros;
	}

}

//hacer intercambio en las colecciones, selecciona la coleccion en la que quiere que vaya el libro y lo traspasa a la otra,
//eliminandolo en una y añadiendolo en la otra
//porleer si mete el libro en leyendo tiene que desaparecer de poleer
//leyendo si termina el libro desaparece de leyendo y va a leidos
//leidos