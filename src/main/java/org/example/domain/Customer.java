package org.example.domain;

import io.ebean.annotation.DbArray;
import io.ebean.annotation.DbComment;
import org.example.domain.finder.CustomerFinder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Customer entity bean.
 *
 * This example shows an entity bean without a default constructor. The name property is
 * expected to be final and non-null. Note the InsertCustomerTest.testRef() test showing
 * loading the partially loaded bean.
 */
@DbComment("Customer table general comment")
@Entity
@Table(name="be_customer")
public class Customer extends BaseModel {

  public static final CustomerFinder find = new CustomerFinder();

  boolean inactive;

  @Column(length=100)
  String name;

  @DbComment("The date the customer first registered")
  LocalDate registered;

  @DbArray // Postgres ARRAY
  List<UUID> uids = new ArrayList<>();

  @Size(max = 1000)
  @Lob
  String comments;

  @ManyToOne(cascade=CascadeType.ALL)
  Address billingAddress;

  @ManyToOne(cascade=CascadeType.ALL)
  Address shippingAddress;

  @OneToMany(mappedBy="customer", cascade=CascadeType.PERSIST)
  List<Contact> contacts;
  
  @OneToOne(mappedBy = "customerHome", cascade = CascadeType.ALL, orphanRemoval = true)
  private Address homeAddress;
  
  public Address getHomeAddress() {
	  return homeAddress;
  }
  
  public void setHomeAddress(Address homeAddress) {
	  this.homeAddress = homeAddress;
  }

  public Customer(String name) {
    this.name = name;
  }

  public Customer(String name, Long id) {
	this.name = name;
	this.id = id;
}

public String toString() {
    return "id:" + id + " name:" + name;
  }

  public boolean isInactive() {
    return inactive;
  }

  public void setInactive(boolean inactive) {
    this.inactive = inactive;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getRegistered() {
    return registered;
  }

  public void setRegistered(LocalDate registered) {
    this.registered = registered;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }
  
  public Address getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(Address billingAddress) {
    this.billingAddress = billingAddress;
  }

  public Address getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(Address shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public List<Contact> getContacts() {
    return contacts;
  }

  public void setContacts(List<Contact> contacts) {
    this.contacts = contacts;
  }

  /**
   * Helper method to add a contact to the customer.
   */
  public void addContact(Contact contact) {
    if (contacts == null) {
      contacts = new ArrayList<>();
    }
    // setting the customer is automatically done when Ebean does
    // a cascade save from customer to contacts. 
    contact.setCustomer(this);
    contacts.add(contact);
  }
  
  
  
}
