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
    
    
    public SchoolSchedule(){
        days = new HashMap<>();
    }    
    
    //Add a day's Schedule
    public void addDay(CompleteDay newDay){
        //Evaluate for repetitions
        if(days.containsKey(newDay.getDay())){
            throw new IncorrectDayException("That Day Already Exists");
        }else{
            //Add new day
            days.put(newDay.getDay(), new LighterDay(newDay));    //Returns a lighter version
        }
    }
    
    //A class created to use less memory
    private class LighterDay extends Day{

        LighterDay(CompleteDay day){
            this.subjects = day.subjects;
        }
    }
    
    //Returns an specific day's subjects
    public ArrayList getDaySubjects(EnumWeekDays dayOfTheWeek){
        return this.days.get(dayOfTheWeek).getSubjects();
    }
    
}
