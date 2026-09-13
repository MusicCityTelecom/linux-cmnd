package com.tpvision.smartcms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpvision.smartcms.dao.WebsiteDAO;
import com.tpvision.smartcms.model.Status;
import com.tpvision.smartcms.model.Website;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@Validated
@RequestMapping("/website")
public class MainRestController {
   private final Log LOG = LogFactory.getLog(this.getClass());
   @Autowired
   private WebsiteDAO websiteService;
   @Value("${published.context}")
   private String publishedContext;
   @Autowired
   private ServletContext servletContext;

   public void setServletContext(ServletContext servletContext) {
      this.servletContext = servletContext;
   }

   @RequestMapping("/")
   @ResponseBody
   public String mainPage() {
      return "smartcms";
   }

   @PostMapping(value = "/add", consumes = "application/json")
   @ResponseBody
   public ResponseEntity<Status> addWebsite(@RequestBody @Valid String json, BindingResult result) {
      Status status = new Status();
      if (result.hasErrors()) {
         status.setMessage("error");
         return new ResponseEntity<>(status, HttpStatus.NOT_ACCEPTABLE);
      }

      System.out.println("ok");
      Website website = null;

      try {
         ObjectMapper mapper = new ObjectMapper();
         Map<String, Object> map = mapper.readValue(json, Map.class);
         if (map.get("id") == null) {
            status.setMessage("null");
            return new ResponseEntity<>(status, HttpStatus.NOT_ACCEPTABLE);
         }

         website = mapper.readValue(json, Website.class);
      } catch (IOException e1) {
         this.LOG.error(e1.getMessage(), e1);
      }

      try {
         if (website == null || website.getId() < 0) {
            status.setMessage("error");
            return new ResponseEntity<>(status, HttpStatus.NOT_ACCEPTABLE);
         }

         if (website.getId() == 0) {
            website.setSuccess("0");
            int id = Integer.valueOf(this.websiteService.addEntity(website));
            status.setId(id);
            status.setMessage("success");
         } else {
            this.websiteService.updateEntity(website);
            status.setId(website.getId());
            status.setMessage("success");
         }
      } catch (Exception e) {
         status.setMessage("error");
         return new ResponseEntity<>(status, HttpStatus.INTERNAL_SERVER_ERROR);
      }

      return new ResponseEntity<>(status, HttpStatus.OK);
   }

   @PutMapping("/{id}")
   @ResponseBody
   public ResponseEntity<Void> updateUser(@PathVariable("id") int id, @RequestBody Website website) {
      Website currentUser = this.websiteService.getEntityById(id);
      if (currentUser == null) {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      } else if (website.getId() == currentUser.getId()) {
         this.websiteService.updateEntity(website);
         return new ResponseEntity<>(HttpStatus.OK);
      } else {
         return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
      }
   }

   @GetMapping(value = "/{id}", produces = {"application/json; charset=utf-8", "application/xml"})
   @ResponseBody
   public ResponseEntity<Website> getWebsite(@PathVariable("id") int id) {
      Website website = null;

      try {
         website = this.websiteService.getEntityByIdList(id);
      } catch (Exception e) {
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      return new ResponseEntity<>(website, HttpStatus.OK);
   }

   @GetMapping(value = "/list", headers = "Accept=application/json,application/xml", produces = {"application/json", "application/xml"})
   @ResponseBody
   public ResponseEntity<List<Website>> getWebsite(@RequestParam(value = "orientation", required = false) String orientation) {
      if (orientation == null) {
         orientation = "all";
      }

      new ArrayList();

      List websiteList;
      try {
         websiteList = this.websiteService.getEntityList(orientation);
      } catch (Exception e) {
         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }

      return websiteList.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(websiteList, HttpStatus.OK);
   }

   @DeleteMapping("/{id}")
   @ResponseBody
   public ResponseEntity<Void> deleteWebsite(@PathVariable("id") int id) {
      try {
         this.websiteService.deleteEntity(id);
      } catch (Exception e) {
         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }

      String resources = this.servletContext.getRealPath("/WEB-INF/pages/") + id + "\\";

      try {
         File folder = new File(resources);
         File[] delfiles = folder.listFiles();
         if (delfiles != null) {
            for (File f : delfiles) {
               try {
                  FileUtils.deleteQuietly(f);
               } catch (Exception e) {
                  this.LOG.error(e.getMessage(), e);
               }
            }
         }

         if (folder.exists()) {
            FileUtils.deleteDirectory(folder);
         }
      } catch (Exception var12) {
      }

      return new ResponseEntity<>(HttpStatus.OK);
   }
}
