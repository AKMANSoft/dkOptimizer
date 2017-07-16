import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    private static int POSITION_PLAYER_LIMIT = 10;
    private static int NUM_OF_BEST_ROSTERS = 20;

    public static void main(String args[]) {
        System.out.println("Loading best MLB Rosters at " + new Date());
        List<Player> players = new Parser().parse();
        List<MLBRoster> rosters = buildRosters(players);
        System.out.println("Completed loading best MLB Rosters at " + new Date());
    }

    private static List<MLBRoster> buildRosters(List<Player> players) {
        List<Player> pitcherList = players.stream()
                .filter(p -> p.getPosition() == Position.STARTING_PITCHER || p.getPosition() == Position.RELIEF_PITCHER)
                .limit(POSITION_PLAYER_LIMIT)
                .collect(Collectors.toList());

        List<Player> catcherList = players.stream()
                .filter(p -> p.getPosition() == Position.CATCHER)
                .limit(POSITION_PLAYER_LIMIT)
                .collect(Collectors.toList());

        List<Player> base1List = players.stream()
                .filter(p -> p.getPosition() == Position.BASE_1)
                .limit(POSITION_PLAYER_LIMIT)
                .collect(Collectors.toList());

        List<Player> base2List = players.stream()
                .filter(p -> p.getPosition() == Position.BASE_2)
                .limit(POSITION_PLAYER_LIMIT)
                .collect(Collectors.toList());

        List<Player> base3List = players.stream()
                .filter(p -> p.getPosition() == Position.BASE_3)
                .limit(POSITION_PLAYER_LIMIT)
                .collect(Collectors.toList());

        List<Player> shortStopList = players.stream()
                .filter(p -> p.getPosition() == Position.SHORT_STOP)
                .limit(POSITION_PLAYER_LIMIT)
                .collect(Collectors.toList());

        List<Player> outfieldList = players.stream()
                .filter(p -> p.getPosition() == Position.OUTFIELD)
                .limit(POSITION_PLAYER_LIMIT)
                .collect(Collectors.toList());

        MLBRoster roster = new MLBRoster();
        List<MLBRoster> rosters = new ArrayList<>();

        System.out.println("Brute force starting at " + new Date());
        for (Player pitcher1 : pitcherList) {
            roster.setPITCHER_1(pitcher1);
            for (Player pitcher2 : pitcherList) {
                roster.setPITCHER_2(pitcher2);
                for (Player catcher : catcherList) {
                    roster.setCATCHER(catcher);
                    for (Player base1 : base1List) {
                        roster.setBASE_1(base1);
                        for (Player base2 : base2List) {
                            roster.setBASE_2(base2);
                            for (Player base3 : base3List) {
                                roster.setBASE_3(base3);
                                for (Player shortStop : shortStopList) {
                                    roster.setSHORT_STOP(shortStop);
                                    for (Player outfield1 : outfieldList) {
                                        roster.setOUTFIELD_1(outfield1);
                                        for (Player outfield2 : outfieldList) {
                                            roster.setOUTFIELD_2(outfield2);
                                            for (Player outfield3 : outfieldList) {
                                                roster.setOUTFIELD_3(outfield3);
                                                rosters = addRoster(roster, rosters);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Completed brute force at " + new Date());

        return rosters;
    }

    private static List<MLBRoster> addRoster(MLBRoster roster, List<MLBRoster> rosters) {
        if (roster.isValid() && !rosters.contains(roster)) {
            rosters.add(new MLBRoster(roster));
            //rosters.sort(MLBRoster.PROJECTED_POINTS_DESC);
            while (rosters.size() > NUM_OF_BEST_ROSTERS) {
                rosters.remove(NUM_OF_BEST_ROSTERS);
            }
        }

        return rosters;
    }
}
