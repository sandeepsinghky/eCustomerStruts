<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%
	request.setAttribute("page","welcome");
%>		

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Welcome to Customer Service Tracking System" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="message" value="message.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >
	
		<%-- Content for the body section belongs here --%>
		<DIV id="pagetitle">
			<h1>eCustomer Service Welcome Page</h1>
		</DIV>
		<DIV id="pagebody">
			<p>
				Welcome to North Carolina Child Support Enforcement Customer Service Tracking System. 
				Below is a snapshot of your current workload.
				<p></p>
				<DIV id="form">
				<logic:equal name="userbean" property="manageAll" value="true">
					<logic:iterate id="workerStat" name="workerStats" type="org.dhhs.dirm.acts.cs.beans.WorkerStatsBean">
						<fieldset><legend>Current Workload for <bean:write name="workerStat" property="idWorker"/></legend>
							<div class="row">
								 <span class="label"><bean:message key="message.outstanding"/></span>
								<span class="field"><bean:write name="workerStat" property="nbOutstanding"/></span>
							</div>
							<div class="row">
								<span class="label"><bean:message key="message.completed"/></span>
								<span class="field"><bean:write name="workerStat" property="nbCompleted"/></span>
							</div>
							<div class="row">
								<span class="label"><bean:message key="message.approval"/></span>
								<span class="field"><bean:write name="workerStat" property="nbApproval"/></span>
							</div>
							<div class="spacer">&nbsp;</div>							
							<div class="row">
								<span class="buttonborderleft"><html:img page="/images/line.gif" width="200px" border="0" /></span>
							</div>
							<div class="row">
								<span class="label"><bean:message key="message.all"/></span> 
								<span class="field"><bean:write name="workerStat" property="nbAll"/></span>
							</div>
						</fieldset>
					</logic:iterate>
				</logic:equal>
				<logic:notEqual name="userbean" property="manageAll" value="true">
				<fieldset><legend>Current Workload </legend>
					<div class="row">
						 <span class="label"><bean:message key="message.outstanding"/></span>
						 <span class="field"><bean:write name="outstandingCount" /></span>
					</div>
					<div class="row">
						<span class="label"><bean:message key="message.completed"/></span>
						<span class="field"><bean:write name="completedCount" /></span>
					</div>
					<div class="row">
						<span class="label"><bean:message key="message.approval"/></span>
						<span class="field"><bean:write name="approvalCount" /></span>
					</div>
					<div class="row">
						<span class="label"><bean:message key="message.all"/></span> 
						<span class="field"><bean:write name="allCount"/></span>
					</div>
				</fieldset>
				</logic:notEqual>
				</DIV>
			</p>
		</DIV>
		
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>
