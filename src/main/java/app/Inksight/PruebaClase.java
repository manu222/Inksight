package app.Inksight;

import java.util.*;

public class PruebaClase {//usuario con limite de 100 amigos
	
	static Scanner teclado = new Scanner(System.in);
	String nombre;
	Vector<PruebaClase> listaAmigos;
	String[] libroLeer;
	String[] libroLeido;
	int permiso;
	int nAmigos;
	int puntos;
	
	public void anyadirAmigo(Scanner teclado, PruebaClase usuario){
		listaAmigos.addElement(usuario);//hacer esta wea un hashset
		System.out.println(listaAmigos.capacity());
		
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PruebaClase Fea = new PruebaClase();
		PruebaClase Jose = new PruebaClase();
		Fea.nombre = "Paula";
		Jose.nombre = "Jose";
		Fea.listaAmigos = new Vector<PruebaClase>(1,1);
		Fea.anyadirAmigo(teclado, Jose);
		Fea.anyadirAmigo(teclado, Jose);
		
		
	}
	
	

}
