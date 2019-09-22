package stadistics;

import UI.Answers;
import UI.Controller;
import UI.Question;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class StatisticQuestion extends Question {

    public  int amountA;
    public  int amountB;
    public  int amountC;
    public  int amountD;
    private Properties properties;


    public StatisticQuestion(Question question){
        super(question);
        updateQuestionData();
    }



    public void updateQuestionData(){
        try (FileInputStream statisticsLocation = new FileInputStream(Controller.STATISTICS_FILE_DIR + "\\" + IDENTIFIER + ".properties")) {
            this.properties = new Properties();
            properties.load(statisticsLocation);
            amountA = Integer.parseInt(properties.getProperty(Answers.A.toString()));
            amountB = Integer.parseInt(properties.getProperty(Answers.B.toString()));
            amountC = Integer.parseInt(properties.getProperty(Answers.C.toString()));
            amountD = Integer.parseInt(properties.getProperty(Answers.D.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString(){
        String superS = super.toString();
        String A = amountA + "\n";
        String B = amountB + "\n";
        String C = amountC + "\n";
        String D = amountD + "\n";
        return superS + A +B+C+D;
    }

}
