/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.grafo_261147;

/**
 * @author EdgarUrias
 */
public enum TipoGrafo{

    DIRIGIDO_NO_PONDERADO(true, false),
    DIRIGIDO_PONDERADO(true, true),
    NO_DIRIGIDO_NO_PONDERADO(false, false),
    NO_DIRIGIDO_PONDERADO(false, true);

    private final boolean dirigido;
    private final boolean ponderado;

    TipoGrafo(boolean dirigido, boolean ponderado){
        this.dirigido = dirigido;
        this.ponderado = ponderado;
    }

    public boolean esDirigido(){
        return dirigido;
    }

    public boolean esPonderado(){
        return ponderado;
    }

    public static TipoGrafo determinarTipo(boolean dirigido, boolean ponderado){
        if (dirigido){
            return ponderado ? DIRIGIDO_PONDERADO : DIRIGIDO_NO_PONDERADO;
        }
        return ponderado ? NO_DIRIGIDO_PONDERADO : NO_DIRIGIDO_NO_PONDERADO;
    }

    public String descripcion() {
        String direccion = dirigido ? "dirigido" : "no dirigido";
        String pesos = ponderado ? "ponderado" : "no ponderado";
        return direccion + " y " + pesos;
    }
}
