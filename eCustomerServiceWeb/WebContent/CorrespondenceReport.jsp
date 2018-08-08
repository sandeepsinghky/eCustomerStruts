<%@page import="org.dhhs.dirm.acts.cs.formbeans.*,org.dhhs.dirm.acts.cs.beans.*"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%
	request.setAttribute("page","correspondenceReport");
	java.util.Calendar rightNow = java.util.Calendar.getInstance();

	int day = rightNow.get(java.util.Calendar.DAY_OF_MONTH);
	int month = rightNow.get(java.util.Calendar.MONTH);
	month++;
	int year = rightNow.get(java.util.Calendar.YEAR);
	
	int beginYear = 2000;
	int endYear = year;
%>		

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Correspondence Reports" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >

<script>
	var today = new Date();
	var Year = takeYear(today);
	var Month = leadingZero(today.getMonth()+1);
	var Day = leadingZero(today.getDate());
	var now = Month + "/" + Day + "/" + Year;

	function customValidateForm(form) {
		var rtn = validateCorrespondenceReportForm(form);
		var selected = 0;
		if (rtn == true) {
			if (form.allCorrespondence.checked == true) {
				selected = selected + 1;
			}
			if (form.completeCorrespondence.checked == true) {
				selected = selected + 1;
			}
			if (form.incompleteCorrespondence.checked == true) {
				selected = selected + 1;
			}
			if (selected > 1) {
				alert("Select only one correspondence report");
				return false;
			}
			
			if (selected == 0) {
				alert("Atleast one correspondence report must be selected");
				return false;
			}
			var val = compareDates(form.fromDate.value,form.toDate.value);
			
			if (val == 1) {
				alert("To date must be equal to or later than from date");
				return false;
			}
			val = compareDates(form.fromDate.value,now);
			if (val == 1) {
				alert("From date entered must be equal to or less than today's date");
				return false;
			}
			
			val = compareDates(form.toDate.value,now);
			if (val == 1) {
				alert("To date entered must be equal to or less than today's date");
				return false;
			}
			return true;
		} else {
			return rtn;
		}
	}
	
	function compareDates (value1, value2) {
	   var date1, date2;
	   var month1, month2;
	   var year1, year2;

	   month1 = value1.substring (0, value1.indexOf ("/"));
	   date1 = value1.substring (value1.indexOf ("/")+1, value1.lastIndexOf ("/"));
	   year1 = value1.substring (value1.lastIndexOf ("/")+1, value1.length);

	   month2 = value2.substring (0, value2.indexOf ("/"));
	   date2 = value2.substring (value2.indexOf ("/")+1, value2.lastIndexOf ("/"));
	   year2 = value2.substring (value2.lastIndexOf ("/")+1, value2.length);

	   if (year1 > year2) return 1;
	   else if (year1 < year2) return -1;
	   else if (month1 > month2) return 1;
	   else if (month1 < month2) return -1;
	   else if (date1 > date2) return 1;
	   else if (date1 < date2) return -1;
	   else return 0;
	} 
	

	function takeYear(theDate){
		x = theDate.getYear();
		var y = x % 100;
		y += (y < 38) ? 2000 : 1900;
		return y;
	}

	function leadingZero(nr){
		if (nr < 10) nr = "0" + nr;
		return nr;
	}
	
</script>
	
		<%-- Content for the body section belongs here --%>
		<DIV id="pagetitle">
			<h1>eCustomer Service Correspondence Report Generation</h1>
		</DIV>
		<DIV id="pagebody">
			<p>Please select All Correspondence, Complete Correspondence or Incomplete Correspondence and optionally enter filter criteria.</p>

		<DIV id="errorheader"><logic:messagesPresent>
			<UL>
				<html:messages id="error">
					<LI><bean:write name="error" /></LI>
				</html:messages>
			</UL>
		</logic:messagesPresent></DIV>
			
			<html:form action="/CorrespondenceReportAction.do?reqCode=generate"  onsubmit="return customValidateForm(this)">
					<fieldset><legend>eCSTS Correspondence Report</legend>
						<div class="row">
							<span class="label30">All Correspondence:</span> 
							<span class="field1"><html:checkbox property="allCorrespondence"/></span>
							<span class="label30">Complete Correspondence:</span> 
							<span class="field1"><html:checkbox property="completeCorrespondence"/></span>
							<span class="label30">Incomplete Correspondence:</span> 
							<span class="field1"><html:checkbox property="incompleteCorrespondence"/></span>
						</div>
						<div class="spacer">&nbsp;</div>
						<div class="spacer">&nbsp;</div>
						<fieldset><legend>Filter Criteria</legend>
							<div class="row">
								<span class="label30">Customer First Name:</span> 
								<span class="field15"><html:text property="customerFirst" maxlength="15" /></span>
								<span class="label30">Customer Last Name:</span> 
								<span class="field17"><html:text property="customerLast" maxlength="17" /></span>
							</div>
							<div class="row">
								<span class="label30">Referral Source 1:</span> 
								<span class="field35">
									<html:select property="referralSource1">
										<html:option value=""/>
										<html:options collection="referralSources" labelProperty="nmStaff" property="idStaff"/>
									</html:select>
								</span>
							</div>
							<div class="row">
								<span class="label30">Referral Source 2:</span> 
								<span class="field35">
									<html:select property="referralSource2">
										<html:option value=""/>
										<html:options collection="referralSources" labelProperty="nmStaff" property="idStaff"/>
									</html:select>
								</span>
							</div>
							<div class="row">
								<span class="label30">Referral Source 3:</span> 
								<span class="field35">
									<html:select property="referralSource3">
										<html:option value=""/>
										<html:options collection="referralSources" labelProperty="nmStaff" property="idStaff"/>
									</html:select>
								</span>
							</div>
							<div class="row">
								<span class="label30">Referral Source 4:</span> 
								<span class="field35">
									<html:select property="referralSource4">
										<html:option value=""/>
										<html:options collection="referralSources" labelProperty="nmStaff" property="idStaff"/>
									</html:select>
								</span>
							</div>
							<div class="row">
								<span class="label30">Agent Assigned:</span> 
								<span class="field35">
									<html:select property="idWorker">
										<html:option value=""/>
										<html:options collection="agents" labelProperty="nmWorker" property="idWorker"/>
									</html:select>
								</span>
							</div>
							<div class="row">
								<div id="slcalcod" style="position:absolute; left:100px; top:100px; z-index:10; visibility:hidden;">
									<script>
										printCalendar("Sun","Mon","Tue","Wed","Thu","Fri","Sat","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec",<%=day%>,<%=month%>,<%=year%>);
									</script>
								</div>								
								<span class="label30">From Date:</span> 
								<span class="field10"><html:text property="fromDate" maxlength="10" /></span>
								<a href="javascript://" onclick="showCalendar(<%=year%>,<%=month%>,<%=day%>,'MM/dd/yyyy','correspondenceReportForm','fromDate',event,<%=beginYear%>,<%=endYear%>);">
									<img alt="pick up from date" border="0" src="/images/calendar.gif" align="top">
								</a>
							</div>
							<div class="row">
								<span class="label30">To Date:</span> 
								<span class="field10"><html:text property="toDate" maxlength="10" /></span>
								<a href="javascript://" onclick="showCalendar(<%=year%>,<%=month%>,<%=day%>,'M/d/yy','correspondenceReportForm','toDate',event,<%=beginYear%>,<%=endYear%>);">
									<img alt="pick up to date" border="0" src="/images/calendar.gif" align="middle">
								</a>
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
		<html:javascript formName="correspondenceReportForm"/>
		<%-- End Content for the body section --%>
		
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>
