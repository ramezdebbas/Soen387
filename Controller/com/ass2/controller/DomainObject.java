package com.ass2.controller;

import org.junit.Assert;

public class DomainObject {

	private Long ID;
	public Long getID() {
		return ID;
	}
	public void setID(Long ID) {
		Assert.assertNotNull("Cannot set a null ID", ID);
		this.ID = ID;
	}
	public DomainObject(Long ID) {
		this.ID = ID;
	}
	
	public DomainObject() {
	}

	
	protected void markNew() {
		UOW.getCurrent().registerNew(this);
	}
	protected void markClean() {
		UOW.getCurrent().registerClean(this);
	}
	protected void markDirty() {
		UOW.getCurrent().registerDirty(this);
	}
	protected void markRemoved() {
		UOW.getCurrent().registerRemoved(this);
	}

}

