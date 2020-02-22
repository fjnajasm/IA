/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mouserun.mouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import javafx.util.Pair;
import mouserun.game.Mouse;
import mouserun.game.Grid;
import mouserun.game.Cheese;

/**
 * Clase que contiene el esqueleto del raton base para las prácticas de
 * Inteligencia Artificial del curso 2019-20.
 *
 * @author Cristóbal José Carmona (ccarmona@ujaen.es) y Ángel Miguel García Vico
 * (agvico@ujaen.es)
 */
public class M20C01a extends Mouse {

    /**
     * Variable para almacenar la ultima celda visitada
     */
    private Grid lastGrid;

    /**
     * Tabla hash para almacenar las celdas visitadas por el raton: Clave:
     * Coordenadas Valor: La celda
     */
    private HashMap<Pair<Integer, Integer>, Grid> celdasVisitadas;

    /**
     * Pila para almacenar el camino recorrido.
     */
    private Stack<Grid> pilaMovimientos;

    private Integer ultimoMovimiento;
    
    private Stack<Integer> pilaMovs;

    /**
     * Constructor (Puedes modificar el nombre a tu gusto).
     */
    public M20C01a() {
        super("PutisimoAmo");
        celdasVisitadas = new HashMap<>();
        pilaMovimientos = new Stack<>();
        ultimoMovimiento = 5;
        pilaMovs = new Stack<>();
    }

    /**
     * @brief Método principal para el movimiento del raton. Incluye la gestión
     * de cuando un queso aparece o no.
     * @param currentGrid Celda actual
     * @param cheese Queso
     */
    public Integer propio(Grid currentGrid) {
        if (!celdasVisitadas.containsKey(new Pair<>(currentGrid.getX(), currentGrid.getY()))) {
            celdasVisitadas.put(new Pair(currentGrid.getX(), currentGrid.getY()), currentGrid);
        }

        pilaMovimientos.add(lastGrid);

        int movimientoFinal = Mouse.BOMB;
        List<Integer> celdasPosibles = new ArrayList<>();

        if (currentGrid.canGoDown() && !celdasVisitadas.containsKey(new Pair<>(currentGrid.getX(), currentGrid.getY() - 1))) {
            celdasPosibles.add(Mouse.DOWN);
        }
        if (currentGrid.canGoRight() && !celdasVisitadas.containsKey(new Pair<>(currentGrid.getX() + 1, currentGrid.getY()))) {
            celdasPosibles.add(Mouse.RIGHT);
        }
        if (currentGrid.canGoUp() && !celdasVisitadas.containsKey(new Pair<>(currentGrid.getX(), currentGrid.getY() + 1))) {
            celdasPosibles.add(Mouse.UP);
        }
        if (currentGrid.canGoLeft() && !celdasVisitadas.containsKey(new Pair<>(currentGrid.getX() - 1, currentGrid.getY()))) {
            celdasPosibles.add(Mouse.LEFT);
        }

        lastGrid = currentGrid;

        if (celdasPosibles.size() == 0) {
            if (ultimoMovimiento == Mouse.UP) {
                movimientoFinal = Mouse.DOWN;
            }
            if (ultimoMovimiento == Mouse.LEFT) {
                movimientoFinal = Mouse.RIGHT;
            }
            if (ultimoMovimiento == Mouse.RIGHT) {
                movimientoFinal = Mouse.LEFT;
            }
            if (ultimoMovimiento == Mouse.DOWN) {
                movimientoFinal = Mouse.UP;
            }
        } else {
            movimientoFinal = celdasPosibles.get(new Random().nextInt(celdasPosibles.size()));
        }

        ultimoMovimiento = movimientoFinal;

        return movimientoFinal;
    }

    @Override
    public int move(Grid currentGrid, Cheese cheese) {

        return DFS(currentGrid);
    }

    
    public int DFS(Grid currentGrid) {
        if (!celdasVisitadas.containsKey(new Pair<>(currentGrid.getX(), currentGrid.getY()))) {
            celdasVisitadas.put(new Pair(currentGrid.getX(), currentGrid.getY()), currentGrid);
            incExploredGrids();
        }
        
        pilaMovimientos.add(lastGrid);

        int movimientoFinal = Mouse.BOMB;
        List<Integer> celdasPosibles = new ArrayList<>();

        if (currentGrid.canGoDown() && !celdasVisitadas.containsKey(new Pair<>(currentGrid.getX(), currentGrid.getY() - 1)) && movimientoFinal == Mouse.BOMB) {
            movimientoFinal = Mouse.DOWN;
        }
        if (currentGrid.canGoRight() && !celdasVisitadas.containsKey(new Pair<>(currentGrid.getX() + 1, currentGrid.getY())) && movimientoFinal == Mouse.BOMB) {
            movimientoFinal = Mouse.RIGHT;
        }
        if (currentGrid.canGoUp() && !celdasVisitadas.containsKey(new Pair<>(currentGrid.getX(), currentGrid.getY() + 1)) && movimientoFinal == Mouse.BOMB) {
            movimientoFinal = Mouse.UP;
        }
        if (currentGrid.canGoLeft() && !celdasVisitadas.containsKey(new Pair<>(currentGrid.getX() - 1, currentGrid.getY())) && movimientoFinal == Mouse.BOMB) {
            movimientoFinal = Mouse.LEFT;
        }

        if(movimientoFinal != Mouse.BOMB) {
            pilaMovs.add(movimientoFinal);
            return movimientoFinal;
        }
                
        movimientoFinal = pilaMovs.pop();
        switch(movimientoFinal) {
            case Mouse.LEFT:
                return Mouse.RIGHT;
            case Mouse.RIGHT:
                return Mouse.LEFT;
            case Mouse.UP:
                return Mouse.DOWN;
            case Mouse.DOWN:
                return Mouse.UP;                
        }
        
        return Mouse.BOMB;
        
    }
    /**
     * @brief Método que se llama cuando aparece un nuevo queso
     */
    @Override
    public void newCheese() {

    }

    /**
     * @brief Método que se llama cuando el raton pisa una bomba
     */
    @Override
    public void respawned() {

    }

    /**
     * @brief Método para evaluar que no nos movamos a la misma celda anterior
     * @param direction Direccion del raton
     * @param currentGrid Celda actual
     * @return True Si las casillas X e Y anterior son distintas a las actuales
     */
    public boolean testGrid(int direction, Grid currentGrid) {
        if (lastGrid == null) {
            return true;
        }

        int x = currentGrid.getX();
        int y = currentGrid.getY();

        switch (direction) {
            case Mouse.UP:
                y += 1;
                break;

            case Mouse.DOWN:
                y -= 1;
                break;

            case Mouse.LEFT:
                x -= 1;
                break;

            case Mouse.RIGHT:
                x += 1;
                break;
        }

        return !(lastGrid.getX() == x && lastGrid.getY() == y);

    }

    /**
     * @brief Método que devuelve si de una casilla dada, está contenida en el
     * mapa de celdasVisitadas
     * @param casilla Casilla que se pasa para saber si ha sido visitada
     * @return True Si esa casilla ya la había visitado
     */
    public boolean visitada(Grid casilla) {
        Pair par = new Pair(casilla.getX(), casilla.getY());
        return celdasVisitadas.containsKey(par);
    }

    /**
     * @brief Método para calcular si una casilla está en una posición relativa
     * respecto a otra
     * @param actual Celda actual
     * @param anterior Celda anterior
     * @return True Si la posición Y de la actual es mayor que la de la anterior
     */
    public boolean actualArriba(Grid actual, Grid anterior) {
        return actual.getY() > anterior.getY();
    }

    /**
     * @brief Método para calcular si una casilla está en una posición relativa
     * respecto a otra
     * @param actual Celda actual
     * @param anterior Celda anterior
     * @return True Si la posición Y de la actual es menor que la de la anterior
     */
    public boolean actualAbajo(Grid actual, Grid anterior) {
        return actual.getY() < anterior.getY();
    }

    /**
     * @brief Método para calcular si una casilla está en una posición relativa
     * respecto a otra
     * @param actual Celda actual
     * @param anterior Celda anterior
     * @return True Si la posición X de la actual es mayor que la de la anterior
     */
    public boolean actualDerecha(Grid actual, Grid anterior) {
        return actual.getX() > anterior.getX();
    }

    /**
     * @brief Método para calcular si una casilla está en una posición relativa
     * respecto a otra
     * @param actual Celda actual
     * @param anterior Celda anterior
     * @return True Si la posición X de la actual es menor que la de la anterior
     */
    public boolean actualIzquierda(Grid actual, Grid anterior) {
        return actual.getX() < anterior.getX();
    }

}
