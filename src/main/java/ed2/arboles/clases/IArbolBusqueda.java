/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ed2.arboles.clases;

import ed2.arboles.excepciones.ExcepcionDatoNoExiste;
import ed2.arboles.excepciones.ExcepcionDatoYaExiste;
import java.util.List;

/**
 *
 * @author Flia Maldonado
 * @param <T>
 */
public interface IArbolBusqueda<T extends Comparable<T>> {
    
    void insertar(T dato) throws ExcepcionDatoYaExiste;
    void eliminar(T dato) throws ExcepcionDatoNoExiste;
    T buscar(T dato);
    
    boolean contiene(T dato);
    int size();
    int altura();
    void vaciar();
    boolean esArbolVacio();
    int nivel();
    List<T> recorridoEnInOrden();
    List<T> recorridoEnPreOrden();
    List<T> recorridoEnPostOrden();
    List<T> recorridoPorNiveles();
    String toStringVertical();
}
