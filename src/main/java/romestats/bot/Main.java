package romestats.bot;

import java.awt.Color;

import javax.security.auth.login.LoginException;

import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.examples.command.AboutCommand;
import com.jagrosh.jdautilities.examples.command.PingCommand;
import com.jagrosh.jdautilities.examples.command.ShutdownCommand;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import romestats.bot.commands.Servers;
import romestats.bot.commands.Stats;
import romestats.bot.commands.VehicleStats;
import romestats.bot.commands.WeaponStats;

public class Main {

	public static void main(String[] args) throws LoginException {
        EventWaiter waiter = new EventWaiter();
        CommandClientBuilder client = new CommandClientBuilder();

        client.useDefaultGame()
        	.setOwnerId(ConfigLoader.get("owner_id"))
        	.setPrefix(ConfigLoader.get("prefix"))
        	.setEmojis("✅", "⚠️", "❌")
        	.addCommands(
                new AboutCommand(Color.BLUE, "RomeStats API Discord Bot",
                        new String[]{"Display servers online is Emulator Nexus Backend","Fetch player stats","Fetch weapon stats","Fetch vehicle stats"},
                        new Permission[]{Permission.ADMINISTRATOR}),
                new PingCommand(),
                new ShutdownCommand(),
                new Servers(waiter),
                new WeaponStats(waiter),
                new VehicleStats(waiter),
                new Stats());

        new JDABuilder(AccountType.BOT)
                .setToken(ConfigLoader.get("bot_token"))
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .setActivity(Activity.playing("loading..."))
                .addEventListeners(waiter, client.build())
                .build();
	}
}
