package app.Inksight;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GestionColecciones {
	public static final int LIBRO_AGREGADO_CORRECTAMENTE = 0; 
	public static final int LIBRO_YA_EXISTE = 1; 
	public static final int COLECCION_NO_EXISTE = 2; 
	public static final int LIBRO_ELIMINADO_CORRECTAMENTE = 3; 
	public static final int LIBRO_NO_EXISTE = 4; 
	public static final int COLECCION_MODIFICADA_CORRECTAMENTE = 5; 
	
	List<ColeccionLibro> listaColecciones = new ArrayList<>();
	public static final String LISTA_NO_LEIDOS = "no leidos"; 
	public static final String LISTA_LEIDOS = "leidos"; 
	public static final String LISTA_LEYENDO = "leyendo"; 

	
	
	
	public GestionColecciones() {
		construirLista(LISTA_LEIDOS);
		construirLista(LISTA_NO_LEIDOS);
		construirLista(LISTA_LEYENDO);
	}

	public List<String> consultarListaColecciones(){
		List<String> nombres = new ArrayList<>();
		for (int i = 0; i < listaColecciones.size(); i++) {
			ColeccionLibro coleccionactual = listaColecciones.get(i);
			nombres.add(coleccionactual.getNombreColeccion());
		}
		return nombres;
	}
	
	
	public void construirLista(String nombreNuevaLista) {
		ColeccionLibro coleccion = new ColeccionLibro(nombreNuevaLista);
		listaColecciones.add(coleccion);
	}

	public ColeccionLibro obtenerColeccion(String coleccionbuscada) {
		for (int i = 0; i < listaColecciones.size(); i++) {
			ColeccionLibro coleccionactual = listaColecciones.get(i);
			if (coleccionactual.getNombreColeccion().equals(coleccionbuscada)) {
				return coleccionactual;
			}
		}
		return null;
	}
	
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

	public int eliminarLibro(String coleccionbuscada,String titulo) {
		ColeccionLibro coleccionactual = obtenerColeccion(coleccionbuscada);
		if(coleccionactual == null) {
			// no se encuentra la coleccion;
			return GestionColecciones.COLECCION_NO_EXISTE;
		}else {	
			int resultado = coleccionactual.eliminar(titulo);
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
	
	

	public void eliminarLista(String nombreColeccionEliminar) {
		//meter condiciones en caso de que no exista la lista 
		ColeccionLibro coleccionActual = obtenerColeccion(nombreColeccionEliminar);
		listaColecciones.remove(coleccionActual);
	}



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

	public int moverLibroDeColeccion(String nombreColeccionOrigen, String nombreColeccionNueva, String titulo) throws IOException {
		ColeccionLibro coleccionOrigen = obtenerColeccion(nombreColeccionOrigen);
		ColeccionLibro coleccionNueva = obtenerColeccion(nombreColeccionNueva);
		if(coleccionOrigen == null || coleccionNueva == null) {
			// no se encuentra la coleccion;
			return GestionColecciones.COLECCION_NO_EXISTE;
		}else {	
			int respuesta = coleccionOrigen.mover(coleccionNueva, titulo);
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
