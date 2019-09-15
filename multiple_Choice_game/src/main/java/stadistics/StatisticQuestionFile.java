package stadistics;


import UI.Answers;
import UI.Question;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public final class StatisticQuestionFile extends Question {

    private Configuration config;

    public StatisticQuestionFile(LinkedList<String> options, Answers answer, String question, File appLocation, int ID){
        super(options, answer, question, ID);
        appLocation.mkdirs();
        File questionStatisticLoc = new File(appLocation.toString() + "\\"+ super.IDENTIFIER+ ".properties");
        try {

            boolean isNewFile = questionStatisticLoc.createNewFile();
            Parameters parameters = new Parameters();
            FileBasedConfigurationBuilder<FileBasedConfiguration> builder  = new FileBasedConfigurationBuilder<FileBasedConfiguration> (PropertiesConfiguration.class).configure(parameters.properties()
                    .setFile(questionStatisticLoc));
            builder.setAutoSave(true);
            this.config = builder.getConfiguration();
            config.setProperty("identifier", super.IDENTIFIER);
            config.setProperty("answer", super.getAnswer());
            config.setProperty("question", super.getQuestion().replaceAll("¿", "")
                .replaceAll("ñ", "n")
                .replaceAll("á", "a")
                .replaceAll("é", "e")
                .replaceAll("í", "i")
                .replaceAll("ó", "o")
                .replaceAll("ú", "u"));
            if(isNewFile){
                config.setProperty(Answers.A.toString(), 0);
                config.setProperty(Answers.B.toString(), 0);
                config.setProperty(Answers.C.toString(), 0);
                config.setProperty(Answers.D.toString(), 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("could not create stream");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void updateProperty(Answers answers){
        int electionsAmount = (config.getProperty(answers.toString()) == null) ? 0 : (Integer) config.getProperty(answers.toString());
        config.setProperty(answers.toString(), ++electionsAmount);

    }


}
