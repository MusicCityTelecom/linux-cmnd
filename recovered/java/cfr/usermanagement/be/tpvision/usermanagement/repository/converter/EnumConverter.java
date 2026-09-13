/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.usermanagement.repository.converter;

import java.util.Objects;
import javax.persistence.AttributeConverter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;
import util.MessageUtilities;

public abstract class EnumConverter<T extends Enum<T>>
implements AttributeConverter<T, String> {
    private Class<T> enumClass;

    public EnumConverter(Class<T> enumClass) {
        Assert.notNull(enumClass, MessageUtilities.ENUM_CLASS_NOT_NULL_MESSAGE);
        this.enumClass = enumClass;
    }

    public Class<T> getEnumClass() {
        Assert.state(this.enumClass != null, MessageUtilities.ENUM_CLASS_NOT_NULL_MESSAGE);
        return this.enumClass;
    }

    @Override
    public String convertToDatabaseColumn(T enumValue) {
        return enumValue != null ? String.valueOf(enumValue) : null;
    }

    @Override
    public T convertToEntityAttribute(String enumValue) {
        T value;
        try {
            value = Enum.valueOf(this.getEnumClass(), enumValue);
        }
        catch (IllegalArgumentException | NullPointerException e) {
            value = null;
        }
        return value;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof EnumConverter)) {
            return false;
        }
        EnumConverter that = (EnumConverter)object;
        return Objects.equals(this.getEnumClass(), that.getEnumClass());
    }

    public int hashCode() {
        return Objects.hash(this.getEnumClass());
    }

    public String toString() {
        return new ToStringBuilder(this).append("enumClass", this.getEnumClass()).toString();
    }
}

