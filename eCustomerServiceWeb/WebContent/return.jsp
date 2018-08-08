<%@page import="org.dhhs.dirm.acts.cs.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>


<%
	NavigationStack stack = (NavigationStack)session.getAttribute(Constants.STACK);
%>

<layout:button onclick="window.location.href='<%=stack.getPreviousStackItem()%>';">
	<bean:message key="button.return"/>
</layout:button>
