package app.Inksight;

import java.io.IOException;
import java.util.Scanner;

public class Review {
        Libro libro;
        String descripcion;
        int puntuacion;

        Review(Libro libro, String descripcion, int puntuacion) {
            this.libro = libro;
            this.descripcion = descripcion;
            this.puntuacion = puntuacion;
        }
        public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

        public static Review hacerReview(Scanner sc) throws IOException {
            System.out.println("Cual es el titulo del libro?");
            String titulo = sc.nextLine();
            System.out.println("Cual es el autor del libro?");
            String autor = sc.nextLine();
            System.out.println("Escribe tu review del libro:");
            String texto = sc.nextLine();
            System.out.println("Escribe tu puntuacion de 1-5:");
            int puntos = sc.nextInt();
            DB db = new DB();
            Review review = new Review (null, null, -1);

            if(db.buscarLibro(titulo) != null){
                Libro libro = db.buscarLibro(titulo);

                review = new Review(libro,texto,puntos);
                return(review);

            }
            else{
                System.out.println("Libro no encontrado");
            }

            return review;
        }

        public String toString() {
            return  "\ntitle=" + libro.title +
                "authors=" + libro.authors + "\n" +
                "descripcion=" + descripcion + "\n" +
                "puntuacion=" + puntuacion + "\n" ;
        }


        public void setLibro(Libro libro) {
            this.libro = libro;
        }

        public void setPuntuacion(int puntuacion) {
            this.puntuacion = puntuacion;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public Libro getLibro() {
            return libro;
        }

        public int getPuntuacion() {
            return puntuacion;
        }


}
