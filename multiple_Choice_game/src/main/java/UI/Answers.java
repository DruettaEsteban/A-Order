package UI;

public enum Answers {
    A(0),B(1),C(2),D(3);

    Answers(final int answer){
        this.answer = answer;
    }

    private int answer;

    public int getAnswer(){
        return answer;
    }
}
