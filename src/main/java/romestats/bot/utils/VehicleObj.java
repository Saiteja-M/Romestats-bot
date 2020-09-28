package romestats.bot.utils;

import com.jagrosh.jdautilities.examples.doc.Author;

@Author("Saiteja")
public class VehicleObj {

    private String name;
    private int kills;
    private int roadKills;
    private int distanceDriven;


    public VehicleObj(String name, int kills, int roadKills, int distanceDriven) {
        super();
        this.name = name;
        this.kills = kills;
        this.roadKills = roadKills;
        this.distanceDriven = distanceDriven;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getRoadKills() {
        return roadKills;
    }

    public void setRoadKills(int roadKills) {
        this.roadKills = roadKills;
    }

    public int getDistanceDriven() {
        return distanceDriven;
    }

    public void setDistanceDriven(int distanceDriven) {
        this.distanceDriven = distanceDriven;
    }


}
