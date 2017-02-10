package com.digirati.liferay.plugins.atlas.action;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.DoubleValidator;
import org.apache.commons.validator.routines.IntegerValidator;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

public class MapConfigurationAction extends DefaultConfigurationAction {

    // Minimum and maximum latitudes and longitudes
    private static final double BBOX_MIN_LAT = -85.0;
    private static final double BBOX_MIN_LON = -180.0;
    private static final double BBOX_MAX_LAT = 85.0;
    private static final double BBOX_MAX_LON = 180.0;
    
    // Minimum and maximum zoom values, as per Leaflet JS library
    private static final int ZOOM_MIN = 1;
    private static final int ZOOM_MAX = 18;

    @Override
    public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

        validateBoundingBox(actionRequest);

        if (SessionErrors.isEmpty(actionRequest)) {
            validateDefaultCoordinates(actionRequest);
        }
        
        validateZoomLevels(actionRequest);

        validateMapSettings(actionRequest);
        
        processOverlays(actionRequest);

        if (!SessionErrors.isEmpty(actionRequest)) {
            return;
        }

        super.processAction(portletConfig, actionRequest, actionResponse);
    }
    
    private void validateMapSettings(ActionRequest actionRequest) {
        
        IntegerValidator integerValidator = IntegerValidator.getInstance();
        UnicodeProperties properties = PropertiesParamUtil.getProperties(actionRequest, "preferences--");
        
        String mapHeightStr = properties.getProperty("mapHeight");
        if(!integerValidator.isValid(mapHeightStr)) {
            SessionErrors.add(actionRequest, "invalid-map-height");
        }
        
        int mapHeight = Integer.parseInt(mapHeightStr);
        if(mapHeight < 1) {
            SessionErrors.add(actionRequest, "invalid-map-height");
        }
    }

    private void validateBoundingBox(ActionRequest actionRequest) {

        UnicodeProperties properties = PropertiesParamUtil.getProperties(actionRequest, "preferences--");

        String bboxBottomStr = properties.getProperty("bboxBottom");
        if (!isValidBboxCoordinate(bboxBottomStr, BBOX_MIN_LAT, BBOX_MAX_LAT)) {
            SessionErrors.add(actionRequest, "invalid-bbox-bottom");
        }

        String bboxLeftStr = properties.getProperty("bboxLeft");
        if (!isValidBboxCoordinate(bboxLeftStr, BBOX_MIN_LON, BBOX_MAX_LON)) {
            SessionErrors.add(actionRequest, "invalid-bbox-left");
        }

        String bboxTopStr = properties.getProperty("bboxTop");
        if (!isValidBboxCoordinate(bboxTopStr, Double.parseDouble(bboxBottomStr), BBOX_MAX_LAT)) {
            SessionErrors.add(actionRequest, "invalid-bbox-top");
        }

        String bboxRightStr = properties.getProperty("bboxRight");
        if (!isValidBboxCoordinate(bboxRightStr, Double.parseDouble(bboxLeftStr), BBOX_MAX_LON)) {
            SessionErrors.add(actionRequest, "invalid-bbox-right");
        }
    }

    private boolean isValidBboxCoordinate(String coordinateStr, double minValue, double maxValue) {

        DoubleValidator doubleValidator = DoubleValidator.getInstance();

        if (!doubleValidator.isValid(coordinateStr)) {
            return false;
        }

        double coordinate = Double.parseDouble(coordinateStr);
        if (!doubleValidator.isInRange(coordinate, minValue, maxValue)) {
            return false;
        }

        return true;
    }

    private void validateDefaultCoordinates(ActionRequest actionRequest) {

        boolean isValidDefaultCoordinates = true;
        DoubleValidator doubleValidator = DoubleValidator.getInstance();
        UnicodeProperties properties = PropertiesParamUtil.getProperties(actionRequest, "preferences--");

        String latStr = properties.getProperty("lat");
        if (!doubleValidator.isValid(latStr)) {
            SessionErrors.add(actionRequest, "invalid-default-lat");
            isValidDefaultCoordinates = false;
        }

        String lonStr = properties.getProperty("lon");
        if (!doubleValidator.isValid(lonStr)) {
            SessionErrors.add(actionRequest, "invalid-default-lon");
            isValidDefaultCoordinates = false;
        }

        if (isValidDefaultCoordinates) {

            double bboxBottom = Double.parseDouble(properties.getProperty("bboxBottom"));
            double bboxLeft = Double.parseDouble(properties.getProperty("bboxLeft"));
            double bboxTop = Double.parseDouble(properties.getProperty("bboxTop"));
            double bboxRight = Double.parseDouble(properties.getProperty("bboxRight"));

            double lat = Double.parseDouble(latStr);
            if (!doubleValidator.isInRange(lat, bboxBottom, bboxTop)) {
                SessionErrors.add(actionRequest, "invalid-default-lat");
            }

            double lon = Double.parseDouble(lonStr);
            if (!doubleValidator.isInRange(lon, bboxLeft, bboxRight)) {
                SessionErrors.add(actionRequest, "invalid-default-lon");
            }
        }
    }

    private void validateZoomLevels(ActionRequest actionRequest) {

        boolean isValidZoomLevels = true;
        DoubleValidator doubleValidator = DoubleValidator.getInstance();
        UnicodeProperties properties = PropertiesParamUtil.getProperties(actionRequest, "preferences--");

        String defaultZoomStr = properties.getProperty("zoom");
        String minZoomStr = properties.getProperty("minZoom");
        String maxZoomStr = properties.getProperty("maxZoom");

        if (!doubleValidator.isValid(defaultZoomStr)) {
            SessionErrors.add(actionRequest, "invalid-default-zoom");
            isValidZoomLevels = false;
        }

        if (!doubleValidator.isValid(minZoomStr)) {
            SessionErrors.add(actionRequest, "invalid-min-zoom");
            isValidZoomLevels = false;
        }

        if (!doubleValidator.isValid(maxZoomStr)) {
            SessionErrors.add(actionRequest, "invalid-max-zoom");
            isValidZoomLevels = false;
        }

        if (isValidZoomLevels) {

            double defaultZoom = Double.parseDouble(defaultZoomStr);
            double minZoom = Double.parseDouble(minZoomStr);
            double maxZoom = Double.parseDouble(maxZoomStr);

            if (minZoom < ZOOM_MIN || minZoom >= maxZoom) {
                SessionErrors.add(actionRequest, "invalid-min-zoom");
            }

            if (maxZoom > ZOOM_MAX || maxZoom <= minZoom) {
                SessionErrors.add(actionRequest, "invalid-max-zoom");
            }

            if (defaultZoom < minZoom || defaultZoom > maxZoom) {
                SessionErrors.add(actionRequest, "invalid-default-zoom");
            }
        }
    }
    
    private void processOverlays(ActionRequest actionRequest) throws PortalException, SystemException, ReadOnlyException, ValidatorException, IOException {

        JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

        String overlayfieldIndexesStr = actionRequest.getParameter("overlayFieldIndexes");
        String[] overlayFieldIndexes = overlayfieldIndexesStr.split(StringPool.COMMA);

        for (int i = 0; i < overlayFieldIndexes.length; i++) {

            int fieldIndex = Integer.parseInt(overlayFieldIndexes[i]);
            String name = ParamUtil.getString(actionRequest, "name" + fieldIndex);
            String endpoint = ParamUtil.getString(actionRequest, "endpoint" + fieldIndex);

            JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
            jsonObject.put("name", name);
            jsonObject.put("endpoint", endpoint);
            if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(endpoint)) {
                jsonArray.put(jsonObject);
            }
        }

        String portletResource = ParamUtil.getString(actionRequest, "portletResource");
        PortletPreferences portletPreferences = PortletPreferencesFactoryUtil.getPortletSetup(actionRequest, portletResource);
        portletPreferences.setValue("overlays", jsonArray.toString());
        portletPreferences.store();
    }
}
