/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ed2.arboles.excepciones;

/**
 *
 * @author Flia Maldonado
 */
public class ExcepcionDatoYaExiste extends Exception {
    
    public ExcepcionDatoYaExiste() {
        super("Dato ya existe en el árbol");
    }
    
    public ExcepcionDatoYaExiste(String msg) {
        super(msg);
    }
}
