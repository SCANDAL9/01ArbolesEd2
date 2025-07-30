package ed2TDAS.arboles.excepciones;


public class ExcepcionRecorridosInvalidos extends Exception {
    public ExcepcionRecorridosInvalidos() {
        super("Los recorridos no pertenecen al mismo Arbol");
    }

    public ExcepcionRecorridosInvalidos(String msg) {
        super(msg);
    }
}


