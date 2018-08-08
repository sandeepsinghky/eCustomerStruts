<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<html:html>
<HEAD>
	<TITLE><tiles:getAsString name="title"/></TITLE>
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%
	response.addHeader("Pragma","No-cache");
//	response.addHeader("Cache-Control","no-cache");
// set standard HTTP/1.1 no-cache headers
	response.addHeader( "Cache-Control", "no-store, no-cache, must-revalidate" );
// set IE extended HTTP/1.1 no-cache headers
	response.addHeader( "Cache-Control", "post-check=0, pre-check=0" );
	response.addDateHeader("Expires",0);
%>
	<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<META name="GENERATOR" content="IBM WebSphere Studio">
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK  rel="stylesheet" type="text/css" href="theme/eCSTSSkin.css">
	<layout:skin/>
	<script LANGUAGE="JavaScript1.3" src="theme/javascript.js";></script>
</HEAD>
<script>
var seconds=3600; 
function countDown() { 
	if(seconds<=0) {
		resetCounter();
		document.location.href="Logoff.do?timeout=true";
		return;
	} 
	seconds--;
	window.setTimeout("countDown()",1000);
}
function resetCounter() {
	seconds=3600; 
}
</script>
<BODY onload="countDown()" onchange="resetCounter()" onclick="resetCounter()" onScroll="resetCounter()">
	<!-- Include Header -->
	<tiles:insert attribute="header" />
	<!-- Include Menu -->
	<tiles:insert attribute="menu" />
	<!-- Include Redirect to Login if required -->
	<tiles:insert attribute="redirect" />
	<!-- Include Navigation Tile if appropriate -->
	<tiles:insert attribute="navigation" />
	<!-- Include Body -->
	<tiles:insert attribute="body" />
	<!-- Include Footer -->
	<tiles:insert attribute="footer" />
</BODY>
</html:html>	