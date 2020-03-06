/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mouserun.mouse;

import java.util.HashMap;
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
    private int ultimo = LEFT;
    /**
     * Tabla hash para almacenar las celdas visitadas por el raton: Clave:
     * Coordenadas Valor: La celda
     */
    private final HashMap<Pair<Integer, Integer>, Grid> celdasVisitadas;
    private final HashMap< Pair<Integer, Integer> , Integer > contadorCeldas;

    /**
     * Pila para almacenar el camino recorrido.
     */
    private final Stack<Grid> pilaMovimientos;
    private final Stack<Integer> pilaMovs;

    /**
     * Constructor (Puedes modificar el nombre a tu gusto).
     */
    public M20C01a() {
        super("M20C01a");
        celdasVisitadas = new HashMap<>();
        pilaMovimientos = new Stack<>();
        pilaMovs = new Stack<>();
        contadorCeldas = new HashMap<>();
        
    }


    @Override
    public int move(Grid currentGrid, Cheese cheese) {
        return DFS(currentGrid);
    }

    public int DFS(Grid currentGrid) {
        Pair pair = new Pair(currentGrid.getX(), currentGrid.getY());
        if (!celdasVisitadas.containsKey(pair)) {
            celdasVisitadas.put(pair, currentGrid);
            incExploredGrids();
            contadorCeldas.put(pair, 0);
        }
        
        contadorCeldas.replace(pair, contadorCeldas.get(pair), contadorCeldas.get(pair) + 1);

        System.out.println("Casilla: X - " + currentGrid.getX() + " / Y - " + currentGrid.getY() + " --> " + contadorCeldas.get(pair));
        //pilaMovimientos.add(lastGrid);

        int movimientoFinal = Mouse.BOMB;

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

        if (movimientoFinal != Mouse.BOMB) {
            pilaMovs.add(movimientoFinal);
            return movimientoFinal;
        }
        if (pilaMovs.empty()) {
            int minimoVeces = Integer.MAX_VALUE;
            int nVecesArriba = Integer.MAX_VALUE, nVecesAbajo = Integer.MAX_VALUE, nVecesIzquierda = Integer.MAX_VALUE, nVecesDerecha = Integer.MAX_VALUE;
            if (currentGrid.canGoUp()) {
                nVecesArriba = contadorCeldas.get(new Pair<>(currentGrid.getX(), currentGrid.getY() + 1));
                if (nVecesArriba < minimoVeces) {
                    minimoVeces = nVecesArriba;
                }
            }
            if (currentGrid.canGoDown()) {
                nVecesAbajo = contadorCeldas.get(new Pair(currentGrid.getX(), currentGrid.getY() - 1));
                if (nVecesAbajo < minimoVeces) {
                    minimoVeces = nVecesAbajo;
                }
            }
            if (currentGrid.canGoLeft()) {
                nVecesIzquierda = contadorCeldas.get(new Pair(currentGrid.getX() - 1, currentGrid.getY()));
                if (nVecesIzquierda < minimoVeces) {
                    minimoVeces = nVecesIzquierda;
                }
            }
            if (currentGrid.canGoRight()) {
                nVecesDerecha = contadorCeldas.get(new Pair(currentGrid.getX() + 1, currentGrid.getY()));
                if (nVecesDerecha < minimoVeces) {
                    minimoVeces = nVecesDerecha;
                }
            }

            if (minimoVeces == nVecesArriba) {
                ultimo = DOWN;
                //pilaMovs.add(ultimo);
                return UP;
            } else if (minimoVeces == nVecesAbajo) {
                ultimo = UP;
                //pilaMovs.add(ultimo);
                return DOWN;
            } else if (minimoVeces == nVecesIzquierda) {
                ultimo = RIGHT;
                //pilaMovs.add(ultimo);
                return LEFT;
            } else {
                ultimo = LEFT;
                //pilaMovs.add(ultimo);
                return RIGHT;
            }
        }

        movimientoFinal = pilaMovs.pop();
        switch (movimientoFinal) {
            case Mouse.LEFT:
                ultimo = Mouse.RIGHT;
                break;
            case Mouse.RIGHT:
                ultimo = Mouse.LEFT;
                break;
            case Mouse.UP:
                ultimo = Mouse.DOWN;
                break;
            case Mouse.DOWN:
                ultimo = Mouse.UP;
                break;
            default:
                ultimo = Mouse.BOMB;
                break;
        }
        
        
        
        return ultimo;
    }

    
    public Stack<Integer> busqueda(Grid currentGrid, Grid objetivo) {
        
        return null;
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
