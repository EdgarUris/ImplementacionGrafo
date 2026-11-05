/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.grafo_261147;


/**
 * @author EdgarUrias
 */
public class Grafo_261147 {

    public static void main(String[] args) throws Exception {
        
        probarGrafoDirigidoPonderado();
    }
    
    private static void probarGrafoDirigidoNoPonderado() throws Exception{
        Nodo n1 = new Nodo(1);
        Nodo n2 = new Nodo(2);
        Nodo n3 = new Nodo(3);
        Nodo n4 = new Nodo(4);
        Nodo n5 = new Nodo(5);
        Nodo n6 = new Nodo(6);
        Nodo n7 = new Nodo(7);
        Nodo n8 = new Nodo(8);
        Nodo n9 = new Nodo(9);
        Nodo n10 = new Nodo(10);
        System.out.println("Prueba grafo no dirigido ponderado");
        
        //crear y establecer tipo de grafo
        Grafo gndp = new Grafo(false, true);
        
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
        
        Grafo gdnp = new Grafo(true, false);
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
    
    private static void probarGrafoNoDirigidoPonderado() throws Exception{
        Nodo n1 = new Nodo(1);
        Nodo n2 = new Nodo(2);
        Nodo n3 = new Nodo(3);
        Nodo n4 = new Nodo(4);
        Nodo n5 = new Nodo(5);
        Nodo n6 = new Nodo(6);
        Nodo n7 = new Nodo(7);
        Nodo n8 = new Nodo(8);
        Nodo n9 = new Nodo(9);
        Nodo n10 = new Nodo(10);
        System.out.println("Prueba de BFS y DFS");
        Grafo gndp = new Grafo(false, true);
        
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
        
        gndp.BusquedaEnProfundidad(n6);
    }
    
    private static void probarGrafoDirigidoPonderado() throws Exception{
        Grafo gdp = new Grafo(true, true);
        Nodo s = new Nodo("s");
        Nodo t = new Nodo("t");
        Nodo x = new Nodo("x");
        Nodo y = new Nodo("y");
        Nodo z = new Nodo("z");
        
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
        

        Double[] distancias = gdp.dijkstra(y);
        for (int i = 0; i < distancias.length; i++) {
            System.out.println(distancias[i] + "");
        }
        
    }
}
