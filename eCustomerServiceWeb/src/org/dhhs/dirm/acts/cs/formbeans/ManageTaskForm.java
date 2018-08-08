

package org.dhhs.dirm.acts.cs.formbeans;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;
import org.dhhs.dirm.acts.cs.Constants;

/**
 * ActsWorkerSearchForm.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 26, 2003 2:29:17 PM
 * 
 * @author Chris Meike
 *
 */
public class ManageTaskForm extends ValidatorForm
{

	private String	idReference;

	private String	nbCase;
	private String	idPart;
	private String	nbDocket;

	private String	idWorker;
	private String	nmWorker;

	private String	nbSSN;

	private String	idEmail;
	private String	idEmailConfirm;

	private String	cdType;
	private String	cdStatus;

	private String	nmCounty;

	private String	controlNumber;

	private String	nbTelAcd;
	private String	nbTelExc;
	private String	nbTelLn;
	private String	nbTelExt;

	private String	dtReceived;
	private String	dtDue;
	private String	dtCompleted;

	private String	nmAuthor;

	private String	nmAuthorFirst;
	private String	nmAuthorLast;
	private String	nmAuthorMi;

	private String	nmCustParFirst;
	private String	nmCustParLast;
	private String	nmCustParMi;

	private String	nmNonCustParFirst;
	private String	nmNonCustParLast;
	private String	nmNonCustParMi;

	private String	idStaff1;
	private String	idStaff2;
	private String	idStaff3;
	private String	idStaff4;

	private String	nmRefSource1;
	private String	nmRefSource2;
	private String	nmRefSource3;
	private String	nmRefSource4;

	private String	ntResolution;

	private boolean	chkOutstanding;
	private boolean	chkApproval;
	private boolean	chkCompleted;

	private boolean	selfAssigned;

	private boolean	approvalRequired;

	// MOD# 2872 - rkodumagulla. 09/15/04. This field will allow the agents to
	// override the default search criteria only to search their own workload
	private boolean	agentSearchOverride;

	private String	processStatus;

	private boolean	owner;

	public ManageTaskForm()
	{

		super();

	}
	public void reset(ActionMapping mapping, HttpServletRequest request)
	{

		idReference = "";
		nbCase = "";
		idPart = "";
		nbDocket = "";
		idWorker = "";
		nmWorker = "";
		nbSSN = "";
		idEmail = "";
		idEmailConfirm = "";
		cdType = "";
		cdStatus = "";
		nmCounty = "";
		controlNumber = "";
		nbTelAcd = "";
		nbTelExc = "";
		nbTelLn = "";
		nbTelExt = "";
		dtReceived = "";
		dtDue = "";
		dtCompleted = "";
		nmAuthor = "";
		nmAuthorFirst = "";
		nmAuthorLast = "";
		nmAuthorMi = "";
		nmCustParFirst = "";
		nmCustParLast = "";
		nmCustParMi = "";
		nmNonCustParFirst = "";
		nmNonCustParLast = "";
		nmNonCustParMi = "";
		idStaff1 = "";
		idStaff2 = "";
		idStaff3 = "";
		idStaff4 = "";
		nmRefSource1 = "";
		nmRefSource2 = "";
		nmRefSource3 = "";
		nmRefSource4 = "";
		ntResolution = "";
		chkOutstanding = false;
		chkApproval = false;
		chkCompleted = false;
		selfAssigned = false;
		approvalRequired = false;
		processStatus = "";
	}

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request)
	{

		ActionErrors errors = null;

		if ((!request.getParameter(Constants.METHOD).equals(Constants.CREATE)) && (!request.getParameter(Constants.METHOD).equals(Constants.EDIT)) && (!request.getParameter(Constants.METHOD).equals(Constants.PRINT)) && (!request.getParameter(Constants.METHOD).equals(Constants.VIEW)))
		{
			errors = super.validate(mapping, request);
		}

		if (errors == null)
		{
			errors = new ActionErrors();
		} else
		{
			if (request.getParameter(Constants.METHOD).equals(Constants.SAVE))
			{
				request.setAttribute("formMode", "1");
			}
			if (request.getParameter(Constants.METHOD).equals(Constants.STORE))
			{
				request.setAttribute("formMode", "0");
			}
		}

		return errors;
	}

	public String toString()
	{

		StringBuffer sb = new StringBuffer();
		sb.append("Date Recieved: " + dtReceived + "\n");
		sb.append("Case: " + nbCase + "\n");
		sb.append("MPI: " + idPart + "\n");
		sb.append("Worker: " + idWorker + "\n");
		sb.append("Author: " + nmAuthor + "\n");
		sb.append("CP First Name: " + nmCustParFirst + "\n");
		sb.append("CP Last Name: " + nmCustParLast + "\n");
		sb.append("CP Middle Init: " + nmCustParMi + "\n");
		sb.append("NCP First Name: " + nmNonCustParFirst + "\n");
		sb.append("NCP Last Name: " + nmNonCustParLast + "\n");
		sb.append("NCP Middle Init: " + nmNonCustParMi + "\n");
		sb.append("Email: " + idEmail + "\n");
		sb.append("Area Code: " + nbTelAcd + "\n");
		sb.append("Exchange: " + nbTelExc + "\n");
		sb.append("Line: " + nbTelLn + "\n");
		sb.append("Ext: " + nbTelExt + "\n");
		sb.append("Due Date: " + dtDue + "\n");
		sb.append("Date Completed: " + dtCompleted + "\n");
		sb.append("Referral Source 1: " + nmRefSource1 + "\n");
		sb.append("Referral Source 1: " + nmRefSource2 + "\n");
		sb.append("Referral Source 3: " + nmRefSource3 + "\n");
		sb.append("Type: " + cdType + "\n");
		sb.append("Status: " + cdStatus + "\n");
		sb.append("Resolution: " + ntResolution + "\n");
		return sb.toString();
	}
	/**
	 * Returns the cdType.
	 * 
	 * @return String
	 */
	public String getCdType()
	{
		return cdType;
	}

	/**
	 * Returns the chkApproval.
	 * 
	 * @return boolean
	 */
	public boolean isChkApproval()
	{
		return chkApproval;
	}

	/**
	 * Returns the chkCompleted.
	 * 
	 * @return boolean
	 */
	public boolean isChkCompleted()
	{
		return chkCompleted;
	}

	/**
	 * Returns the chkOutstanding.
	 * 
	 * @return boolean
	 */
	public boolean isChkOutstanding()
	{
		return chkOutstanding;
	}

	/**
	 * Returns the dtCompleted.
	 * 
	 * @return String
	 */
	public String getDtCompleted()
	{
		return dtCompleted;
	}

	/**
	 * Returns the dtDue.
	 * 
	 * @return String
	 */
	public String getDtDue()
	{
		return dtDue;
	}

	/**
	 * Returns the dtReceived.
	 * 
	 * @return String
	 */
	public String getDtReceived()
	{
		return dtReceived;
	}

	/**
	 * Returns the idEmail.
	 * 
	 * @return String
	 */
	public String getIdEmail()
	{
		return idEmail;
	}

	/**
	 * Returns the idPart.
	 * 
	 * @return String
	 */
	public String getIdPart()
	{
		return idPart;
	}

	/**
	 * Returns the idReference.
	 * 
	 * @return String
	 */
	public String getIdReference()
	{
		return idReference;
	}

	/**
	 * Returns the idWorker.
	 * 
	 * @return String
	 */
	public String getIdWorker()
	{
		return idWorker;
	}

	/**
	 * Returns the nbCase.
	 * 
	 * @return String
	 */
	public String getNbCase()
	{
		return nbCase;
	}

	/**
	 * Returns the nbDocket.
	 * 
	 * @return String
	 */
	public String getNbDocket()
	{
		return nbDocket;
	}

	/**
	 * Returns the nbSSN.
	 * 
	 * @return String
	 */
	public String getNbSSN()
	{
		return nbSSN;
	}

	/**
	 * Returns the nbTelAcd.
	 * 
	 * @return String
	 */
	public String getNbTelAcd()
	{
		return nbTelAcd;
	}

	/**
	 * Returns the nbTelExc.
	 * 
	 * @return String
	 */
	public String getNbTelExc()
	{
		return nbTelExc;
	}

	/**
	 * Returns the nbTelExt.
	 * 
	 * @return String
	 */
	public String getNbTelExt()
	{
		return nbTelExt;
	}

	/**
	 * Returns the nbTelLn.
	 * 
	 * @return String
	 */
	public String getNbTelLn()
	{
		return nbTelLn;
	}

	/**
	 * Returns the nmAuthor.
	 * 
	 * @return String
	 */
	public String getNmAuthor()
	{
		return nmAuthor;
	}

	/**
	 * Returns the nmCustParFirst.
	 * 
	 * @return String
	 */
	public String getNmCustParFirst()
	{
		return nmCustParFirst;
	}

	/**
	 * Returns the nmCustParLast.
	 * 
	 * @return String
	 */
	public String getNmCustParLast()
	{
		return nmCustParLast;
	}

	/**
	 * Returns the nmCustParMi.
	 * 
	 * @return String
	 */
	public String getNmCustParMi()
	{
		return nmCustParMi;
	}

	/**
	 * Returns the nmNonCustParFirst.
	 * 
	 * @return String
	 */
	public String getNmNonCustParFirst()
	{
		return nmNonCustParFirst;
	}

	/**
	 * Returns the nmNonCustParLast.
	 * 
	 * @return String
	 */
	public String getNmNonCustParLast()
	{
		return nmNonCustParLast;
	}

	/**
	 * Returns the nmNonCustParMi.
	 * 
	 * @return String
	 */
	public String getNmNonCustParMi()
	{
		return nmNonCustParMi;
	}

	/**
	 * Returns the nmRefSource1.
	 * 
	 * @return String
	 */
	public String getNmRefSource1()
	{
		return nmRefSource1;
	}

	/**
	 * Returns the nmRefSource2.
	 * 
	 * @return String
	 */
	public String getNmRefSource2()
	{
		return nmRefSource2;
	}

	/**
	 * Returns the nmRefSource3.
	 * 
	 * @return String
	 */
	public String getNmRefSource3()
	{
		return nmRefSource3;
	}

	/**
	 * Returns the ntResolution.
	 * 
	 * @return String
	 */
	public String getNtResolution()
	{
		return ntResolution;
	}

	/**
	 * Sets the cdType.
	 * 
	 * @param cdType
	 *            The cdType to set
	 */
	public void setCdType(String cdType)
	{
		this.cdType = cdType;
	}

	/**
	 * Sets the chkApproval.
	 * 
	 * @param chkApproval
	 *            The chkApproval to set
	 */
	public void setChkApproval(boolean chkApproval)
	{
		this.chkApproval = chkApproval;
	}

	/**
	 * Sets the chkCompleted.
	 * 
	 * @param chkCompleted
	 *            The chkCompleted to set
	 */
	public void setChkCompleted(boolean chkCompleted)
	{
		this.chkCompleted = chkCompleted;
	}

	/**
	 * Sets the chkOutstanding.
	 * 
	 * @param chkOutstanding
	 *            The chkOutstanding to set
	 */
	public void setChkOutstanding(boolean chkOutstanding)
	{
		this.chkOutstanding = chkOutstanding;
	}

	/**
	 * Sets the dtCompleted.
	 * 
	 * @param dtCompleted
	 *            The dtCompleted to set
	 */
	public void setDtCompleted(String dtCompleted)
	{
		this.dtCompleted = dtCompleted;
	}

	/**
	 * Sets the dtDue.
	 * 
	 * @param dtDue
	 *            The dtDue to set
	 */
	public void setDtDue(String dtDue)
	{
		this.dtDue = dtDue;
	}

	/**
	 * Sets the dtReceived.
	 * 
	 * @param dtReceived
	 *            The dtReceived to set
	 */
	public void setDtReceived(String dtReceived)
	{
		this.dtReceived = dtReceived;
	}

	/**
	 * Sets the idEmail.
	 * 
	 * @param idEmail
	 *            The idEmail to set
	 */
	public void setIdEmail(String idEmail)
	{
		this.idEmail = idEmail;
	}

	/**
	 * Sets the idPart.
	 * 
	 * @param idPart
	 *            The idPart to set
	 */
	public void setIdPart(String idPart)
	{
		this.idPart = idPart;
	}

	/**
	 * Sets the idReference.
	 * 
	 * @param idReference
	 *            The idReference to set
	 */
	public void setIdReference(String idReference)
	{
		this.idReference = idReference;
	}

	/**
	 * Sets the idWorker.
	 * 
	 * @param idWorker
	 *            The idWorker to set
	 */
	public void setIdWorker(String idWorker)
	{
		this.idWorker = idWorker;
	}

	/**
	 * Sets the nbCase.
	 * 
	 * @param nbCase
	 *            The nbCase to set
	 */
	public void setNbCase(String nbCase)
	{
		this.nbCase = nbCase;
	}

	/**
	 * Sets the nbDocket.
	 * 
	 * @param nbDocket
	 *            The nbDocket to set
	 */
	public void setNbDocket(String nbDocket)
	{
		this.nbDocket = nbDocket;
	}

	/**
	 * Sets the nbSSN.
	 * 
	 * @param nbSSN
	 *            The nbSSN to set
	 */
	public void setNbSSN(String nbSSN)
	{
		this.nbSSN = nbSSN;
	}

	/**
	 * Sets the nbTelAcd.
	 * 
	 * @param nbTelAcd
	 *            The nbTelAcd to set
	 */
	public void setNbTelAcd(String nbTelAcd)
	{
		this.nbTelAcd = nbTelAcd;
	}

	/**
	 * Sets the nbTelExc.
	 * 
	 * @param nbTelExc
	 *            The nbTelExc to set
	 */
	public void setNbTelExc(String nbTelExc)
	{
		this.nbTelExc = nbTelExc;
	}

	/**
	 * Sets the nbTelExt.
	 * 
	 * @param nbTelExt
	 *            The nbTelExt to set
	 */
	public void setNbTelExt(String nbTelExt)
	{
		this.nbTelExt = nbTelExt;
	}

	/**
	 * Sets the nbTelLn.
	 * 
	 * @param nbTelLn
	 *            The nbTelLn to set
	 */
	public void setNbTelLn(String nbTelLn)
	{
		this.nbTelLn = nbTelLn;
	}

	/**
	 * Sets the nmAuthor.
	 * 
	 * @param nmAuthor
	 *            The nmAuthor to set
	 */
	public void setNmAuthor(String nmAuthor)
	{
		this.nmAuthor = nmAuthor;
	}

	/**
	 * Sets the nmCustParFirst.
	 * 
	 * @param nmCustParFirst
	 *            The nmCustParFirst to set
	 */
	public void setNmCustParFirst(String nmCustParFirst)
	{
		this.nmCustParFirst = nmCustParFirst;
	}

	/**
	 * Sets the nmCustParLast.
	 * 
	 * @param nmCustParLast
	 *            The nmCustParLast to set
	 */
	public void setNmCustParLast(String nmCustParLast)
	{
		this.nmCustParLast = nmCustParLast;
	}

	/**
	 * Sets the nmCustParMi.
	 * 
	 * @param nmCustParMi
	 *            The nmCustParMi to set
	 */
	public void setNmCustParMi(String nmCustParMi)
	{
		this.nmCustParMi = nmCustParMi;
	}

	/**
	 * Sets the nmNonCustParFirst.
	 * 
	 * @param nmNonCustParFirst
	 *            The nmNonCustParFirst to set
	 */
	public void setNmNonCustParFirst(String nmNonCustParFirst)
	{
		this.nmNonCustParFirst = nmNonCustParFirst;
	}

	/**
	 * Sets the nmNonCustParLast.
	 * 
	 * @param nmNonCustParLast
	 *            The nmNonCustParLast to set
	 */
	public void setNmNonCustParLast(String nmNonCustParLast)
	{
		this.nmNonCustParLast = nmNonCustParLast;
	}

	/**
	 * Sets the nmNonCustParMi.
	 * 
	 * @param nmNonCustParMi
	 *            The nmNonCustParMi to set
	 */
	public void setNmNonCustParMi(String nmNonCustParMi)
	{
		this.nmNonCustParMi = nmNonCustParMi;
	}

	/**
	 * Sets the nmRefSource1.
	 * 
	 * @param nmRefSource1
	 *            The nmRefSource1 to set
	 */
	public void setNmRefSource1(String nmRefSource1)
	{
		this.nmRefSource1 = nmRefSource1;
	}

	/**
	 * Sets the nmRefSource2.
	 * 
	 * @param nmRefSource2
	 *            The nmRefSource2 to set
	 */
	public void setNmRefSource2(String nmRefSource2)
	{
		this.nmRefSource2 = nmRefSource2;
	}

	/**
	 * Sets the nmRefSource3.
	 * 
	 * @param nmRefSource3
	 *            The nmRefSource3 to set
	 */
	public void setNmRefSource3(String nmRefSource3)
	{
		this.nmRefSource3 = nmRefSource3;
	}

	/**
	 * Sets the ntResolution.
	 * 
	 * @param ntResolution
	 *            The ntResolution to set
	 */
	public void setNtResolution(String ntResolution)
	{
		this.ntResolution = ntResolution;
	}

	/**
	 * Returns the cdStatus.
	 * 
	 * @return String
	 */
	public String getCdStatus()
	{
		return cdStatus;
	}

	/**
	 * Sets the cdStatus.
	 * 
	 * @param cdStatus
	 *            The cdStatus to set
	 */
	public void setCdStatus(String cdStatus)
	{
		this.cdStatus = cdStatus;
	}

	/**
	 * Returns the selfAssigned.
	 * 
	 * @return boolean
	 */
	public boolean isSelfAssigned()
	{
		return selfAssigned;
	}

	/**
	 * Sets the selfAssigned.
	 * 
	 * @param selfAssigned
	 *            The selfAssigned to set
	 */
	public void setSelfAssigned(boolean selfAssigned)
	{
		this.selfAssigned = selfAssigned;
	}

	/**
	 * Returns the nmAuthorFirst.
	 * 
	 * @return String
	 */
	public String getNmAuthorFirst()
	{
		return nmAuthorFirst;
	}

	/**
	 * Returns the nmAuthorLast.
	 * 
	 * @return String
	 */
	public String getNmAuthorLast()
	{
		return nmAuthorLast;
	}

	/**
	 * Returns the nmAuthorMi.
	 * 
	 * @return String
	 */
	public String getNmAuthorMi()
	{
		return nmAuthorMi;
	}

	/**
	 * Sets the nmAuthorFirst.
	 * 
	 * @param nmAuthorFirst
	 *            The nmAuthorFirst to set
	 */
	public void setNmAuthorFirst(String nmAuthorFirst)
	{
		this.nmAuthorFirst = nmAuthorFirst;
	}

	/**
	 * Sets the nmAuthorLast.
	 * 
	 * @param nmAuthorLast
	 *            The nmAuthorLast to set
	 */
	public void setNmAuthorLast(String nmAuthorLast)
	{
		this.nmAuthorLast = nmAuthorLast;
	}

	/**
	 * Sets the nmAuthorMi.
	 * 
	 * @param nmAuthorMi
	 *            The nmAuthorMi to set
	 */
	public void setNmAuthorMi(String nmAuthorMi)
	{
		this.nmAuthorMi = nmAuthorMi;
	}

	/**
	 * Returns the approvalRequired.
	 * 
	 * @return boolean
	 */
	public boolean isApprovalRequired()
	{
		return approvalRequired;
	}

	/**
	 * Sets the approvalRequired.
	 * 
	 * @param approvalRequired
	 *            The approvalRequired to set
	 */
	public void setApprovalRequired(boolean approvalRequired)
	{
		this.approvalRequired = approvalRequired;
	}

	/**
	 * Returns the controlNumber.
	 * 
	 * @return String
	 */
	public String getControlNumber()
	{
		return controlNumber;
	}

	/**
	 * Returns the idEmailConfirm.
	 * 
	 * @return String
	 */
	public String getIdEmailConfirm()
	{
		return idEmailConfirm;
	}

	/**
	 * Returns the nmCounty.
	 * 
	 * @return String
	 */
	public String getNmCounty()
	{
		return nmCounty;
	}

	/**
	 * Returns the nmRefSource4.
	 * 
	 * @return String
	 */
	public String getNmRefSource4()
	{
		return nmRefSource4;
	}

	/**
	 * Returns the nmWorker.
	 * 
	 * @return String
	 */
	public String getNmWorker()
	{
		return nmWorker;
	}

	/**
	 * Sets the controlNumber.
	 * 
	 * @param controlNumber
	 *            The controlNumber to set
	 */
	public void setControlNumber(String controlNumber)
	{
		this.controlNumber = controlNumber;
	}

	/**
	 * Sets the idEmailConfirm.
	 * 
	 * @param idEmailConfirm
	 *            The idEmailConfirm to set
	 */
	public void setIdEmailConfirm(String idEmailConfirm)
	{
		this.idEmailConfirm = idEmailConfirm;
	}

	/**
	 * Sets the nmCounty.
	 * 
	 * @param nmCounty
	 *            The nmCounty to set
	 */
	public void setNmCounty(String nmCounty)
	{
		this.nmCounty = nmCounty;
	}

	/**
	 * Sets the nmRefSource4.
	 * 
	 * @param nmRefSource4
	 *            The nmRefSource4 to set
	 */
	public void setNmRefSource4(String nmRefSource4)
	{
		this.nmRefSource4 = nmRefSource4;
	}

	/**
	 * Sets the nmWorker.
	 * 
	 * @param nmWorker
	 *            The nmWorker to set
	 */
	public void setNmWorker(String nmWorker)
	{
		this.nmWorker = nmWorker;
	}

	/**
	 * Returns the idStaff1.
	 * 
	 * @return String
	 */
	public String getIdStaff1()
	{
		return idStaff1;
	}

	/**
	 * Returns the idStaff2.
	 * 
	 * @return String
	 */
	public String getIdStaff2()
	{
		return idStaff2;
	}

	/**
	 * Returns the idStaff3.
	 * 
	 * @return String
	 */
	public String getIdStaff3()
	{
		return idStaff3;
	}

	/**
	 * Returns the idStaff4.
	 * 
	 * @return String
	 */
	public String getIdStaff4()
	{
		return idStaff4;
	}

	/**
	 * Sets the idStaff1.
	 * 
	 * @param idStaff1
	 *            The idStaff1 to set
	 */
	public void setIdStaff1(String idStaff1)
	{
		this.idStaff1 = idStaff1;
	}

	/**
	 * Sets the idStaff2.
	 * 
	 * @param idStaff2
	 *            The idStaff2 to set
	 */
	public void setIdStaff2(String idStaff2)
	{
		this.idStaff2 = idStaff2;
	}

	/**
	 * Sets the idStaff3.
	 * 
	 * @param idStaff3
	 *            The idStaff3 to set
	 */
	public void setIdStaff3(String idStaff3)
	{
		this.idStaff3 = idStaff3;
	}

	/**
	 * Sets the idStaff4.
	 * 
	 * @param idStaff4
	 *            The idStaff4 to set
	 */
	public void setIdStaff4(String idStaff4)
	{
		this.idStaff4 = idStaff4;
	}

	/**
	 * Returns the processStatus.
	 * 
	 * @return String
	 */
	public String getProcessStatus()
	{
		return processStatus;
	}

	/**
	 * Sets the processStatus.
	 * 
	 * @param processStatus
	 *            The processStatus to set
	 */
	public void setProcessStatus(String processStatus)
	{
		this.processStatus = processStatus;
	}

	/**
	 * Returns the agentSearchOverride.
	 * 
	 * @return boolean
	 */
	public boolean isAgentSearchOverride()
	{
		return agentSearchOverride;
	}

	/**
	 * Sets the agentSearchOverride.
	 * 
	 * @param agentSearchOverride
	 *            The agentSearchOverride to set
	 */
	public void setAgentSearchOverride(boolean agentSearchOverride)
	{
		this.agentSearchOverride = agentSearchOverride;
	}

	/**
	 * Returns the owner.
	 * 
	 * @return boolean
	 */
	public boolean isOwner()
	{
		return owner;
	}

	/**
	 * Sets the owner.
	 * 
	 * @param owner
	 *            The owner to set
	 */
	public void setOwner(boolean owner)
	{
		this.owner = owner;
	}

}
