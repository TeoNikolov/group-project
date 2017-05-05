package compass.objects;

public class CompassToken {

    public String token;
    public String pos;
    public String ne;

    /** Corresponds to a single token (word)
     * @param token the word
     * @param pos the part of speech attribute
     * @param ne the named entity attribute
     */
    public CompassToken(String token, String pos, String ne) {
        this.token = token;
        this.pos = pos;
        this.ne = ne;
    }

    @Override
    public String toString() {
        return token + " / " + pos + " / " + ne;
    }
}
