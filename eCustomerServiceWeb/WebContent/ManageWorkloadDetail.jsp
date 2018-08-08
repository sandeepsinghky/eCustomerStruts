<%@page import="org.dhhs.dirm.acts.cs.formbeans.*,org.dhhs.dirm.acts.cs.beans.*,java.util.Vector" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<jsp:useBean id="profiles" class="java.util.Vector" scope="application"/>

<%
	/**
	*	CT# 522373 - 08/09/04 - Change verbage to remove Click calculate to test drive
	*/
//	request.setAttribute("page","manageWorkload");
%>		

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Workload Management" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >
<script>

function validateTransfer(form) {

	var count = 0;
	var allInputs = document.getElementById("workloadForm").getElementsByTagName("input");
	for (var i = 0; i < allInputs.length; i++) {
		if (allInputs[i].type == "checkbox") {
			if (allInputs[i].checked == true) {
				count++;
			}
		}
	}
	
	var outstandingCount = 0;
	var pendingCount = 0;
	
	for (var i = 0; i < count; i++) {
		var outstanding = document.getElementById("workloadForm.pctOutstanding[" + i + "]");
		if (!IsNumeric(outstanding.value)) {
			alert("Outstanding count must be numeric");
			focus(outstanding);
			return false;
		}
		outstandingCount = Number(outstandingCount) + Number(outstanding.value);
		
		var pending = document.getElementById("workloadForm.pctPending[" + i + "]");
		
		if (!IsNumeric(pending.value)) {
			alert("Pending count must be numeric");
			focus(pending);
			return false;
		}
		pendingCount = Number(pendingCount) + Number(pending.value);
	}
	
	var msg = "";
	if (outstandingCount > Number(form.nbrOutstanding.value)) {
		msg = "The total outstanding tasks to allocate exceed that of the source user\n";
	}
	if (pendingCount > Number(form.nbrApproval.value)) {
		msg += "The total pending tasks to allocate exceed that of the source user\n";
	}
	if (msg > "") {
		alert(msg);
		return false;
	} else {
		return true;
	}
}

function cal(form) { 

	if (!validateTransfer(form)) {
		return false;
	}
	form.calculate.value = 1;	
	return true;

	/*
	var count = 0;
	var allInputs = document.getElementById("workloadForm").getElementsByTagName("input");
	for (var i = 0; i < allInputs.length; i++) {
		if (allInputs[i].type == "checkbox") {
			if (allInputs[i].checked == true) {
				count++;
			}
		}
	}
	
	var outstandingCount = 0;
	var pendingCount = 0;
	
	for (var i = 0; i < count; i++) {
		var pctOutstanding = document.getElementById("workloadForm.pctOutstanding[" + i + "]");
		var nbrOutstanding = document.getElementById("workloadForm.nbrOutstanding[" + i + "]");
		
		nbrOutstanding.value = Number(pctOutstanding.value) + Number(nbrOutstanding.value);
		
		var pctPending = document.getElementById("workloadForm.pctPending[" + i + "]");
		var nbrPending = document.getElementById("workloadForm.nbrPending[" + i + "]");
		
		nbrPending = Number(pctPending.value) + Number(nbrPending.value);
	}
	*/
}

function IsNumeric(strString) {
	//  check for valid numeric strings	
	var strValidChars = "0123456789";
	var strChar;
	var blnResult = true;

	if (strString.length == 0) return false;

	//  test strString consists of valid characters listed above
	for (i = 0; i < strString.length && blnResult == true; i++) {
		strChar = strString.charAt(i);
		if (strValidChars.indexOf(strChar) == -1) {
			blnResult = false;
		}
	}
	return blnResult;
}

</script>
	
		<%-- Content for the body section belongs here --%>
		
		<DIV id="pagetitle">
			<h1>eCustomer Service Workload Management Detail</h1>
		</DIV>
		<DIV id="pagebody">
			<p>Please select one or more target agent(s), enter number of outstanding and pending tasks to be transferred from the source agent. Click transfer to 
			perform the transfer.
			</p>

		<DIV id="errorheader"><logic:messagesPresent>
			<UL>
				<html:messages id="error">
					<LI><bean:write name="error" /></LI>
				</html:messages>
			</UL>
		</logic:messagesPresent></DIV>
			
			<DIV id="form">
				<html:form action="/ManageWorkloadAction.do?reqCode=transfer" onsubmit="return validateTransfer(this)">
					<fieldset><legend>Source Agent</legend>
						<div class="row">
							<span class="label30">ACTS Worker ID:</span> 
							<span class="field8r"><html:text property="workerid" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Name:</span> 
							<span class="field50r"><html:text property="nmWorker" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Outstanding Tasks:</span> 
							<span class="field4r"><html:text property="nbrOutstanding" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Pending Approval:</span> 
							<span class="field4r"><html:text property="nbrApproval" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Completed Tasks:</span> 
							<span class="field4r"><html:text property="nbrCompleted" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="field4r"><html:hidden property="calculate"/></span>
						</div>
						<div class="spacer">&nbsp;</div>
				
						<layout:collection property="targetUsers" styleClass="FORM" styleClass2="FORM2" sortAction="client" selectType="checkbox" selectName="selectedWorkers" selectProperty="idWorker"  title="" >
							<layout:collectionItem title="Target Agent ID" property="idWorker" sortable="true"/>
							<layout:collectionItem title="Name"  property="nmWorker" sortable="true"/>
							<layout:collectionItem title="Profile" width="50" property="idProfile"/>
							<layout:collectionItem title="Accept Workload ?" width="60"  property="cdAccptWrkld"/>
							<span class="field4r">
								<layout:collectionInput title="Outstanding Tasks"  width="80" property="nbrOutstanding" formProperty="nbrOurstanding" formName="workloadForm" mode="I,I,I"/>
							</span>	
							<span class="field4r">
								<layout:collectionInput title="Pending Tasks"  width="80" property="nbrApproval" formProperty="nbrApproval" formName="workloadForm" mode="I,I,I"/>
							</span>	
							<span class="field4r">
								<layout:collectionInput title="Completed Tasks"  width="80" property="nbrCompleted" formProperty="nbrCompleted" formName="workloadForm" mode="I,I,I"/>
							</span>	
							<span class="field4">
								<layout:collectionInput title="Allocate Outstanding"  width="90" property="pctOutstanding" formProperty="pctOutstanding" formName="workloadForm" maxlength="3"/>
							</span>	
							<span class="field4">
								<layout:collectionInput title="Allocate Pending"  width="90" property="pctPending" formProperty="pctPending" formName="workloadForm" maxlength="3"/>
							</span>	
						</layout:collection>
						<%-- Add the Buttons --%>
						<div class="row">
							<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
						</div>
						
						<div class="row">
							<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
							<span class="button"><html:submit>Transfer</html:submit></span>
							<span class="button"><html:reset><bean:message key="button.reset"/></html:reset></span>
						</div>
						<div class="spacer">&nbsp;</div>
						<div class="spacer">&nbsp;</div>
					</fieldset>
				</html:form>
			</DIV>
		</DIV>
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>
