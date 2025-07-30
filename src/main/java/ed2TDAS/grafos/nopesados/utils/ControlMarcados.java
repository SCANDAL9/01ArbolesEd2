package ed2TDAS.grafos.nopesados.utils;

import java.util.ArrayList;
import java.util.List;

public class ControlMarcados {
    private List<Boolean> listaDeMarcados;

    public ControlMarcados(int nroDeVertices) {
        listaDeMarcados = new ArrayList<Boolean>();
        for (int i = 0; i < nroDeVertices; i++) {
            listaDeMarcados.add(Boolean.FALSE);
        }
    }

    public boolean estaMarcadoElVertice(int nroDelVertice) {
        return listaDeMarcados.get(nroDelVertice);
    }

    public void marcarVertices(int nroDelVertice) {
        listaDeMarcados.set(nroDelVertice, Boolean.TRUE);
    }

    public void desmarcarVertices(int nroDelVertice) {
        listaDeMarcados.set(nroDelVertice, Boolean.FALSE);
    }

    public void desmarcarTodosLosVertices() {
        for (int i = 0; i < listaDeMarcados.size(); i++) {
            listaDeMarcados.set(i, Boolean.FALSE);
        }
    }

    public boolean estanTodosLosVerticesMarcados() {
        return !listaDeMarcados.contains(Boolean.FALSE);
    }
}
