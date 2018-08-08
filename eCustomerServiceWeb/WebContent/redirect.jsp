<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<logic:notPresent scope="session" name="userbean">
	<logic:forward name="logon"/>
</logic:notPresent>