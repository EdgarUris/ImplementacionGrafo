/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.grafo_261147;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

/**
 * Grafo genérico implementado con una matriz de adyacencia.
 *
 * <p>Puede ser dirigido o no dirigido, y ponderado o no ponderado. Los
 * vértices se guardan en orden de inserción y ese mismo orden se utiliza en
 * las listas que devuelven los algoritmos de caminos más cortos.</p>
 *
 * @param <T> tipo de dato almacenado en los vértices
 * @author EdgarUrias
 */
public class Grafo<T> {

    private Double[][] matriz;
    private final List<T> vertices; // vértices del grafo en orden de inserción
    private final Map<T, Integer> posiciones; // guarda cada vértice con su posición
    private TipoGrafo tipo;

    /**
     * Crea un grafo vacío, no dirigido y no ponderado.
     */
    public Grafo() {
        this(TipoGrafo.NO_DIRIGIDO_NO_PONDERADO);
    }

    /**
     * Crea un grafo vacío del tipo indicado.
     *
     * @param tipo tipo de grafo que se desea crear
     * @throws NullPointerException si {@code tipo} es {@code null}
     */
    public Grafo(TipoGrafo tipo) {
        this.tipo = Objects.requireNonNull(tipo, "El tipo de grafo no puede ser null");
        this.vertices = new ArrayList<>();
        this.posiciones = new HashMap<>();
        this.matriz = new Double[0][0];
    }

    /**
     * Crea un grafo vacío indicando si es dirigido y si es ponderado.
     *
     * @param dirigido  {@code true} si las aristas tienen dirección
     * @param ponderado {@code true} si las aristas tienen peso
     */
    public Grafo(boolean dirigido, boolean ponderado) {
        this(TipoGrafo.determinarTipo(dirigido, ponderado));
    }

    /**
     * Devuelve el tipo de este grafo.
     *
     * @return el tipo de grafo
     */
    public TipoGrafo getTipo() {
        return tipo;
    }

    /**
     * Indica si el grafo es dirigido.
     *
     * @return {@code true} si el grafo es dirigido
     */
    public boolean esDirigido() {
        return tipo.esDirigido();
    }

    /**
     * Indica si el grafo es ponderado.
     *
     * @return {@code true} si el grafo es ponderado
     */
    public boolean esPonderado() {
        return tipo.esPonderado();
    }

    /**
     * Indica si el grafo no tiene vértices.
     *
     * @return {@code true} si el grafo está vacío
     */
    public boolean estaVacio() {
        return vertices.isEmpty();
    }

    /**
     * Elimina todos los vértices y todas las aristas del grafo.
     */
    public void limpiar() {
        vertices.clear();
        posiciones.clear();
        matriz = new Double[0][0];
    }

    // métodos para los vértices

    /**
     * Inserta un vértice en el grafo. Si el vértice ya existe, no se modifica
     * el grafo.
     *
     * @param vertice vértice que se desea insertar
     * @return {@code true} si el vértice se insertó, {@code false} si ya existía
     * @throws NullPointerException si {@code vertice} es {@code null}
     */
    public boolean insertarVertice(T vertice) {
        Objects.requireNonNull(vertice, "El vértice no puede ser null");
        if (posiciones.containsKey(vertice)) {
            return false;
        }

        int n = vertices.size();
        vertices.add(vertice);
        posiciones.put(vertice, n);

        // la matriz aumenta 1 fila y 1 columna
        Double[][] nueva = new Double[n + 1][n + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matriz[i], 0, nueva[i], 0, n);
        }
        matriz = nueva;
        return true;
    }

    /**
     * Elimina un vértice y todas las aristas relacionadas con él.
     *
     * @param vertice vértice que se desea eliminar
     * @return {@code true} si el vértice existía y fue eliminado,
     *         {@code false} si es {@code null} o no pertenece al grafo
     */
    public boolean eliminarVertice(T vertice) {
        if (vertice == null) {
            return false;
        }
        Integer indiceEncontrado = posiciones.get(vertice);
        if (indiceEncontrado == null) {
            return false;
        }
        int indice = indiceEncontrado;

        // reducir la matriz de adyacencia en 1 fila y 1 columna
        int n = vertices.size();
        Double[][] nueva = new Double[n - 1][n - 1];
        int filaNueva = 0;
        for (int i = 0; i < n; i++) {
            if (i != indice) {
                int columnaNueva = 0;
                for (int j = 0; j < n; j++) {
                    if (j != indice) {
                        nueva[filaNueva][columnaNueva] = matriz[i][j];
                        columnaNueva++;
                    }
                }
                filaNueva++;
            }
        }

        vertices.remove(indice);
        posiciones.remove(vertice);

        // se actualizan los índices de los vértices que quedaron después
        for (int k = indice; k < vertices.size(); k++) {
            posiciones.put(vertices.get(k), k);
        }

        matriz = nueva;
        return true;
    }

    /**
     * Indica si un vértice pertenece al grafo.
     *
     * @param vertice vértice que se desea comprobar
     * @return {@code true} si el vértice existe, {@code false} en caso contrario
     */
    public boolean existeVertice(T vertice) {
        return vertice != null && posiciones.containsKey(vertice);
    }

    /**
     * Devuelve los vértices del grafo en orden de inserción.
     *
     * @return lista con los vértices
     */
    public List<T> obtenerVertices() {
        return vertices;
    }

    /**
     * Devuelve el número de vértices del grafo.
     *
     * @return cantidad de vértices
     */
    public int getNumeroVertices() {
        return vertices.size();
    }

    // métodos para las aristas

    /**
     * Inserta una arista sin especificar peso; por defecto el peso es 1.
     *
     * @param origen  vértice de origen
     * @param destino vértice de destino
     * @return {@code true} si la arista no existía, {@code false} si ya existía
     *         y solo se actualizó su peso
     * @throws NullPointerException  si alguno de los vértices es {@code null}
     * @throws IllegalArgumentException si alguno de los vértices no existe en el grafo
     */
    public boolean insertarArista(T origen, T destino) {
        return insertarAristaInterna(origen, destino, 1.0);
    }

    /**
     * Inserta una arista con peso en un grafo ponderado.
     *
     * @param origen  vértice de origen
     * @param destino vértice de destino
     * @param peso    peso de la arista
     * @return {@code true} si la arista no existía, {@code false} si ya existía
     *         y solo se actualizó su peso
     * @throws UnsupportedOperationException si el grafo no es ponderado
     * @throws NullPointerException  si alguno de los vértices es {@code null}
     * @throws IllegalArgumentException si alguno de los vértices no existe o
     *         si {@code peso} no es un número
     */
    public boolean insertarArista(T origen, T destino, double peso) {
        if (!tipo.esPonderado()) {
            throw new UnsupportedOperationException(
                    "No se puede insertar una arista con peso en un grafo no ponderado");
        }
        return insertarAristaInterna(origen, destino, peso);
    }

    /**
     * Inserta una arista en la matriz de adyacencia.
     *
     * @param origen  vértice de origen
     * @param destino vértice de destino
     * @param peso    peso de la arista
     * @return {@code true} si la arista es nueva, {@code false} si ya existía
     * @throws NullPointerException  si alguno de los vértices es {@code null}
     * @throws IllegalArgumentException si alguno de los vértices no existe o
     *         si {@code peso} no es un número
     */
    private boolean insertarAristaInterna(T origen, T destino, double peso) {
        if (Double.isNaN(peso)) {
            throw new IllegalArgumentException("El peso tiene que ser un número");
        }

        int i = obtenerIndice(origen);
        int j = obtenerIndice(destino);
        if (i == j) {
            matriz[i][j] = peso;
            return true;
        }

        boolean nueva = matriz[i][j] == null
                && (tipo.esDirigido() || matriz[j][i] == null);

        matriz[i][j] = peso;
        if (!tipo.esDirigido()) {
            matriz[j][i] = peso;
        }
        return nueva;
    }

    /**
     * Elimina la arista indicada.
     *
     * <p>En un grafo dirigido se elimina únicamente la arista de origen a
     * destino; en uno no dirigido se eliminan ambos sentidos.</p>
     *
     * @param origen  vértice de origen
     * @param destino vértice de destino
     * @return {@code true} si la arista existía y fue eliminada,
     *         {@code false} en caso contrario
     */
    public boolean eliminarArista(T origen, T destino) {
        Integer i = posiciones.get(origen);
        Integer j = posiciones.get(destino);
        if (i == null || j == null) {
            return false;
        }

        boolean existia = matriz[i][j] != null;
        matriz[i][j] = null;
        if (!tipo.esDirigido()) {
            matriz[j][i] = null;
        }
        return existia;
    }

    /**
     * Indica si existe una arista entre dos vértices.
     *
     * @param origen  vértice de origen
     * @param destino vértice de destino
     * @return {@code true} si la arista existe
     */
    public boolean existeArista(T origen, T destino) {
        Integer i = posiciones.get(origen);
        Integer j = posiciones.get(destino);
        return i != null && j != null && matriz[i][j] != null;
    }

    /**
     * Actualiza el peso de una arista que ya existe. Solo está disponible en
     * grafos ponderados; en un grafo no dirigido se actualizan ambos sentidos.
     *
     * @param origen  vértice de origen
     * @param destino vértice de destino
     * @param peso    nuevo peso de la arista
     * @return {@code true} si la arista existía y se actualizó,
     *         {@code false} si la arista no existe
     * @throws UnsupportedOperationException si el grafo no es ponderado
     * @throws NullPointerException  si alguno de los vértices es {@code null}
     * @throws IllegalArgumentException si alguno de los vértices no existe,
     *         si son iguales o si {@code peso} no es un número
     */
    public boolean actualizarPeso(T origen, T destino, double peso) {
        if (!tipo.esPonderado()) {
            throw new UnsupportedOperationException(
                    "No se puede actualizar un peso: el grafo no es ponderado");
        }
        if (Double.isNaN(peso)) {
            throw new IllegalArgumentException("El peso tiene que ser un número");
        }

        int i = obtenerIndice(origen);
        int j = obtenerIndice(destino);
        if (i == j) {
            throw new IllegalArgumentException("Los vértices deben ser diferentes");
        }
        if (matriz[i][j] == null) {
            return false;
        }

        matriz[i][j] = peso;
        if (!tipo.esDirigido()) {
            matriz[j][i] = peso;
        }
        return true;
    }

    // recorridos BFS y DFS

    /**
     * Realiza una búsqueda en anchura (BFS) desde el vértice indicado e
     * imprime el orden de visita en la consola.
     *
     * @param verticeInicio vértice desde el que comienza la búsqueda
     * @throws NullPointerException  si {@code verticeInicio} es {@code null}
     * @throws IllegalArgumentException si el vértice no pertenece al grafo
     */
    public void BusquedaEnAnchura(T verticeInicio) {
        boolean[] visitado = new boolean[vertices.size()];
        Queue<T> cola = new LinkedList<>();

        int indiceInicio = obtenerIndice(verticeInicio);
        visitado[indiceInicio] = true;

        cola.add(verticeInicio);
        System.out.println("Busqueda en anchura empezando desde: " + verticeInicio);

        while (!cola.isEmpty()) {
            T actual = cola.poll();
            System.out.println(String.valueOf(actual));

            for (T vecino : getAdyacentesDe(actual)) {
                if (!visitado[posiciones.get(vecino)]) {
                    visitado[posiciones.get(vecino)] = true;
                    cola.add(vecino);
                }
            }
        }
    }

    /**
     * Realiza una búsqueda en profundidad (DFS) e imprime el orden de visita.
     *
     * <p>Comienza en el primer vértice insertado y, si quedan vértices sin
     * visitar (por ejemplo, componentes desconectadas), continúa desde ellos.</p>
     *
     * @throws IllegalStateException si el grafo no tiene vértices
     */
    public void BusquedaEnProfundidad() {
        if (vertices.isEmpty()) {
            throw new IllegalStateException("No se puede recorrer un grafo sin vértices");
        }

        boolean[] visitado = new boolean[vertices.size()];
        System.out.println("Busqueda en profundidad iniciando desde " + verticeString(vertices.getFirst()));
        BusquedaEnProfundidadRec(vertices.getFirst(), visitado);

        for (int i = 0; i < vertices.size(); i++) {
            if (!visitado[i]) {
                BusquedaEnProfundidadRec(vertices.get(i), visitado);
            }
        }
    }

    /**
     * Visita recursivamente un vértice y sus adyacentes no visitados.
     *
     * @param actual   vértice que se está visitando
     * @param visitado arreglo que marca los vértices ya visitados
     */
    private void BusquedaEnProfundidadRec(T actual, boolean[] visitado) {
        visitado[posiciones.get(actual)] = true;
        System.out.println(String.valueOf(actual));

        for (T vecino : getAdyacentesDe(actual)) {
            if (!visitado[posiciones.get(vecino)]) {
                BusquedaEnProfundidadRec(vecino, visitado);
            }
        }
    }

    /**
     * Calcula los caminos más cortos desde un vértice origen mediante el
     * algoritmo de Dijkstra. Solo se aplica a grafos dirigidos con pesos no
     * negativos.
     *
     * @param vOrigen vértice desde el que se calculan las distancias
     * @return resultado con dos listas en orden de inserción de los vértices:
     *         las distancias mínimas y los predecesores de cada camino
     * @throws UnsupportedOperationException si el grafo no es dirigido
     * @throws NullPointerException  si {@code vOrigen} es {@code null}
     * @throws IllegalArgumentException si el vértice origen no existe en el grafo
     */
    public ResultadoCaminos<T> dijkstra(T vOrigen) {
        if (!tipo.esDirigido()) {
            throw new UnsupportedOperationException(
                    "Dijkstra solo está disponible para grafos dirigidos");
        }
        int indiceOrigen = obtenerIndice(vOrigen);

        List<Double> shortest = new ArrayList<>(
                Collections.nCopies(vertices.size(), Double.MAX_VALUE));
        List<T> predecesor = new ArrayList<>(
                Collections.nCopies(vertices.size(), null));
        shortest.set(indiceOrigen, 0.0);

        // se va tomando el vértice no visitado con la distancia menor
        List<T> verticesDijkstra = new ArrayList<>(vertices);

        while (!verticesDijkstra.isEmpty()) {
            int indiceShortestMenor = 0;
            for (int i = 1; i < verticesDijkstra.size(); i++) {
                int indiceVertice = posiciones.get(verticesDijkstra.get(i));
                int indiceVerticeMenor = posiciones.get(verticesDijkstra.get(indiceShortestMenor));
                if (shortest.get(indiceVertice) < shortest.get(indiceVerticeMenor)) {
                    indiceShortestMenor = i;
                }
            }

            T actual = verticesDijkstra.remove(indiceShortestMenor);
            int x = posiciones.get(actual);

            // si el menor es inalcanzable, los que quedan también lo son
            if (shortest.get(x) == Double.MAX_VALUE) {
                break;
            }

            // relajar las aristas de todos los adyacentes del actual
            for (T adyacente : getAdyacentesDe(actual)) {
                double pesoArista = getPeso(actual, adyacente);
                int y = posiciones.get(adyacente);
                if (shortest.get(x) + pesoArista < shortest.get(y)) {
                    shortest.set(y, shortest.get(x) + pesoArista);
                    predecesor.set(y, actual);
                }
            }
        }

        return new ResultadoCaminos<>(shortest, predecesor);
    }

    // ordenamiento topológico

    /**
     * Devuelve los vértices en orden topológico: si existe una arista de u a v,
     * u aparece antes que v. Solo se aplica a grafos dirigidos.
     *
     * <p>Si el grafo tiene ciclos no existe un orden válido; en ese caso la
     * lista devuelta puede estar incompleta.</p>
     *
     * @return lista de vértices en orden topológico
     * @throws UnsupportedOperationException si el grafo no es dirigido
     */
    public List<T> ordenamientoTopologico() {
        if (!tipo.esDirigido()) {
            throw new UnsupportedOperationException(
                    "El ordenamiento topológico solo está disponible para grafos dirigidos");
        }

        int[] gradoEntrada = new int[vertices.size()];
        Arrays.fill(gradoEntrada, 0);

        for (int i = 0; i < vertices.size(); i++) {
            gradoEntrada[i] = getGradoEntrada(vertices.get(i));
        }

        List<T> siguiente = new ArrayList<>();
        List<T> ordenLineal = new ArrayList<>();

        for (int i = 0; i < gradoEntrada.length; i++) {
            if (gradoEntrada[i] == 0) {
                siguiente.add(vertices.get(i));
            }
        }

        while (!siguiente.isEmpty()) {
            T actual = siguiente.getFirst();
            siguiente.removeFirst();

            ordenLineal.addLast(actual);

            List<T> vecinos = getAdyacentesDe(actual);
            for (T vecino : vecinos) {
                int posVecino = posiciones.get(vecino);
                gradoEntrada[posVecino] -= 1;

                if (gradoEntrada[posVecino] == 0) {
                    siguiente.add(vecino);
                }
            }
        }
        return ordenLineal;
    }

    /**
     * Calcula los caminos más cortos en un grafo acíclico dirigido (DAG)
     * usando su ordenamiento topológico. A diferencia de Dijkstra, admite
     * pesos negativos siempre que el grafo no tenga ciclos.
     *
     * @param vInicio vértice desde el que se calculan las distancias
     * @return resultado con dos listas en orden de inserción de los vértices:
     *         las distancias mínimas y los predecesores de cada camino
     * @throws UnsupportedOperationException si el grafo no es dirigido
     * @throws NullPointerException  si {@code vInicio} es {@code null}
     * @throws IllegalArgumentException si el vértice inicial no existe en el grafo
     */
    public ResultadoCaminos<T> caminosMasCortosDAG(T vInicio) {
        if (!tipo.esDirigido()) {
            throw new UnsupportedOperationException(
                    "Los caminos más cortos en un DAG solo están disponibles para grafos dirigidos");
        }
        int indiceInicio = obtenerIndice(vInicio);

        List<T> ordenLineal = ordenamientoTopologico();
        List<Double> shortest = new ArrayList<>(Collections.nCopies(vertices.size(), Double.MAX_VALUE));
        List<T> predecesores = new ArrayList<>(Collections.nCopies(vertices.size(), null));

        shortest.set(indiceInicio, 0.0);

        for (T actual : ordenLineal) {
            int posActual = posiciones.get(actual);
            if (shortest.get(posActual) == Double.MAX_VALUE) {
                continue;
            }
            for (T vecino : getAdyacentesDe(actual)) {
                int posVecino = posiciones.get(vecino);
                double peso = getPeso(actual, vecino);
                if (shortest.get(posActual) + peso < shortest.get(posVecino)) {
                    shortest.set(posVecino, shortest.get(posActual) + peso);
                    predecesores.set(posVecino, actual);
                }
            }
        }

        return new ResultadoCaminos<>(shortest, predecesores);
    }

    /**
     * Devuelve el peso de la arista que va de origen a destino.
     *
     * @param origen  vértice de origen
     * @param destino vértice de destino
     * @return el peso de la arista, o {@code Double.NaN} si algún vértice no
     *         existe o si la arista no está presente
     */
    public double getPeso(T origen, T destino) {
        Integer i = posiciones.get(origen);
        Integer j = posiciones.get(destino);
        if (i == null || j == null || matriz[i][j] == null) {
            return Double.NaN;
        }
        return matriz[i][j];
    }

    /**
     * Devuelve el número de aristas del grafo. En un grafo no dirigido cada
     * arista se cuenta una sola vez.
     *
     * @return cantidad de aristas
     */
    public int getNumeroAristas() {
        int aristas = 0;
        if (tipo.esDirigido()) {
            for (Double[] fila : matriz) {
                for (Double arista : fila) {
                    if (arista != null) {
                        aristas++;
                    }
                }
            }
        } else {
            for (int i = 0; i < matriz.length; i++) {
                for (int j = i; j < matriz.length; j++) {
                    if (matriz[i][j] != null) {
                        aristas++;
                    }
                }
            }
        }
        return aristas;
    }

    /**
     * Devuelve los vértices adyacentes a uno dado.
     *
     * <p>En un grafo dirigido se consideran únicamente los sucesores (aristas
     * que salen del vértice); en uno no dirigido se consideran todos los
     * vecinos.</p>
     *
     * @param vertice vértice que se desea revisar
     * @return lista de vértices adyacentes en orden de inserción
     * @throws NullPointerException  si {@code vertice} es {@code null}
     * @throws IllegalArgumentException si el vértice no existe en el grafo
     */
    public List<T> getAdyacentesDe(T vertice) {
        int i = obtenerIndice(vertice);
        List<T> resultado = new ArrayList<>();
        for (int j = 0; j < matriz.length; j++) {
            if (matriz[i][j] != null) {
                resultado.add(vertices.get(j));
            }
        }
        return resultado;
    }

    /**
     * Calcula el grado de salida de un vértice: el número de aristas que salen
     * de él. Solo tiene sentido en grafos dirigidos.
     *
     * @param vertice vértice que se desea revisar
     * @return grado de salida, o {@code -1} si el grafo no es dirigido
     * @throws NullPointerException  si {@code vertice} es {@code null}
     * @throws IllegalArgumentException si el vértice no existe en el grafo
     */
    public int getGradoSalida(T vertice) {
        if (tipo.esDirigido()) {
            int i = obtenerIndice(vertice);
            int grado = 0;
            for (int j = 0; j < matriz.length; j++) {
                if (matriz[i][j] != null) {
                    grado++;
                }
            }
            return grado;
        }
        return -1;
    }

    /**
     * Calcula el grado de entrada de un vértice: el número de aristas que
     * llegan a él. Solo tiene sentido en grafos dirigidos.
     *
     * @param vertice vértice que se desea consultar
     * @return grado de entrada, o {@code -1} si el grafo no es dirigido
     * @throws NullPointerException  si {@code vertice} es {@code null}
     * @throws IllegalArgumentException si el vértice no existe en el grafo
     */
    public int getGradoEntrada(T vertice) {
        if (tipo.esDirigido()) {
            int j = obtenerIndice(vertice);
            int grado = 0;
            for (int i = 0; i < matriz.length; i++) {
                if (matriz[i][j] != null) {
                    grado++;
                }
            }
            return grado;
        }
        return -1;
    }

    /**
     * Imprime en la consola el grado de entrada de un vértice.
     *
     * @param vertice vértice que se desea evaluar
     * @throws NullPointerException  si {@code vertice} es {@code null}
     * @throws IllegalArgumentException si el vértice no existe en el grafo
     */
    public void imprimirGradoEntrada(T vertice) {
        int gradEntrada = getGradoEntrada(vertice);
        if (gradEntrada == -1) {
            System.out.println("Este método es exclusivo de los grafos dirigidos");
        } else {
            System.out.println("Grado de entrada del vértice " + verticeString(vertice)
                    + ": " + gradEntrada);
        }
    }

    /**
     * Imprime en la consola el grado de salida de un vértice.
     *
     * @param vertice vértice que se desea evaluar
     * @throws NullPointerException  si {@code vertice} es {@code null}
     * @throws IllegalArgumentException si el vértice no existe en el grafo
     */
    public void imprimirGradoSalida(T vertice) {
        int gradSalida = getGradoSalida(vertice);
        if (gradSalida == -1) {
            System.out.println("Este método es exclusivo de los grafos dirigidos");
        } else {
            System.out.println("Grado de salida del vértice " + verticeString(vertice)
                    + ": " + gradSalida);
        }
    }

    /**
     * Imprime en la consola el conjunto de vértices adyacentes a un vértice.
     *
     * @param vertice vértice del que se calculan e imprimen los adyacentes
     * @throws NullPointerException  si {@code vertice} es {@code null}
     * @throws IllegalArgumentException si el vértice no existe en el grafo
     */
    public void imprimirAdyacentes(T vertice) {
        List<T> adyacentes = getAdyacentesDe(vertice);
        StringBuilder ady = new StringBuilder();
        for (T vecino : adyacentes) {
            ady.append(verticeString(vecino));
        }
        System.out.println("Vértices adyacentes a " + verticeString(vertice)
                + ": " + ady);
    }

    /**
     * Imprime la matriz de adyacencia con los vértices como encabezados de
     * fila y columna.
     *
     * <p>En un grafo no ponderado, 0 indica que no hay arista y 1 que sí la
     * hay; en uno ponderado, 0 indica ausencia de arista y el resto de celdas
     * muestran el peso.</p>
     */
    public void imprimirMatrizAdyacencia() {
        int n = vertices.size();
        System.out.println("Matriz de adyacencia (" + tipo.descripcion() + "):");
        if (n == 0) {
            System.out.println("grafo sin vértices");
            return;
        }

        System.out.print("+   ");
        for (int i = 0; i < matriz.length; i++) {
            System.out.print(verticeString(vertices.get(i)) + "   ");
        }
        System.out.print("\n");

        for (int i = 0; i < matriz.length; i++) {
            System.out.print(verticeString(vertices.get(i)) + "   ");
            for (int j = 0; j < vertices.size(); j++) {
                System.out.print(formatearCelda(matriz[i][j]) + "   ");
            }
            System.out.print("\n");
        }
    }

    /**
     * Imprime el número de vértices y de aristas del grafo.
     */
    public void imprimirNumeroVerticesAristas() {
        System.out.println("Número de vértices: " + getNumeroVertices());
        System.out.println("Número de aristas: " + getNumeroAristas());
    }

    /**
     * Imprime todos los vértices del grafo en orden de inserción.
     */
    public void imprimirVertices() {
        StringBuilder lista = new StringBuilder();
        for (T vertice : vertices) {
            lista.append(verticeString(vertice));
        }
        System.out.println("Vértices: " + lista);
    }

    /**
     * Devuelve la posición que ocupa un vértice en la matriz de adyacencia.
     *
     * @param vertice vértice que se desea localizar
     * @return índice del vértice en la lista de inserción
     * @throws NullPointerException  si {@code vertice} es {@code null}
     * @throws IllegalArgumentException si el vértice no existe en el grafo
     */
    private int obtenerIndice(T vertice) {
        Objects.requireNonNull(vertice, "El vértice no puede ser nulo");
        Integer indice = posiciones.get(vertice);
        if (indice == null) {
            throw new IllegalArgumentException("El vértice " + vertice + " no existe en el grafo");
        }
        return indice;
    }

    /**
     * Da formato al valor de una celda de la matriz para imprimirla.
     *
     * @param valor peso guardado en la celda, o {@code null} si no hay arista
     * @return "0" si no hay arista, "1" en grafos no ponderados o el peso
     */
    private String formatearCelda(Double valor) {
        if (valor == null) {
            return "0";
        }
        if (!tipo.esPonderado()) {
            return "1";
        }
        return String.valueOf(valor);
    }

    /**
     * Convierte un vértice a cadena de texto para los mensajes.
     *
     * @param vertice vértice que se desea representar
     * @return representación textual del vértice
     */
    private String verticeString(T vertice) {
        return String.valueOf(vertice);
    }

    /**
     * Representación textual del grafo.
     *
     * @return cadena con el tipo, los vértices y el número de aristas
     */
    @Override
    public String toString() {
        return "Grafo{" + tipo.descripcion()
                + ", vertices=" + vertices
                + ", aristas=" + getNumeroAristas() + "}";
    }
}
