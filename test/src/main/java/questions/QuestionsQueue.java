package questions;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;


public class QuestionsQueue <E extends Question> implements Iterable{

    private LinkedList<E> questions;

    public QuestionsQueue(LinkedList<E> list){
        questions = new LinkedList<>(list);
    }

    public QuestionsQueue(){
        questions = new LinkedList<>();
    }

    public void addQuestion(E q){
        questions.add(q);
    }


    @SafeVarargs
    public final void addQuestions(E... qs){
        questions.addAll(Arrays.asList(qs));

    }


    @Override
    public Iterator iterator() {
        return questions.iterator();
    }


}
