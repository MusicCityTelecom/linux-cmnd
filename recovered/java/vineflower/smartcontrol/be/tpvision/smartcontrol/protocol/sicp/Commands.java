package be.tpvision.smartcontrol.protocol.sicp;

import be.tpvision.smartcontrol.messages.protocol.sicp.commands.AddMessages;
import be.tpvision.smartcontrol.messages.protocol.sicp.commands.FindMessages;
import be.tpvision.smartcontrol.messages.protocol.sicp.commands.RemoveMessages;
import be.tpvision.smartcontrol.messages.protocol.sicp.commands.SetCommandsMessages;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class Commands {
   private HashSet<Command> commands;

   public Commands() {
      this.commands = new HashSet<>();
   }

   public Commands(final HashSet<Command> commands) {
      this.setCommands(commands);
   }

   public HashSet<Command> getCommands() {
      return this.commands;
   }

   public void setCommands(final HashSet<Command> commands) {
      Assert.notNull(commands, SetCommandsMessages.COMMANDS_CAN_NOT_BE_NULL);
      Assert.isTrue(!commands.contains(null), "Commands can not contain null.");
      this.commands = commands;
   }

   public void add(final Command command) {
      Assert.notNull(command, AddMessages.COMMAND_CAN_NOT_BE_NULL);
      this.commands.add(command);
   }

   public void remove(final Command command) {
      Assert.notNull(command, RemoveMessages.COMMAND_CAN_NOT_BE_NULL);
      this.commands.remove(command);
   }

   public Command find(final Command.Type type, final Command.Setting setting) {
      Assert.notNull(type, FindMessages.TYPE_CAN_NOT_BE_NULL);
      Assert.notNull(setting, FindMessages.SETTING_CAN_NOT_BE_NULL);
      Optional<Command> commandOptional = this.commands
         .stream()
         .filter(command -> command.getType().equals(type) && command.getSetting().equals(setting))
         .findFirst();
      return commandOptional.isPresent() ? commandOptional.get() : null;
   }

   public Command find(final byte settingByte) {
      Optional<Command> commandOptional = this.commands.stream().filter(command -> command.getSettingByte() == settingByte).findFirst();
      return commandOptional.isPresent() ? commandOptional.get() : null;
   }

   @Override
   public boolean equals(final Object object) {
      if (this == object) {
         return true;
      }

      if (!(object instanceof Commands)) {
         return false;
      }

      Commands that = (Commands)object;
      return new EqualsBuilder().append(this.getCommands(), that.getCommands()).isEquals();
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.getCommands());
   }

   @Override
   public String toString() {
      return new ToStringBuilder(this).append("commands", this.getCommands()).toString();
   }
}
