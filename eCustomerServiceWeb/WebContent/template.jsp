<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<tiles:insert page="/tiles/layout.jsp" flush="true">
	<tiles:put name="title" value="********** Put Your Title Here ********" />
	<tiles:put name="header" value="/tiles/header.jsp" />
	<tiles:put name="menu" value="/tiles/menu.jsp" />
	<tiles:put name="redirect" type="string" />
	<tiles:put name="message" type="string" />
	<tiles:put name="body" type="string" >
		<%-- Content for the body section belongs here --%>

		<DIV id="pagetitle">
			<h1>Put your page specific title here</h1>
		</DIV>
		<DIV id="pagebody">
			<p>Type your text for your page here.</p>
		</DIV>
		<div class="spacer">&nbsp;</div>
		<div class="spacer">&nbsp;</div>
		<div class="spacer">&nbsp;</div>		
		
		<%-- End Content for the body section --%>
	</tiles:put>
	<tiles:put name="footer" value="/tiles/footer.jsp" />		
</tiles:insert>