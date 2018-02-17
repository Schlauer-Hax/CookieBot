package listeners;

import core.Main;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class readyListener extends ListenerAdapter {

    public void onReady(ReadyEvent event) {

        String out = "\nThis Bot is running on following Servers: \n";

        for (Guild g : event.getJDA().getGuilds()   ) {
            out += g.getName() + " {" + g.getId() + "} \n";
        }

        System.out.println(out);
    }

}
