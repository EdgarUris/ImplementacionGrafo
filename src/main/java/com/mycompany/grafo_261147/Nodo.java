/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.grafo_261147;

/**
 *
 * @author EdgarUrias
 */
public class Nodo<T> {
    private T contenido;

    public Nodo() {
    }

    public Nodo(T contenido) {
        this.contenido = contenido;
    }

    public T getContenido() {
        return contenido;
    }

    public void setContenido(T contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return String.valueOf(contenido);
    }
    
}
