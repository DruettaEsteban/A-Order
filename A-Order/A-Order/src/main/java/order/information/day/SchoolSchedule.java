/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package order.information.day;

import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author Usuario
 */
public class SchoolSchedule{
    
    //Schedule location
    private final HashMap<EnumWeekDays, Day> days;
    
    
    SchoolSchedule(){
        days = new HashMap<>();
    }    
    
    //Add a day's Schedule
    public void addDay(CompleteDay newDay) throws IncorrectDayException{
        //Evaluate for repetitions
        if(days.containsKey(newDay.getDay())){
            throw new IncorrectDayException("That Day Already Exists");
        }else{
            //Add new day
            days.put(newDay.getDay(), newDay.toLighterDay());
        }
    }
}
