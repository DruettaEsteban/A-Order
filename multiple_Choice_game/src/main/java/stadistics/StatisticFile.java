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

public final class StatisticFile extends Question {

    private Configuration config;

    public StatisticFile(LinkedList<String> options, Answers answer, String question, File appLocation){
        super(options, answer, question);
        File questionStatisticLoc = new File(appLocation.toURI().toString() + appLocation.listFiles().length);
        questionStatisticLoc.getParentFile().mkdirs();
        try {
            questionStatisticLoc.createNewFile();
            Parameters parameters = new Parameters();
            FileBasedConfigurationBuilder<FileBasedConfiguration> builder  = new FileBasedConfigurationBuilder<FileBasedConfiguration> (PropertiesConfiguration.class).configure(parameters.properties()
                    .setFile(questionStatisticLoc));
            builder.setAutoSave(true);
            config.setProperty("identifier", super.IDENTIFIER);
            config.setProperty("answer", super.getAnswer());
            this.config = builder.getConfiguration();

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
