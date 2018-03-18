/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package order.information.day;

import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public abstract class Day {

    //Here will be stored this day's subjects
    protected ArrayList<String> subjects;
    
    //This method should return subjects
    public ArrayList<String> getSubject(){
        return subjects;
    }
    
}
