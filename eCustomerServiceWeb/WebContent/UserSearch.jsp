<%@page import="org.dhhs.dirm.acts.cs.formbeans.*,org.dhhs.dirm.acts.cs.beans.*,java.util.Vector" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<jsp:useBean id="profiles" class="java.util.Vector" scope="application"/>
<%
	request.setAttribute("page","userSearch");
%>		

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Search ACTS for Worker" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >

<script>

	function customValidateForm(form) {
		var rtn = validateUserSearchForm(form);
		
		if (rtn == true) {
			if (form.workerid.value.length > 0);
			else if (form.profileid.value.length > 0);
			else if (form.lastname.value.length > 0) ;
			else if (form.firstname.value.length > 0) {
					alert("Last name is required for name search");
					return false;
				}	
			return true;		
		} else {
			return rtn;
		}
	}
	
	function reset_all(form) {
		var count = 0;
		var allInputs = document.getElementById("userSearchForm").getElementsByTagName("input");
		for (var i = 0; i < allInputs.length; i++) {
			if (allInputs[i].type == "text") {
				allInputs[i].value = "";
			}
		}
		var allSelects = document.getElementById("userSearchForm").getElementsByTagName("select");
		for (var i = 0; i < allSelects.length; i++) {
			allSelects[i].value = "";
		}
	}
	
</script>
	
		<%-- Content for the body section belongs here --%>
		
		<DIV id="pagetitle">
			<h1>eCustomer Service Agent Search</h1>
		</DIV>
		<DIV id="pagebody">
			<p>Please enter either the ACTS worker id or select a profile or simply search by Last Name and First Name and click the search button.</p>

		<DIV id="errorheader"><logic:messagesPresent>
			<UL>
				<html:messages id="error">
					<LI><bean:write name="error" /></LI>
				</html:messages>
			</UL>
		</logic:messagesPresent></DIV>
			
			<DIV id="form">
				<html:form action="/UserSearchAction" focus="workerid" onsubmit="return customValidateForm(this)">
					<fieldset><legend>eCSTS Agent Search</legend>
						<div class="row">
							<span class="label30">ACTS Worker ID:</span> 
							<span class="field8"><html:text property="workerid" maxlength="8" /> OR</span>
						</div>
						<div class="row">
							<span class="label30">Profile ID:</span> 
							<span class="field4">
								<html:select property="profileid">
									<html:option value=""/>
									<html:options collection="profiles" property="idProfile"/>
								</html:select> OR
							</span>
						</div>
						<div class="row">
							<span class="label30">Last Name:</span> 
							<span class="field17"><html:text property="lastname" maxlength="17" /> AND</span>
						</div>
						<div class="row">
							<span class="label30">First Name:</span> 
							<span class="field15"><html:text property="firstname" maxlength="15" /></span>
						</div>
						<div class="spacer">&nbsp;</div>

						<%-- Add the Buttons --%>
						<div class="row">
							<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
						</div>
						
						<div class="row">
							<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
							<span class="button"><html:button property="AddNewAgent" onclick="window.location.href='ActsWorkerSearch.do';"><bean:message key="button.addNewUser"/></html:button></span>
							<span class="button"><html:button property="Reset" onclick="reset_all(form)">Reset</html:button></span>
							<span class="button"><html:submit><bean:message key="button.search"/></html:submit></span>
						</div>
						<div class="spacer">&nbsp;</div>
						<div class="spacer">&nbsp;</div>
						
					</fieldset>
				</html:form>
			</DIV>
			
				
			<logic:present scope="request" name="actsworkerlist">
			<p>Based on your search criteria, here is a list of agents selected.</p>
			<DIV id="form">
			<html:form action="/UserSearchAction">
				<fieldset><legend>eCSTS Agent List</legend>
					<layout:collection name="actsworkerlist" styleClass="FORM" styleClass2="FORM2" sortAction="client" title="" >
						<layout:collectionItem title="ACTS Worker ID" property="idWorker" sortable="true"/>
						<layout:collectionItem title="Name"  property="nmWorker" sortable="true"/>
						<layout:collectionItem title="Profile" width="50" property="idProfile"/>
						<layout:collectionItem title="Accept Workload ?" width="60"  property="cdAccptWrkld"/>
						<layout:collectionItem title="Outstanding Tasks"  width="100" property="nbrOutstanding"/>
						<layout:collectionItem title="Pending Approval"  width="100" property="nbrApproval"/>
						<layout:collectionItem title="Completed Tasks"  width="100" property="nbrCompleted"/>
						<layout:collectionItem title="Modify" property="" url="ManageUserAction.do?reqCode=edit&idWorker=" param="idWorker"><img src="images/edit.gif" border="0"></layout:collectionItem>
						<layout:collectionItem title="Details" property="" url="ManageUserAction.do?reqCode=view&idWorker=" param="idWorker"><img src="images/view.gif" border="0"></layout:collectionItem>
						<layout:collectionItem title="Remove Agent" property="" url="ManageWorkloadAction.do?reqCode=loadAll&idWorker=" param="idWorker"><bean:message key="button.removeUser"/></layout:collectionItem>
					</layout:collection>
				</fieldset>
			</html:form>
			</DIV>
			</logic:present>				
			
		</DIV>
		<html:javascript formName="userSearchForm"/>
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>
