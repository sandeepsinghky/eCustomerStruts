

package org.dhhs.dirm.acts.cs.formbeans;

import org.apache.struts.action.ActionForm;

/**
 * PreferencesFormBean.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Nov 13, 2003 3:26:48 PM
 * 
 * @author rkodumagulla
 *
 */
public class PreferencesFormBean extends ActionForm
{

	private String	idWorker;

	private boolean	agent;

	private boolean	dtReceived;

	private boolean	dtDue;

	private boolean	dtCompleted;

	private boolean	nbCase;

	private boolean	idPart;

	private boolean	nbSSN;

	private boolean	cdFormType;

	private boolean	idEmail;

	private boolean	nbDocket;

	private boolean	custodial;

	private boolean	nonCustodial;

	private boolean	nbControl;

	private boolean	writer;

	private boolean	source1;

	private boolean	source2;

	private boolean	source3;

	private boolean	source4;

	private boolean	nmCounty;

	/**
	 * Constructor for PreferencesFormBean.
	 */
	public PreferencesFormBean()
	{
		super();
	}

	/**
	 * Returns the cdFormType.
	 * 
	 * @return boolean
	 */
	public boolean isCdFormType()
	{
		return cdFormType;
	}

	/**
	 * Returns the dtCompleted.
	 * 
	 * @return boolean
	 */
	public boolean isDtCompleted()
	{
		return dtCompleted;
	}

	/**
	 * Returns the dtDue.
	 * 
	 * @return boolean
	 */
	public boolean isDtDue()
	{
		return dtDue;
	}

	/**
	 * Returns the dtReceived.
	 * 
	 * @return boolean
	 */
	public boolean isDtReceived()
	{
		return dtReceived;
	}

	/**
	 * Returns the idEmail.
	 * 
	 * @return boolean
	 */
	public boolean isIdEmail()
	{
		return idEmail;
	}

	/**
	 * Returns the idPart.
	 * 
	 * @return boolean
	 */
	public boolean isIdPart()
	{
		return idPart;
	}

	/**
	 * Returns the nbCase.
	 * 
	 * @return boolean
	 */
	public boolean isNbCase()
	{
		return nbCase;
	}

	/**
	 * Returns the nbDocket.
	 * 
	 * @return boolean
	 */
	public boolean isNbDocket()
	{
		return nbDocket;
	}

	/**
	 * Sets the cdFormType.
	 * 
	 * @param cdFormType
	 *            The cdFormType to set
	 */
	public void setCdFormType(boolean cdFormType)
	{
		this.cdFormType = cdFormType;
	}

	/**
	 * Sets the dtCompleted.
	 * 
	 * @param dtCompleted
	 *            The dtCompleted to set
	 */
	public void setDtCompleted(boolean dtCompleted)
	{
		this.dtCompleted = dtCompleted;
	}

	/**
	 * Sets the dtDue.
	 * 
	 * @param dtDue
	 *            The dtDue to set
	 */
	public void setDtDue(boolean dtDue)
	{
		this.dtDue = dtDue;
	}

	/**
	 * Sets the dtReceived.
	 * 
	 * @param dtReceived
	 *            The dtReceived to set
	 */
	public void setDtReceived(boolean dtReceived)
	{
		this.dtReceived = dtReceived;
	}

	/**
	 * Sets the idEmail.
	 * 
	 * @param idEmail
	 *            The idEmail to set
	 */
	public void setIdEmail(boolean idEmail)
	{
		this.idEmail = idEmail;
	}

	/**
	 * Sets the idPart.
	 * 
	 * @param idPart
	 *            The idPart to set
	 */
	public void setIdPart(boolean idPart)
	{
		this.idPart = idPart;
	}

	/**
	 * Sets the nbCase.
	 * 
	 * @param nbCase
	 *            The nbCase to set
	 */
	public void setNbCase(boolean nbCase)
	{
		this.nbCase = nbCase;
	}

	/**
	 * Sets the nbDocket.
	 * 
	 * @param nbDocket
	 *            The nbDocket to set
	 */
	public void setNbDocket(boolean nbDocket)
	{
		this.nbDocket = nbDocket;
	}

	/**
	 * Returns the custodial.
	 * 
	 * @return boolean
	 */
	public boolean isCustodial()
	{
		return custodial;
	}

	/**
	 * Returns the nbControl.
	 * 
	 * @return boolean
	 */
	public boolean isNbControl()
	{
		return nbControl;
	}

	/**
	 * Returns the nbSSN.
	 * 
	 * @return boolean
	 */
	public boolean isNbSSN()
	{
		return nbSSN;
	}

	/**
	 * Returns the nmCounty.
	 * 
	 * @return boolean
	 */
	public boolean isNmCounty()
	{
		return nmCounty;
	}

	/**
	 * Returns the nonCustodial.
	 * 
	 * @return boolean
	 */
	public boolean isNonCustodial()
	{
		return nonCustodial;
	}

	/**
	 * Returns the source1.
	 * 
	 * @return boolean
	 */
	public boolean isSource1()
	{
		return source1;
	}

	/**
	 * Returns the source2.
	 * 
	 * @return boolean
	 */
	public boolean isSource2()
	{
		return source2;
	}

	/**
	 * Returns the source3.
	 * 
	 * @return boolean
	 */
	public boolean isSource3()
	{
		return source3;
	}

	/**
	 * Returns the source4.
	 * 
	 * @return boolean
	 */
	public boolean isSource4()
	{
		return source4;
	}

	/**
	 * Returns the writer.
	 * 
	 * @return boolean
	 */
	public boolean isWriter()
	{
		return writer;
	}

	/**
	 * Sets the custodial.
	 * 
	 * @param custodial
	 *            The custodial to set
	 */
	public void setCustodial(boolean custodial)
	{
		this.custodial = custodial;
	}

	/**
	 * Sets the nbControl.
	 * 
	 * @param nbControl
	 *            The nbControl to set
	 */
	public void setNbControl(boolean nbControl)
	{
		this.nbControl = nbControl;
	}

	/**
	 * Sets the nbSSN.
	 * 
	 * @param nbSSN
	 *            The nbSSN to set
	 */
	public void setNbSSN(boolean nbSSN)
	{
		this.nbSSN = nbSSN;
	}

	/**
	 * Sets the nmCounty.
	 * 
	 * @param nmCounty
	 *            The nmCounty to set
	 */
	public void setNmCounty(boolean nmCounty)
	{
		this.nmCounty = nmCounty;
	}

	/**
	 * Sets the nonCustodial.
	 * 
	 * @param nonCustodial
	 *            The nonCustodial to set
	 */
	public void setNonCustodial(boolean nonCustodial)
	{
		this.nonCustodial = nonCustodial;
	}

	/**
	 * Sets the source1.
	 * 
	 * @param source1
	 *            The source1 to set
	 */
	public void setSource1(boolean source1)
	{
		this.source1 = source1;
	}

	/**
	 * Sets the source2.
	 * 
	 * @param source2
	 *            The source2 to set
	 */
	public void setSource2(boolean source2)
	{
		this.source2 = source2;
	}

	/**
	 * Sets the source3.
	 * 
	 * @param source3
	 *            The source3 to set
	 */
	public void setSource3(boolean source3)
	{
		this.source3 = source3;
	}

	/**
	 * Sets the source4.
	 * 
	 * @param source4
	 *            The source4 to set
	 */
	public void setSource4(boolean source4)
	{
		this.source4 = source4;
	}

	/**
	 * Sets the writer.
	 * 
	 * @param writer
	 *            The writer to set
	 */
	public void setWriter(boolean writer)
	{
		this.writer = writer;
	}

	/**
	 * Returns the agent.
	 * 
	 * @return boolean
	 */
	public boolean isAgent()
	{
		return agent;
	}

	/**
	 * Sets the agent.
	 * 
	 * @param agent
	 *            The agent to set
	 */
	public void setAgent(boolean agent)
	{
		this.agent = agent;
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
	 * Sets the idWorker.
	 * 
	 * @param idWorker
	 *            The idWorker to set
	 */
	public void setIdWorker(String idWorker)
	{
		this.idWorker = idWorker;
	}

}
