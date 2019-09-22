package UI;

import java.util.LinkedList;

public abstract class Question {
    protected final LinkedList<String> options;
    private final Answers answer;
    protected final String question;
    protected final int IDENTIFIER;


    public int getIDENTIFIER() {
        return IDENTIFIER;
    }

    public Question(LinkedList<String> options, Answers answer, String question, int IDENTIFIER){
        this.options = options;
        this.answer = answer;
        this.question = question;
        this.IDENTIFIER = IDENTIFIER;
    }

    public Question(Question q){
        this.options = q.options;
        this.answer = q.answer;
        this.question = q.question;
        this.IDENTIFIER = q.IDENTIFIER;
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
