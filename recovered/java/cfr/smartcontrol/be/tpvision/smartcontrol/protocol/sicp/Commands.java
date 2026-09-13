/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.protocol.sicp;

import be.tpvision.smartcontrol.messages.protocol.sicp.commands.AddMessages;
import be.tpvision.smartcontrol.messages.protocol.sicp.commands.FindMessages;
import be.tpvision.smartcontrol.messages.protocol.sicp.commands.RemoveMessages;
import be.tpvision.smartcontrol.messages.protocol.sicp.commands.SetCommandsMessages;
import be.tpvision.smartcontrol.protocol.sicp.Command;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public class Commands {
    private HashSet<Command> commands;

    public Commands() {
        this.commands = new HashSet();
    }

    public Commands(HashSet<Command> commands) {
        this.setCommands(commands);
    }

    public HashSet<Command> getCommands() {
        return this.commands;
    }

    public void setCommands(HashSet<Command> commands) {
        Assert.notNull(commands, SetCommandsMessages.COMMANDS_CAN_NOT_BE_NULL);
        Assert.isTrue(!commands.contains(null), "Commands can not contain null.");
        this.commands = commands;
    }

    public void add(Command command) {
        Assert.notNull((Object)command, AddMessages.COMMAND_CAN_NOT_BE_NULL);
        this.commands.add(command);
    }

    public void remove(Command command) {
        Assert.notNull((Object)command, RemoveMessages.COMMAND_CAN_NOT_BE_NULL);
        this.commands.remove(command);
    }

    public Command find(Command.Type type, Command.Setting setting) {
        Assert.notNull((Object)type, FindMessages.TYPE_CAN_NOT_BE_NULL);
        Assert.notNull((Object)setting, FindMessages.SETTING_CAN_NOT_BE_NULL);
        Optional<Command> commandOptional = this.commands.stream().filter(command -> command.getType().equals((Object)type) && command.getSetting().equals((Object)setting)).findFirst();
        return commandOptional.isPresent() ? commandOptional.get() : null;
    }

    public Command find(byte settingByte) {
        Optional<Command> commandOptional = this.commands.stream().filter(command -> command.getSettingByte() == settingByte).findFirst();
        return commandOptional.isPresent() ? commandOptional.get() : null;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Commands)) {
            return false;
        }
        Commands that = (Commands)object;
        return new EqualsBuilder().append(this.getCommands(), that.getCommands()).isEquals();
    }

    public int hashCode() {
        return Objects.hash(this.getCommands());
    }

    public String toString() {
        return new ToStringBuilder(this).append("commands", this.getCommands()).toString();
    }
}

