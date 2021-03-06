package net.draycia.carbon.common.listeners.events;

import net.draycia.carbon.api.CarbonChat;
import net.draycia.carbon.api.CarbonChatProvider;
import net.draycia.carbon.api.events.ChatComponentEvent;
import net.draycia.carbon.api.events.ChatFormatEvent;
import net.draycia.carbon.api.events.misc.CarbonEvents;
import net.draycia.carbon.api.users.CarbonUser;
import net.draycia.carbon.api.users.PlayerUser;
import net.kyori.adventure.sound.Sound;
import net.kyori.event.PostOrders;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingHandler {

  @SuppressWarnings("method.invocation.invalid")
  public PingHandler() {
    final CarbonChat carbonChat = CarbonChatProvider.carbonChat();

    CarbonEvents.register(ChatComponentEvent.class, PostOrders.LAST, false, event -> {
      if (!carbonChat.carbonSettings().channelPings().enabled()) {
        return;
      }

      final CarbonUser target = event.target();

      if (!(target instanceof PlayerUser)) {
        return;
      }

      final String targetName = target.name();
      final String prefix = carbonChat.carbonSettings().channelPings().prefix();
      final boolean caseSensitive = carbonChat.carbonSettings().channelPings().caseSensitive();
      final String ping = prefix + targetName;

      if (caseSensitive) {
        if (!event.originalMessage().contains(ping)) {
          return;
        }
      } else {
        if (!event.originalMessage().toLowerCase().contains(ping.toLowerCase())) {
          return;
        }
      }

      final Sound sound = ((PlayerUser) target).pingOptions().pingSound();

      if (sound != null) {
        target.playSound(sound);
      } else {
        target.playSound(carbonChat.carbonSettings().channelPings().sound());
      }
    });

    CarbonEvents.register(ChatFormatEvent.class, PostOrders.LAST, false, event -> {
      if (!carbonChat.carbonSettings().channelPings().enabled()) {
        return;
      }

      final CarbonUser target = event.target();

      if (!(target instanceof PlayerUser)) {
        return;
      }

      final String targetName = target.name();
      final String prefix = carbonChat.carbonSettings().channelPings().prefix();
      final boolean caseSensitive = carbonChat.carbonSettings().channelPings().caseSensitive();
      final String ping = prefix + targetName;

      if (caseSensitive) {
        if (!event.message().contains(ping)) {
          return;
        }

        event.message(event.message().replace(ping,
          carbonChat.carbonSettings().channelPings().display()
            .replace("<ping>", ping)
            .replace("<name>", targetName)
        ));
      } else {
        if (!event.message().toLowerCase().contains(ping.toLowerCase())) {
          return;
        }

        event.message(this.replace(event.message(), ping,
          carbonChat.carbonSettings().channelPings().display()
            .replace("<ping>", ping)
            .replace("<name>", targetName)
        ));
      }
    });
  }

  private String replace(final @NonNull String input, final @NonNull CharSequence target,
                         final @NonNull CharSequence replacement) {
    return Pattern.compile("(?i)" + Pattern.quote(target.toString())).matcher(input)
      .replaceAll(Matcher.quoteReplacement(replacement.toString()));
  }

}
