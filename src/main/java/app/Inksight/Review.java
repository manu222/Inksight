package app.Inksight;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The type Review.
 */
public class Review {
    /**
     * The Libro.
     */
    Libro libro;
    /**
     * The Descripcion.
     */
    String descripcion;
    /**
     * The Puntuacion.
     */
    float puntuacion;

    /**
     * Instantiates a new Review.
     *
     * @param libro       the libro
     * @param descripcion the descripcion
     * @param puntuacion  the puntuacion
     */
    Review(Libro libro, String descripcion, float puntuacion) {
            this.libro = libro;
            this.descripcion = descripcion;
            this.puntuacion = puntuacion;
        }

    /**
     * Sets descripcion.
     *
     * @param descripcion the descripcion
     */
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    /**
     * Hacer review review.
     *
     * @param sc the sc
     * @return the review
     * @throws IOException the io exception
     */
    public static Review hacerReview(Scanner sc) throws IOException {
            Interfaz.clearConsole();
            DB db = new DB();

            System.out.println("Cual es el titulo del libro?");

            String titulo = sc.nextLine();

            if (db.buscarLibro(titulo).getTitle().equalsIgnoreCase("error")){
                return null;
            }

            System.out.println("titulo: "+db.buscarLibro(titulo).getTitle());

            System.out.println("Escribe tu review del libro:");
            String texto = sc.nextLine();
            boolean valid = false;
            float puntuacion= -1f;
            while (!valid) {
                System.out.println("ingrese la puntuacion del libro");
                try {
                    puntuacion = sc.nextFloat();
                    valid = puntuacion>=1 && puntuacion<=5;
                }catch(InputMismatchException e){
                    System.out.println("La puntuacion debe ser entre 1 y 5");
                    sc.nextLine();
                }
            }
            Review review = new Review (DB.libroError, "", -1);


                Libro libro = db.buscarLibro(titulo);

                review = new Review(libro,texto,puntuacion);
                System.out.println("Review hecha de manera correcta.");
                return review;



        }

        public String toString() {
            return  "\nLibro: " + libro.title + "\nPuntaje:" + puntuacion + "\nReseña: "+descripcion;
        }


    /**
     * Sets libro.
     *
     * @param libro the libro
     */
    public void setLibro(Libro libro) {
            this.libro = libro;
        }

    /**
     * Sets puntuacion.
     *
     * @param puntuacion the puntuacion
     */
    public void setPuntuacion(int puntuacion) {
            this.puntuacion = puntuacion;
        }

    /**
     * Gets descripcion.
     *
     * @return the descripcion
     */
    public String getDescripcion() {
            return descripcion;
        }

    /**
     * Gets libro.
     *
     * @return the libro
     */
    public Libro getLibro() {
            return libro;
        }

    /**
     * Gets puntuacion.
     *
     * @return the puntuacion
     */
    public float getPuntuacion() {
            return puntuacion;
        }


}
