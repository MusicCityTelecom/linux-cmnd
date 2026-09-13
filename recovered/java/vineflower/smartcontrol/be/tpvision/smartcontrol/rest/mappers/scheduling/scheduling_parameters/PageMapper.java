package be.tpvision.smartcontrol.rest.mappers.scheduling.scheduling_parameters;

import be.tpvision.smartcontrol.domain.device_settings.input_sources.InputSource;
import be.tpvision.smartcontrol.domain.device_settings.scheduling.scheduling_parameters.Page;
import be.tpvision.smartcontrol.messages.mappers.scheduling.scheduling_parameters.page.ToPageMessages;
import be.tpvision.smartcontrol.messages.mappers.scheduling.scheduling_parameters.page.ToPageViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.scheduling.PageViewModel;
import be.tpvision.smartcontrol.rest.view_models.scheduling.WorkingDayViewModel;
import be.tpvision.smartcontrol.util.ValueUtilities;
import java.time.LocalTime;
import java.util.List;
import org.springframework.util.Assert;

public class PageMapper {
   private PageMapper() {
   }

   public static PageViewModel toPageViewModel(final Page page) {
      Assert.notNull(page, ToPageViewModelMessages.PAGE_CAN_NOT_BE_NULL);
      PageViewModel pageViewModel = new PageViewModel();
      Integer number = page.getNumber();
      pageViewModel.setNumber(number);
      Page.Status domainStatus = page.getStatus();
      Assert.state(domainStatus != null, ToPageViewModelMessages.DOMAIN_STATUS_CAN_NOT_BE_NULL);
      String viewModelStatus = String.valueOf(domainStatus);
      pageViewModel.setStatus(viewModelStatus);
      LocalTime domainStart = page.getStart();
      String viewModelStart = domainStart != null ? String.valueOf(domainStart) : null;
      pageViewModel.setStart(viewModelStart);
      LocalTime domainEnd = page.getEnd();
      String viewModelEnd = domainEnd != null ? String.valueOf(domainEnd) : null;
      pageViewModel.setEnd(viewModelEnd);
      InputSource.SourceType domainSourceType = page.getSourceType();
      String viewModelSourceType = domainSourceType != null ? String.valueOf(domainSourceType) : null;
      pageViewModel.setSourceType(viewModelSourceType);
      Page.WorkingDays domainWorkingDays = page.getWorkingDays();
      Assert.state(domainWorkingDays != null, ToPageViewModelMessages.DOMAIN_WORKING_DAYS_CAN_NOT_BE_NULL);
      List<WorkingDayViewModel> viewModelWorkingDays = WorkingDayMapper.toWorkingDayViewModelList(domainWorkingDays);
      Assert.state(viewModelWorkingDays != null, ToPageViewModelMessages.VIEW_MODEL_WORKING_DAYS_CAN_NOT_BE_NULL);
      Assert.state(!viewModelWorkingDays.isEmpty(), ToPageViewModelMessages.VIEW_MODEL_WORKING_DAYS_CAN_NOT_BE_EMPTY);
      Assert.state(!viewModelWorkingDays.contains(null), ToPageViewModelMessages.VIEW_MODEL_WORKING_DAYS_CAN_NOT_CONTAIN_NULL_VALUES);
      pageViewModel.setWorkingDays(viewModelWorkingDays);
      InputSource.Tag domainTag = page.getTag();
      String viewModelTag = domainTag != null ? String.valueOf(domainTag) : null;
      pageViewModel.setTag(viewModelTag);
      return pageViewModel;
   }

   public static Page toPage(final PageViewModel pageViewModel) {
      Assert.notNull(pageViewModel, ToPageMessages.PAGE_VIEW_MODEL_CAN_NOT_BE_NULL);
      Integer number = pageViewModel.getNumber();
      String viewModelStatus = pageViewModel.getStatus();
      Assert.state(viewModelStatus != null, ToPageMessages.VIEW_MODEL_STATUS_CAN_NOT_BE_NULL);
      Page.Status domainStatus = ValueUtilities.getEnumValue(Page.Status.class, viewModelStatus);
      String viewModelStart = pageViewModel.getStart();
      LocalTime domainStart = viewModelStart != null ? LocalTime.parse(viewModelStart) : null;
      String viewModelEnd = pageViewModel.getEnd();
      LocalTime domainEnd = viewModelEnd != null ? LocalTime.parse(viewModelEnd) : null;
      String viewModelSourceType = pageViewModel.getSourceType();
      InputSource.SourceType domainSourceType = viewModelSourceType != null
         ? ValueUtilities.getEnumValue(InputSource.SourceType.class, viewModelSourceType)
         : null;
      List<WorkingDayViewModel> viewModelWorkingDays = pageViewModel.getWorkingDays();
      Assert.state(viewModelWorkingDays != null, ToPageMessages.VIEW_MODEL_WORKING_DAYS_CAN_NOT_BE_NULL);
      Assert.state(!viewModelWorkingDays.isEmpty(), ToPageMessages.VIEW_MODEL_WORKING_DAYS_CAN_NOT_BE_EMPTY);
      Assert.state(!viewModelWorkingDays.contains(null), ToPageMessages.VIEW_MODEL_WORKING_DAYS_CAN_NOT_CONTAIN_NULL_VALUES);
      Page.WorkingDays domainWorkingDays = WorkingDayMapper.toWorkingDays(viewModelWorkingDays);
      Assert.state(domainWorkingDays != null, ToPageMessages.DOMAIN_WORKING_DAYS_CAN_NOT_BE_NULL);
      String viewModelTag = pageViewModel.getTag();
      InputSource.Tag domainTag = viewModelTag != null ? ValueUtilities.getEnumValue(InputSource.Tag.class, viewModelTag) : null;
      return new Page(number, domainStatus, domainStart, domainEnd, domainSourceType, domainWorkingDays, domainTag);
   }
}
