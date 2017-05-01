package compass.objects;

public class CompassToken {

    public String token;
    public String pos;
    public String ne;

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
