package app.Inksight;

import java.io.IOException;
import java.util.Scanner;

import static app.Inksight.DB.*;

public class Funciones {
    public static void menuDBLibro() throws IOException {

        DB db= new DB();
        Scanner sc = new Scanner(System.in);
        int opcion;

        System.out.println("¿Qué acción deseas realizar?");
        System.out.println("1) Añadir un libro");
        System.out.println("2) Actualizar un libro");
        System.out.println("3) Eliminar un libro");
        System.out.println("4) Buscar un libro por título");
        System.out.println("5) Salir");
        System.out.println("6) Crear persona de ejemplo");
        opcion = sc.nextInt();

        switch (opcion) {
            case 1:
                addLibro();
                break;
            case 2:
                updateLibro();
                break;
            case 3:
                eliminarLibro();
                break;
            case 4:

                Scanner sc2 = new Scanner(System.in);
                int opcion2;
                System.out.println("¿Qué acción deseas realizar?");
                System.out.println("1) Buscar por ID");
                System.out.println("2) Lista que coincide con query");
                System.out.println("3) Primer resultado coincidente con query");
                opcion2= sc2.nextInt();
                switch (opcion2){
                    case 1:
                        Scanner sc3= new Scanner(System.in);
                        System.out.println("Que quieres buscar? ");
                        int query= sc2.nextInt();
                        System.out.println(db.buscarLibroPorId(query));
                        break;
                    case 2:
                            Scanner sc4= new Scanner(System.in);
                            System.out.println("Que quieres buscar? ");
                            String query2= sc4.nextLine();
                            System.out.println(db.buscarLibros(query2));
                         break;
                    case 3:
                        Scanner sc5= new Scanner(System.in);
                        System.out.println("Que quieres buscar? ");
                        query2= sc5.nextLine();
                        System.out.println(db.buscarLibro(query2));
                        break;
                    default:
                        break;
                }
                break;
            case 5:
                break;
            case 6:
               //db.createPersona("test","test","test");
                break;
            default:
                System.out.println("Opción no válida, por favor intenta de nuevo.");
                break;

        }while (opcion != 5);
    }
}
