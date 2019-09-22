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
        //super.originalList.forEach(System.out::println);
        /*
        super.originalList.forEach(e-> {
            System.out.println(e);
            ((StatisticQuestion) e).updateQuestionData();
            System.out.println(e);
        });
        /*
        LinkedList<E> updatedList = new LinkedList<>();
        for (int i = 0;i < originalList.size() ; i++) {
            StatisticQuestion buff = (StatisticQuestion) originalList.get(i);
            buff.updateQuestionData();
            updatedList.add((E) buff);
        }
        originalList = updatedList;
        */



        //super.originalList.forEach(System.out::println);

        for (Object originalQuestion: originalList) {
            StatisticQuestion originalSQuestion = (StatisticQuestion) originalQuestion;
            if (originalSQuestion.getIDENTIFIER() == question.getIDENTIFIER()) originalSQuestion.updateQuestionData();
        }


    }



}
