package compass.utilities;

import compass.Constants;
import compass.objects.Identifier;
import compass.objects.CompassToken;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import us.monoid.json.JSONArray;
import java.io.*;
import java.util.*;

public class NLP {
    private static ArrayList<String> stopwords = new ArrayList<>();

    /**
     * Method accesses local file containing stopwords and loads them to a static list.
     */
    public static void loadStopwords() {
        File stopwordsFile = new File(Constants.basePath + Constants.stopwordsFilename);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(stopwordsFile));
            String line = bufferedReader.readLine();

            while (line != null) {
                String[] words = line.toLowerCase().split(",");
                for (String word : words) {
                    stopwords.add(word.trim());
                }
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method accesses local file containing identifiers of specific schema and loads them to a static list.
     */
    public static void loadIdentifiers() {
        File file = new File(Constants.basePath + Constants.idsFilename);
        Scanner reader;
        Identifier currentID = null;

        try {
            reader = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        while (reader.hasNextLine()) {
            String line = reader.nextLine().trim();

            if (line.isEmpty() || line.charAt(0) == '#')
                continue;

            if (line.charAt(0) == '$') {
                if (currentID != null) {
                    if (currentID.isValid(true))
                        currentID.add();
                }
                currentID = new Identifier(line.substring(1).trim());
            } else {
                if (currentID != null) {
                    String[] pair = line.split(":+");

                    if (pair.length != 2)
                        continue;
                    if (pair[0].isEmpty())
                        continue;

                    currentID.addPair(pair[0]);
                    for (String s : pair[1].split(",+"))
                        currentID.addLink(pair[0], s);
                }
            }
        }

        if (currentID != null)
            if (currentID.isValid(true))
                currentID.add();
    }

    public static void main(String[] args) {
        loadStopwords();
        loadIdentifiers();

//        parseInput("What is the weather today?");
        File trainerFile = new File(Constants.basePath + Constants.questionsName);

        PrintStream err = System.err;
        System.setErr(new PrintStream(new OutputStream() {
            public void write(int b) {
            }
        }));

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(trainerFile));
            String line = bufferedReader.readLine();

            while (line != null) {
                System.out.println(line);
                parseInput(line);
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.setErr(err);
        }

    /**
     * Primary method responsible for parsing the input and outputting the result of the input in
     * a general format.
     *
     * @param input the Natural Language question asked by the user
     * @return the output passed back to the client in JSON format
     */
    public static String parseInput(String input) {
        Properties props = new Properties();
        Boolean requiresProcessing = true;

        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");  // Set the NLP Properties

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation annotation = new Annotation(input);
        pipeline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        ArrayList<CompassToken> filteredTokens = new ArrayList<>();
        ArrayList<CompassToken> tokens = new ArrayList<>();

        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);

                tokens.add(new CompassToken(word, pos, ne));

                if (!stopwords.contains(word.toLowerCase())) {
                    filteredTokens.add(new CompassToken(word, pos, ne));
                }
            }
        }

        String category = obtainCategory(filteredTokens);
        String response = "";
        String data = "";

        for (CompassToken token : filteredTokens) {
            if (token.pos.equals("NNP") ||
                    token.pos.equals("NNPS") ||
                    token.pos.equals("JJ") ||
                    token.pos.equals("JJS") ||
                    token.pos.equals("NN") ||
                    token.pos.equals("NNS")) {
                data += " " + token.token;
            }
        }

        data = data.trim();

        try {
            switch (category) {
                case "weather":
                    response = APIInterface.getAPI("Weather", "Cardiff");
                    JSONParser parser = new JSONParser();

                    try {
                        JSONObject robj = (JSONObject) parser.parse(response);
                        JSONObject rcurrently = (JSONObject) robj.get("currently");
                        Object rinfo = rcurrently.get(data);

                        if (rinfo != null) {
                            String metric = "";

                            switch(data) {
                                case("visibility"):
                                    metric = "m";
                                    break;
                                case("humidity"):
                                    metric = "%";
                                    break;
                                case("pressure"):
                                    metric = "mb";
                                    break;
                                case("temperature"):
                                    metric = "°";
                                    break;
                            }

                            response = "The " + data + " is currently " + rinfo + metric + ".";
                            requiresProcessing = false;
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;
                case "transport":
                    response = APIInterface.getAPI("Transport", "Cardiff");
                    parser = new JSONParser();

                    try {
                        JSONObject robj = (JSONObject) parser.parse(response);
                        org.json.simple.JSONArray rmember = (org.json.simple.JSONArray) robj.get("member");
                        JSONObject rstation = (JSONObject) rmember.get(0);


                        if (data.contains("nearest") || data.contains("distance")) {
                            response = "Station " + rstation.get("name") + " is " + rstation.get("distance") +
                                    "m away from you.";
                            requiresProcessing = false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case "general":
                    if (Constants.errdebug) {
                        System.err.println("General Data: " + data.trim());
                    }

                    try {
                        response = APIInterface.getAPI("General", data.trim(), true);
                        parser = new JSONParser();
                        JSONObject robj = (JSONObject) parser.parse(response);

                        if (((org.json.simple.JSONArray) robj.get("results")).size() == 0) {
                            category = "none";
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject resultObj = new JSONObject();
        Collection<JSONObject> tokenList = new ArrayList<>();

        for(CompassToken t : tokens) {
            JSONObject temp = new JSONObject();
            temp.put("NamedEntity", t.ne);
            temp.put("PartOfSpeech", t.pos);
            temp.put("Token", t.token);
            tokenList.add(temp);
        }

        resultObj.put("Category", category);
        resultObj.put("TokenInfo", new JSONArray(tokenList));
        if (requiresProcessing) {
            resultObj.put("Response", response);
        } else {
            JSONObject rtext = new JSONObject();
            rtext.put("Text", response);
            resultObj.put("Response", rtext);
        }

        return resultObj.toJSONString();
    }

    /**
     * @param tokens the list of tokens to compare against the loaded identifiers
     * @return the string version of the category extracted from the tokens
     */
    private static String obtainCategory(ArrayList<CompassToken> tokens) {
        String category = "general";

        for (CompassToken token : tokens) {
            Identifier id = Identifier.getLinkIdentifier(token.token.toLowerCase());

            if (id != null)
                category = id.getCategory();
        }

        if (Constants.errdebug) {
            System.err.println("Tokens: " + tokens);
            System.err.println("Category derived: " + category);
        }

        return category;
    }
}