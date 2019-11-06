package listeners;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReadyListener extends ListenerAdapter {

    public void onReady(ReadyEvent event) {

        StringBuilder out = new StringBuilder("\nThe Bot is running on following Servers: \n");

        for (Guild g : event.getJDA().getGuilds()   ) {
            out.append(g.getName()).append(" {").append(g.getId()).append("} \n");
        }

        System.out.println(out);
    }

}
