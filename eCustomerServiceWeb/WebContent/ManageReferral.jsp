<%@page import="org.dhhs.dirm.acts.cs.formbeans.*,org.dhhs.dirm.acts.cs.beans.*,java.util.Vector"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
//	request.setAttribute("page","manageReferral");
	String type = "";
%>

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Manage Referral Type" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >

	
		<%-- Content for the body section belongs here --%>
		
		<logic:present scope="request" name="referralTypeForm">
			<%
				ReferralTypeForm ftf = (ReferralTypeForm)request.getAttribute("referralTypeForm");
				if (ftf != null) {
					Vector v = ftf.getFormSteps();
				}
//				request.setAttribute("formSteps",v);
				
				type = ftf.getType();
			%>
		</logic:present>
		
		<logic:equal name="formMode" value="0">	
			<DIV id="pagetitle">
				<h1>Create New eCSTS Referral Type</h1>
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
				<html:form action="/ManageReferralAction?reqCode=store" focus="type" styleClass="form60"onsubmit="return validateReferralTypeForm(this)">
					<fieldset><legend>eCSTS Referral Type Details</legend>
						<div class="row">
							<span class="label30">Referral Type:</span> 
							<span class="field4"><html:text property="type" maxlength="4"/></span>
						</div>
						<div class="row">
							<span class="label30">Referral Description:</span> 
							<span class="field30"><html:text property="description" maxlength="30"/></span>
						</div>
						<div class="row">
							<span class="label30">Create Correspondence?:</span> 
							<span class="field1"><html:checkbox property="generateCorrespondence"/></span>
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
				<h1>Edit eCSTS Referral Type</h1>
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
				<html:form action="/ManageReferralAction?reqCode=save" focus="type"onsubmit="return validateReferralTypeForm(this)">
					<fieldset><legend>Modify eCSTS Referral Type</legend>
						<div class="row">
							<span class="label30">Referral Type:</span> 
							<span class="field4r"><html:text property="type" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Referral Description:</span> 
							<span class="field30"><html:text property="description" maxlength="30"/></span>
						</div>
						<div class="row">
							<span class="label30">Create Correspondence?:</span> 
							<span class="field1"><html:checkbox property="generateCorrespondence"/></span>
						</div>
						
						<div class="spacer">&nbsp;</div>

						<layout:collection name="referralTypeForm" property="formSteps" styleClass="FORM" styleClass2="FORM2" sortAction="client" title="" >
							<layout:collectionItem title="Process Type"  property="step" sortable="true"/>
							<layout:collectionItem title="Sequence #"  property="seq" sortable="true"/>
							<layout:collectionItem title="Duration"  property="duration"/>
							<layout:collectionItem title="Created By" width="50" property="wrkrCreate"/>
							<layout:collectionItem title="Created On" width="60"  property="tsCreate"/>
							<layout:collectionItem title="Updated By" width="50" property="wrkrUpdate"/>
							<layout:collectionItem title="Updated On" width="60"  property="tsUpdate"/>
							<layout:collectionItem title="Modify" property="" url="ManageReferralProcessAction.do?reqCode=edit" paramId="type,step" paramProperty="type,step"><img src="images/edit.gif" border="0"></layout:collectionItem>
							<layout:collectionItem title="Details" property="" url="ManageReferralProcessAction.do?reqCode=view" paramId="type,step" paramProperty="type,step"><img src="images/view.gif" border="0"></layout:collectionItem>
						</layout:collection>
						
						<div class="spacer">&nbsp;</div>
					
						<%-- Add the Buttons --%>
						<div class="row">
							<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
						</div>
						
						<div class="row">
							<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
							<span class="button"><html:submit>Save</html:submit></span>
							<span class="button"><input type="button" name="Add" value="Add New Process" onclick="window.location.href='ManageReferralProcessAction.do?reqCode=create&type=<%=type%>';"></span>
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
				<h1>View eCSTS Referral Type</h1>
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
				<html:form action="/ManageReferralAction?reqCode=edit">
					<fieldset><legend>View eCSTS Referral Type</legend>
						<div class="row">
							<span class="label30">Referral Type:</span> 
							<span class="field4r"><html:text property="type" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Referral Description:</span> 
							<span class="field30r"><html:text property="description" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Create Correspondence?:</span> 
							<span class="field1r"><html:checkbox property="generateCorrespondence" disabled="true"/></span>
						</div>
						<div class="spacer">&nbsp;</div>
						
						<layout:collection name="referralTypeForm" property="formSteps" styleClass="FORM" styleClass2="FORM2" sortAction="client" title="" >
							<layout:collectionItem title="Process Type"  property="step" sortable="true"/>
							<layout:collectionItem title="Sequence #"  property="seq" sortable="true"/>
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
		<html:javascript formName="referralTypeForm"/>
				
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>