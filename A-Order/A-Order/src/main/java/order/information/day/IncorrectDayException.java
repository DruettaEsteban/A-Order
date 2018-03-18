/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package order.information.day;

/**
 *
 * @author Usuario
 */
public class IncorrectDayException extends Exception{
    
    String message;
    
    IncorrectDayException(String error){
        message = error;
    }
    
    public String message(){
        return message;
    }
    
}
