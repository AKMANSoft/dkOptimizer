import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Parser {

    private List<Player> playerList = new ArrayList<>();

    public List<Player> parse() {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://rotogrinders.com/lineup-builder/mlb?site=draftkings").maxBodySize(0).timeout(60 * 1000).get();
        } catch (IOException e) {
            System.out.println(Parser.class.getName() + " - " + e.getLocalizedMessage());
        }

        if (doc != null) {
            Elements scriptTags = doc.select("script");
            for (Element scriptTag : scriptTags) {
                if (scriptTag.toString().contains("var data = ")) {
                    String trimmedElement = scriptTag.toString()
                            .substring(scriptTag.toString().indexOf("var data = "))
                            .substring("var data = ".length());
                    JSONObject line = new JSONObject(trimmedElement.substring(0, trimmedElement.indexOf("\n")));
                    JSONObject playersObject = new JSONObject(line.get("players").toString());
                    try {
                        parsePlayers(new JSONObject(playersObject.get("mlb_hitter").toString()));
                        parsePlayers(new JSONObject(playersObject.get("mlb_pitcher").toString()));
                    } catch (JSONException e) {
                        System.out.println(Parser.class.getName() + " - " + e.getLocalizedMessage());
                    }
                }
            }
        }

        return playerList;
    }

    private void parsePlayers(JSONObject playersObject) {
        Iterator<?> keys = playersObject.keys();
        while (keys.hasNext()) {
            Player player = new Player();

            JSONObject athleteObject = new JSONObject(playersObject.get((String) keys.next()).toString());
            JSONObject athleteDataObject = new JSONObject(athleteObject.get("data").toString());
            JSONObject playerObject = new JSONObject(athleteDataObject.get("player").toString());
            JSONObject playerDataObject = new JSONObject(playerObject.get("data").toString());
            player.setName(playerDataObject.get("first_name").toString() + " " + playerDataObject.get("last_name").toString());

            JSONObject scheduleObject = new JSONObject(athleteDataObject.get("schedule").toString());
            JSONObject scheduleDataObject = new JSONObject(scheduleObject.get("data").toString());

            JSONObject fptsObject = new JSONObject(athleteDataObject.get("fpts").toString());
            player.setProjectedPoints(Double.parseDouble(fptsObject.get("20").toString()));

            JSONObject salariesObject;
            JSONArray salariesCollection;
            try {
                salariesObject = new JSONObject(scheduleDataObject.get("salaries").toString());
                salariesCollection = salariesObject.getJSONArray("collection");
            } catch (JSONException ignored) {
                salariesCollection = new JSONArray();
            }

            List<Position> positions = new ArrayList<>();
            for (Object salary : salariesCollection) {
                JSONObject salaryObject = new JSONObject(salary.toString());
                JSONObject salaryData = new JSONObject(salaryObject.get("data").toString());
                JSONArray importArray = null;
                try {
                    importArray = salaryData.getJSONArray("import_id");
                } catch (JSONException ignored) {
                }
                if (importArray != null) {
                    JSONObject importObject = new JSONObject(importArray.get(0).toString());
                    player.setSalary(Integer.parseInt(importObject.get("salary").toString()));

                    String[] positionAbbreviations = importObject.get("position").toString().split("/");
                    for (String positionAbbreviation : positionAbbreviations) {
                        Position position = Position.findByAbbreviationAndLeague(positionAbbreviation);
                        if (position != null) {
                            positions.add(position);
                        }
                    }
                }
            }

            if (positions.size() > 0) {
                for (Position position : positions) {
                    Player tempPlayer = new Player(player);
                    tempPlayer.setPosition(position);
                    playerList.add(tempPlayer);
                }
            }
        }
    }
}
