<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
	request.setAttribute("page","workloadManagementMenu");
%>		

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Welcome to Customer Service Tracking System" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="message" value="message.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >
	
		<%-- Content for the body section belongs here --%>
		<DIV id="pagetitle">
			<h1>eCustomer Service Workload Management</h1>
		</DIV>
		<DIV id="pagebody">
			<p>
				Welcome to the eCustomer Workload Management Menu.
			</p>
			<p>
				<html:link href="WorkloadSearch.do">
					<html:img page="/images/bullet.gif" align="bottom" border="0" />
				</html:link>
				<bean:message key="button.WorkloadSearch"/>
			</p>
			<p>
				<html:link href="ManageWorkloadAction.do?reqCode=select">
					<html:img page="/images/bullet.gif" align="bottom" border="0" />
				</html:link>
				<bean:message key="button.WorkloadMassTransfer"/>
			</p>
			<p>
				<html:link href="Navigation.do">
					<html:img page="/images/bullet.gif" align="bottom" border="0" />
				</html:link>
				<bean:message key="button.return"/>
			</p>
		</DIV>
		
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>
