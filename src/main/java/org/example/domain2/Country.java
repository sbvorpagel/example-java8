package org.example.domain2;

import io.ebean.Model;
import io.ebean.annotation.Cache;
import org.example.domain.finder.CountryFinder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Country entity bean.
 */
@Cache
@Entity
@Table(name = "o_country")
public class Country extends Model {

	public static final CountryFinder find = new CountryFinder();

	@OneToOne(mappedBy = "contactCountry")
	private Contact countryContact;

	@Id
	@Size(max = 2)
	final String code;

	@Size(max = 60)
	final String name;

	public Country(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String toString() {
		return code;
	}

	/**
	 * Return code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Return name.
	 */
	public String getName() {
		return name;
	}

	public Contact getCountryContact() {
		return countryContact;
	}

	public void setCountryContact(Contact countryContact) {
		this.countryContact = countryContact;
	}

}
