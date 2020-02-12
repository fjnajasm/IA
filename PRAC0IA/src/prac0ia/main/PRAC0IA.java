/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prac0ia.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import prac0ia.clases.Alumno;
import prac0ia.clases.Alumno_IA;
import prac0ia.utils.ConsoleUtils;

/**
 *
 * @author fran
 */
public class PRAC0IA {

    public static void alumnosPares() throws FileNotFoundException, IOException {
 
        try (BufferedReader in = new BufferedReader(new FileReader("datos.txt"))) {
            String lineaFichero;
            
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("pares.txt"))) {
                while((lineaFichero = in.readLine()) != null) {
                    String[] datosAlumno = lineaFichero.split(", ");
                    int dni = Integer.parseInt(datosAlumno[1]);
                    if(dni % 2 == 0) {
                        bw.write(lineaFichero + "\n");
                    }
                }
                
                in.close();
                bw.close();
            }
        } catch (IOException e) {
            System.out.println("Error al leer de fichero");
        }
    }
    
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        while(true) {
            int i = 0;
            ConsoleUtils.println("1. Crear alumno:");
            ConsoleUtils.println("2. Poner notas alumno:");
            ConsoleUtils.println("3. Datos a pares:");
            i = ConsoleUtils.getIntField("Introduzca una opción") - 1;
            
            
            switch(i) {
                case 0:
                    Alumno al = Alumno.crearAlumno();
                    Alumno.mostrarAlumno(al);
                    break;
                case 1:
                    Alumno_IA al1 = new Alumno_IA("3", 0, "Fran", "21025923J", "fjnajasm@gmail.com");
                    al1.calcularNotaPracticas();
                    System.out.println("La nota de practicas es: " + al1.getNotaPrac());
                    break;
                case 2:
                    alumnosPares();
                    break;
            }
        }
        
    }
    
}
