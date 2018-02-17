package commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import core.Main;
import stuff.SECRETS;

public class helpcommand implements Command{


    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage(Main.Embed.setDescription(
                "Version: " + SECRETS.VERSION + "\nListe der Commands:\n" +
                "``" + SECRETS.PREFIX + "shop`` - Da kannst du dir mit Cookies Sachen wie Clicker und Farmen kaufen\n" +
                "``" + SECRETS.PREFIX + "stats`` - Die Stats vom Bot :D\n" +
                "``" + SECRETS.PREFIX + "bank`` - Cookies in Haxis umtauschen\n" +
                "``" + SECRETS.PREFIX + "botinfo`` - Was man mit Haxis machen kann\n")
                .setTitle("Help - Hilfe").build()).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
