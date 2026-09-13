package com.tpvision.smartinstall.dao.mgr;

import com.tpvision.smartinstall.dao.MyChoiceTemplateRepository;
import com.tpvision.smartinstall.dao.core.MyChoiceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyChoiceTemplateManager {
   private static final int CONFIG_ID = 1;
   @Autowired
   private MyChoiceTemplateRepository myChoiceTemplateRepository;

   public MyChoiceTemplate getMyChoiceTemplate() {
      return this.myChoiceTemplateRepository.findById(1).orElse(null);
   }

   public MyChoiceTemplate saveMyChoiceTemplate(MyChoiceTemplate mychoiceTemplate) {
      return this.myChoiceTemplateRepository.save(mychoiceTemplate);
   }
}
