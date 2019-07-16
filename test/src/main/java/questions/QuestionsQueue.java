package questions;
import java.util.Iterator;
import java.util.LinkedList;


public class QuestionsQueue <E extends Question> implements Iterable{

    private LinkedList<E> questions;

    public QuestionsQueue(LinkedList<E> list){
        questions = new LinkedList<>(list);

    }

    public void addQuestion(E q){
        questions.add(q);
    }


    @Override
    public Iterator iterator() {
        return questions.iterator();
    }


}
