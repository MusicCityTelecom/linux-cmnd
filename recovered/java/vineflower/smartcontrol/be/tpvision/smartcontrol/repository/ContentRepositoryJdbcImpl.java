package be.tpvision.smartcontrol.repository;

import be.tpvision.smartcontrol.domain.Content;
import be.tpvision.smartcontrol.messages.repositories.jdbc.content.AddContentMessages;
import be.tpvision.smartcontrol.messages.repositories.jdbc.content.ConstructorMessages;
import be.tpvision.smartcontrol.messages.repositories.jdbc.content.DeleteContentMessages;
import be.tpvision.smartcontrol.messages.repositories.jdbc.content.GetContentArgumentsMessages;
import be.tpvision.smartcontrol.messages.repositories.jdbc.content.GetContentMessages;
import be.tpvision.smartcontrol.messages.repositories.jdbc.content.UpdateContentMessages;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.AttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.Assert;

@org.springframework.stereotype.Repository
public class ContentRepositoryJdbcImpl implements ContentRepositoryJdbc {
   private final JdbcTemplate jdbcTemplate;
   private final RowMapper<Content> contentRowMapper;
   private final AttributeConverter<LocalDateTime, Timestamp> localDateTimeConverter;
   private final AttributeConverter<Content.Orientation, String> orientationConverter;

   @Autowired
   public ContentRepositoryJdbcImpl(
      final JdbcTemplate jdbcTemplate,
      final RowMapper<Content> contentRowMapper,
      final AttributeConverter<LocalDateTime, Timestamp> localDateTimeConverter,
      final AttributeConverter<Content.Orientation, String> orientationConverter
   ) {
      Assert.notNull(jdbcTemplate, ConstructorMessages.JDBC_TEMPLATE_CAN_NOT_BE_NULL);
      this.jdbcTemplate = jdbcTemplate;
      this.contentRowMapper = contentRowMapper;
      this.localDateTimeConverter = localDateTimeConverter;
      this.orientationConverter = orientationConverter;
   }

   private Object[] getContentArguments(final Content content) {
      Assert.state(this.localDateTimeConverter != null, GetContentArgumentsMessages.LOCAL_DATE_TIME_CONVERTER_CAN_NOT_BE_NULL);
      Assert.state(this.orientationConverter != null, GetContentArgumentsMessages.ORIENTATION_CONVERTER_CAN_NOT_BE_NULL);
      Assert.notNull(content, GetContentArgumentsMessages.CONTENT_CAN_NOT_BE_NULL);
      String id = content.getId();
      String title = content.getTitle();
      LocalDateTime created = content.getCreated();
      Timestamp createdTimeStamp = this.localDateTimeConverter.convertToDatabaseColumn(created);
      LocalDateTime changed = content.getChanged();
      Timestamp changedTimeStamp = this.localDateTimeConverter.convertToDatabaseColumn(changed);
      Content.Orientation orientation = content.getOrientation();
      String orientationString = this.orientationConverter.convertToDatabaseColumn(orientation);
      String thumbnail = content.getThumbnail();
      LocalDateTime localChanged = content.getLocalChanged();
      Timestamp localChangedTimeStamp = this.localDateTimeConverter.convertToDatabaseColumn(localChanged);
      LocalDateTime publishDate = content.getPublishDate();
      Timestamp publishDateTimeStamp = this.localDateTimeConverter.convertToDatabaseColumn(publishDate);
      return new Object[]{id, title, createdTimeStamp, changedTimeStamp, orientationString, thumbnail, localChangedTimeStamp, publishDateTimeStamp};
   }

   @Override
   public Set<Content> getContent() {
      Assert.state(this.contentRowMapper != null, GetContentMessages.CONTENT_ROW_MAPPER_CAN_NOT_BE_NULL);
      String selectContentSql = "SELECT id, title, orientation, thumbnail, created, changed, local_changed, publish_date FROM content";
      List<Content> contentList = this.jdbcTemplate
         .query("SELECT id, title, orientation, thumbnail, created, changed, local_changed, publish_date FROM content", this.contentRowMapper);
      return new HashSet<>(contentList);
   }

   @Override
   public Content getContent(final String id) {
      Assert.state(this.contentRowMapper != null, GetContentMessages.CONTENT_ROW_MAPPER_CAN_NOT_BE_NULL);
      Assert.notNull(id, GetContentMessages.ID_CAN_NOT_BE_NULL);
      Assert.isTrue(!id.isEmpty(), GetContentMessages.ID_CAN_NOT_BE_EMPTY);
      String selectContentSql = "SELECT id, title, orientation, thumbnail, created, changed, local_changed, publish_date FROM content WHERE id = ?";

      try {
         return this.jdbcTemplate
            .queryForObject(
               "SELECT id, title, orientation, thumbnail, created, changed, local_changed, publish_date FROM content WHERE id = ?", this.contentRowMapper, id
            );
      } catch (EmptyResultDataAccessException emptyResultDataAccessException) {
         return null;
      }
   }

   @Override
   public void addContent(final Content content) {
      Assert.notNull(content, AddContentMessages.CONTENT_CAN_NOT_BE_NULL);
      String insertContentSql = "INSERT INTO content (id, title, created, changed, orientation, thumbnail, local_changed, publish_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
      Object[] insertContentArguments = this.getContentArguments(content);
      Assert.state(insertContentArguments != null, AddContentMessages.INSERT_CONTENT_ARGUMENTS_CAN_NOT_BE_NULL);
      this.jdbcTemplate
         .update(
            "INSERT INTO content (id, title, created, changed, orientation, thumbnail, local_changed, publish_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
            insertContentArguments
         );
   }

   @Override
   public void updateContent(final Content content) {
      Assert.notNull(content, UpdateContentMessages.CONTENT_CAN_NOT_BE_NULL);
      String updateContentSql = "UPDATE content SET id = ?, title = ?, created = ?, changed = ?, orientation = ?, thumbnail = ?, local_changed = ?, publish_date = ? WHERE id = ?";
      Object[] updateContentArguments = this.getContentArguments(content);
      Assert.state(updateContentArguments != null, UpdateContentMessages.UPDATE_CONTENT_ARGUMENTS_CAN_NOT_BE_NULL);
      int newUpdateContentArgumentsLength = updateContentArguments.length + 1;
      updateContentArguments = Arrays.copyOf(updateContentArguments, newUpdateContentArgumentsLength);
      int whereIdIndex = updateContentArguments.length - 1;
      String id = content.getId();
      Assert.state(id != null, UpdateContentMessages.ID_CAN_NOT_BE_NULL);
      Assert.state(!id.isEmpty(), UpdateContentMessages.ID_CAN_NOT_BE_EMPTY);
      updateContentArguments[whereIdIndex] = id;
      this.jdbcTemplate
         .update(
            "UPDATE content SET id = ?, title = ?, created = ?, changed = ?, orientation = ?, thumbnail = ?, local_changed = ?, publish_date = ? WHERE id = ?",
            updateContentArguments
         );
   }

   @Override
   public void deleteContent(final Content content) {
      Assert.notNull(content, DeleteContentMessages.CONTENT_CAN_NOT_BE_NULL);
      String id = content.getId();
      Assert.state(id != null, DeleteContentMessages.ID_CAN_NOT_BE_NULL);
      Assert.state(!id.isEmpty(), DeleteContentMessages.ID_CAN_NOT_BE_EMPTY);
      String deleteContentSql = "DELETE FROM content WHERE id = ?";
      this.jdbcTemplate.update("DELETE FROM content WHERE id = ?", id);
   }
}
