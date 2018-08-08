<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<%
	request.setAttribute("page","workloadSearch");
	java.util.Calendar rightNow = java.util.Calendar.getInstance();

	int day = rightNow.get(java.util.Calendar.DAY_OF_MONTH);
	int month = rightNow.get(java.util.Calendar.MONTH);
	month++;
	int year = rightNow.get(java.util.Calendar.YEAR);
	
	int beginYear = 2000;
	int endYear = year;
	
%>
<script LANGUAGE="JavaScript">

	var today = new Date();
	var Year = takeYear(today);
	var Month = leadingZero(today.getMonth()+1);
	var Day = leadingZero(today.getDate());
	var now = Month + "/" + Day + "/" + Year;

	
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


	function reset_all(form) {
		form.idReference.value = "";
		form.fromDate.value = "";
		form.toDate.value = "";
		form.srcAgent.value = "";
		form.trgAgent.value="";
	}
	
	function select_all() {
		
		var allInputs = document.getElementById("workloadFormBean").getElementsByTagName("input");
		for (var i = 0; i < allInputs.length; i++) {
		
			if (allInputs[i].type == "checkbox") {
				allInputs[i].checked = true;
			}
		}
	}
	
	function clear_all() {
		
		var allInputs = document.getElementById("workloadFormBean").getElementsByTagName("input");
		for (var i = 0; i < allInputs.length; i++) {
		
			if (allInputs[i].type == "checkbox") {
				allInputs[i].checked = false;
			}
		}
	}
	
	function transfer_all(form) {
		form.button.value = "transferAll";
		return true;
	}
	
	function customValidateForm(form) {
		var rtn = validateWorkloadFormBean(form);

		if (rtn == true) {
			var idRefNull = 0;
			var frDateNull = 0;
			var toDateNull = 0;
			var srcAgntNull = 0;
			var trgAgntNull = 0;
			
			if (form.idReference.value == null || form.idReference.value == "") {
				idRefNull = 1;
			}
			
			if (form.fromDate.value == "") {
				frDateNull = 1;
			}
			
			if (form.fromDate.value != null) {
				var val = compareDates(form.fromDate.value,now);
				if (val == 1) {
					alert("From date must be equal to or less than today's date");
					return false;
				}
			} else {
				frDateNull = 1;
			}
			
			if (form.toDate.value == "") {
				toDateNull = 1;
			}
			
			if (form.toDate.value != null) {
				var val = compareDates(form.toDate.value,now);
				if (val == 1) {
					alert("To date must be equal to or less than today's date");
					return false;
				}
			} else {
				toDateNull = 1;
			}
			
			if (form.srcAgent.value == "") {
				srcAgntNull = 1;
			}
			if (form.trgAgent.value == "") {
				trgAgntNull = 1;
			}
			
			// If Reference ID is null, then the agent must enter from date, to date, src and trg agents
			if (idRefNull == 1) {
				if (frDateNull == 1) {
					alert("From date must be entered when reference id is blank");
					form.fromDate.focus();
					return false;
				}
				if (toDateNull == 1) {
					alert("To date must be entered when reference id is blank");
					form.toDate.focus();
					return false;
				}
				if (srcAgntNull == 1) {
					alert("Source Agent must be selected when reference id is blank");
					form.srcAgent.focus();
					return false;
				}
				if (trgAgntNull == 1) {
					alert("Target Agent must be selected when reference id is blank");
					form.trgAgent.focus();
					return false;
				}
			}
			return true;			
		} else {
			return rtn;
		}
	}	
</script>

<tiles:insert page="layout.jsp" flush="true">
	<tiles:put name="title" value="Workload Search" />
	<tiles:put name="header" value="header.jsp" />
	<tiles:put name="menu" value="menu.jsp" />
	<tiles:put name="redirect" value="redirect.jsp" />
	<tiles:put name="navigation" value="ManageStack.jsp" />
	<tiles:put name="body" type="string" >
		<%-- Content for the body section belongs here --%>

		<DIV id="pagetitle">
			<h1>eCustomer Service Workload Search</h1>
		</DIV>
		<DIV id="pagebody">
			<p>
				If you know the task that you want to transfer, enter the reference id for that task and click search.
					If you prefer, you can select a source agent, enter a date range to select tasks for that agent,
					select a target	agent and click search.
			</p>
		<DIV id="errorheader"><logic:messagesPresent>
			<UL>
				<html:messages id="error">
					<LI><bean:write name="error" /></LI>
				</html:messages>
			</UL>
		</logic:messagesPresent></DIV>
		<DIV id="form">
			<html:form action="/WorkloadSearchAction.do?reqCode=execute" focus="idReference" styleClass="form100" onsubmit="return customValidateForm(this)">
				<fieldset><legend>eCSTS Workload Search</legend>
					<html:hidden property="button"/>
					<div class="row">
						<span class="label20">Reference ID:</span> 
						<span class="field8"><html:text property="idReference" maxlength="10" /> Enter the specific reference id or any combination of fields below</span>
					</div>
					<div class="spacer">&nbsp;</div>
					<div class="row">
						<span class="separator"><html:img page="/images/line.gif" width="500px" border="0" /></span>
					</div>
					<div class="spacer">&nbsp;</div>

						<div class="row">
							<div id="slcalcod" style="position:absolute; left:100px; top:100px; z-index:10; visibility:hidden;">
								<script>
									printCalendar("Sun","Mon","Tue","Wed","Thu","Fri","Sat","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec",<%=day%>,<%=month%>,<%=year%>);
								</script>
							</div>								
							<span class="label20">From Date:</span> 
							<span class="field10"><html:text property="fromDate" maxlength="10" /></span>
							<a href="javascript://" onclick="showCalendar(<%=year%>,<%=month%>,<%=day%>,'MM/dd/yyyy','workloadFormBean','fromDate',event,<%=beginYear%>,<%=endYear%>);">
								<img alt="pick up to date" border="0" src="/images/calendar.gif" align="middle">
							</a>
							<span class="label30">To Date:</span> 
							<span class="field10"><html:text property="toDate" maxlength="10" /></span>
							<a href="javascript://" onclick="showCalendar(<%=year%>,<%=month%>,<%=day%>,'MM/dd/yyyy','workloadFormBean','toDate',event,<%=beginYear%>,<%=endYear%>);">
								<img alt="pick up to date" border="0" src="/images/calendar.gif" align="middle">
							</a>
						</div>
					<div class="row">
						<span class="label20">From Agent:</span> 
						<span class="field30">
							<html:select property="srcAgent">
								<html:option value=""/>
								<html:options collection="agents" labelProperty="nmWorker" property="idWorker" />
							</html:select>
						</span>
						<span class="label15">To Agent:</span> 
						<span class="field30">
							<html:select property="trgAgent">
								<html:option value=""/>
								<html:options collection="agents" labelProperty="nmWorker" property="idWorker" />
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
						<span class="button"><html:button property="Reset" onclick="reset_all(form)">Reset</html:button></span>
						<span class="button"><html:submit><bean:message key="button.search"/></html:submit></span>
					</div>
					<div class="spacer">&nbsp;</div>
					<div class="spacer">&nbsp;</div>
				</fieldset>
				
				<logic:present name="workloadFormBean" property="tasks">
					<logic:notEmpty name="workloadFormBean" property="tasks">
					<p>Based on your search criteria, here is a list of tasks selected for transfer.</p>
					<DIV id="form">
						<fieldset><legend>eCSTS Task Transfer List</legend>
							<layout:collection property="tasks" styleClass="FORM" styleClass2="FORM2"  selectType="checkbox" selectName="selectedTasks" selectProperty="idReference" sortAction="client" title="" >
								<layout:collectionItem title="Select" property="filler"/>
								<layout:collectionItem title="Reference ID" property="idReference" sortable="true"/>
								<layout:collectionItem title="Status" property="cdStatus" sortable="true"/>
								<layout:collectionItem title="Agent Assigned" property="srcAgent" sortable="true"/>
								<layout:collectionItem title="Transfer To" property="trgAgent" sortable="true"/>
							</layout:collection>
							<div class="row">
								<span class="buttonborder"><html:img page="/images/line.gif" width="500px" border="0" /></span>
							</div>
							<div class="row">
								<span class="button"><html:button property="Select All" onclick="select_all();">Select All</html:button></span>
								<span class="button"><html:button property="Clear" onclick="clear_all();">Clear</html:button></span>
								<span class="button"><html:button property="Transfer" onclick="transfer_all(form);form.submit();">Transfer</html:button></span>
							</div>
						</fieldset>
					</DIV>
					</logic:notEmpty>
				</logic:present>
			</html:form>
		</DIV>
		</DIV>
			<div class="spacer">&nbsp;</div>
			<div class="spacer">&nbsp;</div>
			<div class="spacer">&nbsp;</div>
			
			<html:javascript formName="workloadFormBean"/>
		
			<%-- End Content for the body section --%>
	</tiles:put>
	<tiles:put name="footer" value="footer.jsp" />		
</tiles:insert>