/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.servlet.menuBar;
import com.tpvision.smartinstall.util.Utils;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(value={"/IndexServlet"})
public class IndexServlet
extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(IndexServlet.class);
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cmndVersion = Utils.getCMNDMajorVersion();
        if ("".equals(cmndVersion)) {
            cmndVersion = "7.0.1";
        }
        String navbarData = menuBar.getMenuBar(request, response, cmndVersion);
        try (PrintWriter writer = response.getWriter();){
            writer.println(navbarData);
            writer.flush();
        }
        catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }
}

