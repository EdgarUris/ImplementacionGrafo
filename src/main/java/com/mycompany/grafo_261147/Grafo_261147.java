/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.grafo_261147;


import java.util.List;

/**
 * Clase principal con ejemplos de uso de la implementación de grafos.
 *
 * @author EdgarUrias
 */
public class Grafo_261147 {

    /**
     * Punto de entrada de la aplicación; ejecuta la prueba de Dijkstra.
     *
     * @param args argumentos de la línea de comandos (no se utilizan)
     */
    public static void main(String[] args) {

        probarGrafoDirigidoPonderado();
    }

    /**
     * Prueba la inserción y eliminación de vértices y aristas en grafos
     * ponderados y no ponderados.
     */
    private static void probarGrafoDirigidoNoPonderado(){
        Nodo<Integer> n1 = new Nodo<>(1);
        Nodo<Integer> n2 = new Nodo<>(2);
        Nodo<Integer> n3 = new Nodo<>(3);
        Nodo<Integer> n4 = new Nodo<>(4);
        Nodo<Integer> n5 = new Nodo<>(5);
        Nodo<Integer> n6 = new Nodo<>(6);
        Nodo<Integer> n7 = new Nodo<>(7);
        Nodo<Integer> n8 = new Nodo<>(8);
        Nodo<Integer> n9 = new Nodo<>(9);
        Nodo<Integer> n10 = new Nodo<>(10);
        System.out.println("Prueba grafo no dirigido ponderado");
        
        //crear y establecer tipo de grafo
        Grafo<Nodo<Integer>> gndp = new Grafo<>(false, true);
        
        //insertar vertices
        System.out.println("Insertando vertices");
        gndp.insertarVertice(n1);
        gndp.insertarVertice(n2);
        gndp.insertarVertice(n3);
        gndp.insertarVertice(n4);
        
        System.out.println("Vertices en el grafo: ");
        gndp.imprimirVertices();
        
        System.out.println("\n\n");
        
        //eliminar vertice
        System.out.println("Eliminando vertice n4");
        gndp.eliminarVertice(n4);
        
        System.out.println("Vertices en el grafo despues de eliminar n4");
        gndp.imprimirVertices();
        
        System.out.println("\n\n");
        
        //insertar arista con ponderación
        System.out.println("Insertando aristas n1 a n2, n2 a n3 y n3 a n1");
        gndp.insertarArista(n1, n2, 3.0);
        gndp.insertarArista(n2, n3, 2.0);
        gndp.insertarArista(n3, n1, 4.0);
        
        System.out.println("Mostrando aristas del grafo");
        //matriz de adyacencia del grafo
        gndp.imprimirMatrizAdyacencia();
        
        System.out.println("\n\n");
        
        //eliminar arista
        System.out.println("Eliminando arista de n3 a n1");
        gndp.eliminarArista(n3, n1);
        gndp.imprimirMatrizAdyacencia();

        System.out.println("\n\n");
        
        //actualizar peso de arista
        System.out.println("Actualizando peso de arista n2 a n3 de 3 a 2.5");
        gndp.actualizarPeso(n2, n3, 2.5);
        gndp.imprimirMatrizAdyacencia();

        System.out.println("\n\n");
        
        System.out.println("\n\n");
        
        //imprimir vertices adyacentes a n1
        System.out.println("Vertices adyacentes a n1");
        gndp.imprimirAdyacentes(n1);
        
        gndp.imprimirNumeroVerticesAristas();
        System.out.println("\n\n");
        
        
        
        System.out.println("Pruebas de grafo dirigido y no ponderado");
        
        Grafo<Nodo<Integer>> gdnp = new Grafo<>(true, false);
        System.out.println("Insertando vertices n5, n6, n7, n8");
        
        gdnp.insertarVertice(n5);
        gdnp.insertarVertice(n6);
        gdnp.insertarVertice(n7);
        gdnp.insertarVertice(n8);
        
        System.out.println("Vertices en el grafo");
        gdnp.imprimirVertices();
        
        System.out.println("\n\n");
        
        System.out.println("Eliminando vertice n8");
        gdnp.eliminarVertice(n8);
        
        System.out.println("Insertando aristas");
        gdnp.insertarArista(n5, n6);
        gdnp.insertarArista(n6, n7);
        gdnp.insertarArista(n7, n5);
        
        System.out.println("Matriz de adyacencia");
        gdnp.imprimirMatrizAdyacencia();
        
        System.out.println("Eliminando todo para probar los recorridos");
        gndp.limpiar();
    }
    
    /**
     * Prueba los recorridos BFS y DFS en un grafo no dirigido ponderado.
     */
    private static void probarGrafoNoDirigidoPonderado(){
        Nodo<Integer> n1 = new Nodo<>(1);
        Nodo<Integer> n2 = new Nodo<>(2);
        Nodo<Integer> n3 = new Nodo<>(3);
        Nodo<Integer> n4 = new Nodo<>(4);
        Nodo<Integer> n5 = new Nodo<>(5);
        Nodo<Integer> n6 = new Nodo<>(6);
        Nodo<Integer> n7 = new Nodo<>(7);
        Nodo<Integer> n8 = new Nodo<>(8);
        Nodo<Integer> n9 = new Nodo<>(9);
        Nodo<Integer> n10 = new Nodo<>(10);
        System.out.println("Prueba de BFS y DFS");
        Grafo<Nodo<Integer>> gndp = new Grafo<>(false, true);
        
        gndp.insertarVertice(n1);
        gndp.insertarVertice(n2);
        gndp.insertarVertice(n3);
        gndp.insertarVertice(n4);
        gndp.insertarVertice(n5);
        gndp.insertarVertice(n6);
        gndp.insertarVertice(n7);
        gndp.insertarVertice(n8);
        gndp.insertarVertice(n9);
        gndp.insertarVertice(n10);
        
        gndp.insertarArista(n1, n2, 1);
        gndp.insertarArista(n2, n3, 1);
        gndp.insertarArista(n3, n4, 1);
        gndp.insertarArista(n2, n4, 1);
        gndp.insertarArista(n3, n5, 1);
        gndp.insertarArista(n4, n5, 1);
        gndp.insertarArista(n5, n6, 1);
        gndp.insertarArista(n6, n7, 1);
        gndp.insertarArista(n6, n8, 1);
        gndp.insertarArista(n9, n10, 1);
        
        gndp.BusquedaEnAnchura(n6);
        
        System.out.println("\n");
        
        gndp.BusquedaEnProfundidad();
    }
    
    /**
     * Prueba el algoritmo de Dijkstra en un grafo dirigido y ponderado.
     */
    private static void probarGrafoDirigidoPonderado(){
        Grafo<Nodo<String>> gdp = new Grafo<>(true, true);
        Nodo<String> s = new Nodo<>("s");
        Nodo<String> t = new Nodo<>("t");
        Nodo<String> x = new Nodo<>("x");
        Nodo<String> y = new Nodo<>("y");
        Nodo<String> z = new Nodo<>("z");
        
        gdp.insertarVertice(s);
        gdp.insertarVertice(t);
        gdp.insertarVertice(x);
        gdp.insertarVertice(y);
        gdp.insertarVertice(z);
        
        gdp.insertarArista(s, t, 6.0);
        gdp.insertarArista(s, y, 4.0);
        gdp.insertarArista(y, t, 1.0);
        gdp.insertarArista(t, y, 2.0);
        gdp.insertarArista(y, x, 3.0);
        gdp.insertarArista(y, z, 9.0);
        gdp.insertarArista(t, z, 3.0);
        gdp.insertarArista(x, z, 5.0);
        gdp.insertarArista(z, x, 4.0);
        gdp.insertarArista(x, s, 7.0);
        

        ResultadoCaminos<Nodo<String>> resultado = gdp.dijkstra(s);
        List<Double> distancias = resultado.getDistancias();
        List<Nodo<String>> predecesores = resultado.getPredecesores();
        List<Nodo<String>> vertices = gdp.obtenerVertices();

        for (int i = 0; i < distancias.size(); i++) {
            System.out.println(vertices.get(i) + ": " + distancias.get(i)
                    + " (predecesor: " + predecesores.get(i) + ")");
        }

    }

    /**
     * Prueba el ordenamiento topológico y los caminos más cortos en un DAG.
     */
    private static void probarOrdenamientoTopologico() {
        Grafo<Nodo<String>> gd = new Grafo<>(true, true);

        Nodo<String> a = new Nodo<>("A");
        Nodo<String> b = new Nodo<>("B");
        Nodo<String> c = new Nodo<>("C");
        Nodo<String> d = new Nodo<>("D");
        Nodo<String> e = new Nodo<>("E");
        Nodo<String> f = new Nodo<>("F");
        Nodo<String> g = new Nodo<>("G");
        Nodo<String> h = new Nodo<>("H");
        Nodo<String> i = new Nodo<>("I");
        Nodo<String> j = new Nodo<>("J");

        gd.insertarVertice(a);
        gd.insertarVertice(b);
        gd.insertarVertice(c);
        gd.insertarVertice(d);
        gd.insertarVertice(e);
        gd.insertarVertice(f);
        gd.insertarVertice(g);
        gd.insertarVertice(h);
        gd.insertarVertice(i);
        gd.insertarVertice(j);

        gd.insertarArista(a, d, 3);
        gd.insertarArista(b, d, 6);
        gd.insertarArista(c, f, 6);
        gd.insertarArista(d, f, 3);
        gd.insertarArista(d, e, 5);
        gd.insertarArista(e, g, 3);
        gd.insertarArista(e, h, 2);
        gd.insertarArista(h, j, 5);
        gd.insertarArista(f, i, 1);
        gd.insertarArista(i, j, 2);
        gd.insertarArista(d, h, 6);

        List<Nodo<String>> orden = gd.ordenamientoTopologico();

        for (Nodo<String> n : orden) {
            System.out.println(n);
        }

        ResultadoCaminos<Nodo<String>> resultado = gd.caminosMasCortosDAG(a);
        List<Double> distancias = resultado.getDistancias();
        List<Nodo<String>> predecesores = resultado.getPredecesores();
        List<Nodo<String>> vertices = gd.obtenerVertices();

        System.out.println("Caminos mas cortos desde el nodo: " + a);
        for (int v = 0; v < distancias.size(); v++) {
            String distancia = distancias.get(v) == Double.MAX_VALUE
                    ? "inalcanzable"
                    : distancias.get(v).toString();
            System.out.println(vertices.get(v) + ": " + distancia
                    + " (predecesor: " + predecesores.get(v) + ")");
        }

    }
}
