package net.draycia.carbon.api.channels;

import net.draycia.carbon.api.users.CarbonUser;
import net.kyori.adventure.audience.ForwardingAudience;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface WhisperChannel extends ChatChannel, ForwardingAudience.Single {

  @NonNull CarbonUser sender();

  /**
   * The recipient for this channel, where messages are sent to.
   * @return The channel recipient.
   */
  @Override
  @NonNull CarbonUser audience();

  @Override
  default @NonNull String name() {
    return "Whisper";
  }

  @Override
  default @NonNull String key() {
    return "whisper";
  }

}
