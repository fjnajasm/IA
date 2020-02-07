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
public class Alumno_IA extends Alumno {
    
    private String grupoPrac;
    private float notaPrac;

    public Alumno_IA() {
        super();
    }

    public Alumno_IA(String grupoPrac, float notaPrac) {
        super();
        this.grupoPrac = grupoPrac;
        this.notaPrac = notaPrac;
    }

    public Alumno_IA(String grupoPrac, float notaPrac, String nombre, String DNI, String eMail) {
        super(nombre, DNI, eMail);
        this.grupoPrac = grupoPrac;
        this.notaPrac = notaPrac;
    }

    public String getGrupoPrac() {
        return grupoPrac;
    }

    public void setGrupoPrac(String grupoPrac) {
        this.grupoPrac = grupoPrac;
    }

    public float getNotaPrac() {
        return notaPrac;
    }

    public void setNotaPrac(float notaPrac) {
        this.notaPrac = notaPrac;
    }
    
    public void calcularNotaPracticas() {
        notaPrac = 0;
        float nota = 0;
        for(int i = 0; i < 4; i++) {
            nota = ConsoleUtils.getFloatField("Introduzca la nota " + i);
            if(nota < 0 || nota > 10) {
                ConsoleUtils.println("Nota no valida");
                i--;
                nota = 0;
            }
            notaPrac += nota;
            
        } 
        
        notaPrac = notaPrac/4;
    }
}
