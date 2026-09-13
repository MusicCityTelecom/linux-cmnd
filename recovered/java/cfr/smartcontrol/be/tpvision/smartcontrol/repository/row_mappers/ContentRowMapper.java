/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.repository.row_mappers;

import be.tpvision.smartcontrol.domain.Content;
import be.tpvision.smartcontrol.messages.repositories.row_mappers.content.ConstructorMessages;
import be.tpvision.smartcontrol.messages.repositories.row_mappers.content.MapRowMessages;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.AttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ContentRowMapper
implements RowMapper<Content> {
    private static final String ID_COLUMN_NAME = "id";
    private static final String TITLE_COLUMN_NAME = "title";
    private static final String CREATED_COLUMN_NAME = "created";
    private static final String CHANGED_COLUMN_NAME = "changed";
    private static final String ORIENTATION_COLUMN_NAME = "orientation";
    private static final String THUMBNAIL_COLUMN_NAME = "thumbnail";
    private static final String LOCAL_CHANGED_COLUMN_NAME = "local_changed";
    private static final String PUBLISH_DATE_COLUMN_NAME = "publish_date";
    private final AttributeConverter<LocalDateTime, Timestamp> localDateTimeConverter;
    private final AttributeConverter<Content.Orientation, String> orientationConverter;

    @Autowired
    public ContentRowMapper(AttributeConverter<LocalDateTime, Timestamp> localDateTimeConverter, AttributeConverter<Content.Orientation, String> orientationConverter) {
        Assert.notNull(localDateTimeConverter, ConstructorMessages.LOCAL_DATE_TIME_CONVERTER_CAN_NOT_BE_NULL);
        Assert.notNull(orientationConverter, ConstructorMessages.ORIENTATION_CONVERTER_CAN_NOT_BE_NULL);
        this.localDateTimeConverter = localDateTimeConverter;
        this.orientationConverter = orientationConverter;
    }

    @Override
    public Content mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
        Assert.notNull((Object)resultSet, MapRowMessages.RESULT_SET_CAN_NOT_BE_NULL);
        Content content = new Content();
        String id = resultSet.getString(ID_COLUMN_NAME);
        content.setId(id);
        String title = resultSet.getString(TITLE_COLUMN_NAME);
        content.setTitle(title);
        Timestamp createdTimeStamp = resultSet.getTimestamp(CREATED_COLUMN_NAME);
        LocalDateTime created = this.localDateTimeConverter.convertToEntityAttribute(createdTimeStamp);
        content.setCreated(created);
        Timestamp changedTimeStamp = resultSet.getTimestamp(CHANGED_COLUMN_NAME);
        LocalDateTime changed = this.localDateTimeConverter.convertToEntityAttribute(changedTimeStamp);
        content.setChanged(changed);
        String orientationString = resultSet.getString(ORIENTATION_COLUMN_NAME);
        Content.Orientation orientation = this.orientationConverter.convertToEntityAttribute(orientationString);
        content.setOrientation(orientation);
        String thumbnail = resultSet.getString(THUMBNAIL_COLUMN_NAME);
        content.setThumbnail(thumbnail);
        Timestamp localChangedTimeStamp = resultSet.getTimestamp(LOCAL_CHANGED_COLUMN_NAME);
        LocalDateTime localChanged = this.localDateTimeConverter.convertToEntityAttribute(localChangedTimeStamp);
        content.setLocalChanged(localChanged);
        Timestamp publishDateTimeStamp = resultSet.getTimestamp(PUBLISH_DATE_COLUMN_NAME);
        LocalDateTime publishDate = this.localDateTimeConverter.convertToEntityAttribute(publishDateTimeStamp);
        content.setPublishDate(publishDate);
        return content;
    }
}

