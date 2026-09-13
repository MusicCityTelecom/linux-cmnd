/*
 * Decompiled with CFR 0.152.
 */
package be.tpvision.smartcontrol.rest.mappers.content;

import be.tpvision.smartcontrol.domain.Content;
import be.tpvision.smartcontrol.messages.mappers.content.content.ToContentListMessages;
import be.tpvision.smartcontrol.messages.mappers.content.content.ToContentMessages;
import be.tpvision.smartcontrol.messages.mappers.content.content.ToContentViewModelListMessages;
import be.tpvision.smartcontrol.messages.mappers.content.content.ToContentViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.content.ContentViewModel;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.util.Assert;

public class ContentMapper {
    private ContentMapper() {
    }

    public static ContentViewModel toContentViewModel(Content content) {
        LocalDateTime changed;
        Assert.notNull((Object)content, ToContentViewModelMessages.CONTENT_CAN_NOT_BE_NULL);
        ContentViewModel contentViewModel = new ContentViewModel();
        String id = content.getId();
        contentViewModel.setId(id);
        String title = content.getTitle();
        contentViewModel.setTitle(title);
        LocalDateTime created = content.getCreated();
        ZoneId zoneId = ZoneId.systemDefault();
        if (created != null) {
            ZonedDateTime createdZonedDateTime = ZonedDateTime.of(created, zoneId);
            OffsetDateTime createdOffsetDateTime = createdZonedDateTime.toOffsetDateTime();
            String createdString = createdOffsetDateTime.toString();
            contentViewModel.setCreated(createdString);
        }
        if ((changed = content.getChanged()) != null) {
            ZonedDateTime changedZonedDateTime = ZonedDateTime.of(changed, zoneId);
            OffsetDateTime changedOffsetDateTime = changedZonedDateTime.toOffsetDateTime();
            String changedString = changedOffsetDateTime.toString();
            contentViewModel.setChanged(changedString);
        }
        Content.Orientation orientation = content.getOrientation();
        String orientationString = orientation.toString();
        contentViewModel.setOrientation(orientationString);
        String thumbnail = content.getThumbnail();
        contentViewModel.setThumbnail(thumbnail);
        LocalDateTime publishDate = content.getPublishDate();
        if (publishDate != null) {
            ZonedDateTime publishDateZonedDateTime = ZonedDateTime.of(publishDate, zoneId);
            OffsetDateTime publishDateOffsetDateTime = publishDateZonedDateTime.toOffsetDateTime();
            String publishDateString = publishDateOffsetDateTime.toString();
            contentViewModel.setPublishDate(publishDateString);
        }
        return contentViewModel;
    }

    public static Content toContent(ContentViewModel contentViewModel) {
        Assert.notNull((Object)contentViewModel, ToContentMessages.CONTENT_VIEW_MODEL_CAN_NOT_BE_NULL);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        Content content = new Content();
        String id = contentViewModel.getId();
        content.setId(id);
        String title = contentViewModel.getTitle();
        content.setTitle(title);
        String createdString = contentViewModel.getCreated();
        ZonedDateTime createdZonedDateTime = ZonedDateTime.parse(createdString, dateTimeFormatter);
        ZoneId zoneId = ZoneId.systemDefault();
        createdZonedDateTime = createdZonedDateTime.withZoneSameInstant(zoneId);
        LocalDateTime created = createdZonedDateTime.toLocalDateTime();
        content.setCreated(created);
        String changedString = contentViewModel.getChanged();
        ZonedDateTime changedZonedDateTime = ZonedDateTime.parse(changedString, dateTimeFormatter);
        changedZonedDateTime = changedZonedDateTime.withZoneSameInstant(zoneId);
        LocalDateTime changed = changedZonedDateTime.toLocalDateTime();
        content.setChanged(changed);
        String orientationString = contentViewModel.getOrientation();
        Content.Orientation orientation = ValueUtilities.getEnumValue(Content.Orientation.class, orientationString);
        content.setOrientation(orientation);
        String thumbnail = contentViewModel.getThumbnail();
        content.setThumbnail(thumbnail);
        String publishDateString = contentViewModel.getPublishDate();
        ZonedDateTime publishDateZonedDateTime = ZonedDateTime.parse(publishDateString, dateTimeFormatter);
        publishDateZonedDateTime = publishDateZonedDateTime.withZoneSameInstant(zoneId);
        LocalDateTime publishDate = publishDateZonedDateTime.toLocalDateTime();
        content.setPublishDate(publishDate);
        return content;
    }

    public static List<ContentViewModel> toContentViewModelList(Collection<Content> contentCollection) {
        Assert.notNull(contentCollection, ToContentViewModelListMessages.CONTENT_COLLECTION_CAN_NOT_BE_NULL);
        return contentCollection.stream().filter(Objects::nonNull).map(ContentMapper::toContentViewModel).filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
    }

    public static List<Content> toContentList(Collection<ContentViewModel> contentViewModelCollection) {
        Assert.notNull(contentViewModelCollection, ToContentListMessages.CONTENT_VIEW_MODEL_COLLECTION_CAN_NOT_BE_NULL);
        return contentViewModelCollection.stream().filter(Objects::nonNull).map(ContentMapper::toContent).filter(Objects::nonNull).collect(Collectors.toList());
    }
}

