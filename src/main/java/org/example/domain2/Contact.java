package org.example.domain2;

import org.example.domain.finder.ContactFinder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Contact entity bean.
 */
@Entity
@Table(name = "be_contact")
public class Contact extends BaseModel {

	public static final ContactFinder find = new ContactFinder();

	@Column(length = 50, nullable = false)
	String firstName;

	@Column(length = 50)
	String lastName;

	@Column(length = 200)
	String email;

	@Column(length = 20)
	String phone;

	@ManyToOne(optional = false)
	Customer customer;

	@Lob
	String comments;

	@OneToOne(optional = false)
	@JoinColumn(name = "country_id", referencedColumnName = "code")
	private Country contactCountry;

	// @OneToMany(mappedBy = "contact")
	// List<ContactNote> notes;

	/**
	 * Default constructor.
	 */
	public Contact() {
	}

	public Country getContactCountry() {
		return contactCountry;
	}

	public void setContactCountry(Country contactCountry) {
		this.contactCountry = contactCountry;
	}

	/**
	 * Construct with a firstName and lastName.
	 */
	public Contact(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	// public List<ContactNote> getNotes() {
	// return notes;
	// }
	//
	// public void setNotes(List<ContactNote> notes) {
	// this.notes = notes;
	// }
}
