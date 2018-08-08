<%@page import="java.util.*,org.dhhs.dirm.acts.cs.formbeans.*,org.dhhs.dirm.acts.cs.beans.*,org.apache.struts.util.*"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
	request.setAttribute("page","localSupportReport");
	java.util.Calendar c = java.util.Calendar.getInstance();
	int year = c.get(java.util.Calendar.YEAR);

	int startYear = 2000;
	int endYear = year + 2;
	java.util.Vector yearList = new java.util.Vector();
	while (true) {
		yearList.addElement(new Integer(startYear));
		startYear++;
		if (startYear > endYear) {
			break;
		}
	}
	request.setAttribute("yearList",yearList);

	java.util.Vector monthList = new java.util.Vector();
	monthList.addElement("January");
	monthList.addElement("February");
	monthList.addElement("March");
	monthList.addElement("April");
	monthList.addElement("May");
	monthList.addElement("June");
	monthList.addElement("July");
	monthList.addElement("August");
	monthList.addElement("September");
	monthList.addElement("October");
	monthList.addElement("November");
	monthList.addElement("December");
	request.setAttribute("monthList",monthList);
%>		

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Correspondence Reports" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >
	
<script LANGUAGE="JavaScript">
		
	function disable_all() {
		
		var chkbox = 0;
		var allInputs = document.getElementById("localSupportForm").getElementsByTagName("input");
		var allSelects = document.getElementById("localSupportForm").getElementsByTagName("select");
		
		for (var i = 0; i < allSelects.length; i++) {
			allSelects[i].disabled = false;
		}
		
		for (var i = 0; i < allInputs.length; i++) {
			allInputs[i].disabled = false;
			if (allInputs[i].type == "radio") {
//				alert("Field Number : " + i + " Field Value: " + allInputs[i].value + " Field State: " + allInputs[i].checked);
				if (allInputs[i].checked == true) {
					chkbox = allInputs[i].value;
				}
			}
		}
//		alert("CheckBox: " + chkbox);
		
		if (chkbox == 0) {
//				alert("Processing Monthly Option: " + chkbox);
				document.localSupportForm.month.disabled = false;
				document.localSupportForm.detailed.disabled = false;
				document.localSupportForm.year1.disabled = false;
				document.localSupportForm.quarter.disabled = true;
				document.localSupportForm.quarter.value = "";
				document.localSupportForm.year2.disabled = true;
				document.localSupportForm.year2.value = "";
				document.localSupportForm.semiannual.disabled = true;
				document.localSupportForm.semiannual.value = "";
				document.localSupportForm.year3.disabled = true;
				document.localSupportForm.year3.value = "";
				document.localSupportForm.year4.disabled = true;
				document.localSupportForm.year4.value = "";
		} else if (chkbox == 1) {
//				alert("Processing Quarterly Option:" + chkbox);
				document.localSupportForm.month.disabled = true;
				document.localSupportForm.month.value = "";
				document.localSupportForm.detailed.disabled = true;
				document.localSupportForm.detailed.value = "";
				document.localSupportForm.year1.disabled = true;
				document.localSupportForm.year1.value = "";
				document.localSupportForm.quarter.disabled = false;
				document.localSupportForm.year2.disabled = false;
				document.localSupportForm.semiannual.disabled = true;
				document.localSupportForm.semiannual.value = "";
				document.localSupportForm.year3.disabled = true;
				document.localSupportForm.year3.value = "";
				document.localSupportForm.year4.disabled = true;
				document.localSupportForm.year4.value = "";
		} else if (chkbox == 2) {
//				alert("Processing Semiannual Option:" + chkbox);
				document.localSupportForm.month.disabled = true;
				document.localSupportForm.month.value = "";
				document.localSupportForm.detailed.disabled = true;
				document.localSupportForm.detailed.value = "";
				document.localSupportForm.year1.disabled = true;
				document.localSupportForm.year1.value = "";
				document.localSupportForm.year2.disabled = true;
				document.localSupportForm.year2.value = "";
				document.localSupportForm.quarter.disabled = true;
				document.localSupportForm.quarter.value = "";
				document.localSupportForm.year2.disabled = true;
				document.localSupportForm.year2.value = "";
				document.localSupportForm.semiannual.disabled = false;
				document.localSupportForm.year3.disabled = false;
				document.localSupportForm.year4.disabled = true;
				document.localSupportForm.year4.value = "";
		} else if (chkbox == 3) {
//				alert("Processing Annual Option:" + chkbox);
				document.localSupportForm.month.disabled = true;
				document.localSupportForm.month.value = "";
				document.localSupportForm.detailed.disabled = true;
				document.localSupportForm.detailed.value = "";
				document.localSupportForm.year1.disabled = true;
				document.localSupportForm.year1.value = "";
				document.localSupportForm.quarter.disabled = true;
				document.localSupportForm.quarter.value = "";
				document.localSupportForm.year2.disabled = true;
				document.localSupportForm.year2.value = "";
				document.localSupportForm.semiannual.disabled = true;
				document.localSupportForm.semiannual.value = "";
				document.localSupportForm.year3.disabled = true;
				document.localSupportForm.year3.value = "";
				document.localSupportForm.year4.disabled = false;
		}
	}
</script>
	
		<%-- Content for the body section belongs here --%>
		<DIV id="pagetitle">
			<h1>eCustomer Service Local Support Report Generation</h1>
		</DIV>
		<DIV id="pagebody">
			<p>Please select an appropriate reporting period and select report options for the period.</p>

		<DIV id="errorheader"><logic:messagesPresent>
			<UL>
				<html:messages id="error">
					<LI><bean:write name="error" /></LI>
				</html:messages>
			</UL>
		</logic:messagesPresent></DIV>
			
			<html:form action="/LocalSupportReportAction.do?reqCode=generate">
				<fieldset><legend>eCSTS Local Support Report</legend>
					<div class="row">
						<span class="label30"></span>
						<logic:iterate id="item" name="localSupportForm" property="itemChoices" type="org.dhhs.dirm.acts.cs.formbeans.Item">
							<html:radio property="itemNumber" value="<%=new Integer(item.getSelectedItemNumber()).toString()%>" onclick="disable_all();"/>
							<bean:write name="item" property="displayLabel"/>
						</logic:iterate>
					</div>
					<div class="spacer">&nbsp;</div>
					<div class="spacer">&nbsp;</div>
					<fieldset class="1"><legend>Monthly Report Options</legend>
						<div class="row">
							<span class="label30">Month/Year:</span> 
							<span class="field15">
								<html:select property="month">
									<html:option value=""/>
									<html:options name="monthList"/>
								</html:select>
							</span>
							<span class="field4">
								<html:select property="year1">
									<html:option value=""/>
									<html:options name="yearList"/>
								</html:select>
							</span>
						</div>
						<div class="row">
							<span class="label30">Detailed Report:</span> 
							<span class="field1"><html:checkbox property="detailed"/></span>
						</div>
					</fieldset>
					<div class="spacer">&nbsp;</div>
					<div class="spacer">&nbsp;</div>
					<fieldset class="1"><legend>Quarterly Report Options</legend>
						<div class="row">
							<span class="label30">Quarter/Year:</span> 
							<span class="field1">
								<html:select property="quarter">
									<html:option value=" "/>
									<html:option value="1"/>
									<html:option value="2"/>
									<html:option value="3"/>
									<html:option value="4"/>
								</html:select>
							</span>
							<span class="field4">
								<html:select property="year2">
									<html:option value=""/>
									<html:options name="yearList"/>
								</html:select>
							</span>
						</div>
					</fieldset>
					<div class="spacer">&nbsp;</div>
					<div class="spacer">&nbsp;</div>
					<fieldset class="1"><legend>Semi-Annual Report Options</legend>
						<div class="row">
							<span class="label30">Semi-Annual/Year:</span> 
							<span class="field1">
								<html:select property="semiannual">
									<html:option value=" "/>
									<html:option value="1"/>
									<html:option value="2"/>
								</html:select>
							</span>
							<span class="field4">
								<html:select property="year3">
									<html:option value=""/>
									<html:options name="yearList"/>
								</html:select>
							</span>
						</div>
					</fieldset>
					<div class="spacer">&nbsp;</div>
					<div class="spacer">&nbsp;</div>
					<fieldset class="1"><legend>Annual Report Options</legend>
						<div class="row">
							<span class="label30">Annual:</span> 
							<span class="field4">
								<html:select property="year4">
									<html:option value=""/>
									<html:options name="yearList"/>
								</html:select>
							</span>
						</div>
					</fieldset>
					<div class="spacer">&nbsp;</div>
					<div class="spacer">&nbsp;</div>
					
					<%-- Add the Buttons --%>
					<div class="row">
						<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
					</div>
						
					<div class="row">
						<span class="button"><html:button property="Return" onclick="window.location.href='Navigation.do';"><bean:message key="button.return"/></html:button></span>
						<span class="button"><html:reset><bean:message key="button.reset"/></html:reset></span>
						<span class="button"><html:submit><bean:message key="button.execute"/></html:submit></span>
					</div>
					<div class="spacer">&nbsp;</div>
					<div class="spacer">&nbsp;</div>
				</fieldset>
			</html:form>
		</DIV>
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>
