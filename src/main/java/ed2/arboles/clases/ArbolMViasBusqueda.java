package ed2.arboles.clases;

import ed2.arboles.excepciones.ExcepcionDatoNoExiste;
import ed2.arboles.excepciones.ExcepcionDatoYaExiste;
import ed2.arboles.excepciones.ExcepcionOrdenInvalido;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArbolMViasBusqueda<T extends Comparable<T>>
        implements IArbolBusqueda<T> {


    protected NodoMVias<T> raiz;
    protected int orden;
    protected static final int POSICION_INVALIDA = -1;
    protected static final int ORDEN_MINIMO = 3;


    public ArbolMViasBusqueda() {
        this.orden = ArbolMViasBusqueda.ORDEN_MINIMO;
    }

    public ArbolMViasBusqueda(int orden) throws ExcepcionOrdenInvalido {
        if (orden < ArbolMViasBusqueda.ORDEN_MINIMO) {
            throw new ExcepcionOrdenInvalido();
        }
        this.orden = orden;
    }

    @Override
    public void insertar(T datoAInsertar) throws ExcepcionDatoYaExiste {
        if (datoAInsertar == null) {
            throw new IllegalArgumentException("No se aceptan datos vacio");
        }
        if (esArbolVacio()) {
            this.raiz = new NodoMVias<>(this.orden, datoAInsertar);
            return;
        }
        NodoMVias<T> nodoAux = this.raiz;
        do {
            int posicionDeDatoAInsertar = buscarPosicionDeDatoEnNodo(
                    nodoAux,
                    datoAInsertar);
            if ((posicionDeDatoAInsertar != POSICION_INVALIDA)) {
                throw new ExcepcionDatoYaExiste();
            }
            if (nodoAux.esHoja()) {
                if (nodoAux.estanDatosLlenos()) {
                    int posicionPorDondeBajar = obtenerPosicionPorDondeBajar(
                            nodoAux,
                            datoAInsertar);
                    NodoMVias<T> nuevoNodo = new NodoMVias<>(orden, datoAInsertar);
                    nodoAux.setHijo(posicionPorDondeBajar, nuevoNodo);
                } else {
                    insertarDatoEnNodoOrdenado(nodoAux, datoAInsertar);
                }
                nodoAux = NodoMVias.nodoVacio();
            }
        } while (!NodoMVias.esNodoVacio(nodoAux));
    }

    protected void insertarDatoEnNodoOrdenado(NodoMVias<T> nodoAux, T datoAInsertar) {
        int posicionDeDatoAInsertar = obtenerPosicionPorDondeBajar(
                nodoAux, datoAInsertar);
        for (int i = nodoAux.nroDeDatosNoVacios(); i > posicionDeDatoAInsertar; i--) {
            T datoDelNodo = nodoAux.getDato(i - 1);
            nodoAux.setDato(i, datoDelNodo);
        }
        nodoAux.setDato(posicionDeDatoAInsertar, datoAInsertar);
    }

    protected int obtenerPosicionPorDondeBajar(NodoMVias<T> nodoAux, T datoAInsertar) {
        for (int i = 0; i < nodoAux.nroDeDatosNoVacios(); i++) {
            T datoDelNodo = nodoAux.getDato(i);
            if (datoAInsertar.compareTo(datoDelNodo) < 0) {
                return i;
            }
        }
        return nodoAux.nroDeDatosNoVacios();
    }

    protected int buscarPosicionDeDatoEnNodo(NodoMVias<T> nodoAux, T datoAInsertar) {
        for (int i = 0; i < nodoAux.nroDeDatosNoVacios(); i++) {
            T datoDelNodo = nodoAux.getDato(i);
            if (datoAInsertar.compareTo(datoDelNodo) == 0) {
                return i;
            }
        }
        return POSICION_INVALIDA;
    } //deberia devolver la posicion del dato o el hijo?

    @Override
    public void eliminar(T datoAEliminar) throws ExcepcionDatoNoExiste {
        if (datoAEliminar == null) {
            throw new IllegalArgumentException("No se permiten datos vacios");
        }
        this.raiz = eliminar(this.raiz, datoAEliminar);
    }

    private NodoMVias<T> eliminar(NodoMVias<T> nodoActual, T datoAEliminar)
            throws ExcepcionDatoNoExiste {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            throw new ExcepcionDatoNoExiste();
        }
        for (int i = 0; i < nodoActual.nroDeDatosNoVacios(); i++) {
            T datoActual = nodoActual.getDato(i);
            if (datoAEliminar.compareTo(datoActual) == 0) {
                if (nodoActual.esHoja()) {
                    eliminarDatoDePosicion(nodoActual, i);
                    if (nodoActual.nroDeDatosNoVacios() == 0) {
                        return NodoMVias.nodoVacio();
                    }
                    return nodoActual;
                }
                //cuando nodoActual no es hoja
                T datoDeReemplazo;
                if (existenHijosDespuesDePosicion(nodoActual, i)) {
                    //caso 2
                    datoDeReemplazo = obtenerSucesorInOrden(nodoActual, datoActual);
                } else {
                    //caso 3
                    datoDeReemplazo = obtenerPredecesorInOrden(nodoActual, datoActual);
                }
                nodoActual = eliminar(nodoActual, datoDeReemplazo);
                nodoActual.setDato(i, datoAEliminar);
                return nodoActual;
            }
            if (datoAEliminar.compareTo(datoActual) < 0) {
                NodoMVias<T> supuestoNuevohijo = eliminar(nodoActual.getHijo(i),
                        datoAEliminar);
                nodoActual.setHijo(i, supuestoNuevohijo);
                return nodoActual;
            }
        } //fin de for

        NodoMVias<T> supuestoNuevoHijo = eliminar(nodoActual.getHijo(
                nodoActual.nroDeDatosNoVacios()), datoAEliminar);
        nodoActual.setHijo(nodoActual.nroDeDatosNoVacios(), supuestoNuevoHijo);
        return nodoActual;
    }

    private T obtenerPredecesorInOrden(NodoMVias<T> nodoActual, T datoActual) {
        int i = 0;
        while ( i < nodoActual.nroDeDatosNoVacios() &&
                !nodoActual.getDato(i).equals(datoActual)) {
            i++;
        }

        NodoMVias<T> nodoAux = nodoActual.getHijo(i);
        while (!NodoMVias.esNodoVacio(nodoAux)) {
            nodoActual = nodoAux;
            nodoAux = nodoAux.getHijo(nodoAux.nroDeDatosNoVacios());
        }
        return nodoActual.getDato(nodoActual.nroDeDatosNoVacios() - 1);
    }

    private T obtenerSucesorInOrden(NodoMVias<T> nodoActual, T datoActual) {
        int i = 0;
        while ( i < nodoActual.nroDeDatosNoVacios() &&
                !nodoActual.getDato(i).equals(datoActual)) {
            i++;
        }

        NodoMVias<T> nodoAux = nodoActual.getHijo(i + 1);;
        if (NodoMVias.esNodoVacio(nodoAux)) {
            return nodoActual.getDato(i + 1);
        }
        while (!NodoMVias.esNodoVacio(nodoAux)) {
            nodoActual = nodoAux;
            nodoAux = nodoAux.getHijo(0);
        }
        return nodoActual.getDato(0);
    }

    private boolean existenHijosDespuesDePosicion(NodoMVias<T> nodoActual, int i) {
        while (i <= nodoActual.nroDeDatosNoVacios()) {
            NodoMVias<T> nodoHijo = nodoActual.getHijo(i);
            if (!NodoMVias.esNodoVacio(nodoHijo)) {
                return true;
            }
            i++;
        }
        return false;
    }

    protected void eliminarDatoDePosicion(NodoMVias<T> nodoActual, int i) {
        for (int j = i; j < nodoActual.nroDeDatosNoVacios() - 1; j++) {
            T datoAMover = nodoActual.getDato(j + 1);
            nodoActual.setDato(j, datoAMover);
        }
        T datoVacio = (T)NodoMVias.datoVacio();
        nodoActual.setDato(nodoActual.nroDeDatosNoVacios(), datoVacio);
    }

    @Override
    public T buscar(T datoABuscar) {
        if (!this.esArbolVacio()) {
            NodoMVias<T> nodoAux = this.raiz;
            do {
                boolean cambioDeNodoAux = false;
                for (int i = 0; i < nodoAux.nroDeDatosNoVacios() &&
                        !cambioDeNodoAux; i++) {
                    T datoNodoAux = nodoAux.getDato(i);
                    if (datoABuscar.compareTo(datoNodoAux) == 0) {
                        return datoABuscar;
                    }
                    if (datoABuscar.compareTo(datoNodoAux) < 0) {
                        nodoAux = nodoAux.getHijo(i);
                        cambioDeNodoAux = true;
                    }
                }
                if (!cambioDeNodoAux) {
                    nodoAux = nodoAux.getHijo(nodoAux.nroDeDatosNoVacios());
                }
            } while (!NodoMVias.esNodoVacio(nodoAux));
        }
        return null;
    }

    @Override
    public boolean contiene(T dato) {
        return false;
    }

    @Override
    public int size() {
        return size(this.raiz);
    }
    private int size(NodoMVias<T> nodoAux) {
        if (NodoMVias.esNodoVacio(nodoAux)) {
            return 0;
        }
        int sizeTotal = 0;
        for (int i = 0; i <= nodoAux.nroDeDatosNoVacios(); i++) {
            sizeTotal = sizeTotal + size(nodoAux.getHijo(i));
        }
        return sizeTotal + 1;
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }
    private int altura(NodoMVias<T> nodoAux) {
        if (NodoMVias.esNodoVacio(nodoAux)) {
            return 0;
        }
        int alturaMayor = 0;
        for (int i = 0; i <= nodoAux.nroDeDatosNoVacios(); i++) {
            int alturaDelHijoActual = altura(nodoAux.getHijo(i));
            if (alturaDelHijoActual > alturaMayor) {
                alturaMayor = alturaDelHijoActual;
            }
        }
        return alturaMayor + 1;
    }


    @Override
    public void vaciar() {
        this.raiz = NodoMVias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
        return NodoMVias.esNodoVacio(raiz);
    }

    @Override/// //
    public int nivel() {
        return 0;
    }

    @Override/////
    public List<T> recorridoEnInOrden() {
        return List.of();
    }

    @Override
    public List<T> recorridoEnPreOrden() {
        List<T> recorrido = new ArrayList<>();
        recorridoEnPreOrden(this.raiz, recorrido);
        return recorrido;
    }
    private void recorridoEnPreOrden(NodoMVias<T> nodoAux, List<T> recorrido) {
        if (NodoMVias.esNodoVacio(nodoAux)) {
            return;
        }
        for (int i = 0; i < nodoAux.nroDeDatosNoVacios(); i++) {
            recorrido.add(nodoAux.getDato(i));
            recorridoEnPreOrden(nodoAux.getHijo(i), recorrido);
        }
        recorridoEnPreOrden(nodoAux.getHijo(nodoAux.nroDeDatosNoVacios()), recorrido);
    }

    @Override
    public List<T> recorridoEnPostOrden() {
        List<T> recorrido = new ArrayList<>();
        recorridoEnPostOrden(this.raiz, recorrido);
        return recorrido;
    }
    private void recorridoEnPostOrden(NodoMVias<T> nodoAux, List<T> recorrido) {
        if (NodoMVias.esNodoVacio(nodoAux)) {
            return;
        }
        recorridoEnPostOrden(nodoAux.getHijo(0), recorrido);
        for (int i = 0; i < nodoAux.nroDeDatosNoVacios(); i++) {
            recorridoEnPostOrden(nodoAux.getHijo(i + 1), recorrido);
            recorrido.add(nodoAux.getDato(i));
        }
    }

    @Override
    public List<T> recorridoPorNiveles() {
        List<T> recorrido = new LinkedList<>();
        if (!this.esArbolVacio()) {
            Queue<NodoMVias<T>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            do {
                NodoMVias<T> nodoAux = colaDeNodos.poll();
                for (int i = 0; i < nodoAux.nroDeDatosNoVacios(); i++) {
                    recorrido.add(nodoAux.getDato(i));
                    if (!nodoAux.esHijoVacio(i)) {
                        colaDeNodos.offer(nodoAux.getHijo(i));
                    }
                }
                if (!nodoAux.esHijoVacio(nodoAux.nroDeDatosNoVacios())) {
                    colaDeNodos.offer(nodoAux.getHijo(nodoAux.nroDeDatosNoVacios()));
                }
            } while (!colaDeNodos.isEmpty());
        }
        return recorrido;
    }

    @Override
    public String toStringVertical() {
        return "";
    }

    public boolean nroDeHijosParOHojas() {
        return nroDeHijosParOHojas(this.raiz);
    }
    private boolean nroDeHijosParOHojas(NodoMVias<T> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return false;
        }
        if (nodoActual.esHoja()) {
            return true;
        }

        if (nroHijosNoVacios(nodoActual) % 2 != 0) {
            return false;
        }
        boolean existeSoloHojasOParesNoVacios = true;
        for (int i = 0; i < orden && existeSoloHojasOParesNoVacios; i++) {
            if (!nodoActual.esHijoVacio(i)) {
                existeSoloHojasOParesNoVacios = nroDeHijosParOHojas(nodoActual.getHijo(i));
            }
        }
        return existeSoloHojasOParesNoVacios;
    }
    private int nroHijosNoVacios(NodoMVias<T> nodoActual) {
        int nroHijosNoVacios = 0;
        for (int i = 0; i < orden; i++) {
            if (!nodoActual.esHijoVacio(i)) {
                nroHijosNoVacios++;
            }
        }
        return nroHijosNoVacios;
    }
}
