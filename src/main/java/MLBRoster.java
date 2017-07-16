import java.util.*;
import java.util.stream.Collectors;

public class MLBRoster {

    private final int maxSalary = 50000;

    private Player PITCHER_1 = new Player();

    private Player PITCHER_2 = new Player();

    private Player CATCHER = new Player();

    private Player BASE_1 = new Player();

    private Player BASE_2 = new Player();

    private Player BASE_3 = new Player();

    private Player SHORT_STOP = new Player();

    private Player OUTFIELD_1 = new Player();

    private Player OUTFIELD_2 = new Player();

    private Player OUTFIELD_3 = new Player();

    public MLBRoster() {
    }

    public MLBRoster(MLBRoster roster) {
        this.PITCHER_1 = roster.getPITCHER_1();
        this.PITCHER_2 = roster.getPITCHER_2();
        this.CATCHER = roster.getCATCHER();
        this.BASE_1 = roster.getBASE_1();
        this.BASE_2 = roster.getBASE_2();
        this.BASE_3 = roster.getBASE_3();
        this.SHORT_STOP = roster.getSHORT_STOP();
        this.OUTFIELD_1 = roster.getOUTFIELD_1();
        this.OUTFIELD_2 = roster.getOUTFIELD_2();
        this.OUTFIELD_3 = roster.getOUTFIELD_3();
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public Player getPITCHER_1() {
        return PITCHER_1;
    }

    public void setPITCHER_1(Player PITCHER_1) {
        this.PITCHER_1 = PITCHER_1;
    }

    public Player getPITCHER_2() {
        return PITCHER_2;
    }

    public void setPITCHER_2(Player PITCHER_2) {
        this.PITCHER_2 = PITCHER_2;
    }

    public Player getCATCHER() {
        return CATCHER;
    }

    public void setCATCHER(Player CATCHER) {
        this.CATCHER = CATCHER;
    }

    public Player getBASE_1() {
        return BASE_1;
    }

    public void setBASE_1(Player BASE_1) {
        this.BASE_1 = BASE_1;
    }

    public Player getBASE_2() {
        return BASE_2;
    }

    public void setBASE_2(Player BASE_2) {
        this.BASE_2 = BASE_2;
    }

    public Player getBASE_3() {
        return BASE_3;
    }

    public void setBASE_3(Player BASE_3) {
        this.BASE_3 = BASE_3;
    }

    public Player getSHORT_STOP() {
        return SHORT_STOP;
    }

    public void setSHORT_STOP(Player SHORT_STOP) {
        this.SHORT_STOP = SHORT_STOP;
    }

    public Player getOUTFIELD_1() {
        return OUTFIELD_1;
    }

    public void setOUTFIELD_1(Player OUTFIELD_1) {
        this.OUTFIELD_1 = OUTFIELD_1;
    }

    public Player getOUTFIELD_2() {
        return OUTFIELD_2;
    }

    public void setOUTFIELD_2(Player OUTFIELD_2) {
        this.OUTFIELD_2 = OUTFIELD_2;
    }

    public Player getOUTFIELD_3() {
        return OUTFIELD_3;
    }

    public void setOUTFIELD_3(Player OUTFIELD_3) {
        this.OUTFIELD_3 = OUTFIELD_3;
    }

    public List<Player> getPlayers() {
        List<Player> playerList = new ArrayList<>();
        playerList.add(PITCHER_1);
        playerList.add(PITCHER_2);
        playerList.add(CATCHER);
        playerList.add(BASE_1);
        playerList.add(BASE_2);
        playerList.add(BASE_3);
        playerList.add(SHORT_STOP);
        playerList.add(OUTFIELD_1);
        playerList.add(OUTFIELD_2);
        playerList.add(OUTFIELD_3);
        return playerList;
    }

    public int getSalaryTotal() {
        List<Player> players = getPlayers();
        int salary = 0;
        for (Player player : players) {
            salary += player.getSalary();
        }
        return salary;
    }

    public double getProjectedPointsTotal() {
        List<Player> players = getPlayers();
        double projectedPointsTotal = 0;
        for (Player player : players) {
            projectedPointsTotal += player.getProjectedPoints();
        }
        return projectedPointsTotal;
    }

    public boolean isValid() {
        boolean isValid = false;
        if (getSalaryTotal() <= getMaxSalary()) {
            List<Player> players = getPlayers();

            Set<Player> playerSet = new HashSet<>(players);
            if (players.size() == playerSet.size()) {
                List<Player> invalidPlayers = players.stream().filter(player -> player.getSalary() <= 0).collect(Collectors.toList());
                if (invalidPlayers.size() == 0) {
                    isValid = true;
                }
            }
        }
        return isValid;
    }

    @Override
    public boolean equals(Object object) {

        if (object == this)
            return true;
        if (!(object instanceof MLBRoster)) {
            return false;
        }

        MLBRoster roster = (MLBRoster) object;

        List<Player> playerList = getPlayers();
        List<Player> rosterList = roster.getPlayers();

        return playerList.containsAll(rosterList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(PITCHER_1, PITCHER_2, CATCHER, BASE_1, BASE_2, BASE_3, SHORT_STOP, OUTFIELD_1, OUTFIELD_2, OUTFIELD_3);
    }

    public static final Comparator<MLBRoster> PROJECTED_POINTS_DESC = (roster1, roster2) -> {
        if (roster1.getProjectedPointsTotal() < roster2.getProjectedPointsTotal())
            return 1;
        else if (roster2.getProjectedPointsTotal() < roster1.getProjectedPointsTotal())
            return -1;
        return 0;
    };
}
