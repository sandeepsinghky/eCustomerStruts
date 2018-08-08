<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
	<DIV id="errorheader"><logic:messagesPresent>
		<UL>
			<html:messages id="error">
				<LI><bean:write name="error" /></LI>
			</html:messages>
		</UL>
	</logic:messagesPresent></DIV>
