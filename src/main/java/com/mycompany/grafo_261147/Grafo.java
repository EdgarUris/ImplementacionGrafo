/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.grafo_261147;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/*
 * @param <T> tipo de dato almacenado en los vertices
 * @author EdgarUrias
 */
public class Grafo<T> {

    private Double[][] matriz;
    private final List<T> vertices; //vertices del grafo en orden determinarTipo insercion
    private final Map<T, Integer> posiciones; //guarda cada vertice con su posicion
    private TipoGrafo tipo;

    //grafo por defecto
    public Grafo() {
        this(TipoGrafo.NO_DIRIGIDO_NO_PONDERADO);
    }

    //crear un grafo del tipo solicitado
    public Grafo(TipoGrafo tipo) {
        this.tipo = tipo;
        this.vertices = new ArrayList<>();
        this.posiciones = new HashMap<>();
        this.matriz = new Double[0][0];
    }

    //crear un grafo solo indicando si es dirigido y ponderado 
    public Grafo(boolean dirigido, boolean ponderado) {
        this(TipoGrafo.determinarTipo(dirigido, ponderado));
    }

    public TipoGrafo getTipo() {
        return tipo;
    }

    public boolean esDirigido(){
        return tipo.esDirigido();
    }

    public boolean esPonderado(){
        return tipo.esPonderado();
    }
    
    public boolean estaVacio(){
        return vertices.isEmpty();
    }
    
    //elimina todos los vertices y aristas 
    public void limpiar(){
        vertices.clear();
        posiciones.clear();
        matriz = new Double[0][0];
    }


    //metodos para los vertices
    
    /**
     * inserta un vertice en el grafo. Si el vertice ya existe, no se modifica
     * el grafo.
     *
     * @param vertice un vertice para insertar
     * @return true si el vertice se insertó, o false si ya existia.
     * @throws Exception si vertice es null.
     */
    public boolean insertarVertice(T vertice) throws Exception{
        if (vertice == null){
            throw new Exception("El vértice no puede ser null");
        }
        if (posiciones.containsKey(vertice)){
            return false;
        }

        int n = vertices.size();
        vertices.add(vertice);
        posiciones.put(vertice, n);

        //la matriz aumenta 1 fila y 1 columna
        Double[][] nueva = new Double[n + 1][n + 1];
        for (int i = 0; i < n; i++){
            System.arraycopy(matriz[i], 0, nueva[i], 0, n);
        }
        matriz = nueva;
        return true;
    }

    /**
     * elimina un vertice y todas las aristas relacionadas a ese vertice
     *
     * @param vertice vertice que se desea eliminar
     * @return true si el vértice existía y fue eliminado, false en caso contrario
     */
    public boolean eliminarVertice(T vertice){
        if (vertice == null) {
            return false;
        }
        int indice = posiciones.get(vertice);

        //reducir la matriz de adyacencia -1
        int n = vertices.size();
        Double[][] nueva = new Double[n - 1][n - 1];
        int filaNueva = 0;
        for (int i = 0; i < n; i++){
            if (i != indice){ 
                /*con el indice donde estaba el vertice a eliminar, se elimina la fila y columna
                que le corresponde*/
                
                int columnaNueva = 0;
                
                for (int j = 0; j < n; j++){
                    if (j != indice){
                        nueva[filaNueva][columnaNueva] = matriz[i][j];
                        columnaNueva++;
                    }
                }
                
                filaNueva++;
            }
        }

        vertices.remove(indice);
        posiciones.remove(vertice);

        //se actualizan los indices de los vertices que quedaron despues
        for (int k = indice; k < vertices.size(); k++) {
            posiciones.put(vertices.get(k), k);
        }

        matriz = nueva;
        return true;
    }

    /**
     * indica si un vertice pertenece al grafo
     *
     * @param vertice vertice a comprobar
     * @return true si el vertice existe, false si no pertenece
     */
    public boolean existeVertice(T vertice){
        if(vertice != null){
            return posiciones.containsKey(vertice);
        }
        else return false;
    }

    /**
     * devuelve una lista de los vertices del grafo en orden de inserción
     * @return lista con los vertices
     */
    public List<T> obtenerVertices(){
        return vertices;
    }

    /**
     * devuelve el numero de vertices del grafo
     *
     * @return cantidad de vertices
     */
    public int getNumeroVertices(){
        return vertices.size();
    }
    
    /**
     * inserta una arista sin especificar peso, por defecto 1
     *
     * @param origen  vertice de origen
     * @param destino vertice de destino
     * @return true si la arista no existia, false si ya existia y solo se actualizó,
     * si origen y destino son el mismo vértice.
     */
    public boolean insertarArista(T origen, T destino) throws Exception{
        return insertarAristaInterna(origen, destino, 1.0);
    }

    /**
     * inserta una arista en un grafo ponderado
     * @param origen  vertice de origen
     * @param destino vertice de destino
     * @param peso    peso de la arista
     * @return true si la arista se agregó, false si ya existia
     * @throws Exception si el grafo no es ponderado, si algun vertice no existe, si
     * los vertices son iguales o si el peso no es un numero
     */
    public boolean insertarArista(T origen, T destino, double peso) throws Exception{
        if (!tipo.esPonderado()){
            System.out.println("No se puede insertar la arista con peso en un grafo no ponderado");
        }
        return insertarAristaInterna(origen, destino, peso);
    }
    
    
    private boolean insertarAristaInterna(T origen, T destino, double peso) throws Exception{
        //verificar que el peso sea un numero
        if(Double.isNaN(peso)){
            throw new Exception("El peso tiene que ser un numero");
        }
        
        //verificar que los vertices sean diferentes
        int i = obtenerIndice(origen);
        int j = obtenerIndice(destino);
        if (i == j){
            matriz[i][j] = peso;
            return true;
        }
        
        //mejor explicación de este metodo en el documento
        boolean nueva = matriz[i][j] == null 
                && (tipo.esDirigido() || matriz[j][i] == null);

        //poner en la matriz de adyacencia los valores del peso de la arista nueva
        matriz[i][j] = peso;
        if (!tipo.esDirigido()){
            matriz[j][i] = peso;
        }
        return nueva;
    }

    /**
     * elimina la arista indicada
     *
     * en un grafo dirigido se elimina unicamente la arista
     * origen a destino y en un grafo no dirigido se eliminan ambos
     * sentidos
     *
     * @param origen  vertice de origen
     * @param destino vertice de destino
     * @return true si la arista existia y fue eliminada, false en caso contrario
     */
    public boolean eliminarArista(T origen, T destino){
        Integer i = posiciones.get(origen);
        Integer j = posiciones.get(destino);
        if (i == null || j == null){
            return false;
        }

        boolean existia = matriz[i][j] != null;
        matriz[i][j] = null;
        if (!tipo.esDirigido()){
            matriz[j][i] = null;
        }
        return existia;
    }

    /**
     * indica si existe una arista entre dos vertices.
     * @param origen vertice de origen
     * @param destino vertice de destino
     * @return true si la arista existe
     */
    public boolean existeArista(T origen, T destino){
        Integer i = posiciones.get(origen);
        Integer j = posiciones.get(destino);
        return i != null && j != null && matriz[i][j] != null;
    }

    /**
     * actualiza el peso de una arista que ya existe,
     * solo esta disponible para grafos ponderados
     *
     * @param origen vertice de origen
     * @param destino vertice de destino
     * @param peso nuevo peso
     * @return true si la arista existia y se actualizó, false si la arista no existe
     * @throws Exception si el grafo no es ponderado, si alguno de los vertices no existe,
     * si los vertices son iguales o si el peso no es numero
     */
    public boolean actualizarPeso(T origen, T destino, double peso) throws Exception {
        if (!tipo.esPonderado()){
            throw new Exception("No se puede actualizar un peso: el grafo no es ponderado");
        }
        if(Double.isNaN(peso));
        int i = obtenerIndice(origen);
        int j = obtenerIndice(destino);
        if (i == j){
            throw new Exception("Los vertices deben ser diferentes");
        }
        if (matriz[i][j] == null){
            return false;
        }

        matriz[i][j] = peso;
        if (!tipo.esDirigido()){ //si no es dirigido, el peso es igual de v1 a v2 que de v2 a v1
            matriz[j][i] = peso;
        }
        return true;
    }
    
    // recorridos BFS y DFS
    
    public void BusquedaEnAnchura(T verticeInicio) throws Exception{
        boolean visitado[] = new boolean[vertices.size()];
        Queue<T> cola = new LinkedList<>();
        
        int indiceInicio = posiciones.get(verticeInicio);
        visitado[indiceInicio] = true;
        
        cola.add(verticeInicio);
        System.out.println("Busqueda en anchura empezando desde: " + verticeInicio.toString());
        
        while(!cola.isEmpty()){
            T actual = cola.poll();
            System.out.println(String.valueOf(actual));
            
            for(T vecino : getAdyacentesDe(actual)){
                if(!visitado[posiciones.get(vecino)]){
                    visitado[posiciones.get(vecino)] = true;
                    cola.add(vecino);
                }
            }
        }      
    }
    
    public void BusquedaEnProfundidad(T verticeInicio) throws Exception{
        boolean[] visitado = new boolean[vertices.size()];
        System.out.println("Busqueda en profundidad empezando desde " +String.valueOf(verticeInicio));
        BusquedaEnProfundidadRec(verticeInicio, visitado);
        for (int i = 0; i < vertices.size(); i++) {
            if(visitado[i] == false){
                BusquedaEnProfundidadRec(vertices.get(i), visitado);
            }
        }
    }
    
    private void BusquedaEnProfundidadRec(T actual, boolean[] visitado) throws Exception{
        visitado[posiciones.get(actual)] = true;
        System.out.println(String.valueOf(actual));
        
        for(T vecino : getAdyacentesDe(actual)){
            if(!visitado[posiciones.get(vecino)]){
                BusquedaEnProfundidadRec(vecino, visitado);
            }
        }
    }
    
    public Double[] dijkstra(T vOrigen) throws Exception{
        Double[] shortest = new Double[vertices.size()];
        ArrayList<T> predecesor = new ArrayList<>();
        
        Arrays.fill(shortest, Double.MAX_VALUE);
        for (T vertice : vertices) {
            predecesor.add(null);
        }
        
        shortest[posiciones.get(vOrigen)] = 0.0;
        
        Queue<T> cola = new LinkedList<>();
        cola.add(vOrigen);
        
        for(T vertice : vertices){
            cola.add(vertice);
        }
        
        while(!cola.isEmpty()){
            
            T actual = cola.poll();
            List<T> adyacentes = getAdyacentesDe(actual);
            for (T adyacente : adyacentes){
                int x = posiciones.get(actual);
                int y = posiciones.get(adyacente);
                if(shortest[x] + matriz[x][y] > shortest[y]){
                    shortest[y] = shortest[x] + matriz[x][y];
                    predecesor.set(y, actual);
                }
            }
            
        }
        return shortest;
    }
    
    
    public double getPeso(T origen, T destino) {
        Integer i = posiciones.get(origen);
        Integer j = posiciones.get(destino);
        if (i == null || j == null || matriz[i][j] == null) {
            return Double.NaN;
        }
        return matriz[i][j];
    }

    /**
     * devuelve el numero de aristas del grafo, en un grafo no dirigido cada
     * arista se cuenta una sola vez
     *
     * @return cantidad de aristas
     */
    public int getNumeroAristas(){
        int aristas = 0;
        if(tipo.esDirigido()){
            for (Double[] fila : matriz){
                for (Double arista : fila){
                    if(arista != null){
                        aristas++;
                    }
                }
            }
        }
        else{
            for (int i = 0; i < matriz.length; i++){
                for (int j = i; j < matriz.length; j++){
                    if(matriz[i][j] != null){
                        aristas++;
                    }
                }
            }
        }
        return aristas;
    }

    /**
     * devuelve los vertices adyacentes a un vertice.
     *
     * en un grafo dirigido se consideran unicamente los sucesores (aristas
     * que salen del vertice) y en un grafo no dirigido se consideran todos los
     * vecinos
     *
     * @param vertice vertice a revisar
     * @return lista de vertices en orden de inserción.
     */
    public List<T> getAdyacentesDe(T vertice) throws Exception{
        int i = obtenerIndice(vertice);
        List<T> resultado = new ArrayList<>();
        for(int j = 0; j < matriz.length; j++){
            if(matriz[i][j] != null) {
                resultado.add(vertices.get(j));
            }
        }
        return resultado;
    }

    /**
     * calcula el grado de salida del vertice, el numero de aristas
     * que salen de ese vertice
     *
     * @param vertice vertice a revisar
     * @return grado de salida
     */
    public int getGradoSalida(T vertice) throws Exception{
        if(tipo.esDirigido()){
            int i = obtenerIndice(vertice);
            int grado = 0;
            for(int j = 0; j < matriz.length; j++){
                if (matriz[i][j] != null){
                    grado++;
                }
            }
            return grado;
        }
        else{
            return -1;
        }
    }

    /**
     * calcula el grado de entrada de un vertice es decir
     * el numero de aristas que entran en ese vertice
     *
     * @param vertice vertice de consulta.
     * @return grado de entrada
     */
    public int getGradoEntrada(T vertice) throws Exception{
        if(tipo.esDirigido()){
            int j = obtenerIndice(vertice);
            int grado = 0;
            for(int i = 0; i < matriz.length; i++){
                if (matriz[i][j] != null){
                    grado++;
                }
            }
            return grado;
        }
        else{
            return -1;
        }
    }
    
    /***
     * 
     * permite imprimir el grado de entrada de un vertice especificado
     * 
     * @param vertice el vertice para evaluar
     */
    public void imprimirGradoEntrada(T vertice) throws Exception{
        int gradEntrada = getGradoEntrada(vertice);
        if(gradEntrada == -1){
            System.out.println("Este metodo es exclusivo de los grafos dirigidos");
        }
        else{
            System.out.println("Grado de entrada del vertice " + verticeString(vertice) + ": " + String.valueOf(gradEntrada));
        }
    }
    
    /***
     * 
     * permite imprimir el grado de salida de un vertice especificado
     * 
     * @param vertice el vertice para evaluar
     */
    public void imprimirGradoSalida(T vertice) throws Exception{
        int gradSalida = getGradoSalida(vertice);
        if(gradSalida == -1){
            System.out.println("Este metodo es exclusivo de los grafos dirigidos");
        }
        else{
            System.out.println("Grado de salida del vertice " + verticeString(vertice) + ": " + String.valueOf(gradSalida));
        }
    }
    
    /**
     * imprime el conjunto de vertices adyacentes a un vertice
     *
     * @param vertice es el vertice para calcular los adyacentes e imprimirlos
     */
    public void imprimirAdyacentes(T vertice) throws Exception{
        List<T> adyacentes = getAdyacentesDe(vertice);
        StringBuilder ady = new StringBuilder();
        for(T vecino : adyacentes){
            ady.append(verticeString(vecino));
        }
        System.out.println("Vertices adyacentes a " + verticeString(vertice)
                + ": " + ady);
    }

    /**
     * imprime la matriz de adyacencia del grafo con los vertices como
     * encabezados de fila y columna
     *
     * en un grafo no ponderado, 0 indica que no hay arista y
     * 1 que si hay arista, en un grafo ponderado 0 indicq que no hay arista
     * y el resto de celdas muestran el peso
     */
    public void imprimirMatrizAdyacencia(){
        int n = vertices.size();
        System.out.println("Matriz de adyacencia (" + tipo.descripcion() + "):");
        if (n == 0) {
            System.out.println("grafo sin vertices");
            return;
        }
        
        //encabezado
        System.out.print("+   ");
        for (int i = 0; i < matriz.length; i++){
            System.out.print(verticeString(vertices.get(i)) + "   ");
        }
        System.out.print("\n");
        
        //filas
        for (int i = 0; i < matriz.length; i++){
            System.out.print(verticeString(vertices.get(i)) + "   ");
            for (int j = 0; j < vertices.size(); j++){
                System.out.print(formatearCelda(matriz[i][j])+ "   ");
            }
            System.out.print("\n");
        }
    }

    
    //imprime el numero de vertices y de aristas del grafo. 
    public void imprimirNumeroVerticesAristas(){
        System.out.println("Numero de vertices: "+getNumeroVertices());
        System.out.println("Numero de aristas: "+getNumeroAristas());
    }

    //imprime todos los vertices del grafo en orden de inserción.
    public void imprimirVertices(){
        StringBuilder lista = new StringBuilder();
        for (T vertice : vertices){
            lista.append(verticeString(vertice));
        }
        System.out.println("Vertices: " + lista);
    }

    private int obtenerIndice(T vertice) throws Exception {
        if (vertice == null){
            throw new Exception("El vertice no puede ser nulo");
        }
        Integer indice = posiciones.get(vertice);
        if (indice == null){
            throw new Exception("El vertice " + vertice + " no existe en el grafo");
        }
        return indice;
    }

    private String formatearCelda(Double valor){
        if(valor == null){
            return "0";
        }
        if(!tipo.esPonderado()){
            return "1";
        }
        return String.valueOf(valor);
    }

    
    private String verticeString(T vertice){
        return String.valueOf(vertice);
    }
    
    @Override
    public String toString(){
        return "Grafo{" + tipo.descripcion()
                + ", vertices=" + vertices
                + ", aristas=" + getNumeroAristas() + "}";
    }

}
