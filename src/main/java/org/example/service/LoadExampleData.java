package org.example.service;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import org.example.domain.Address;
import org.example.domain.Contact;
import org.example.domain.Country;
import org.example.domain.Customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoadExampleData {

  private static boolean runOnce;

  private static EbeanServer server = Ebean.getServer(null);

  public static synchronized void load() {

    if (runOnce) {
      return;
    }

    final LoadExampleData me = new LoadExampleData();

    server.execute(() -> {
      if (Country.find.query().findCount() > 0) {
        return;
      }
      me.deleteAll();
      me.insertCountries();
    });
    runOnce = true;
  }

  public void deleteAll() {
    Ebean.execute(() -> {

      // Ebean.currentTransaction().setBatchMode(false);

      // orm update use bean name and bean properties
      // server.createUpdate(OrderShipment.class, "delete from orderShipment").execute();

      server.createUpdate(Contact.class, "delete from contact").execute();
      server.createUpdate(Customer.class, "delete from Customer").execute();
      server.createUpdate(Address.class, "delete from address").execute();

      // sql update uses table and column names
      server.createSqlUpdate("delete from o_country").execute();
      server.createSqlUpdate("delete from o_product").execute();
    });
  }

  public void insertCountries() {

    server.execute(() ->  {
      new Country("NZ", "New Zealand").save();
      new Country("AU", "Australia").save();
    });
  }

  public static Customer createCustAndOrder(String custName) {

    LoadExampleData me = new LoadExampleData();
    Customer cust1 = insertCustomer(custName);
    return cust1;
  }

  private static int contactEmailNum = 1;

  private Customer insertCustomerFiona() {

    Customer c = createCustomer("Fiona", "12 Apple St", "West Coast Rd", 1);

    c.addContact(createContact("Fiona", "Black"));
    c.addContact(createContact("Tracy", "Red"));

    Ebean.save(c);
    return c;
  }

  public static Contact createContact(String firstName, String lastName) {
    Contact contact = new Contact();
    contact.setFirstName(firstName);
    contact.setLastName(lastName);
    String email = contact.getLastName() + (contactEmailNum++) + "@test.com";
    contact.setEmail(email.toLowerCase());
    return contact;
  }

  private Customer insertCustomerNoContacts(String name) {

    Customer c = createCustomer(name, "15 Kumera Way", "Bos town", 1);

    Ebean.save(c);
    return c;
  }

  private Customer insertCustomerNoAddress() {

    Customer c = new Customer("Jack Hill");
    c.addContact(createContact("Jack", "Black"));
    c.addContact(createContact("Jill", "Hill"));
    c.addContact(createContact("Mac", "Hill"));

    Ebean.save(c);
    return c;
  }

  private static Customer insertCustomer(String name) {
    Customer c = createCustomer(name, "1 Banana St", "P.O.Box 1234", 1);
    Ebean.save(c);
    return c;
  }

  private static Customer createCustomer(String name, String shippingStreet, String billingStreet, int contactSuffix) {

    Customer c = new Customer(name);
    if (contactSuffix > 0) {
      c.addContact(new Contact("Jim" + contactSuffix, "Cricket"));
      c.addContact(new Contact("Fred" + contactSuffix, "Blue"));
      c.addContact(new Contact("Bugs" + contactSuffix, "Bunny"));
    }

    if (shippingStreet != null) {
      Address shippingAddr = new Address();
      shippingAddr.setLine1(shippingStreet);
      shippingAddr.setLine2("Sandringham");
      shippingAddr.setCity("Auckland");
      shippingAddr.setCountry(Country.find.ref("NZ"));

      c.setShippingAddress(shippingAddr);
    }

    if (billingStreet != null) {
      Address billingAddr = new Address();
      billingAddr.setLine1(billingStreet);
      billingAddr.setLine2("St Lukes");
      billingAddr.setCity("Auckland");
      billingAddr.setCountry(Ebean.getReference(Country.class, "NZ"));

      c.setBillingAddress(billingAddr);
    }

    return c;
  }

}
