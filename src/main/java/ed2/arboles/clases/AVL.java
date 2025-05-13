package ed2.arboles.clases;

import ed2.arboles.excepciones.ExcepcionDatoNoExiste;
import ed2.arboles.excepciones.ExcepcionDatoYaExiste;

public class AVL <T extends Comparable<T>>
        extends ArbolBinarioBusqueda<T> {

    private static byte LIMITE_MAXIMO = 1;

    @Override
    public void eliminar(T datoAEliminar) throws ExcepcionDatoNoExiste {
        super.raiz = eliminar(super.raiz, datoAEliminar);
    }
    private NodoBinario<T> eliminar(NodoBinario<T> nodoActual, T datoAEliminar)
            throws ExcepcionDatoNoExiste {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            throw new ExcepcionDatoNoExiste();
        }
        T datoDelNodoActual = nodoActual.getDato();
        if (datoAEliminar.compareTo(datoDelNodoActual) < 0) {
            NodoBinario<T> supuestoNuevoHijoIzquierdo =
                    eliminar(nodoActual.getHijoIzquierdo(), datoAEliminar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzquierdo);
            return balancear(nodoActual);
        }

        if (datoAEliminar.compareTo(datoDelNodoActual) > 0) {
            NodoBinario<T> supuestoNuevoHijoDerecho =
                    eliminar(nodoActual.getHijoDerecho(), datoAEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
            return balancear(nodoActual);
        }

        //caso 1
        if (nodoActual.esHoja()) {
            return NodoBinario.nodoVacio();
        }

        //caso 2
        if (!nodoActual.esVacioHijoIzquierdo() &&
                nodoActual.esVacioHijoDerecho()) {
            return balancear(nodoActual.getHijoIzquierdo());
        }
        if (nodoActual.esVacioHijoIzquierdo() &&
                !nodoActual.esVacioHijoDerecho()) {
            return balancear(nodoActual.getHijoDerecho());
        }

        //caso 3
        T datoReemplazo = obtenerDatoSucesorInOrden(nodoActual.getHijoDerecho());
        NodoBinario<T> supuestoNuevoHijoDerecho =
                eliminar(nodoActual.getHijoDerecho(), datoReemplazo);
        nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
        nodoActual.setDato(datoReemplazo);
        return balancear(nodoActual);
    }
    private T obtenerDatoSucesorInOrden(NodoBinario<T> nodoActual) {
        while (!nodoActual.esVacioHijoIzquierdo()) {
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoActual.getDato();
    }

    @Override
    public void insertar(T datoAInsertar) throws ExcepcionDatoYaExiste {
        super.raiz = insertar(super.raiz, datoAInsertar);
    }

    private NodoBinario<T> insertar(NodoBinario<T> nodoActual, T datoAInsertar)
        throws ExcepcionDatoYaExiste {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return new NodoBinario<>(datoAInsertar);
        }

        T datoDelNodoActual = nodoActual.getDato();
        if (datoAInsertar.compareTo(datoDelNodoActual) < 0) {
            NodoBinario<T> supuestoNuevoHijoIzq =
                    insertar(nodoActual.getHijoIzquierdo(), datoAInsertar);
            nodoActual.setHijoIzquierdo(supuestoNuevoHijoIzq);
            return balancear(nodoActual);
        }

        if (datoAInsertar.compareTo(datoDelNodoActual) > 0) {
            NodoBinario<T> supuestoNuevoHijoDer =
                    insertar(nodoActual.getHijoDerecho(), datoAInsertar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDer);
            return balancear(nodoActual);
        }

        throw new ExcepcionDatoYaExiste();
    }

    private NodoBinario<T> balancear(NodoBinario<T> nodoActual) {
        int diferencia = diferenciaDeAlturaHijos(nodoActual);
        if (diferencia > AVL.LIMITE_MAXIMO) {
            if (diferenciaDeAlturaHijos(nodoActual.getHijoIzquierdo()) < 0) {
                return rotacionDobleADerecha(nodoActual);
            }
            return rotarDerecha(nodoActual);
        } else if (diferencia < -AVL.LIMITE_MAXIMO) {
            if (diferenciaDeAlturaHijos(nodoActual.getHijoDerecho()) > 0) {
                return rotacionDobleAIzquierda(nodoActual);
            }
            return rotarIzquierda(nodoActual);
        }
        return nodoActual;
    }

    private NodoBinario<T> rotacionDobleAIzquierda(NodoBinario<T> nodoActual) {
        nodoActual.setHijoDerecho(rotarDerecha(nodoActual.getHijoDerecho()));
        return rotarIzquierda(nodoActual);
    }

    private NodoBinario<T> rotacionDobleADerecha(NodoBinario<T> nodoActual) {
        nodoActual.setHijoIzquierdo(rotarIzquierda(nodoActual.getHijoIzquierdo()));
        return rotarDerecha(nodoActual);
    }


    private NodoBinario<T> rotarDerecha(NodoBinario<T> nodoActual) {
        NodoBinario<T> nuevoRaiz = nodoActual.getHijoIzquierdo();
        nodoActual.setHijoIzquierdo(nuevoRaiz.getHijoDerecho());
        nuevoRaiz.setHijoDerecho(nodoActual);
        return nuevoRaiz;
    }

    private NodoBinario<T> rotarIzquierda(NodoBinario<T> nodoActual) {

        NodoBinario<T> nuevoRaiz = nodoActual.getHijoDerecho();
        nodoActual.setHijoDerecho(nuevoRaiz.getHijoIzquierdo());
        nuevoRaiz.setHijoIzquierdo(nodoActual);
        return nuevoRaiz;
    }

    private int diferenciaDeAlturaHijos(NodoBinario<T> nodoActual) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return 0;
        }
        return altura(nodoActual.getHijoIzquierdo())
                - altura(nodoActual.getHijoDerecho());
    }

    //hola
}
