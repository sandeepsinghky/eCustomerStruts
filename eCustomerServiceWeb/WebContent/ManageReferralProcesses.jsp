<%@page import="org.dhhs.dirm.acts.cs.formbeans.*,org.dhhs.dirm.acts.cs.beans.*,java.util.Vector"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
//	request.setAttribute("page","manageReferralProcesses");
%>

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Manage Referral Processes" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >

	
		<%-- Content for the body section belongs here --%>
		
		<logic:equal name="formMode" value="0">	
			<DIV id="pagetitle">
				<h1>Create New eCSTS Referral Process</h1>
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
				<html:form action="/ManageReferralProcessAction?reqCode=add" focus="step" styleClass="form60"onsubmit="return validateReferralProcess(this)">
					<fieldset><legend>eCSTS Referral Process Details</legend>
						<div class="row">
							<span class="label30">Referral Type:</span>
							<span class="field4r"><html:text property="type" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Process Type:</span>
							<span class="field4">
								<html:select property="step">
									<html:options collection="steps" property="step"/>
								</html:select>
							</span>
						</div>
						<html:hidden property="seq"/>
						<div class="row">
							<span class="label30">Duration:</span> 
							<span class="field2"><html:text property="duration" maxlength="2"/></span>
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
				<DIV id="form">
					<fieldset><legend>eCSTS Referral Processes List</legend>
					<layout:collection align="left" name="formSteps" styleClass="FORM" styleClass2="FORM2" sortAction="client" title="" >
						<layout:collectionItem title="Process Type"  property="step" sortable="true"/>
						<layout:collectionItem title="Duration"  property="duration"/>
						<layout:collectionItem title="Created By" width="50" property="wrkrCreate"/>
						<layout:collectionItem title="Created On" width="60"  property="tsCreate"/>
						<layout:collectionItem title="Updated By" width="50" property="wrkrUpdate"/>
						<layout:collectionItem title="Updated On" width="60"  property="tsUpdate"/>
					</layout:collection>
				</DIV>
			</DIV>
		</logic:equal>

		<logic:equal name="formMode" value="1">
			<DIV id="pagetitle">
				<h1>Edit eCSTS Referral Process</h1>
			</DIV>
			<DIV id="pagebody">
				<p>Complete all fields on the page and click Save.</p>
				
			<DIV id="errorheader"><logic:messagesPresent>
				<UL>
					<html:messages id="error">
						<LI><bean:write name="error" /></LI>
					</html:messages>
				</UL>
			</logic:messagesPresent></DIV>
				
				<DIV id="form">
				<html:form action="/ManageReferralProcessAction?reqCode=save" focus="step"onsubmit="return validateReferralProcess(this)">
					<fieldset><legend>Modify eCSTS Referral Processes</legend>
						<div class="row">
							<span class="label30">Referral Type:</span> 
							<span class="field4r"><html:text property="type" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Process Type:</span> 
							<span class="field4r"><html:text property="step" readonly="true"/></span>
						</div>
						<html:hidden property="seq"/>
						<div class="row">
							<span class="label30">Duration:</span> 
							<span class="field2"><html:text property="duration" maxlength="2"/></span>
						</div>
						
						<div class="spacer">&nbsp;</div>
						
						<div class="row">
							<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
						</div>
						
						<div class="row">
							<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
							<span class="button"><html:button property="Add" onclick="window.location.href='ReferralSearchAction.do';">Cancel</html:button></span>
							<span class="button"><html:submit>Save</html:submit></span>
						</div>
						
						<div class="spacer">&nbsp;</div>
						<div class="spacer">&nbsp;</div>
					</fieldset>
				</html:form>
				</DIV>
						
				<DIV id="form">
					<fieldset><legend>eCSTS Referral Processes List</legend>
						<layout:collection name="formSteps" styleClass="FORM" styleClass2="FORM2" sortAction="client" title="" >
							<layout:collectionItem title="Process Type"  property="step" sortable="true"/>
							<layout:collectionItem title="Duration"  property="duration"/>
							<layout:collectionItem title="Created By" width="50" property="wrkrCreate"/>
							<layout:collectionItem title="Created On" width="60"  property="tsCreate"/>
							<layout:collectionItem title="Updated By" width="50" property="wrkrUpdate"/>
							<layout:collectionItem title="Updated On" width="60"  property="tsUpdate"/>
						</layout:collection>
						<div class="spacer">&nbsp;</div>
					</fieldset>
								<%-- 	</html:form> --%>
				</DIV>
				
			</DIV>
		</logic:equal>

		<logic:equal name="formMode" value="2">
			<DIV id="pagetitle">
				<h1>View eCSTS Referral Process</h1>
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
				<html:form action="/ManageReferralProcessAction?reqCode=edit">
					<fieldset><legend>View eCSTS Referral Processes</legend>
						<div class="row">
							<span class="label30">Referral Type:</span> 
							<span class="field4r"><html:text property="type" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Process Type:</span> 
							<span class="field4r"><html:text property="step" readonly="true"/></span>
						</div>
						<html:hidden property="seq"/>
						<div class="row">
							<span class="label30">Duration:</span> 
							<span class="field2r"><html:text property="duration" readonly="true"/></span>
						</div>
						
						<div class="spacer">&nbsp;</div>
						
						<layout:collection name="formSteps" styleClass="FORM" styleClass2="FORM2" sortAction="client" title="" >
							<layout:collectionItem title="Process Type"  property="step" sortable="true"/>
							<layout:collectionItem title="Duration"  property="duration"/>
							<layout:collectionItem title="Created By" width="50" property="wrkrCreate"/>
							<layout:collectionItem title="Created On" width="60"  property="tsCreate"/>
							<layout:collectionItem title="Updated By" width="50" property="wrkrUpdate"/>
							<layout:collectionItem title="Updated On" width="60"  property="tsUpdate"/>
						</layout:collection>
						
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
		<html:javascript formName="referralProcess"/>		
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>