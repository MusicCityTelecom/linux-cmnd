/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository.converters;

import be.tpvision.smartcontrol.messages.repositories.converters.enum_converter.ConstructorMessages;
import java.util.Objects;
import javax.persistence.AttributeConverter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

public abstract class EnumConverter<T extends Enum<T>>
implements AttributeConverter<T, String> {
    private final Class<T> enumClass;

    public EnumConverter(Class<T> enumClass) {
        Assert.notNull(enumClass, ConstructorMessages.ENUM_CLASS_CAN_NOT_BE_NULL);
        this.enumClass = enumClass;
    }

    @Override
    public String convertToDatabaseColumn(T enumValue) {
        if (enumValue == null) {
            return null;
        }
        return String.valueOf(enumValue);
    }

    @Override
    public T convertToEntityAttribute(String enumValue) {
        try {
            return Enum.valueOf(this.enumClass, enumValue);
        }
        catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof EnumConverter)) {
            return false;
        }
        EnumConverter that = (EnumConverter)object;
        return Objects.equals(this.enumClass, that.enumClass);
    }

    public int hashCode() {
        return Objects.hash(this.enumClass);
    }

    public String toString() {
        return new ToStringBuilder(this).append("enumClass", this.enumClass).toString();
    }
}

