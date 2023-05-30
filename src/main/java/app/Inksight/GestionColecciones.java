package app.Inksight;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Gestion colecciones.
 */
public class GestionColecciones {
    /**
     * The constant LIBRO_AGREGADO_CORRECTAMENTE.
     */
    public static final int LIBRO_AGREGADO_CORRECTAMENTE = 0;
    /**
     * The constant LIBRO_YA_EXISTE.
     */
    public static final int LIBRO_YA_EXISTE = 1;
    /**
     * The constant COLECCION_NO_EXISTE.
     */
    public static final int COLECCION_NO_EXISTE = 2;
    /**
     * The constant LIBRO_ELIMINADO_CORRECTAMENTE.
     */
    public static final int LIBRO_ELIMINADO_CORRECTAMENTE = 3;
    /**
     * The constant LIBRO_NO_EXISTE.
     */
    public static final int LIBRO_NO_EXISTE = 4;
    /**
     * The constant COLECCION_MODIFICADA_CORRECTAMENTE.
     */
    public static final int COLECCION_MODIFICADA_CORRECTAMENTE = 5;

    /**
     * The Lista colecciones.
     */
    List<ColeccionLibro> listaColecciones = new ArrayList<>();
    /**
     * The constant LISTA_NO_LEIDOS.
     */
    public static final String LISTA_NO_LEIDOS = "no leidos";
    /**
     * The constant LISTA_LEIDOS.
     */
    public static final String LISTA_LEIDOS = "leidos";
    /**
     * The constant LISTA_LEYENDO.
     */
    public static final String LISTA_LEYENDO = "leyendo";


    /**
     * Instantiates a new Gestion colecciones.
     */
    public GestionColecciones() {
		construirLista(LISTA_LEIDOS);
		construirLista(LISTA_NO_LEIDOS);
		construirLista(LISTA_LEYENDO);
	}

    /**
     * Instantiates a new Gestion colecciones.
     *
     * @param cl the cl
     */
    public GestionColecciones(List<ColeccionLibro> cl){
		this.listaColecciones=cl;
	}

    /**
     * Consultar lista colecciones list.
     *
     * @return the list
     */
    public List<String> consultarListaColecciones(){
		List<String> nombres = new ArrayList<>();
		for (int i = 0; i < listaColecciones.size(); i++) {
			ColeccionLibro coleccionactual = listaColecciones.get(i);
			nombres.add(coleccionactual.getNombreColeccion());
		}
		return nombres;
	}


    /**
     * Construir lista.
     *
     * @param nombreNuevaLista the nombre nueva lista
     */
    public boolean construirLista(String nombreNuevaLista) {
		ColeccionLibro coleccion = new ColeccionLibro(nombreNuevaLista);
		for (ColeccionLibro c:listaColecciones) {
			if (c.getNombreColeccion().equalsIgnoreCase(nombreNuevaLista)){
				return false;
			}
		}
		return listaColecciones.add(coleccion);
	}

    /**
     * Obtener coleccion coleccion libro.
     *
     * @param coleccionbuscada the coleccionbuscada
     * @return the coleccion libro
     */
    public ColeccionLibro obtenerColeccion(String coleccionbuscada) {
		for (int i = 0; i < listaColecciones.size(); i++) {
			ColeccionLibro coleccionactual = listaColecciones.get(i);
			if (coleccionactual.getNombreColeccion().equalsIgnoreCase(coleccionbuscada)) {
				return coleccionactual;
			}
		}
		return null;
	}

    /**
     * Agregarlibro int.
     *
     * @param coleccionbuscada the coleccionbuscada
     * @param titulo           the titulo
     * @return the int
     * @throws IOException the io exception
     */
    public int agregarlibro(String coleccionbuscada,String titulo) throws IOException {
		ColeccionLibro coleccionactual = obtenerColeccion(coleccionbuscada);
		if(coleccionactual == null) {
			// no se encuentra la coleccion;
			return GestionColecciones.COLECCION_NO_EXISTE;
		}else {	
			int resultado = coleccionactual.anadir(titulo);
			return resultado;
		}
	}

//	public boolean agregarlibro() {
//		System.out.println("Dime en que coleccion:");
//		String coleccionbuscada = sc.nextLine();
//		System.out.println("Dime el titulo del nuevo libro:");
//		String titulo = sc.nextLine();
//		Libro libro = new Libro(titulo);
//		
//		ColeccionLibro coleccionactual = obtenerColeccion(coleccionbuscada);
//		if(coleccionactual == null) {
//			// no se encuentra la coleccion;
//			return false;
//		}else {	
//			coleccionactual.anadir(libro);
//			return true;
//		}
//	
//	}

    /**
     * Eliminar libro int.
     *
     * @param coleccionbuscada the coleccionbuscada
     * @param titulo           the titulo
     * @return the int
     * @throws IOException the io exception
     */
    public int eliminarLibro(String coleccionbuscada,String titulo) throws IOException {
		ColeccionLibro coleccionactual = obtenerColeccion(coleccionbuscada);
		if(coleccionactual == null) {
			// no se encuentra la coleccion;
			return GestionColecciones.COLECCION_NO_EXISTE;
		}else {
			DB db = new DB();
			int resultado = coleccionactual.eliminar(db.buscarUnLibro(titulo).getTitle());
			return resultado;
		}

	}

	
//	public void eliminarLibro() {
//		System.out.println("introduzca la lista de la que desea eliminar el libro");
//		String coleccionSeleccionada = sc.nextLine();
//		System.out.println("dime el titulo que quiere eliminar");
//		String titulo = sc.nextLine();
//
//		for (int i = 0; i < listaColecciones.size(); i++) {
//			Iterator copia = listaColecciones.iterator();
//			while (copia.hasNext()) {
//				copia.next();
//				copia.remove();
//			}
//		}
//
//	}


    /**
     * Eliminar lista.
     *
     * @param nombreColeccionEliminar the nombre coleccion eliminar
     */
    public boolean eliminarLista(String nombreColeccionEliminar) {
		//meter condiciones en caso de que no exista la lista 
		ColeccionLibro coleccion = new ColeccionLibro(nombreColeccionEliminar);
		for (ColeccionLibro c:listaColecciones) {
			if (c.getNombreColeccion().equalsIgnoreCase(nombreColeccionEliminar)){
				return listaColecciones.remove(c);
			}
		}
		return false;

	}


    /**
     * Cambiar nombre lista int.
     *
     * @param nombreantiguo the nombreantiguo
     * @param nuevonombre   the nuevonombre
     * @return the int
     */
    public int cambiarNombreLista(String nombreantiguo, String nuevonombre) {
		ColeccionLibro coleccionactual = obtenerColeccion(nombreantiguo);
		if(coleccionactual == null) {
			// no se encuentra la coleccion;
			return GestionColecciones.COLECCION_NO_EXISTE;
		}else {	
			coleccionactual.setNombreColeccion(nuevonombre);
			return GestionColecciones.COLECCION_MODIFICADA_CORRECTAMENTE;
		}
	}

    /**
     * Mover libro de coleccion int.
     *
     * @param nombreColeccionOrigen the nombre coleccion origen
     * @param nombreColeccionNueva  the nombre coleccion nueva
     * @param titulo                the titulo
     * @return the int
     * @throws IOException the io exception
     */
    public int moverLibroDeColeccion(String nombreColeccionOrigen, String nombreColeccionNueva, String titulo) throws IOException {
		ColeccionLibro coleccionOrigen = obtenerColeccion(nombreColeccionOrigen);
		ColeccionLibro coleccionNueva = obtenerColeccion(nombreColeccionNueva);
		if(coleccionOrigen == null || coleccionNueva == null) {
			// no se encuentra la coleccion;
			return GestionColecciones.COLECCION_NO_EXISTE;
		}else {
			DB db = new DB();
			db.buscarUnLibro(titulo);
			int respuesta = coleccionOrigen.mover(coleccionNueva, db.buscarUnLibro(titulo).getTitle());
			return respuesta;
		}
	}
	
}
/*
 * se encarga del manejo de las listas: construye las listas
 * 
 * se encarga de al agregar ver si la coleccion existe o no con eliminar lo
 * mismo
 * 
 * elimina las listas
 * 
 * cambia el nombre de la lista
 */
