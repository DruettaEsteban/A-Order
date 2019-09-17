package stadistics;

import UI.Answers;
import UI.Question;

import java.util.LinkedList;

public class StatisticQuestion extends Question {

    public int amountA;
    public int amountB;
    public int amountC;
    public int amountD;

    public StatisticQuestion(LinkedList<String> options, Answers answer, String question, int IDENTIFIER) {
        super(options, answer, question, IDENTIFIER);
    }



}
