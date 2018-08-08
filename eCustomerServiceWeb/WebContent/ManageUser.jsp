<%@page import="org.dhhs.dirm.acts.cs.formbeans.*,org.dhhs.dirm.acts.cs.beans.*,org.dhhs.dirm.acts.cs.struts.*, fr.improve.struts.taglib.layout.util.*"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<jsp:useBean id="profiles" class="java.util.Vector" scope="application"/>
<%
//	request.setAttribute("page","manageUser");
	String idWrkr = "";
%>		

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Modify User" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >

	
		<%-- Content for the body section belongs here --%>
		
		<logic:present scope="request" name="userForm">
			<%
				UserForm userForm = (UserForm)request.getAttribute("userForm");
				idWrkr = userForm.getIdWorker();
			%>
		</logic:present>
		
		
		<logic:equal name="formMode" value="0">		
			<DIV id="pagetitle">
				<h1>Create New eCSTS Agent</h1>
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
				<html:form action="/ManageUserAction?reqCode=store" focus="idPassword">
					<fieldset><legend>eCSTS Agent Details</legend>
						<div class="row">
							<span class="label30">ACTS Worker ID:</span> 
							<span class="field8r"><html:text property="idWorker" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Name:</span> 
							<span class="field30r"><html:text property="nmWorker" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Profile ID:</span> 
							<span class="field4">
								<html:select property="idProfile">
									<html:options collection="profiles" property="idProfile"/>
								</html:select>
							</span>
						</div>						
						<div class="row">
							<span class="label30">Accept Workload ?:</span> 
							<span class="field1"><html:checkbox property="cdAccptWrkld"/></span>
						</div>
						<div class="row">
							<span class="label30">Approval Required ?:</span> 
							<span class="field1"><html:checkbox property="approvalRequired"/></span>
						</div>
						<div class="row">
							<span class="label30">Phone Number:</span> 
							<span class="field17r"><html:text property="nbPhone" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Printer:</span> 
							<span class="field8r"><html:text property="idPrinter" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Work Hours:</span> 
							<span class="field8r"><html:text property="nbWorkHourStart" readonly="true"/></span>
							<span class="field8r"><html:text property="nbWorkHourEnd" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Lunch:</span> 
							<span class="field8r"><html:text property="nbLunchStart" readonly="true"/></span>
							<span class="field8r"><html:text property="nbLunchEnd" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Email:</span> 
							<span class="field50r"><html:text property="idEmail" readonly="true"/></span>
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
				<h1>Edit eCSTS Agent</h1>
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
				<html:form action="/ManageUserAction?reqCode=save">
					<fieldset><legend>eCSTS Agent Details</legend>
						<div class="row">
							<span class="label30">ACTS Worker ID:</span> 
							<span class="field8r"><html:text property="idWorker" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Name:</span> 
							<span class="field30r"><html:text property="nmWorker" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Profile ID:</span> 
							<span class="field4">
								<html:select property="idProfile">
									<html:options collection="profiles" property="idProfile"/>
								</html:select>
							</span>
						</div>
						<div class="row">
							<span class="label30">Accept Workload ?:</span> 
							<span class="field1"><html:checkbox property="cdAccptWrkld"/></span>
						</div>
						<div class="row">
							<span class="label30">Reset Password ?:</span> 
							<span class="field1"><html:checkbox property="cdResetPassword"/></span>
						</div>
						<div class="row">
							<span class="label30">Approval Required ?:</span> 
							<span class="field1"><html:checkbox property="approvalRequired"/></span>
						</div>
						<div class="row">
							<span class="label30">Outstanding Tasks:</span> 
							<span class="field4r"><html:text property="nbOutstanding" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Pending Approval:</span> 
							<span class="field4r"><html:text property="nbApproval" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Completed Tasks:</span> 
							<span class="field4r"><html:text property="nbCompleted" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Phone Number:</span> 
							<span class="field17r"><html:text property="nbPhone" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Printer:</span> 
							<span class="field8r"><html:text property="idPrinter" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Work Hours:</span> 
							<span class="field8r"><html:text property="nbWorkHourStart" readonly="true"/></span>
							<span class="field8r"><html:text property="nbWorkHourEnd" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Lunch:</span> 
							<span class="field8r"><html:text property="nbLunchStart" readonly="true"/></span>
							<span class="field8r"><html:text property="nbLunchEnd" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Email:</span> 
							<span class="field50r"><html:text property="idEmail" readonly="true"/></span>
						</div>
						<div class="spacer">&nbsp;</div>
					
						<%-- Add the Buttons --%>
						<div class="row">
							<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
						</div>
												
						<div class="row">
							<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
							<span class="button"><input type="button" name="Preferences" value="Preferences" onclick="window.location.href='UserPreferencesAction.do?reqCode=edit&idWorker=<%=idWrkr%>';"></span>
							<span class="button"><input type="button" name="Reassign" value="Reassign" onclick="window.location.href='ManageWorkloadAction.do?reqCode=loadAll&idWorker=<%=idWrkr%>';"></span>
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
				<h1>View eCSTS Agent Information</h1>
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
				<html:form action="/ManageUserAction?reqCode=edit">
					<fieldset><legend>eCSTS Agent Details</legend>
						<div class="row">
							<span class="label30">ACTS Worker ID:</span> 
							<span class="field8r"><html:text property="idWorker" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Name:</span> 
							<span class="field30r"><html:text property="nmWorker" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Profile ID:</span> 
							<span class="field4r"><html:text property="idProfile" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Accept Workload ?:</span> 
							<span class="field1"><html:checkbox property="cdAccptWrkld" disabled="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Approval Required ?:</span> 
							<span class="field1"><html:checkbox property="approvalRequired" disabled="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Outstanding Tasks:</span> 
							<span class="field4r"><html:text property="nbOutstanding" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Pending Approval:</span> 
							<span class="field4r"><html:text property="nbApproval" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Completed Tasks:</span> 
							<span class="field4r"><html:text property="nbCompleted" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Phone Number:</span> 
							<span class="field17r"><html:text property="nbPhone" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Printer:</span> 
							<span class="field8r"><html:text property="idPrinter" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Work Hours:</span> 
							<span class="field8r"><html:text property="nbWorkHourStart" readonly="true"/></span>
							<span class="field8r"><html:text property="nbWorkHourEnd" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Lunch:</span> 
							<span class="field8r"><html:text property="nbLunchStart" readonly="true"/></span>
							<span class="field8r"><html:text property="nbLunchEnd" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Email:</span> 
							<span class="field50r"><html:text property="idEmail" readonly="true"/></span>
						</div>
						<div class="spacer">&nbsp;</div>
					
						<%-- Add the Buttons --%>
						<div class="row">
							<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
						</div>
						
						<div class="row">
							<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
							<span class="button"><html:submit>Edit</html:submit></span>
							<span class="button"><input type="button" name="Preferences" value="Preferences" onclick="window.location.href='UserPreferencesAction.do?reqCode=view&idWorker=<%=idWrkr%>';"></span>
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