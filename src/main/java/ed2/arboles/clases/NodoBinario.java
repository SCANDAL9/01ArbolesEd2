/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ed2.arboles.clases;

/**
 *
 * @author Flia Maldonado
 * @param <T>
 */
public class NodoBinario<T> { //Alt + Ins

    private T dato;
    private NodoBinario<T> hijoIzquierdo;
    private NodoBinario<T> hijoDerecho;

    public NodoBinario() {
    }

    public NodoBinario(T dato) {
        this.dato = dato;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoBinario<T> getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public void setHijoIzquierdo(NodoBinario<T> hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public NodoBinario<T> getHijoDerecho() {
        return hijoDerecho;
    }

    public void setHijoDerecho(NodoBinario<T> hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }

    public boolean esVacioHijoIzquierdo() {
        return NodoBinario.esNodoVacio(this.hijoIzquierdo);
    }

    public boolean esVacioHijoDerecho() {
        return NodoBinario.esNodoVacio(this.hijoDerecho);
    }

    public boolean esHoja() {
        return this.esVacioHijoIzquierdo()
                && this.esVacioHijoDerecho();
    }

    //static
    public static boolean esNodoVacio(NodoBinario nodo) {
        return nodo == NodoBinario.nodoVacio();
    }
    
    public static NodoBinario nodoVacio() {
        return null;
    }



}


    

