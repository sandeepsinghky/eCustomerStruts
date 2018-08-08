<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@page import="fr.improve.struts.taglib.layout.util.*" %>
<%
	response.addHeader( "X-FRAME-OPTIONS", "SAMEORIGIN" );
	if (session != null) {
		LayoutUtils.setSkin(session,"eCSTSSkin");
	}
%>
<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Welcome to Customer Service Tracking System" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="redirect" type="string" />
	<tiles:put name="message" type="string" />
	<tiles:put name="body" type="string" >
		<%-- Content for the body section belongs here --%>

		<DIV id="pagetitle">
			<h1>Customer Service Home</h1>
		</DIV>
		<DIV id="pagebody">
			<p>Welcome to North Carolina Child Support Enforcement Customer Service Tracking System.</p>
		</DIV>
		<div class="spacer">&nbsp;</div>
		<div class="spacer">&nbsp;</div>
		<div class="spacer">&nbsp;</div>		
		
		<%-- End Content for the body section --%>
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>