<%@page import="org.dhhs.dirm.acts.cs.formbeans.*,org.dhhs.dirm.acts.cs.beans.*"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title"
		value="Welcome to Customer Service Tracking System" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="redirect" type="string" />
	<tiles:put name="body" type="string">

		<%-- Content for the body section belongs here --%>

		<%
			boolean resetPassword = false;
			String s = (String)request.getAttribute("resetPassword");
			resetPassword = (Boolean.valueOf(s)).booleanValue();
			String  username = (String)request.getParameter("id");
		%>
		
		<DIV id="pagetitle">
			<h1>Password Reset</h1>
		</DIV>
		<DIV id="pagebody">
			<p>Welcome to North Carolina Child Support Enforcement Customer
			Service Tracking System. Please enter your new password and reconfirm password to
			sign in.</p>

		<DIV id="errorheader"><logic:messagesPresent>
			<UL>
				<html:messages id="error">
					<LI><bean:write name="error" /></LI>
				</html:messages>
			</UL>
		</logic:messagesPresent></DIV>

			<DIV id="form">
			<html:form action="/PasswordResetAction?reqCode=resetPassword" focus="newPassword" styleClass="form60" onsubmit="return validateResetPasswordForm(this)">
				<fieldset><legend>Sign in</legend>
				<div class="row">
					<span class="label30">User ID:</span> 
					<span class="field8r"><html:text property="username" value="<%=username%>" readonly="true"/></span>
				</div>
				<div class="row">
					<span class="label30">New Password:</span> 
					<span class="field8"><html:password property="newPassword" redisplay="false" maxlength="8" /></span>
				</div>
				<div class="row">
					<span class="label30">Confirm Password:</span> 
					<span class="field8"><html:password property="confirmPassword" redisplay="false" maxlength="8" /></span>
				</div>
				<html:hidden property="password"/>
				
				<div class="spacer">&nbsp;</div>
				<div class="spacer">&nbsp;</div>
				<div class="spacer">&nbsp;</div>

				<%-- Add the Buttons --%>
				<div class="row">
					<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
				</div>
				<div class="row">
					<span class="button"><html:reset><bean:message key="button.reset"/></html:reset></span>
					<span class="button"><html:submit><bean:message key="button.logon"/></html:submit></span>
				</div>
				
				<div class="spacer">&nbsp;</div>
				<div class="spacer">&nbsp;</div>
				</fieldset>
			</html:form>
			</DIV>
		</DIV>
		<div class="spacer">&nbsp;</div>
		<div class="spacer">&nbsp;</div>
		<div class="spacer">&nbsp;</div>
		
		<html:javascript formName="resetPasswordForm"/>
		<%-- End Content for the body section --%>

	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />
</tiles:insert>