package app.Inksight;

import java.text.Normalizer;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Interfaz {
	static Scanner sc = new Scanner(System.in);
	String titulo;
	String nombreLista;
	String nombreListaAntiguo;
	int opcion;
	GestionColecciones listas = new GestionColecciones();
	Stats stats = new Stats();
	int timeout = 2000;

	// Libro libro = new Libro(0,"","",0,"","");
	// Libro libro = new Libro(libro);
	Persona personaActual = new Usuario("", "", "");
	Usuario usuarioActual;
	DB db = new DB();
	String permiso;

	public void menu_principal() {
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
		switch (opcion) {
			case 1:
				System.out.print("Ingrese un nombre de usuario: ");
				nombreUser = sc.next();
				System.out.print("Ingrese un correo electronico: ");
				correo = sc.next();

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
				if (contraseña.matches(regex)) {
					validPassword = true;
					System.out.println("Confirmar contraseña: ");
					contraseña = sc.next();
				} else {
					System.out.println("Contraseña no válida");

				}
				if (validPassword) {
					System.out.println("Contraseña válida");
				}
				// si la contraseña es válida, se crea el usuario

				break;
			case 2:
				boolean valid = false;
				while (!valid) {
					System.out.println("Ingresa nombre o correo:");
					nombre = sc.next();
					validarNombre(nombre);
					System.out.println("Ingresa contraseña:");
					contraseña = sc.next();
					// asegurarse de que la contraseña pertenece al usuario

					if (valid) {
						System.out.println("No válido");
						permiso = ((personaActual instanceof Admin) ? "admin"
								: (personaActual instanceof Moderador ? "Moderador" : "usuario"));
						if (permiso.equals("usuario")) {
							usuarioActual = (Usuario) personaActual;
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
							}
						}

					} else {
						System.out.println("Válido");
					}
					// validar que el usuario existe
					// validar que la contraseña pertenece al usuario

					// Si se mete X usuario y contraseña, se abre el menu de el administrador
					// Si se mete Y usuario y contraseña, se abre el menu del modificador
					// Si se mete Z usuario y contraseña, se abre el menu del usuario

				} // while
		}

	}// menu

	private static void validarNombre(String nombre) {
		// comprobar si el String nombre existe en el archivo de texto
		// boolean valid = usuarios.validarNombre(nombre);
		// if(nombre.equals(usuarioActual.first_name)){}

	}

	public void menu_PerfilUsuario(String nombre) {
		System.out.println(nombre);
		System.out.println();
		System.out.println("1. Estadisticas");
		System.out.println("2. Retos");
		System.out.println("3. Amigos");
		System.out.println("4. Hacer reseña");
		System.out.println("5. Libros");
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
				System.out.println("LIBROS");
				menu_Libro();
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
		menu_principal();
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

		switch (opcion) {
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
				if (usuarioActual instanceof Usuario) {
					usuarioActual.getDesafios();
				}
				break;
			case 4:
				System.out.println("Escriba el nombre del reto que desea eliminar: ");
				reto = sc.next();
				// coleccion.eliminar(reto);
				break;
			case 5:
				System.out.println("Saliendo...");
				// llamar al menu de perfil de usuario
				break;

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
				// usuarioActual.buscarAmigo(amigo);
				// comprobar si el nombre del amigo existe
				boolean existe = false;
				if (existe) {
					System.out.println("Nombre no encontrado");
				}
				if (!existe) {
					System.out.println("¿Desea agregarlo?");
					System.out.println("1. Si");
					System.out.println("2. No");
					int numero = sc.nextInt();
					if (numero == 1) {
						// usuarioActual.anyadirAmigo(sc, usuarioActual);
						System.out.println("Amigo agregado");

					} else if (numero == 2) {
						System.out.println("Amigo no agregado");
						menu_Amigo();
					} else {
						System.out.println("No has elegido una opción correcta");
					}
				}
				break;
			case 2:
				System.out.println("Ingresa el nombre del amigo que quieras borrar: ");
				amigo = sc.next();

				// si el nombre ingresado no existe salta un aviso y deja volver a escribir el
				// nombre
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

	public void menuReseñas() {
		System.out.println("RESEÑAS");
		System.out.println("1. Seleccionar el libro del que desee hacer la reseña");
		System.out.println("2. Salir");
		System.out.println();
		System.out.println("¿Qué deseas hacer? Inserta la opcion deseada");
		opcion = sc.nextInt();

		switch (opcion) {
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
				listas.consultarListaColecciones();
				// mostrar todas las listas que tiene el usuario
				break;
			case 5:
				try {

					System.out.println("Escriba el nombre del libro que deseas añadir: ");
					titulo = sc.next();
					System.out.println("Escriba el nombre de la lista a la que desea añadirlo: ");
					nombreLista = sc.next();
					listas.agregarlibro(nombreLista, titulo);
					if (listas.agregarlibro(nombreLista, titulo) == GestionColecciones.COLECCION_NO_EXISTE) {
						System.out.println("La lista no existe");
					}
				} catch (Exception e) {
					System.out.println("El libro no existe");
				}
				break;
			case 6:
				try {

					System.out.println("Seleccione la lista de la que desea mover el libro: ");
					nombreListaAntiguo = sc.next();
					System.out.println("Seleccione la lista a la que desea moverlo o cree una nueva: ");
					nombreLista = sc.next();
					System.out.println("Seleccione el libro que desea mover: ");
					titulo = sc.next();
					listas.moverLibroDeColeccion(nombreListaAntiguo, nombreLista, titulo);
				} catch (Exception e) {
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
	}

	public void checkTimeWhenLogin() {
		Date lastChallenge = usuarioActual.getLastChallenge();
		Date now = new Date(System.currentTimeMillis());
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
			switch (tipo) {
				// permite agregar todas las opciones de desafios que quieras, simplemente
				// agrega un nuevo case(permite ajustar pesos repitiendo numeros)
				case 0:
					usuarioActual.makeChallenge("Leer 5 libros", "Leer 5 libros", 5, "libros", 100);
					break;
				case 1:
					usuarioActual.makeChallenge("Leer 100 páginas", "Leer 100 páginas en una semana", 100, "paginas",
							100);
					break;
				case 2:
					usuarioActual.makeChallenge("Leer 10 libros", "Leer 10 libros en una semana", 10, "libros", 200);
					break;
				case 3:
				case 4:
					usuarioActual.makeChallenge("Aceptar 1 recomendacion",
							"lee un libro que un amigo te haya recomendado", 1, "rec", 40);
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
				
				//todo crear el Moderador y guardarlo en la BDD
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
		int amount = Integer.parseInt(parts[0]);
		String unit = parts[1].toLowerCase(null);
		unit = Normalizer.normalize(unit, Normalizer.Form.NFD);
		unit = unit.replaceAll("[^\\p{ASCII}]", "");
		switch (unit) {
			case "días":
				break;
			case "semanas":
				amount *= 7;
				break;
			case "meses":
				amount *= 30;
				break;
			case "años":
				amount *= 365;
				break;
			default:
				System.out.println("Unidad de tiempo no válida");
				break;
		}
		return amount;
	}

	public void limpiarConDelay() {
		try {
			Thread.sleep(timeout);
			clearConsole();
		} catch (InterruptedException e) {
			System.out.println("ocurrió un error inesperado");
		}
	}

	public static void clearConsole() {
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

	public static void main(String[] args) {
		Interfaz interfaz = new Interfaz();
		interfaz.menu_principal();

	}

}
