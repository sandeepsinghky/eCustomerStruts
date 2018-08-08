<%@page import="org.dhhs.dirm.acts.cs.formbeans.*,org.dhhs.dirm.acts.cs.beans.*,java.util.Vector" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
	request.setAttribute("page","actsWorkerSearch");
%>		

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Search ACTS for Worker" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >
	
		<%-- Content for the body section belongs here --%>
		
		<DIV id="pagetitle">
			<h1>Acts Worker Search</h1>
		</DIV>
		<DIV id="pagebody">
			<p>Please enter either the ACTS worker id or simply search by Last Name and First Name and click the search button.</p>
			
		<DIV id="errorheader"><logic:messagesPresent>
			<UL>
				<html:messages id="error">
					<LI><bean:write name="error" /></LI>
				</html:messages>
			</UL>
		</logic:messagesPresent></DIV>
			
			<DIV id="form">
			<html:form action="/ActsWorkerSearchAction" focus="workerid" onsubmit="return validateActsWorkerSearchForm(this)">
				<fieldset><legend>Acts Worker Search</legend>
					<div class="row">
						<span class="label30">ACTS Worker ID:</span> 
						<span class="field8"><html:text property="workerid" maxlength="8" /> OR</span>
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
						<span class="button"><html:submit><bean:message key="button.search"/></html:submit></span>
					</div>
					<div class="spacer">&nbsp;</div>
					<div class="spacer">&nbsp;</div>
					
				</fieldset>
			</html:form>
			</DIV>

			<logic:present scope="request" name="actsworkerlist">
			<p>Based on your search criteria, here is a list of ACTS workers selected.</p>
			<DIV id="form">
			<html:form action="/ActsWorkerSearchAction">
				<fieldset><legend>ACTS Worker List</legend>
					<layout:collection name="actsworkerlist" styleClass="FORM" styleClass2="FORM2" sortAction="client" title="" align="left" width="100%">
						<layout:collectionItem title="ACTS Worker ID" property="idWrkr" sortable="true"/>
						<layout:collectionItem title="Name" property="nmWrkr" sortable="true"/>
						<layout:collectionItem title="Racf ID" property="idWrkrLogon" sortable="true"/>
						<layout:collectionItem title="Select" property="" url="ManageUserAction.do?reqCode=create&idWorker=" param="idWrkr"><img src="images/view.gif" border="0"></layout:collectionItem>
					</layout:collection>
				</fieldset>
			</html:form>
			</DIV>
			</logic:present>				
		</DIV>
		<html:javascript formName="actsWorkerSearchForm"/>		
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>
