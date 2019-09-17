package stadistics;

import UI.Question;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class  QuestionRandomizer<E extends Question> {
    private final ArrayList<E> originalList;
    private LinkedList<E> currentIteration;

    public QuestionRandomizer(ArrayList<E> originalList) {
        this.originalList = new ArrayList<>(originalList);
        this.currentIteration = new LinkedList<>(originalList);
    }

    public QuestionRandomizer() {
        this.originalList = new ArrayList<>();
        this.currentIteration = new LinkedList<>();
    }

    public E getRandomQuestion(){
        if (originalList.isEmpty()){
            System.err.println("No question is loaded. Can't shuffle");
            return null;
        }
        if(currentIteration.isEmpty()) resetQuestionQueue();

        return currentIteration.pop();
    }

    private void resetQuestionQueue(){
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
