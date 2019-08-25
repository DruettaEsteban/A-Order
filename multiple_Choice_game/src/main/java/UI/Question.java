package UI;

import java.util.LinkedList;

public class Question {
    private LinkedList<String> options;
    private Answers answers;
    private String question;

    public Question(LinkedList options, Answers answer, String question){
        this.options = options;
        this.answers = answer;
        this.question = question;
    }

    public LinkedList<String> getOptions() {
        return options;
    }

    public Answers getAnswer() {
        return answers;
    }

    public String getQuestion() {
        return question;
    }
}
