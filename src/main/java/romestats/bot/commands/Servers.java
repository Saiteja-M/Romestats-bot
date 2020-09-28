package romestats.bot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.examples.doc.Author;
import com.jagrosh.jdautilities.menu.Paginator;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONException;
import net.dv8tion.jda.api.exceptions.PermissionException;
import org.json.JSONArray;
import org.json.JSONObject;
import romestats.bot.Config;

import java.awt.*;
import java.util.concurrent.TimeUnit;

@Author("Saiteja")
public class Servers extends Command {


    private Paginator.Builder pbuilder;

    public Servers(EventWaiter waiter) {
        this.name = "servers";
        this.help = "shows the list of servers online";

        this.pbuilder = new Paginator.Builder();
        pbuilder = new Paginator.Builder()
                .setColumns(1)
                .setItemsPerPage(2)
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

        Unirest.get(Config.SERVERS_BASEURL)
                .asString()
                .ifSuccess((response -> this.getServersList(response, event)));

    }

    private void getServersList(HttpResponse<String> response, CommandEvent event) {

        int page = 1;
        pbuilder.clearItems();

        try {
            JSONObject json = new JSONObject(response.getBody());
            JSONArray dataArray = json.getJSONArray("data");

            dataArray.forEach(data -> {
                JSONObject jNode = new JSONObject(data.toString());
                String info = "**HostName:** " + jNode.get("Hostname") + "\n"
                        + "**RemoteIP:** " + jNode.get("RemoteIP") + ":" + jNode.get("RemotePort") + "\n"
                        + "**Region:** " + jNode.get("Region") + "\n"
                        + "**Gamemode:** " + jNode.get("Region") + "\n"
                        + "**Level:** " + jNode.get("Level") + "\n"
                        + "**Players:** " + jNode.get("ActivePlayers") + "/" + jNode.get("MaxPlayers") + "\n"
                        + "**Spectators:** " + jNode.get("SpectatorCount") + "/" + jNode.get("MaxSpectators") + "\n"
                        + "**HardCore:** " + ((jNode.getBoolean("Hardcore")) ? "yes" : "no") + "**Password:** " + ((jNode.getBoolean("HasPassword")) ? "yes" : "no") + "\n";
                pbuilder.addItems(info);

            });
            Paginator p = pbuilder.setColor(Color.BLUE)
                    .setUsers(event.getAuthor())
                    .setText("**__Rome Servers__** [" + dataArray.length() + "]").build();

            p.paginate(event.getChannel(), page);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
