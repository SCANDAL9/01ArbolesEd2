/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ed2.arboles.clases;

import ed2.arboles.excepciones.ExcepcionDatoNoExiste;
import ed2.arboles.excepciones.ExcepcionDatoYaExiste;
import ed2.arboles.excepciones.ExcepcionRecorridosInvalidos;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Flia Maldonado
 * @param <T>
 */
public class ArbolBinarioBusqueda<T extends Comparable<T>> 
implements IArbolBusqueda<T> {
    
    protected NodoBinario<T> raiz;
    public ArbolBinarioBusqueda() {
    }
            
    public ArbolBinarioBusqueda(List<T> recInOrden,
                                List<T> recNoInOrden,
                                boolean conPreOrden) throws ExcepcionRecorridosInvalidos {
        //validaciones
        if (conPreOrden) {
            this.raiz = reconstruirConPreOrden(recInOrden, recNoInOrden);
        } else {
            this.raiz = reconstruirConPostOrden(recInOrden, recNoInOrden);
        }
    }

    private NodoBinario<T> reconstruirConPostOrden(List<T> recInOrden, List<T> recPostOrden) {
        if (recInOrden.isEmpty()) {
            return null;
        }
        T lastPostOrden = recPostOrden.getLast();
        int index = recInOrden.indexOf(lastPostOrden);

        NodoBinario<T> nodoActual = new NodoBinario<>();
        nodoActual.setDato(recPostOrden.getLast());

        recPostOrden.removeLast();
        recInOrden.remove(index);

        List<T> izqInOrden = new LinkedList<>(recInOrden.subList(0, index));
        List<T> derInOrden = new LinkedList<>(recInOrden.subList(index, recInOrden.size()));
        List<T> izqPostOrden = new LinkedList<>(recPostOrden.subList(0, izqInOrden.size()));
        List<T> derPostOrden = new LinkedList<>(recPostOrden.subList(izqInOrden.size(), recPostOrden.size()));

        NodoBinario<T> nodoIzq = reconstruirConPostOrden(izqInOrden, izqPostOrden);
        NodoBinario<T> nodoDer = reconstruirConPostOrden(derInOrden, derPostOrden);

        nodoActual.setHijoIzquierdo(nodoIzq);
        nodoActual.setHijoDerecho(nodoDer);

        return nodoActual;
    }

    //revisar

    private NodoBinario<T> reconstruirConPreOrden(List<T> recInOrden, List<T> recPreOrden) {
        if (recInOrden.isEmpty()) {
            return null;
        }
        T firstPreOrden = recPreOrden.get(0);
        int index = recInOrden.indexOf(firstPreOrden);

        NodoBinario<T> nodoActual = new NodoBinario<>();
        nodoActual.setDato(recPreOrden.get(0));

        recPreOrden.remove(0);
        recInOrden.remove(index);

        List<T> izqInOrden = new LinkedList<>(recInOrden.subList(0, index));
        List<T> derInOrden = new LinkedList<>(recInOrden.subList(index, recInOrden.size()));
        List<T> izqPreOrden = new LinkedList<>(recPreOrden.subList(0, izqInOrden.size()));
        List<T> derPreOrden = new LinkedList<>(recPreOrden.subList(izqInOrden.size(), recPreOrden.size()));

        NodoBinario<T> nodoIzq = reconstruirConPostOrden(izqInOrden, izqPreOrden);
        NodoBinario<T> nodoDer = reconstruirConPostOrden(derInOrden, derPreOrden);

        nodoActual.setHijoIzquierdo(nodoIzq);
        nodoActual.setHijoDerecho(nodoDer);

        return nodoActual;

    }

    @Override
    public void vaciar() {
        this.raiz = NodoBinario.nodoVacio();
    }

    @Override
    public void insertar(T datoAInsertar) throws ExcepcionDatoYaExiste {
        if (this.esArbolVacio()) {
            this.raiz = new NodoBinario(datoAInsertar);
            return;
        }
        NodoBinario<T> nodoAnterior = NodoBinario.nodoVacio();
        NodoBinario<T> nodoAux = this.raiz;
        do {
            T datoNodoAux = nodoAux.getDato();
            nodoAnterior = nodoAux;
            if (datoAInsertar.compareTo(datoNodoAux) < 0) {
                nodoAux = nodoAux.getHijoIzquierdo();
            } else if (datoAInsertar.compareTo(datoNodoAux) > 0) {
                nodoAux = nodoAux.getHijoDerecho();
            } else {
                throw new ExcepcionDatoYaExiste();
            }
        } while (!NodoBinario.esNodoVacio(nodoAux));
        
        NodoBinario<T> nuevoNodo = new NodoBinario<>(datoAInsertar);
        if (datoAInsertar.compareTo(nodoAnterior.getDato()) < 0) {
            nodoAnterior.setHijoIzquierdo(nuevoNodo);
        } else {
            nodoAnterior.setHijoDerecho(nuevoNodo);
        }
    } //fin metodo

    @Override
    public void eliminar(T datoAEliminar) throws ExcepcionDatoNoExiste {
        this.raiz = eliminar(this.raiz, datoAEliminar);
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
            return nodoActual;
        }

        if (datoAEliminar.compareTo(datoDelNodoActual) > 0) {
            NodoBinario<T> supuestoNuevoHijoDerecho =
                    eliminar(nodoActual.getHijoDerecho(), datoAEliminar);
            nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
            return nodoActual;
        }

        //caso 1
        if (nodoActual.esHoja()) {
            return NodoBinario.nodoVacio();
        }

        //caso 2
        if (!nodoActual.esVacioHijoIzquierdo() &&
                nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoIzquierdo();
        }
        if (nodoActual.esVacioHijoIzquierdo() &&
                !nodoActual.esVacioHijoDerecho()) {
            return nodoActual.getHijoDerecho();
        }

        //caso 3
        T datoReemplazo = obtenerDatoSucesorInOrden(nodoActual.getHijoDerecho());
        NodoBinario<T> supuestoNuevoHijoDerecho =
                eliminar(nodoActual.getHijoDerecho(), datoReemplazo);
        nodoActual.setHijoDerecho(supuestoNuevoHijoDerecho);
        nodoActual.setDato(datoReemplazo);
        return nodoActual;
    }

    private T obtenerDatoSucesorInOrden(NodoBinario<T> nodoActual) {
        while (!nodoActual.esVacioHijoIzquierdo()) {
            nodoActual = nodoActual.getHijoIzquierdo();
        }
        return nodoActual.getDato();
    }

    @Override
    public T buscar(T datoABuscar) {
        if (!this.esArbolVacio()) {
            NodoBinario<T> nodoAux = this.raiz;
            do {
                T datoNodoAux = nodoAux.getDato();
                if (datoABuscar.compareTo(datoNodoAux) < 0) {
                    nodoAux = nodoAux.getHijoIzquierdo();
                } else if (datoABuscar.compareTo(datoNodoAux) > 0) {
                    nodoAux = nodoAux.getHijoDerecho();
                } else {
                    return datoABuscar;
                }
            } while (!NodoBinario.esNodoVacio(nodoAux));
        }
        return null;
    }

    @Override
    public boolean contiene(T dato) {
        return this.buscar(dato) != null;
    }

    @Override
    public int size() {
        return size(this.raiz);
    }

    private int size(NodoBinario<T> nodoAux) {
        if (NodoBinario.esNodoVacio(nodoAux)) {
            return 0;
        }
        int sizeXIzq = size(nodoAux.getHijoIzquierdo());
        int sizeXDer = size(nodoAux.getHijoDerecho());
        return sizeXIzq + sizeXDer + 1;
    }

    //Iterativo
    public int sizeIterativo() {
        int cantNodos = 0;
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<T>> pilaDeNodos = new Stack<>();
            pilaDeNodos.push(this.raiz);
            do {
                NodoBinario<T> nodoAux = pilaDeNodos.pop();
                cantNodos++;
                if (!nodoAux.esVacioHijoDerecho()) {
                    pilaDeNodos.push(nodoAux.getHijoDerecho());
                }
                if(!nodoAux.esVacioHijoIzquierdo()) {
                    pilaDeNodos.push(nodoAux.getHijoIzquierdo());
                }
            } while(!pilaDeNodos.isEmpty());
        }
        return cantNodos;
    }

    @Override
    public int altura() {
        return this.altura(this.raiz);
    }

    protected int altura(NodoBinario<T> nodoAux) {
        if (NodoBinario.esNodoVacio(nodoAux)) {
            return 0;
        }
        int alturaXIzq = this.altura(nodoAux.getHijoIzquierdo());
        int alturaXDer = this.altura(nodoAux.getHijoDerecho());
        return alturaXIzq > alturaXDer ? alturaXIzq + 1 : alturaXDer + 1;
    }

    public int alturaIt() {
        int alturaDelArbol = 0;
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<T>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            do {
                int cantNodosEnLaCola = colaDeNodos.size();
                for(int i = 0; i < cantNodosEnLaCola; i++) {
                    NodoBinario<T> nodoAux = colaDeNodos.poll();
                    if (!nodoAux.esVacioHijoIzquierdo()) {
                        colaDeNodos.offer(nodoAux.getHijoIzquierdo());
                    }
                    if (!nodoAux.esVacioHijoDerecho()) {
                        colaDeNodos.offer(nodoAux.getHijoDerecho());
                    }
                }
                alturaDelArbol++;
            } while(!colaDeNodos.isEmpty());
        }
        return alturaDelArbol;
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public int nivel() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<T> recorridoEnInOrden() {
        List<T> recorrido = new LinkedList<>();
        recorridoEnInOrden(this.raiz, recorrido);
        return recorrido;
    }
    private void recorridoEnInOrden(NodoBinario<T> nodoAux, List<T> recorrido) {
        if (NodoBinario.esNodoVacio(nodoAux)) {
            return;
        }
        recorridoEnInOrden(nodoAux.getHijoIzquierdo(), recorrido);
        recorrido.add(nodoAux.getDato());
        recorridoEnInOrden(nodoAux.getHijoDerecho(), recorrido);
    }

    public List<T> recorridoEnInOrdenIt() {
        List<T> recorrido = new LinkedList<>();
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<T>> pilaDeNodos = new Stack();
            NodoBinario<T> nodoAux = this.raiz;
            insertarEnPilaParaInOrden(nodoAux, pilaDeNodos);
            while (!pilaDeNodos.isEmpty()) {
                nodoAux = pilaDeNodos.pop();
                recorrido.add(nodoAux.getDato());
                nodoAux = nodoAux.getHijoDerecho();
                insertarEnPilaParaInOrden(nodoAux, pilaDeNodos);
            }
        }
        return recorrido;
    }
    private void insertarEnPilaParaInOrden(NodoBinario<T> nodoAux,
            Stack<NodoBinario<T>> pilaDeNodos) {
        while (!NodoBinario.esNodoVacio(nodoAux)) {
            pilaDeNodos.push(nodoAux);
            nodoAux = nodoAux.getHijoIzquierdo();
        }
    }

    @Override

    public List<T> recorridoEnPreOrden() {
        List<T> recorrido = new LinkedList<>();
        recorridoEnPreOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPreOrden(NodoBinario<T> nodoAux, List<T> recorrido) {
        if (NodoBinario.esNodoVacio(nodoAux)) {
            return;
        }
        recorrido.add(nodoAux.getDato());
        recorridoEnPreOrden(nodoAux.getHijoIzquierdo(), recorrido);
        recorridoEnPreOrden(nodoAux.getHijoDerecho(), recorrido);
    }

    public List<T> recorridoEnPreOrdenIt() {
        List<T> recorrido = new LinkedList<>();
        if(!this.esArbolVacio()) {
            Stack<NodoBinario<T>> pilaDeNodos = new Stack<>();
            pilaDeNodos.push(this.raiz);
            do {
                NodoBinario<T> nodoAux = pilaDeNodos.pop();
                recorrido.add(nodoAux.getDato());
                if (!nodoAux.esVacioHijoDerecho()) {
                    pilaDeNodos.push(nodoAux.getHijoDerecho());
                }                
                if (!nodoAux.esVacioHijoIzquierdo()) {
                    pilaDeNodos.push(nodoAux.getHijoIzquierdo());
                }
            } while(!pilaDeNodos.isEmpty());
        }
        return recorrido;
    }

    @Override
    public List<T> recorridoEnPostOrden() {
        List<T> recorrido = new LinkedList<>();
        recorridoEnPostOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPostOrden(NodoBinario<T> nodoAux, List<T> recorrido) {
        if (NodoBinario.esNodoVacio(nodoAux)) {
            return;
        }
        recorridoEnPostOrden(nodoAux.getHijoIzquierdo(), recorrido);
        recorridoEnPostOrden(nodoAux.getHijoDerecho(), recorrido);
        recorrido.add(nodoAux.getDato());
    }

    //revisar
    public List<T> recorridoEnPostOrdenIt() {
        List<T> recorrido = new LinkedList<>();
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<T>> pilaDeNodos = new Stack<>();
            NodoBinario<T> nodoAux = this.raiz;
            insertarEnPilaParaPostOrden(nodoAux, pilaDeNodos);
            do {
                nodoAux = pilaDeNodos.pop();
                recorrido.add(nodoAux.getDato());
                if (!nodoAux.esVacioHijoIzquierdo()) {
                    insertarEnPilaParaPostOrden(nodoAux.getHijoIzquierdo(), pilaDeNodos);
                }
            } while(!pilaDeNodos.isEmpty());
        }
        return recorrido;

        /*        List<T> recorrido = new LinkedList<>();
        if(!this.esArbolVacio()) {
            Stack<NodoBinario<T>> pilaDeNodos = new Stack();
            NodoBinario<T> nodoAux = this.raiz;

            insertarEnPilaParaPostOrden(nodoAux.getHijoDerecho(), pilaDeNodos);
            insertarEnPilaParaPostOrden(nodoAux.getHijoIzquierdo(), pilaDeNodos);
            while (!pilaDeNodos.isEmpty()) {
                nodoAux = pilaDeNodos.pop();
                recorrido.add(nodoAux.getDato());
            }
        }
        return recorrido;*/
    }

    private void insertarEnPilaParaPostOrden(NodoBinario<T> nodoAux,
                                             Stack<NodoBinario<T>> pilaDeNodos) {
        while (!NodoBinario.esNodoVacio(nodoAux)) {
            pilaDeNodos.push(nodoAux);
            nodoAux = nodoAux.getHijoDerecho();
        }

        /*        while (!NodoBinario.esNodoVacio(nodoAux)) {
            pilaDeNodos.push(nodoAux);
            nodoAux = nodoAux.getHijoDerecho();
        }
        */
    }

    @Override
    public List<T> recorridoPorNiveles() {
        List<T> recorrido = new LinkedList<>();
        if(!this.esArbolVacio()) {
            Queue<NodoBinario<T>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            do {
                NodoBinario<T> nodoAux = colaDeNodos.poll();
                recorrido.add(nodoAux.getDato());
                if (!nodoAux.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoAux.getHijoIzquierdo());
                }
                if (!nodoAux.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoAux.getHijoDerecho());
                }
            } while(!colaDeNodos.isEmpty());
        }
        return recorrido;
    }

    public int cantidadNodosConUnHijoNoVacio() {
        return cantidadNodosConUnHijoNoVacio(this.raiz);
    }

    private int cantidadNodosConUnHijoNoVacio(NodoBinario<T> nodoAux) {
        if (NodoBinario.esNodoVacio(nodoAux)) {
            return 0;
        }

        int cantidadXIzq = cantidadNodosConUnHijoNoVacio(nodoAux.getHijoIzquierdo());
        int cantidadXDer = cantidadNodosConUnHijoNoVacio(nodoAux.getHijoDerecho());
        if ((nodoAux.esVacioHijoIzquierdo() && !nodoAux.esVacioHijoIzquierdo()) ||
                (!nodoAux.esVacioHijoIzquierdo() && nodoAux.esVacioHijoDerecho())) {
            return cantidadXIzq + cantidadXDer + 1;
        }
        return cantidadXIzq + cantidadXDer;
    }

    public int cantNodosEnUnNivel(int nivel) {
        int alturaDelArbol = 0;
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<T>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            do {
                int cantNodosEnLaCola = colaDeNodos.size();
                for(int i = 0; i < cantNodosEnLaCola; i++) {
                    NodoBinario<T> nodoAux = colaDeNodos.poll();
                    if (!nodoAux.esVacioHijoIzquierdo()) {
                        colaDeNodos.offer(nodoAux.getHijoIzquierdo());
                    }
                    if (!nodoAux.esVacioHijoDerecho()) {
                        colaDeNodos.offer(nodoAux.getHijoDerecho());
                    }
                }
                alturaDelArbol++;

                if (alturaDelArbol == nivel) {
                    return alturaDelArbol;
                }
            } while(!colaDeNodos.isEmpty());
        }
        return 0;
    }

    public boolean nodosCompletosEnNivel(NodoBinario<T> raiz, int nivelObj) {
        return nodosCompletosEnNivel(this.raiz, nivelObj, 0);
    }

    private boolean nodosCompletosEnNivel(NodoBinario<T> nodoActual, int nivelObj, int nivelAct) {
        if (NodoBinario.esNodoVacio(nodoActual)) {
            return false;
        }

        if (nivelObj == nivelAct) {
            return !nodoActual.esVacioHijoIzquierdo() &&
                    !nodoActual.esVacioHijoDerecho();
        }

        boolean esCompletoElNivelPorIzq = this.nodosCompletosEnNivel(
                nodoActual.getHijoIzquierdo(),
                nivelObj, nivelAct+1);
        if (!esCompletoElNivelPorIzq) {
            return false;
        }
        return this.nodosCompletosEnNivel(
                nodoActual.getHijoDerecho(),
                nivelObj, nivelAct+1);
    }






    public String toStringVertical() {
        // Si el nodo es nulo, representamos "||"
        if (this.raiz == null) {
            return "(raíz) ||\n";
        }
        return toStringVertical("", "(raíz)", this.raiz);
    }

    /**
     * Método auxiliar recursivo que construye la representación vertical del árbol.
     * @param prefix      Prefijo para alinear visualmente las ramas verticales.
     * @param branchLabel Etiqueta a mostrar antes del dato: (raíz), (I) o (D).
     * @return Cadena con el formato vertical deseado.
     */
    private String toStringVertical(String prefix, String branchLabel, NodoBinario<T> nodoActual) {
        if (nodoActual == null) {
            return prefix + branchLabel + " ||\n";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(prefix)
                .append(branchLabel)
                .append(" ")
                .append(nodoActual.getDato() == null ? "||" : nodoActual.getDato())
                .append("\n");

        // Si el nodo tiene hijos, preparamos la conexión visual
        String childPrefix = prefix + "│  ";

        // (I) hijo izquierdo
        if (nodoActual.getHijoIzquierdo() != null) {
            sb.append(toStringVertical(childPrefix, "├─(I)", nodoActual.getHijoIzquierdo()));
        } else {
            sb.append(childPrefix).append("├─(I) ||\n");
        }

        // (D) hijo derecho
        if (nodoActual.getHijoDerecho() != null) {
            sb.append(toStringVertical(childPrefix, "└─(D)", nodoActual.getHijoDerecho()));
        } else {
            sb.append(childPrefix).append("└─(D) ||\n");
        }

        return sb.toString();
    }
}
