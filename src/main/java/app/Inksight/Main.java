package app.Inksight;
import com.google.gson.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.Vector;
import java.util.*;


/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of ap plication.
     *
     * @param args the input arguments
     * @throws IOException              the io exception
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        
        Interfaz i = new Interfaz();
        i.menu_principal();

    }
}