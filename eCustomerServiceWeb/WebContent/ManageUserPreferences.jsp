<%@page import="org.dhhs.dirm.acts.cs.formbeans.*,org.dhhs.dirm.acts.cs.beans.*, fr.improve.struts.taglib.layout.util.*"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
//	request.setAttribute("page","manageUserPreferences");
%>		

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="User Preferences" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >

	
		<%-- Content for the body section belongs here --%>
		
		<logic:equal name="formMode" value="1">		
			<DIV id="pagetitle">
				<h1>Change eCSTS Agent Preferences</h1>
			</DIV>
			<DIV id="pagebody">
				<p>The fields on this page provide the ability to modify default search criteria for record inquires. 
				Complete all fields on the page and click Save. </p>
				
			<DIV id="errorheader"><logic:messagesPresent>
				<UL>
					<html:messages id="error">
						<LI><bean:write name="error" /></LI>
					</html:messages>
				</UL>
			</logic:messagesPresent></DIV>
				
				<DIV id="form">
				<html:form action="/UserPreferencesAction?reqCode=save">
					<fieldset><legend>eCSTS Agent Preferences</legend>
						<div class="row">
							<span class="label30">Date Received:</span> 
							<span class="field1"><html:checkbox property="dtReceived"/></span>
							<span class="label30">Date Completed:</span> 
							<span class="field1"><html:checkbox property="dtCompleted"/></span>
							<span class="label30">Date Due:</span> 
							<span class="field1"><html:checkbox property="dtDue"/></span>
						</div>
						<div class="row">
							<span class="label30">IV-D #:</span> 
							<span class="field1"><html:checkbox property="nbCase"/></span>
							<span class="label30">MPI #:</span> 
							<span class="field1"><html:checkbox property="idPart"/></span>
							<span class="label30">SSN:</span> 
							<span class="field1"><html:checkbox property="nbSSN"/></span>
						</div>
						<div class="row">
							<span class="label30">Agent Assigned:</span> 
							<span class="field1"><html:checkbox property="agent"/></span>
							<span class="label30">Referral Type:</span> 
							<span class="field1"><html:checkbox property="cdFormType"/></span>
							<span class="label30">Email:</span> 
							<span class="field1"><html:checkbox property="idEmail"/></span>
						</div>
						<div class="row">
							<span class="label30">Custodial Parent:</span> 
							<span class="field1"><html:checkbox property="custodial"/></span>
							<span class="label30">Non-Custodial Parent:</span> 
							<span class="field1"><html:checkbox property="nonCustodial"/></span>
							<span class="label30">Control #:</span> 
							<span class="field1"><html:checkbox property="nbControl"/></span>
						</div>
						<div class="row">
							<span class="label30">Customer:</span> 
							<span class="field1"><html:checkbox property="writer"/></span>
							<span class="label30">Docket #:</span> 
							<span class="field1"><html:checkbox property="nbDocket"/></span>
							<span class="label30">Referral Source 1:</span> 
							<span class="field1"><html:checkbox property="source1"/></span>
						</div>
						<div class="row">
							<span class="label30">Referral Source 2:</span> 
							<span class="field1"><html:checkbox property="source2"/></span>
							<span class="label30">Referral Source 3:</span> 
							<span class="field1"><html:checkbox property="source3"/></span>
							<span class="label30">Referral Source 4:</span> 
							<span class="field1"><html:checkbox property="source4"/></span>
						</div>
						<div class="row">
							<span class="label30">County:</span> 
							<span class="field1"><html:checkbox property="nmCounty"/></span>
						</div>
						<html:hidden property="idWorker"/>
						
						<div class="row">
						</div>
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
				<h1>View eCSTS User Preferences</h1>
			</DIV>
			<DIV id="pagebody">
				<p>The fields on this page provide the ability to modify default search criteria for record inquires. 
				To make changes, click Edit. </p>
				
			<DIV id="errorheader"><logic:messagesPresent>
				<UL>
					<html:messages id="error">
						<LI><bean:write name="error" /></LI>
					</html:messages>
				</UL>
			</logic:messagesPresent></DIV>
				
				<DIV id="form">
				<html:form action="/UserPreferencesAction?reqCode=edit">
					<fieldset><legend>eCSTS User Preferences</legend>
						<div class="row">
							<span class="label30">Date Received:</span> 
							<span class="field1"><html:checkbox property="dtReceived" disabled="true"/></span>
							<span class="label30">Date Completed:</span> 
							<span class="field1"><html:checkbox property="dtCompleted" disabled="true"/></span>
							<span class="label30">Due Date:</span> 
							<span class="field1"><html:checkbox property="dtDue" disabled="true"/></span>
						</div>
						<div class="row">
							<span class="label30">IV-D #:</span> 
							<span class="field1"><html:checkbox property="nbCase" disabled="true"/></span>
							<span class="label30">MPI #:</span> 
							<span class="field1"><html:checkbox property="idPart" disabled="true"/></span>
							<span class="label30">SSN:</span> 
							<span class="field1"><html:checkbox property="nbSSN" disabled="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Agent Assigned:</span> 
							<span class="field1"><html:checkbox property="agent" disabled="true"/></span>
							<span class="label30">Referral Type:</span> 
							<span class="field1"><html:checkbox property="cdFormType" disabled="true"/></span>
							<span class="label30">Email:</span> 
							<span class="field1"><html:checkbox property="idEmail" disabled="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Custodial Parent:</span> 
							<span class="field1"><html:checkbox property="custodial" disabled="true"/></span>
							<span class="label30">Non-Custodial Parent:</span> 
							<span class="field1"><html:checkbox property="nonCustodial" disabled="true"/></span>
							<span class="label30">Control #:</span> 
							<span class="field1"><html:checkbox property="nbControl" disabled="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Customer:</span> 
							<span class="field1"><html:checkbox property="writer" disabled="true"/></span>
							<span class="label30">Docket #:</span>
							<span class="field1"><html:checkbox property="nbDocket" disabled="true"/></span>
							<span class="label30">Referral Source 1:</span>
							<span class="field1"><html:checkbox property="source1" disabled="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Referral Source 2:</span> 
							<span class="field1"><html:checkbox property="source2" disabled="true"/></span>
							<span class="label30">Referral Source 3:</span>
							<span class="field1"><html:checkbox property="source3" disabled="true"/></span>
							<span class="label30">Referral Source 4:</span>
							<span class="field1"><html:checkbox property="source4" disabled="true"/></span>
						</div>
						<div class="row">
							<span class="label30">County:</span> 
							<span class="field1"><html:checkbox property="nmCounty" disabled="true"/></span>
						</div>
						<html:hidden property="idWorker"/>
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
				
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>