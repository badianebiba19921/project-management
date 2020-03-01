package com.ebb.pma.entities.project;

public class Domain {
	
	private long domainId;
	private String code;
	private String fullName;
	
	public Domain() {}
	
	public Domain(String code, String fullName) {
		super();
		this.code = code;
		this.fullName = fullName;
	}

	public long getDomainId() {
		return domainId;
	}
	public void setDomainId(long domainId) {
		this.domainId = domainId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	

}
