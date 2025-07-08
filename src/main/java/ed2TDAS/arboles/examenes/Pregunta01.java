package ed2TDAS.arboles.examenes;

import ed2TDAS.arboles.clases.ArbolMViasBusqueda;
import ed2TDAS.arboles.clases.NodoMVias;
import ed2TDAS.arboles.excepciones.ExcepcionOrdenInvalido;

/**
 * @author Ismael
 * @param <T>
 */
public class Pregunta01 <T extends Comparable<T>> extends ArbolMViasBusqueda {
    /*
     *  1. Para un árbol mvias de búsqueda implementar un método que reciba una clave, la busque
     *  en el árbol, en caso de encontrar la llave que retorne en que nivel está. Que retorne -1
     *  en caso de no estar la clave en el árbol. La implementación debe ser recursiva.
     */

    public Pregunta01(int orden) throws ExcepcionOrdenInvalido {
        super(orden);
    }

    public int nivelEnDondeSeEncuentraUnDato(T datoObjetivo) {
        return nivelEnDondeSeEncuentraUnDato(super.raiz, datoObjetivo, 0);
    }


    private int nivelEnDondeSeEncuentraUnDato(NodoMVias<T> nodoActual, T datoObjetivo, int nivelActual) {
        if (datoObjetivo == null) {
            throw new IllegalArgumentException("La clave no puede ser null");
        }
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return -1;
        }

        for (int i = 0; i < nodoActual.nroDeDatosNoVacios(); i++) {
            T datoEnNodo = nodoActual.getDato(i);

            if (datoObjetivo.compareTo(datoEnNodo) == 0) {
                return nivelActual;
            }

            if (datoObjetivo.compareTo(datoEnNodo) < 0) {
                return nivelEnDondeSeEncuentraUnDato(nodoActual.getHijo(i), datoObjetivo,
                        nivelActual + 1);
            }
        }

        return nivelEnDondeSeEncuentraUnDato(nodoActual.getHijo(nodoActual.nroDeDatosNoVacios()),
                datoObjetivo, nivelActual + 1);
    }


    private int nivelEnDondeSeEncuentraUnDato2(NodoMVias<T> nodoActual, T datoObjetivo, int nulo) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return -(nulo + 1);
        }

        int nivelDelDato = 0;
        for (int i = 0; i < nodoActual.nroDeDatosNoVacios(); i++) {
            T datoEnNodo = nodoActual.getDato(i);
            if (datoObjetivo.compareTo(datoEnNodo) < 0) {
                nodoActual = nodoActual.getHijo(i);
                nivelDelDato += nivelEnDondeSeEncuentraUnDato(nodoActual, datoObjetivo, nulo + 1);
                break;
            }
            if (datoObjetivo.compareTo(datoEnNodo) == 0) {
                return 0;
            }
        }
        if (datoObjetivo.compareTo(nodoActual.getDato(nodoActual.nroDeDatosNoVacios() - 1)) > 0) {
            nodoActual = nodoActual.getHijo(nodoActual.nroDeDatosNoVacios());
            nivelDelDato += nivelEnDondeSeEncuentraUnDato(nodoActual, datoObjetivo, nulo + 1);
        }

        return nivelDelDato + 1;
    }
}
