/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prac0ia.clases;

import prac0ia.utils.ConsoleUtils;

/**
 *
 * @author fran
 */
public class Alumno {
    
    private String nombre;
    private String DNI;
    private String eMail;

    public Alumno() {
    }

    public Alumno(String nombre, String DNI, String eMail) {
        this.nombre = nombre;
        this.DNI = DNI;
        this.eMail = eMail;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    
    public static Alumno crearAlumno() {
        Alumno res = new Alumno();
        
        res.setNombre(ConsoleUtils.getStringField("Introduzca el nombre del alumno"));
        res.setDNI(ConsoleUtils.getStringField("Introduzca el DNI"));
        res.seteMail(ConsoleUtils.getStringField("Introduzca el email del alumno"));
        
        return res;
    }
    
    public static void mostrarAlumno(Alumno alumno) {
        ConsoleUtils.println("El nombre del alumno es " + alumno.getNombre());
        ConsoleUtils.println("El DNI del alumno es " + alumno.getDNI());
        ConsoleUtils.println("El e-Mail del alumno es " + alumno.geteMail());
    }
}
