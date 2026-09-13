/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.pms.ImpalaTmsUtils;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.pms.TmsUtils;
import com.tpvision.smartinstall.servlet.BaseHttpServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(value={"/ImpalaService"})
public class ImpalaWebhookServlet
extends BaseHttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(ImpalaTmsUtils.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.responseText("Impala webhook handler, please using post to access", response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TmsUtils tms = PmsUtils.getTmsInstance();
        if (tms instanceof ImpalaTmsUtils) {
            try {
                String content = this.requestContent(request);
                String signature = request.getHeader("X-Impala-Signature");
                if (!((ImpalaTmsUtils)tms).checkWebhookAuthenticity(content, signature)) {
                    LOG.error("check webhook Authenticity failed,not valid impala webhook data");
                    return;
                }
                JSONObject obj = new JSONObject(content);
                ((ImpalaTmsUtils)tms).processWebhooks(obj);
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        } else {
            LOG.info("Impala PMS is not enabled");
        }
    }
}

