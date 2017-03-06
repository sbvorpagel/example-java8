package wssim;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.example.ExampleBaseTestCase;
import org.example.domain.Address;
import org.example.domain.Contact;
import org.example.domain.Customer;
import org.testng.annotations.Test;

import io.ebean.Ebean;

public class CustomerUnitTest extends ExampleBaseTestCase {
		
	@Test
  	public void oneTonOneUpdate() {
	  
		Customer jack = new Customer("Jack", new Long(1));
		Address address = new Address();
		
		address.setCity("Cascavel");
		jack.setHomeAddress(address);
		jack.save();
		
		address.setCity("Londrina");
		jack.setHomeAddress(address);
		jack.update();	
		
		Customer customer = Ebean.find(Customer.class)
			.where()
			.findList().get(0);
		
		assertTrue(customer.getHomeAddress().getCity() == "Londrina");
	}
	
	@Test
  	public void oneTonOneSetNull() {
	  
		Customer jack = new Customer("Jack", new Long(1));
		Address address = new Address();
		
		address.setCity("Cascavel");
		jack.setHomeAddress(address);
		jack.save();
		
		jack.setHomeAddress(null);
		jack.update();	 
		
		Customer customer = Ebean.find(Customer.class)
			.where()
			.findList().get(0);
		
		assertTrue(customer.getHomeAddress() == null);	
	}
	
	@Test
  	public void updateNonexistentRegister () {
	  
		Customer jack = new Customer("Jack", new Long(1));
		Address address = new Address();
		
		address.setCity("Cascavel");
		jack.setHomeAddress(address);
	
		jack.update();	
		
		Customer customer = Ebean.find(Customer.class)
			.where()
			.findList().get(0);
			
		assertTrue(customer == null);	
	}
	
	@Test
  	public void deleteOneToManySetNull() {
	  
		Customer jack = new Customer("Jack");
		Contact contact = new Contact();
		contact.setFirstName("João");
		List<Contact> contacts = new ArrayList<>();
		contacts.add(contact);
		
		jack.setContacts(contacts);
		jack.save();
		
		jack.setContacts(null);
		jack.update();
		
		Customer customer = Ebean.find(Customer.class)
			.where()
			.findList().get(0);
		
		assertTrue(customer.getContacts().isEmpty() || customer.getContacts() == null);	
		
	}

}
