/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.ServletContext
 *  javax.servlet.ServletOutputStream
 *  javax.servlet.http.HttpServletResponse
 */
package com.tpvision.smartcms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.tpvision.smartcms.dao.WebsiteDAO;
import com.tpvision.smartcms.model.Status;
import com.tpvision.smartcms.model.Website;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {
    private final Log LOG = LogFactory.getLog(this.getClass());
    @Autowired
    private WebsiteDAO websiteService;
    @Autowired
    private ServletContext servletContext;
    private static final String WEB_INF_ADDRESS = "/WEB-INF/pages/";

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @RequestMapping(value={"/"})
    @ResponseBody
    public String mainPage() {
        System.out.println(this.servletContext.getRealPath(WEB_INF_ADDRESS));
        return "";
    }

    @GetMapping(value={"/website/show"})
    public String displayForm() {
        return "file_upload_form";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @PostMapping(value={"/website/upload/{id}"})
    @ResponseBody
    public ResponseEntity<Status> multipleSave(@RequestParam(value="thumb") MultipartFile[] thumbs, @RequestParam(value="file") MultipartFile[] files, @RequestParam(value="file_right", required=false) MultipartFile[] filesRight, @PathVariable(value="id") String id, @RequestHeader HttpHeaders headers) {
        File[] delfiles;
        Website website = null;
        Status status = new Status();
        if (id.equalsIgnoreCase(null) || id.equalsIgnoreCase("null")) {
            status.setId(-1);
            status.setMessage("Bad Request");
            return new ResponseEntity<Status>(status, HttpStatus.BAD_REQUEST);
        }
        String resources = this.servletContext.getRealPath(WEB_INF_ADDRESS) + id + "\\";
        try {
            Object json = headers.get("json");
            ObjectMapper mapper = new ObjectMapper();
            website = mapper.readValue((String)json.get(0), Website.class);
        }
        catch (IOException e) {
            this.LOG.error(e.getMessage(), e);
        }
        if (website == null || Integer.parseInt(id) != website.getId()) {
            status.setMessage(HttpStatus.BAD_REQUEST.toString());
            return new ResponseEntity<Status>(status, HttpStatus.BAD_REQUEST);
        }
        Website curwebsite = null;
        try {
            curwebsite = this.websiteService.getEntityById(Integer.parseInt(id));
        }
        catch (Exception e) {
            status.setMessage("Not found");
            return new ResponseEntity<Status>(status, HttpStatus.BAD_REQUEST);
        }
        Boolean isNewFiles = true;
        try {
            StdDateFormat df = new StdDateFormat();
            Date date1 = df.parse(curwebsite.getPublishDate());
            Date date2 = df.parse(website.getPublishDate());
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(date1);
            cal2.setTime(date2);
            if (cal1.after(cal2)) {
                System.out.println("Date1 is after Date2");
                isNewFiles = false;
            }
            if (cal1.before(cal2)) {
                // empty if block
            }
            if (!cal1.equals(cal2)) {
                // empty if block
            }
        }
        catch (ParseException ex) {
            this.LOG.error(ex.getMessage(), ex);
        }
        if (!new File(resources).exists()) {
            new File(resources).mkdir();
        }
        String str1 = this.writefile(resources, thumbs, "thumbs");
        String str2 = this.writefile(resources, files, "file");
        String str3 = this.writefile(resources, filesRight, "filesRight");
        String str = "{";
        if (!str1.contains("empty")) {
            str = str + str1;
        }
        if (!str2.contains("empty")) {
            str = str + str2;
        }
        if (!str3.contains("empty")) {
            str = str + str3;
        }
        str = str + "}";
        str = str.replaceAll(",}", "}");
        website.setSuccess("1");
        website.setId(Integer.parseInt(id));
        if (isNewFiles.booleanValue()) {
            this.websiteService.updateEntity(website);
        }
        if ((delfiles = new File(resources).listFiles()) != null) {
            for (File f : delfiles) {
                if (f.toString().contains(website.getFolderName())) continue;
                try {
                    FileUtils.deleteQuietly(f);
                }
                catch (Exception e) {
                    this.LOG.error(e.getMessage(), e);
                }
            }
        }
        status.setId(Integer.parseInt(id));
        status.setMessage(str);
        System.out.println(status.toString());
        return new ResponseEntity<Status>(status, HttpStatus.OK);
    }

    @RequestMapping(value={"/website/download/{id}"}, method={RequestMethod.GET})
    @ResponseBody
    public ResponseEntity<Status> download(HttpServletResponse resp, @PathVariable(value="id") String id, @RequestParam(value="orientation", required=false, defaultValue="0") String orientation) {
        Website getwebsite = null;
        Status status = new Status();
        if (orientation.equalsIgnoreCase(null) || orientation.equalsIgnoreCase("null") || id.equalsIgnoreCase(null) || id.equalsIgnoreCase("null")) {
            status.setId(-1);
            status.setMessage("Bad Request");
            return new ResponseEntity<Status>(status, HttpStatus.BAD_REQUEST);
        }
        status.setId(Integer.parseInt(id));
        String resources = this.servletContext.getRealPath(WEB_INF_ADDRESS) + id + "\\";
        try {
            getwebsite = this.websiteService.getEntityById(Integer.parseInt(id));
        }
        catch (Exception e) {
            status.setMessage("Error");
            return new ResponseEntity<Status>(status, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Path path = Paths.get(resources + getwebsite.getFolderName() + ".zip", new String[0]);
        if (orientation.equalsIgnoreCase("1")) {
            path = Paths.get(resources + getwebsite.getFolderName() + "_right.zip", new String[0]);
        }
        if (!new File(path.toString()).exists()) {
            status.setMessage("No Content");
            return new ResponseEntity<Status>(status, HttpStatus.NO_CONTENT);
        }
        File toServeUp = new File(path.toString());
        resp.setHeader("Content-Disposition", "attachment; filename=\"website.zip\"");
        try (FileInputStream is = new FileInputStream(toServeUp);
             ServletOutputStream os = resp.getOutputStream();){
            int read = 0;
            byte[] bytes = new byte[2048];
            while ((read = ((InputStream)is).read(bytes)) != -1) {
                os.write(bytes, 0, read);
            }
            os.flush();
            status.setMessage("success");
        }
        catch (FileNotFoundException e) {
            status.setMessage("fail");
        }
        catch (IOException e) {
            status.setMessage("fail");
        }
        return new ResponseEntity<Status>(status, HttpStatus.OK);
    }

    @GetMapping(value={"/website/getphoto/{id}"})
    public ResponseEntity<byte[]> getImage(@PathVariable(value="id") String id, HttpServletResponse response) throws IOException {
        String resources = this.servletContext.getRealPath(WEB_INF_ADDRESS) + id + "/";
        Website getwebsite = null;
        try {
            getwebsite = this.websiteService.getEntityById(Integer.parseInt(id));
        }
        catch (Exception e) {
            this.LOG.error(e.getMessage(), e);
        }
        HttpHeaders headers = new HttpHeaders();
        byte[] b = new byte[]{};
        try (RandomAccessFile f = new RandomAccessFile(resources + getwebsite.getFolderName() + ".png", "r");){
            b = new byte[(int)f.length()];
            f.readFully(b);
            headers.setContentType(MediaType.IMAGE_PNG);
        }
        catch (Exception e) {
            this.LOG.error(e.getMessage(), e);
        }
        return new ResponseEntity<byte[]>(b, (MultiValueMap<String, String>)headers, HttpStatus.CREATED);
    }

    private String writefile(String resources, MultipartFile[] files, String con) {
        String fileName = null;
        StringBuilder sbld = new StringBuilder();
        sbld.append(con).append(" : ");
        if (files != null && files.length > 0) {
            for (MultipartFile item : files) {
                if (item.isEmpty()) continue;
                fileName = item.getOriginalFilename();
                System.out.println(resources + fileName);
                try {
                    item.transferTo(new File(resources + fileName));
                    sbld.append("success");
                }
                catch (IOException | IllegalStateException e) {
                    sbld.append("fail");
                }
            }
        } else {
            sbld.append("empty");
        }
        return sbld.toString() + ",";
    }
}

