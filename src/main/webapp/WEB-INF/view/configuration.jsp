<%@ include file="/WEB-INF/includes/includes.jsp" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>
<%@ page import="com.liferay.util.portlet.PortletProps" %>
<%@ page import="com.liferay.portal.kernel.json.JSONArray" %>
<%@ page import="com.liferay.portal.kernel.json.JSONFactoryUtil" %>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationUrl" />

<%
int mapHeight_cfg = GetterUtil.getInteger(portletPreferences.getValue("mapHeight", PortletProps.get("default.map.height")));

double lat_cfg = GetterUtil.getDouble(portletPreferences.getValue("lat", PortletProps.get("default.lat")));
double lon_cfg = GetterUtil.getDouble(portletPreferences.getValue("lon", PortletProps.get("default.lon")));

double bboxBottom_cfg = GetterUtil.getDouble(portletPreferences.getValue("bboxBottom", PortletProps.get("default.bbox.bottom")));
double bboxLeft_cfg = GetterUtil.getDouble(portletPreferences.getValue("bboxLeft", PortletProps.get("default.bbox.left")));
double bboxTop_cfg = GetterUtil.getDouble(portletPreferences.getValue("bboxTop", PortletProps.get("default.bbox.top")));
double bboxRight_cfg = GetterUtil.getDouble(portletPreferences.getValue("bboxRight", PortletProps.get("default.bbox.right")));

int zoom_cfg = GetterUtil.getInteger(portletPreferences.getValue("zoom", PortletProps.get("default.zoom")));;
int minZoom_cfg = GetterUtil.getInteger(portletPreferences.getValue("minZoom", PortletProps.get("default.minZoom")));;
int maxZoom_cfg = GetterUtil.getInteger(portletPreferences.getValue("maxZoom", PortletProps.get("default.maxZoom")));;

String overlays_cfg = GetterUtil.getString(portletPreferences.getValue("overlays", "[]"));
JSONArray overlaysArr = JSONFactoryUtil.createJSONArray(overlays_cfg);
%>

<aui:form action="<%= configurationUrl %>" method="post" name="fm">
    <aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	
	<aui:fieldset label="cfg.map.fieldset" helpMessage="cfg.map.fieldset.help">
		<liferay-ui:error key="invalid-map-height" message="cfg.map.height.invalid" />
		<aui:input label="cfg.map.height" name="preferences--mapHeight--" type="text" value="<%= mapHeight_cfg %>" inlineField="true" />
	</aui:fieldset>
	
	<aui:fieldset label="cfg.default.fieldset" helpMessage="cfg.default.fieldset.help">
		<liferay-ui:error key="invalid-default-lat" message="cfg.default.lat.invalid" />
		<liferay-ui:error key="invalid-default-lon" message="cfg.default.lon.invalid" />
		<aui:input label="cfg.default.lat" name="preferences--lat--" type="text" value="<%= lat_cfg %>" inlineField="true" />
		<aui:input label="cfg.default.lon" name="preferences--lon--" type="text" value="<%= lon_cfg %>" inlineField="true" />
	</aui:fieldset>
	
	<aui:fieldset label="cfg.bbox.fieldset" helpMessage="cfg.bbox.fieldset.help">
		<liferay-ui:error key="invalid-bbox-bottom" message="cfg.bbox.bottom.invalid" />
		<liferay-ui:error key="invalid-bbox-left" message="cfg.bbox.left.invalid" />
		<liferay-ui:error key="invalid-bbox-top" message="cfg.bbox.top.invalid" />
		<liferay-ui:error key="invalid-bbox-right" message="cfg.bbox.right.invalid" />
		<aui:input label="cfg.bbox.bottom" name="preferences--bboxBottom--" type="text" value="<%= bboxBottom_cfg %>" inlineField="true" />
		<aui:input label="cfg.bbox.left" name="preferences--bboxLeft--" type="text" value="<%= bboxLeft_cfg %>" inlineField="true" />
		<aui:input label="cfg.bbox.top" name="preferences--bboxTop--" type="text" value="<%= bboxTop_cfg %>" inlineField="true" />
		<aui:input label="cfg.bbox.right" name="preferences--bboxRight--" type="text" value="<%= bboxRight_cfg %>" inlineField="true" />
	</aui:fieldset>
	
	<aui:fieldset label="cfg.zoom.fieldset" helpMessage="cfg.zoom.fieldset.help">
		<liferay-ui:error key="invalid-default-zoom" message="cfg.zoom.def.invalid" />
		<liferay-ui:error key="invalid-min-zoom" message="cfg.zoom.min.invalid" />
		<liferay-ui:error key="invalid-max-zoom" message="cfg.zoom.max.invalid" />
		<aui:input label="cfg.zoom.def" name="preferences--zoom--" type="text" value="<%= zoom_cfg %>" inlineField="true" />
		<aui:input label="cfg.zoom.min" name="preferences--minZoom--" type="text" value="<%= minZoom_cfg %>" inlineField="true" />
		<aui:input label="cfg.zoom.max" name="preferences--maxZoom--" type="text" value="<%= maxZoom_cfg %>" inlineField="true" />
	</aui:fieldset>
	
	<aui:fieldset label="cfg.overlays.fieldset" helpMessage="cfg.overlays.fieldset.help">
		<div id="overlayFields">
			<%
			if(overlaysArr.length() > 0) {
			    for(int i = 0; i < overlaysArr.length(); i++) {
				    JSONObject overlay = overlaysArr.getJSONObject(i);
				    String name = overlay.getString("name");
				    String endpoint = overlay.getString("endpoint");
				    %>
					<div class="lfr-form-row lfr-form-row-inline">
						<div class="row-fields">
							<aui:input label="cfg.overlays.name" name="<%= \"name\" + i %>" value="<%= name %>" inlineField="true" />
							<aui:input label="cfg.overlays.endpoint" name="<%= \"endpoint\" + i %>" value="<%= endpoint %>" inlineField="true" />
						</div>
					</div>
					<%
				}
			} else {
				%>
			    <div class="lfr-form-row lfr-form-row-inline">
					<div class="row-fields">
						<aui:input label="cfg.overlays.name" name="name0" inlineField="true" />
						<aui:input label="cfg.overlays.endpoint" name="endpoint0" inlineField="true" />
					</div>
				</div>
				<%
			}
			%>
		</div>
	</aui:fieldset>
	
    <aui:button-row>
        <aui:button type="submit" />
    </aui:button-row>
</aui:form>

<aui:script use="liferay-auto-fields">
	new Liferay.AutoFields({
		contentBox: '#overlayFields',
		fieldIndexes: '<portlet:namespace />overlayFieldIndexes'
	}).render();
</aui:script>