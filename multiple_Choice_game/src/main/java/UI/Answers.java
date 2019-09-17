package UI;

public enum Answers {
    A(0),B(1),C(2),D(3);

    Answers(final int answer){
        this.answer = answer;
    }

    private final int answer;

    public int getAnswer(){
        return answer;
    }

    public static int toInt(String entry){
        switch (entry){
            case "A":
            case "a":
                return 0;

            case "B":
            case "b":
                return 1;
            case "C":
            case "c":
                return 2;
            case "D":
            case "d":
            default:
                return 3;


        }
    }

}
