package compass.objects;

import javafx.util.Pair;
import java.util.ArrayList;

public class Identifier {
    public static ArrayList<Identifier> identifiers = new ArrayList<>();

    public static Identifier getLinkIdentifier(String link) {
        for (Identifier id : identifiers) {
            for (Pair<String, ArrayList<String>> pair : id.metadata) {
                for (String s : pair.getValue()) {
                    if (s.equals(link))
                        return id;
                }
            }
        }

        return null;
    }

    public static Identifier getLinkIdentifier(String subcategory, String link) {
        for (Identifier id : identifiers) {
            for (Pair<String, ArrayList<String>> pair : id.metadata) {
                if (pair.getKey().equals(subcategory)) {
                    for (String s : pair.getValue()) {
                        if (s.equals(link))
                            return id;
                    }
                }
            }
        }

        return null;
    }

    private String category;
    private ArrayList<Pair<String, ArrayList<String>>> metadata;

    public String getCategory() {
        return category;
    }

    public Identifier(String category) {
        this.category = category;
        metadata = new ArrayList<>();
    }

    /**
     * Add the current instance to the list of managed identifiers.
     */
    public void add() {
        identifiers.add(this);
    }

    /**
     * Remove the current instance from the list of managed identifiers.
     */
    public void remove() {
        identifiers.remove(this);
    }

    /**
     * Method checks if the object's metadata is valid.
     *
     * @param cleanup true if object metadata should be cleaned before validation test
     * @return true if the current identifier has a valid metadata, false otherwise
     */
    public boolean isValid(boolean cleanup) {
        if (cleanup) {
            cleanMetadata();
        }

        return metadata.size() != 0;
    }

    /**
     * Perform cleanup of the Identifier's metadata by removing empty occurrences.
     */
    private void cleanMetadata() {
        for (int i=metadata.size() - 1; i>=0; i--) {
            Pair<String, ArrayList<String>> pair = metadata.get(i);

            if (pair.getKey().isEmpty() || pair.getValue().isEmpty())
                metadata.remove(pair);
        }
    }

    /**
     * Create and add a new empty subcategory to the metadata.
     *
     * @param subcategory the title of the subcategory
     */
    public void addPair(String subcategory) {
        if (subcategory.trim().isEmpty())
            return;

        metadata.add(new Pair<>(subcategory, new ArrayList<>()));
    }

    /**
     * Add a new populated subcategory to the metadata.
     *
     * @param pair the populated subcategory Pair object to be added
     */
    public void addPair(Pair<String, ArrayList<String>> pair) {
        metadata.add(pair);
    }

    /**
     * Create and add a new populated subcategory to the metadata.
     *
     * @param subcategory the title of the subcategory
     * @param links a list with all links to be added to the subcategory
     */
    public void addPair(String subcategory, ArrayList<String> links) {
        metadata.add(new Pair<>(subcategory, links));
    }

    /**
     * Add a new link to an existing category.
     *
     * @param subcategory the title of the subcategory to add the link to
     * @param link the link to be added to the specified subcategory
     */
    public void addLink(String subcategory, String link) {
        if (subcategory.trim().isEmpty() || link.trim().isEmpty())
            return;

        metadata.stream().filter(pair -> pair.getKey().equals(subcategory)).forEach(pair -> pair.getValue().add(link));
    }

    /**
     * Method checks if the specified link exists within the object's whole metadata.
     *
     * @param link the link to be searched for in the metadata
     * @return true if the specified link has been found in the metadata
     */
    public boolean hasLink(String link) {
        for (Pair<String, ArrayList<String>> pair : metadata) {
            for (String s : pair.getValue()) {
                if (s.equals(link))
                    return true;
            }
        }

        return false;
    }

    /**
     * Method checks if the specified link exists within the object's whole metadata.
     *
     * @param subcategory the subcategory to search the specified link within
     * @param link the link to be searched for in the specified subcategory in the metadata
     * @return true if the specified link has been found in the specified subcategory in the metadata
     */
    public boolean hasLink(String subcategory, String link) {
        for (Pair<String, ArrayList<String>> pair : metadata) {
            if (pair.getKey().equals(subcategory)) {
                for (String s : pair.getValue()) {
                    if (s.equals(link))
                        return true;
                }
            }
        }

        return false;
    }

    @Override
    public String toString() {
        String out = category;

        for (Pair<String, ArrayList<String>> pair : metadata) {
            out += "\n" + pair.getKey() + " -";
            boolean first = true;
            for (String s : pair.getValue()) {
                if (first) {
                    first = false;
                } else {
                    out += ",";
                }

                out += " " + s;
            }
        }

        return out;
    }

}
