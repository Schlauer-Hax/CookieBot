package commands;

import core.Main;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static core.Main.*;

public class stats implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        try {
            Connection con = DriverManager.getConnection(url + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
            PreparedStatement pst = con.prepareStatement("SELECT * FROM `user` WHERE `ID` LIKE '"+event.getAuthor().getId()+"'");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                TempCookies = rs.getInt(2);
                TempClick = rs.getInt(3);
            } else {
                event.getTextChannel().sendMessage((Message) Embed.setDescription("Du musst zuerst eine Nachricht schreiben!").setTitle("Fehler").build()).queue();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        event.getTextChannel().sendMessage(
                Main.Embed.setDescription("Der Bot ist auf " + event.getJDA().getGuilds().size() +" Servern!" +
                        "\nCookies: " + TempCookies + "\nCookies pro Nachricht: " + TempClick).setTitle("Deine Stats").build()
        ).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
