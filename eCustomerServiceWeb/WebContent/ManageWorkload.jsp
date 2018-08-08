<%@page import="org.dhhs.dirm.acts.cs.formbeans.*,org.dhhs.dirm.acts.cs.beans.*,java.util.Vector" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<%
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

	function customValidateForm(form) {
	
		var rtn = true;
		var msg = "";
		var selection = form.workerid.options[form.workerid.selectedIndex].value;
		if (selection == "") {
			msg = "Source agent must be selected\n";
			rtn = false;
		}
		var count = 0;
		var allInputs = document.getElementById("workloadForm").getElementsByTagName("input");
		for (var i = 0; i < allInputs.length; i++) {
			if (allInputs[i].type == "checkbox") {
				if (allInputs[i].checked == true) {
					count = count + 1;
				}
			}
		}
		
		if (count <= 0) {
			msg = msg + "At least one target agent must be selected";
			rtn = false;
		}
		
		if (rtn == false) {
			alert(msg);
		}
		return rtn;
	}	
</script>
	
		<%-- Content for the body section belongs here --%>
		
		<DIV id="pagetitle">
			<h1>eCustomer Service Workload Management</h1>
		</DIV>
		<DIV id="pagebody">
			<p>Please select a source agent and one or more target agents to transfer workload from the source agent to target agent(s).</p>

		<DIV id="errorheader"><logic:messagesPresent>
			<UL>
				<html:messages id="error">
					<LI><bean:write name="error" /></LI>
				</html:messages>
			</UL>
		</logic:messagesPresent></DIV>
			
			<DIV id="form">
				<html:form action="/ManageWorkloadAction.do?reqCode=load" onsubmit="return customValidateForm(this)">
					<div class="row">
						<span class="label30">Source Agent:</span> 
						<span class="field10">
							<html:select property="workerid">
								<html:optionsCollection property="targetUsers" value="idWorker" label="idWorker"/>
							</html:select>
						</span>
					</div>
				
					<layout:collection property="targetUsers" styleClass="FORM" styleClass2="FORM2" selectType="checkbox" selectName="selectedWorkers" selectProperty="idWorker" sortAction="client" title="" >
						<layout:collectionItem title="Target Agent" property="idWorker" sortable="true"/>
						<layout:collectionItem title="Name"  property="nmWorker" sortable="true"/>
						<layout:collectionItem title="Profile" width="50" property="idProfile"/>
						<layout:collectionItem title="Accept Workload ?" width="60"  property="cdAccptWrkld"/>
						<layout:collectionItem title="Outstanding Tasks"  width="100" property="nbrOutstanding" sortable="true"/>
						<layout:collectionItem title="Pending Approval"  width="100" property="nbrApproval" sortable="true"/>
						<layout:collectionItem title="Completed Tasks"  width="100" property="nbrCompleted" sortable="true"/>
					</layout:collection>
					<%-- Add the Buttons --%>
					<div class="row">
						<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
					</div>
					
					<div class="row">
						<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
						<span class="button"><html:reset><bean:message key="button.reset"/></html:reset></span>
						<span class="button"><html:submit>Next</html:submit></span>
					</div>
					<div class="spacer">&nbsp;</div>
					<div class="spacer">&nbsp;</div>
				</html:form>
			</DIV>
		</DIV>
		
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>
