package core;

import commands.*;
import listeners.MessageListener;
import listeners.CommandListener;
import listeners.ReadyListener;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import stuff.SECRETS;

import java.awt.*;

public class Main {

    public static String url = "jdbc:mysql://localhost/cookiebot";
    public static String urlbank = "jdbc:mysql://localhost/bank";
    public static String user = "root";
    public static String password = "root";

    public static int TempCookies;
    public static int TempClick;
    //Shop
    public static int Preis;
    public static int UserC;
    public static int ShopClick;
    public static String ShopName;
    //stats
    public static long serverlong;
    //bank
    public static int Temp;

    public static EmbedBuilder Embed = new EmbedBuilder()
            .setColor(Color.CYAN)
            .setFooter("© Cookiebot v." + SECRETS.VERSION, "http://www.baggerstation.de/testseite/CookieBot/cookie.png");

    public static void main(String[] Args) {
        //Click
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(SECRETS.TOKEN);
        builder.setAutoReconnect(true);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing(SECRETS.GAME));

        builder.addEventListeners(new MessageListener(), new CommandListener(), new ReadyListener());

        CommandHandler.commands.put("help", new CommandHelp());
        CommandHandler.commands.put("shop", new CommandShop());
        CommandHandler.commands.put("stats", new CommandStats());
        CommandHandler.commands.put("bank", new CommandBank());
        CommandHandler.commands.put("botinfo", new CommandBotInfo());

        try {
            JDA jda = builder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
