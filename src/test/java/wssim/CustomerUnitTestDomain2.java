package wssim;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.example.ExampleBaseTestCase;
import org.example.domain2.Address;
import org.example.domain2.Contact;
import org.example.domain2.Country;
import org.example.domain2.Customer;
import org.testng.annotations.Test;

import io.ebean.Ebean;

public class CustomerUnitTestDomain2 extends ExampleBaseTestCase {

	/**
	 * In this test, we show that the order of execution is 
	 * violating a uniqueness rule between country and contact. 
	 */
	@Test
	public void deleteOneToManySetNull() {

		Country country = new Country("BR", "Brazil");
		country.save();

		Customer jack = new Customer("Jack", new Long(600));

		Contact contact = new Contact();
		contact.setFirstName("João");
		contact.setContactCountry(country);

		jack.setContacts(Arrays.asList(contact));
		jack.save();

		Contact contact2 = new Contact();
		contact2.setFirstName("João 2");
		contact2.setContactCountry(country);

		jack.setContacts(Arrays.asList(contact2));
		
		// Due to insertion before deletion, the unique constraint at the Contact table is broken, 
		// so the process is not performed as expected 
		jack.update();
		
		Customer customer = Ebean.find(Customer.class).where().eq("id", new Long(600)).findList().get(0);

		assertTrue(customer.getContacts().isEmpty() || customer.getContacts() == null);

	}

	/**
	 * In this test we simulate an concurrent change when an address is deleted by another 
	 * process. In this particular test case we just do it in the same thread referring the old 
	 * record in the next update. We expect that the the entity to be inserted again or 
	 * thrown an error, but no option is achieved.
	 */
	@Test
	public void updateOnDeletedEntity() {

		// Create a new Address 
		Address address = new Address();
		address.setId(new Long(700));
		address.setCity("Toledo");

		// Create a new Customer with previous Address
		Customer jack = new Customer("Jack", new Long(700));
		jack.setHomeAddress(address);

		jack.save();

		// Create a new Address referring the old record
		Address address2 = new Address();
		address2.setId(address.getId());
		address2.setCity("Cascavel");

		// Delete the old Address
		address.delete();

		// Put again the new address with old id in the customer  
		jack.setHomeAddress(address2);

		// Update the customer, expecting the insertion or error thrown 
		jack.update();

		Customer customer = Ebean.find(Customer.class).where().eq("id", new Long(700)).findList().get(0);

		assertNull(customer.getHomeAddress());

	}

}
