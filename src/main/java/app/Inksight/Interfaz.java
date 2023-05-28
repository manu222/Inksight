package app.Inksight;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.*;

public class Interfaz {
	static Scanner sc = new Scanner(System.in);
	String titulo;
	String nombreLista;
	String nombreListaAntiguo;
	int opcion;
	GestionColecciones listas = new GestionColecciones();
	Stats stats = new Stats();
	int timeout = 2000;
	static HashSet<String> listaAmigos = new HashSet<>();
	// Libro libro = new Libro(0,"","",0,"","");
	// Libro libro = new Libro(libro);
	Persona personaActual = new Usuario("", "", "", "", "", "", false, 0, stats, new HashSet<>(), new LinkedList<>());
	private Usuario usuarioActual = (Usuario) personaActual;

	DB db = new DB();
	String permiso;

	public void menu_principal() throws NoSuchAlgorithmException {
		System.out.println();
		System.out.println("-----INKSIGHT-----");
		System.out.println();
		System.out.println("1. Registrarse");
		System.out.println("2. Iniciar sesión");

		System.out.print("Elija la opción deseada: ");
		opcion = sc.nextInt();
		int opcionUser;
		String userType;
		String nombreUser;
		String pass;
		String correo;
		switch (opcion) {
			case 1:
				System.out.println("1) User");
				System.out.println("2) Moderador");
				System.out.println("3) Admin");

				opcionUser = sc.nextInt();
				if (opcionUser == 3) {
					userType = "admin";
				} else if (opcionUser == 2) {
					userType = "moderador";
				} else {
					userType = "user";
				}

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
				while (validPassword == false) {
					System.out.print("Ingrese una contraseña valida: ");
					pass = sc.next();
					if (pass.matches(regex)) {
						validPassword = true;
						System.out.println("Confirmar contraseña: ");
						String contraseñaConfirmada = sc.next();
						if (pass.matches(contraseñaConfirmada)) {
							DB db = new DB();
							MessageDigest digest = MessageDigest.getInstance("SHA-256");
							byte[] encodedhash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
							String passHash = db.bytesToHex(encodedhash);
							personaActual = db.createPersona(nombreUser, correo, passHash, userType);
						}
						if (personaActual instanceof Usuario) {
							usuarioActual = (Usuario) personaActual;
							usuarioActual.serializeToJson();
							menu_PerfilUsuario();
						} else {
							menu_principal();

						}

					} else {
						System.out.println("Contraseña no válida");
					}
				}
				break;
			case 2:
				boolean valid = false;
				while (!valid) {
					System.out.println("Ingresa nombre de usuario:");
					nombreUser = sc.next();
					System.out.println("Ingresa contraseña:");
					String passLogin = sc.next();
					personaActual = DB.personaCast(DB.buscarUser(nombreUser));
					if (personaActual instanceof Usuario) {
						usuarioActual = (Usuario) personaActual;
					}
					// asegurarse de que la contraseña pertenece al usuario

					MessageDigest digest = MessageDigest.getInstance("SHA-256");
					byte[] encodedhash = digest.digest(passLogin.getBytes(StandardCharsets.UTF_8));
					String passHash = DB.bytesToHex(encodedhash);

					if (personaActual.getPass().equals(passHash)) {
						System.out.println("Sesión Iniciada");

						permiso = ((personaActual instanceof Admin) ? "admin"
								: (personaActual instanceof Moderador ? "Moderador" : "usuario"));
						if (permiso.equals("usuario")) {
							usuarioActual = (Usuario) personaActual;
							usuarioActual.setOnline(true);
							checkTimeWhenLogin();
							if (usuarioActual.isBanned()) {
								System.out.println("El usuario seleccionado no puede acceder al sistema.");
								if (usuarioActual.getDaysUntilUnban() > 0) {
									System.out.println(
											"duración de la prohibicion: " + usuarioActual.getDaysUntilUnban());
								} else {
									System.out.println("El usuario está prohibido permanentemente.");
								}
								System.out.println("¿Desea iniciar sesión con otro usuario? (S/N)");
								String respuesta = sc.next();
								if (respuesta.equals("S")) {
									valid = false;
								} else {
									System.exit(0);
								}
							} else {
								usuarioActual.serializeToJson();
								valid = true;
								menu_PerfilUsuario();
							}

						}

					}
				}
				break;
			case 3:
				usuarioActual = (Usuario) DB.buscarUser("test");
				usuarioActual.setOnline(true);
				checkTimeWhenLogin();
				usuarioActual.serializeToJson();
				menu_PerfilUsuario();
				break;

		}
	}

	public void menu_PerfilUsuario() throws NoSuchAlgorithmException {
		System.out.println(usuarioActual.getFirst_name());
		System.out.println();
		System.out.println("1. Estadisticas");
		System.out.println("2. Desafios");
		System.out.println("3. Amigos");
		System.out.println("4. Hacer reseña");
		System.out.println("5. Marcar libro como leido");
		System.out.println("6. Listas");
		if (personaActual instanceof Admin) {
			System.out.println("7. Administrador");
			System.out.println("8. Cambiar de usuario");
		} else if (personaActual instanceof Moderador) {
			System.out.println("7. Buscar usuario (moderador)");
			System.out.println("8. Cambiar de usuario");
		} else {
			System.out.println("7. Cambiar de usuario");
		}

		System.out.println("¿Qué deseas hacer? Inserta la opcion deseada");
		opcion = sc.nextInt();

		switch (opcion) {
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
				boolean valid = false;
				while (!valid) {
					System.out.println("introduzca el titulo del libro o \"cancelar\"para abortar la operacion");
					titulo = sc.next();
					valid = titulo.toLowerCase().trim().equals("cancelar");
					// validar que el libro existe
					if (!valid) {
						Libro l;
						try {
							l = db.buscarLibro(titulo);
							valid = true;
						} catch (IOException e) {
							System.out.println("Libro no encontrado");
							break;
						}
						usuarioActual.libroTerminado(l);
						usuarioActual.serializeToJson();
						System.out.println("Libro marcado como leido");
					}
				}
				break;
			case 6:
				System.out.println("LISTAS ");
				menu_Lista();
				break;
			case 7:
				if (permiso.equals("admin")) {
					limpiarConDelay();
					System.out.println("ADMINISTRADOR");
					menu_Administrador();
				} else if (permiso.equals("moderador")) {
					limpiarConDelay();
					System.out.println("MODERADOR");
					menu_Moderador();
				} else {
					cerrarSesion();
				}
				break;
			case 8:
				cerrarSesion();
				break;
		}
	}

	private void cerrarSesion() {
		personaActual.setOnline(false);
		personaActual = null;
		usuarioActual = null;
		clearConsole();
		try {
			menu_principal();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	private void menu_Estadisticas() {
		clearConsole();
		System.out.println("Nivel: " + usuarioActual.getStats().getLevel());
		System.out.println("XP: " + usuarioActual.getStats().getXp());
		System.out.println("Xp para siguiente nivel: " + usuarioActual.getStats().getXpToNextLevel());
		System.out.println("Libros leidos: " + usuarioActual.getStats().getNumBooks());
		System.out.println("Pgs leidas: " + usuarioActual.getStats().getNumPages());
		// wait for enter key to advance. if q is pressed, go back
		System.out.println("Ingrese cualquier caracter para salir");
		String input;
		try {
			input = sc.next();
		} catch (Exception e) {
			input = "a";
		}
		if (input != null) {
			try {
				menu_PerfilUsuario();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

		}
	}

	private void menuRetos() {
		clearConsole();
		System.out.println("RETOS:");
		for (Challenge c : usuarioActual.getDesafios()) {
			clearConsole();
			System.out.println(c.toString());
			// wait for enter key to advance. if q is pressed, go back
			System.out.println("Presiona enter para continuar o q para salir");
			String input = sc.nextLine();
			if (input.equals("q")) {
				break;
			}
		}
		try {
			menu_PerfilUsuario();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public void menu_Amigo() {
		System.out.println("1. Agregar amigo");
		System.out.println("2. Borrar amigo");
		System.out.println("3. Consultar amigos");
		System.out.println("4. Salir");

		System.out.println();

		System.out.println("¿Qué deseas hacer? Inserta la opcion deseada");
		opcion = sc.nextInt();

		switch (opcion) {
			case 1:
				System.out.print("Escriba el nombre del amigo que desea agregar: ");
				String amigo = sc.next();
				usuarioActual.addAmigo(amigo);
				// usuarioActual.buscarAmigo(amigo);
				// comprobar si el nombre del amigo existe
				boolean existe = usuarioActual.addAmigo(amigo);
				;
				if (existe) {
					System.out.println("Amigo añadido de manera correcta");
					limpiarConDelay();
					menu_Amigo();
				}
				if (!existe) {
					System.out.println("Usuario no encontrado");
					System.out.println("¿Desea agregarlo?");
					System.out.println("1. Si");
					System.out.println("2. No");
					int numero = sc.nextInt();
					if (numero == 1) {
						usuarioActual.addAmigo(amigo);
						// usuarioActual.anyadirAmigo(sc, usuarioActual);
						boolean existe2 = usuarioActual.addAmigo(amigo);
						;
						if (existe2) {
							System.out.println("Amigo añadido de manera correcta");
						}
						if (!existe2) {
							System.out.println("Usuario no encontrado");
						}
						limpiarConDelay();
						menu_Amigo();

					} else if (numero == 2) {
						System.out.println("Amigo no agregado");
						limpiarConDelay();
						menu_Amigo();
					} else {
						System.out.println("No has elegido una opción correcta");
						limpiarConDelay();
						menu_Amigo();
					}
				}

				break;
			case 2:
				System.out.println("Ingresa el nombre del amigo que quieras borrar: ");
				amigo = sc.next();
				boolean borrado = usuarioActual.borrarAmigo(amigo);
				if (borrado) {
					System.out.println("Usuario borrado de lista de amigos");
					limpiarConDelay();
					menu_Amigo();
				}
				if (!borrado) {
					System.out.println("Usuario no existe vuelva a intentarlo.");
				}

				System.out.println("Ingresa el nombre del amigo que quieras borrar: ");
				amigo = sc.next();
				borrado = usuarioActual.borrarAmigo(amigo);
				if (borrado) {
					System.out.println("Usuario borrado de lista de amigos");
				}
				if (!borrado) {
					System.out.println("Usuario no existe vuelva a intentarlo.");
				}
				limpiarConDelay();
				menu_Amigo();

				// si el nombre ingresado no existe salta un aviso y deja volver a escribir el
				// nombre
				break;
			case 3:
				System.out.println("AMIGOS");
				usuarioActual.getListaAmigos();
				menu_Amigo();
				// se muestra la lista de amigos
				break;
			case 4:
				System.out.println("Saliendo...");
				// llamar al menu de perfil de usuario
				try {
					menu_PerfilUsuario();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				break;
		}
	}

	public void menuReseñas() throws IOException {
		System.out.println("RESEÑAS");
		System.out.println("1. Hacer reseña");
		System.out.println("2. Borrar Review");
		System.out.println("3. Ver Reviews");
		System.out.println("4. Salir");
		System.out.println("¿Qué deseas hacer? Inserta la opcion deseada");
		opcion = sc.nextInt();

		switch (opcion) {
			case 1:
				usuarioActual.addReview(sc);

				if (!usuarioActual.addReview(sc)) {
					System.out.println("Fallo al guardar la Review");
					limpiarConDelay();
					menuReseñas();
				}
				if (usuarioActual.addReview(sc)) {
					System.out.println("Review Guardada");
					limpiarConDelay();
					menuReseñas();
				}
				break;
			// validar que el libro existe

			case 2:
				System.out.println("Escoge que review quieres borrar");
				List<Review> reviews = usuarioActual.getListaReviews();
				int n = 0;
				for (Review review : reviews) {
					System.out.println("Review numero:" + n + " " + review.toString());
					n++;
				}
				System.out.println("Que review desea eliminar(numero)?");
				int k = sc.nextInt();
				reviews.remove(k);
				limpiarConDelay();
				menuReseñas();
				// llamar al menu de perfil de usuario
				break;

			case 3:

				List<Review> reviews2 = usuarioActual.getListaReviews();
				int c = 0;
				for (Review review : reviews2) {
					System.out.println("Review numero:" + c + " " + review.toString());
					c++;
				}
				limpiarConDelay();
				menuReseñas();

				break;
			case 4:
				System.out.println("Saliendo...");
				try {
					menu_PerfilUsuario();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
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

		switch (opcion) {
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

	public void menu_Lista() {
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

		switch (opcion) {
			case 1:
				System.out.println("Escriba el nombre de la lista que desea crear: ");
				nombreLista = sc.nextLine();
				listas.construirLista(nombreLista);
				break;
			case 2:
				System.out.println("Seleccione la lista que desea eliminar: ");
				nombreLista = sc.nextLine();
				listas.eliminarLista(nombreLista);
				break;
			case 3:
				System.out.println("Seleccione la lista a la que desea cambiarle el nombre: ");
				nombreListaAntiguo = sc.nextLine();
				System.out.println("Escriba el nuevo nombre: ");
				nombreLista = sc.nextLine();
				listas.cambiarNombreLista(nombreListaAntiguo, nombreLista);
				if (listas.cambiarNombreLista(nombreListaAntiguo,
						nombreLista) == GestionColecciones.COLECCION_NO_EXISTE) {
					System.out.println("La lista no existe");
				}
				if (listas.cambiarNombreLista(nombreListaAntiguo,
						nombreLista) == GestionColecciones.COLECCION_MODIFICADA_CORRECTAMENTE) {
					System.out.println("El nombre de la lista ha sido modificado correctamente");
				}
				break;
			case 4:
				System.out.println("LISTAS");
				for (String s : listas.consultarListaColecciones()) {
					System.out.println(s);
					// wait for input to show next list
					sc.nextLine();
				}
				limpiarConDelay();
				// mostrar todas las listas que tiene el usuario
				break;
			case 5:
				try {
					sc.nextLine();
					System.out.println("Escriba el nombre del libro que deseas añadir: ");
					titulo = sc.nextLine();
					clearConsole();
					System.out.println("Escriba el nombre de la lista a la que desea añadirlo: ");
					nombreLista = sc.nextLine();
					clearConsole();
					if (listas.agregarlibro(nombreLista, titulo) == GestionColecciones.COLECCION_NO_EXISTE) {
						System.out.println("La lista no existe");
					} else {
						System.out.println("El libro ha sido añadido correctamente");
						usuarioActual.serializeToJson();
					}
				} catch (Exception e) {
					System.out.println("El libro no existe");
				}
				break;
			case 6:
				try {
					clearConsole();
					System.out.println("Seleccione la lista de la que desea mover el libro: ");
					nombreListaAntiguo = sc.next();
					clearConsole();
					System.out.println("Seleccione la lista a la que desea moverlo o cree una nueva: ");
					nombreLista = sc.nextLine();
					clearConsole();
					System.out.println("Seleccione el libro que desea mover: ");
					titulo = sc.nextLine();
					listas.moverLibroDeColeccion(nombreListaAntiguo, nombreLista, titulo);
				} catch (Exception e) {
					System.out.println("El libro no existe");
				}
				break;
			case 7:
				System.out.println("Seleccione la lista de la que desea mostrar los libros: ");
				sc.nextLine();
				nombreLista = sc.nextLine();
				coleccion = usuarioActual.gestionColecciones.obtenerColeccion(nombreLista);
				for (Libro l : coleccion.consultarLibrosEnLista()) {
					System.out.println(l.getTitle());
				}
				break;
			case 8:
				System.out.println("Escriba el nombre del libro que desea eliminar: ");
				titulo = sc.nextLine();
				System.out.println("Seleccione la lista de la que desea eliminar el libro: ");
				nombreLista = sc.nextLine();
				listas.eliminarLibro(nombreLista, titulo);
				if (listas.eliminarLibro(nombreLista, titulo) == 2) {
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
		try {
			menu_PerfilUsuario();
		} catch (Exception e) {
			System.out.println("Opción no válida");
		}
	}

	public void checkTimeWhenLogin() {
		Date lastChallenge = usuarioActual.getLastChallenge();
		Date now = new Date(System.currentTimeMillis());
		// primer inicio de sesión?
		if (lastChallenge == null) {
			// asegurarse de que se va a crear un nuevo desafío
			usuarioActual.setLastChallenge(new Date(0));
		}
		if (usuarioActual.getLastLogin() == null) {
			usuarioActual.setLastLogin(now);
		}
		// revisar si el usuario está baneado después de reducir el tiempo restante de
		// la condena
		if ((now.getTime() - usuarioActual.getLastLogin().getTime()) < (24 * 60 * 60 * 1000)) {
			// pasó 1 día. disminuir la condena y si es 0, desbanear
			if (usuarioActual.dayPassed()) {
				usuarioActual.unban();
			}
		}
		// revisar todos los desafíos. si alguno expiró, eliminarlo. Si no, disminuir el
		// tiempo restante
		for (Challenge c : usuarioActual.getDesafios()) {
			if (c.getTimeRemaining() < (usuarioActual.getLastLogin().getTime() - now.getTime())) {
				usuarioActual.getDesafios().remove(c);
			} else {
				c.setTimeRemaining(
						(int) (c.getTimeRemaining() - (usuarioActual.getLastLogin().getTime() - now.getTime())));
			}
		}
		// actualizar la fecha de ultimo inicio de sesión
		usuarioActual.setLastLogin(now);

		// revisar si el usuario tiene menos de 3 desafíos y si pasaron 7 días desde el
		// último desafío
		if (lastChallenge.getTime() < (now.getTime() - 604800000) && usuarioActual.getDesafios().size() < 3) {
			// pasaron 7 días desde el último desafío
			int tipo = (int) (Math.random() * 3);
			int difficulty;
			switch (tipo) {
				// permite agregar todas las opciones de desafios que quieras, simplemente
				// agrega un nuevo case(permite ajustar pesos repitiendo numeros)
				/*
				 * //aceptar recomendaciones
				 * usuarioActual.makeChallenge("Aceptar una recomendación",
				 * "Lee un libro que haya sido recomendado por un amigo", 1, "accept", 100);
				 * usuarioActual.makeChallenge("Aceptar 2 recomendaciones",
				 * "Lee 2 libros que hayan sido recomendados por un amigo", 2, "accept", 250);
				 * //recomendar
				 * usuarioActual.makeChallenge("Recomienda un libro",
				 * "Expresa tu opinión sobre un libro. Comparte tu opinion con el mundo", 1,
				 * "recommend", 50);
				 * //leer de un autor
				 * difficulty = (int)(Math.random()*2+1);
				 * usuarioActual.makeChallenge("Leer"+ difficulty +"libros del mismo autor",
				 * "Lee libros del mismo autor. No tiene que ser una saga", difficulty,
				 * "author_repeat", 150*difficulty);
				 * //leer un autor nuevo
				 * usuarioActual.makeChallenge("Leer un libro de un autor nuevo",
				 * "Lee un libro de un autor que no hayas leido antes", 1, "author_new", 100);
				 * //leer un libro de mas de x paginas
				 * difficulty = ((int)(Math.random()*10))*10;
				 * usuarioActual.makeChallenge("Leer un libro de más de "+ difficulty
				 * +" páginas", "Lee un libro de más de "+ difficulty +" páginas", 1,
				 * "book_length", difficulty);
				 * 
				 */
				case 0:
					difficulty = (int) (Math.random() * 2);
					usuarioActual.makeChallenge("Leer" + difficulty * 5 + "libros",
							"Leer una determinada cantidad de libros", difficulty * 5, "libros", 50 * difficulty * 5);
					break;
				case 1:
					usuarioActual.makeChallenge("Leer 100 páginas", "Leer 100 páginas en una semana", 100, "paginas",
							100);
					break;
				case 2:
					usuarioActual.makeChallenge("Aceptar una recomendación",
							"Lee un libro que haya sido recomendado por un amigo", 1, "accept", 100);
					break;
				case 3:
					usuarioActual.makeChallenge("Aceptar 2 recomendaciones",
							"Lee 2 libros que hayan sido recomendados por un amigo", 2, "accept", 250);
					break;
				case 4:
				case 5:
					usuarioActual.makeChallenge("Recomienda un libro",
							"Expresa tu opinión sobre un libro. Comparte tu opinion con el mundo", 1,
							"recommend", 50);
					break;
				case 6:
					usuarioActual.makeChallenge("Recomienda un libro",
							"Expresa tu opinión sobre un libro. Comparte tu opinion con el mundo", 1,
							"recommend", 50);
					break;
				case 7:
					difficulty = ((int) (Math.random() * 10)) * 10;
					usuarioActual.makeChallenge("Leer un libro de más de " + difficulty
							+ " páginas", "Lee un libro de más de " + difficulty + " páginas", 1,
							"book_length", difficulty);
					break;
			}
		}
	}

	public void menu_Recomendaciones() {
		// mostrar las recomendaciones que tiene el usuario por parte de sus amigos
		System.out.println("RECOMENDACIONES");
		usuarioActual.getListaReviews();

	}

	public void menu_Logros() {
		// mostrar los logros que tiene el usuario
	}

	public void menu_Administrador() {
		// mostrar las opciones que tiene el administrador
		System.out.println("ADMINISTRADOR:");
		System.out.println("1. Gestionar Libros");
		System.out.println("2. Gestionar Usuarios");
		System.out.println("3. Crear nuevo moderador");
		System.out.println("4. Cerrar sesión");
		int opcion = sc.nextInt();
		switch (opcion) {
			case 1:
				try {
					Funciones.menuDBLibro();
				} catch (Exception e) {
					System.out.println("Error");
				}
				break;
			case 2:
				menu_Moderador();
				break;
			case 3:
				System.out.println("Ingrese el nombre del nuevo moderador");
				String nombre = sc.next();
				System.out.println("Ingrese el apellido del nuevo moderador");
				String apellido = sc.next();
				System.out.println("ingrese el nombre de usuario del nuevo moderador");
				String username = sc.next();
				System.out.println("ingrese la contraseña del nuevo moderador");
				String password = sc.next();
				System.out.println("ingrese el correo del nuevo moderador");
				String correo = sc.next();

				// todo crear el Moderador y guardarlo en la BDD
				break;
			case 4:
				cerrarSesion();

		}
	}

	public void menu_Moderador() {
		Moderador mod = (Moderador) personaActual;
		// mostrar las opciones que tiene el moderador
		System.out.println("ingrese el nombre del usuario que desea moderar");
		String nombreUsuario = sc.next();
		Persona victim = db.buscarUser(nombreUsuario);
		boolean canBeBanned = victim instanceof Usuario;

		if (victim == null) {
			System.out.println("El usuario no existe");
		} else {
			if (canBeBanned) {
				Usuario user = (Usuario) victim;
				System.out.println("usuario: " + victim.getFirst_name() + " " + victim.getLast_name());
				System.out.println("1. Suspender usuario");
				System.out.println(user.isBanned ? "2. Eliminar veto" : "2. vetar usuario");
				System.out.println("3. Ver reseñas");
				System.out.println("4. Cambiar usuario");
				int opcion = sc.nextInt();
				switch (opcion) {
					case 1:
						clearConsole();
						System.out.println("Ingrese la duración del veto: (cantidad + (días|semanas|meses|años))");
						String ans = sc.nextLine();
						mod.addBanDuration(user, parseTime(ans));
						System.out.println("Usuario vetado");
						limpiarConDelay();
						break;
					case 2:
						if (user.isBanned) {
							clearConsole();
							mod.unbanUser(user);
							System.out.println("Veto eliminado");
							limpiarConDelay();
							break;
						} else {
							mod.banUser(user);
							clearConsole();
							System.out.println("Usuario vetado.");
							limpiarConDelay();
						}
						break;
					case 3:
						clearConsole();
						List<Review> lista = user.getListaReviews();
						for (Review r : lista) {
							System.out.println(r);
							System.out.println("1. Eliminar reseña");
							System.out.println("2. Eliminar texto de la reseña");
							System.out.println("Presione Enter para ver la siguiente reseña o 3 para volver");
							ans = sc.nextLine();
							if (ans.equals("1")) {
								clearConsole();
								user.eliminarReview(r);
								System.out.println("Reseña eliminada");
								limpiarConDelay();
							} else if (ans.equals("2")) {
								clearConsole();
								r.setDescripcion("");
								System.out.println("Texto eliminado");
								limpiarConDelay();
							} else if (ans.equals("3")) {
								clearConsole();
								break;
							} else {
								clearConsole();
							}
						}

						break;
					case 4:
						cerrarSesion();
						break;
					default:
						System.out.println("Opción no válida");
						break;
				}
			} else {
				System.out.println("El usuario no puede ser moderado");
			}
		}
	}

	private int parseTime(String duration) {
		String[] parts = duration.split(" ");

		// obtener la cantidad de tiempo
		int amount = Integer.parseInt(parts[0]);

		// pasar a minuscula y eliminar acentos
		String unit = parts[1].toLowerCase(null);
		unit = Normalizer.normalize(unit, Normalizer.Form.NFD);
		unit = unit.replaceAll("[^\\p{ASCII}]", "");
		String[] seccion = unit.split("");

		// eliminar el plural
		if (seccion[seccion.length - 1].equals("s")) {
			unit = unit.substring(0, unit.length() - 1);
		}
		switch (unit) {
			case "día":
				break;
			case "semana":
				amount *= 7;
				break;
			case "mese":
				amount *= 30;
				break;
			case "año":
				amount *= 365;
				break;
			default:
				System.out.println("Unidad de tiempo no válida");
				break;
		}

		// la cantidad de tiempo en dias
		return amount;
	}

	public void limpiarConDelay() {
		// espera 2 segundos y limpia la consola
		try {
			Thread.sleep(timeout);
			clearConsole();
		} catch (InterruptedException e) {
			System.out.println("ocurrió un error inesperado");
		}
	}

	public static void clearConsole() {
		// limpia la consola
		try {
			final String os = System.getProperty("os.name");
			if (os.contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				System.out.print("\033[H\033[2J");
				System.out.flush();
			}
		} catch (final Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {

		Interfaz interfaz = new Interfaz();
		interfaz.menu_principal();
		// interfaz.menu_Estadisticas();

	}

}
