/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interficiepersistencia;

/**
 *
 * @author Juan Antonio
 */
public class IGestorException extends Exception{
    public IGestorException(String mssg){
        super(mssg);
    }
    public IGestorException(String mssg, Throwable cause){
        super(mssg, cause);
    }
}
