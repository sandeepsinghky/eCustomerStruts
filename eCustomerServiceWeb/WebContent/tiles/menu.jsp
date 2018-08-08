<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<logic:present scope="session" name="userbean">
	<logic:equal name="userbean" property="idProfile" value="SSSM">
		<DIV id="navigationbar">
		<TABLE border="0" width="50%">
			<TBODY>
				<TR>
					<TD align="center" width="67"><DIV id="menu"><A href="#" title="Home" align="center">Home</A></DIV></TD>
					<TD align="center" width="130"><DIV id="menu"><html:link forward="userSearch">Users</html:link></DIV></TD>
					<TD align="center" width="130"><DIV id="menu"><a href="#" title="Manage Workload">Workload</a></DIV></TD>
					<TD align="center" width="130"><DIV id="menu"><a href="#" title="Manage Workflow">Workflow</a></DIV></TD>
					<TD align="center" width="130"><DIV id="menu"><a href="#" title="Manage Tasks">Tasks</a></DIV></TD>
					<TD align="center" width="81"><DIV id="menu"><a href="#" title="Reports">Reports</a></DIV></TD>
					<TD align="center" width="67"><DIV id="menu"><html:link forward="logoff">Logout</html:link></DIV></TD>
				</TR>
			</TBODY>
		</TABLE>
		</DIV>
	</logic:equal>
	<logic:equal name="userbean" property="idProfile" value="WRKR">
		<DIV id="navigationbar">
		<TABLE border="0" width="50%">
			<TBODY>
				<TR>
					<TD align="center" width="67"><DIV id="menu"><A href="#" title="Home" align="center">Home</A></DIV></TD>
					<TD align="center" width="130"><DIV id="menu"><a href="#" title="Manage Workload">Workload</a></DIV></TD>
					<TD align="center" width="130"><DIV id="menu"><a href="#" title="Manage Tasks">Tasks</a></DIV></TD>
					<TD align="center" width="81"><DIV id="menu"><a href="#" title="Reports">Reports</a></DIV></TD>
					<TD align="center" width="67"><DIV id="menu"><html:link forward="logoff">Logout</html:link></DIV></TD>
				</TR>
			</TBODY>
		</TABLE>
		</DIV>
	</logic:equal>
</logic:present>
<logic:notPresent scope="session" name="userbean">
	<DIV id="navigationbar">
	<TABLE border="0" width="50%">
		<TBODY>
			<TR>
				<TD align="center" width="67">&nbsp;</TD>
				<TD align="center" width="67">&nbsp;</TD>
				<TD align="center" width="67">&nbsp;</TD>
				<TD align="center" width="67">&nbsp;</TD>
				<TD align="center" width="67">&nbsp;</TD>
				<TD align="center" width="67"><DIV id="menu"><A href="#" title="Home" align="center">Home</A></DIV></TD>
				<TD align="center" width="67"><DIV id="menu"><html:link forward="logon">Logon</html:link></DIV></TD>
			</TR>
		</TBODY>
	</TABLE>
	</DIV>
</logic:notPresent>
