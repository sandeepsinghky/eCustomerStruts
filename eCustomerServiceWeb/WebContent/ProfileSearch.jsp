<%@page import="org.dhhs.dirm.acts.cs.formbeans.*,org.dhhs.dirm.acts.cs.beans.*,fr.improve.struts.taglib.layout.util.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<jsp:useBean id="profiles" class="java.util.Vector" scope="application"/>
<%
	request.setAttribute("page","profileSearch");
%>		


<script LANGUAGE="JavaScript">

	function reset_all(form) {
		form.idProfile.value = "";
	}
</script>	


<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Manage Profiles for Customer Service System" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >

	
		<%-- Content for the body section belongs here --%>
		
		<DIV id="pagetitle">
			<h1>Manage Profiles</h1>
		</DIV>
		<DIV id="pagebody">
			<p>Please enter either the profile id or simply click the search button to view list of available profiles.</p>

		<DIV id="errorheader"><logic:messagesPresent>
			<UL>
				<html:messages id="error">
					<LI><bean:write name="error" /></LI>
				</html:messages>
			</UL>
		</logic:messagesPresent></DIV>
			
			<DIV id="form">
				<html:form action="/ProfileSearchAction" focus="idProfile">
					<fieldset><legend>eCSTS Profile Search</legend>
						<div class="row">
							<span class="label30">Profile ID:</span> 
							<span class="field4">
								<html:select property="idProfile">
									<html:option value=""/>
									<html:options collection="profiles" property="idProfile"/>
								</html:select>
							</span>
						</div>
						<div class="spacer">&nbsp;</div>

						<%-- Add the Buttons --%>
						<div class="row">
							<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
						</div>
						
						<div class="row">
							<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
							<span class="button"><html:button property="AddNewProfile" onclick="window.location.href='ManageProfileAction.do?reqCode=create';"><bean:message key="button.addNewProfile"/></html:button></span>
							<span class="button"><html:button property="Reset" onclick="reset_all(form)">Reset</html:button></span>
							<span class="button"><html:submit><bean:message key="button.search"/></html:submit></span>
						</div>
						<div class="spacer">&nbsp;</div>
						<div class="spacer">&nbsp;</div>
					</fieldset>
				</html:form>
			</DIV>
				
			<logic:present scope="request" name="profilelist">
			<p>Based on your search criteria, here is a list of profiles selected.</p>
			<DIV id="form">
			<html:form action="/UserSearchAction">
				<fieldset><legend>eCSTS Profile List</legend>
					<layout:collection name="profilelist" styleClass="FORM" styleClass2="FORM" sortAction="client" title="" >
						<layout:collectionItem title="Profile ID" property="idProfile" sortable="true"/>
						<layout:collectionItem title="Profile Desc" property="idProfileDesc"/>
						<layout:collectionItem title="Modify" property="" url="ManageProfileAction.do?reqCode=edit&idProfile=" param="idProfile"><img src="images/edit.gif" border="0"></layout:collectionItem>
						<layout:collectionItem title="Details" property="" url="ManageProfileAction.do?reqCode=view&idProfile=" param="idProfile"><img src="images/view.gif" border="0"></layout:collectionItem>
					</layout:collection>
				</fieldset>
			</html:form>
			</DIV>
			</logic:present>				
		</DIV>
		
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>