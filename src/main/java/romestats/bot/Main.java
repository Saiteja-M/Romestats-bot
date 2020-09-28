package romestats.bot;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.examples.command.PingCommand;
import com.jagrosh.jdautilities.examples.command.ShutdownCommand;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import romestats.bot.commands.Servers;
import romestats.bot.commands.SoldierStats;
import romestats.bot.commands.VehicleStats;
import romestats.bot.commands.WeaponStats;

import javax.security.auth.login.LoginException;

public class Main {

    public static void main(String[] args) throws LoginException {
        EventWaiter waiter = new EventWaiter();
        CommandClientBuilder client = new CommandClientBuilder();

        client.useDefaultGame()
                .setOwnerId(Config.get("owner_id"))
                .setPrefix(Config.get("prefix"))
                .setEmojis("✅", "⚠️", "❌")
                .addCommands(
                        new PingCommand(),
                        new ShutdownCommand(),
                        new Servers(waiter),
                        new WeaponStats(waiter),
                        new VehicleStats(waiter),
                        new SoldierStats());

        new JDABuilder(AccountType.BOT)
                .setToken(Config.get("bot_token"))
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.playing("loading..."))
                .addEventListeners(waiter, client.build())
                .build();

    }

}
