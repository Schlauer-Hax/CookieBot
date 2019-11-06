package listeners;

import core.Main;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.*;

import static core.Main.*;

public class MessageListener extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (!event.getMessage().getContentRaw().startsWith("-") && !event.getAuthor().isBot()) {
            try {
                Connection con = DriverManager.getConnection(url + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
                PreparedStatement pst = con.prepareStatement("SELECT * FROM `user` WHERE `ID` LIKE '" + event.getAuthor().getId() + "' ");
                ResultSet rs = pst.executeQuery();
                if (!rs.next()) {
                    con = DriverManager.getConnection(url + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
                    pst = con.prepareStatement("INSERT INTO `user` (`ID`, `Cookies`,`Click`) VALUES ('" + event.getAuthor().getId() + "', '0','1')");
                    pst.execute();
                } else {
                    pst = con.prepareStatement("SELECT * FROM `user` WHERE `ID` LIKE '" + event.getAuthor().getId() + "' ");
                    rs = pst.executeQuery();
                    if (rs.next()) {
                        Main.TempCookies = rs.getInt(2);
                        TempClick = rs.getInt(3);
                    }
                    Main.TempCookies= TempCookies+TempClick;
                    pst = con.prepareStatement("UPDATE `user` SET `Cookies`=" + Main.TempCookies+" WHERE ID=" + event.getAuthor().getId());
                    pst.executeUpdate();

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
