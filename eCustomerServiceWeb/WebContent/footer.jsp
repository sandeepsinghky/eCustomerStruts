<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<DIV id="pagefooter">
	<logic:present scope="session" name="userbean">
		<html:link forward="welcome">Home</html:link> |
	</logic:present>
	<logic:notPresent scope="session" name="userbean">
		<html:link forward="index">Home</html:link> |
	</logic:notPresent>
	<A href="http://www.dhhs.state.nc.us/dss/">DSS Home</A> | 
	<A href="http://www.dhhs.state.nc.us/index.htm">DHHS</A> | 
	<A href="http://www.dhhs.state.nc.us/dss/cse">CSE</A> | 
	<A	href="http://www.ncgov.com">Official Website of North Carolina</A>
</DIV>