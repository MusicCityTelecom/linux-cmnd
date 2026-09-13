package be.tpvision.smartcontrol.rest.mappers.scheduling.scheduling_parameters;

import be.tpvision.smartcontrol.domain.device_settings.scheduling.scheduling_parameters.Page;
import be.tpvision.smartcontrol.domain.device_settings.scheduling.scheduling_parameters.SchedulingParameters;
import be.tpvision.smartcontrol.messages.mappers.scheduling.scheduling_parameters.ToSchedulingParametersViewModelMessages;
import be.tpvision.smartcontrol.rest.view_models.scheduling.PageViewModel;
import be.tpvision.smartcontrol.rest.view_models.scheduling.SchedulingParametersViewModel;
import java.util.List;
import org.springframework.util.Assert;

public class SchedulingParametersMapper {
   private SchedulingParametersMapper() {
   }

   public static SchedulingParametersViewModel toSchedulingParametersViewModel(final SchedulingParameters schedulingParameters) {
      Assert.notNull(schedulingParameters, ToSchedulingParametersViewModelMessages.SCHEDULING_PARAMETERS_CAN_NOT_BE_NULL);
      SchedulingParametersViewModel schedulingParametersViewModel = new SchedulingParametersViewModel();
      List<Page> pages = schedulingParameters.getPages();
      Assert.state(pages != null, ToSchedulingParametersViewModelMessages.PAGES_CAN_NOT_BE_NULL);
      Page domainPage1 = pages.get(0);
      Assert.state(domainPage1 != null, ToSchedulingParametersViewModelMessages.DOMAIN_PAGE_1_CAN_NOT_BE_NULL);
      PageViewModel viewModelPage1 = PageMapper.toPageViewModel(domainPage1);
      Assert.state(viewModelPage1 != null, ToSchedulingParametersViewModelMessages.VIEW_MODEL_PAGE_1_CAN_NOT_BE_NULL);
      schedulingParametersViewModel.setPage1(viewModelPage1);
      Page domainPage2 = pages.get(1);
      Assert.state(domainPage2 != null, ToSchedulingParametersViewModelMessages.DOMAIN_PAGE_2_CAN_NOT_BE_NULL);
      PageViewModel viewModelPage2 = PageMapper.toPageViewModel(domainPage2);
      Assert.state(viewModelPage2 != null, ToSchedulingParametersViewModelMessages.VIEW_MODEL_PAGE_2_CAN_NOT_BE_NULL);
      schedulingParametersViewModel.setPage2(viewModelPage2);
      Page domainPage3 = pages.get(2);
      Assert.state(domainPage3 != null, ToSchedulingParametersViewModelMessages.DOMAIN_PAGE_3_CAN_NOT_BE_NULL);
      PageViewModel viewModelPage3 = PageMapper.toPageViewModel(domainPage3);
      Assert.state(viewModelPage3 != null, ToSchedulingParametersViewModelMessages.VIEW_MODEL_PAGE_3_CAN_NOT_BE_NULL);
      schedulingParametersViewModel.setPage3(viewModelPage3);
      Page domainPage4 = pages.get(3);
      Assert.state(domainPage4 != null, ToSchedulingParametersViewModelMessages.DOMAIN_PAGE_4_CAN_NOT_BE_NULL);
      PageViewModel viewModelPage4 = PageMapper.toPageViewModel(domainPage4);
      Assert.state(viewModelPage4 != null, ToSchedulingParametersViewModelMessages.VIEW_MODEL_PAGE_4_CAN_NOT_BE_NULL);
      schedulingParametersViewModel.setPage4(viewModelPage4);
      Page domainPage5 = pages.get(4);
      Assert.state(domainPage5 != null, ToSchedulingParametersViewModelMessages.DOMAIN_PAGE_5_CAN_NOT_BE_NULL);
      PageViewModel viewModelPage5 = PageMapper.toPageViewModel(domainPage5);
      Assert.state(viewModelPage5 != null, ToSchedulingParametersViewModelMessages.VIEW_MODEL_PAGE_5_CAN_NOT_BE_NULL);
      schedulingParametersViewModel.setPage5(viewModelPage5);
      Page domainPage6 = pages.get(5);
      Assert.state(domainPage6 != null, ToSchedulingParametersViewModelMessages.DOMAIN_PAGE_6_CAN_NOT_BE_NULL);
      PageViewModel viewModelPage6 = PageMapper.toPageViewModel(domainPage6);
      Assert.state(viewModelPage6 != null, ToSchedulingParametersViewModelMessages.VIEW_MODEL_PAGE_6_CAN_NOT_BE_NULL);
      schedulingParametersViewModel.setPage6(viewModelPage6);
      Page domainPage7 = pages.get(6);
      Assert.state(domainPage7 != null, ToSchedulingParametersViewModelMessages.DOMAIN_PAGE_7_CAN_NOT_BE_NULL);
      PageViewModel viewModelPage7 = PageMapper.toPageViewModel(domainPage7);
      Assert.state(viewModelPage7 != null, ToSchedulingParametersViewModelMessages.VIEW_MODEL_PAGE_7_CAN_NOT_BE_NULL);
      schedulingParametersViewModel.setPage7(viewModelPage7);
      return schedulingParametersViewModel;
   }
}
