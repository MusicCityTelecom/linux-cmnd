/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall.servlet;

import com.tpvision.smartinstall.dao.core.GuestInfo;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.dao.mgr.WakeupInfoManager;
import com.tpvision.smartinstall.pms.PmsException;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.pms.SOAPUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import com.tpvision.smartinstall.util.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TigerTmsServlet
extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(TigerTmsServlet.class);
    public static final String DEFULAT_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        StringBuilder sb = new StringBuilder();
        String requestHeader = "";
        try {
            request.setCharacterEncoding("utf8");
        }
        catch (UnsupportedEncodingException e2) {
            LOG.error(e2.getMessage());
        }
        try (BufferedReader reader = request.getReader();){
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        String data = null;
        try {
            requestHeader = sb.toString();
            Iterator<?> msgIt = this.getSoapMsgIterator(requestHeader);
            if (null != msgIt) {
                while (msgIt.hasNext()) {
                    Element funResults = (Element)msgIt.next();
                    String msg = funResults.getStringValue();
                    this.handleTmsMessage(msg);
                }
            }
            data = SOAPUtils.getResponseMessage("success");
        }
        catch (Exception e) {
            data = SOAPUtils.getResponseMessage("FAILED \u2013 " + e.getMessage());
        }
        LOG.info("SI >>>>>>>> TMS {}", (Object)data);
        Utils.writeToResponse(data, "text/xml", response);
    }

    private void handleTmsMessage(String msg) throws Exception {
        LOG.info("TigerTMS received:{}", (Object)msg);
        Document msgDoc = new SAXReader().read(new StringReader(msg));
        Element rootElt = msgDoc.getRootElement();
        Element error = rootElt.element("error");
        if (error != null && !StringUtils.isEmpty(error.getTextTrim())) {
            LOG.error("Tiger tms error: {}", (Object)error.getTextTrim());
            return;
        }
        this.checkResNoExists(rootElt);
        String roomId = rootElt.elementTextTrim("room");
        if ("checkinresults".equalsIgnoreCase(rootElt.getName())) {
            LOG.info("TigerTMS checkin");
            this.checkNodesExists(rootElt, "room,guestid");
            GuestInfo guest = PmsUtils.saveCheckinResults2DB(rootElt);
            PmsUtils.processCheckin(guest.getGuestId());
        } else if ("checkoutresults".equalsIgnoreCase(rootElt.getName())) {
            LOG.info("TigerTMS checkout");
            this.checkNodesExists(rootElt, "room,guestid");
            GuestInfo guest = PmsUtils.saveCheckoutResults2DB(rootElt);
            if (guest != null) {
                PmsUtils.processCheckout(guest.getRoomid());
            } else {
                LOG.info("guest not found:{}", (Object)rootElt);
            }
        } else if ("excheckoutresults".equalsIgnoreCase(rootElt.getName())) {
            LOG.info("TigerTMS express checkout");
            PmsUtils.removeRequestTimer(PmsUtils.RequestType.RequestExpressCheckout, roomId);
            String reply = rootElt.elementTextTrim("reply");
            Element status = rootElt.addElement("status");
            if (reply != null && reply.equalsIgnoreCase("Complete")) {
                status.setText("true");
            } else {
                LOG.warn("express checkout reply:{}", (Object)reply);
                status.setText("Others");
            }
            this.checkNodesExists(rootElt, "room");
            PmsUtils.processExpressCheckoutFromPms(rootElt);
        } else if ("roommoveresults".equalsIgnoreCase(rootElt.getName())) {
            LOG.info("TigerTMS Move room");
            this.checkNodesExists(rootElt, "room,roomold");
            String roomold = rootElt.elementTextTrim("roomold");
            String room = rootElt.elementTextTrim("room");
            PmsUtils.changeGuestRoom(roomold, room);
        } else if ("editguestresults".equalsIgnoreCase(rootElt.getName())) {
            LOG.info("TigerTMS edit guest");
            this.checkNodesExists(rootElt, "room,guestid");
            PmsUtils.handleGuestResults(rootElt);
        } else if ("roombillresults".equalsIgnoreCase(rootElt.getName())) {
            LOG.info("TigerTMS send bill");
            PmsUtils.removeRequestTimer(PmsUtils.RequestType.RequestBill, roomId);
            this.checkNodesExists(rootElt, "room");
            GuestInfo guest = this.saveBillItemAndBalance2DB(rootElt);
            PmsUtils.sendBill2TV(guest);
        } else if ("refreshresults".equalsIgnoreCase(rootElt.getName())) {
            LOG.info("TigerTMS send refresh results");
            String occupied = rootElt.elementTextTrim("occupied");
            if (!"N".equalsIgnoreCase(occupied)) {
                this.checkNodesExists(rootElt, "room,guestid");
            }
            GuestInfo guest = PmsUtils.saveRefreshResults2DB(rootElt);
            PmsUtils.checkin2TV(guest, false);
        } else if ("messagetextresults".equalsIgnoreCase(rootElt.getName())) {
            LOG.info("TigerTMS send message text");
            this.checkNodesExists(rootElt, "room");
            String datetime = rootElt.elementTextTrim("datetime");
            rootElt.element("datetime").setText(this.formatMessageTime(datetime));
            PmsUtils.saveMessageTextResults2DB(rootElt);
        } else if ("wakeupsetresults".equalsIgnoreCase(rootElt.getName())) {
            LOG.info("TigerTMS set wakeup");
            this.checkNodesExists(rootElt, "room");
            this.handleWakeup(rootElt, false);
        } else if ("wakeupclearresults".equalsIgnoreCase(rootElt.getName())) {
            LOG.info("TigerTMS clear wakeup");
            this.checkNodesExists(rootElt, "room");
            this.handleWakeup(rootElt, true);
        }
    }

    private void handleWakeup(Element rootElt, boolean clearFlag) {
        WakeupInfoManager wakeupInfoManager = JpaManager.getWakeupInfoManager();
        String roomNo = rootElt.elementTextTrim("room");
        try {
            String wakeupTime = TigerTmsServlet.formatWakeupTime(rootElt.elementTextTrim("wakeupdate"), rootElt.elementTextTrim("wakeuptime"));
            LOG.info("roomId:{},wakeupTime:{}", (Object)roomNo, (Object)wakeupTime);
            if (clearFlag) {
                wakeupInfoManager.deleteWakeup(roomNo, wakeupTime);
            } else {
                wakeupInfoManager.insertWakeup(roomNo, "", wakeupTime);
            }
        }
        catch (PmsException e) {
            LOG.error(e.getMessage());
        }
    }

    private Iterator<?> getSoapMsgIterator(String str) {
        Iterator<Element> msgIt = null;
        try {
            Document personDoc = new SAXReader().read(new StringReader(str));
            Element rootElt = personDoc.getRootElement();
            LOG.info("root node:{}", (Object)rootElt.getName());
            Iterator<Element> body = rootElt.elementIterator("Body");
            while (body.hasNext()) {
                Element recordEless = body.next();
                Iterator<Element> sendMessageToExternalInterface = recordEless.elementIterator("SendMessageToExternalInterface");
                while (sendMessageToExternalInterface.hasNext()) {
                    Element msgValue = sendMessageToExternalInterface.next();
                    msgIt = msgValue.elementIterator("Msg");
                    LOG.info(" TMS >>>>>>>> SI {}", (Object)msgValue.getStringValue());
                }
            }
        }
        catch (DocumentException e1) {
            LOG.error(e1.getMessage(), e1);
        }
        return msgIt;
    }

    public GuestInfo saveBillItemAndBalance2DB(Element rootElt) {
        String roomId = rootElt.elementTextTrim("room");
        String balance = rootElt.elementTextTrim("balance");
        String totalDateTime = TpvDateUtils.formatLocalDate(new Date(), DEFULAT_DATE_FORMAT);
        List<Node> billItems = rootElt.selectNodes("//item");
        PmsUtils.deletePreBillItems(roomId);
        GuestInfo gi = PmsUtils.storageBalance2GuestInfo(roomId, balance, totalDateTime);
        for (Element element : billItems) {
            element.addElement("room").setText(roomId);
            String srcDate = element.elementTextTrim("datetime");
            element.element("datetime").setText(this.formatDateTime(srcDate));
            element.addElement("displayflag").setText("Yes");
            PmsUtils.saveBillItem2DB(element);
        }
        return gi;
    }

    private String formatDateTime(String srcDatetime) {
        String[] formats;
        for (String fmt : formats = new String[]{"yyyy-MM-dd HH:mm:ss", DEFULAT_DATE_FORMAT}) {
            SimpleDateFormat df = new SimpleDateFormat(fmt);
            try {
                Date date = df.parse(srcDatetime);
                return TpvDateUtils.getBillItemDate(date);
            }
            catch (ParseException parseException) {
            }
        }
        LOG.info("not valid date time format:{}", (Object)srcDatetime);
        return srcDatetime;
    }

    private String formatMessageTime(String srcDateTime) {
        String[] formats;
        for (String fmt : formats = new String[]{"yyyy-MM-dd HH:mm:ss", DEFULAT_DATE_FORMAT}) {
            SimpleDateFormat df = new SimpleDateFormat(fmt);
            try {
                Date date = df.parse(srcDateTime);
                return TpvDateUtils.formatLocalDate(date, "yyyy-MM-dd HH:mm:ss");
            }
            catch (ParseException parseException) {
            }
        }
        LOG.info("not valid date time format:{}", (Object)srcDateTime);
        return srcDateTime;
    }

    private static String formatWakeupTime(String wakeupDate, String wakeupTime) throws PmsException {
        if (StringUtils.isEmpty(wakeupDate) || StringUtils.isEmpty(wakeupTime)) {
            throw new PmsException("wakeupDate or wakeupTime is empty");
        }
        Date date = TpvDateUtils.parseDateString(wakeupDate + " " + wakeupTime, DEFULAT_DATE_FORMAT);
        return TpvDateUtils.formatLocalDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public void checkResNoExists(Element rootElt) throws PmsException {
        if (rootElt.attributeValue("resno") == null) {
            throw new PmsException("attribute :resno not found in element");
        }
    }

    public void checkNodesExists(Element rootElt, String fieldNames) throws PmsException {
        String[] names;
        for (String name : names = fieldNames.split(",")) {
            if (rootElt.elementTextTrim(name) != null) continue;
            throw new PmsException("node :" + name + " not found in element");
        }
    }
}

