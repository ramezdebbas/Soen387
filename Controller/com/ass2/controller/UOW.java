package com.ass2.controller;
import java.lang.Object;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;


public class UOW {
	private List newObjects = new ArrayList();
	private List dirtyObjects = new ArrayList();
	private List removedObjects = new ArrayList();
	private static ThreadLocal current = new ThreadLocal();
	
	
	public static void newCurrent() {
		setCurrent(new UOW());
	}
	public static void setCurrent(UOW uow) {
		current.set(uow);
	}
	public static UOW getCurrent() {
		return (UOW) current.get();
	}
	
	
	public void registerNew(DomainObject obj) {
		Assert.assertNotNull("id not null", obj.getID());
		Assert.assertTrue("object not dirty", !dirtyObjects.contains(obj));
		Assert.assertTrue("object not removed", !removedObjects.contains(obj));
		Assert.assertTrue("object not already registered new", !newObjects.contains(obj));
		newObjects.add(obj);
	}
	
	public void registerDirty(DomainObject obj) {
		Assert.assertNotNull("id not null", obj.getID());
		Assert.assertTrue("object not removed", !removedObjects.contains(obj));
		if (!dirtyObjects.contains(obj) && !newObjects.contains(obj)) {
			dirtyObjects.add(obj);
		}
	}
	
	public void registerRemoved(DomainObject obj) {
		Assert.assertNotNull("id not null", obj.getID());
		if (newObjects.remove(obj)) return;
		dirtyObjects.remove(obj);
		if (!removedObjects.contains(obj)) {
			removedObjects.add(obj);
		}
	}
	
	public void registerClean(DomainObject obj) {
		Assert.assertNotNull("id not null", obj.getID());
	}
	
	public void commit() {
		insertNew();
		updateDirty();
		deleteRemoved();
	}
	private void insertNew() {
		for (Iterator objects = newObjects.iterator(); objects.hasNext();) {
			DomainObject obj = (DomainObject) objects.next();
			//MapperRegistry.getMapper(obj.getClass()).insert(obj);
		}
	}
	
	private void updateDirty() {
		for (Iterator objects = dirtyObjects.iterator(); objects.hasNext();) {
			DomainObject obj = (DomainObject) objects.next();
			//MapperRegistry.getMapper(obj.getClass()).update(obj);
		}
	}
	
	private void deleteRemoved() {
		for (Iterator objects = removedObjects.iterator(); objects.hasNext();) {
			DomainObject obj = (DomainObject) objects.next();
			//MapperRegistry.getMapper(obj.getClass()).delete(obj);
		}
	}


}
