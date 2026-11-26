/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.grafo_261147;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Guarda el resultado de un algoritmo de caminos más cortos.
 *
 * <p>Contiene dos listas paralelas: la distancia mínima desde el origen hasta
 * cada vértice y el predecesor de cada vértice en ese camino. Ambas listas
 * siguen el orden de inserción de los vértices del grafo, por lo que la
 * posición {@code i} corresponde siempre al mismo vértice en las dos.</p>
 *
 * @param <T> tipo de dato almacenado en los vértices
 * @author EdgarUrias
 */
public class ResultadoCaminos<T> {

    private final List<Double> distancias;
    private final List<T> predecesores;

    /**
     * Crea un resultado a partir de las dos listas calculadas por el algoritmo.
     *
     * @param distancias  distancia mínima a cada vértice; {@code Double.MAX_VALUE}
     *                    significa que el vértice es inalcanzable
     * @param predecesores predecesor de cada vértice en el camino mínimo;
     *                     {@code null} para el origen y para los inalcanzables
     * @throws NullPointerException  si alguna de las listas es {@code null}
     * @throws IllegalArgumentException si las listas tienen tamaños distintos
     */
    public ResultadoCaminos(List<Double> distancias, List<T> predecesores) {
        Objects.requireNonNull(distancias, "La lista de distancias no puede ser null");
        Objects.requireNonNull(predecesores, "La lista de predecesores no puede ser null");
        if (distancias.size() != predecesores.size()) {
            throw new IllegalArgumentException(
                    "Distancias y predecesores deben tener el mismo tamaño");
        }
        //para q no se mueva nADA
        this.distancias = Collections.unmodifiableList(new ArrayList<>(distancias));
        this.predecesores = Collections.unmodifiableList(new ArrayList<>(predecesores));
    }

    /**
     * Devuelve las distancias mínimas en orden de inserción de los vértices.
     *
     * @return lista no modificable con las distancias
     */
    public List<Double> getDistancias() {
        return distancias;
    }

    /**
     * Devuelve los predecesores en orden de inserción de los vértices.
     *
     * @return lista no modificable con los predecesores
     */
    public List<T> getPredecesores() {
        return predecesores;
    }

    /**
     * Representación textual del resultado.
     *
     * @return cadena con las dos listas
     */
    @Override
    public String toString() {
        return "ResultadoCaminos{distancias=" + distancias
                + ", predecesores=" + predecesores + "}";
    }
}
