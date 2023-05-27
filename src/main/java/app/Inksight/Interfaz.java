package app.Inksight;

import java.util.Scanner;
public class Interfaz {
	static Scanner sc = new Scanner(System.in);
	String titulo;
	String nombreLista;
	String nombreListaAntiguo; 
	static int opcion;
	GestionColecciones listas = new GestionColecciones();
	Stats stats = new Stats();
	
	//Libro libro = new Libro(0,"","",0,"","");
	//Libro libro = new Libro(libro);
	 Usuario usuarioActual = new Usuario("","","");	

	
	public static void menu_principal() throws NoSuchAlgorithmException {
		System.out.println();
		System.out.println("-----INKSIGHT-----");
		System.out.println();
		System.out.println("1. Registrarse");
		System.out.println("2. Iniciar sesión");
		
		System.out.print("Elija la opción deseada: ");
		opcion = sc.nextInt();
		String nombreUser;
		String pass;
		String correo;
		switch(opcion){
			case 1:
				System.out.print("Ingrese un nombre de usuario: ");
				nombreUser = sc.next();
				System.out.print("Ingrese un correo electronico: ");
				correo = sc.next();


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
				while(validPassword==false){
					System.out.print("Ingrese una contraseña valida: ");
					pass = sc.next();
					if (pass.matches(regex)) {
						validPassword = true;
						System.out.println("Confirmar contraseña: ");
						String contraseñaConfirmada = sc.next();
						if (pass.matches(contraseñaConfirmada)){
							DB db = new DB();
							MessageDigest digest = MessageDigest.getInstance("SHA-256");
							byte[] encodedhash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
							String passHash= db.bytesToHex(encodedhash);
							db.createPersona(nombreUser,correo, passHash);

						}

					} else {
						System.out.println("Contraseña no válida");
					}
				}
				break;
			case 2:
				boolean valid= false;
				while(!valid ){
					System.out.println("Ingresa nombre de usuario:");
					nombreUser = sc.next();
					System.out.println("Ingresa contraseña:");
					pass = sc.next();
					// asegurarse de que la contraseña pertenece al usuario

					if(valid){
						System.out.println("No válido");
					}
				}
		}
		
	private static void validarNombre(String nombre) {
		//comprobar si el String nombre existe en el archivo de texto
		//boolean valid = usuarios.validarNombre(nombre);
		//if(nombre.equals(usuarioActual.first_name)){}


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
			System.out.println("ESTADISTICAS");
			menu_Estadisticas();
			break; 
		case 2: 
			System.out.println("RETOS");
			menuRetos();
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
			menu_Lista();
			break; 
		
		}
	}
	
	private void menu_Estadisticas() {
	}
	private void menuRetos() {
		String reto;
		System.out.println();
		System.out.println("RETOS:");
		System.out.println("1. Crear reto");
		System.out.println("2. Marcar reto como completado");
		System.out.println("3. Consultar retos");
		System.out.println("4. Eliminar reto");
		System.out.println("5. Salir");
		System.out.println();

		System.out.println("¿Qué deseas hacer? Inserta la opcion deseada");
		opcion = sc.nextInt();

		switch(opcion){
		case 1: 
			System.out.println("Escriba el nombre del reto que desea crear: ");
			reto = sc.next();
			System.out.println("Escriba la descripción del reto: ");
			String descripcion = sc.next();
			System.out.println("Escriba el objetivo del reto: ");
			int objetivo = sc.nextInt();
			System.out.println("Escriba el tipo de reto: ");
			String tipo = sc.next();
			System.out.println("Escriba la recompensa del reto: ");
			float recompensa = sc.nextFloat();
			usuarioActual.makeChallenge(reto, descripcion, objetivo, tipo, recompensa);
			
			break;
		case 2:
			System.out.println("Escriba el nombre del reto que desea marcar como completado: ");
			reto = sc.next();
			break;
		case 3:
			System.out.println("Retos creados: ");
			// llamar a la lista para mostrar los retos creados
			usuarioActual.getDesafios();
			break;
		case 4:
			System.out.println("Escriba el nombre del reto que desea eliminar: ");
			reto = sc.next();
			//coleccion.eliminar(reto);
			break;
		case 5:
			System.out.println("Saliendo...");
			// llamar al menu de perfil de usuario
			break;
		
		}

	}
	public void menu_Amigo(){
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
			//usuarioActual.buscarAmigo(amigo);
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
					//usuarioActual.anyadirAmigo(sc, usuarioActual);
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
			usuarioActual.getListaAmigos();
			// se muestra la lista de amigos
			break;
		case 4:
			System.out.println("Saliendo...");
			// llamar al menu de perfil de usuario
			break;
		}
	}
	public void menuReseñas(){
		System.out.println("RESEÑAS");
		System.out.println("1. Seleccionar el libro del que desee hacer la reseña");
		System.out.println("2. Salir");
		System.out.println();
		System.out.println("¿Qué deseas hacer? Inserta la opcion deseada");
		opcion = sc.nextInt();

		switch(opcion){
		case 1: 
			System.out.println("Selecciona el libro del que deseas hacer la reseña: ");
			titulo = sc.next();
			// validar que el libro existe
			break;
		case 2:
			System.out.println("Saliendo...");
			// llamar al menu de perfil de usuario
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
	public void menu_Lista(){
		ColeccionLibro coleccion = new ColeccionLibro(nombreLista);

		System.out.println();
		System.out.println("1. Crear lista ");
		System.out.println("2. Eliminar lista");
		System.out.println("3. Cambiar nombre de una lista");
		System.out.println("4. Consultar todas las listas");
		System.out.println("5. Añadir libro a una lista");
		System.out.println("6. Mover libro de una lista a otra");
		System.out.println("7. Mostrar los libros de una lista");
		System.out.println("8. Eliminar libro de una lista");
		System.out.println("9. Salir");

		
		System.out.println("¿Qué deseas hacer? Inserta la opcion deseada");
		opcion = sc.nextInt();
		
		switch(opcion){
		case 1: 
			System.out.println("Escriba el nombre de la lista que desea crear: ");
			nombreLista = sc.next();
			listas.construirLista(nombreLista);
			break;
		case 2: 
			System.out.println("Seleccione la lista que desea eliminar: ");
			nombreLista = sc.next();
			listas.eliminarLista(nombreLista);
			break;
		case 3: 
			System.out.println("Seleccione la lista a la que desea cambiarle el nombre: ");
			nombreListaAntiguo = sc.next();
			System.out.println("Escriba el nuevo nombre: ");
			nombreLista = sc.next();
			listas.cambiarNombreLista(nombreListaAntiguo, nombreLista);
			if(listas.cambiarNombreLista(nombreListaAntiguo, nombreLista) == GestionColecciones.COLECCION_NO_EXISTE){
				System.out.println("La lista no existe");
			}
			if(listas.cambiarNombreLista(nombreListaAntiguo, nombreLista)==GestionColecciones.COLECCION_MODIFICADA_CORRECTAMENTE){
				System.out.println("El nombre de la lista ha sido modificado correctamente");
			}
			break;
		case 4: 
			System.out.println("LISTAS");
			listas.consultarListaColecciones();
			// mostrar todas las listas que tiene el usuario
			break;
		case 5:
		try{

			System.out.println("Escriba el nombre del libro que deseas añadir: ");
			titulo = sc.next();
			System.out.println("Escriba el nombre de la lista a la que desea añadirlo: ");
			nombreLista = sc.next();
			listas.agregarlibro(nombreLista,titulo);
			if(listas.agregarlibro(nombreLista,titulo) == GestionColecciones.COLECCION_NO_EXISTE ){
				System.out.println("La lista no existe");
			}
		}catch(Exception e){
			System.out.println("El libro no existe");
		}
			break; 
		case 6:
		try{

			System.out.println("Seleccione la lista de la que desea mover el libro: ");
			nombreListaAntiguo = sc.next();
			System.out.println("Seleccione la lista a la que desea moverlo o cree una nueva: ");
			nombreLista = sc.next();
			System.out.println("Seleccione el libro que desea mover: ");
			titulo = sc.next();
			listas.moverLibroDeColeccion(nombreListaAntiguo, nombreLista, titulo);
		}catch(Exception e){
			System.out.println("El libro no existe");
		}
			break;
		case 7:
			System.out.println("Libros agregados: ");
			coleccion.consultarLibrosEnLista();
			break;
		case 8:
			System.out.println("Escriba el nombre del libro que desea eliminar: ");
			titulo = sc.next();
			System.out.println("Seleccione la lista de la que desea eliminar el libro: ");
			nombreLista = sc.next();
			listas.eliminarLibro(nombreLista, titulo);
			if(listas.eliminarLibro(nombreLista, titulo) == 2){
				System.out.println("La lista no existe");
			}
			break; 
		case 9:
			System.out.println("Saliendo...");
			// llamar al menu de perfil de usuario
			break;
		default:
			System.out.println("Opción no válida");
			break;
		}
	}
	
	public void menu_Recomendaciones(){
		// mostrar las recomendaciones que tiene el usuario por parte de sus amigos
		System.out.println("RECOMENDACIONES");
		usuarioActual.getListaReviews();

	}
	
	public void menu_Logros(){
		// mostrar los logros que tiene el usuario
	}
	

	public static void main(String[] args) {
		menu_principal();
		
			

	}

}
