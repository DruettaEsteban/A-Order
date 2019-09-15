package IO;

import UI.Answers;
import UI.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import stadistics.StatisticQuestionFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class QuestionsFactory {
    private final LinkedList<StatisticQuestionFile> questions = new LinkedList<>();
    private final File LOCATION;
    private Document document;
    private Element root;

    private static String fileLocation;

    private static QuestionsFactory instance;

    public static QuestionsFactory newInstance(){
        if(instance == null) {
            instance = new QuestionsFactory(fileLocation);
        }
        return instance;
    }

    public static QuestionsFactory newInstance(String fileLocation){
        if(instance == null) {
            QuestionsFactory.fileLocation = fileLocation;
            instance = new QuestionsFactory(fileLocation);
        }
        return instance;

    }


    private QuestionsFactory(String fileLocation){
        LOCATION = new File(fileLocation);
        LOCATION.getParentFile().mkdirs();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            if(LOCATION.createNewFile()){
                //CREATED
                document =  documentBuilder.newDocument();
                root = document.createElement("questions");
                document.appendChild(root);
                saveDocument();

            }else {
                //Already exists
                document = documentBuilder.parse(LOCATION);
                document.getDocumentElement().normalize();
                root = (Element) document.getElementsByTagName("questions").item(0);
                NodeList nodeList = document.getElementsByTagName("simpleQuestion");

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if(node.getNodeType() == Node.ELEMENT_NODE){
                        Element element = (Element) node;
                        String question = element.getElementsByTagName("text").item(0).getTextContent();
                        String A = element.getElementsByTagName("A").item(0).getTextContent();
                        String B = element.getElementsByTagName("B").item(0).getTextContent();
                        String C = element.getElementsByTagName("C").item(0).getTextContent();
                        String D = element.getElementsByTagName("D").item(0).getTextContent();
                        String ID = element.getElementsByTagName("id").item(0).getTextContent();
                        Answers answer = null;
                        switch (element.getElementsByTagName("answer").item(0).getTextContent()){
                            case "0":
                                answer = Answers.A;
                                break;
                            case "1":
                                answer = Answers.B;
                                break;
                            case "2":
                                answer = Answers.C;
                                break;
                            case "3":
                                answer = Answers.D;
                                break;
                        }
                        LinkedList<String> options = new LinkedList<>();
                        Collections.addAll(options, A,B,C,D);

                        StatisticQuestionFile recoveredQuestion = new StatisticQuestionFile(options, answer, question, new File(Controller.STATISTICS_FILE_DIR), Integer.parseInt(ID));
                        questions.add(recoveredQuestion);
                    }
                }
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.err.println("Se ha iniciado el sistema sin ninguna pregunta cargada");
        }
    }


    public void addQuestion(StatisticQuestionFile question){
        //Adds new question to the buffer
        questions.add(question);

        //New Question Marker
        Element questionMarker = document.createElement("simpleQuestion");

        //Question Text
        Element text = document.createElement("text");
        text.appendChild(document.createTextNode(question.getQuestion()));
        questionMarker.appendChild(text);

        //Option A
        Element A = document.createElement("A");
        A.appendChild(document.createTextNode(question.getOptions().get(Answers.A.getAnswer())));
        questionMarker.appendChild(A);

        //Option B
        Element B = document.createElement("B");
        B.appendChild(document.createTextNode(question.getOptions().get(Answers.B.getAnswer())));
        questionMarker.appendChild(B);

        //Option C
        Element C = document.createElement("C");
        C.appendChild(document.createTextNode(question.getOptions().get(Answers.C.getAnswer())));
        questionMarker.appendChild(C);

        //Option D
        Element D = document.createElement("D");
        D.appendChild(document.createTextNode(question.getOptions().get(Answers.D.getAnswer())));
        questionMarker.appendChild(D);

        //IDENTIFIER
        Element identifier = document.createElement("id");
        identifier.appendChild(document.createTextNode(String.valueOf(question.getIDENTIFIER())));
        questionMarker.appendChild(identifier);

        //ANSWER
        Element correctAnswer = document.createElement("answer");
        correctAnswer.appendChild(document.createTextNode(String.valueOf((question.getAnswer().getAnswer()))));
        questionMarker.appendChild(correctAnswer);
        //Add to root
        root.appendChild(questionMarker);
        saveDocument();


    }

    private void saveDocument(){
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(LOCATION);
            transformer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    public StatisticQuestionFile getRandomQuestion(){

        if(questions.size()> 0) {
            int item = new Random().nextInt(questions.size());
            return questions.get(item);
        }else {
            return null;
        }
    }

    public static StatisticQuestionFile inputQuestion(){
        while (true) {
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Ingrese la pregunta: ");
                String question = scanner.nextLine();
                System.out.print("Ingrese respuesta A: ");
                String A = scanner.nextLine();
                System.out.print("Ingrese respuesta B: ");
                String B = scanner.nextLine();
                System.out.print("Ingrese respuesta C: ");
                String C = scanner.nextLine();
                System.out.print("Ingrese respuesta D: ");
                String D = scanner.nextLine();
                System.out.print("Cual es la respuesta? (A, B, C o D)");
                String answer = scanner.nextLine();
                Answers answersRectified;
                switch (answer) {
                    case "A":
                    case "a":
                        answersRectified = Answers.A;
                        break;
                    case "B":
                    case "b":
                        answersRectified = Answers.B;
                        break;
                    case "C":
                    case "c":
                        answersRectified = Answers.C;
                        break;
                    case "D":
                    case "d":
                        answersRectified = Answers.D;
                        break;
                    default:
                        throw new Exception("illegal state");
                }
                LinkedList<String> options = new LinkedList<>();
                Collections.addAll(options, A, B, C, D);
                for (String e : options) {
                    if (e == null || e.isEmpty()) throw new Exception("Illegal state");
                }
                if (question == null || question.isEmpty()) throw new Exception("illegal state");
                return new StatisticQuestionFile(options, answersRectified, question, new File(Controller.STATISTICS_FILE_DIR),  new Random().nextInt(1073741824));


            } catch (Exception e) {
                System.out.println("ESTADO NO PERMITIDO!! REINICIANDO...\r\n");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }




    }


}
