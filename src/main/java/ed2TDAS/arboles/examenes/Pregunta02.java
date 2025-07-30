package ed2TDAS.arboles.examenes;

import ed2TDAS.arboles.clases.ArbolBinarioBusqueda;
import ed2TDAS.arboles.clases.NodoBinario;

public class Pregunta02  <T extends Comparable<T>> extends ArbolBinarioBusqueda<T> {

    /**
     * 2. Para un árbol binario de búsqueda implemente un método que retorne
     * otro arbol que sea el reflejo del árbol original.
     */

    protected NodoBinario<T> raizNueva;

    public Pregunta02() {
        super();
    }

    public ArbolBinarioBusqueda reflejarArbol() {

        Pregunta02 arbolNuevo = new Pregunta02<>();
        arbolNuevo.raizNueva = this.raizNueva = reflejarArbol(this.raiz);
        return arbolNuevo;
    }
    private NodoBinario<T> reflejarArbol(NodoBinario<T> nodoActual) {

        if (esArbolVacio()) {
            return NodoBinario.nodoVacio();
        }
        if (NodoBinario.esNodoVacio(nodoActual)){
            return NodoBinario.nodoVacio();
        }
        NodoBinario<T> nodoNuevo = new NodoBinario<>(nodoActual.getDato());
        NodoBinario<T> supuestoNuevoHijoIzquierdo = reflejarArbol(
                nodoActual.getHijoDerecho());
        nodoNuevo.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);

        NodoBinario<T> supuestoNuevoHijoDerecho = reflejarArbol(
                nodoActual.getHijoIzquierdo());
        nodoNuevo.setHijoDerecho(supuestoNuevoHijoDerecho);

        return nodoNuevo;
    }
}
