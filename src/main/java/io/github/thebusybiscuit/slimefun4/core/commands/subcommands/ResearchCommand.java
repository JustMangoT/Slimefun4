package io.github.thebusybiscuit.slimefun4.core.commands.subcommands;

import java.util.Optional;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.thebusybiscuit.cscorelib2.players.PlayerList;
import io.github.thebusybiscuit.slimefun4.core.commands.SlimefunCommand;
import io.github.thebusybiscuit.slimefun4.core.commands.SubCommand;
import me.mrCookieSlime.Slimefun.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.Objects.Research;
import me.mrCookieSlime.Slimefun.api.PlayerProfile;

public class ResearchCommand extends SubCommand {
	
	private static final String PLACEHOLDER_PLAYER = "%player%";
	private static final String PLACEHOLDER_RESEARCH = "%research%";

	public ResearchCommand(SlimefunPlugin plugin, SlimefunCommand cmd) {
		super(plugin, cmd);
	}

	@Override
	public String getName() {
		return "research";
	}
	
	@Override
	protected String getDescriptionPath() {
		return "commands.research.description";
	}

	@Override
	public void onExecute(CommandSender sender, String[] args) {
		if (args.length == 3) {
			if (sender.hasPermission("slimefun.cheat.researches") || !(sender instanceof Player)) {
				Optional<Player> player = PlayerList.findByName(args[1]);
				
				if (player.isPresent()) {
					Player p = player.get();
					
					PlayerProfile.get(p, profile -> {
						if (args[2].equalsIgnoreCase("all")) {
							for (Research res : SlimefunPlugin.getRegistry().getResearches()) {
								if (!profile.hasUnlocked(res)) SlimefunPlugin.getLocal().sendMessage(sender, "messages.give-research", true, msg -> msg.replace(PLACEHOLDER_PLAYER, p.getName()).replace(PLACEHOLDER_RESEARCH, res.getName()));
								res.unlock(p, true);
							}
						}
						else if (args[2].equalsIgnoreCase("reset")) {
							for (Research res : SlimefunPlugin.getRegistry().getResearches()) {
								profile.setResearched(res, false);
							}
							SlimefunPlugin.getLocal().sendMessage(p, "commands.research.reset", true, msg -> msg.replace(PLACEHOLDER_PLAYER, args[1]));
						}
						else {
							Research research = null;
							for (Research res : SlimefunPlugin.getRegistry().getResearches()) {
								if (res.getName().toUpperCase().replace(' ', '_').equalsIgnoreCase(args[2])) {
									research = res;
									break;
								}
							}
							
							if (research != null) {
								research.unlock(p, true);
								final Research r = research;
								SlimefunPlugin.getLocal().sendMessage(sender, "messages.give-research", true, msg -> msg.replace(PLACEHOLDER_PLAYER, p.getName()).replace(PLACEHOLDER_RESEARCH, r.getName()));
							}
							else {
								SlimefunPlugin.getLocal().sendMessage(sender, "messages.not-valid-research", true, msg -> msg.replace(PLACEHOLDER_RESEARCH, args[2]));
							}
						}
					});
				}
				else {
					SlimefunPlugin.getLocal().sendMessage(sender, "messages.not-online", true, msg -> msg.replace(PLACEHOLDER_PLAYER, args[1]));
				}
			}
			else SlimefunPlugin.getLocal().sendMessage(sender, "messages.no-permission", true);
		}
		else {
			SlimefunPlugin.getLocal().sendMessage(sender, "messages.usage", true, msg -> msg.replace("%usage%", "/sf research <Player> <all/reset/Research>"));
		}
	}

}
