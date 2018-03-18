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
public class CompleteDay extends Day{
    
    //Wich day is it...
    private final EnumWeekDays dayOfTheWeek;
    
    //A day is required to create an object
    public CompleteDay(EnumWeekDays dayOfTheWeek){
        this.dayOfTheWeek = dayOfTheWeek;
    }
    
    //Add a new subject to this day
    public void addSubject(String subject){
        this.subjects.add(subject);
    }
    
    //Returns a lighter version
    public Day toLighterDay(){
        return new LighterDay(this);
    }
    
    //Return this object day
    public EnumWeekDays getDay(){
        return dayOfTheWeek;
    }

}
