import java.util.ArrayList;
import java.util.List;

public class StatisticsData {
    private List<Player> players;

    private String gameMode;

    private String map;

    private String matchLength;

    public StatisticsData() {
        players = new ArrayList<Player>();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getMatchLength() {
        return matchLength;
    }

    public void setMatchLength(String matchLength) {
        this.matchLength = matchLength;
    }
}
