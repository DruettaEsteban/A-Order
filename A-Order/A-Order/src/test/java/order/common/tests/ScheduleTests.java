/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package order.common.tests;
import order.information.day.CompleteDay;
import order.information.day.EnumWeekDays;
import order.information.day.SchoolSchedule;
import org.junit.Assert;
import org.junit.Test;
/**
 *
 * @author Usuario
 */
public class ScheduleTests {
    
    @Test
    public void testAddition(){
        CompleteDay monday = new CompleteDay(EnumWeekDays.MONDAY);
        monday.addSubject("maths");
        monday.addSubject("music");
        
        SchoolSchedule schedule = new SchoolSchedule();
        schedule.addDay(monday);
        System.out.println(monday.getSubjects().toString()+"-----------------");
        
        Assert.assertTrue(schedule.getDaySubjects(EnumWeekDays.MONDAY).equals(monday.getSubjects()));

    }
    
}
