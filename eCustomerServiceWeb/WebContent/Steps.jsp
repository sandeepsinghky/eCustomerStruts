<%@page import="org.dhhs.dirm.acts.cs.beans.Step,java.util.Vector" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
	request.setAttribute("page","steps");
%>		

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="eCSTS Process Steps" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >
	
		<%-- Content for the body section belongs here --%>
		
		<DIV id="pagetitle">
			<h1>eCustomer Service Process Types</h1>
		</DIV>
		<DIV id="pagebody">
			<p>Here is a list of valid process types applicable for this application.</p>

		<DIV id="errorheader"><logic:messagesPresent>
			<UL>
				<html:messages id="error">
					<LI><bean:write name="error" /></LI>
				</html:messages>
			</UL>
		</logic:messagesPresent></DIV>
			
			<logic:present scope="request" name="stepslist">
			<DIV id="form">
				<fieldset><legend>eCSTS Forms List</legend>
					<layout:collection name="stepslist" styleClass="FORM" styleClass2="FORM2" sortAction="client" title="" >
						<layout:collectionItem title="Process Type" property="step" sortable="true"/>
						<layout:collectionItem title="Description"  property="description" sortable="true"/>
						<layout:collectionItem title="Created By" width="50" property="wrkrCreate"/>
						<layout:collectionItem title="Created On" width="60"  property="tsCreate"/>
						<layout:collectionItem title="Updated By" width="50" property="wrkrUpdate"/>
						<layout:collectionItem title="Updated On" width="60"  property="tsUpdate"/>
						<layout:collectionItem title="Modify" property="" url="ManageFormsAction.do?reqCode=edit&type=" param="step"><img src="images/edit.gif" border="0"></layout:collectionItem>
						<layout:collectionItem title="Details" property="" url="ManageFormsAction.do?reqCode=view&type=" param="step"><img src="images/view.gif" border="0"></layout:collectionItem>
					</layout:collection>
					<%-- Add the Buttons --%>
					<div class="row">
						<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
					</div>
						
					<div class="row">
						<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
						<span class="button"><html:submit>New Process Type</html:submit></span>
					</div>
					<div class="spacer">&nbsp;</div>
					<div class="spacer">&nbsp;</div>
					
				</fieldset>
			</DIV>
			</logic:present>				
			
		</DIV>
		
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>
