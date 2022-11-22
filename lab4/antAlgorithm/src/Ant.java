import java.util.ArrayList;

public class Ant {
    boolean isElite;

    int position;

    public Ant(boolean isElite, int position) {
        this.isElite = isElite;
        this.position = position;
    }

    public boolean isElite() {
        return isElite;
    }

    public int getPosition() {
        return position;
    }
}
