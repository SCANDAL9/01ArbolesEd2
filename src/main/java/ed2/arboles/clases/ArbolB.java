package ed2.arboles.clases;

import ed2.arboles.excepciones.ExcepcionDatoNoExiste;
import ed2.arboles.excepciones.ExcepcionDatoYaExiste;
import ed2.arboles.excepciones.ExcepcionOrdenInvalido;

import java.util.Stack;

public class ArbolB<T extends Comparable<T>>
    extends ArbolMViasBusqueda<T>{

    public ArbolB() {
        super();
    }
    public ArbolB(int orden) throws ExcepcionOrdenInvalido {
        super(orden);
    }

    public int getNroMaximoDeHijos() {
        return orden;
    }
    public int getNroMaximoDeDatos() {
        return orden - 1;
    }
    public int getNroMinimoDeDatos() {
        return getNroMaximoDeDatos() / 2;
    }
    public int getNroMinimoDeHijos() {
        return getNroMinimoDeDatos() + 1;
    }

    @Override
    public void insertar(T datoAInsertar) throws ExcepcionDatoYaExiste {
        if (datoAInsertar == null) {
            throw new IllegalArgumentException("No se permiten datos vacios");
        }
        if (esArbolVacio()) {
            this.raiz = new NodoMVias<>(this.orden, datoAInsertar);
            return;
        }
        Stack<NodoMVias<T>> pilaDeAncestros = new Stack<>();
        NodoMVias<T> nodoAux = this.raiz;
        do {
            int posicionDeDatoAInsertar = super.buscarPosicionDeDatoEnNodo(
                    nodoAux, datoAInsertar);
            if (posicionDeDatoAInsertar != POSICION_INVALIDA) {
                throw new ExcepcionDatoYaExiste();
            }
            if (nodoAux.esHoja()) { //si es hoja
                if (nodoAux.estanDatosLlenos()) {
                    this.dividirYEmpujar(nodoAux, pilaDeAncestros, datoAInsertar,
                            NodoMVias.nodoVacio());
                } else {
                    super.insertarDatoEnNodoOrdenado(nodoAux, datoAInsertar);
                }
                nodoAux = NodoMVias.nodoVacio();
            } else { //no es hoja
                int posicionPorDondeBajar = super.obtenerPosicionPorDondeBajar(
                        nodoAux, datoAInsertar);
                pilaDeAncestros.push(nodoAux);
                nodoAux = nodoAux.getHijo(posicionPorDondeBajar);
            }
        } while (!NodoMVias.esNodoVacio(nodoAux));
    }

    private void dividirYEmpujar(NodoMVias<T> nodoAux, Stack<NodoMVias<T>> pilaDeAncestros, T datoAInsertar,
                                 NodoMVias<T> nodoMVias) {

    }

    @Override
    public void eliminar(T datoAEliminar) throws ExcepcionDatoNoExiste {
        if (datoAEliminar == null) {
            throw new IllegalArgumentException("No se permiten datos vacios");
        }

        Stack<NodoMVias<T>> pilaDeAncestros = new Stack<>();
        NodoMVias<T> nodoAux = this.raiz;
        NodoMVias<T> nodoDelDatoAEliminar = NodoMVias.nodoVacio();
        int posicionDeDatoAEliminar = super.POSICION_INVALIDA;

        while (!NodoMVias.esNodoVacio(nodoAux)) {
            posicionDeDatoAEliminar = buscarPosicionDeDatoEnNodo(nodoAux, datoAEliminar);
            if (posicionDeDatoAEliminar != super.POSICION_INVALIDA) {
                nodoDelDatoAEliminar = nodoAux;
                nodoAux = NodoMVias.nodoVacio();

            } else {
                int posicionPorDondeBajar = super.obtenerPosicionPorDondeBajar(
                        nodoAux, datoAEliminar);
                pilaDeAncestros.push(nodoAux);
                nodoAux = nodoAux.getHijo(posicionPorDondeBajar);
            }
        } //fin del while

        if (NodoMVias.esNodoVacio(nodoDelDatoAEliminar)) {
            throw new ExcepcionDatoNoExiste();
        }

        if (nodoDelDatoAEliminar.esHoja()) {
            super.eliminarDatoDePosicion(nodoDelDatoAEliminar, posicionDeDatoAEliminar);
            if (nodoDelDatoAEliminar.nroDeDatosNoVacios() < this.getNroMinimoDeDatos()) {
                prestarseOFusionar(nodoDelDatoAEliminar, pilaDeAncestros);
            }
        } else {
            pilaDeAncestros.push(nodoDelDatoAEliminar);
            NodoMVias<T> nodoDelPredecesor = obtenerNodoDelPredecesor(
                    nodoDelDatoAEliminar.getHijo(posicionDeDatoAEliminar), pilaDeAncestros);
            T datoPredecesor = nodoDelPredecesor.getDato(
                    nodoDelPredecesor.nroDeDatosNoVacios() - 1);
            nodoDelPredecesor.setDato(nodoDelPredecesor.nroDeDatosNoVacios() - 1,
                    (T)NodoMVias.nodoVacio());
            nodoDelDatoAEliminar.setDato(posicionDeDatoAEliminar, datoPredecesor);

            if (nodoDelPredecesor.nroDeDatosNoVacios() < this.getNroMinimoDeDatos()) {
                prestarseOFusionar(nodoDelPredecesor, pilaDeAncestros);
            }
        }
    }

    private NodoMVias<T> obtenerNodoDelPredecesor(NodoMVias<T> hijo, Stack<NodoMVias<T>> pilaDeAncestros) {
        /*NodoMVias<T> nodoDelPredecesor = hijo;
        while (!NodoMVias.esNodoVacio(nodoDelPredecesor.getHijo(
                nodoDelPredecesor.nroDeDatosNoVacios()))) {
            pilaDeAncestros.push(nodoDelPredecesor);
            nodoDelPredecesor = nodoDelPredecesor.getHijo(
                    nodoDelPredecesor.nroDeDatosNoVacios());
        }
        return nodoDelPredecesor;*/
        return NodoMVias.nodoVacio();
    }

    private void prestarseOFusionar(NodoMVias<T> nodoDelDatoAEliminar, Stack<NodoMVias<T>> pilaDeAncestros) {
    }


    public int cantNodosAntesDeNivel(int nivel) {
        return cantNodosAntesDeNivel(this.raiz, nivel, 0);
    }
    private int cantNodosAntesDeNivel(NodoMVias<T> nodoActual, int nivelObjetivo, int nivelActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }
        if (nivelActual >= nivelObjetivo) {
            return 0;
        }

        if (nodoActual.esHoja()) {
            return 1;
        }
        int nroDeHojasAntesDelNivel = 0;
        for (int i = 0; i < nodoActual.nroDeDatosNoVacios(); i++) {
            nroDeHojasAntesDelNivel = nroDeHojasAntesDelNivel +
                    this.cantNodosAntesDeNivel(nodoActual.getHijo(i),
                            nivelObjetivo, nivelActual + 1);
        }
        return nroDeHojasAntesDelNivel;
    }

}
