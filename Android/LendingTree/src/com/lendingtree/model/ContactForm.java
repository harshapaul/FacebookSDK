package com.lendingtree.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/*@Root(name = "PartnerPostedDataLead")
@NamespaceList({ @Namespace(prefix = "LTPPDL", reference = "http://www.lendingtree.com/static/xsd/PartnerPostedDataLead") })*/
@Root(name = "DataLeadRequest")
public class ContactForm {

	/*@Attribute(required = false)
	private String version;*/
	
	@Element(required = false)
	@JsonProperty("ApiKey")
	private String ApiKey;
	
	@Element(required = false)
	@JsonProperty("SearchRequestId")
	private String SearchRequestId;
	
	@Element(required = false)
	@JsonProperty("OfferId")
	private String OfferId;
	
	@Element(required = false)
	@JsonProperty("LenderId")
	private String LenderId;
	
	@Element(required = false)
	@JsonProperty("ClientIP")
	private String ClientIP;
	
	
	@Element(required = false)
	@JsonProperty("ContactDetails")
	private ContactDetails ContactDetails;

	/*public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}*/

	public String getSearchRequestId() {
		return SearchRequestId;
	}

	public String getApiKey() {
		return ApiKey;
	}

	@JsonProperty("ApiKey")
	public void setApiKey(String ApiKey) {
		this.ApiKey = ApiKey;
	}

	@JsonProperty("SearchRequestId")
	public void setSearchRequestId(String SearchRequestId) {
		this.SearchRequestId = SearchRequestId;
	}

	public String getOfferId() {
		return OfferId;
	}

	@JsonProperty("OfferId")
	public void setOfferId(String OfferId) {
		this.OfferId = OfferId;
	}

	public String getLenderId() {
		return LenderId;
	}

	@JsonProperty("LenderId")
	public void setLenderId(String LenderId) {
		this.LenderId = LenderId;
	}

	public ContactDetails getContactDetails() {
		return ContactDetails;
	}

	@JsonProperty("ContactDetails")
	public void setContactDetails(ContactDetails ContactDetails) {
		this.ContactDetails = ContactDetails;
	}

	public String getClientIP() {
		return ClientIP;
	}

	@JsonProperty("ClientIP")
	public void setClientIP(String clientIP) {
		ClientIP = clientIP;
	}
	
	
}
