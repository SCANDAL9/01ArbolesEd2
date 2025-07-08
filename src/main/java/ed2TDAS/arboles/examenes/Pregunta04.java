package ed2TDAS.arboles.examenes;

import ed2TDAS.arboles.clases.ArbolBinarioBusqueda;
import ed2TDAS.arboles.clases.NodoBinario;

public class Pregunta04 <T extends Comparable<T>> extends ArbolBinarioBusqueda<T> {
    /*2.	(ARBOL BINARIO BUSQUEDA) CALCULAR CUANTOS NODOS NECESITA SER COMPLETADO PARA
            QUE UN ARBOL SEA COMPLETO.*/

    public Pregunta04() {
        super();
    }

    public int nodosACompletar(){
        int nodos = altura();
        int totalNodos = size();
        int totalBalance = 0;
        for (int i = 0; i < nodos; i++) {
            totalBalance = (int) (totalBalance + Math.pow(2,i));
        }
        return totalBalance - totalNodos;
    }

}
