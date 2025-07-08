package ed2TDAS.grafos.pesados.utils;

public class AristaConPeso implements Comparable<AristaConPeso> {
    private int origen;
    private int destino;
    private double peso;

    public AristaConPeso(int origen, int destino, double peso) {
        this.origen = origen;
        this.destino = destino;
        this.peso = peso;
    }

    public int getOrigen() {return origen;}
    public int getDestino() {return destino;}
    public double getPeso() {return peso;}

    @Override
    public int compareTo(AristaConPeso otra) {
        return Double.compare(this.peso, otra.peso);
    }
}
