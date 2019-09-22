package stadistics;

import UI.Question;

import java.util.LinkedList;
import java.util.Random;

public class  QuestionRandomizer<E extends Question> {
    protected LinkedList<E> originalList;
    protected LinkedList<E> currentIteration;


    public LinkedList<E> getQuestions(){

        return new LinkedList<>(originalList);
    }

    public QuestionRandomizer() {
        this.originalList = new LinkedList<>();
        this.currentIteration = new LinkedList<>();
    }

    public E getRandomQuestion(){
        if (originalList.isEmpty()){
            System.err.println("No question is loaded. Can't shuffle");
            return null;
        }
        if(currentIteration.isEmpty()) resetQuestionQueue();

        return  currentIteration.pop();
    }

     void resetQuestionQueue(){

        LinkedList<E> buffer = new LinkedList<>(originalList);
        Random random = new Random();

        int chosenOne;

        while (!buffer.isEmpty()){

            chosenOne = random.nextInt(buffer.size());
            currentIteration.add(buffer.get(chosenOne));
            buffer.remove(chosenOne);
        }

    }


    public void clearAndShuffle(){
        currentIteration.clear();
        resetQuestionQueue();
    }

    public void updateOriginalList(E newQuestion){
        originalList.add(newQuestion);
        currentIteration.add(newQuestion);
    }



}
