package app.Inksight;

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

    public int anadir(Libro libroQueAñado) {
        boolean bandera = false;
        for (int i = 0; i < listaLibros.size(); i++) {
            LibroAmpliado libroactual = listaLibros.get(i);
            if (libroactual.getTitle().equals(libroQueAñado.getTitle())) {
                bandera = true;
            }
        }

        if (bandera == false) {
            Date hoy = Calendar.getInstance().getTime();
            LibroAmpliado la = new LibroAmpliado(libroQueAñado, hoy);
            listaLibros.add(la);
            return GestionColecciones.LIBRO_AGREGADO_CORRECTAMENTE;
        } else {
            // avisar que ya existe el titulo
            return GestionColecciones.LIBRO_YA_EXISTE;
        }

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
                int respuesta = coleccionNueva.anadir(libroactual);
                if (respuesta == GestionColecciones.LIBRO_YA_EXISTE) {
                    return GestionColecciones.LIBRO_YA_EXISTE;
                } else {
                    listaLibros.remove(libroactual);
                    return GestionColecciones.COLECCION_MODIFICADA_CORRECTAMENTE;
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