/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.dao.core.ProfileRole;
import com.tpvision.smartinstall.dao.core.Role;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.ProfileRoleManager;
import com.tpvision.smartinstall.dao.mgr.RoleManager;
import com.tpvision.smartinstall.util.Configs;
import com.tpvision.smartinstall.util.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@WebServlet(urlPatterns={"/rest/api/menubar/*"})
public class menuBar
extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(menuBar.class);
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cmndVersion = Utils.getCMNDMajorVersion();
        if ("".equals(cmndVersion)) {
            cmndVersion = "7.0.1";
        }
        String navbar = menuBar.getMenuBar(request, response, cmndVersion);
        response.setHeader("Access-Control-Allow-Origin", "*");
        try (PrintWriter writer = response.getWriter();){
            writer.println(navbar);
            writer.flush();
        }
        catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    public static String getMenuBar(HttpServletRequest request, HttpServletResponse response, String cmndVersion) {
        String username = request.getParameter("user");
        if (null == username) {
            username = Utils.getAuthenticationName();
        }
        String user = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (null != auth && auth.isAuthenticated()) {
            user = auth.getName();
        }
        boolean isAdminUser = false;
        if (null == user || user.equalsIgnoreCase("anonymousUser")) {
            RoleManager rm = JpaManager.getRoleManager();
            List<Role> rs = rm.loadAll();
            ProfileRoleManager prm = JpaManager.getProfileRoleManager();
            List<ProfileRole> prs = prm.loadByProfileId(username);
            for (ProfileRole pr : prs) {
                if (pr.getRoleIdrole() != rs.get(0).getIdrole()) continue;
                isAdminUser = true;
                break;
            }
        } else {
            isAdminUser = Utils.isAdmin();
        }
        boolean isSecureLink = request.getScheme().startsWith("https") || StringUtils.equalsIgnoreCase("1", request.getParameter("secure"));
        String domainUrl = "";
        domainUrl = isSecureLink ? "https://" + request.getServerName() + ":" + Configs.getProperty("tomcat.https.port", 8443) : "http://" + request.getServerName() + ":" + Configs.getProperty("tomcat.http.port", 8080);
        String siUrl = domainUrl + "/SmartInstall/";
        String hitSelectNavScript = siUrl + "static/js/hitSelectNav.js";
        String aboutRef = siUrl + "about.jsp";
        String adminRef = "#";
        if (isAdminUser) {
            adminRef = siUrl + "admin.jsp";
        }
        String controlUrl = domainUrl + "/smartcontrol/";
        String cmsUrl = "";
        cmsUrl = isSecureLink ? "https://" + request.getServerName() + ":" + Configs.getProperty("cms.port.https", 8444) + "/SmartCMS/" : "http://" + request.getServerName() + ":" + Configs.getProperty("cms.port", 8082) + "/SmartCMS/";
        return "<nav class=\"navbar navbar-inverse\" role=\"navigation\" style=\"border-radius: 0px;\"><div class=\"navbar-header\"><button type=\"button\" class=\"navbar-toggle collapsed\" data-toggle=\"collapse\" data-target=\"#navbar\" aria-expanded=\"false\" aria-controls=\"navbar\"><span class=\"sr-only\">Toggle navigation</span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span><span class=\"icon-bar\"></span></button><a href=\"#\"><img alt=\"Brand\" style=\"padding-left: 3px; height:50px; float:left;\" src=\"" + siUrl + "static/images/cmnd.png\"></a><p id=\"nav_sys_ver\" class=\"navbar-text\">" + cmndVersion + "</p></div><div id=\"navbar\" class=\"navbar_real collapse navbar-collapse\"><ul class=\"nav navbar-nav navbar-right\" style=\"margin-right:0px;\"><li><a id=\"nav_monitors\" href=\"" + controlUrl + "\">Monitors</a></li><li><a id=\"nav_tvs\" href=\"" + siUrl + "dev?type=index\"> TVs </a></li><li><a id=\"nav_files\" href=\"" + siUrl + "getFile?mode=index\">Files</a></li><li><a id=\"nav_cms\" href=\"" + cmsUrl + "\"> Create </a></li><li><a id=\"nav_admin\" href=\"" + adminRef + "\">Admin</a></li><li><a id=\"nav_about\" href=\"" + aboutRef + "\">Documentation</a></li><li><a id=\"nav_logout\" href=\"" + siUrl + "logout\" onclick=\"clearls()\">Logout</a></li><li>&nbsp;&nbsp;</li></ul></div></nav><script type=\"text/javascript\" src=\"" + hitSelectNavScript + "\"></script>";
    }
}

