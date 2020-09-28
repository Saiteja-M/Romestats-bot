package romestats.bot.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import net.dv8tion.jda.api.EmbedBuilder;
import romestats.bot.Config;
import romestats.bot.utils.Helper;

public class SoldierStats extends Command {

    private final String UNITS_ROMESTATS = "https://unitygamers.net/romestats/soldier.php?name=";
    private final String RANKS_URL = "https://unitygamers.net/romestats/assets/ranks/";

    public SoldierStats() {
        this.name = "stats";
        this.help = "shows rome stats of the specified soldier";
        this.arguments = "<playername>";
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent event) {
        if (event.getArgs().isEmpty()) {
            event.reactWarning();
            event.reply("You didn't give me any choices!```" + event.getClient().getPrefix() + this.name + " " + this.arguments + "```");
            return;
        }
        String url = Config.STATS_BASEURL + event.getArgs() + "/stats";
        Unirest.get(url).asJson().ifSuccess((res) -> {

            JSONObject data = res.getBody().getObject().getJSONObject("data");

            int rank = (data.has("rank")) ? data.getInt("rank") : 0;
            int score = (data.has("score")) ? data.getInt("score") : 0;
            String rankName = Helper.getRankName(score);
            int timeSpent = (data.has("time")) ? data.getInt("time") : 0;

            int games = (data.has("games")) ? data.getInt("games") : 0;
            int wins = (data.has("wins")) ? data.getInt("wins") : 0;
            int losses = (data.has("losses")) ? data.getInt("losses") : 0;

            int kills = (data.has("kills")) ? data.getInt("kills") : 0;
            int deaths = (data.has("deaths")) ? data.getInt("deaths") : 0;
            int dogtags = (data.has("dogt")) ? data.getInt("dogt") : 0;

            int teamkills = (data.has("teamkills")) ? data.getInt("teamkills") : 0;
            int skill = (data.has("elo")) ? data.getInt("elo") : 0;
            float kd = (float) kills / (float) deaths;

            int teamScore = (data.has("sc_team")) ? data.getInt("sc_team") : 0;
            int squadScore = (data.has("sc_squad")) ? data.getInt("sc_squad") : 0;
            int vehicleScore = (data.has("sc_vehicle")) ? data.getInt("sc_vehicle") : 0;

            int objScore = (data.has("sc_objective")) ? data.getInt("sc_objective") : 0;
            int awardScore = (data.has("sc_award")) ? data.getInt("sc_award") : 0;
            int bonusScore = (data.has("sc_bonus")) ? data.getInt("sc_bonus") : 0;

            String rankImage = RANKS_URL + rank + ".png";

            EmbedBuilder embed = new EmbedBuilder();

            embed.setTitle("Romestats - " + event.getArgs(), UNITS_ROMESTATS + event.getArgs()).setThumbnail(rankImage)
                    .addField("Rank", rankName, true).addField("Score", score + "", true).addField("Time Spent", (timeSpent / 23600) + " hrs", true)
                    .addField("Games", games + "", true).addField("Wins", wins + "", true).addField("Losses", losses + "", true)
                    .addField("Kills", kills + "", true).addField("Deaths", deaths + "", true).addField("Dogtags", dogtags + "", true)
                    .addField("Teamkills", teamkills + "", true).addField("Skill", skill + "", true).addField("K/D", kd + "", true)
                    .addField("Team Score", teamScore + "", true).addField("Squad Score", squadScore + "", true).addField("Vehicle Score", vehicleScore + "", true)
                    .addField("Objective Score", objScore + "", true).addField("Award Score", awardScore + "", true).addField("Bonus Score", bonusScore + "", true);

            event.reply(embed.build());

        }).ifFailure((res) -> {
            try {
                String error = (String) res.getBody().getObject().getJSONObject("status").get("error_message");
                event.reactError();
                event.reply(error);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

}
