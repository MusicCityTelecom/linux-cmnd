/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.annotation.WebServlet
 */
package com.tpvision.smartinstall.servlet;

import com.google.gson.Gson;
import com.tpvision.smartinstall.schedule.PlayoutWeatherForecastTask;
import com.tpvision.smartinstall.util.CommonConstants;
import com.tpvision.smartinstall.util.Location;
import com.tpvision.smartinstall.util.LocationManager;
import com.tpvision.smartinstall.util.Utils;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(value={"/location"})
public class LocationServlet
extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(LocationServlet.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mode = request.getParameter("mode");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String geonameid = request.getParameter("geonameid");
        String pin = request.getParameter("pin");
        String addressLine1 = request.getParameter("aline1");
        String addressLine2 = request.getParameter("aline2");
        String hotelName = request.getParameter("hotel");
        String name = request.getParameter("name");
        String phoneNumber = request.getParameter("phoneNumber");
        String phoneWebsite = request.getParameter("phoneWebsite");
        String result = "{\"status\":\"fail\"}";
        if ("set".equalsIgnoreCase(mode)) {
            Location location = new Location(hotelName, country, city, geonameid, pin, addressLine1, addressLine2, name, phoneNumber, phoneWebsite);
            LocationManager.saveDataToFile(location);
            PlayoutWeatherForecastTask.startPlay();
            result = "{\"status\":\"success\"}";
        } else if ("get".equalsIgnoreCase(mode)) {
            try {
                byte[] locationContent = FileUtils.readFileToByteArray(new File(CommonConstants.LOCATION_MANAGER_STORE));
                result = new String(locationContent);
            }
            catch (Exception e) {
                LOG.error(e.getMessage(), e);
            }
        } else if ("index".equalsIgnoreCase(mode)) {
            Location location = LocationManager.getLocationFromFile();
            request.setAttribute("location", new Gson().toJson(location));
            request.getRequestDispatcher("jsp/admin/location.jsp").forward(request, response);
            return;
        }
        Utils.writeJsonToResponse(result, response);
    }
}

