<%@page import="org.dhhs.dirm.acts.cs.formbeans.*,org.dhhs.dirm.acts.cs.beans.*, fr.improve.struts.taglib.layout.util.*"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%
	org.dhhs.dirm.acts.cs.beans.UserBean loggedInUser = (org.dhhs.dirm.acts.cs.beans.UserBean) session.getAttribute("userbean");
%>
<!-- ... -->

<script>

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

	function CheckLimit(form, maxChars) {
		if (form.ntResolution.value.length > maxChars) {
			alert("Requested action cannot be completed as you have exceeded the maximum of 3900 characters allowed by the system.");
			return false;
		}
		return true;
	}

	function LimitText(fieldObj,maxChars){
		var result = true;
		if (fieldObj.value.length >= maxChars)
			result = false;
  
		if (window.event)
			window.event.returnValue = result;
		return result;
	}

	function doPrint(form) {
  		if (form.idEmail.value == "") {
			var url = "ManageTaskAction.do?reqCode=print&status=CLSD&idReference=" + form.idReference.value;
			window.open(url,"","toolbar=no,location=no,directories=no,status=no,menubar=no");
  		} else {
			window.location.href = "ManageTaskAction.do?reqCode=print&status=CLSD&idReference=" + form.idReference.value;
  		}
	}

	
	function doCreate(form) {
		if (validateManageTaskForm(form)) {
		
			if (!CheckLimit(form,3900)) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	
	function doSave(form) {
		if (validateManageTaskForm(form)) {
		
			if (!CheckLimit(form,3900)) {
				return false;
			}
			if (validateProcessStatus(form)) {
				return confirm("Are you sure you want to use the same processing status?");
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	function doReview(form) {
		if (validateManageTaskForm(form)) {
			if (!CheckLimit(form,3900)) {
				return false;
			}
		   if (validateProcessStatus(form)) {
			   if (form.cdStatus.value == "APRT") {
				   alert("Unable to perform the desired operation. This task has already been approved and returned. Click Complete to close");
				   return false;
			   }
			   form.cdStatus.value = "REQR";
			   return true;
		   } else {
			   return false;
		   }
		} else {
		   return false;
		}
	}

	function doDelete(form) {
		if (validateManageTaskForm(form)) {
		   if (validateProcessStatus(form)) {
			   if (form.cdStatus.value == "APRT") {
				   alert("Unable to perform the desired operation. This task has already been approved and returned. Click Complete to close");
				   return false;
			   }
			   form.cdStatus.value = "DELT";
			   return true;
		   } else {
			   return false;
		   }
		} else {
		   return false;
		}
	}

	
	function doComplete(form) {
		if (validateManageTaskForm(form)) {
			if (!CheckLimit(form,3900)) {
				return false;
			}
           if (validateProcessStatus(form)) {
	   		  if (form.cdStatus.value == "APRT") {
	   		  		if (form.idEmail.value == "") {
	   		  			form.target = "CorrespondenceWin"
		   		  		w = window.open(form.action,"CorrespondenceWin","toolbar=no,location=no,directories=no,status=no,menubar=no");
		   		  		w.focus();
	   			  		form.submit();
						setTimeout("", 1200);
						window.location.href = "ManageTaskAction.do?reqCode=view&status=CLSD&idReference=" + form.idReference.value;
						return false;
	   			  	}
		      } else {
					form.cdStatus.value = "CLSD";
					if (form.idEmail.value == "") {
						<%
							if (!loggedInUser.isApprovalRequired()) {
						%>
			   		  			form.target = "CorrespondenceWin"
				   		  		w = window.open(form.action,"CorrespondenceWin","toolbar=no,location=no,directories=no,status=no,menubar=no");
				   		  		w.focus();
			   			  		form.submit();
								setTimeout("", 1200);
								window.location.href = "ManageTaskAction.do?reqCode=view&status=CLSD&idReference=" + form.idReference.value;
								return false;
						<%
							}
						%>
					}
			  }
			  return true;
		   } else {
			   return false;
		   }
		} else {
		   return false;
		}
	}
	
	function validateProcessStatus(form) {
		if (form.cdStatus.value == "OPEN") {
			return true;
		}
		if (form.cdStatus.value == "APRV") {
			alert("Unable to perform the desired operation. This task has already been submitted for approval");
			return false;
		}
		if (form.cdStatus.value == "APCL") {
			alert("Unable to perform the desired operation. This task has already been approved and closed");
			return false;
		}
		if (form.cdStatus.value == "CLSD") {
			alert("Unable to perform the desired operation. This task has already been closed");
			return false;
		}
		if (form.cdStatus.value == "DELT") {
			alert("Unable to perform the desired operation. This task has been marked for deletion");
			return false;
		}
		if (form.cdStatus.value == "REQR") {
			alert("Unable to perform the desired operation. This task has already been submitted for approval");
			return false;
		}
		return true;		
	}
</script>

<%
//	request.setAttribute("page","manageTask");
%>		

	<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Manage Task" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >

	
		<%-- Content for the body section belongs here --%>
		
		<logic:equal name="formMode" value="0">		
			<DIV id="pagetitle">
				<h1>Create New eCSTS Task</h1>
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
				<html:form action="/ManageTaskAction?reqCode=store" focus="controlNumber" onsubmit="return validateManageTaskForm(this)"> 
					<fieldset><legend>eCSTS Task Details</legend>
						<div class="row">
							<span class="label30">Referral Type</span>
							<span class="field4">
								<html:select property="cdType">
									<html:options collection="forms" property="type"/>
								</html:select>
							</span>
						</div>
<!-- Change for mod 2869 -->
						<logic:equal name="userbean" property="manageAll" value="true">
							<div class="row">
								<span class="label30">Agent Assigned:</span> 
								<span class="field35">
									<html:select name="userbean" property="idWorker">
										<html:options collection="agents" labelProperty="nmWorker" property="idWorker" />
										<html:option value=""/>
									</html:select>
								</span>
							</div>
						</logic:equal>
						<logic:notEqual name="userbean" property="manageAll" value="true">
							<logic:equal name="userbean" property="manageUsers" value="true">
		   							<div class="row">
										<span class="label30">Agent Assigned:</span> 
										<span class="field35">
										<html:select name="userbean" property="idWorker">
											<html:options collection="agents" labelProperty="nmWorker" property="idWorker" />
											<html:option value=""/>
										</html:select>
									</span>
								</div>
						</logic:equal>
					</logic:notEqual>
<!-- Change for mod 2869 -->
						<div class="row">
							<span class="label30">Control Number:</span>
							<span class="field10"><html:text property="controlNumber" maxlength="10" /></span>
						</div>
						<div class="row">
							<span class="label30">County:</span> 
							<span class="field35">
								<html:select property="nmCounty">
									<html:option value=""/>
									<html:options name="countyCodes"/>
								</html:select>
							</span>
						</div>
						<div class="row">
							<span class="label30">IV-D Number:</span> 
							<span class="field10"><html:text property="nbCase" maxlength="10"/></span>
						</div>
						<div class="row">
							<span class="label30">MPI Number:</span> 
							<span class="field10"><html:text property="idPart" maxlength="10"/></span>
						</div>
						<div class="row">
							<span class="label30">SSN:</span> 
							<span class="field10"><html:text property="nbSSN" maxlength="11"/></span>
						</div>
						<div class="row">
							<span class="label30">Docket #:</span> 
							<span class="field17"><html:text property="nbDocket" maxlength="17"/></span>
						</div>
						<div class="row">
							<span class="label30">Email Address:</span> 
							<span class="field50"><html:text property="idEmail" maxlength="50"/></span>
						</div>
						<div class="row">
							<span class="label30">Confirm Email Address:</span> 
							<span class="field50"><html:text property="idEmailConfirm" maxlength="50"/></span>
						</div>
						<div class="row">
							<span class="label30">Phone Number:</span> 
							<span class="field4"><html:text property="nbTelAcd" maxlength="3"/></span>
							<span class="field4"><html:text property="nbTelExc" maxlength="3"/></span>
							<span class="field4"><html:text property="nbTelLn" maxlength="4"/></span>
							<span class="field8"><html:text property="nbTelExt" maxlength="6"/></span>
						</div>
						<div class="row">
							<span class="label30">Customer:</span> 
							<span class="field15"><html:text property="nmAuthorFirst" maxlength="15"/></span>
							<span class="field17"><html:text property="nmAuthorLast" maxlength="17"/></span>
							<span class="field1"><html:text property="nmAuthorMi" maxlength="1"/></span>
						</div>
						<div class="row">
							<span class="label30">Custodial Parent Name:</span> 
							<span class="field15"><html:text property="nmCustParFirst" maxlength="15"/></span>
							<span class="field17"><html:text property="nmCustParLast" maxlength="17"/></span>
							<span class="field1"><html:text property="nmCustParMi" maxlength="1"/></span>
						</div>
						<div class="row">
							<span class="label30">Non Custodial Parent Name:</span> 
							<span class="field15"><html:text property="nmNonCustParFirst" maxlength="15"/></span>
							<span class="field17"><html:text property="nmNonCustParLast" maxlength="17"/></span>
							<span class="field1"><html:text property="nmNonCustParMi" maxlength="1"/></span>
						</div>
						<div class="row">
							<span class="label30">Referral Source 1:</span> 
							<span class="field35">
								<html:select property="nmRefSource1">
									<html:option value=""/>
									<html:options collection="referralSources" labelProperty="nmStaff" property="idStaff"/>
								</html:select>
							</span>
						</div>
						<div class="row">
							<span class="label30">Referral Source 2:</span> 
							<span class="field35">
								<html:select property="nmRefSource2">
									<html:option value=""/>
									<html:options collection="referralSources" labelProperty="nmStaff" property="idStaff"/>
								</html:select>
							</span>
						</div>
						<div class="row">
							<span class="label30">Referral Source 3:</span> 
							<span class="field35">
								<html:select property="nmRefSource3">
									<html:option value=""/>
									<html:options collection="referralSources" labelProperty="nmStaff" property="idStaff"/>
								</html:select>
							</span>
						</div>
						<div class="row">
							<span class="label30">Referral Source 4:</span> 
							<span class="field35">
								<html:select property="nmRefSource4">
									<html:option value=""/>
									<html:options collection="referralSources" labelProperty="nmStaff" property="idStaff"/>
								</html:select>
							</span>
						</div>
						<div class="row">
							<span class="label30">Resolution Details:</span> 
							<span class="freeform"><html:textarea cols="80" property="ntResolution" rows="25" onkeypress="LimitText(this,3900)"/></span>
						</div>
						<div class="row">
							<span class="label30">Self Assigned?:</span> 
							<span class="field1"><html:checkbox property="selfAssigned"/></span>
						</div>
						<div class="spacer">&nbsp;</div>
					
						<%-- Add the Buttons --%>
						<div class="row">
							<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
						</div>
						
						<div class="row">
							<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
							<span class="button"><html:button property="Create" onclick="javascript:if (doCreate(document.manageTaskForm)){document.manageTaskForm.submit()}">Create</html:button></span>
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
				<h1>Manage eCSTS Task</h1>
			</DIV>
			<DIV id="pagebody">
				<p>This page allows the supervisor/agent to work on a selected task. The agent has the ability to submit the task for review, 
				print correspondence, save the task with the same process status or complete a task. If the logged in agent is a supervisor, then the agent has ability to delete the task.</p>
				
			<DIV id="errorheader"><logic:messagesPresent>
				<UL>
					<html:messages id="error">
						<LI><bean:write name="error" /></LI>
					</html:messages>
				</UL>
			</logic:messagesPresent></DIV>
				
				<DIV id="form">
				<html:form action="/ManageTaskAction?reqCode=save" focus="nbCase" onsubmit="return validateManageTaskForm(this)" >
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
							<span class="field10"><html:text property="controlNumber" maxlength="10" /></span>
						</div>
						<div class="row">
							<span class="label30">County:</span> 
							<span class="field35">
								<html:select property="nmCounty">
									<html:option value=""/>
									<html:options name="countyCodes"/>
								</html:select>
							</span>
						</div>
						<div class="row">
							<span class="label30">Agent Assigned:</span> 
							<span class="field8r"><html:text property="idWorker" readonly="true"/></span>
						</div>
						<html:hidden property="cdStatus"/>
<!-- COMMENTED BY RAMA 04/30/04. We do not want the agents to select a status code. The system will decide the 
	appropriate status. Only valid statuses that will be assigned on this screen are CLSD/APRV/REQR
-->
						<div class="row">
							<span class="label30">IV-D Number:</span> 
							<span class="field10"><html:text property="nbCase" maxlength="10"/></span>
						</div>
						<div class="row">
							<span class="label30">MPI Number:</span> 
							<span class="field10"><html:text property="idPart" maxlength="10"/></span>
						</div>
						<div class="row">
							<span class="label30">Docket #:</span> 
							<span class="field17"><html:text property="nbDocket" maxlength="17"/></span>
						</div>
						<div class="row">
							<span class="label30">SSN:</span> 
							<span class="field10"><html:text property="nbSSN" maxlength="11"/></span>
						</div>
						<div class="row">
							<span class="label30">Email Address:</span> 
							<span class="field50"><html:text property="idEmail" maxlength="50"/></span>
						</div>
						<div class="row">
							<span class="label30">Confirm Email Address:</span> 
							<span class="field50"><html:text property="idEmailConfirm" maxlength="50"/></span>
						</div>
						<div class="row">
							<span class="label30">Phone Number:</span> 
							<span class="field4"><html:text property="nbTelAcd" maxlength="3"/></span>
							<span class="field4"><html:text property="nbTelExc" maxlength="3"/></span>
							<span class="field4"><html:text property="nbTelLn" maxlength="4"/></span>
							<span class="field8"><html:text property="nbTelExt" maxlength="6"/></span>
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
							<span class="field15"><html:text property="nmAuthorFirst" maxlength="15"/></span>
							<span class="field17"><html:text property="nmAuthorLast" maxlength="17"/></span>
							<span class="field1"><html:text property="nmAuthorMi" maxlength="1"/></span>
						</div>
						<div class="row">
							<span class="label30">Custodial Parent Name:</span> 
							<span class="field15"><html:text property="nmCustParFirst" maxlength="15"/></span>
							<span class="field17"><html:text property="nmCustParLast" maxlength="17"/></span>
							<span class="field1"><html:text property="nmCustParMi" maxlength="1"/></span>
						</div>
						<div class="row">
							<span class="label30">Non Custodial Parent Name:</span> 
							<span class="field15"><html:text property="nmNonCustParFirst" maxlength="15"/></span>
							<span class="field17"><html:text property="nmNonCustParLast" maxlength="17"/></span>
							<span class="field1"><html:text property="nmNonCustParMi" maxlength="1"/></span>
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
							<span class="freeform"><html:textarea cols="80" property="ntResolution" rows="25" onkeypress="LimitText(this,3900)"/></span>
						</div>
						<div class="spacer">&nbsp;</div>
					
						<%-- Add the Buttons --%>
						<div class="row">
							<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
						</div>
						
						<div class="row">
							<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
							<span class="button"><html:button property="Save" onclick="javascript:if (doSave(document.manageTaskForm)){document.manageTaskForm.submit()}">Save</html:button></span>
							<span class="button"><html:button property="Supervisor Review" onclick="javascript:if (doReview(document.manageTaskForm)){document.manageTaskForm.submit()}">Supervisor Review</html:button></span>
							<logic:equal name="userbean" property="manageAll" value="true">							
								<span class="button"><html:button property="Delete" onclick="javascript:if (doDelete(document.manageTaskForm)){document.manageTaskForm.submit()}">Delete Task</html:button></span>
							</logic:equal>
							<span class="button"><html:button property="Complete" onclick="javascript:if (doComplete(document.manageTaskForm)){document.manageTaskForm.submit()}">Complete</html:button></span>
							<logic:equal name="manageTaskForm" property="cdStatus" value="CLSD">
								<span class="button"><html:button property="Print Correspondence" onclick="javascript:doPrint(document.manageTaskForm)">Print Correspondence</html:button></span>
							</logic:equal>
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
				<h1>View Selected eCSTS Task</h1>
			</DIV>
			<DIV id="pagebody">
				<p>To change task click Manage Task. </p>
				
			<DIV id="errorheader"><logic:messagesPresent>
				<UL>
					<html:messages id="error">
						<LI><bean:write name="error" /></LI>
					</html:messages>
				</UL>
			</logic:messagesPresent></DIV>
				
				<DIV id="form">
				<html:form action="/ManageTaskAction?reqCode=edit">
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
							<span class="label30">Process Status</span>
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
							<span class="field10r"><html:text property="nbSSN" readonly="true"/></span>
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
							<span class="freeformr"><html:textarea cols="80" property="ntResolution" rows="25" readonly="true"/></span>
						</div>


						<div class="spacer">&nbsp;</div>
					
						<%-- Add the Buttons --%>
						<div class="row">
							<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
						</div>
						
						<div class="row">
							<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
							<logic:equal name="userbean" property="manageAll" value="true">
								<span class="button"><html:submit>Manage Task</html:submit></span>
							</logic:equal>
							<logic:notEqual name="userbean" property="manageAll" value="true">
								<logic:equal name="userbean" property="manageUsers" value="true">
									<span class="button"><html:submit>Manage Task</html:submit></span>
								</logic:equal>
								<logic:notEqual name="userbean" property="manageUsers" value="true">
									<logic:equal name="manageTaskForm" property="owner" value="true">
										<span class="button"><html:submit>Manage Task</html:submit></span>
									</logic:equal>
								</logic:notEqual>
							</logic:notEqual>
							<logic:equal name="manageTaskForm" property="cdStatus" value="CLSD">
								<span class="button"><html:button property="Print Correspondence" onclick="javascript:doPrint(document.manageTaskForm)">Print Correspondence</html:button></span>
							</logic:equal>
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
						<layout:collectionItem title="View Details" property="" url="ManageTaskAction.do?reqCode=view" paramId="status,idReference" paramProperty="cdStatus,idReference"><img src="images/view.gif" border="0"></layout:collectionItem>
					</layout:collection>
				</fieldset>
				</logic:present>				
				</DIV>
			</DIV>
		</logic:equal>
			
<!-- COMMENTED BY Carolyn 09/08/04. added logic for value=3  for transfer-->
			
		<logic:equal name="formMode" value="3">
			<DIV id="pagetitle">
				<h1>View Selected eCSTS Task for Transfer</h1>
			</DIV>
			<DIV id="pagebody">
				<p>
					This page provides the supervisor the ability to review a task in detail and
					transfer. To transfer the task to another agent, select an agent from the list and click
					the transfer button.
				</p>
				
				<DIV id="errorheader"><logic:messagesPresent>
					<UL>
						<html:messages id="error">
							<LI><bean:write name="error" /></LI>
						</html:messages>
					</UL>
				</logic:messagesPresent></DIV>

				<DIV id="form">
				<html:form action="/ManageTaskAction?reqCode=transfer">
					<fieldset><legend>eCSTS Task Details</legend>
						<div class="row">
							<span class="label30">Reference ID:</span>
							<span class="field10r"><html:text property="idReference" readonly="true"/></span>
						</div>
						<div class="row">
								<span class="label30">Agent Assigned:</span> 
								<span class="field35">
									<html:select name="userbean" property="idWorker">
										<html:options collection="agents" labelProperty="nmWorker" property="idWorker" />
									</html:select>
								</span>
						</div>
						<div class="row">
							<span class="label30">Referral Type:</span>
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
							<span class="label30">Process Status</span>
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
							<span class="field10r"><html:text property="nbSSN" readonly="true"/></span>
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
							<span class="freeformr"><html:textarea cols="80" property="ntResolution" rows="25" readonly="true"/></span>
						</div>


						<div class="spacer">&nbsp;</div>
					
						<%-- Add the Buttons --%>
						<div class="row">
							<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
						</div>
						
						<div class="row">
							<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
							<span class="button"><html:submit>Transfer Task</html:submit></span>
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
					</layout:collection>
				</fieldset>
				</logic:present>				
			</DIV>
		</logic:equal>
		<html:javascript formName="manageTaskForm"/>
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>