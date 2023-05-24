package app.Inksight;

import java.util.Scanner;
public class Interfaz {
	static Scanner sc = new Scanner(System.in);
	String titulo;
	String nombreLista;
	static int opcion;

	
	public static void menu_principal(){
		System.out.println();
		System.out.println("-----INKSIGHT-----");
		System.out.println();
		System.out.println("1. Registrarse");
		System.out.println("2. Iniciar sesión");
		
		System.out.print("Elija la opción deseada: ");
		opcion = sc.nextInt();
		String nombre;
		String contraseña;
		switch(opcion){
		case 1: 
			System.out.print("Ingrese un nombre o correo: ");
			nombre = sc.next();
			validarNombre(nombre);

			System.out.print("Ingrese una contraseña: ");
			contraseña = sc.next();
			// aviso de los requisitos que tiene que tener la contraseña
			System.out.println("Tener en cuenta que: ");
			System.out.println("Debe tener entre 8 y 20 caracteres");
			System.out.println("Debe contener al menos un dígito");
			System.out.println("Debe contener al menos una minúscula");
			System.out.println("Debe contener al menos una mayúscula");
			System.out.println("No debe contener espacios en blanco");
			System.out.println("No debe contener caracteres especiales");
			
			String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
			boolean validPassword = false;
			if(contraseña.matches(regex)){
				validPassword = true;
				System.out.println("Confirmar contraseña: ");
			    contraseña =sc.next();
			} else {
				System.out.println("Contraseña no válida");

			}
			break;
		case 2: 
			boolean valid= false;
			while(!valid ){
			System.out.println("Ingresa nombre o correo:");
			nombre = sc.next();
			validarNombre(nombre);
			System.out.println("Ingresa contraseña:");
			contraseña = sc.next();
			// asegurarse de que la contraseña pertenece al usuario
			
		    if(valid){
		    	System.out.println("No válido");
		    }
			}// while
		}
			
		
	}// menu
	private static void validarNombre(String nombre) {

	}
	public void menu_PerfilUsuario(String nombre){ 
		System.out.println(nombre);
		System.out.println();
		System.out.println("1. Estadisticas");
		System.out.println("2. Retos");
		System.out.println("3. Amigos");
		System.out.println("4. Hacer reseña");
		System.out.println("5. Libros");
		System.out.println("6. Listas");

		
		
		System.out.println("¿Qué deseas hacer? Inserta la opcion deseada");
		opcion = sc.nextInt();
		
		switch(opcion){
		case 1: 
			System.out.println("LOGROS");
			break; 
		case 2: 
			System.out.println("RETOS");
			
			break;
		case 3: 
			System.out.println("AMIGOS");
			menu_Amigo();
			break; 
		case 4: 
			System.out.println("Selecciona el libro del que desees hacer la reseña:");
			titulo = sc.next();
			// validar que el libro existe
			break;
		case 5:
			System.out.println("LIBROS");
			menu_Libro();
			break;
		case 6: 
			System.out.println("LISTAS ");
			menu_ListaGeneral();
			break; 
		
		}
	}
	private void menu_Libro() {
		System.out.println();
		System.out.println("1. Agregar libro ");
		System.out.println("2. Mover libro a una lista");
		System.out.println("3. Eliminar libro");
		System.out.println("4. Libro terminado");
		System.out.println("5. Salir");
		
		System.out.println("¿Qué deseas hacer? Inserta la opcion deseada");
		opcion = sc.nextInt();
		
		switch(opcion){
		case 1: 
			System.out.println("Selecciona el libro que deseas agregar: ");
			titulo = sc.next();
			// validar que el libro existe
			break;
		case 2: 
			System.out.println("Selecciona el libro que deseas mover: ");
			titulo = sc.next();
			System.out.println("Selecciona la lista a la que deseas moverlo o crea una nueva: ");
			nombreLista = sc.next();
			// validar que la lista existe o crearla
			break;
		case 3: 
			System.out.println("Selecciona el libro que deseas eliminar: ");
			titulo = sc.next();
			// preguntar si está seguro
			break; 
		case 4: 
			System.out.println("Selecciona el libro que deseas consultar: ");
			titulo = sc.next();
			// mostrar la información del libro
			break;
		case 5: 
			System.out.println("Selecciona el libro al que deseas cambiarle el nombre: ");
			titulo = sc.next();
			System.out.println("Escribe el nuevo nombre: ");
			titulo = sc.next();
			// validar que el libro existe
			break;
		}
		
	}
	public  void menu_ListaGeneral(){
		System.out.println();
		System.out.println("1. Crear");
		System.out.println("2. Seleccionar lista");
		System.out.println("3. Consultar todas las listas");
		System.out.println("4. Mover libros");
		System.out.println("5. Eliminar listas");

		System.out.println("¿Qué deseas hacer? Inserta la opcion deseada");
		opcion = sc.nextInt();

		switch(opcion){
		case 1:
			System.out.println("Escriba el nombre de la lista que desea crear: ");
			break;
		case 2:
			System.out.println("Seleccione la lista que desea: ");
			break;
		case 3:
			System.out.println("LISTAS");
			// mostrar todas las listas con la cantidad de libros que tiene cada una
			break;
		case 4:
			System.out.println("Seleccione la lista de la que desea mover el libro: ");
			nombreLista = sc.next();
			System.out.println("Seleccione la lista a la que desea moverlo o cree una nueva: ");
			nombreLista = sc.next();
			// validar que la lista existe o crearla
			break;
		case 5:
			System.out.println("Seleccione la lista que desea eliminar: ");
			nombreLista = sc.next();
			// preguntar si está seguro

			break;
		}

	}
	public void menu_Lista(){
		System.out.println();
		System.out.println("1. Agregar libro ");
		System.out.println("2. Mover libro");
		System.out.println("3. Eliminar libro");
		System.out.println("4. Consultar libros");
		System.out.println("5. Cambiar nombre de la lista");
		System.out.println("6. Salir");
		
		System.out.println("¿Qué deseas hacer? Inserta la opcion deseada");
		opcion = sc.nextInt();
		
		switch(opcion){
		case 1: 
			System.out.println("Escriba el nombre del libro que deseas añadir: ");
			titulo = sc.next();
			break; 
		case 2: 
			System.out.println("Escriba el libro que desea mover: ");
			titulo = sc.next();
			System.out.println("Seleccione la lista a la que desea moverlo o cree una nueva: ");
			break;
		case 3: 
			System.out.println("Escriba el nombre del libro que desea eliminar: ");
			titulo = sc.next();
			break; 
		case 4: 
			System.out.println("Libros agregados: ");
			// llamar a la lista para mostrar los libros agregados a ella
			break;
		case 5:
			System.out.println("Escriba el nuevo nombre de la lista: ");
			nombreLista = sc.next();
			// validar que el nombre no exista y asegurarse de que el usuario quiere cambiarlo
			break;
		case 6:
			System.out.println("Saliendo...");
			// llamar al menu de perfil de usuario
			break;
		}
	}
	public  void menu_Amigo(){
		System.out.println("1. Agregar amigo");
		System.out.println("2. Borrar amigo");
		System.out.println("3. Consultar amigos");
		System.out.println("4. Salir");

		System.out.println();
		
		System.out.println("¿Qué deseas hacer? Inserta la opcion deseada");
		opcion = sc.nextInt();
		
		switch(opcion){
		case 1: 
            System.out.print("Escriba el nombre del amigo que desea agregar: ");
            String amigo = sc.next();
            // comprobar si el nombre del amigo existe 
            boolean existe = false; 
			if(existe){
				System.out.println("Nombre no encontrado");
			}
            if(!existe){
            	System.out.println("¿Desea agregarlo?");
            	System.out.println("1. Si");
            	System.out.println("2. No");
            	int numero = sc.nextInt();
            	if(numero == 1){
            		System.out.println("Amigo agregado");
            		
            	}else if(numero == 2){
            		System.out.println("Amigo no agregado");
					menu_Amigo();
            	}else{
            		System.out.println("No has elegido una opción correcta");
            	}
            }
			break; 
		case 2: 
			System.out.println("Ingresa el nombre del amigo que quieras borrar: ");
			amigo = sc.next();
			// si el nombre ingresado no existe salta un aviso y deja volver a escribir el nombre
			break;
		case 3: 
			System.out.println("AMIGOS");
			// se muestra la lista de amigos
			break;
		case 4:
			System.out.println("Saliendo...");
			// llamar al menu de perfil de usuario
			break;
		}
	}
	public void menu_Recomendaciones(){
		// mostrar las recomendaciones que tiene el usuario por parte de sus amigos

	}
	public void menu_Logros(){
		// mostrar los logros que tiene el usuario
	}
	public  void Perfil(String nombre, String contraseña){
		System.out.println(" " + nombre);
		menu_PerfilUsuario(nombre);
	
		
		
	}

	public static void main(String[] args) {
		menu_principal();
		
			

	}

}
