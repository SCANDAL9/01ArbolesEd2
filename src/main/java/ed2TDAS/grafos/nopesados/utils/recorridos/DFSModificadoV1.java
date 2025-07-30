package ed2TDAS.grafos.nopesados.utils.recorridos;

import ed2TDAS.grafos.excepciones.ExcepcionAristaNoExiste;
import ed2TDAS.grafos.excepciones.ExcepcionAristaYaExiste;
import ed2TDAS.grafos.nopesados.DiGrafo;
import ed2TDAS.grafos.nopesados.Grafo;
import ed2TDAS.grafos.nopesados.utils.ControlMarcados;
import ed2TDAS.grafos.nopesados.utils.matrices.MatrizDeAdyacencia;

public class DFSModificadoV1<T extends Comparable<T>> {
    private Grafo<T> grafoAux;
    protected final Grafo<T> elGrafo;
    protected final ControlMarcados controlMarcados;

//SIN TERMINAR
    public DFSModificadoV1(Grafo<T> unGrafo) {
        elGrafo = unGrafo;
        controlMarcados = new ControlMarcados(elGrafo.cantidadDeVertices());
    }

    public boolean existeCicloEnGrafo() throws ExcepcionAristaYaExiste {
        if (!(elGrafo instanceof Grafo<T>)) {
            throw new IllegalArgumentException("Se requiere un Grafo No Dirigido");
        }
        grafoAux = new Grafo<>(elGrafo.getVertices());
        for (T vertice : elGrafo.getVertices()) {
            if (!seVisitoElVertice(vertice)) {
                if (buscarCicloEnGrafo(vertice)) {
                    return true;
                }
            }
        }
        //}
        return false;
    }

    private boolean buscarCicloEnGrafo(T verticeAProcesar) throws ExcepcionAristaYaExiste {
        elGrafo.validarVertice(verticeAProcesar);
        int nroDelVerticeEnTurno = elGrafo.getNroVertice(verticeAProcesar);
        controlMarcados.marcarVertices(nroDelVerticeEnTurno);
        Iterable<T> adyacentesAProcesar = elGrafo.getAdyacentesDelVertice(verticeAProcesar);
        for (T adyacente : adyacentesAProcesar) {
            int nroDelAdyacente = elGrafo.getNroVertice(adyacente);
            if (!controlMarcados.estaMarcadoElVertice(nroDelAdyacente)) {
                grafoAux.insertarArista(verticeAProcesar, adyacente);
                if (buscarCicloEnGrafo(adyacente)) return true;
            } else {
                if (!grafoAux.existeAdyacencia(verticeAProcesar, adyacente)) {
                    return true;
                }
            }
        }
        return false;
    }

    //no terminado
    public boolean existeCicloEnDiGrafo1() throws ExcepcionAristaYaExiste {
        if (!(elGrafo instanceof DiGrafo<T>)) {
            throw new IllegalArgumentException("Se requiere un Grafo Dirigido");
        }
        grafoAux = new DiGrafo<>(elGrafo.getVertices());
        for (T vertice : elGrafo.getVertices()) {
            if (!seVisitoElVertice(vertice)) {
                controlMarcados.desmarcarTodosLosVertices();
                if (buscarCicloEnDiGrafo1(vertice)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean existeCicloEnDiGrafo() throws ExcepcionAristaYaExiste {
        MatrizDeAdyacencia<T> matrizDeAdy = new MatrizDeAdyacencia<>(elGrafo);
        return matrizDeAdy.existeCicloEnDiGrafo();
    }

    //sin terminar
    private boolean buscarCicloEnDiGrafo1(T verticeAProcesar) throws ExcepcionAristaYaExiste {
        elGrafo.validarVertice(verticeAProcesar);
        int nroDelVerticeEnTurno = elGrafo.getNroVertice(verticeAProcesar);
        controlMarcados.marcarVertices(nroDelVerticeEnTurno);
        Iterable<T> adyacentesAProcesar = elGrafo.getAdyacentesDelVertice(verticeAProcesar);

        for (T adyacente : adyacentesAProcesar) {
            int nroDelAdyacente = elGrafo.getNroVertice(adyacente);
            if (!controlMarcados.estaMarcadoElVertice(nroDelAdyacente)) {
                if (!grafoAux.existeAdyacencia(verticeAProcesar,adyacente)) {
                    grafoAux.insertarArista(verticeAProcesar, adyacente);
                }
                if (buscarCicloEnDiGrafo1(adyacente)) return true;
            } else {
                if (!grafoAux.existeAdyacencia(verticeAProcesar, adyacente)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean seVisitoTodosLosVertices() {
        return controlMarcados.estanTodosLosVerticesMarcados();
    }
    public boolean seVisitoElVertice(T unVertice) {
        elGrafo.validarVertice(unVertice);
        int nroVertice = elGrafo.getNroVertice(unVertice);
        return controlMarcados.estaMarcadoElVertice(nroVertice);
    }
}
