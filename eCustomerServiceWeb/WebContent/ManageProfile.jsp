<%@page import="org.dhhs.dirm.acts.cs.formbeans.*,org.dhhs.dirm.acts.cs.beans.*, fr.improve.struts.taglib.layout.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%
//	request.setAttribute("page","manageProfile");
%>		
<script LANGUAGE="JavaScript">
	function protect_all() {
		
		var master = document.getElementById("manageAll");
		var disable = false;
		if (master.checked == true) {
			disable = true;
		}
		
		var allInputs = document.getElementById("profileForm").getElementsByTagName("input");
		for (var i = 0; i < allInputs.length; i++) {
		
			if (allInputs[i].type == "checkbox" && allInputs[i].name != 'manageAll') {
				allInputs[i].disabled = disable;
			}
		}
	}
		
	function disable_all() {
		
		var master = document.getElementById("manageAll");
		var disable = false;
		if (master.checked == true) {
			disable = true;
		}
		
		var allInputs = document.getElementById("profileForm").getElementsByTagName("input");
		for (var i = 0; i < allInputs.length; i++) {
		
			if (allInputs[i].type == "checkbox" && allInputs[i].name != 'manageAll') {
				allInputs[i].disabled = disable;
				if (disable == true) allInputs[i].checked = false;
			}
		}
	}
</script>

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Manage Profile" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="message" value="message.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />	
	<tiles:put name="body" type="string" >

	
		<%-- Content for the body section belongs here --%>
		
		<logic:equal name="formMode" value="0">		
			<DIV id="pagetitle">
				<h1>Create New eCSTS Profile</h1>
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
				<html:form action="/ManageProfileAction?reqCode=store" focus="idProfile" onsubmit="return validateProfileForm(this)">
					<fieldset><legend>eCSTS Profile Details</legend>
						<div class="row">
							<span class="label30">Profile ID:</span> 
							<span class="field4"><html:text property="idProfile" maxlength="4"/></span>
						</div>
						<div class="row">
							<span class="label30">Profile Desc:</span> 
							<span class="field30"><html:text property="idProfileDesc" maxlength="30"/></span>
						</div>
						<div class="row">
							<span class="label30">Administrator:</span> 
							<span class="field1"><html:checkbox property="manageAll" onclick="disable_all();"/></span>
							<span class="label30">Manage Agents:</span> 
							<span class="field1"><html:checkbox property="manageUsers"/></span>
						</div>
						<div class="row">
							<span class="label30">Manage Profiles:</span> 
							<span class="field1"><html:checkbox property="manageProfiles"/></span>
							<span class="label30">Manage WorkFlow:</span> 
							<span class="field1"><html:checkbox property="manageWorkFlow"/></span>
						</div>
						<div class="row">
							<span class="label30">Manage WorkLoad:</span> 
							<span class="field1"><html:checkbox property="manageWorkLoad"/></span>
							<span class="label30">Manage Approvals:</span> 
							<span class="field1"><html:checkbox property="manageApprovals"/></span>
						</div>
						<div class="row">
							<span class="label30">Manage Referral Sources:</span> 
							<span class="field1"><html:checkbox property="manageReferralSources"/></span>
							<span class="label30">Manage Reports:</span> 
							<span class="field1"><html:checkbox property="manageReports"/></span>
						</div>
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
				<h1>Edit eCSTS Profile</h1>
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
				<html:form action="/ManageProfileAction?reqCode=save" focus="idProfileDesc" onsubmit="return validateProfileForm(this)">
					<fieldset><legend>eCSTS Profile Details</legend>
						<div class="row">
							<span class="label30">Profile ID:</span> 
							<span class="field4r"><html:text property="idProfile" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Profile Desc:</span> 
							<span class="field30"><html:text property="idProfileDesc" maxlength="30"/></span>
						</div>
						<div class="row">
							<span class="label30">Administrator:</span> 
							<span class="field1"><html:checkbox property="manageAll" onclick="protect_all();"/></span>
							<span class="label30">Manage Agents:</span> 
							<span class="field1"><html:checkbox property="manageUsers"/></span>
						</div>
						<div class="row">
							<span class="label30">Manage Profiles:</span> 
							<span class="field1"><html:checkbox property="manageProfiles"/></span>
							<span class="label30">Manage WorkFlow:</span> 
							<span class="field1"><html:checkbox property="manageWorkFlow"/></span>
						</div>
						<div class="row">
							<span class="label30">Manage WorkLoad:</span> 
							<span class="field1"><html:checkbox property="manageWorkLoad"/></span>
							<span class="label30">Manage Approvals:</span> 
							<span class="field1"><html:checkbox property="manageApprovals"/></span>
						</div>
						<div class="row">
							<span class="label30">Manage Referral Sources:</span> 
							<span class="field1"><html:checkbox property="manageReferralSources"/></span>
							<span class="label30">Manage Reports:</span> 
							<span class="field1"><html:checkbox property="manageReports"/></span>
						</div>
						<div class="row">
							<span class="label30">Created By:</span> 
							<span class="field8r"><html:text property="idWrkrCreate" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Created On:</span> 
							<span class="field17r"><html:text property="tsCreate" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Updated By:</span> 
							<span class="field8r"><html:text property="idWrkrUpdate" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Updated On:</span> 
							<span class="field17r"><html:text property="tsUpdate" readonly="true"/></span>
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
				<h1>View eCSTS Profile</h1>
			</DIV>
			<DIV id="pagebody">
				<p>To make changes, click Edit. </p>
				
			<DIV id="errorheader"><logic:messagesPresent>
				<UL>
					<html:messages id="error">
						<LI><bean:write name="error" /></LI>
					</html:messages>
				</UL>
			</logic:messagesPresent></DIV>
				<DIV id="form">
				<html:form action="/ManageProfileAction?reqCode=edit">
					<fieldset><legend>eCSTS Profile Details</legend>
						<div class="row">
							<span class="label30">Profile ID:</span> 
							<span class="field4r"><html:text property="idProfile" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Profile Desc:</span> 
							<span class="field30r"><html:text property="idProfileDesc" readonly="true"/></span>
						</div>
						<div class="row">
							<logic:equal name="profileForm" property="manageAll" value="true">
								<span class="label30">Administrator:</span>
								<span class="field10r"><html:text property="manageAll" readonly="true" value="Yes"/></span>
							</logic:equal>
							<logic:notEqual name="profileForm" property="manageAll" value="true">
								<span class="label30">Administrator:</span>
								<span class="field10r"><html:text property="manageAll" readonly="true" value="No"/></span>
							</logic:notEqual>
							<logic:equal name="profileForm" property="manageUsers" value="true">
								<span class="label30">Manage Agents:</span> 
								<span class="field10r"><html:text property="manageUsers" readonly="true" value="Yes"/></span>
							</logic:equal>
							<logic:notEqual name="profileForm" property="manageUsers" value="true">
								<span class="label30">Manage Agents:</span> 
								<span class="field10r"><html:text property="manageUsers" readonly="true" value="No"/></span>
							</logic:notEqual>
						</div>
						<div class="row">
							<logic:equal name="profileForm" property="manageProfiles" value="true">
								<span class="label30">Manage Profiles:</span> 
								<span class="field10r"><html:text property="manageProfiles" readonly="true" value="Yes"/></span>
							</logic:equal>
							<logic:notEqual name="profileForm" property="manageProfiles" value="true">
								<span class="label30">Manage Profiles:</span> 
								<span class="field10r"><html:text property="manageProfiles" readonly="true" value="No"/></span>
							</logic:notEqual>
							<logic:equal name="profileForm" property="manageWorkFlow" value="true">
								<span class="label30">Manage WorkFlow:</span> 
								<span class="field10r"><html:text property="manageWorkFlow" readonly="true" value="Yes"/></span>
							</logic:equal>
							<logic:notEqual name="profileForm" property="manageWorkFlow" value="true">
								<span class="label30">Manage WorkFlow:</span> 
								<span class="field10r"><html:text property="manageWorkFlow" readonly="true" value="No"/></span>
							</logic:notEqual>
						</div>
						<div class="row">
							<logic:equal name="profileForm" property="manageWorkLoad" value="true">
								<span class="label30">Manage WorkLoad:</span> 
								<span class="field10r"><html:text property="manageWorkLoad" readonly="true" value="Yes"/></span>
							</logic:equal>
							<logic:notEqual name="profileForm" property="manageWorkLoad" value="true">
								<span class="label30">Manage WorkLoad:</span> 
								<span class="field10r"><html:text property="manageWorkLoad" readonly="true" value="No"/></span>
							</logic:notEqual>
							<logic:equal name="profileForm" property="manageApprovals" value="true">
								<span class="label30">Manage Approvals:</span> 
								<span class="field10r"><html:text property="manageApprovals" readonly="true" value="Yes"/></span>
							</logic:equal>
							<logic:notEqual name="profileForm" property="manageApprovals" value="true">
								<span class="label30">Manage Approvals:</span> 
								<span class="field10r"><html:text property="manageApprovals" readonly="true" value="No"/></span>
							</logic:notEqual>
						</div>
						<div class="row">
							<logic:equal name="profileForm" property="manageReferralSources" value="true">
								<span class="label30">Manage Referral Sources:</span> 
								<span class="field10r"><html:text property="manageReferralSources" readonly="true" value="Yes"/></span>
							</logic:equal>
							<logic:notEqual name="profileForm" property="manageReferralSources" value="true">
								<span class="label30">Manage Referral Sources:</span> 
								<span class="field10r"><html:text property="manageReferralSources" readonly="true" value="No"/></span>
							</logic:notEqual>
							<logic:equal name="profileForm" property="manageReports" value="true">
								<span class="label30">Manage Reports:</span> 
								<span class="field10r"><html:text property="manageReports" readonly="true" value="Yes"/></span>
							</logic:equal>
							<logic:notEqual name="profileForm" property="manageReports" value="true">
								<span class="label30">Manage Reports:</span> 
								<span class="field10r"><html:text property="manageReports" readonly="true" value="No"/></span>
							</logic:notEqual>
						</div>
						<div class="row">
							<span class="label30">Created By:</span> 
							<span class="field8r"><html:text property="idWrkrCreate" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Created On:</span> 
							<span class="field17r"><html:text property="tsCreate" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Updated By:</span> 
							<span class="field8r"><html:text property="idWrkrUpdate" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Updated On:</span> 
							<span class="field17r"><html:text property="tsUpdate" readonly="true"/></span>
						</div>
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
		<html:javascript formName="profileForm"/>		
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>