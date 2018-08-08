

package org.dhhs.dirm.acts.cs.beans;

/**
 * UserBean.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Aug 19, 2003 2:37:25 PM
 * 
 * @author Rkodumagulla
 *
 */
public class UserBean
{

	private String	idWorker				= null;
	private String	cdAccptWrkld			= null;
	private boolean	approvalRequired		= false;
	private String	nmWorker				= null;
	private String	idEmail					= null;
	private int		nbrOutstanding			= 0;
	private int		nbrCompleted			= 0;
	private int		nbrApproval				= 0;
	private String	idProfile				= null;
	private String	profileDesc				= null;
	private int		pctAllocation			= 0;
	private int		pctOutstanding			= 0;
	private int		pctPending				= 0;
	private boolean	selection				= false;

	private boolean	manageAll				= false;
	private boolean	manageUsers				= false;
	private boolean	manageProfiles			= false;
	private boolean	manageWorkFlow			= false;
	private boolean	manageWorkLoad			= false;
	private boolean	manageApprovals			= false;
	private boolean	manageReferralSources	= false;
	private boolean	manageReports			= false;

	private boolean	byDtReceived;

	private boolean	byDtCompleted;

	private boolean	byDtDue;

	private boolean	byNbCase;

	private boolean	byIdPart;

	private boolean	byNbSSN;

	private boolean	byAgent;

	private boolean	byReferralType;

	private boolean	byEmail;

	private boolean	byCP;

	private boolean	byNCP;

	private boolean	byControlNbr;

	private boolean	byCustomer;

	private boolean	byNbDkt;

	private boolean	bySrc1;

	private boolean	bySrc2;

	private boolean	bySrc3;

	private boolean	bySrc4;

	private boolean	byCounty;

	/**
	 * Constructor for UserBean.
	 */
	public UserBean()
	{
		super();
	}

	/**
	 * Returns the idEmail.
	 * 
	 * @return String
	 */
	public String getIdEmail()
	{
		return this.idEmail;
	}

	/**
	 * Returns the idProfile.
	 * 
	 * @return String
	 */
	public String getIdProfile()
	{
		return this.idProfile;
	}

	/**
	 * Returns the idWorker.
	 * 
	 * @return String
	 */
	public String getIdWorker()
	{
		return this.idWorker;
	}

	/**
	 * Returns the nmWorker.
	 * 
	 * @return String
	 */
	public String getNmWorker()
	{
		return this.nmWorker;
	}

	/**
	 * Returns the profileDesc.
	 * 
	 * @return String
	 */
	public String getProfileDesc()
	{
		return this.profileDesc;
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
	 * Sets the idProfile.
	 * 
	 * @param idProfile
	 *            The idProfile to set
	 */
	public void setIdProfile(String idProfile)
	{
		this.idProfile = idProfile;
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
	 * Sets the profileDesc.
	 * 
	 * @param profileDesc
	 *            The profileDesc to set
	 */
	public void setProfileDesc(String profileDesc)
	{
		this.profileDesc = profileDesc;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "UserBean: " + this.idWorker + " " + this.cdAccptWrkld + " " + this.nmWorker + " " + this.idProfile;
	}

	/**
	 * Returns the cdAccptWrkld.
	 * 
	 * @return String
	 */
	public String getCdAccptWrkld()
	{
		return this.cdAccptWrkld;
	}

	/**
	 * Returns the nbrApproval.
	 * 
	 * @return int
	 */
	public int getNbrApproval()
	{
		return this.nbrApproval;
	}

	/**
	 * Returns the nbrCompleted.
	 * 
	 * @return int
	 */
	public int getNbrCompleted()
	{
		return this.nbrCompleted;
	}

	/**
	 * Returns the nbrOutstanding.
	 * 
	 * @return int
	 */
	public int getNbrOutstanding()
	{
		return this.nbrOutstanding;
	}

	/**
	 * Sets the cdAccptWrkld.
	 * 
	 * @param cdAccptWrkld
	 *            The cdAccptWrkld to set
	 */
	public void setCdAccptWrkld(String cdAccptWrkld)
	{
		this.cdAccptWrkld = cdAccptWrkld;
	}

	/**
	 * Sets the nbrApproval.
	 * 
	 * @param nbrApproval
	 *            The nbrApproval to set
	 */
	public void setNbrApproval(int nbrApproval)
	{
		this.nbrApproval = nbrApproval;
	}

	/**
	 * Sets the nbrCompleted.
	 * 
	 * @param nbrCompleted
	 *            The nbrCompleted to set
	 */
	public void setNbrCompleted(int nbrCompleted)
	{
		this.nbrCompleted = nbrCompleted;
	}

	/**
	 * Sets the nbrOutstanding.
	 * 
	 * @param nbrOutstanding
	 *            The nbrOutstanding to set
	 */
	public void setNbrOutstanding(int nbrOutstanding)
	{
		this.nbrOutstanding = nbrOutstanding;
	}

	/**
	 * Returns the pctAllocation.
	 * 
	 * @return int
	 */
	public int getPctAllocation()
	{
		return this.pctAllocation;
	}

	/**
	 * Sets the pctAllocation.
	 * 
	 * @param pctAllocation
	 *            The pctAllocation to set
	 */
	public void setPctAllocation(int pctAllocation)
	{
		this.pctAllocation = pctAllocation;
	}

	/**
	 * Returns the selection.
	 * 
	 * @return boolean
	 */
	public boolean isSelection()
	{
		return this.selection;
	}

	/**
	 * Sets the selection.
	 * 
	 * @param selection
	 *            The selection to set
	 */
	public void setSelection(boolean selection)
	{
		this.selection = selection;
	}

	/**
	 * Returns the pctOutstanding.
	 * 
	 * @return int
	 */
	public int getPctOutstanding()
	{
		return this.pctOutstanding;
	}

	/**
	 * Returns the pctPending.
	 * 
	 * @return int
	 */
	public int getPctPending()
	{
		return this.pctPending;
	}

	/**
	 * Sets the pctOutstanding.
	 * 
	 * @param pctOutstanding
	 *            The pctOutstanding to set
	 */
	public void setPctOutstanding(int pctOutstanding)
	{
		this.pctOutstanding = pctOutstanding;
	}

	/**
	 * Sets the pctPending.
	 * 
	 * @param pctPending
	 *            The pctPending to set
	 */
	public void setPctPending(int pctPending)
	{
		this.pctPending = pctPending;
	}

	/**
	 * Returns the manageAll.
	 * 
	 * @return boolean
	 */
	public boolean isManageAll()
	{
		return this.manageAll;
	}

	/**
	 * Returns the manageApprovals.
	 * 
	 * @return boolean
	 */
	public boolean isManageApprovals()
	{
		return this.manageApprovals;
	}

	/**
	 * Returns the manageProfiles.
	 * 
	 * @return boolean
	 */
	public boolean isManageProfiles()
	{
		return this.manageProfiles;
	}

	/**
	 * Returns the manageReferralSources.
	 * 
	 * @return boolean
	 */
	public boolean isManageReferralSources()
	{
		return this.manageReferralSources;
	}

	/**
	 * Returns the manageReports.
	 * 
	 * @return boolean
	 */
	public boolean isManageReports()
	{
		return this.manageReports;
	}

	/**
	 * Returns the manageUsers.
	 * 
	 * @return boolean
	 */
	public boolean isManageUsers()
	{
		return this.manageUsers;
	}

	/**
	 * Returns the manageWorkFlow.
	 * 
	 * @return boolean
	 */
	public boolean isManageWorkFlow()
	{
		return this.manageWorkFlow;
	}

	/**
	 * Returns the manageWorkLoad.
	 * 
	 * @return boolean
	 */
	public boolean isManageWorkLoad()
	{
		return this.manageWorkLoad;
	}

	/**
	 * Sets the manageAll.
	 * 
	 * @param manageAll
	 *            The manageAll to set
	 */
	public void setManageAll(boolean manageAll)
	{
		this.manageAll = manageAll;
	}

	/**
	 * Sets the manageApprovals.
	 * 
	 * @param manageApprovals
	 *            The manageApprovals to set
	 */
	public void setManageApprovals(boolean manageApprovals)
	{
		this.manageApprovals = manageApprovals;
	}

	/**
	 * Sets the manageProfiles.
	 * 
	 * @param manageProfiles
	 *            The manageProfiles to set
	 */
	public void setManageProfiles(boolean manageProfiles)
	{
		this.manageProfiles = manageProfiles;
	}

	/**
	 * Sets the manageReferralSources.
	 * 
	 * @param manageReferralSources
	 *            The manageReferralSources to set
	 */
	public void setManageReferralSources(boolean manageReferralSources)
	{
		this.manageReferralSources = manageReferralSources;
	}

	/**
	 * Sets the manageReports.
	 * 
	 * @param manageReports
	 *            The manageReports to set
	 */
	public void setManageReports(boolean manageReports)
	{
		this.manageReports = manageReports;
	}

	/**
	 * Sets the manageUsers.
	 * 
	 * @param manageUsers
	 *            The manageUsers to set
	 */
	public void setManageUsers(boolean manageUsers)
	{
		this.manageUsers = manageUsers;
	}

	/**
	 * Sets the manageWorkFlow.
	 * 
	 * @param manageWorkFlow
	 *            The manageWorkFlow to set
	 */
	public void setManageWorkFlow(boolean manageWorkFlow)
	{
		this.manageWorkFlow = manageWorkFlow;
	}

	/**
	 * Sets the manageWorkLoad.
	 * 
	 * @param manageWorkLoad
	 *            The manageWorkLoad to set
	 */
	public void setManageWorkLoad(boolean manageWorkLoad)
	{
		this.manageWorkLoad = manageWorkLoad;
	}

	/**
	 * Returns the byAgent.
	 * 
	 * @return boolean
	 */
	public boolean isByAgent()
	{
		return this.byAgent;
	}

	/**
	 * Returns the byControlNbr.
	 * 
	 * @return boolean
	 */
	public boolean isByControlNbr()
	{
		return this.byControlNbr;
	}

	/**
	 * Returns the byCounty.
	 * 
	 * @return boolean
	 */
	public boolean isByCounty()
	{
		return this.byCounty;
	}

	/**
	 * Returns the byCP.
	 * 
	 * @return boolean
	 */
	public boolean isByCP()
	{
		return this.byCP;
	}

	/**
	 * Returns the byCustomer.
	 * 
	 * @return boolean
	 */
	public boolean isByCustomer()
	{
		return this.byCustomer;
	}

	/**
	 * Returns the byDtCompleted.
	 * 
	 * @return boolean
	 */
	public boolean isByDtCompleted()
	{
		return this.byDtCompleted;
	}

	/**
	 * Returns the byDtDue.
	 * 
	 * @return boolean
	 */
	public boolean isByDtDue()
	{
		return this.byDtDue;
	}

	/**
	 * Returns the byDtReceived.
	 * 
	 * @return boolean
	 */
	public boolean isByDtReceived()
	{
		return this.byDtReceived;
	}

	/**
	 * Returns the byEmail.
	 * 
	 * @return boolean
	 */
	public boolean isByEmail()
	{
		return this.byEmail;
	}

	/**
	 * Returns the byIdPart.
	 * 
	 * @return boolean
	 */
	public boolean isByIdPart()
	{
		return this.byIdPart;
	}

	/**
	 * Returns the byNbCase.
	 * 
	 * @return boolean
	 */
	public boolean isByNbCase()
	{
		return this.byNbCase;
	}

	/**
	 * Returns the byNbDkt.
	 * 
	 * @return boolean
	 */
	public boolean isByNbDkt()
	{
		return this.byNbDkt;
	}

	/**
	 * Returns the byNbSSN.
	 * 
	 * @return boolean
	 */
	public boolean isByNbSSN()
	{
		return this.byNbSSN;
	}

	/**
	 * Returns the byNCP.
	 * 
	 * @return boolean
	 */
	public boolean isByNCP()
	{
		return this.byNCP;
	}

	/**
	 * Returns the byReferralType.
	 * 
	 * @return boolean
	 */
	public boolean isByReferralType()
	{
		return this.byReferralType;
	}

	/**
	 * Returns the bySrc1.
	 * 
	 * @return boolean
	 */
	public boolean isBySrc1()
	{
		return this.bySrc1;
	}

	/**
	 * Returns the bySrc2.
	 * 
	 * @return boolean
	 */
	public boolean isBySrc2()
	{
		return this.bySrc2;
	}

	/**
	 * Returns the bySrc3.
	 * 
	 * @return boolean
	 */
	public boolean isBySrc3()
	{
		return this.bySrc3;
	}

	/**
	 * Returns the bySrc4.
	 * 
	 * @return boolean
	 */
	public boolean isBySrc4()
	{
		return this.bySrc4;
	}

	/**
	 * Sets the byAgent.
	 * 
	 * @param byAgent
	 *            The byAgent to set
	 */
	public void setByAgent(boolean byAgent)
	{
		this.byAgent = byAgent;
	}

	/**
	 * Sets the byControlNbr.
	 * 
	 * @param byControlNbr
	 *            The byControlNbr to set
	 */
	public void setByControlNbr(boolean byControlNbr)
	{
		this.byControlNbr = byControlNbr;
	}

	/**
	 * Sets the byCounty.
	 * 
	 * @param byCounty
	 *            The byCounty to set
	 */
	public void setByCounty(boolean byCounty)
	{
		this.byCounty = byCounty;
	}

	/**
	 * Sets the byCP.
	 * 
	 * @param byCP
	 *            The byCP to set
	 */
	public void setByCP(boolean byCP)
	{
		this.byCP = byCP;
	}

	/**
	 * Sets the byCustomer.
	 * 
	 * @param byCustomer
	 *            The byCustomer to set
	 */
	public void setByCustomer(boolean byCustomer)
	{
		this.byCustomer = byCustomer;
	}

	/**
	 * Sets the byDtCompleted.
	 * 
	 * @param byDtCompleted
	 *            The byDtCompleted to set
	 */
	public void setByDtCompleted(boolean byDtCompleted)
	{
		this.byDtCompleted = byDtCompleted;
	}

	/**
	 * Sets the byDtDue.
	 * 
	 * @param byDtDue
	 *            The byDtDue to set
	 */
	public void setByDtDue(boolean byDtDue)
	{
		this.byDtDue = byDtDue;
	}

	/**
	 * Sets the byDtReceived.
	 * 
	 * @param byDtReceived
	 *            The byDtReceived to set
	 */
	public void setByDtReceived(boolean byDtReceived)
	{
		this.byDtReceived = byDtReceived;
	}

	/**
	 * Sets the byEmail.
	 * 
	 * @param byEmail
	 *            The byEmail to set
	 */
	public void setByEmail(boolean byEmail)
	{
		this.byEmail = byEmail;
	}

	/**
	 * Sets the byIdPart.
	 * 
	 * @param byIdPart
	 *            The byIdPart to set
	 */
	public void setByIdPart(boolean byIdPart)
	{
		this.byIdPart = byIdPart;
	}

	/**
	 * Sets the byNbCase.
	 * 
	 * @param byNbCase
	 *            The byNbCase to set
	 */
	public void setByNbCase(boolean byNbCase)
	{
		this.byNbCase = byNbCase;
	}

	/**
	 * Sets the byNbDkt.
	 * 
	 * @param byNbDkt
	 *            The byNbDkt to set
	 */
	public void setByNbDkt(boolean byNbDkt)
	{
		this.byNbDkt = byNbDkt;
	}

	/**
	 * Sets the byNbSSN.
	 * 
	 * @param byNbSSN
	 *            The byNbSSN to set
	 */
	public void setByNbSSN(boolean byNbSSN)
	{
		this.byNbSSN = byNbSSN;
	}

	/**
	 * Sets the byNCP.
	 * 
	 * @param byNCP
	 *            The byNCP to set
	 */
	public void setByNCP(boolean byNCP)
	{
		this.byNCP = byNCP;
	}

	/**
	 * Sets the byReferralType.
	 * 
	 * @param byReferralType
	 *            The byReferralType to set
	 */
	public void setByReferralType(boolean byReferralType)
	{
		this.byReferralType = byReferralType;
	}

	/**
	 * Sets the bySrc1.
	 * 
	 * @param bySrc1
	 *            The bySrc1 to set
	 */
	public void setBySrc1(boolean bySrc1)
	{
		this.bySrc1 = bySrc1;
	}

	/**
	 * Sets the bySrc2.
	 * 
	 * @param bySrc2
	 *            The bySrc2 to set
	 */
	public void setBySrc2(boolean bySrc2)
	{
		this.bySrc2 = bySrc2;
	}

	/**
	 * Sets the bySrc3.
	 * 
	 * @param bySrc3
	 *            The bySrc3 to set
	 */
	public void setBySrc3(boolean bySrc3)
	{
		this.bySrc3 = bySrc3;
	}

	/**
	 * Sets the bySrc4.
	 * 
	 * @param bySrc4
	 *            The bySrc4 to set
	 */
	public void setBySrc4(boolean bySrc4)
	{
		this.bySrc4 = bySrc4;
	}

	/**
	 * Returns the approvalRequired.
	 * 
	 * @return boolean
	 */
	public boolean isApprovalRequired()
	{
		return this.approvalRequired;
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

}
