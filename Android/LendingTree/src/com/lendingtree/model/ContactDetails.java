package com.lendingtree.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "ContactDetails")
@JsonRootName("ContactDetails")
public class ContactDetails {
	
	@Element(required = false)
	@JsonProperty("FirstName")
	private String FirstName;
	
	@Element(required =false)
	@JsonProperty("LastName")
	private String LastName;
	
	@Element(required = false)
	@JsonProperty("PhoneNumber")
	private String PhoneNumber;
	
	@Element(required = false)
	@JsonProperty("EmailAddress")
	private String EmailAddress;
	
	@Element(required = false)
	@JsonProperty("Comments")
	private String Comments;
	
	/*@Element(required = false)
	private int EsourceId;

	public int getEsourceId() {
		return EsourceId;
	}

	public void setEsourceId(int esourceId) {
		EsourceId = esourceId;
	}*/

	public String getFirstName() {
		return FirstName;
	}

	@JsonProperty("FirstName")
	public void setFirstName(String FirstName) {
		this.FirstName = FirstName;
	}

	public String getLastName() {
		return LastName;
	}

	@JsonProperty("LastName")
	public void setLastName(String LastName) {
		this.LastName = LastName;
	}

	public String getPhone() {
		return PhoneNumber;
	}

	@JsonProperty("PhoneNumber")
	public void setPhone(String PhoneNumber) {
		this.PhoneNumber = PhoneNumber;
	}

	public String getEmailAddress() {
		return EmailAddress;
	}

	@JsonProperty("EmailAddress")
	public void setEmailAddress(String EmailAddress) {
		this.EmailAddress = EmailAddress;
	}

	public String getComments() {
		return Comments;
	}

	@JsonProperty("Comments")
	public void setComments(String Comments) {
		this.Comments = Comments;
	}

	
}
