/*
 * Decompiled with CFR 0.152.
 */
package org.htng._2011b;

import com.tpvision.smartinstall.dao.core.Billitem;
import com.tpvision.smartinstall.dao.core.Devices;
import com.tpvision.smartinstall.dao.core.GuestInfo;
import com.tpvision.smartinstall.dao.core.Message;
import com.tpvision.smartinstall.dao.core.Reservation;
import com.tpvision.smartinstall.dao.mgr.JpaManager;
import com.tpvision.smartinstall.pms.PmsUtils;
import com.tpvision.smartinstall.pms.TmsUtils;
import com.tpvision.smartinstall.util.CertUtils;
import com.tpvision.smartinstall.util.TpvDateUtils;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.htng._2011b.FolioManagementClient;
import org.htng._2011b.GuestCommunicationsClient;
import org.htng._2011b.HTNGComponentRoomType;
import org.htng._2011b.HTNGHotelRoomMoveNotifRQ;
import org.htng._2011b.HTNGProfileMessageRS;
import org.htng._2011b.HTNGProfileMessageStatusType;
import org.htng._2011b.HTNGRequestBaseType;
import org.htng._2011b.HTNGResponseBaseType;
import org.htng._2011b.HTNGRoomElementType;
import org.htng._2011b.RoomStatusManagementClient;
import org.json.JSONObject;
import org.opentravel.ota._2003._05.CostingItemType;
import org.opentravel.ota._2003._05.CustomerType;
import org.opentravel.ota._2003._05.DateTimeSpanType;
import org.opentravel.ota._2003._05.ErrorType;
import org.opentravel.ota._2003._05.ErrorsType;
import org.opentravel.ota._2003._05.FormattedTextTextType;
import org.opentravel.ota._2003._05.HotelReservationsType;
import org.opentravel.ota._2003._05.PersonNameType;
import org.opentravel.ota._2003._05.ProfileType;
import org.opentravel.ota._2003._05.ProfilesType;
import org.opentravel.ota._2003._05.ResGuestType;
import org.opentravel.ota._2003._05.ResGuestsType;
import org.opentravel.ota._2003._05.RoomStaysType;
import org.opentravel.ota._2003._05.UniqueIDType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTNGHelper {
    private static final Logger LOG = LoggerFactory.getLogger(HTNGHelper.class);

    private HTNGHelper() {
    }

    public static boolean isValidURL(String url) {
        try {
            if (!StringUtils.isEmpty(url)) {
                new URL(url);
                return true;
            }
        }
        catch (MalformedURLException e) {
            LOG.error("invalid wsdlurl:{}", (Object)url);
        }
        return false;
    }

    public static FolioManagementClient getFolioClient(String wsdl) {
        if (HTNGHelper.isValidURL(wsdl)) {
            return new FolioManagementClient(wsdl);
        }
        return null;
    }

    public static RoomStatusManagementClient getRoomStatusClient(String wsdl) {
        if (HTNGHelper.isValidURL(wsdl)) {
            return new RoomStatusManagementClient(wsdl);
        }
        return null;
    }

    public static GuestCommunicationsClient getMessageClient(String wsdl) {
        if (HTNGHelper.isValidURL(wsdl)) {
            return new GuestCommunicationsClient(wsdl);
        }
        return null;
    }

    public static void configSSLClient(Object port) {
        Client client = ClientProxy.getClient(port);
        HTTPConduit http = (HTTPConduit)client.getConduit();
        try {
            TLSClientParameters parameters = new TLSClientParameters();
            parameters.setSSLSocketFactory(CertUtils.getSSLContext().getSocketFactory());
            http.setTlsClientParameters(parameters);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
        httpClientPolicy.setConnectionTimeout(36000L);
        httpClientPolicy.setAllowChunking(false);
        httpClientPolicy.setReceiveTimeout(32000L);
        http.setClient(httpClientPolicy);
    }

    public static void setConnected(boolean connected) {
        TmsUtils tms = PmsUtils.getTmsInstance();
        if (tms != null) {
            tms.setConnected(connected);
        }
    }

    public static void checkHTNGEnabled() throws IOException {
        String pmsType = PmsUtils.getTmsType();
        if (!pmsType.contains("htng")) {
            throw new IOException("HTNG is not enabled");
        }
    }

    public static void checkRoomId(HTNGComponentRoomType room) throws IOException {
        if (room == null || room.getRoomID() == null) {
            throw new IOException("Roomid not found or invalid");
        }
    }

    public static void checkTVExists(String roomId) throws IOException {
        List<Devices> tvs = PmsUtils.getTVsForRoom(roomId);
        if ((tvs == null || tvs.isEmpty()) && !PmsUtils.isAutoCreateTV()) {
            throw new IOException("TV for Room " + roomId + " not exists");
        }
    }

    public static String getSourceRoomId(HTNGHotelRoomMoveNotifRQ htngHotelRoomMoveNotifRQ) throws IOException {
        try {
            return htngHotelRoomMoveNotifRQ.getSourceRoomInformation().getRoom().getRoomID();
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new IOException("Source RoomId not found");
        }
    }

    public static String getDestinationRoomId(HTNGHotelRoomMoveNotifRQ htngHotelRoomMoveNotifRQ) throws IOException {
        try {
            return htngHotelRoomMoveNotifRQ.getDestinationRoomInformation().getRoom().getRoomID();
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new IOException("Destination RoomId not found");
        }
    }

    public static HotelReservationsType.HotelReservation extractFirstReservation(HotelReservationsType hotelReservationsType) throws IOException {
        try {
            return hotelReservationsType.getHotelReservation().get(0);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new IOException("HotelReservation not found");
        }
    }

    public static UniqueIDType generateUniqueTypeID(String type, String id) {
        UniqueIDType uniqueID = new UniqueIDType();
        uniqueID.setID(id);
        return uniqueID;
    }

    public static HTNGRequestBaseType.PropertyInfo generatePropertyInfo() {
        HTNGRequestBaseType.PropertyInfo popInfo = new HTNGRequestBaseType.PropertyInfo();
        popInfo.setChainCode("ChainCode-1044367337");
        popInfo.setBrandCode("BrandCode-582576776");
        popInfo.setHotelCode("HotelCode-869716361");
        popInfo.setHotelCityCode("HotelCityCode-1191365089");
        popInfo.setHotelName("HotelName-778779810");
        popInfo.setHotelCodeContext("HotelCodeContext-559280762");
        popInfo.setChainName("ChainName-1096278805");
        popInfo.setBrandName("BrandName-1412167194");
        popInfo.setAreaID("AreaID-1985938151");
        return popInfo;
    }

    public static JSONObject getUpdateContent(HotelReservationsType.HotelReservation res) {
        JSONObject updateContent = new JSONObject();
        CustomerType customer = null;
        try {
            customer = res.getResGuests().getResGuest().get(0).getProfiles().getProfileInfo().get(0).getProfile().getCustomer();
            String language = customer.getLanguage();
            if (!"".equalsIgnoreCase(language)) {
                TmsUtils tms = PmsUtils.getTmsInstance();
                language = tms.getMappedLanguage(language);
                updateContent.put("Language", language);
            }
        }
        catch (Exception e) {
            LOG.warn("no need to update language");
        }
        try {
            PersonNameType personName = customer.getPersonName().get(0);
            String firstName = personName.getGivenName().get(0);
            String lastName = personName.getSurname();
            updateContent.put("GuestName", firstName + " " + lastName);
        }
        catch (Exception e) {
            LOG.warn("no need to update guestName");
        }
        try {
            DateTimeSpanType timespan = res.getRoomStays().getRoomStay().get(0).getTimeSpan();
            JSONObject tobj = new JSONObject();
            tobj.put("Start", TpvDateUtils.getJapitDate(timespan.getStart()));
            tobj.put("End", TpvDateUtils.getJapitDate(timespan.getEnd()));
            updateContent.put("TimeSpan", tobj);
        }
        catch (Exception e) {
            LOG.warn("no need to update departure");
        }
        return updateContent;
    }

    public static Element getElementFromReservation(HotelReservationsType.HotelReservation res, String roomId) throws IOException {
        String resno = res.getUniqueID().get(0).getID();
        try {
            res.getRoomStays().getRoomStay().get(0).getTimeSpan();
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new IOException("Reservation TimeSpan not found");
        }
        String start = res.getRoomStays().getRoomStay().get(0).getTimeSpan().getStart();
        String end = res.getRoomStays().getRoomStay().get(0).getTimeSpan().getEnd();
        start = TpvDateUtils.getJapitDate(start);
        end = TpvDateUtils.getJapitDate(end);
        ProfilesType.ProfileInfo profileInfo = null;
        try {
            profileInfo = res.getResGuests().getResGuest().get(0).getProfiles().getProfileInfo().get(0);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new IOException("Reservation guest profile not found");
        }
        try {
            profileInfo.getProfile().getCustomer().getPersonName().get(0);
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new IOException("Reservation guest personName not found");
        }
        String guestId = profileInfo.getUniqueID().get(0).getID();
        String title = profileInfo.getProfile().getCustomer().getPersonName().get(0).getNamePrefix().get(0);
        String first = profileInfo.getProfile().getCustomer().getPersonName().get(0).getGivenName().get(0);
        String last = profileInfo.getProfile().getCustomer().getPersonName().get(0).getSurname();
        String lang = profileInfo.getProfile().getCustomer().getLanguage();
        Element rootElt = DocumentHelper.createDocument().addElement("checkinresults");
        rootElt.addAttribute("resno", resno);
        rootElt.addElement("room").setText(roomId);
        rootElt.addElement("guestid").setText(guestId);
        rootElt.addElement("title").setText(title);
        rootElt.addElement("last").setText(last);
        rootElt.addElement("first").setText(first);
        rootElt.addElement("lang").setText(lang);
        rootElt.addElement("group").setText("");
        rootElt.addElement("arrival").setText(start);
        rootElt.addElement("departure").setText(end);
        rootElt.addElement("tv").setText("Standard");
        rootElt.addElement("viewbill").setText("True");
        rootElt.addElement("expressco").setText("False");
        LOG.info("checinresult:{}", (Object)rootElt);
        return rootElt;
    }

    public static void fillResponseProperties(HTNGResponseBaseType res) {
        res.setEchoToken("EchoToken-2092234436");
        res.setVersion(new BigDecimal("2.0"));
    }

    public static List<HTNGProfileMessageRS.ProfileMessages.ProfileMessage> getMessages(String roomId) {
        ArrayList<HTNGProfileMessageRS.ProfileMessages.ProfileMessage> profileMessageList = new ArrayList<HTNGProfileMessageRS.ProfileMessages.ProfileMessage>();
        List<Message> messages = JpaManager.getMessageManager().findMessageByGuestIds(roomId);
        for (Message msg : messages) {
            HTNGProfileMessageRS.ProfileMessages.ProfileMessage profileMessage = new HTNGProfileMessageRS.ProfileMessages.ProfileMessage();
            profileMessage.setMessageID(String.valueOf(msg.getId()));
            FormattedTextTextType text = new FormattedTextTextType();
            text.setValue(msg.getContent());
            text.setTextFormat("PlainText");
            text.setFormatted(true);
            JAXBElement<FormattedTextTextType> content = new JAXBElement<FormattedTextTextType>(new QName("Text"), FormattedTextTextType.class, text);
            profileMessage.getTextOrImageOrURL().add(content);
            try {
                Date send = TpvDateUtils.parseMessageDate(msg.getTimeSend());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                profileMessage.setCreateDateTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(df.format(send)));
            }
            catch (DatatypeConfigurationException e) {
                LOG.error(e.getMessage(), e);
            }
            profileMessage.setStatus(HTNGHelper.messageStatusToHTNG(msg.getStatus()));
            profileMessageList.add(profileMessage);
        }
        return profileMessageList;
    }

    public static HTNGRoomElementType generateRoom(String roomId) {
        HTNGRoomElementType room = new HTNGRoomElementType();
        room.setRoomID(roomId);
        return room;
    }

    public static List<CostingItemType> getBillItems(String roomId) {
        ArrayList<CostingItemType> costingItemList = new ArrayList<CostingItemType>();
        List<Billitem> billitems = JpaManager.getBillitemManager().findBillitemsByRoomId(roomId);
        for (Billitem item : billitems) {
            CostingItemType costingItem = new CostingItemType();
            costingItem.setDescription(item.getBillItemDisplayName());
            CostingItemType.ExtendedCost extendedCost = new CostingItemType.ExtendedCost();
            extendedCost.setAmount(new BigDecimal(item.getBillItemAmount()));
            costingItem.setExtendedCost(extendedCost);
            costingItemList.add(costingItem);
        }
        return costingItemList;
    }

    public static ResGuestType generateResGuestType(String guestId) {
        ResGuestType resGuest = new ResGuestType();
        GuestInfo gi = JpaManager.getGuestInfoManager().loadByKey(guestId);
        if (gi == null) {
            gi = new GuestInfo();
            gi.setGuestId(guestId);
            gi.setTitle("Mr.");
            gi.setGuestName("John Smith");
            gi.setGuestLanguage("eng");
        }
        PersonNameType person = new PersonNameType();
        person.getNamePrefix().add(gi.getTitle());
        person.getGivenName().add(gi.getGuestName());
        person.setSurname("");
        CustomerType customer = new CustomerType();
        customer.getPersonName().add(person);
        customer.setLanguage(gi.getGuestLanguage());
        ProfileType profile = new ProfileType();
        profile.setCustomer(customer);
        ProfilesType.ProfileInfo info = new ProfilesType.ProfileInfo();
        info.setProfile(profile);
        UniqueIDType uniqueId = HTNGHelper.generateUniqueTypeID("1", PmsUtils.extractGuestId(guestId));
        info.getUniqueID().add(uniqueId);
        ProfilesType profilesType = new ProfilesType();
        profilesType.getProfileInfo().add(info);
        resGuest.setProfiles(profilesType);
        return resGuest;
    }

    public static ResGuestsType generateResGuests(String guestId) {
        ResGuestsType resGuests = new ResGuestsType();
        ArrayList<ResGuestType> resGuestList = new ArrayList<ResGuestType>();
        ResGuestType resGuest = HTNGHelper.generateResGuestType(guestId);
        resGuestList.add(resGuest);
        resGuests.getResGuest().addAll(resGuestList);
        return resGuests;
    }

    public static HotelReservationsType.HotelReservation generateHoltelReservation(String resId) {
        Reservation res = JpaManager.getReservationManager().loadByKey(resId);
        if (res == null) {
            res = new Reservation();
            res.setReservationId(resId);
            res.setStatus("Checked In");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            res.setStartTime(df.format(new Date()));
            res.setEndTime(df.format(new Date()));
        }
        HotelReservationsType.HotelReservation hotelReservationVal1 = new HotelReservationsType.HotelReservation();
        hotelReservationVal1.setResStatus(res.getStatus());
        RoomStaysType.RoomStay roomStay = new RoomStaysType.RoomStay();
        DateTimeSpanType timespan = new DateTimeSpanType();
        timespan.setStart(res.getStartTime());
        timespan.setEnd(res.getEndTime());
        roomStay.setTimeSpan(timespan);
        RoomStaysType roomStaysType = new RoomStaysType();
        roomStaysType.getRoomStay().add(roomStay);
        hotelReservationVal1.setRoomStays(roomStaysType);
        List<GuestInfo> gis = JpaManager.getGuestInfoManager().findGuestInfosByRoomid(res.getRooms());
        String guestId = gis != null && !gis.isEmpty() ? gis.get(0).getGuestId() : PmsUtils.generateNewGuestId("GST123", res.getRooms());
        hotelReservationVal1.setResGuests(HTNGHelper.generateResGuests(guestId));
        UniqueIDType resUniqueid = HTNGHelper.generateUniqueTypeID("1", resId);
        hotelReservationVal1.getUniqueID().add(resUniqueid);
        return hotelReservationVal1;
    }

    public static HotelReservationsType generateHoltelReservations(String resId) {
        HotelReservationsType hotelReservationsType = new HotelReservationsType();
        ArrayList<HotelReservationsType.HotelReservation> hotelReservations = new ArrayList<HotelReservationsType.HotelReservation>();
        HotelReservationsType.HotelReservation hotelReservationVal1 = HTNGHelper.generateHoltelReservation(resId);
        hotelReservations.add(hotelReservationVal1);
        hotelReservationsType.getHotelReservation().addAll(hotelReservations);
        return hotelReservationsType;
    }

    public static ErrorsType getErrorsType(String message) {
        ErrorsType returnErrors = new ErrorsType();
        ArrayList<ErrorType> returnErrorsError = new ArrayList<ErrorType>();
        ErrorType returnErrorsErrorVal1 = new ErrorType();
        returnErrorsErrorVal1.setValue(message);
        returnErrorsError.add(returnErrorsErrorVal1);
        returnErrors.getError().addAll(returnErrorsError);
        return returnErrors;
    }

    public static String convertStatusToCMND(HTNGProfileMessageStatusType statusType) {
        String status = "New";
        switch (statusType) {
            case DELETED: {
                status = "Delete";
                break;
            }
            case NEW: {
                break;
            }
            case VIEWED: {
                status = "Read";
                break;
            }
        }
        return status;
    }

    public static HTNGProfileMessageStatusType messageStatusToHTNG(String msgStatus) {
        switch (msgStatus) {
            case "New": 
            case "Unread": {
                return HTNGProfileMessageStatusType.NEW;
            }
            case "Read": {
                return HTNGProfileMessageStatusType.VIEWED;
            }
            case "Delete": {
                return HTNGProfileMessageStatusType.DELETED;
            }
        }
        return HTNGProfileMessageStatusType.NEW;
    }
}

