<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
	response.addHeader("Pragma","No-cache");
	response.addHeader("Cache-Control","no-cache");
	response.addDateHeader("Expires",0);
%>
<html:html>
<HEAD>
	<TITLE><tiles:getAsString name="title"/></TITLE>
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
	<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<META name="GENERATOR" content="IBM WebSphere Studio">
	<META http-equiv="Content-Style-Type" content="text/css">
	<html:base/>
	<LINK  rel="stylesheet" type="text/css" ref="<html:rewrite page='/theme/MenuStyles.css'/>">
	<LINK  rel="stylesheet" type="text/css" ref="<html:rewrite page='/theme/PageStyles.css'/>">
	<LINK  rel="stylesheet" type="text/css" ref="<html:rewrite page='/theme/FormStyles.css'/>">
</HEAD>

<BODY>
	<!-- Include Header -->
	<tiles:insert attribute="header" />
	<!-- Include Menu -->
	<tiles:insert attribute="menu" />
	<!-- Include Redirect to Login if required -->
	<tiles:insert attribute="redirect" />
	<!-- Include Message Tile if appropriate -->
	<tiles:insert attribute="message" />
	<!-- Include Navigation -->
	<tiles:insert attribute="navigation" />
	<!-- Include Body -->
	<tiles:insert attribute="body" />
	<!-- Include Footer -->
	<tiles:insert attribute="footer" />
</BODY>
</html:html>	