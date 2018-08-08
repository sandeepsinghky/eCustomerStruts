<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Modify User" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="message" value="message.jsp" />
	<tiles:put name="body" type="string" >
	
		<%-- Content for the body section belongs here --%>
		
		<DIV id="pagetitle">
			<h1>Modify eCustomer Service User</h1>
		</DIV>
		<DIV id="pagebody">
			<p>Make changes to the selected User and click Update to submit changes. Click return to return to the previous page.</p>
			<DIV id="form">
			<layout:html>
				<fieldset><legend>eCSTS User Details</legend>
					<layout:form action="/UserSearchAction" styleClass="FORM" focus="workerid">
						<layout:field key="Worker ID" property="idWorker" styleClass="LABEL" />
						<layout:field key="Name" property="nmWorker" styleClass="LABEL"/>
						<layout:text key="Profile ID" property="idProfile" styleClass="FIELD" maxlength="4"/>
						<layout:text key="Status" property="cdStatus" styleClass="FIELD" maxlength="1"/>
						<layout:field key="Active Count" property="nbActiveCount" styleClass="LABEL"/>
						<layout:field key="Pending Count" property="nbPendCount" styleClass="LABEL"/>
						<layout:submit>Update</layout:submit>
						<layout:cancel>Return</layout:cancel>
					</layout:form>
				</fieldset>
			</layout:html>
			</DIV>
		</DIV>
		
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>