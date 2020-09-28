package romestats.bot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.menu.Paginator;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.exceptions.PermissionException;
import romestats.bot.Config;
import romestats.bot.utils.TableStringBuilder;
import romestats.bot.utils.VehicleObj;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VehicleStats extends Command {

    private final int ITEMS_PER_PAGE = 20;

    private Paginator.Builder pbuilder;

    public VehicleStats(EventWaiter waiter) {
        this.name = "vstats";
        this.help = "shows vehicle stats of the specified soldier";
        this.arguments = "<playername>";

        this.pbuilder = new Paginator.Builder();
        pbuilder = new Paginator.Builder()
                .setColumns(1)
                .setItemsPerPage(1)
                .showPageNumbers(true)
                .waitOnSinglePage(false)
                .useNumberedItems(false)
                .setFinalAction(m -> {
                    try {
                        m.clearReactions().queue();
                    } catch (PermissionException ex) {
                        m.delete().queue();
                    }
                }).setEventWaiter(waiter).setTimeout(1, TimeUnit.MINUTES);
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().isEmpty()) {
            event.reactWarning();
            event.reply("You didn't give me any choices!```" + event.getClient().getPrefix() + this.name + " " + this.arguments + "```");
            return;
        }
        String url = Config.STATS_BASEURL + event.getArgs() + "/stats";

        Unirest.get(url).asJson().ifSuccess((res) -> fetchVehicleStats(res.getBody().getObject(), event)).ifFailure((res) -> {
            try {
                String error = (String) res.getBody().getObject().getJSONObject("status").get("error_message");
                event.reactError();
                event.reply(error);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void fetchVehicleStats(JSONObject jsonResponse, CommandEvent event) {
        int page = 1;
        pbuilder.clearItems();

        JSONObject data = jsonResponse.getJSONObject("data");
        List<VehicleObj> vehicleData = new ArrayList<>();

        Config.KEY_CONFIG.getJSONArray("Vehicles").forEach((config) -> {

            JSONObject json = new JSONObject(config.toString());

            String weapon = (String) json.get("Name");
            int kills = (data.has((String) json.get("Kills"))) ? data.getInt((String) json.get("Kills")) : 0;
            int roadKills = (data.has((String) json.get("RoadKills"))) ? data.getInt((String) json.get("RoadKills")) : 0;
            int distanceDriven = (data.has((String) json.get("DistanceDriven"))) ? data.getInt((String) json.get("DistanceDriven")) : 0;

            vehicleData.add(new VehicleObj(weapon, kills, roadKills, distanceDriven));

            if (vehicleData.size() % ITEMS_PER_PAGE == 0) {
                this.buildTable(vehicleData);
                vehicleData.clear();
            }
        });

        this.buildTable(vehicleData);

        Paginator p = pbuilder.setColor(Color.BLUE)
                .setUsers(event.getAuthor())
                .setText("**Weapon Stats for \"__" + event.getArgs() + "__\"**")
                .build();
        event.reactSuccess();
        p.paginate(event.getChannel(), page);
    }

    private void buildTable(List<VehicleObj> weaponsData) {
        TableStringBuilder<VehicleObj> t = new TableStringBuilder<>();
        t.addColumn(" Name ", VehicleObj::getName);
        t.addColumn(" Kills ", VehicleObj::getKills);
        t.addColumn(" RoadKills ", VehicleObj::getRoadKills);
        t.addColumn(" Distance[m] ", VehicleObj::getDistanceDriven);
        pbuilder.addItems("```" + t.createString(weaponsData) + "```");
    }

}
