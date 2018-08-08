package org.dhhs.dirm.acts.cs.ejb;
/**
 * Key class for Entity Bean: User
 */
public class UserKey implements java.io.Serializable {
	/**
	 * Implementation field for persistent attribute: idWrkr
	 */
	public java.lang.String idWrkr;
	/**
	 * Creates a key for Entity Bean: User
	 */
	public UserKey(java.lang.String idWrkr) {
		this.idWrkr = idWrkr;
	}
	/**
	 * Returns true if both keys are equal.
	 */
	public boolean equals(java.lang.Object otherKey) {
		if (otherKey instanceof org.dhhs.dirm.acts.cs.ejb.UserKey) {
			org.dhhs.dirm.acts.cs.ejb.UserKey o = (org.dhhs.dirm.acts.cs.ejb.UserKey) otherKey;
			return ((this.idWrkr.equals(o.idWrkr)));
		}
		return false;
	}
	/**
	 * Returns the hash code for the key.
	 */
	public int hashCode() {
		return (idWrkr.hashCode());
	}
}
