package compass.utilities;

import compass.Constants;
import compass.objects.Identifier;
import compass.objects.CompassToken;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.TreePrint;
import edu.stanford.nlp.util.ArrayHeap;
import edu.stanford.nlp.util.CoreMap;
//import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import us.monoid.json.JSONArray;

import java.io.*;
import java.util.*;

// Class for Natural Language Processing stuff
public class NLP {
    private static ArrayList<String> stopwords = new ArrayList<>();

    /**
     * Method accesses local file containing stopwords and loads them to a static list
     */
    public static void loadStopwords() {
        File stopwordsFile = new File(Constants.basePath + Constants.stopwordsFilename);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(stopwordsFile));
            String line = bufferedReader.readLine();

            while (line != null) {
                stopwords.add(line.toLowerCase());
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method accesses local file containing identifiers of specific schema and loads them to a static list
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

//    public static void main(String[] args) {
//        loadStopwords();
//        loadIdentifiers();
////        parseInput("What is the weather today?");
//        File trainerFile = new File(Constants.basePath + Constants.questionsName);
//
//        PrintStream err = System.err;
//        System.setErr(new PrintStream(new OutputStream() {
//            public void write(int b) {
//            }
//        }));
//
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new FileReader(trainerFile));
//            String line = bufferedReader.readLine();
//
//            while (line != null) {
//                System.out.println(line);
//                parseInput(line);
//                line = bufferedReader.readLine();
//            }
//
//            bufferedReader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.setErr(err);
//    }

    /**
     * Primary method responsible for parsing the input and outputting the result of the input in
     * HTML format relevant to the category
     *
     * @param input generally the Natural Language question asked by the user
     * @return the output passed back to the client in HTML format
     */
    public static String parseInput(String input) {
        Properties props = new Properties();

        if (Constants.debug) {
            props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse");
        } else {
            props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");
        }

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation annotation = new Annotation(input);
        pipeline.annotate(annotation);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        ArrayList<CompassToken> tokens = new ArrayList<>();

        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);

                if (!stopwords.contains(word.toLowerCase())) {
                    String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                    String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                    tokens.add(new CompassToken(word, pos, ne));

                    if (Constants.debug) {
//                        if (
//                                pos.equals("NN") ||
//                                        pos.equals("NNS") ||
//                                        pos.equals("RB") ||
//                                        pos.equals("CD") ||
//                                        pos.equals("WRB") ||
//                                        pos.equals("WP") ||
//                                        pos.equals("JJ")) {
                            System.out.println(word + " / " + pos + " / " + ne);
//                        }
                    }
                }
            }

//            if (Constants.debug) {
//                Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
//                TreePrint printer = new TreePrint("penn");
//                printer.printTree(tree);
//            }
        }

        String category = obtainCategory(tokens);
        String response = "";
        try {
            switch (category) {
                case "weather":
                    response = APIInterface.getAPI("Weather", "Cardiff");
                    break;
                case "transport":
                    response = APIInterface.getAPI("Transport", "Cardiff");
                    break;
                case "events":
                	response = APIInterface.getAPI("Events", "Cardiff");
                case "none":
                	//CURRENTLY COMPARING TOKENS CHANGE TO VALID STRING
                	response = APIInterface.getAPI("General", input.replace(" ", "+"));
                	if(response.equals("{\"results\":[]}")) {
                		response = "Sorry, we do not have an answer to your question!";	
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
        resultObj.put("Response", response);

        return resultObj.toJSONString();
    }

    private static String obtainCategory(ArrayList<CompassToken> tokens) {
        String category = "none";

        for (CompassToken token : tokens) {
            Identifier id = Identifier.getLinkIdentifier(token.token);

            if (id != null)
                category = id.getCategory();
        }

        return category;
    }
}
