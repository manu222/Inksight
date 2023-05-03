package app.Inksight;

import java.util.*;

public class PruebaClase {//usuario con limite de 100 amigos


    String nombre;
    List<String> listaAmigos; //probar hashmap
    List<String> libroLeer;
    List<String> libroLeido;
    int permiso;
    int nAmigos;
    int puntos;

    public void anyadirAmigo(Scanner teclado, String usuario){
        listaAmigos.add(usuario);//hacer esta wea un hashset
    }

    public int getnAmigos(){
        return listaAmigos.size();
    }

    public String buscarAmigo(){

        return "hola";
    }
    @Override
    public String toString() {
        return "Nombre: " + nombre + "\n" +
                "Lista de amigos: " + listaAmigos + "\n" +
                "Libros por leer: " + libroLeer + "\n" +
                "Libros leidos: " + libroLeido + "\n" +
                "Permiso: " + permiso + "\n" +
                "Numero de amigos: " + nAmigos + "\n" +
                "Puntos: " + puntos + "\n" ;
    }
}