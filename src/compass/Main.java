package compass;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.json.simple.JSONValue;
import tk.plogitech.darksky.forecast.APIKey;
import tk.plogitech.darksky.forecast.ForecastRequest;
import tk.plogitech.darksky.forecast.ForecastRequestBuilder;

import javax.xml.bind.JAXBException;

public class Main {
    public static void main(String[] args) {
        String text = "What is the weather going to be tomorrow in Cardiff?";

        System.out.println(NLPMethod(text));
    }

    public static String NLPMethod(String input) {
        generateWeather();

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Annotation annotation = new Annotation(input);
        pipeline.annotate(annotation);

        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

        String response = "This is your input broken down into tokens and assigned Part-of-Speech tags...<br>";

        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                response += word + " / " + pos + "<br>";
            }
        }

        return response;
    }

    public static void generateWeather() {
        ForecastRequest request = new ForecastRequestBuilder()
                .key(new APIKey())
                .location();
    }

    public static String randomMethod() {
        Random r = new Random();
        return Integer.toString(r.nextInt());
    }
}
