package ed2TDAS.grafos.pesados.utils;

public class AdyacenteConPeso implements Comparable<AdyacenteConPeso> {
    private int indiceVertice;
    private double peso;
    public AdyacenteConPeso(int vertice) {
        this.indiceVertice = vertice;
    }

    public AdyacenteConPeso(int vertice, double peso) {
        this.indiceVertice = vertice;
        this.peso = peso;
    }

    public int getIndiceVertice() {
        return indiceVertice;
    }

    public void setIndiceVertice(int vertice) {
        this.indiceVertice = vertice;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    @Override
    public int compareTo(AdyacenteConPeso vertice) {
        Integer esteVertice = this.indiceVertice;
        Integer otroVertice = vertice.indiceVertice;
        return esteVertice.compareTo(otroVertice);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.indiceVertice;
        return hash;
    }

    @Override
    // Dos AdyacenteConPeso son considerados iguales si apuntan al mismo vértice, sin importar el peso.
    public boolean equals(Object otro) {
        if (otro == null) {
            return false;
        }
        if (getClass() != otro.getClass()) {
            return false;
        }
        AdyacenteConPeso other = (AdyacenteConPeso) otro;
        return this.indiceVertice == other.indiceVertice;
    }
}
