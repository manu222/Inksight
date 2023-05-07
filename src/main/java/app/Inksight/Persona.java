package app.Inksight;

import java.util.*;

public class Persona {

    private String first_name;
    private String last_name;
    private String location;
    private boolean online;
    private int followers;
    HashSet<Persona> listaAmigos; //probar hashmap
    List<reviews> listaReviews;
    List<String> libroLeer;
    List<String> libroLeido;


    int permiso;

    int puntos;

    public Persona(String first_name, String last_name, String location, boolean online, int followers,HashSet<Persona> listaAmigos,List<reviews> listaReviews) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.location = location;
        this.online = online;
        this.followers = followers;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        System.out.println("d");
        return last_name;

    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }
}


    public void anyadirAmigo(Scanner teclado, Persona usuario){
        listaAmigos.add(usuario);//hacer esta wea un hashset
    }

    public int getnAmigos(){
        return listaAmigos.size();
    }

    public List<reviews> getListaReviews(){return listaReviews;}

    public HashSet<Persona> getListaAmigos(){return listaAmigos;}
    public Persona buscarAmigo(Persona usuario) {
        Persona actual = new Persona();
        Iterator<Persona> it = usuario.getListaAmigos().iterator();

        if (usuario.getnAmigos() != 0) {
            int contador = 0;
            while (it.hasNext() && contador < usuario.getnAmigos()) {
                if(it.next() == usuario){
                actual = it.next();
                }
            }
            }
        return actual;

        }




    public static reviews sortByPuntuacion(List<reviews> reviewsList) {
        int n = reviewsList.size();

        // Bubble sort algorithm
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (reviewsList.get(j).getPuntuacion() < reviewsList.get(j + 1).getPuntuacion()) {
                    // swap reviews
                    reviews temp = reviewsList.get(j);
                    reviewsList.set(j, reviewsList.get(j + 1));
                    reviewsList.set(j + 1, temp);
                }
            }
        }
        return reviewsList.get(0);
    }


        public void recomendacion (Scanner teclado, Persona usuario){//libros leidos por un amigo
            Random rand = new Random();
            HashSet<Persona> listaAmigos = usuario.getListaAmigos();

            int numeroAmigo = rand.nextInt(usuario.getListaAmigos().size() + 1);

            Iterator<Persona> it = listaAmigos.iterator();

            if (listaAmigos.size() != 0) {
                int contador = 0;
                while (it.hasNext() && contador < numeroAmigo) {
                    Persona actual = it.next();
                }

                reviews recomendado = usuario.sortByPuntuacion(usuario.getListaReviews());
                System.out.println("Esta es una review recomendada por tu amigo");
                System.out.println("Titulo:" + recomendado.getLibro().getTitle());
                System.out.println("Review:" + recomendado.getDescripcion());
                System.out.println("Puntuacion:" + recomendado.getPuntuacion());


            } else {
                System.out.println("El usuario no tiene amigos, por lo que no recibe recomendaciones");

            }

            //hazerlo de un amigo al azar/uno que yo escsoga
            //amigo al azar
            //checkear si tiene algo en la lista de reviews, si esta vacia otro amigo random, si no dame tu mejor review

        }
    }