/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ed2TDAS.arboles.excepciones;

/**
 *
 * @author H
 */
public class ExcepcionDatoYaExiste extends Exception {
    
    public ExcepcionDatoYaExiste() {
        super("Dato ya existe en el árbol");
    }
    
    public ExcepcionDatoYaExiste(String msg) {
        super(msg);
    }
}
