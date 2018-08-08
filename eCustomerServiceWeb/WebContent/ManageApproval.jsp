<%@page import="org.dhhs.dirm.acts.cs.formbeans.*,org.dhhs.dirm.acts.cs.beans.*, fr.improve.struts.taglib.layout.util.*"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
//	request.setAttribute("page","manageApproval");
%>		

<script>

	//rk 08/27/04. CT# 530077. Limit the user from enterting more than 3900 characters
	function TrackCount(fieldObj,countFieldName,maxChars) {
		var countField = eval("fieldObj.form."+countFieldName);
		var diff = maxChars - fieldObj.value.length;

		// Need to check & enforce limit here also in case user pastes data
		if (diff < 0) {
			fieldObj.value = fieldObj.value.substring(0,maxChars);
			diff = maxChars - fieldObj.value.length;
		}
		countField.value = diff;
	}

	function LimitText(fieldObj,maxChars){
		var result = true;
		if (fieldObj.value.length >= maxChars)
			result = false;
  
		if (window.event)
			window.event.returnValue = result;
		return result;
	}
	// CT# 530077. End

	function doApproveComplete(form) {
		form.cdStatus.value = "APCL";
  		if (form.idEmail.value == "") {
  			form.target = "CorrespondenceWin"
	  		w = window.open(form.action,"CorrespondenceWin","toolbar=no,location=no,directories=no,status=no,menubar=no");
	  		w.focus();
	  		form.submit();
			setTimeout("", 2000);
			window.location.href = "ManageApprovalAction.do?reqCode=view&status=CLSD&idReference=" + form.idReference.value;
			return false;
	  	} else {
	  		return true;
	  	}
	}

	function doCorrect(form) {
		form.cdStatus.value = "CORR";
		return true;
	}
	
	function doApproveReturn(form) {
		form.cdStatus.value = "APRT";
		return true;
	}
	
</script>

	<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Manage Approval" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >

	
		<%-- Content for the body section belongs here --%>
		
		<logic:equal name="formMode" value="1">		
			<DIV id="pagetitle">
				<h1>Manage eCSTS Task Approval</h1>
			</DIV>
			<DIV id="pagebody">
				<p>This page allows the supervisor to either approve, reject or reassign a task. Based on the option selected, the system either prompts the user to
				perform certain actions or the system automatically performs certain actions.</p>
				
			<DIV id="errorheader"><logic:messagesPresent>
				<UL>
					<html:messages id="error">
						<LI><bean:write name="error" /></LI>
					</html:messages>
				</UL>
			</logic:messagesPresent></DIV>
				
				<DIV id="form">
				<html:form action="/ManageApprovalAction?reqCode=save" focus="nbCase">
					<fieldset><legend>eCSTS Task Details</legend>
						<div class="row">
							<span class="label30">Reference ID:</span> 
							<span class="field10r"><html:text property="idReference" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Referral Type</span>
							<span class="field4r"><html:text property="cdType" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Control Number:</span>
							<span class="field10r"><html:text property="controlNumber" readonly="true" /></span>
						</div>
						<div class="row">
							<span class="label30">County:</span> 
							<span class="field10r"><html:text property="nmCounty"  readonly="true" /></span>
						</div>
						<div class="row">
							<span class="label30">Agent Assigned:</span>
							<span class="field8r"><html:text property="idWorker" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Process Status:</span> 
							<span class="field4r"><html:text property="cdStatus" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">IV-D Number:</span> 
							<span class="field10r"><html:text property="nbCase" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">MPI Number:</span> 
							<span class="field10r"><html:text property="idPart" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Docket #:</span> 
							<span class="field17r"><html:text property="nbDocket" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">SSN:</span> 
							<span class="field17r"><html:text property="nbSSN" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Email Address:</span> 
							<span class="field50r"><html:text property="idEmail" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Phone Number:</span> 
							<span class="field4r"><html:text property="nbTelAcd" readonly="true"/></span>
							<span class="field4r"><html:text property="nbTelExc" readonly="true"/></span>
							<span class="field4r"><html:text property="nbTelLn" readonly="true"/></span>
							<span class="field8r"><html:text property="nbTelExt" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Date Received:</span> 
							<span class="field15r"><html:text property="dtReceived" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Date Completed:</span> 
							<span class="field15r"><html:text property="dtCompleted"  readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Date Due:</span> 
							<span class="field15r"><html:text property="dtDue" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Customer:</span> 
							<span class="field15r"><html:text property="nmAuthorFirst" readonly="true"/></span>
							<span class="field17r"><html:text property="nmAuthorLast" readonly="true"/></span>
							<span class="field1r"><html:text property="nmAuthorMi" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Custodial Parent Name:</span> 
							<span class="field15r"><html:text property="nmCustParFirst" readonly="true"/></span>
							<span class="field17r"><html:text property="nmCustParLast" readonly="true"/></span>
							<span class="field1r"><html:text property="nmCustParMi" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Non Custodial Parent Name:</span> 
							<span class="field15r"><html:text property="nmNonCustParFirst" readonly="true"/></span>
							<span class="field17r"><html:text property="nmNonCustParLast" readonly="true"/></span>
							<span class="field1r"><html:text property="nmNonCustParMi" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Referral Source 1:</span> 
							<span class="field30r"><html:text property="nmRefSource1" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Referral Source 2:</span> 
							<span class="field30r"><html:text property="nmRefSource2" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Referral Source 3:</span> 
							<span class="field30r"><html:text property="nmRefSource3" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Referral Source 4:</span> 
							<span class="field30r"><html:text property="nmRefSource4" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Resolution Details:</span> 
							<span class="freeform"><html:textarea cols="80" property="ntResolution" rows="5" onkeypress="LimitText(this,3900)"/></span>
						</div>


						<div class="spacer">&nbsp;</div>
					
						<%-- Add the Buttons --%>
						<div class="row">
							<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
						</div>
						
						<div class="row">
							<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
							<span class="button"><html:button property="ApproveComplete" onclick="javascript:if (doApproveComplete(document.approvalTaskForm)){document.approvalTaskForm.submit()}">Approve & Complete</html:button></span>
							<span class="button"><html:button property="ApproveReturn" onclick="javascript:if (doApproveReturn(document.approvalTaskForm)){document.approvalTaskForm.submit()}">Approve & Return</html:button></span>
							<span class="button"><html:button property="Correct" onclick="javascript:if (doCorrect(document.approvalTaskForm)){document.approvalTaskForm.submit()}">Correct</html:button></span>
						</div>
						<div class="spacer">&nbsp;</div>
						<div class="spacer">&nbsp;</div>
					</fieldset>
				</html:form>

				<logic:present scope="request" name="taskHistory">
				<p>Task History.</p>
				<fieldset><legend>eCSTS Task History</legend>
					<layout:collection name="taskHistory" styleClass="FORM" styleClass2="FORM2" sortAction="client" title="" >
						<layout:collectionItem title="Agent Assigned" property="nmWorkerAssign"/>
						<layout:collectionItem title="Process Status" property="cdStatus"/>
						<layout:collectionItem title="Start Date" property="formattedStart"/>
						<layout:collectionItem title="End Date" property="formattedEnd"/>
						<layout:collectionItem title="Due Date" property="formattedDue"/>
					</layout:collection>
				</fieldset>
				</logic:present>				
				</DIV>
			</DIV>
		</logic:equal>
		
		<logic:equal name="formMode" value="2">
			<DIV id="pagetitle">
				<h1>View eCSTS Task Approval</h1>
			</DIV>
			<DIV id="pagebody">
				<p>To Manage task approval, click Manage Task Approval. </p>
				
			<DIV id="errorheader"><logic:messagesPresent>
				<UL>
					<html:messages id="error">
						<LI><bean:write name="error" /></LI>
					</html:messages>
				</UL>
			</logic:messagesPresent></DIV>
				
				<DIV id="form">
				<html:form action="/ManageApprovalAction?reqCode=manage">
					<fieldset><legend>eCSTS Task Details</legend>
						<div class="row">
							<span class="label30">Reference ID:</span>
							<span class="field10r"><html:text property="idReference" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Referral Type:</span>
							<span class="field4r"><html:text property="cdType" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Agent Assigned:</span>
							<span class="field8r"><html:text property="idWorker" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Current Status</span>
							<span class="field4r"><html:text property="cdStatus" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">IV-D Number:</span> 
							<span class="field10r"><html:text property="nbCase" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">MPI Number:</span> 
							<span class="field10r"><html:text property="idPart" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Docket #:</span> 
							<span class="field17r"><html:text property="nbDocket" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Email Address:</span> 
							<span class="field50r"><html:text property="idEmail" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Phone Number:</span> 
							<span class="field4r"><html:text property="nbTelAcd" readonly="true"/></span>
							<span class="field4r"><html:text property="nbTelExc" readonly="true"/></span>
							<span class="field4r"><html:text property="nbTelLn" readonly="true"/></span>
							<span class="field8r"><html:text property="nbTelExt" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Date Received:</span> 
							<span class="field10r"><html:text property="dtReceived" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Date Completed:</span> 
							<span class="field10r"><html:text property="dtCompleted"  readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Date Due:</span> 
							<span class="field10r"><html:text property="dtDue" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Author:</span> 
							<span class="field15r"><html:text property="nmAuthorFirst" readonly="true"/></span>
							<span class="field17r"><html:text property="nmAuthorLast" readonly="true"/></span>
							<span class="field1r"><html:text property="nmAuthorMi" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Custodial Parent Name:</span> 
							<span class="field15r"><html:text property="nmCustParFirst" readonly="true"/></span>
							<span class="field17r"><html:text property="nmCustParLast" readonly="true"/></span>
							<span class="field1r"><html:text property="nmCustParMi" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Non Custodial Parent Name:</span> 
							<span class="field15r"><html:text property="nmNonCustParFirst" readonly="true"/></span>
							<span class="field17r"><html:text property="nmNonCustParLast" readonly="true"/></span>
							<span class="field1r"><html:text property="nmNonCustParMi" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Referral Source 1:</span> 
							<span class="field30r"><html:text property="nmRefSource1" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Referral Source 2:</span> 
							<span class="field30r"><html:text property="nmRefSource2" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Referral Source 3:</span> 
							<span class="field30r"><html:text property="nmRefSource3" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Resolution Details:</span> 
							<span class="freeformr"><html:textarea cols="80" property="ntResolution" rows="5" readonly="true"/></span>
						</div>


						<div class="spacer">&nbsp;</div>
					
						<%-- Add the Buttons --%>
						<div class="row">
							<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
						</div>
						
						<div class="row">
							<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
						</div>
						<div class="spacer">&nbsp;</div>
						<div class="spacer">&nbsp;</div>
					</fieldset>
				</html:form>
				
				<logic:present scope="request" name="taskHistory">
				<p>Task History.</p>
				<fieldset><legend>eCSTS Task History</legend>
					<layout:collection name="taskHistory" styleClass="FORM" styleClass2="FORM2" sortAction="client" title="" indexId="count">
						<layout:collectionItem title="Agent Assigned" property="nmWorkerAssign"/>
						<layout:collectionItem title="Process Status" property="cdStatus"/>
						<layout:collectionItem title="Start Date" property="formattedStart"/>
						<layout:collectionItem title="End Date" property="formattedEnd"/>
						<layout:collectionItem title="Due Date" property="formattedDue"/>
						<layout:collectionItem title="View Details" property="" url="ManageApprovalAction.do?reqCode=view" paramId="status,idReference" paramProperty="cdStatus,idReference"><img src="images/view.gif" border="0"></layout:collectionItem>
					</layout:collection>
				</fieldset>
				</logic:present>				
				</DIV>
			</DIV>
		</logic:equal>
		
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>