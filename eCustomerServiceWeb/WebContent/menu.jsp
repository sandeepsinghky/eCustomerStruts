<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<logic:present scope="session" name="userbean">
	<DIV id="navigationbar">
	<TABLE border="0" width="50%">
		<TBODY>
			<TR>
				<TD align="center" width="67"><DIV id="menu"><html:link forward="welcome">Home</html:link></DIV></TD>
				<logic:equal name="userbean" property="manageAll" value="true">
					<TD align="center" width="130"><DIV id="menu"><html:link forward="administration">Administration</html:link></DIV></TD>
				</logic:equal>
				<logic:notEqual name="userbean" property="manageAll" value="true">
					<logic:equal name="userbean" property="manageUsers" value="true">
						<TD align="center" width="130"><DIV id="menu"><html:link forward="administration">Administration</html:link></DIV></TD>
					</logic:equal>
					<logic:notEqual name="userbean" property="manageUsers" value="true">
						<logic:equal name="userbean" property="manageProfiles" value="true">
							<TD align="center" width="130"><DIV id="menu"><html:link forward="administration">Administration</html:link></DIV></TD>
						</logic:equal>
						<logic:notEqual name="userbean" property="manageProfiles" value="true">
							<logic:equal name="userbean" property="manageWorkFlow" value="true">
								<TD align="center" width="130"><DIV id="menu"><html:link forward="administration">Administration</html:link></DIV></TD>
							</logic:equal>
							<logic:notEqual name="userbean" property="manageWorkFlow" value="true">
								<logic:equal name="userbean" property="manageWorkLoad" value="true">
									<TD align="center" width="130"><DIV id="menu"><html:link forward="administration">Administration</html:link></DIV></TD>
								</logic:equal>
								<logic:notEqual name="userbean" property="manageWorkLoad" value="true">
									<logic:equal name="userbean" property="manageApprovals" value="true">
										<TD align="center" width="130"><DIV id="menu"><html:link forward="administration">Administration</html:link></DIV></TD>
									</logic:equal>
									<logic:notEqual name="userbean" property="manageApprovals" value="true">
										<logic:equal name="userbean" property="manageReferralSources" value="true">
											<TD align="center" width="130"><DIV id="menu"><html:link forward="administration">Administration</html:link></DIV></TD>
										</logic:equal>
									</logic:notEqual>
								</logic:notEqual>
							</logic:notEqual>
						</logic:notEqual>
					</logic:notEqual>
				</logic:notEqual>
				<logic:equal name="userbean" property="manageAll" value="true">
					<TD align="center" width="130"><DIV id="menu"><html:link forward="workloadManagementMenu">Workload</html:link></DIV></TD>
					<TD align="center" width="130"><DIV id="menu"><html:link forward="reportsManagement">Reports</html:link></DIV></TD>
				</logic:equal>
				<logic:notEqual name="userbean" property="manageAll" value="true">
					<logic:equal name="userbean" property="manageWorkLoad" value="true">
						<TD align="center" width="130"><DIV id="menu"><html:link forward="workloadManagementMenu">Workload</html:link></DIV></TD>
					</logic:equal>
				</logic:notEqual>
				<logic:notEqual name="userbean" property="manageAll" value="true">
					<logic:equal name="userbean" property="manageReports" value="true">
						<TD align="center" width="130"><DIV id="menu"><html:link forward="reportsManagement">Reports</html:link></DIV></TD>
					</logic:equal>
				</logic:notEqual>
				<TD align="center" width="130"><DIV id="menu"><html:link forward="taskSearch">Tasks</html:link></DIV></TD>
				<TD align="center" width="67"><DIV id="menu"><html:link forward="logoff">Logout</html:link></DIV></TD>
			</TR>
		</TBODY>
	</TABLE>
	</DIV>
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
				<TD align="center" width="67"><DIV id="menu"><html:link forward="index">Home</html:link></DIV></TD>
				<TD align="center" width="67"><DIV id="menu"><html:link forward="logon">Login</html:link></DIV></TD>
			</TR>
		</TBODY>
	</TABLE>
	</DIV>
</logic:notPresent>