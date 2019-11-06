package commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import stuff.SECRETS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static core.Main.*;

public class CommandBank implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length<1) {
            event.getTextChannel().sendMessage(
                    Embed.setDescription("Hier kannst du mit folgenden Commands dein Geld in die Haxi Währung tauschen :D\n" +
                            "Mit der Haxi Währung kannst bei den anderen Bots von Hax Dinge kaufen! Mehr informationen mit ``"+SECRETS.PREFIX+"botinfo``\n" +
                            "``"+ SECRETS.PREFIX + "bank deposit <Haxis>`` - 1.000.000 Cookies/1 Haxi").setTitle("Bank").build()).queue();
        }
        try {
            if ("deposit".equals(args[0].toLowerCase())) {
                Connection con = DriverManager.getConnection(url + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
                PreparedStatement pst = con.prepareStatement("SELECT * FROM `user` WHERE `ID` LIKE '" + event.getAuthor().getId() + "' ");
                ResultSet rs = pst.executeQuery();
                if (!rs.next()) {
                    event.getTextChannel().sendMessage(Embed.setDescription("Du musst erst etwas schreiben!").setTitle("Fehler").build()).queue();
                } else {
                    Preis = 1000000 * Integer.parseInt(args[1]);
                    if (rs.getInt(2) <= Preis - 1) {
                        event.getTextChannel().sendMessage(
                                Embed.setDescription("Du hast zu wenig Cookies. Schreibe mehr!").setTitle("Fehler").build()).queue();
                    } else {
                        Temp = rs.getInt(2) - Preis;
                        con = DriverManager.getConnection(url + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
                        pst = con.prepareStatement("UPDATE `user` SET `Cookies`=" + Temp + " WHERE ID=" + event.getAuthor().getId());
                        pst.executeUpdate();
                        //deposit to Bank
                        con = DriverManager.getConnection(urlbank + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
                        pst = con.prepareStatement("SELECT * FROM `user` WHERE `ID` LIKE '" + event.getAuthor().getId() + "' ");
                        rs = pst.executeQuery();
                        if (!rs.next()) {
                            event.getTextChannel().sendMessage(Embed.setDescription("Du musst erst etwas schreiben!").setTitle("Fehler").build()).queue();
                        } else {
                            Temp = rs.getInt(2) + Integer.parseInt(args[1]);
                            pst = con.prepareStatement("UPDATE `user` SET `Haxis`=" + Temp + " WHERE ID=" + event.getAuthor().getId());
                            pst.executeUpdate();
                            event.getTextChannel().sendMessage
                                    (Embed.setDescription("Erfolgreich getauscht!").setTitle("Transaktion erfolgreich!").build()).queue();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
