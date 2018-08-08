package org.dhhs.dirm.acts.cs.ejb.util;

import java.io.*;
import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import org.dhhs.dirm.acts.cs.persister.*;

/**
 * A Simple Class that helps the user of this class typically a servlet, jsp or an
 * stand alone java program to send email messages from the smtp host.
 * <p>
 * The smtp host name must be obtained from either a properties file or
 * included within the class.
 * <p>
 * Enhancements have made to the class to be able to accept multiple To addresses,
 * multiple CC's and BCC's and also has the ability to send the email in HTML format
 * or plain text with attachments
 * Creation date: (3/28/2002 10:36:39 PM)
 * @author: Ramakanth Kodumagulla
 */
public class EmailManager {

	private Vector to;
	private String from;
	private Vector bcc;
	private Vector cc;

	private String subject;

	private String body;

	private String host;
	private boolean debug;
	private String user;
	private String password;
	private boolean attach;
	private Vector attachments;

	private static final String className = "EmailManager";
	private String methodName = "";

	public static final String ERR_TO_ADDRESS = "Error: To Address Missing";
	public static final String ERR_FROM_ADDRESS = "Error: From Address Missing";
	public static final String ERR_INVALID_HOST_NAME = "Error: Invalid Host Name";
	public static final String ERR_WITH_ATTACHMENTS = "Error: Failed to Load Attachments";
	public static final String ERR_SEND_FAILED = "Error: EmailManager failed to send email";
	public static final String ERR_PROPERTIES = "Error: Failed to read properties file";
	public static final String ERR_MULTIPLE = "Error: Failed to set either from, to, cc or bcc";

	/**
	 * EmailHandler constructor comment.
	 */
	public EmailManager() throws ApplicationException {

		methodName = "EmailManager()";

		host = PropertyManager.getMailServerHost();
		user = PropertyManager.getMailServerUser();
		password = PropertyManager.getMailServerPassword();
		from = PropertyManager.getMailSender();

		// Initialize the subject and body to spaces
		subject = "";
		body = "";

		// Create arrays to hold the addresses
		to = new Vector();
		bcc = new Vector();
		cc = new Vector();
		attachments = new Vector();
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/29/2002 11:42:44 AM)
	 * @param s java.lang.String
	 */
	public void addAttachments(String s) {

		attachments.addElement(s);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/29/2002 11:42:04 AM)
	 * @param s java.lang.String
	 */
	public void addBcc(String s) {

		bcc.addElement(s);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/29/2002 11:42:23 AM)
	 * @param s java.lang.String
	 */
	public void addCc(String s) {

		cc.addElement(s);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/29/2002 11:25:30 AM)
	 * @param s java.lang.String
	 */
	public void addTo(String s) {

		to.addElement(s);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/20/2001 10:07:08 AM)
	 * @return java.lang.String
	 */
	public java.lang.String getBody() {
		return body;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/20/2001 10:07:08 AM)
	 * @return java.lang.String
	 */
	public java.lang.String getFrom() {
		return from;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/20/2001 10:35:19 AM)
	 * @return java.lang.String
	 */
	public java.lang.String getHost() {
		return host;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/20/2001 10:07:08 AM)
	 * @return java.lang.String
	 */
	public java.lang.String getSubject() {
		return subject;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/29/2002 10:32:28 PM)
	 * @return boolean
	 */
	public boolean isAttach() {
		return attach;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/20/2001 10:35:19 AM)
	 * @return boolean
	 */
	public boolean isDebug() {
		return debug;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/29/2002 6:25:46 PM)
	 * @param args java.lang.String[]
	 */
	public static void main(String[] args) {

	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/29/2002 11:44:11 AM)
	 */
	private void processAttachments(Multipart mp) throws javax.mail.MessagingException {

		MimeBodyPart mbpAttachment = new MimeBodyPart();

		FileDataSource fds;

		for (int i = 0; i < attachments.size(); i++) {
			String attachment = (String) attachments.elementAt(i);

			/** attach the file to the message**/
			if (!(attachment.equals(""))) {

				MimeBodyPart mbpMessage = new MimeBodyPart();
				mbpMessage.setText("The following file is attached - " + attachment);
				mp.addBodyPart(mbpMessage);

				fds = new FileDataSource(attachment);
				mbpAttachment.setDataHandler(new DataHandler(fds));
				mbpAttachment.setFileName(fds.getName());
				mp.addBodyPart(mbpAttachment);
			}
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/20/2001 10:08:02 AM)
	 */
	public void sendMail() throws ApplicationException {

		methodName = "sendMail";

		try {

			Properties props = new Properties();

			if (to == null) {
				ErrorDescriptor ed = new ErrorDescriptor(className, methodName, ERR_TO_ADDRESS, null);
				throw new ApplicationException(ERR_TO_ADDRESS, ed);
			}

			if (from == null) {
				ErrorDescriptor ed = new ErrorDescriptor(className, methodName, ERR_FROM_ADDRESS, null);
				throw new ApplicationException(ERR_FROM_ADDRESS, ed);
			}

			if (host == null) {
				ErrorDescriptor ed = new ErrorDescriptor(className, methodName, ERR_INVALID_HOST_NAME, null);
				throw new ApplicationException(ERR_INVALID_HOST_NAME, ed);
			}

			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");

			if (debug) {
				props.put("mail.debug", "true");
			}

			javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, null);
			session.setDebug(debug);

			MimeMessage msg = new MimeMessage(session);

			try {
				msg.setFrom(new InternetAddress(from, "N.C. Child Support Customer Service Center"));

				for (int i = 0; i < to.size(); i++) {
					InternetAddress[] address = { new InternetAddress((String) to.elementAt(i))};
					msg.addRecipients(Message.RecipientType.TO, address);
				}

				for (int i = 0; i < cc.size(); i++) {
					InternetAddress[] address = { new InternetAddress((String) cc.elementAt(i))};
					msg.addRecipients(Message.RecipientType.CC, address);
				}

				for (int i = 0; i < bcc.size(); i++) {
					InternetAddress[] address = { new InternetAddress((String) bcc.elementAt(i))};
					msg.addRecipients(Message.RecipientType.BCC, address);
				}

				msg.setSubject(subject);

				msg.setSentDate(new Date());

			} catch (MessagingException mex) {
				ErrorDescriptor ed = new ErrorDescriptor(className, methodName, ERR_MULTIPLE, mex);
				throw new ApplicationException(ERR_MULTIPLE, mex, ed);
			}

			// Create the multipart object
			Multipart mp = new MimeMultipart();

			// create and fill the first message part
			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setText(body);

			mp.addBodyPart(mbp);

			try {
				if (attach) {
					processAttachments(mp);
				}

			} catch (MessagingException mex) {
				ErrorDescriptor ed = new ErrorDescriptor(className, methodName, ERR_WITH_ATTACHMENTS, mex);
				throw new ApplicationException(ERR_WITH_ATTACHMENTS, mex, ed);
			}

			msg.setContent(mp);

			try {
				Transport transport = session.getTransport("smtp");
				transport.connect(host, 25, user, password);
				msg.saveChanges();
				transport.sendMessage(msg, msg.getAllRecipients());
				//          transport.send(msg);
			} catch (MessagingException mex) {
				ErrorDescriptor ed = new ErrorDescriptor(className, methodName, ERR_SEND_FAILED, mex);
				throw new ApplicationException(ERR_SEND_FAILED, mex, ed);
			}
		} catch (Exception e) {
			throw new ApplicationException("Unknown Exception", e);
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/20/2001 10:08:02 AM)
	 */
	public void sendMailHTML() throws ApplicationException {

		methodName = "sendMail";

		try {

			Properties props = new Properties();

			if (to == null) {
				ErrorDescriptor ed = new ErrorDescriptor(className, methodName, ERR_TO_ADDRESS, null);
				throw new ApplicationException(ERR_TO_ADDRESS, ed);
			}

			if (from == null) {
				ErrorDescriptor ed = new ErrorDescriptor(className, methodName, ERR_FROM_ADDRESS, null);
				throw new ApplicationException(ERR_FROM_ADDRESS, ed);
			}

			if (host == null) {
				ErrorDescriptor ed = new ErrorDescriptor(className, methodName, ERR_INVALID_HOST_NAME, null);
				throw new ApplicationException(ERR_INVALID_HOST_NAME, ed);
			}

			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");

			if (debug) {
				props.put("mail.debug", "true");
			}

			javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, null);
			session.setDebug(debug);

			MimeMessage msg = new MimeMessage(session);

			try {
				msg.setFrom(new InternetAddress(from));

				for (int i = 0; i < to.size(); i++) {
					InternetAddress[] address = { new InternetAddress((String) to.elementAt(i))};
					msg.addRecipients(Message.RecipientType.TO, address);
				}

				for (int i = 0; i < cc.size(); i++) {
					InternetAddress[] address = { new InternetAddress((String) cc.elementAt(i))};
					msg.addRecipients(Message.RecipientType.CC, address);
				}

				for (int i = 0; i < bcc.size(); i++) {
					InternetAddress[] address = { new InternetAddress((String) bcc.elementAt(i))};
					msg.addRecipients(Message.RecipientType.BCC, address);
				}

				msg.setSubject(subject);

				msg.setSentDate(new Date());

			} catch (MessagingException mex) {
				ErrorDescriptor ed = new ErrorDescriptor(className, methodName, ERR_MULTIPLE, mex);
				throw new ApplicationException(ERR_MULTIPLE, mex, ed);
			}

			// Create the multipart object
			Multipart mp = new MimeMultipart();

			// create and fill the first message part
			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setText(body);
			mbp.setHeader("Content-Type", "text/html;");
			mbp.setHeader("Content-Transfer-Encoding", "base64");
			mbp.setHeader("charset", "\"gb2312\"");

			mp.addBodyPart(mbp);

			try {
				if (attach) {
					processAttachments(mp);
				}

			} catch (MessagingException mex) {
				ErrorDescriptor ed = new ErrorDescriptor(className, methodName, ERR_WITH_ATTACHMENTS, mex);
				throw new ApplicationException(ERR_WITH_ATTACHMENTS, mex, ed);
			}

			msg.setContent(mp);

			try {
				Transport transport = session.getTransport("smtp");
				transport.connect(host, 25, user, password);
				msg.saveChanges();
				transport.sendMessage(msg, msg.getAllRecipients());
				//          transport.send(msg);
			} catch (MessagingException mex) {
				ErrorDescriptor ed = new ErrorDescriptor(className, methodName, ERR_SEND_FAILED, mex);
				throw new ApplicationException(ERR_SEND_FAILED, mex, ed);
			}
		} catch (Exception e) {
			throw new ApplicationException("Unknown Exception", e);
		}
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/29/2002 10:32:28 PM)
	 * @param newAttach boolean
	 */
	public void setAttach(boolean newAttach) {
		attach = newAttach;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/20/2001 10:07:08 AM)
	 * @param newBody java.lang.String
	 */
	public void setBody(java.lang.String newBody) {
		body = newBody;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/20/2001 10:35:19 AM)
	 * @param newDebug boolean
	 */
	public void setDebug(boolean newDebug) {
		debug = newDebug;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/20/2001 10:07:08 AM)
	 * @param newFrom java.lang.String
	 */
	public void setFrom(java.lang.String newFrom) {
		from = newFrom;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/20/2001 10:35:19 AM)
	 * @param newHost java.lang.String
	 */
	public void setHost(java.lang.String newHost) {
		host = newHost;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (3/20/2001 10:07:08 AM)
	 * @param newSubject java.lang.String
	 */
	public void setSubject(java.lang.String newSubject) {
		subject = newSubject;
	}
}
