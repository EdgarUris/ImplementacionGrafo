/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.grafo_261147;

/**
 * Tipos de grafo soportados, según sea dirigido o no y ponderado o no.
 *
 * @author EdgarUrias
 */
public enum TipoGrafo {

    /** Grafo dirigido sin pesos en sus aristas. */
    DIRIGIDO_NO_PONDERADO(true, false),
    /** Grafo dirigido con pesos en sus aristas. */
    DIRIGIDO_PONDERADO(true, true),
    /** Grafo no dirigido sin pesos en sus aristas. */
    NO_DIRIGIDO_NO_PONDERADO(false, false),
    /** Grafo no dirigido con pesos en sus aristas. */
    NO_DIRIGIDO_PONDERADO(false, true);

    private final boolean dirigido;
    private final boolean ponderado;

    /**
     * Crea un tipo de grafo con las opciones indicadas.
     *
     * @param dirigido  si las aristas tienen dirección
     * @param ponderado si las aristas tienen peso
     */
    TipoGrafo(boolean dirigido, boolean ponderado) {
        this.dirigido = dirigido;
        this.ponderado = ponderado;
    }

    /**
     * Indica si este tipo corresponde a un grafo dirigido.
     *
     * @return {@code true} si es dirigido
     */
    public boolean esDirigido() {
        return dirigido;
    }

    /**
     * Indica si este tipo corresponde a un grafo ponderado.
     *
     * @return {@code true} si es ponderado
     */
    public boolean esPonderado() {
        return ponderado;
    }

    /**
     * Determina el tipo de grafo a partir de dos opciones booleanas.
     *
     * @param dirigido  {@code true} si el grafo será dirigido
     * @param ponderado {@code true} si el grafo será ponderado
     * @return el tipo de grafo correspondiente a esa combinación
     */
    public static TipoGrafo determinarTipo(boolean dirigido, boolean ponderado) {
        if (dirigido) {
            return ponderado ? DIRIGIDO_PONDERADO : DIRIGIDO_NO_PONDERADO;
        }
        return ponderado ? NO_DIRIGIDO_PONDERADO : NO_DIRIGIDO_NO_PONDERADO;
    }

    /**
     * Devuelve una descripción legible del tipo de grafo.
     *
     * @return cadena como "dirigido y ponderado"
     */
    public String descripcion() {
        String direccion = dirigido ? "dirigido" : "no dirigido";
        String pesos = ponderado ? "ponderado" : "no ponderado";
        return direccion + " y " + pesos;
    }
}
