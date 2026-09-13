/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.japit.remotecontrol.RemoteControlHelper;
import com.tpvision.smartinstall.servlet.BaseHttpServlet;
import com.tpvision.smartinstall.util.PlatformUtils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(value={"/remote"})
public class RemoteControlServlet
extends BaseHttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(RemoteControlServlet.class);
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method;
        switch (method = request.getParameter("method")) {
            case "request": {
                this.requestSetting(request, response);
                break;
            }
            case "change": {
                this.changeSetting(request, response);
                break;
            }
            default: {
                LOG.error("not support method:{}", (Object)method);
            }
        }
    }

    private void changeSetting(HttpServletRequest request, HttpServletResponse response) {
        String tvid = request.getParameter("tvid");
        String groupName = request.getParameter("groupName");
        String settingName = request.getParameter("settingName");
        String value = request.getParameter("value");
        String status = this.changeSetting(tvid, groupName, settingName, value);
        this.responseJSON(status, response);
    }

    private void requestSetting(HttpServletRequest request, HttpServletResponse response) {
        String tvid = request.getParameter("tvid");
        String groupName = request.getParameter("groupName");
        String settingName = request.getParameter("settingName");
        String status = this.requestSetting(tvid, settingName, groupName);
        this.responseJSON(status, response);
    }

    public String requestSetting(String tvid, String settingName, String groupName) {
        String resp = "";
        try {
            RemoteControlHelper helper = RemoteControlHelper.getInstance(tvid, groupName);
            RemoteControlType settingType = RemoteControlType.valueOf(settingName);
            switch (settingType) {
                case Application: {
                    resp = this.successStatus(helper.requestActiveApplication());
                    break;
                }
                case ApplicationList: {
                    resp = this.successStatus(helper.getValueList(RemoteControlType.ApplicationList.name()));
                    break;
                }
                case ChannelList: {
                    resp = this.successStatus(RemoteControlHelper.generateChannelList());
                    break;
                }
                case Channel: {
                    resp = this.successStatus(helper.requestCurrentChannel());
                    break;
                }
                case Mute: {
                    resp = this.successStatus(helper.requestAudioMute());
                    break;
                }
                case Power: {
                    resp = this.successStatus(helper.requestPower());
                    break;
                }
                case Source: {
                    resp = this.successStatus(helper.requestSource());
                    break;
                }
                case SourceList: {
                    resp = this.successStatus(helper.requestSourceList());
                    break;
                }
                case Volume: {
                    resp = this.successStatus(helper.requestAudioVolume());
                    break;
                }
                case ManualTrigger: {
                    resp = this.successStatus(helper.requestManualTriggerMap());
                    break;
                }
                case WakeupOnLan: {
                    resp = this.successStatus(String.valueOf(PlatformUtils.isSupportWakeupOnLanViaRemoteControl(helper.getPlatform())));
                    break;
                }
                default: {
                    throw new IOException("unsupported setting to request:" + settingName);
                }
            }
        }
        catch (Exception e) {
            LOG.info(e.getMessage(), e);
            resp = this.failedStatus(e.getMessage());
        }
        return resp;
    }

    public String changeSetting(String tvid, String groupName, String settingName, String value) {
        String resp = "";
        try {
            RemoteControlHelper helper = RemoteControlHelper.getInstance(tvid, groupName);
            helper.changeSetting(settingName, value);
            resp = this.successStatus();
        }
        catch (Exception e) {
            LOG.info(e.getMessage(), e);
            resp = this.failedStatus(e.getMessage());
        }
        return resp;
    }

    public static enum RemoteControlType {
        ApplicationList,
        SourceList,
        ChannelList,
        Power,
        Source,
        Mute,
        Volume,
        Channel,
        Application,
        Enabler,
        ManualTrigger,
        WakeupOnLan;

    }
}

