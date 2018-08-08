

package org.dhhs.dirm.acts.cs.beans;

/**
 * Profile.java
 * 
 * Property of State of North Carolina DHHS. Developed and Maintained by SYSTEMS
 * RESEARCH & DEVELOPMENT INC.,
 * 
 * Creation Date: Oct 8, 2003 9:30:57 AM
 * 
 * @author rkodumagulla
 *
 */
public class Profile
{

	private String	idProfile		= "";

	private String	idProfileDesc	= "";

	private String	tsCreate;

	private String	tsUpdate;

	private String	idWrkrCreate;

	private String	idWrkrUpdate;

	private String	cdMenuItems;

	/**
	 * Constructor for Profile.
	 */
	public Profile()
	{
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
	 * Returns the idProfileDesc.
	 * 
	 * @return String
	 */
	public String getIdProfileDesc()
	{
		return this.idProfileDesc;
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
	 * Sets the idProfileDesc.
	 * 
	 * @param idProfileDesc
	 *            The idProfileDesc to set
	 */
	public void setIdProfileDesc(String idProfileDesc)
	{
		this.idProfileDesc = idProfileDesc;
	}

	/**
	 * Returns the idWrkrCreate.
	 * 
	 * @return String
	 */
	public String getIdWrkrCreate()
	{
		return this.idWrkrCreate;
	}

	/**
	 * Returns the idWrkrUpdate.
	 * 
	 * @return String
	 */
	public String getIdWrkrUpdate()
	{
		return this.idWrkrUpdate;
	}

	/**
	 * Returns the tsCreate.
	 * 
	 * @return String
	 */
	public String getTsCreate()
	{
		return this.tsCreate;
	}

	/**
	 * Returns the tsUpdate.
	 * 
	 * @return String
	 */
	public String getTsUpdate()
	{
		return this.tsUpdate;
	}

	/**
	 * Sets the idWrkrCreate.
	 * 
	 * @param idWrkrCreate
	 *            The idWrkrCreate to set
	 */
	public void setIdWrkrCreate(String idWrkrCreate)
	{
		this.idWrkrCreate = idWrkrCreate;
	}

	/**
	 * Sets the idWrkrUpdate.
	 * 
	 * @param idWrkrUpdate
	 *            The idWrkrUpdate to set
	 */
	public void setIdWrkrUpdate(String idWrkrUpdate)
	{
		this.idWrkrUpdate = idWrkrUpdate;
	}

	/**
	 * Sets the tsCreate.
	 * 
	 * @param tsCreate
	 *            The tsCreate to set
	 */
	public void setTsCreate(String tsCreate)
	{
		this.tsCreate = tsCreate;
	}

	/**
	 * Sets the tsUpdate.
	 * 
	 * @param tsUpdate
	 *            The tsUpdate to set
	 */
	public void setTsUpdate(String tsUpdate)
	{
		this.tsUpdate = tsUpdate;
	}

	/**
	 * Returns the cdMenuItems.
	 * 
	 * @return String
	 */
	public String getCdMenuItems()
	{
		return this.cdMenuItems;
	}

	/**
	 * Sets the cdMenuItems.
	 * 
	 * @param cdMenuItems
	 *            The cdMenuItems to set
	 */
	public void setCdMenuItems(String cdMenuItems)
	{
		this.cdMenuItems = cdMenuItems;
	}

}
