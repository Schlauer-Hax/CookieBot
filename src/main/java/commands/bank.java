package commands;

import core.Main;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import stuff.SECRETS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static core.Main.*;

public class bank implements Command {
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


            switch (args[0].toLowerCase()) {
                case "deposit":
                    Connection con = DriverManager.getConnection(url + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
                    PreparedStatement pst = con.prepareStatement("SELECT * FROM `user` WHERE `ID` LIKE '"+event.getAuthor().getId()+"' ");
                    ResultSet rs = pst.executeQuery();
                    if (!rs.next()) {
                        event.getTextChannel().sendMessage(Embed.setDescription("Du musst erst etwas schreiben!").setTitle("Fehler").build()).queue();
                    } else {
                        Preis = 1000000 * Integer.parseInt(args[1]);
                        if (rs.getInt(2) <= Preis-1) {
                            event.getTextChannel().sendMessage(
                                    Embed.setDescription("Du hast zuwenig Cookies. schreibe mehr!").setTitle("Fehler").build()).queue();
                        } else {
                            Temp=rs.getInt(2)-Preis;
                            con = DriverManager.getConnection(url + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
                            pst = con.prepareStatement("UPDATE `user` SET `Cookies`="+ Temp +" WHERE ID="+event.getAuthor().getId());
                            pst.executeUpdate();
                            //deposit to Bank
                            con = DriverManager.getConnection(urlbank + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
                            pst = con.prepareStatement("SELECT * FROM `user` WHERE `ID` LIKE '"+event.getAuthor().getId()+"' ");
                            rs = pst.executeQuery();
                            if (!rs.next()) {
                                System.out.println("Debug");
                                event.getTextChannel().sendMessage(Embed.setDescription("Du musst erst etwas schreiben!").setTitle("Fehler").build()).queue();
                            } else {
                                System.out.println("Debug2");
                                Temp=rs.getInt(2)+Integer.parseInt(args[1]);
                                System.out.println("Debug5");
                                pst = con.prepareStatement("UPDATE `user` SET `Haxis`="+ Temp +" WHERE ID="+event.getAuthor().getId());
                                System.out.println("DEbug4");
                                pst.executeUpdate();
                                System.out.println("Debug3");
                                event.getTextChannel().sendMessage
                                        (Embed.setDescription("Erfolgreich getauscht!").setTitle("Transaktion erfolgreich!").build()).queue();
                            }
                        }
                    }
                    break;
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
