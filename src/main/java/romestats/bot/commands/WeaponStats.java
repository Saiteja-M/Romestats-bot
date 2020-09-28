package romestats.bot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.examples.doc.Author;
import com.jagrosh.jdautilities.menu.Paginator;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.exceptions.PermissionException;
import romestats.bot.Config;
import romestats.bot.utils.TableStringBuilder;
import romestats.bot.utils.WeaponObj;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Author("Saiteja")
public class WeaponStats extends Command {

    private final int ITEMS_PER_PAGE = 20;
    private Paginator.Builder pbuilder;

    public WeaponStats(EventWaiter waiter) {
        this.name = "wstats";
        this.help = "shows weapon stats of the specified soldier";
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

        Unirest.get(url).asJson().ifSuccess((res) -> fetchWeaponStats(res.getBody().getObject(), event)).ifFailure((res) -> {
            try {
                String error = (String) res.getBody().getObject().getJSONObject("status").get("error_message");
                event.reactError();
                event.reply(error);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void fetchWeaponStats(JSONObject jsonResponse, CommandEvent event) {
        int page = 1;
        pbuilder.clearItems();

        JSONObject data = jsonResponse.getJSONObject("data");
        List<WeaponObj> weaponsData = new ArrayList<>();

        Config.KEY_CONFIG.getJSONArray("Weapons").forEach((config) -> {

            JSONObject json = new JSONObject(config.toString());

            String weapon = (String) json.get("Name");
            int kills = (data.has((String) json.get("Kills"))) ? data.getInt((String) json.get("Kills")) : 0;
            int hs = (data.has((String) json.get("Headshots"))) ? data.getInt((String) json.get("Headshots")) : 0;
            int shotsFired = (data.has((String) json.get("ShotsFired"))) ? data.getInt((String) json.get("ShotsFired")) : 0;
            int shotsHit = (data.has((String) json.get("ShotsHit"))) ? data.getInt((String) json.get("ShotsHit")) : 0;

            weaponsData.add(new WeaponObj(weapon, kills, hs, shotsFired, shotsHit));

            if (weaponsData.size() % ITEMS_PER_PAGE == 0) {
                this.buildTable(weaponsData);
                weaponsData.clear();
            }
        });

        this.buildTable(weaponsData);

        Paginator p = pbuilder.setColor(Color.BLUE)
                .setUsers(event.getAuthor())
                .setText("**Weapon Stats for \"__" + event.getArgs() + "__\"**")
                .build();
        event.reactSuccess();
        p.paginate(event.getChannel(), page);
    }

    private void buildTable(List<WeaponObj> weaponsData) {
        TableStringBuilder<WeaponObj> t = new TableStringBuilder<>();
        t.addColumn(" Weapon ", WeaponObj::getName);
        t.addColumn(" Kills ", WeaponObj::getKills);
        t.addColumn(" H/S ", WeaponObj::getHeadshots);
        t.addColumn(" Accuracy ", WeaponObj::getAccuracy);
        pbuilder.addItems("```" + t.createString(weaponsData) + "```");
    }

}
