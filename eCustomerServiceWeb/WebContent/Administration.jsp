<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
	request.setAttribute("page","administration");
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
			<h1>eCustomer Service Administration</h1>
		</DIV>
		<DIV id="pagebody">
			<p>
				Welcome to the eCustomer Service Administration page. As an administrator, you have the ability to perform the following tasks.
			</p>
			<logic:equal name="userbean" property="manageUsers" value="true">
				<p>
					<html:link href="UserSearch.do">
						<html:img page="/images/bullet.gif" align="bottom" border="0" />
					</html:link>
					<bean:message key="button.ManageUsers"/>
				</p>
			</logic:equal>
			<logic:equal name="userbean" property="manageUsers" value="true">
				<p>
					<html:link href="ProfileSearch.do">
						<html:img page="/images/bullet.gif" align="bottom" border="0" />
					</html:link>
					<bean:message key="button.ManageProfile"/>
				</p>
			</logic:equal>
			<logic:equal name="userbean" property="manageWorkFlow" value="true">
				<p>
					<html:link href="ReferralSearchAction.do">
						<html:img page="/images/bullet.gif" align="bottom" border="0" />
					</html:link>
					<bean:message key="button.ManageReferralTypes"/>
				</p>
				<p>
					<html:link href="ProcessSearchAction.do">
						<html:img page="/images/bullet.gif" align="bottom" border="0" />
					</html:link>
					<bean:message key="button.ManageProcess"/>
				</p>
			</logic:equal>
			<logic:equal name="userbean" property="manageReferralSources" value="true">
				<p>
					<html:link href="ManageOfficeAction.do?reqCode=load">
						<html:img page="/images/bullet.gif" align="bottom" border="0" />
					</html:link>
					<bean:message key="button.ManageReferralSources"/>
				</p>
			</logic:equal>
			<logic:equal name="userbean" property="manageWorkLoad" value="true">
				<p>
					<html:link href="WorkloadManagement.do">
						<html:img page="/images/bullet.gif" align="bottom" border="0" />
					</html:link>
					<bean:message key="button.ManageWorkload"/>
				</p>
			</logic:equal>
			<logic:equal name="userbean" property="manageApprovals" value="true">
				<p>
					<html:link href="ApprovalSearch.do">
						<html:img page="/images/bullet.gif" align="bottom" border="0" />
					</html:link>
					<bean:message key="button.ManageApprovals"/>
				</p>
			</logic:equal>
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
