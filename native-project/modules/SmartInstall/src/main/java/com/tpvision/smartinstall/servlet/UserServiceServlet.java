package com.tpvision.smartinstall.servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tpvision.smartinstall.dao.core.Profile;
import com.tpvision.smartinstall.dao.core.ProfileRole;
import com.tpvision.smartinstall.dao.core.Role;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.ProfileManager;
import com.tpvision.smartinstall.dao.mgr.ProfileRoleManager;
import com.tpvision.smartinstall.dao.mgr.RoleManager;
import com.tpvision.smartinstall.util.UserEmailMonitorHelper;
import com.tpvision.smartinstall.util.UserManagerUtils;
import com.tpvision.smartinstall.util.Utils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserServiceServlet {
   private static final Logger LOG = LoggerFactory.getLogger(UserServiceServlet.class);

   @PostMapping("/updateEmail")
   public void updateUserEmail(HttpServletRequest request, HttpServletResponse response) {
      if (!Utils.isAdmin()) {
         Utils.renderErrorJsonMsg("only admin role user can update user email", response);
      } else {
         String username = request.getParameter("username");
         String email = request.getParameter("email");
         Profile profile = JpaManager.getProfileManager().loadByKey(username);
         if (profile != null) {
            profile.setEmail(email);
            JpaManager.getProfileManager().save(profile);
         }

         Utils.renderSuccessJsonData(response);
      }
   }

   @PostMapping("/updateMonitorStatus")
   public void updateMonitorStatus(HttpServletRequest request, HttpServletResponse response) {
      if (!Utils.isAdmin()) {
         Utils.renderErrorJsonMsg("only admin role user can update user monitor status", response);
      } else {
         String username = request.getParameter("userName");
         String checkStatus = request.getParameter("checkStatus");
         Profile profile = JpaManager.getProfileManager().loadByKey(username);
         if (profile != null) {
            profile.setExtn(checkStatus);
            JpaManager.getProfileManager().save(profile);
         }

         Utils.renderSuccessJsonData(response);
      }
   }

   @PostMapping("/updateMonitorConfig")
   public void updateMonitorConfig(HttpServletRequest request, HttpServletResponse response) {
      if (!Utils.isAdmin()) {
         Utils.renderErrorJsonMsg("only admin role user can update user monitor config", response);
      } else {
         String username = request.getParameter("userName");
         String monitorConfig = request.getParameter("monitorConfig");
         Profile profile = JpaManager.getProfileManager().loadByKey(username);
         if (profile != null) {
            profile.setMonitorConfig(monitorConfig);
            JpaManager.getProfileManager().save(profile);
         }

         Utils.renderSuccessJsonData(response);
      }
   }

   @PostMapping("/list")
   public void getUserList4Page(HttpServletRequest request, HttpServletResponse response) {
      if (Utils.isAdmin()) {
         JsonObject data = new JsonObject();
         int current = Integer.parseInt(request.getParameter("current"));
         int rowCount = Integer.parseInt(request.getParameter("rowCount"));
         String searchPhrase = request.getParameter("searchPhrase");
         String sortId = request.getParameter("sort[id]");
         String filter = request.getParameter("filter");
         String sortUsername = request.getParameter("sort[username]");
         String sortRole = request.getParameter("sort[role]");
         LOG.info("[Admin-user]current={},rowCount={}", current, rowCount);
         String sort = "";
         if (null != sortId) {
            filter = "id";
            sort = "id&" + sortId;
         } else if (null != sortUsername) {
            filter = "username";
            sortId = sortUsername;
            sort = "username&" + sortUsername;
         } else if (null != sortRole) {
            filter = "role";
            sortId = sortRole;
            sort = "role&" + sortRole;
         } else {
            sortId = "desc";
            sort = "id&desc";
         }

         if (StringUtils.isBlank(filter)) {
            filter = "id";
         }

         JSONArray finalResult = UserManagerUtils.queryUserList(filter, sortId, request);
         int count = finalResult.length();
         Map<String, String> params = new HashMap<>();
         params.put("Admin_tabsList_page", String.valueOf(current));
         params.put("Admin_tabsList_dropdownText", String.valueOf(rowCount));
         params.put("Admin_tabsList_search", searchPhrase);
         params.put("Admin_tabsList_sort", sort);
         Utils.updateUserProfileConfig(params);
         RoleManager rm = JpaManager.getRoleManager();
         ProfileManager manager = JpaManager.getProfileManager();
         List<Role> rs = rm.loadAll();
         JsonArray array = new JsonArray();
         int offset = 0;
         if (current > 0 && rowCount > 0) {
            offset = (current - 1) * rowCount;
         }

         for (int i = offset; i < finalResult.length(); i++) {
            JsonObject jsonObj = new JsonObject();
            JSONObject resobj = (JSONObject)finalResult.get(i);
            int roleid = 1;
            String role = resobj.optString("role");
            String username = resobj.optString("username");

            for (Role r : rs) {
               if (r.getName().contains(role)) {
                  roleid = r.getIdrole();
                  break;
               }
            }

            ProfileRoleManager prm = JpaManager.getProfileRoleManager();
            ProfileRole pr = null;
            List<ProfileRole> prs = prm.loadByProfileId(username);
            if (prs.isEmpty()) {
               pr = new ProfileRole();
               pr.setProfileId(username);
               pr.setRoleIdrole(roleid);
               prm.save(pr);
            } else {
               pr = prs.get(0);
               if (pr.getRoleIdrole() != roleid) {
                  prm.deleteByKey(pr.getProfileId(), pr.getRoleIdrole());
                  pr = new ProfileRole();
                  pr.setProfileId(username);
                  pr.setRoleIdrole(roleid);
                  prm.save(pr);
               }
            }

            Profile profile = manager.loadByKey(username);
            String email = profile == null ? "" : profile.getEmail();
            jsonObj.addProperty("email", email);
            jsonObj.addProperty("monitoring", UserEmailMonitorHelper.isSupportNotice(profile));
            jsonObj.addProperty("monitorConfig", UserEmailMonitorHelper.resolveProfileMonitorConfig(profile));
            boolean found = true;

            for (String keyStr : resobj.keySet()) {
               Object keyvalue = resobj.get(keyStr);
               jsonObj.addProperty(keyStr, String.valueOf(keyvalue));
            }

            if (StringUtils.isNotBlank(searchPhrase)) {
               String name = String.valueOf(resobj.get("username"));
               if (name.contains(searchPhrase)) {
                  found = true;
               } else {
                  found = false;
               }
            }

            if (found) {
               array.add(jsonObj);
            }
         }

         data.addProperty("current", current);
         data.addProperty("rowCount", rowCount);
         data.addProperty("total", count);
         data.add("rows", array);
         Utils.writeJsonToResponse(data.toString(), response);
      }
   }

   @PostMapping("/resetAdminPassword")
   public void resetAdminPassword(HttpServletRequest request, HttpServletResponse response) {
      if (!Utils.isAdmin()) {
         Utils.renderErrorJsonMsg("only admin role user can reset admin password", response);
      } else {
         JSONObject status = new JSONObject();
         status.put("status", "fail");
         String userId = request.getParameter("uid");
         String uname = request.getParameter("uname");
         String password = request.getParameter("p");
         String resetPassword = request.getParameter("rp");
         String role = request.getParameter("role");
         if (!password.equals(resetPassword)) {
            Utils.renderErrorJsonMsg("admin password data error", response);
         } else {
            try {
               String res = UserManagerUtils.modifyUserInfo(userId, uname, resetPassword, role, request);
               status.put("status", res);
            } catch (Exception e) {
               LOG.error(e.getMessage(), e);
            }

            Utils.writeJsonToResponse(status.toString(), response);
         }
      }
   }

   @PostMapping("/resetPassword")
   public void resetUserPassword(HttpServletRequest request, HttpServletResponse response) throws NullPointerException {
      if (!Utils.isAdmin()) {
         Utils.renderErrorJsonMsg("only admin role user can reset user password", response);
      } else {
         JSONObject status = new JSONObject();
         status.put("status", "fail");
         String uname = Utils.getAuthenticationName();
         String password = request.getParameter("p");
         String repeatPassowrd = request.getParameter("rp");
         if (!password.equals(repeatPassowrd)) {
            Utils.renderErrorJsonMsg("password data error", response);
         } else {
            try {
               String res = UserManagerUtils.modifyUserPassword(uname, password, request);
               status.put("status", res);
            } catch (Exception e) {
               LOG.error(e.getMessage(), e);
            }

            String currentUserName = Utils.getAuthenticationName().trim();
            ProfileManager profileManager = JpaManager.getProfileManager();
            Profile profile = profileManager.loadByKey(currentUserName);
            profile.setFirstLogin("false");
            profileManager.save(profile);
            Utils.writeJsonToResponse(status.toString(), response);
         }
      }
   }

   @PostMapping("/add")
   public void addUser(HttpServletRequest request, HttpServletResponse response) {
      if (!Utils.isAdmin()) {
         Utils.renderErrorJsonMsg("only admin role user can add user", response);
      } else {
         JSONObject status = new JSONObject();
         status.put("status", "fail");
         String userId = request.getParameter("uid").trim();
         String password = request.getParameter("p");
         String repeartPassword = request.getParameter("rp");
         String role = request.getParameter("role");
         String email = request.getParameter("email");
         if (password.equals(repeartPassword)) {
            try {
               String res = UserManagerUtils.addUser(userId, password, role, request);
               status.put("status", res);
               if (null != res && res.equalsIgnoreCase("success")) {
                  this.setUserProfile(userId, role, email);
               }
            } catch (Exception e) {
               LOG.error(e.getMessage(), e);
            }
         }

         Utils.writeJsonToResponse(status.toString(), response);
      }
   }

   @PostMapping("/modifyRole")
   public void modifyUserRole(HttpServletRequest request, HttpServletResponse response) {
      if (!Utils.isAdmin()) {
         Utils.renderErrorJsonMsg("only admin role user can update user role", response);
      } else {
         JSONObject status = new JSONObject();
         status.put("status", "fail");
         String userId = request.getParameter("uid");
         String role = request.getParameter("role");
         String username = request.getParameter("username");
         if (!"ROLE_ADMIN".contains(role.toUpperCase())) {
            List<String> adminIds = JpaManager.getProfileManager().findUserIdsByRole("ROLE_ADMIN");
            if (adminIds.size() == 1 && StringUtils.equalsIgnoreCase(username, adminIds.get(0))) {
               Utils.renderErrorJsonMsg("You must have at least one account with the Administrator role", response);
               return;
            }
         }

         try {
            String res = UserManagerUtils.modifyUserRole(userId, role, request);
            if (null != res && res.equalsIgnoreCase("success")) {
               this.setUserProfile(username, role, null);
            }

            status.put("status", res);
         } catch (Exception e) {
            LOG.error(e.getMessage(), e);
         }

         Utils.writeJsonToResponse(status.toString(), response);
      }
   }

   @PostMapping("/update")
   public void updateUser(HttpServletRequest request, HttpServletResponse response) {
      if (!Utils.isAdmin()) {
         Utils.renderErrorJsonMsg("only admin role user can update/delete user", response);
      } else {
         JSONObject status = new JSONObject();
         status.put("status", "fail");
         String userId = request.getParameter("uid");
         String uname = request.getParameter("uname");
         String flag = request.getParameter("flag");
         String role = request.getParameter("role");
         if ("delete".equalsIgnoreCase(flag)) {
            List<String> adminIds = JpaManager.getProfileManager().findUserIdsByRole("ROLE_ADMIN");
            if (adminIds.size() == 1 && StringUtils.equalsIgnoreCase(uname, adminIds.get(0))) {
               Utils.renderErrorJsonMsg("You must have at least one account with the Administrator role", response);
               return;
            }

            try {
               String res = UserManagerUtils.removeUser(userId, request);
               status.put("status", res);
               if (null != res && res.equalsIgnoreCase("success")) {
                  this.deleteUserProfile(uname);
               }
            } catch (Exception e) {
               LOG.error(e.getMessage(), e);
            }
         } else {
            String password = request.getParameter("p");
            String newpassword = request.getParameter("rp");
            if (password.equals(newpassword)) {
               try {
                  String res = UserManagerUtils.modifyUserInfo(userId, uname, newpassword, role, request);
                  status.put("status", res);
               } catch (Exception e) {
                  LOG.error(e.getMessage(), e);
               }
            }
         }

         Utils.writeJsonToResponse(status.toString(), response);
      }
   }

   private boolean deleteUserProfile(String userId) {
      JpaManager.getProfileRoleManager().deleteByKey(userId);
      JpaManager.getProfileManager().deleteByKey(userId);
      return true;
   }

   private void setUserProfile(String userId, String role, String email) {
      ProfileManager pm = JpaManager.getProfileManager();
      Profile p = pm.loadByKey(userId);
      if (null == p) {
         p = new Profile();
         p.setId(userId);
         String uname = Utils.getAuthenticationName();
         p.setCreatedBy(uname);
         p.setPassword(DigestUtils.md5Hex("passowrd"));
      }

      if (email != null) {
         p.setEmail(email);
      }

      if (p.getCreatedDate() == null) {
         p.setCreatedDate(new Date());
      }

      p.setLastUpdatedDate(new Date());
      pm.save(p);
      int roleid = 1;
      RoleManager rm = JpaManager.getRoleManager();

      for (Role r : rm.loadAll()) {
         if (r.getName().contains(role.toUpperCase())) {
            roleid = r.getIdrole();
            break;
         }
      }

      ProfileRoleManager prm = JpaManager.getProfileRoleManager();
      List<ProfileRole> prs = prm.loadByProfileId(userId);
      if (prs.isEmpty()) {
         ProfileRole pr = new ProfileRole();
         pr.setProfileId(userId);
         pr.setRoleIdrole(roleid);
         prm.save(pr);
      } else {
         for (ProfileRole pr : prs) {
            if (pr.getRoleIdrole() != roleid) {
               prm.deleteByKey(pr.getProfileId(), pr.getRoleIdrole());
               pr = new ProfileRole();
               pr.setProfileId(userId);
               pr.setRoleIdrole(roleid);
               prm.save(pr);
            }
         }
      }
   }
}
