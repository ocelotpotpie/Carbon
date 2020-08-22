package net.draycia.carbon.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Dependency;
import net.draycia.carbon.CarbonChat;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

@CommandAlias("chatreload|creload")
@CommandPermission("carbonchat.reload")
public class ChatReloadCommand extends BaseCommand {

    @Dependency
    private CarbonChat carbonChat;

    @Default
    public void baseCommand(CommandSender sender) {
        carbonChat.reloadConfig();

        carbonChat.getChannelManager().reload();

        Component message = carbonChat.getAdventureManager().processMessage(carbonChat.getLanguage().getString("reloaded"),
                "br", "\n");

        carbonChat.getAdventureManager().getAudiences().audience(sender).sendMessage(message);

        // TODO: Ensure the command list is updated when reloading.
        // If new channels are added, their commands should appear to players.
    }

}