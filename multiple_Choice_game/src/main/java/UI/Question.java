package UI;

import java.util.LinkedList;

public class Question {
    private final LinkedList<String> options;
    private final Answers answer;
    private final String question;

    public Question(LinkedList<String> options, Answers answer, String question){
        this.options = options;
        this.answer = answer;
        this.question = question;
    }

    public LinkedList<String> getOptions() {
        return options;
    }

    public Answers getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }


    public String toString(){
        return "Question: " + question+ "\n" +
                "First Question: " + options.get(0) + "\n" +
                "Second Question: " + options.get(1) + "\n" +
                "Third Question: " + options.get(2) + "\n" +
                "Forth Question: " + options.get(3) + "\n" +
                "True Answer: " + answer.toString() + "\n";

    }
}
