package app.Inksight;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.*;

/**
 * The type Interfaz.
 */
public class Interfaz {
	public Interfaz() {

	}

	/**
	 * The Sc.
	 */
	static Scanner sc = new Scanner(System.in);
	/**
	 * The Titulo.
	 */
	String titulo;
	/**
	 * The Nombre lista.
	 */
	String nombreLista;
	/**
	 * The Nombre lista antiguo.
	 */
	String nombreListaAntiguo;
	/**
	 * The Opcion.
	 */
	int opcion;
	/**
	 * The Listas.
	 */
	GestionColecciones listas = new GestionColecciones();
	/**
	 * The Stats.
	 */
	Stats stats = new Stats();
	/**
	 * The Timeout.
	 */
	int timeout = 2000;

	/**
	 * The Persona actual.
	 */
	Persona personaActual = new Usuario("", "", "", "", "", "", false, 0, stats, new HashSet<>(), new LinkedList<>(),
			0);
	private Usuario usuarioActual = (Usuario) personaActual;

	/**
	 * The Db.
	 */
	DB db = new DB();
	/**
	 * The Permiso.
	 */
	String permiso;

	/**
	 * Menu principal.
	 *
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws IOException              the io exception
	 */
	public void menu_principal() throws NoSuchAlgorithmException, IOException {
		boolean ok = false;
		while (!ok) {
			clearConsole();
			System.out.println();
			System.out.println("-----INKSIGHT-----");
			System.out.println();
			System.out.println("1. Registrarse");
			System.out.println("2. Iniciar sesión");

			System.out.print("Elija la opción deseada: ");

			try{
				opcion = sc.nextInt();
				ok = opcion >= 1 && opcion <= 2;
				sc.nextLine();
			}catch(InputMismatchException e){
				System.out.println("Intente de nuevo, opcion invalida");
				sc.nextLine();
			}
		}

		int opcionUser = 0;
		String userType;
		String nombreUser;
		String pass;
		String correo;
		switch (opcion) {
			case 1:
				boolean ok2=false;
				while (!ok2){
					System.out.println("1) User");
					System.out.println("2) Moderador");
					System.out.println("3) Admin");
					try {
						opcionUser=sc.nextInt();
						ok2= opcionUser >= 1 && opcionUser <= 3;
						sc.nextLine();
					}catch (InputMismatchException e){
						System.out.println("Intente de nuevo, opcion invalida");
						sc.nextLine();
					}
				}

				clearConsole();
				if (opcionUser == 3) {
					userType = "admin";
				} else if (opcionUser == 2) {
					userType = "moderador";
				} else {
					userType = "user";
				}

				System.out.print("Ingrese un nombre de usuario: ");
				nombreUser = sc.nextLine();
				if(DB.buscarUser(nombreUser)!=null) {
					System.out.println("El nombre de usuario ya existe");
					limpiarConDelay();
					menu_principal();
				}
				clearConsole();
				System.out.print("Ingrese un correo electronico: ");
				correo = sc.nextLine();

				// aviso de los requisitos que tiene que tener la contraseña
				System.out.println("ingrese una contraseña:");
				System.out.println("Debe tener entre 8 y 20 caracteres");
				System.out.println("Debe contener al menos un dígito");
				System.out.println("Debe contener al menos una minúscula");
				System.out.println("Debe contener al menos una mayúscula");
				System.out.println("No debe contener espacios en blanco");
				System.out.println("No debe contener caracteres especiales");

				String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
				boolean validPassword = false;
				while (!validPassword) {
					System.out.print("Ingrese una contraseña valida: ");
					pass = sc.nextLine();
					if (pass.matches(regex)) {
						validPassword = true;
						clearConsole();
						System.out.println("Confirmar contraseña: ");
						String passConfirmada = sc.nextLine();
						if (pass.matches(passConfirmada)) {
							DB db = new DB();
							MessageDigest digest = MessageDigest.getInstance("SHA-256");
							byte[] encodedhash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
							String passHash = db.bytesToHex(encodedhash);
							clearConsole();
							personaActual = db.createPersona(nombreUser, correo, passHash, userType);
						}
						if (personaActual instanceof Usuario) {
							usuarioActual = (Usuario) personaActual;
							checkTimeWhenLogin();
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
				clearConsole();
				boolean valid = false;
				while (!valid) {
					boolean valid2 = false;
					while (!valid2) {
						System.out.println("Ingresa nombre de usuario:");
						nombreUser = sc.nextLine();
						personaActual = DB.personaCast(DB.buscarUser(nombreUser));
						clearConsole();
						if (personaActual != null) {
							valid2 = true;
						}
					}
					boolean valid3 = false;
					while (!valid3) {
						clearConsole();
						System.out.println("Ingresa contraseña:");
						String passLogin = sc.nextLine();
						if (personaActual instanceof Usuario) {
							usuarioActual = (Usuario) personaActual;
						}
						// asegurarse de que la contraseña pertenece al usuario

						MessageDigest digest = MessageDigest.getInstance("SHA-256");
						byte[] encodedhash = digest.digest(passLogin.getBytes(StandardCharsets.UTF_8));
						String passHash = DB.bytesToHex(encodedhash);

						if (personaActual.getPass().equals(passHash)) {

							valid = true;
							valid3 = true;
							permiso = ((personaActual instanceof Admin) ? "admin"
									: (personaActual instanceof Moderador ? "Moderador" : "usuario"));
							if (permiso.equals("usuario")) {
								usuarioActual = (Usuario) personaActual;
								usuarioActual.setOnline(true);
								checkTimeWhenLogin();
								if (usuarioActual.isBanned()||usuarioActual.getDaysUntilUnban()>0) {
									System.out.println("El usuario seleccionado no puede acceder al sistema.");
									if (usuarioActual.getDaysUntilUnban() > 0) {
										System.out.println(
												"duración de la prohibicion: " + usuarioActual.getDaysUntilUnban());
									} else {
										System.out.println("El usuario está prohibido permanentemente.");
									}
									System.out.println("¿Desea iniciar sesión con otro usuario? (S/N)");
									String respuesta = sc.nextLine();
									if (respuesta.split("")[0].equalsIgnoreCase("S")) {
										valid = false;
									} else {
										System.exit(0);
									}
								} else {
									System.out.println("Sesión Iniciada");
									usuarioActual.serializeToJson();
									valid = true;
									menu_PerfilUsuario();
								}

							} else if (permiso.equals("admin")) {
								menu_Administrador();
							} else {
								menu_Moderador();
							}
						}
					}
				}
				break;
		}
	}

	/**
	 * Menu perfil usuario.
	 *
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws IOException              the io exception
	 */
	public void menu_PerfilUsuario() throws NoSuchAlgorithmException, IOException {
		clearConsole();
		boolean valid3=false;
		while (!valid3) {
			System.out.println(usuarioActual.getFirst_name());
			System.out.println();
			System.out.println("1. Estadisticas");
			System.out.println("2. Desafios");
			System.out.println("3. Amigos");
			System.out.println("4. Reseñas");
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
			try {
				System.out.println("¿Qué deseas hacer? Inserta la opcion deseada");
				opcion = sc.nextInt();
				valid3= opcion >= 1 && opcion <= 7;

			}catch (InputMismatchException e){
				System.out.println("Ingrese algo valido");
			}
			sc.nextLine();
		}
		switch (opcion) {
			case 1:
				System.out.println("ESTADISTICAS");
				menu_Estadisticas();
				break;
			case 2:
				menuRetos();
				break;
			case 3:
				System.out.println("AMIGOS");
				menu_Amigo();
				break;
			case 4:
				clearConsole();
				menu_Reviews();

				break;
			case 5:
				boolean valid = false;
				while (!valid) {
					System.out.println("introduzca el titulo del libro o 'cancelar' para abortar la operacion");
					titulo = sc.nextLine();
					valid = titulo.toLowerCase().trim().equals("cancelar");
					// validar que el libro existe
					if (!valid) {
						Libro l;
						try {
							l = db.buscarLibro(titulo);
							valid = true;
							if(!l.getTitle().equalsIgnoreCase("error")){
								//existe el libro
								usuarioActual.libroTerminado(l);
								usuarioActual.serializeToJson();
							}
							else{
								System.out.println("Libro no encontrado");
								limpiarConDelay();
								break;
							}
						} catch (IOException e) {
							System.out.println("Libro no encontrado");
							break;
						}

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
		menu_PerfilUsuario();
	}

	private void cerrarSesion() {
		personaActual.setOnline(false);
		personaActual = null;
		usuarioActual = null;
		clearConsole();
		try {
			try {
				menu_principal();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	private void menu_Estadisticas() {
		// HelloWorld("System.out.println");
		clearConsole();
		System.out.println("Nivel: " + usuarioActual.getStats().getLevel());
		System.out.println("XP: " + usuarioActual.getStats().getXp());
		System.out.println("Xp para siguiente nivel: " + usuarioActual.getStats().getXpToNextLevel());
		System.out.println("Libros leidos: " + usuarioActual.getStats().getNumBooks());
		System.out.println("Pgs leidas: " + usuarioActual.getStats().getNumPages());
		System.out.println("Ingrese cualquier caracter para salir");
		String input;
		try {
			input = sc.nextLine();
		} catch (Exception e) {
			input = "a";
		}
		if (input != null) {
			try {
				try {
					menu_PerfilUsuario();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

		}
	}

	private void menuRetos() {
		clearConsole();
		if (usuarioActual.getDesafios().size() == 0) {
			System.out.println("No tienes desafios pendientes");
			limpiarConDelay();
		} else {

			for (Challenge c : usuarioActual.getDesafios()) {
				System.out.println(c.toString());
				// wait for enter key to advance. if q is pressed, go back
				System.out.println("Presiona enter para continuar o q para salir");
				String input = sc.nextLine();
				if (input.equals("q")) {
					break;
				}
			}
		}
		try {
			try {
				clearConsole();
				menu_PerfilUsuario();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Menu amigo.
	 */
	public void menu_Amigo() {
		clearConsole();
		boolean valid4=false;
		while (!valid4) {
			System.out.println("1. Agregar amigo");
			System.out.println("2. Borrar amigo");
			System.out.println("3. Consultar amigos");
			System.out.println("4. Salir");

			System.out.println("¿Qué deseas hacer? Inserta la opcion deseada");
				try {
					opcion = sc.nextInt();
					valid4 = opcion>=1 && opcion<=4;
					sc.nextLine();
				} catch (InputMismatchException e) {
					System.out.println("Intente de nuevo, opcion invalida");
					sc.nextLine();
				}


		}
		switch (opcion) {
			case 1:
				clearConsole();
				System.out.print("Escriba el nombre del amigo que desea agregar: ");
				String amigo = sc.nextLine();
				boolean existe = usuarioActual.addAmigo(amigo);

				usuarioActual.serializeToJson();
				// usuarioActual.buscarAmigo(amigo);
				// comprobar si el nombre del amigo existe
				if (existe) {
					System.out.println("Amigo añadido de manera correcta");
					Usuario UnewAmigo = DB.buscarUser(amigo);
					UnewAmigo.addAmigo(usuarioActual.getNombreUser());
					UnewAmigo.serializeToJson();
					limpiarConDelay();
					clearConsole();
					menu_Amigo();
				}
				if (!existe) {
					System.out.println("Usuario no encontrado");
					limpiarConDelay();
					menu_Amigo();
				}

				break;
			case 2:
				clearConsole();
				System.out.println("Ingresa el nombre del amigo que quieras borrar: ");
				amigo = sc.nextLine();
				boolean borrado = usuarioActual.borrarAmigo(amigo);
				if (borrado) {
					Usuario Uamigo = DB.buscarUser(amigo);
					Uamigo.borrarAmigo(usuarioActual.getNombreUser());
					Uamigo.serializeToJson();
					System.out.println("Usuario borrado de lista de amigos");
					usuarioActual.serializeToJson();
					limpiarConDelay();
					menu_Amigo();
				}
				if (!borrado) {
					System.out.println("Usuario no existe vuelva a intentarlo.");
				}

				System.out.println("Ingresa el nombre del amigo que quieras borrar: ");
				amigo = sc.nextLine();
				borrado = usuarioActual.borrarAmigo(amigo);
				if (borrado) {
					System.out.println("Usuario borrado de lista de amigos");
				}
				if (!borrado) {
					System.out.println("Usuario no existe vuelva a intentarlo.");
				}
				limpiarConDelay();
				clearConsole();
				menu_Amigo();

				// si el nombre ingresado no existe salta un aviso y deja volver a escribir el
				// nombre
				break;
			case 3:
				clearConsole();
				System.out.println("AMIGOS");
				Usuario target;
				for (String s : usuarioActual.getListaAmigos()) {
					boolean valid5=false;
					while (!valid5) {
						System.out.println(s);
						System.out.println("1. Ver perfil");
						System.out.println("2. Ver reseñas");
						System.out.println("3. siguiente");
						System.out.println("4. salir");
						try {
							opcion = sc.nextInt();
							valid5 = opcion >= 1 && opcion <= 4;
							sc.nextLine();
						} catch (InputMismatchException e) {
							System.out.println("Ingrese una opcion valida");
							sc.nextLine();
						}

					}
					switch (opcion) {
						case 1:
							boolean valid6=false;
							while (!valid6) {
								clearConsole();
								target = DB.buscarUser(s);
								System.out.println("PERFIL");
								System.out.println("Nombre: " + target.getFirst_name());
								System.out.println("Apellido: " + target.getLast_name());
								System.out.println(target.getStats());
								System.out.println("1. Volver");
								int opcion;
								try {
									opcion = sc.nextInt();
									valid6 = opcion == 1;
								} catch (InputMismatchException e) {
									System.out.println("Intente de nuevo, opcion invalida");
									sc.nextLine();
								}

							}
							break;
						case 2:
							// se muestra la lista de reseñas
							target = DB.buscarUser(s);
							clearConsole();
							List<Review> lista = target.getListaReviews();
							for (Review r : lista) {
								System.out.println(r);
								System.out.println("presione q para salir o cualquier tecla para ver la siguiente");
								String opcion3 = sc.nextLine();
								if (opcion3.equals("q")) {
									clearConsole();
									menu_Amigo();
								}
							}
							break;
						case 3:
							clearConsole();
							break;
						case 4:
							clearConsole();
							menu_Amigo();
							break;
					}
				}

				clearConsole();
				menu_Amigo();
				// se muestra la lista de amigos
				break;
			case 4:
				clearConsole();
				System.out.println("Saliendo...");
				limpiarConDelay();
				// llamar al menu de perfil de usuario
				try {
					try {
						menu_PerfilUsuario();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				break;
		}
	}

	/**
	 * Menu reseñas.
	 *
	 * @throws IOException the io exception
	 */
	public void menu_Reviews() throws IOException {
		boolean valid =false;
		opcion=0;
		while (!valid) {
			clearConsole();
			System.out.println("RESEÑAS");
			System.out.println("1. Hacer reseña");
			System.out.println("2. Borrar Review");
			System.out.println("3. Ver Reviews");
			System.out.println("4. Salir");
			System.out.println("¿Qué deseas hacer? Inserta la opcion deseada");
			try {
				opcion = sc.nextInt();
				valid = opcion>=1 && opcion<=4;

			}catch (InputMismatchException e){
				System.out.println("Intente de nuevo, opcion invalida");

			}
			sc.nextLine();
		}
		switch (opcion) {
			case 1:
				clearConsole();
				if (!usuarioActual.addReview(sc)) {
					System.out.println("Fallo al guardar la Review");
					limpiarConDelay();
					menu_Reviews();
				} else {
					System.out.println("Review Guardada");
					usuarioActual.serializeToJson();
					limpiarConDelay();
					menu_Reviews();
				}
				break;
			// validar que el libro existe

			case 2:
				List<Review> del = new ArrayList<Review>();
				boolean valid2=false;
				int opcion = 0;
				while (!valid2) {
					clearConsole();
					for (Review review : usuarioActual.getListaReviews()) {
						System.out.println(review.toString());
						System.out.println("1.Eliminar");
						System.out.println("2.Siguiente");
						System.out.println("3.Salir");
						try {
							opcion = sc.nextInt();
							valid2 = opcion>=1 && opcion<=3;
							sc.nextLine();
						}catch (InputMismatchException e){
							System.out.println("Opcion invalida");
							sc.nextLine();
						}

						switch (opcion) {
							case 1:
								del.add(review);
								System.out.println("Review borrada");
								break;
							case 2:
								break;
							case 3:
								usuarioActual.getListaReviews().removeAll(del);
								menu_Reviews();
								break;
						}
					}
				}
				usuarioActual.getListaReviews().removeAll(del);
				usuarioActual.serializeToJson();
				limpiarConDelay();
				menu_Reviews();
				break;

			case 3:
				clearConsole();
				List<Review> reviews2 = usuarioActual.getListaReviews();
				int c = 0;
				for (Review review : reviews2) {
					System.out.println("Review numero:" + c + " " + review.toString());
					c++;
				}
				limpiarConDelay();
				menu_Reviews();

				break;
			case 4:
				clearConsole();
				System.out.println("Saliendo...");
				limpiarConDelay();
				try {
					menu_PerfilUsuario();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				// llamar al menu de perfil de usuario
				break;
		}

	}

	/**
	 * Menu lista.
	 *
	 * @throws IOException the io exception
	 */
	public void menu_Lista() throws IOException {
		boolean valid7 = false;
		while (!valid7) {
			clearConsole();
			listas = usuarioActual.gestionColecciones;
			System.out.println();
			System.out.println("1. Crear lista ");
			System.out.println("2. Eliminar lista");
			System.out.println("3. Cambiar nombre de una lista");
			System.out.println("4. Consultar todas las listas");
			System.out.println("5. Añadir libro a una lista");
			System.out.println("6. Mover libro de una lista a otra");
			System.out.println("7. Eliminar libro de una lista");
			System.out.println("8. Salir");

			System.out.println("¿Qué deseas hacer? Inserta la opcion deseada");
			try{
				opcion = sc.nextInt();
				valid7 = opcion>=1 && opcion<=8;
				sc.nextLine();
			}catch (InputMismatchException e){
				System.out.println("Ingresa opcion valida");
				sc.nextLine();
			}
		}
		switch (opcion) {
			case 1:
				clearConsole();
				System.out.println("Escriba el nombre de la lista que desea crear: ");
				nombreLista = sc.nextLine();
				try {
					if (!usuarioActual.gestionColecciones.construirLista(nombreLista)){
						throw new Exception();
					}
					System.out.println(nombreLista + " creada");
				} catch (Exception e) {
					System.out.println("La lista ya existe");
				}
				usuarioActual.serializeToJson();
				limpiarConDelay();
				break;
			case 2:
				clearConsole();
				System.out.println("Seleccione la lista que desea eliminar: ");
				nombreLista = sc.nextLine();
				try {
					if (!usuarioActual.gestionColecciones.eliminarLista(nombreLista)){
						throw new Exception();
					}
					System.out.println(nombreLista + " eliminada");
				} catch (Exception e) {
					System.out.println("La lista no existe");
				}
				usuarioActual.serializeToJson();
				limpiarConDelay();
				break;
			case 3:
				clearConsole();
				System.out.println("Seleccione la lista a la que desea cambiarle el nombre: ");
				nombreListaAntiguo = sc.nextLine();
				System.out.println("Escriba el nuevo nombre: ");
				nombreLista = sc.nextLine();
				int respuesta = listas.cambiarNombreLista(nombreListaAntiguo, nombreLista);
				if (respuesta == GestionColecciones.COLECCION_NO_EXISTE) {
					System.out.println("La lista no existe");
				}
				if (respuesta == GestionColecciones.COLECCION_MODIFICADA_CORRECTAMENTE) {
					System.out.println("El nombre de la lista ha sido modificado correctamente");
				} else {
					System.out.println("No se pudo modificar la lista");
				}
				usuarioActual.serializeToJson();
				limpiarConDelay();
				break;
			case 4:
				for (String s : listas.consultarListaColecciones()) {
					clearConsole();
					System.out.println("LISTA:");
					System.out.println(s);
					System.out.println("1. para ver los libros en la lista");
					System.out.println("2. para siguiente lista");
					System.out.println("3. volver");
					// wait for input to show next list
					String in = sc.nextLine();

					if (in.equalsIgnoreCase("1")) {
						for (Libro l : usuarioActual.gestionColecciones.obtenerColeccion(s).getListaLibros()) {
							System.out.println(l.getTitle());
						}
						System.out.println("------------------");
					} else {
						if (!in.equalsIgnoreCase("2")) {
							break;
						}
					}
				}
				limpiarConDelay();
				// mostrar todas las listas que tiene el usuario
				break;
			case 5:
				clearConsole();
				try {
					System.out.println("Escriba el nombre del libro que deseas añadir: ");
					titulo = sc.nextLine();
					System.out.println("Escriba el nombre de la lista a la que desea añadirlo: ");
					nombreLista = sc.nextLine();
					// validar que la lista existe
					if (listas.agregarlibro(nombreLista, titulo) == GestionColecciones.COLECCION_NO_EXISTE) {
						System.out.println("La lista no existe");
					}
					// el libro esta en la base de datos pero no en la lista, se agrega
					if (listas.agregarlibro(nombreLista, titulo) == GestionColecciones.LIBRO_AGREGADO_CORRECTAMENTE) {
						System.out.println("El libro ha sido añadido correctamente");
					}
					// el libro ya esta en la lista
					if (listas.agregarlibro(nombreLista, titulo) == GestionColecciones.LIBRO_YA_EXISTE) {
						System.out.println("El libro ya existe en la lista");
					}
					// el libro no esta en la base de datos
					if (listas.agregarlibro(nombreLista, titulo) == GestionColecciones.LIBRO_NO_EXISTE) {
						System.out.println("El libro no esta disponible en la base de datos");
					}
				} catch (Exception e) {
					System.out.println("El libro no existe");
				}
				usuarioActual.serializeToJson();
				limpiarConDelay();
				break;
			case 6:
				clearConsole();
				try {
					sc.nextLine();
					System.out.println("Seleccione la lista de la que desea mover el libro: ");
					nombreListaAntiguo = sc.nextLine();
					System.out.println("Seleccione la lista a la que desea moverlo o cree una nueva: ");
					nombreLista = sc.nextLine();
					System.out.println("Seleccione el libro que desea mover: ");
					titulo = sc.nextLine();
					// validar que la lista existe
					int respuesta2 = listas.moverLibroDeColeccion(nombreListaAntiguo, nombreLista, titulo);

					if (respuesta2 == GestionColecciones.COLECCION_NO_EXISTE) {
						System.out.println("La lista no existe");
					}
					// el libro no esta en la base de datos
					if (respuesta2 == GestionColecciones.LIBRO_NO_EXISTE) {
						System.out.println("El libro no esta disponible en la base de datos");
					}
					// el libro esta en la base de datos pero ya existe en la lista a la que lo
					// quiere mover
					if (respuesta2 == GestionColecciones.LIBRO_YA_EXISTE) {
						System.out.println("El libro ya existe en la lista a la que lo quiere mover");
					}
					// el libro esta en la base de datos y no existe en la lista a la que lo quiere
					// mover
					if (respuesta2 == GestionColecciones.COLECCION_MODIFICADA_CORRECTAMENTE) {
						System.out.println("El libro ha sido movido correctamente");
					}
				} catch (Exception e) {
					System.out.println("El libro no existe");
				}
				usuarioActual.serializeToJson();
				limpiarConDelay();
				break;
			case 7:
				clearConsole();
				System.out.println("Escriba el nombre del libro que desea eliminar: ");
				titulo = sc.nextLine();
				System.out.println("Seleccione la lista de la que desea eliminar el libro: ");
				nombreLista = sc.nextLine();
				// validar que la lista existe
				int respuesta3 = listas.eliminarLibro(nombreLista, titulo);
				if (respuesta3 == GestionColecciones.COLECCION_NO_EXISTE) {
					System.out.println("La lista no existe");
				}
				// el libro no esta en la lista
				if (respuesta3 == GestionColecciones.LIBRO_NO_EXISTE) {
					System.out.println("El libro no existe en la lista");
				}
				// el libro esta en la lista
				if (respuesta3 == GestionColecciones.LIBRO_ELIMINADO_CORRECTAMENTE) {
					System.out.println("El libro ha sido eliminado correctamente");
				}
				usuarioActual.serializeToJson();
				limpiarConDelay();
				menu_Lista();
				break;
			case 8:
				clearConsole();
				System.out.println("Saliendo...");
				// llamar al menu de perfil de usuario
				limpiarConDelay();
				try {
					menu_PerfilUsuario();
				} catch (NoSuchAlgorithmException e) {
					throw new RuntimeException(e);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
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

	/**
	 * Check time when login.
	 */
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
			usuarioActual.setLastChallenge(new Date(0));
		}

		// revisar si el usuario está baneado después de reducir el tiempo restante de
		// la condena
		if ((now.getTime() - usuarioActual.getLastLogin().getTime()) < (24 * 60 * 60 * 1000)
				&& usuarioActual.isBanned()) {
			// pasó 1 día. disminuir la condena y si es 0, desbanear
			for (int i = 0; i < (int) ((now.getTime() - usuarioActual.getLastLogin().getTime())
					/ (24 * 60 * 60 * 1000)); i++) {
				if (usuarioActual.dayPassed()) {
					usuarioActual.unban();
				}
			}
			usuarioActual.setLastLogin(now);
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
		if (!usuarioActual.isBanned)
			usuarioActual.setLastLogin(now);

		// revisar si el usuario tiene menos de 3 desafíos y si pasaron 7 días desde el
		// último desafío
		if (usuarioActual.getLastChallenge().getTime() <= (now.getTime() - 7 * 24 * 60 * 60 * 1000)
				&& usuarioActual.getDesafios().size() < 3) {
			// pasaron 7 días desde el último desafío
			int tipo = (int) (Math.random() * 7);
			int difficulty;
			switch (tipo) {
				// permite agregar todas las opciones de desafios que quieras, simplemente
				// agrega un nuevo case(permite ajustar pesos repitiendo numeros)

				case 0:
					difficulty = (int) (Math.random() * 2);
					usuarioActual.makeChallenge("Leer" + difficulty * 5 + "libros",
							"Leer una determinada cantidad de libros", difficulty * 5, "libros", 50 * difficulty * 5,
							true, 7);
					break;
				case 1:
					usuarioActual.makeChallenge("Leer 100 páginas", "Leer 100 páginas en una semana", 100, "paginas",
							100, true, 7);
					break;
				case 2:
					usuarioActual.makeChallenge("Aceptar una recomendación",
							"Lee un libro que haya sido recomendado por un amigo", 1, "accept", 100, true, 7);
					break;
				case 3:
					usuarioActual.makeChallenge("Aceptar 2 recomendaciones",
							"Lee 2 libros que hayan sido recomendados por un amigo", 2, "accept", 250, true, 7);
					break;
				case 4:
				case 5:
					usuarioActual.makeChallenge("Recomienda un libro",
							"Expresa tu opinión sobre un libro. Comparte tu opinion con el mundo", 1,
							"recommend", 50, true, 7);
					break;
				case 6:
					usuarioActual.makeChallenge("Recomienda un libro",
							"Expresa tu opinión sobre un libro. Comparte tu opinion con el mundo", 1,
							"recommend", 50, true, 7);
					break;
				case 7:
					difficulty = ((int) (Math.random() * 10)) * 10;
					usuarioActual.makeChallenge("Leer un libro de más de " + difficulty
							+ " páginas", "Lee un libro de más de " + difficulty + " páginas", 1,
							"book_length", difficulty, true, 7);
					break;
			}
		}

	}

	/**
	 * Menu recomendaciones.
	 */
	public void menu_Recomendaciones() {
		// mostrar las recomendaciones que tiene el usuario por parte de sus amigos
		System.out.println("RECOMENDACIONES");
		usuarioActual.getListaReviews();

	}

	/**
	 * Menu logros.
	 */
	public void menu_Logros() {
		// mostrar los logros que tiene el usuario
	}

	/**
	 * Menu administrador.
	 *
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 */
	public void menu_Administrador() throws NoSuchAlgorithmException {
		boolean valid = false;
		int opcion=0;
		while (!valid) {
			clearConsole();
			// mostrar las opciones que tiene el administrador
			System.out.println("ADMINISTRADOR:");
			System.out.println("1. Gestionar Libros");
			System.out.println("2. Gestionar Usuarios");
			System.out.println("3. Cerrar sesión");
			try {
				opcion=sc.nextInt();
				valid = opcion>=1 && opcion<=3;
				sc.nextLine();
			}catch (InputMismatchException e){
				System.out.println("Intente de nuevo, opcion invalida");
				sc.nextLine();
			}
		}
		switch (opcion) {
			case 1:
				clearConsole();
				try {
					Funciones.menuDBLibro();
					menu_Administrador();
				} catch (Exception e) {
					System.out.println("Error");
					menu_Administrador();
				}
				menu_Administrador();
				break;
			case 2:
				clearConsole();
				menu_AdminMod();
				break;
			case 3:
				cerrarSesion();

		}
	}

	/**
	 * Menu moderador.
	 */
	public void menu_Moderador() {
		clearConsole();
		Moderador mod = (Moderador) personaActual;
		// mostrar las opciones que tiene el moderador
		boolean valid = false;
		while (!valid) {

			System.out.println("ingrese el nombre del usuario que desea moderar o q para cambiar de usuario");
			String nombreUsuario = sc.nextLine();
			if (nombreUsuario.equals("q")) {
				clearConsole();
				try {
					menu_principal();
				} catch (Exception e) {
					System.out.println("Error");
				}
			}
			Persona victim = db.buscarUser(nombreUsuario);
			boolean canBeBanned = victim instanceof Usuario;

			if (victim == null) {
				System.out.println("El usuario no existe");
			} else
				valid = true;
			clearConsole();
			if (canBeBanned && valid) {
				Usuario user = (Usuario) victim;
				valid = false;
				int opcion = 0;
				while (!valid) {
					System.out.println("usuario: " + victim.getFirst_name() + " " + victim.getLast_name());
					System.out.println(user.getDaysUntilUnban()>0?"1. quitar suspensión":"1. Suspender usuario");
					System.out.println(user.isBanned ? "2. Eliminar veto" : "2. vetar usuario");
					System.out.println("3. Ver reseñas");
					System.out.println("4. Cambiar usuario a moderar");
					try {
						opcion = sc.nextInt();
						valid = opcion>=1 && opcion<=4;

					} catch (Exception e) {
						System.out.println("Ingrese una opción válida");
					}
					sc.nextLine();
				}
				switch (opcion) {
					case 1:
					if(user.getDaysUntilUnban()>0){
						user.setDaysUntilUnban(0);
						user.setIsBanned(false);
						user.serializeToJson();
						clearConsole();
						System.out.println("Usuario reincorporado");
						limpiarConDelay();
						menu_Moderador();
						break;
					}
						clearConsole();
						System.out.println("Ingrese la duración del veto: (cantidad + (días|semanas|meses|años))");
						String ans = sc.nextLine();
						mod.addBanDuration(user, parseTime(ans));
						System.out.println("Usuario vetado");
						user.serializeToJson();
						limpiarConDelay();
						menu_Moderador();
						break;
					case 2:
						if (user.isBanned) {
							clearConsole();
							mod.unbanUser(user);
							System.out.println("Veto eliminado");
							user.serializeToJson();
							limpiarConDelay();
							menu_Moderador();
							break;
						} else {
							mod.banUser(user);
							clearConsole();
							System.out.println("Usuario vetado.");
							user.serializeToJson();
							limpiarConDelay();
							menu_Moderador();
						}
						break;
					case 3:
						clearConsole();
						List<Review> lista = user.getListaReviews();
						List<Review> del = new ArrayList<Review>();
						for (Review r : lista) {
							System.out.println(r);
							System.out.println("1. Eliminar reseña");
							System.out.println("2. Eliminar texto de la reseña");
							System.out.println("3. Ver siguiente");
							System.out.println("4. para volver");

							ans = sc.nextLine();
							if (ans.equals("1")) {
								clearConsole();
								del.add(r);
								System.out.println("Reseña eliminada");
								limpiarConDelay();
								user.serializeToJson();
							} else if (ans.equals("2")) {
								clearConsole();
								r.setDescripcion("");
								System.out.println("Texto eliminado");
								limpiarConDelay();
								user.serializeToJson();
							} else if (ans.equals("3")) {
								clearConsole();
							} else {
								clearConsole();
								user.eliminarMultiplesReviews(del);
								user.serializeToJson();
								menu_Moderador();
								break;
							}
						}
						user.eliminarMultiplesReviews(del);
						user.serializeToJson();
						menu_Moderador();
						break;
					case 4:
						menu_Moderador();
						break;
					default:
						System.out.println("Opción no válida");
						menu_Moderador();
						break;
				}
			} else {
				System.out.println("El usuario no puede ser moderado");
				menu_Moderador();
			}
		}
	}

	/**
	 * Menu admin mod.
	 */
	public void menu_AdminMod() {
		clearConsole();
		Admin admin = (Admin) personaActual;
		// mostrar las opciones que tiene el moderador
		System.out.println("ingrese el nombre del usuario que desea moderar o q para cambiar de usuario");
		String nombreUsuario = sc.nextLine();
		if (nombreUsuario.equals("q")) {
			clearConsole();
			try {
				menu_principal();
			} catch (Exception e) {
				System.out.println("Error");
			}
		}
		Persona victim = DB.buscarUser(nombreUsuario);

		if (victim == null) {
			System.out.println("El usuario no existe");
			menu_AdminMod();
		} else {
			boolean canBeBanned = (victim.getAuthLevel().equals("user") || (victim.getAuthLevel().equals("moderador")));
			if (canBeBanned) {
				Usuario user = (Usuario) victim;
				boolean valid=false;
				int opcion = 0;
				while (!valid) {
					clearConsole();
					System.out.println("usuario: " + victim.getFirst_name() + " " + victim.getLast_name());
					System.out.println(user.getDaysUntilUnban() > 0 ? "1. Quitar suspensión de usuario" : "1. suspender usuario");
					System.out.println(user.isBanned ? "2. Eliminar veto" : "2. vetar usuario");
					System.out.println("3. Ver reseñas");
					System.out.println("4. Cambiar usuario a moderar");
					try {
						opcion = sc.nextInt();
						valid = opcion>=1 && opcion<=4;
					}catch (InputMismatchException e){
						System.out.println("Ingrese una opción válida");
						sc.nextLine();
					}
				}
				switch (opcion) {
					case 1:
						if(user.getDaysUntilUnban()>0){
						user.setDaysUntilUnban(0);
						user.setIsBanned(false);
						user.serializeToJson();
						clearConsole();
						System.out.println("Usuario reincorporado");
						menu_AdminMod();
						break;
					}
						clearConsole();
						System.out.println("Ingrese la duración del veto: (cantidad + (días|semanas|meses|años))");
						String ans = sc.nextLine();
						int duration = parseTime(ans);
						if(duration==0){
							System.out.println("Duración no válida");
							menu_AdminMod();
							break;
						}
						admin.addBanDuration(user, duration);
						System.out.println("Usuario vetado");
						user.serializeToJson();
						limpiarConDelay();
						menu_AdminMod();
						break;
					case 2:
						if (user.isBanned) {
							clearConsole();
							admin.unbanUser(user);
							System.out.println("Veto eliminado");
							user.serializeToJson();
							limpiarConDelay();
							menu_AdminMod();
							break;
						} else {
							admin.banUser(user);
							clearConsole();
							System.out.println("Usuario vetado.");
							user.serializeToJson();
							limpiarConDelay();
							menu_AdminMod();
						}
						break;
					case 3:
						clearConsole();
						if (victim.getAuthLevel().equalsIgnoreCase("moderador")){
							System.out.println("Un moderador no puede hacer reseñas");
							limpiarConDelay();
						}else {
							List<Review> lista = user.getListaReviews();
							List<Review> del = new ArrayList<Review>();
							for (Review r : lista) {
								System.out.println(r);
								System.out.println("1. Eliminar reseña");
								System.out.println("2. Eliminar texto de la reseña");
								System.out.println("3. Ver siguiente");
								System.out.println("4. para volver");

								ans = sc.nextLine();
								if (ans.equals("1")) {
									clearConsole();
									del.add(r);
									System.out.println("Reseña eliminada");
									limpiarConDelay();
									user.serializeToJson();
									menu_AdminMod();
								} else if (ans.equals("2")) {
									clearConsole();
									r.setDescripcion("");
									System.out.println("Texto eliminado");
									limpiarConDelay();
									user.serializeToJson();
									menu_AdminMod();
								} else if (ans.equals("3")) {
									clearConsole();
								} else {
									clearConsole();
									user.getListaReviews().removeAll(del);
									user.serializeToJson();
									menu_AdminMod();
									break;
								}
							}
							user.getListaReviews().removeAll(del);
							user.serializeToJson();
						}
						break;
					case 4:
						sc.nextLine();
						clearConsole();
						menu_AdminMod();
						break;
					default:
						System.out.println("Opción no válida");
						menu_AdminMod();
						break;
				}
				menu_AdminMod();
			} else {
				System.out.println("El usuario no puede ser moderado");
				menu_AdminMod();
			}
		}
	}

	private int parseTime(String duration) {
		String[] parts = duration.split(" ");
		int amount;
		if (parts.length == 1) {
			parts = new String[] { parts[0], "dias" };
		}
		if(parts.length>1){
			amount=0;
		}
		// obtener la cantidad de tiempo
		try{
			amount=Integer.parseInt(parts[0]);
		}
		catch(Exception e){
			amount=0;
		}

		// pasar a minuscula y eliminar acentos
		String unit = parts[1].toLowerCase();
		unit = Normalizer.normalize(unit, Normalizer.Form.NFD);
		unit = unit.replaceAll("[^\\p{ASCII}]", "");
		String[] seccion = unit.split("");

		// eliminar el plural
		if (seccion[seccion.length - 1].equals("s")) {
			unit = unit.substring(0, unit.length() - 1);
		}
		switch (unit) {
			case "dia":
				break;
			case "semana":
				amount *= 7;
				break;
			case "mes":
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

	/**
	 * Limpiar con delay.
	 */
	public void limpiarConDelay() {
		// espera 2 segundos y limpia la consola
		try {
			Thread.sleep(timeout);
			clearConsole();
		} catch (InterruptedException e) {
			System.out.println("ocurrió un error inesperado");
		}
	}

	/**
	 * Clear console.
	 */
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

}
