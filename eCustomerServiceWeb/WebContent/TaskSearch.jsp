<%@page import="org.dhhs.dirm.acts.cs.formbeans.*,org.dhhs.dirm.acts.cs.beans.*,java.util.*,org.apache.struts.*,org.apache.struts.action.*" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<jsp:useBean id="forms" class="java.util.Vector" scope="application"/>

<%

/**
   Name        : TaskSearch.jsp
   Description : This program will display the Search Screen for Task for Customer Service user
   
   07/14/2006 BST Incident #224859
              Fixed the problem so that LSA worker will be able work current
              in box task even though their Accept Workload box is unchecked.
              This include addition of userBean.isManageProfiles() in the logic
              and modification of LSA profile to enable ManageProfile.
**/
	request.setAttribute("page","taskSearch");
	
	UserBean userBean = (UserBean)session.getAttribute("userbean");
	boolean acceptWorkLoad = true;
	
	java.util.Vector taskList = (java.util.Vector)request.getAttribute("tasklist");
	
	if (userBean.getCdAccptWrkld().equalsIgnoreCase("N")) {
	
		acceptWorkLoad = false;
		ActionMessages messageHolder = new ActionMessages();
		messageHolder.add(ActionMessages.GLOBAL_MESSAGE,new ActionMessage("error.unauthorized.taskcreate"));
		request.setAttribute(Globals.MESSAGE_KEY,messageHolder);
		
		ActionErrors errorHolder = (ActionErrors)request.getAttribute(Globals.ERROR_KEY);
		if (errorHolder == null) {
			errorHolder = new ActionErrors();
		}
		
		if ((userBean.isManageAll() || userBean.isManageUsers()) || userBean.isManageProfiles()) {
		} else {
			errorHolder.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.unauthorized.taskcreate"));
			request.setAttribute(Globals.ERROR_KEY,errorHolder);
		}
	}
%>		

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Task Search" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >


<script LANGUAGE="JavaScript">

	var today = new Date();
	var Year = takeYear(today);
	var Month = leadingZero(today.getMonth()+1);
	var Day = leadingZero(today.getDate());
	var now = Month + "/" + Day + "/" + Year;

	function customValidateForm(form) {
		var rtn = validateTaskSearchForm(form);
		var selected = 0;
		if (rtn == true) {
			if (form.dtReceived.value != null) {
				var val = compareDates(form.dtReceived.value,now);
				if (val == 1) {
					alert("Date received must be equal to or less than today's date");
					return false;
				}
			}
			if (form.agentSearchOverride.checked == true){
		             selected = selected + 1;
		           }  

		if (selected == 1) {			 		
 	      		if(form.idReference.value.length <= 0	&&   		 	      		
               		 form.controlNumber.value.length <= 0	&&
			 form.nbCase.value.length <= 0  	&&
               		 form.idPart.value.length <= 0  	&&
               		 form.nbSSN.value.length <= 0  	        &&
               		 form.nbDocket.value.length <= 0  	&&
                   	 form.idEmail.value.length <= 0  	&&               		 
               		 form.nmAuthorFirst.value.length <= 0  	&&
               		 form.nmAuthorLast.value.length <= 0  	&&
               		 form.nmAuthorMi.value.length <= 0)
               		 
			 {
               		alert("Enter at least one case specific field to search");			
		       return false;
		         }
		     } 


                       
			if (form.dtCompleted.value != null) {
				val = compareDates(form.dtCompleted.value,now);
				if (val == 1) {
			alert("Date completed must be equal to or less than today's date");
					return false;
				}
			}
			return true;
		} else {
			return rtn;
		}
	}
	
	function compareDates (value1, value2) {
	   var date1, date2;
	   var month1, month2;
	   var year1, year2;

	   month1 = value1.substring (0, value1.indexOf ("/"));
	   date1 = value1.substring (value1.indexOf ("/")+1, value1.lastIndexOf ("/"));
	   year1 = value1.substring (value1.lastIndexOf ("/")+1, value1.length);

	   month2 = value2.substring (0, value2.indexOf ("/"));
	   date2 = value2.substring (value2.indexOf ("/")+1, value2.lastIndexOf ("/"));
	   year2 = value2.substring (value2.lastIndexOf ("/")+1, value2.length);

	   if (year1 > year2) return 1;
	   else if (year1 < year2) return -1;
	   else if (month1 > month2) return 1;
	   else if (month1 < month2) return -1;
	   else if (date1 > date2) return 1;
	   else if (date1 < date2) return -1;
	   else return 0;
	} 
	

	function takeYear(theDate){
		x = theDate.getYear();
		var y = x % 100;
		y += (y < 38) ? 2000 : 1900;
		return y;
	}

	function leadingZero(nr){
		if (nr < 10) nr = "0" + nr;
		return nr;
	}

	function reset_all(form) {
		var count = 0;
		var allInputs = document.getElementById("taskSearchForm").getElementsByTagName("input");
		for (var i = 0; i < allInputs.length; i++) {
			if (allInputs[i].type == "text") {
				allInputs[i].value = "";
			}
			if (allInputs[i].type == "checkbox") {
				allInputs[i].checked = false;
			}
		}
		var allSelects = document.getElementById("taskSearchForm").getElementsByTagName("select");
		for (var i = 0; i < allSelects.length; i++) {
			if (allSelects[i].name != "idWorker") {
				allSelects[i].value = "";
			}
		}
	}
	
	function validateWorkload() {
	<%
		if (acceptWorkLoad) {
	%>
			// User can accept workload
			window.location.href = "ManageTaskAction.do?reqCode=create";
			return true;
	<%			
		} else {
			if ((userBean.isManageAll() || userBean.isManageUsers()) || userBean.isManageProfiles()) {
	%>
				// User cannot accept workload, but is a supervisor or manager
				window.location.href = "ManageTaskAction.do?reqCode=create";
				return true;
	<%
			} else {
	%>
				// User cannot accept workload and not a supervisor or manager
				alert("Your user profile does not allow task creation. Please contact your supervisor.");
				return false;
	<%
			}
		}
	%>	
	}
	
</script>
	
		<%-- Content for the body section belongs here --%>
				
		<DIV id="pagetitle">
			<h1>eCustomer Service Task Search</h1>
		</DIV>
		<DIV id="pagebody">
			<p>Complete desired fields and click the search button. You can search on a Reference ID if you have one for direct access. However, if you do not have one
			, you can increase the accuracy of the searches by supplying other relevant fields that fine-tune your search. 
			<br><br>
			If you are an agent	and would like to search tasks that might belong to other agents, please select the "Agent Override" check box in addition to entering
			the relevant filter criteria.
			</p>

		<DIV id="errorheader"><logic:messagesPresent>
			<UL>
				<html:messages id="error">
					<LI><bean:write name="error" /></LI>
				</html:messages>
			</UL>
		</logic:messagesPresent></DIV>

		<DIV id="form">
				<html:form action="/TaskSearchAction" focus="idReference" styleClass="form100" onsubmit="return customValidateForm(this)">
					<fieldset><legend>eCSTS Task Search</legend>
						<div class="row">
							<span class="label30">Reference ID:</span> 
							<span class="field8"><html:text property="idReference" maxlength="10" /> Enter the specific reference id or any combination of fields below</span>
						</div>
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
						<logic:notEqual name="userbean" property="manageAll" value="true">
							<logic:notEqual name="userbean" property="manageUsers" value="true">
								<div class="row">
									<span class="label30">Agent Override:</span>
									<span class="field1"><html:checkbox property="agentSearchOverride" /></span>
								</div>
							</logic:notEqual>
						</logic:notEqual>
						<logic:equal name="userbean" property="byReferralType" value="true">
							<div class="row">
								<span class="label30">Referral Type:</span>
								<span class="field4">
									<html:select property="cdType">
										<html:option value=""/>
										<html:options collection="forms" property="type"/>
									</html:select>
								</span>
							</div>
						</logic:equal>
						<logic:equal name="userbean" property="byControlNbr" value="true">
							<div class="row">
								<span class="label30">Control Number:</span> 
								<span class="field10"><html:text property="controlNumber" maxlength="10" /></span>
							</div>
						</logic:equal>
						<logic:equal name="userbean" property="byCounty" value="true">
							<div class="row">
								<span class="label30">County:</span> 
								<span class="field35">
									<html:select property="nmCounty">
										<html:option value=""/>
										<html:options name="countyCodes"/>
									</html:select>
								</span>
							</div>
						</logic:equal>
						<logic:equal name="userbean" property="byNbCase" value="true">
							<div class="row">
								<span class="label30">IV-D Case #:</span> 
								<span class="field10"><html:text property="nbCase" maxlength="10" /></span>
							</div>
						</logic:equal>
						<logic:equal name="userbean" property="byIdPart" value="true">
							<div class="row">
								<span class="label30">MPI #:</span> 
								<span class="field10"><html:text property="idPart" maxlength="10" /></span>
							</div>
						</logic:equal>
						<logic:equal name="userbean" property="byNbSSN" value="true">
							<div class="row">
								<span class="label30">SSN:</span>
								<span class="field10"><html:text property="nbSSN" maxlength="11" /></span>
							</div>
						</logic:equal>
						<logic:equal name="userbean" property="byNbDkt" value="true">
							<div class="row">
								<span class="label30">Docket #:</span>
								<span class="field17"><html:text property="nbDocket" maxlength="17" /></span>
							</div>
						</logic:equal>
						<logic:equal name="userbean" property="byEmail" value="true">
							<div class="row">
								<span class="label30">Email:</span> 
								<span class="field50"><html:text property="idEmail" maxlength="50" /></span>
							</div>
						</logic:equal>
						<logic:equal name="userbean" property="byDtReceived" value="true">
							<div class="row">
								<span class="label30">Date Received:</span> 
								<span class="field10"><html:text property="dtReceived" maxlength="10" /></span>
							</div>
						</logic:equal>
											
						<logic:equal name="userbean" property="byDtCompleted" value="true">
							<div class="row">
								<span class="label30">Date Completed:</span> 
								<span class="field10"><html:text property="dtCompleted" maxlength="10" /></span>
							</div>
						</logic:equal>					
						
						<logic:equal name="userbean" property="byDtDue" value="true">
							<div class="row">
								<span class="label30">Date Due:</span> 
								<span class="field10"><html:text property="dtDue" maxlength="10" /></span>
							</div>
						</logic:equal>
						<logic:equal name="userbean" property="byCustomer" value="true">
							<div class="row">
								<span class="label30">Customer:</span> 
								<span class="field15"><html:text property="nmAuthorFirst" maxlength="15"/></span>
								<span class="field17"><html:text property="nmAuthorLast" maxlength="17"/></span>
								<span class="field1"><html:text property="nmAuthorMi" maxlength="1"/></span>
							</div>
						</logic:equal>
						<logic:equal name="userbean" property="byCP" value="true">
							<div class="row">
								<span class="label30">Custodial Parent Name:</span> 
								<span class="field15"><html:text property="nmCustParFirst" maxlength="15"/></span>
								<span class="field17"><html:text property="nmCustParLast" maxlength="17"/></span>
								<span class="field1"><html:text property="nmCustParMi" maxlength="1"/></span>
							</div>
						</logic:equal>
						<logic:equal name="userbean" property="byNCP" value="true">
							<div class="row">
								<span class="label30">Non Custodial Parent Name:</span> 
								<span class="field15"><html:text property="nmNonCustParFirst" maxlength="15"/></span>
								<span class="field17"><html:text property="nmNonCustParLast" maxlength="17"/></span>
								<span class="field1"><html:text property="nmNonCustParMi" maxlength="1"/></span>
							</div>
						</logic:equal>
						<logic:equal name="userbean" property="bySrc1" value="true">
							<div class="row">
								<span class="label30">Referral Source 1:</span> 
								<span class="field35">
									<html:select property="nmRefSource1">
										<html:option value=""/>
										<html:options collection="referralSources" labelProperty="nmStaff" property="idStaff"/>
									</html:select>
								</span>
							</div>
						</logic:equal>
						<logic:equal name="userbean" property="bySrc2" value="true">
							<div class="row">
								<span class="label30">Referral Source 2:</span> 
								<span class="field35">
									<html:select property="nmRefSource2">
										<html:option value=""/>
										<html:options collection="referralSources" labelProperty="nmStaff" property="idStaff"/>
									</html:select>
								</span>
							</div>
						</logic:equal>
						<logic:equal name="userbean" property="bySrc3" value="true">
							<div class="row">
								<span class="label30">Referral Source 3:</span> 
								<span class="field35">
									<html:select property="nmRefSource3">
										<html:option value=""/>
										<html:options collection="referralSources" labelProperty="nmStaff" property="idStaff"/>
									</html:select>
								</span>
							</div>
						</logic:equal>
						<logic:equal name="userbean" property="bySrc4" value="true">
							<div class="row">
								<span class="label30">Referral Source 4:</span> 
								<span class="field35">
									<html:select property="nmRefSource4">
										<html:option value=""/>
										<html:options collection="referralSources" labelProperty="nmStaff" property="idStaff"/>
									</html:select>
								</span>
							</div>
						</logic:equal>
						<div class="row">
							<span class="label30">Completed:</span>
							<span class="field1"><html:checkbox property="chkCompleted" /></span>
						</div>
						<div class="row">
							<span class="label30">Outstanding:</span>
							<span class="field1"><html:checkbox property="chkOutstanding" value="true" /></span>
						</div>
						<div class="row">
							<span class="label30">Pending Approval:</span>
							<span class="field1"><html:checkbox property="chkApproval" /></span>
						</div>
						<div class="spacer">&nbsp;</div>

						<%-- Add the Buttons --%>
						<div class="row">
							<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
						</div>
						
						<div class="row">
							<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
							<span class="button"><html:button property="Reset" onclick="reset_all(form)">Reset</html:button></span>
							<span class="button"><input type="button" name="Preferences" value="Preferences" onclick="window.location.href='UserPreferencesAction.do?reqCode=edit&idWorker=<%=userBean.getIdWorker()%>';"></span>
							<span class="button"><html:button property="AddNewTask" onclick="javascript:validateWorkload()">Create New Task</html:button></span>
							<span class="button"><html:submit><bean:message key="button.search"/></html:submit></span>
						</div>
						<div class="spacer">&nbsp;</div>
						<div class="spacer">&nbsp;</div>
						
					</fieldset>
				</html:form>
			</DIV>
			
				
			<logic:present scope="request" name="tasklist">
			<p>Based on your search criteria, here is a list of tasks selected.</p>
			<DIV id="form">
				<fieldset><legend>eCSTS Task List</legend>
					<layout:collection name="tasklist" id="task" styleClass="FORM" styleClass2="FORM2" sortAction="client" title="" >
						<layout:collectionItem title="Reference ID" property="idReference" sortable="true"/>
						<layout:collectionItem title="Status" property="cdStatus" sortable="true"/>
						<layout:collectionItem title="Agent Assigned" property="nmWorker" sortable="true"/>
						<layout:collectionItem title="IV-D Case #" property="nbCase"/>
						<layout:collectionItem title="Customer Last Name" property="nmCustomerLast"/>
						<layout:collectionItem title="Customer First Name" property="nmCustomerFirst"/>
						<layout:collectionItem title="Referral Type" property="cdType"/>
						<logic:equal name="userbean" property="manageAll" value="true">
							<layout:collectionItem title="Manage" property="" url="ManageTaskAction.do?reqCode=edit&idReference=" param="idReference"><img src="images/edit.gif" border="0"></layout:collectionItem>
						</logic:equal>							
						<logic:notEqual name="userbean" property="manageAll" value="true">
							<logic:equal name="userbean" property="manageUsers" value="true">
								<layout:collectionItem title="Manage" property="" url="ManageTaskAction.do?reqCode=edit&idReference=" param="idReference"><img src="images/edit.gif" border="0"></layout:collectionItem>
							</logic:equal>
							<logic:notEqual name="userbean" property="manageUsers" value="true">
							<!--Add logic here to test if this task belongs to the agent  -->
								<layout:collectionItem title="Manage">
									<logic:equal name="task" property="owner" value="true">
										<html:link href="ManageTaskAction.do?reqCode=edit" paramId="idReference" paramName="task" paramProperty="idReference"><img src="images/edit.gif" border="0"></html:link>
									</logic:equal>
								</layout:collectionItem>
							</logic:notEqual>							
						</logic:notEqual>	
						<layout:collectionItem title="View" property="" url="ManageTaskAction.do?reqCode=view&idReference=" param="idReference"><img src="images/view.gif" border="0"></layout:collectionItem>
					</layout:collection>
				</fieldset>
			</DIV>
			</logic:present>				
			
		</DIV>
		<html:javascript formName="taskSearchForm"/>
	
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>
