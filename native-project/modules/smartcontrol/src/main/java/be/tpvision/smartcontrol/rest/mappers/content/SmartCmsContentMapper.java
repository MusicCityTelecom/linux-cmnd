package be.tpvision.smartcontrol.rest.mappers.content;

import be.tpvision.smartcontrol.domain.Content;
import be.tpvision.smartcontrol.messages.mappers.content.smart_cms_content.ToContentListMessages;
import be.tpvision.smartcontrol.messages.mappers.content.smart_cms_content.ToContentMessages;
import be.tpvision.smartcontrol.rest.view_models.content.SmartCmsContentViewModel;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.Assert;

public class SmartCmsContentMapper {
   private SmartCmsContentMapper() {
   }

   public static Content toContent(final SmartCmsContentViewModel smartCmsContentViewModel) {
      Assert.notNull(smartCmsContentViewModel, ToContentMessages.SMART_CMS_CONTENT_VIEW_MODEL_CAN_NOT_BE_NULL);
      DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
      Content content = new Content();
      String id = smartCmsContentViewModel.getId();
      content.setId(id);
      String title = smartCmsContentViewModel.getTitle();
      content.setTitle(title);
      String createdString = smartCmsContentViewModel.getCreated();
      ZoneId zoneId = ZoneId.systemDefault();
      if (createdString != null) {
         ZonedDateTime createdZonedDateTime = ZonedDateTime.parse(createdString, dateTimeFormatter);
         createdZonedDateTime = createdZonedDateTime.withZoneSameInstant(zoneId);
         LocalDateTime created = createdZonedDateTime.toLocalDateTime();
         content.setCreated(created);
      }

      String changedString = smartCmsContentViewModel.getChanged();
      if (changedString != null) {
         ZonedDateTime changedZonedDateTime = ZonedDateTime.parse(changedString, dateTimeFormatter);
         changedZonedDateTime = changedZonedDateTime.withZoneSameInstant(zoneId);
         LocalDateTime changed = changedZonedDateTime.toLocalDateTime();
         content.setChanged(changed);
      }

      String publishDateString = smartCmsContentViewModel.getPublishDate();
      if (publishDateString != null) {
         ZonedDateTime publishDateZonedDateTime = ZonedDateTime.parse(publishDateString, dateTimeFormatter);
         publishDateZonedDateTime = publishDateZonedDateTime.withZoneSameInstant(zoneId);
         LocalDateTime publishDate = publishDateZonedDateTime.toLocalDateTime();
         content.setPublishDate(publishDate);
      }

      String thumbnail = smartCmsContentViewModel.getThumbnail();
      content.setThumbnail(thumbnail);
      Integer orientationInteger = smartCmsContentViewModel.getOrientation();
      if (orientationInteger != null) {
         Content.Orientation orientation;
         switch (orientationInteger) {
            case 0:
               orientation = Content.Orientation.LANDSCAPE;
               break;
            case 1:
               orientation = Content.Orientation.PORTRAIT;
               break;
            default:
               String message = ToContentMessages.getOrientationIsNotSupportedMessage(orientationInteger);
               throw new UnsupportedOperationException(message);
         }

         content.setOrientation(orientation);
      }

      return content;
   }

   public static List<Content> toContentList(final Collection<SmartCmsContentViewModel> smartCmsContentViewModelCollection) {
      Assert.notNull(smartCmsContentViewModelCollection, ToContentListMessages.SMART_CMS_CONTENT_VIEW_MODEL_COLLECTION_CAN_NOT_BE_NULL);
      return smartCmsContentViewModelCollection.stream()
         .filter(Objects::nonNull)
         .map(SmartCmsContentMapper::toContent)
         .filter(Objects::nonNull)
         .collect(Collectors.toList());
   }
}
