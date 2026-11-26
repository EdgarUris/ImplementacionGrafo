/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grafo_261147;

/**
 * Nodo genérico que guarda un contenido y puede utilizarse como vértice de un
 * {@link Grafo}.
 *
 * @param <T> tipo del contenido almacenado
 * @author EdgarUrias
 */
public class Nodo<T> {

    private T contenido;

    /**
     * Crea un nodo sin contenido.
     */
    public Nodo() {
    }

    /**
     * Crea un nodo con el contenido indicado.
     *
     * @param contenido contenido que guardará el nodo
     */
    public Nodo(T contenido) {
        this.contenido = contenido;
    }

    /**
     * Devuelve el contenido del nodo.
     *
     * @return contenido almacenado
     */
    public T getContenido() {
        return contenido;
    }

    /**
     * Cambia el contenido del nodo.
     *
     * @param contenido nuevo contenido del nodo
     */
    public void setContenido(T contenido) {
        this.contenido = contenido;
    }

    /**
     * Representación textual del nodo.
     *
     * @return el contenido convertido a cadena
     */
    @Override
    public String toString() {
        return String.valueOf(contenido);
    }
}
