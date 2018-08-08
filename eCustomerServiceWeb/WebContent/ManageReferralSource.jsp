<%@page import="org.dhhs.dirm.acts.cs.formbeans.*,org.dhhs.dirm.acts.cs.beans.*,java.util.Vector"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
//	request.setAttribute("page","manageReferralSource");
%>

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Manage Referral Source" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >

	
		<%-- Content for the body section belongs here --%>
		
		<logic:equal name="formMode" value="0">	
			<DIV id="pagetitle">
				<h1>Create New eCSTS Referral Source</h1>
			</DIV>
			<DIV id="pagebody">
				<p>Complete all fields on the page and click Create. </p>
				
			<DIV id="errorheader"><logic:messagesPresent>
				<UL>
					<html:messages id="error">
						<LI><bean:write name="error" /></LI>
					</html:messages>
				</UL>
			</logic:messagesPresent></DIV>
				
				<DIV id="form">
				<html:form action="/ManageReferralSourceAction?reqCode=store" focus="nmStaff" onsubmit="return validateReferralSource(this)">
					<fieldset><legend>eCSTS Referral Source</legend>
						<div class="row">
							<span class="label30">Office:</span> 
							<span class="field8r"><html:text property="nmOffice" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Referral Source:</span> 
							<span class="field30"><html:text property="nmStaff" maxlength="35"/></span>
						</div>
						<div class="row">
							<span class="label30">Title:</span> 
							<span class="field50"><html:text property="title" maxlength="50"/></span>
						</div>
						<div class="row">
							<span class="label30">Active:</span> 
							<span class="field1"><html:checkbox property="cdStatus"/></span>
						</div>
						
						<html:hidden property="nbSeq" />
						<div class="spacer">&nbsp;</div>
					
						<%-- Add the Buttons --%>
						<div class="row">
							<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
						</div>
						
						<div class="row">
							<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
							<span class="button"><html:submit>Create</html:submit></span>
						</div>
						<div class="spacer">&nbsp;</div>
						<div class="spacer">&nbsp;</div>
					</fieldset>
				</html:form>
				</DIV>
			</DIV>
		</logic:equal>

		<logic:equal name="formMode" value="1">
			<DIV id="pagetitle">
				<h1>Edit eCSTS Referral Source</h1>
			</DIV>
			<DIV id="pagebody">
				<p>Complete all fields on the page and click Save. </p>
				
			<DIV id="errorheader"><logic:messagesPresent>
				<UL>
					<html:messages id="error">
						<LI><bean:write name="error" /></LI>
					</html:messages>
				</UL>
			</logic:messagesPresent></DIV>
				
				<DIV id="form">
				<html:form action="/ManageReferralSourceAction?reqCode=save" focus="nmStaff"onsubmit="return validateReferralSource(this)">
					<fieldset><legend>eCSTS Referral Source</legend>
						<div class="row">
							<span class="label30">Office:</span> 
							<span class="field8r"><html:text property="nmOffice" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Staff ID:</span> 
							<span class="field8r"><html:text property="idStaff" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Referral Source:</span> 
							<span class="field30"><html:text property="nmStaff" maxlength="35"/></span>
						</div>
						<div class="row">
							<span class="label30">Title:</span> 
							<span class="field50"><html:text property="title" maxlength="50"/></span>
						</div>
						<div class="row">
							<span class="label30">Active:</span> 
							<span class="field1"><html:checkbox property="cdStatus"/></span>
						</div>
						<html:hidden property="nbSeq" />
						
						<div class="spacer">&nbsp;</div>
					
					
						<%-- Add the Buttons --%>
						<div class="row">
							<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
						</div>
						
						<div class="row">
							<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
							<span class="button"><html:submit>Save</html:submit></span>
						</div>
						<div class="spacer">&nbsp;</div>
						<div class="spacer">&nbsp;</div>
					</fieldset>
				</html:form>
				</DIV>
						
				
			</DIV>
		</logic:equal>

		<logic:equal name="formMode" value="2">
			<DIV id="pagetitle">
				<h1>View eCSTS Referral Source</h1>
			</DIV>
			<DIV id="pagebody">
				<p>To make changes, click Edit. Click Return to return to the previous page</p>
				
			<DIV id="errorheader"><logic:messagesPresent>
				<UL>
					<html:messages id="error">
						<LI><bean:write name="error" /></LI>
					</html:messages>
				</UL>
			</logic:messagesPresent></DIV>
				
				<DIV id="form">
				<html:form action="/ManageReferralSourceAction?reqCode=edit">
					<fieldset><legend>eCSTS Referral Source</legend>
						<div class="row">
							<span class="label30">Office:</span> 
							<span class="field8r"><html:text property="nmOffice" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Staff ID:</span> 
							<span class="field8r"><html:text property="idStaff" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Referral Source:</span> 
							<span class="field30r"><html:text property="nmStaff"  readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Title:</span> 
							<span class="field50r"><html:text property="title" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Active:</span> 
							<span class="field1r"><html:checkbox property="cdStatus"/></span>
						</div>
						<html:hidden property="nbSeq" />
						
						<div class="spacer">&nbsp;</div>
					
						<%-- Add the Buttons --%>
						<div class="row">
							<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
						</div>
						
						<div class="row">
							<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
							<span class="button"><html:submit>Edit</html:submit></span>
						</div>
						<div class="spacer">&nbsp;</div>
						<div class="spacer">&nbsp;</div>
					</fieldset>
				</html:form>
				</DIV>
			</DIV>
		</logic:equal>
		<html:javascript formName="referralSource"/>
				
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>