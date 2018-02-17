package core;

import commands.*;
import listeners.MessageRecieve;
import listeners.commandListener;
import listeners.readyListener;
import net.dv8tion.jda.core.*;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import stuff.SECRETS;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.sql.*;

public class Main {

    public static String url = "jdbc:mysql://localhost/cookiebot";
    public static String urlbank = "jdbc:mysql://localhost/bank";
    public static String user = "root";
    public static String password = "root";

    //Click
    public static JDABuilder builder;
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
        builder = new JDABuilder(AccountType.BOT);
        builder.setToken(SECRETS.TOKEN);
        builder.setAutoReconnect(true);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setGame(Game.of(Game.GameType.DEFAULT, SECRETS.GAME));

        builder.addEventListener(new MessageRecieve());
        builder.addEventListener(new commandListener());
        builder.addEventListener(new readyListener());

        commandHandler.commands.put("help", new helpcommand());
        commandHandler.commands.put("shop", new shop());
        commandHandler.commands.put("stats", new stats());
        commandHandler.commands.put("bank", new bank());
        commandHandler.commands.put("botinfo", new botinfo());

        try {
            JDA jda = builder.buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
