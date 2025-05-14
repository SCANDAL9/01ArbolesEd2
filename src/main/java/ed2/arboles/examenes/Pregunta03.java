package ed2.arboles.examenes;

import ed2.arboles.clases.ArbolBinarioBusqueda;

public class Pregunta03 <T extends Comparable<T>> extends ArbolBinarioBusqueda {

    /**
     * 3.  Para un árbol binario implementar un método que retorne verdadero si el árbol es zurdo,
     * falso en caso contrario. Diremos que el árbol binario es zurdo si se cumple lo siguiente:
     *    3.1. Si el árbol es vacio; o
     *    3.2. Si el árbol es una hoja; o
     *    3.3. Si para cualquier nodo, su hijo izquierdo y derecho son zurdos y el número de nodos
     *    descendientes no vacios del hijo izquierdo son mayores que el número de nodos descencientes
     *    no vacios del hijo derecho.
     */
    public boolean arbolEsZurdo(){
        return true;
    }
}
