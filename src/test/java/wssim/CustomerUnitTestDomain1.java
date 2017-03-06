package wssim;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.example.ExampleBaseTestCase;
import org.example.domain.Address;
import org.example.domain.Contact;
import org.example.domain.Country;
import org.example.domain.Customer;
import org.testng.annotations.Test;

import io.ebean.Ebean;

public class CustomerUnitTestDomain1 extends ExampleBaseTestCase {

	/**
	 * 
	 * When updating the entity with "OneToOne" relationship, it is expected
	 * that the entity is deleted so that the new one is created. However, due
	 * to the order of execution, it is inserted before and this may violate
	 * rules (in this case uniqueness).
	 */
	@Test
	public void oneTonOneInsertBeforeDeleting() {

		Customer jack = new Customer("Jack", new Long(1));
		Address address = new Address();

		//Set initial city
		address.setCity("Cascavel");
		jack.setHomeAddress(address);
		jack.save();

		//Set new ciry
		Address address2 = new Address();
		address2.setCity("Londrina");
		jack.setHomeAddress(address2);
		jack.update();

		Customer customer = Ebean.find(Customer.class).where().eq("id", new Long(1)).findList().get(0);

		//Expected city = "Londrina", but, violation role prevents updating.
		assertEquals(customer.getHomeAddress().getCity(), "Londrina");
	}

	/**
	 * When added null in an entity with relation "OneToOne", I hope this
	 * relation is deleted.
	 * 
	 * Address testing
	 */
	@Test
	public void oneTonOneNotDeleteNullEntity_Address() {

		Customer jack = new Customer("Jack", new Long(200));
		Address address = new Address();
		address.setId(new Long(100));

		//Set initial city
		address.setCity("Cascavel");
		jack.setHomeAddress(address);
		jack.save();

		//Set null in the city
		jack.setHomeAddress(null);
		jack.update();

		Address addressSearch = Ebean.find(Address.class).where().eq("id", new Long(100)).findList().get(0);

		//Expected address will be deleted
		assertNull(addressSearch);
	}

	/**
	 * When added null in an entity with relation "OneToOne", I hope this
	 * relation is deleted.
	 * 
	 * Customer testing
	 */
	@Test
	public void oneTonOneNotDeleteNullEntity_Customer() {

		Customer jack = new Customer("Jack", new Long(300));
		Address address = new Address();
		address.setId(new Long(300));

		//Set initial city
		address.setCity("Cascavel");
		jack.setHomeAddress(address);
		jack.save();

		//Set null in the city
		jack.setHomeAddress(null);
		jack.update();

		Customer customer = Ebean.find(Customer.class).where().eq("id", new Long(300)).findList().get(0);

		//Expected home address null or empty
		assertNull(customer.getHomeAddress());
	}

	/**
	 * In a "OneToMany" relation I expect the relation to be deleted when the
	 * attribute is null.
	 */
	@Test
	public void oneToManySetDeleteWithNull() {

		Customer jack = new Customer("Jack", new Long(400));
		Contact contact = new Contact();
		contact.setFirstName("João");
		List<Contact> contacts = new ArrayList<>();
		contacts.add(contact);

		jack.setContacts(contacts);
		jack.save();

		//Delete contact
		jack.setContacts(null);
		jack.update();

		Customer customer = Ebean.find(Customer.class).where().eq("id", new Long(400)).findList().get(0);

		//Expected contacts address null or empty
		assertTrue(customer.getContacts().isEmpty() || customer.getContacts() == null);
	}

}
