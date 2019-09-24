package stadistics;

import UI.Question;

import java.util.LinkedList;

public class QuestionRectifiedRandomizer <E extends StatisticQuestion> extends QuestionRandomizer{

    @Override
    void resetQuestionQueue(){
        LinkedList<E> buffer = new LinkedList<E>(super.originalList);

        while (!buffer.isEmpty()){
            super.currentIteration.add(buffer.pop());
            System.out.println("here");
        }
    }

    public void updateQuestion(Question question) {



        for (Object originalQuestion: originalList) {
            StatisticQuestion originalSQuestion = (StatisticQuestion) originalQuestion;
            if (originalSQuestion.getIDENTIFIER() == question.getIDENTIFIER()) originalSQuestion.updateQuestionData();
        }


    }



}
