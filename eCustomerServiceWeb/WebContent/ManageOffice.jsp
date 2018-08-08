<%@page import="org.dhhs.dirm.acts.cs.formbeans.*,org.dhhs.dirm.acts.cs.beans.*,java.util.Vector"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
//	request.setAttribute("page","manageOffice");
	
	ReferralOffice ro = (ReferralOffice)request.getAttribute("officeForm");
	request.setAttribute("sources",ro.getSources());
	String key = ro.getNbSeq();
%>

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Manage Office" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >

	
		<%-- Content for the body section belongs here --%>
		
		<logic:equal name="formMode" value="0">	
			<DIV id="pagetitle">
				<h1>Create New eCSTS Referral Office</h1>
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
				<html:form action="/ManageOfficeAction?reqCode=store" focus="nmOffice" onsubmit="return validateReferralOffice(this)">
					<fieldset><legend>eCSTS Referral Office Details</legend>
						<div class="row">
							<span class="label30">Office:</span> 
							<span class="field8"><html:text property="nmOffice" maxlength="6"/></span>
						</div>
						<div class="row">
							<span class="label30">Description:</span> 
							<span class="field50"><html:text property="nmOfficeDesc" maxlength="50"/></span>
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
				<h1>Edit eCSTS Referral Office</h1>
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
				<html:form action="/ManageOfficeAction?reqCode=save" focus="nmOffice" onsubmit="return validateReferralOffice(this)">
					<fieldset><legend>Modify eCSTS Referral Office</legend>
						<div class="row">
							<span class="label30">Office:</span> 
							<span class="field8r"><html:text property="nmOffice" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Description:</span> 
							<span class="field50"><html:text property="nmOfficeDesc" maxlength="50"/></span>
						</div>
						<html:hidden property="nbSeq" />						
						<div class="spacer">&nbsp;</div>

						<layout:collection name="sources" styleClass="FORM" styleClass2="FORM2" sortAction="client" title="" >
							<layout:collectionItem title="Staff"  property="nmStaff" sortable="true"/>
							<layout:collectionItem title="Title"  property="title"/>
							<layout:collectionItem title="Created By" width="50" property="idWrkrCreate"/>
							<layout:collectionItem title="Created On" width="60"  property="tsCreate"/>
							<layout:collectionItem title="Updated By" width="50" property="idWrkrUpdate"/>
							<layout:collectionItem title="Updated On" width="60"  property="tsUpdate"/>
							<layout:collectionItem title="Inactivate" property="" url="ManageReferralSourceAction.do?reqCode=inactivate" paramId="idStaff" paramProperty="idStaff"><img src="images/view.gif" border="0"></layout:collectionItem>
							<layout:collectionItem title="Modify" property="" url="ManageReferralSourceAction.do?reqCode=edit" paramId="idStaff" paramProperty="idStaff"><img src="images/edit.gif" border="0"></layout:collectionItem>
							<layout:collectionItem title="Details" property="" url="ManageReferralSourceAction.do?reqCode=view" paramId="idStaff" paramProperty="idStaff"><img src="images/view.gif" border="0"></layout:collectionItem>
						</layout:collection>
						
						<div class="spacer">&nbsp;</div>
					
						<%-- Add the Buttons --%>
						<div class="row">
							<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
						</div>
						
						<div class="row">
							<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
							<span class="button"><html:submit>Save</html:submit></span>
							<span class="button"><input type="button" name="Add" value="Add Referral Source" onclick="window.location.href='ManageReferralSourceAction.do?reqCode=create&key=<%=key%>';"></span>
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
				<h1>View eCSTS Referral Office</h1>
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
				<html:form action="/ManageOfficeAction?reqCode=edit">
					<fieldset><legend>View eCSTS Referral Office</legend>
						<div class="row">
							<span class="label30">Office:</span> 
							<span class="field4r"><html:text property="nmOffice" readonly="true"/></span>
						</div>
						<div class="row">
							<span class="label30">Description:</span> 
							<span class="field30r"><html:text property="nmOfficeDesc" readonly="true"/></span>
						</div>
						<html:hidden property="nbSeq" />						
						
						<div class="spacer">&nbsp;</div>
						<layout:collection name="sources" styleClass="FORM" styleClass2="FORM2" sortAction="client" title="" >
							<layout:collectionItem title="Staff"  property="nmStaff" sortable="true"/>
							<layout:collectionItem title="Title"  property="title"/>
							<layout:collectionItem title="Created By" width="50" property="idWrkrCreate"/>
							<layout:collectionItem title="Created On" width="60"  property="tsCreate"/>
							<layout:collectionItem title="Updated By" width="50" property="idWrkrUpdate"/>
							<layout:collectionItem title="Updated On" width="60"  property="tsUpdate"/>
							<layout:collectionItem title="Details" property="" url="ManageReferralSourceAction.do?reqCode=view" paramId="idStaff" paramProperty="idStaff"><img src="images/view.gif" border="0"></layout:collectionItem>
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
		<html:javascript formName="referralOffice"/>		
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>