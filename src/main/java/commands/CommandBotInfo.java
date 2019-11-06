package commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import static core.Main.Embed;

public class CommandBotInfo implements Command {

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage(Embed.setDescription("Mit Haxis kannst du dir z.b. neue Features von Bots freischalten").setTitle("Die Haxi Währung").build()).queue();
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }

    @Override
    public String help() {
        return null;
    }
}
