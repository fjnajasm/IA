/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prac0ia.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import prac0ia.clases.Alumno;
import prac0ia.clases.Alumno_IA;

/**
 *
 * @author fran
 */
public class PRAC0IA {

    public static void alumnosPares() throws FileNotFoundException, IOException {
        String cadena;
        FileReader f = new FileReader("datos.txt");
        BufferedReader bf = new BufferedReader(f);
        
        String nombreFicheroNuevo = "pares.txt";
        File pares = new File(nombreFicheroNuevo);
        BufferedWriter bw = new BufferedWriter(new FileWriter(pares));
        
        while((cadena = bf.readLine()) != null) {
            String cadenafichero = cadena;
            int indice = cadena.indexOf(",");
            cadena = cadena.substring(indice+2, cadena.length());
            indice = cadena.indexOf(",");
            cadena = cadena.substring(0,indice);
            Integer dni = Integer.parseInt(cadena);
            if(dni % 2 == 0 && pares.exists()) {
                System.out.println(cadenafichero);
                bw.write(cadenafichero + "\n");
            }
        }
        
        bw.close();
        bf.close();
        
    }
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Alumno al = Alumno.crearAlumno();
        Alumno.mostrarAlumno(al);

        Alumno_IA al1 = new Alumno_IA("3", 0, al.getNombre(), al.getDNI(), al.geteMail());

        al1.calcularNotaPracticas();

        Alumno.mostrarAlumno(al1);
        System.out.println("La nota de practicas es: " + al1.getNotaPrac());
        
        alumnosPares();
        
    }
    
}
