<%@page import="org.dhhs.dirm.acts.cs.struts.*,org.dhhs.dirm.acts.cs.formbeans.*,org.dhhs.dirm.acts.cs.beans.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<jsp:useBean id="forms" class="java.util.Vector" scope="application"/>

<%
	request.setAttribute("page","approvalSearch");
%>		

<script LANGUAGE="JavaScript">

	function reset_all(form) {
		form.idReference.value = "";
		form.idWorker.value = "";
		form.cdType.value = "";
	}
	
	function select_all() {
		
		var allInputs = document.getElementById("approvalSearchForm").getElementsByTagName("input");
		for (var i = 0; i < allInputs.length; i++) {
		
			if (allInputs[i].type == "checkbox") {
				allInputs[i].checked = true;
			}
		}
	}
	
	function clear_all() {
		
		var allInputs = document.getElementById("approvalSearchForm").getElementsByTagName("input");
		for (var i = 0; i < allInputs.length; i++) {
		
			if (allInputs[i].type == "checkbox") {
				allInputs[i].checked = false;
			}
		}
	}
	
	function approve_all(form) {
		form.button.value = "approveAll";
		return true;
	}
</script>

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Approvals Search" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >
	
		<%-- Content for the body section belongs here --%>
		
		<DIV id="pagetitle">
			<h1>eCustomer Service Approvals Search</h1>
		</DIV>
		<DIV id="pagebody">
			<p>Complete required fields and click the search button. You can search on a Reference ID if you have one for direct access. However, if you do not have one
			, you can increase the accuracy of the searches by supplying other relevant fields that fine-tune your search. 
			</p>

		<DIV id="errorheader"><logic:messagesPresent>
			<UL>
				<html:messages id="error">
					<LI><bean:write name="error" /></LI>
				</html:messages>
			</UL>
		</logic:messagesPresent></DIV>
			
			<DIV id="form">
				<html:form action="/ApprovalSearchAction?reqCode=search" focus="idReference" styleClass="form100">
					<fieldset><legend>eCSTS Approval Search</legend>
						<div class="row">
							<span class="label30">Reference ID:</span>
							<span class="field8"><html:text property="idReference" maxlength="10" /> Enter the specific reference id or any combination of fields below</span>
						</div>
						<div class="row">
							<span class="label30">Agent Assigned:</span> 
							<span class="field35">
								<html:select property="idWorker">
									<html:option value=""/>
									<html:options collection="agents" labelProperty="nmWorker" property="idWorker"/>
								</html:select>
							</span>
						</div>
						<div class="row">
							<span class="label30">Referral Type:</span>
							<span class="field4">
								<html:select property="cdType">
									<html:option value=""/>
									<html:options collection="forms" property="type"/>
								</html:select>
							</span>
						</div>
						<html:hidden property="button"/>
						
						<div class="spacer">&nbsp;</div>

						<%-- Add the Buttons --%>
						<div class="row">
							<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
						</div>
						<div class="row">
							<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
							<span class="button"><html:button property="Reset" onclick="reset_all(form)">Reset</html:button></span>
							<span class="button"><html:submit><bean:message key="button.search"/></html:submit></span>
						</div>
						<div class="spacer">&nbsp;</div>
						<div class="spacer">&nbsp;</div>
					</fieldset>


					<logic:present name="approvalSearchForm" property="approvalList">
					<p>Based on your search criteria, here is a list of tasks selected for approval.</p>
					<DIV id="form">
						<fieldset><legend>eCSTS Task Approval List</legend>
							<layout:collection property="approvalList" styleClass="FORM" styleClass2="FORM2"  selectType="checkbox" selectName="selection" selectProperty="idReference" sortAction="client" title="" >
								<layout:collectionItem title="Select" property="filler"/>
								<layout:collectionItem title="Reference ID" property="idReference" sortable="true"/>
								<layout:collectionItem title="Status" property="cdStatus" sortable="true"/>
								<layout:collectionItem title="Agent Assigned" property="nmWorker" sortable="true"/>
								<layout:collectionItem title="IV-D Case #" property="nbCase"/>
								<layout:collectionItem title="Customer Last Name" property="nmCustomerLast"/>
								<layout:collectionItem title="Customer First Name" property="nmCustomerFirst"/>
								<layout:collectionItem title="Referral Type" property="cdType"/>
								<layout:collectionItem title="Manage" property="" url="ManageApprovalAction.do?reqCode=manage&idReference=" param="idReference"><img src="images/edit.gif" border="0"></layout:collectionItem>
							</layout:collection>
							<div class="row">
								<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
							</div>
							<div class="row">
								<span class="button"><html:button property="Select All" onclick="select_all();">Select All</html:button></span>
								<span class="button"><html:button property="Clear" onclick="clear_all();">Clear</html:button></span>
								<span class="button"><html:button property="Approve" onclick="approve_all(form);form.submit();">Approve</html:button></span>
							</div>
						</fieldset>
					</DIV>
					</logic:present>
			</html:form>
			</DIV>
		</DIV>
		
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>
