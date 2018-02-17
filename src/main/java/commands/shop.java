package commands;

import core.Main;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import stuff.SECRETS;

import java.sql.*;

import static core.Main.*;

public class shop implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args.length < 1) {
            event.getTextChannel().sendMessage(
                    Embed.setDescription("Du kannst sachen kaufen mit ``" + SECRETS.PREFIX + "shop buy <ITEM> [Number]" +
                            "``\nWelche Sachen es gibt erfährst du mit ``" + SECRETS.PREFIX + "shop list``\nMehr gibt es nicht zu erklären! Viel Spaß im Shop!")
                            .setTitle("Willkommen im Shop!").build()
            ).queue();
        }
        try {

            switch (args[0].toLowerCase()) {
                case "buy":
                    try {
                        Connection con = DriverManager.getConnection(Main.url + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", Main.user, Main.password);
                        PreparedStatement pst = con.prepareStatement("SELECT * FROM `shop` WHERE `Name` LIKE '"+args[1]+"'");
                        ResultSet rs = pst.executeQuery();

                        if (!rs.next()) {
                            event.getTextChannel().sendMessage(
                                    (Message) Embed.setDescription("Huups: du hast etwas falsch eingegeben oder den Artikel gibt es nicht!").setTitle("Fehler").build()).queue();
                        } else {
                            Preis=rs.getInt(2);
                            ShopClick=rs.getInt(3);
                            ShopName=rs.getString(1);
                            pst = con.prepareStatement("SELECT * FROM `user` WHERE `ID` LIKE '"+event.getAuthor().getId()+"' ");
                            rs = pst.executeQuery();
                            if (!rs.next()) {
                                event.getTextChannel().sendMessage((Message) Embed.setDescription("Du musst zuerst eine Nachricht schreiben!").setTitle("Fehler").build()).queue();
                            } else {
                                UserC=rs.getInt(2);
                                TempClick=rs.getInt(3);
                                try {
                                    Preis= Preis*Integer.parseInt(args[2]);
                                } catch (ArrayIndexOutOfBoundsException e) {
                                }

                                if (!(UserC >=Preis)) {
                                    event.getTextChannel().sendMessage(
                                    Embed.setDescription("Du hast zuwenig Cookies. schreibe mehr!").setTitle("Fehler").build()).queue();
                                } else {
                                    try {


                                        ShopClick = ShopClick * Integer.parseInt(args[2]);
                                    }catch (ArrayIndexOutOfBoundsException e) {}
                                    TempClick=TempClick+ShopClick;
                                    UserC=UserC-Preis;
                                    pst = con.prepareStatement("UPDATE `user` SET `Cookies`="+Main.UserC+" WHERE ID="+event.getAuthor().getId());
                                    pst.executeUpdate();
                                    pst = con.prepareStatement("UPDATE `user` SET `Click`="+TempClick+" WHERE ID="+event.getAuthor().getId());
                                    pst.executeUpdate();
                                    System.out.println(event.getAuthor().getName()+" hat gerade ein/en " + ShopName +"gekauft!");
                                    event.getTextChannel().sendMessage(Embed.setDescription("Du hast dir "+args[2]+" "+ShopName+" gekauft!").setTitle("Kauf abgeschlossen!").build()).queue();
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case "list":
                    event.getTextChannel().sendMessage(
                            Embed.setDescription("Hier eine Liste mit dem was du kaufen kannst:\n" +
                                    "``Raspberry`` - 100 Cookies - 1 Cookies/Message\n" +
                                    "``Laptop`` - 10.000 - 10 Cookies/Message\n" +
                                    "``Prozessor`` - 1.000.000 - 100 Cookies/Message\n" +
                                    "``Computer`` - 100.000.000 - 1000 Cookies/Message\n" +
                                    "Bald kommen noch mehr dazu!")
                                    .setTitle("Was kann ich kaufen?").build()
                    ).queue();
                    break;
            }




        } catch (ArrayIndexOutOfBoundsException e) {}



    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
