package org.example.domain;

import io.ebean.Ebean;
import org.example.ExampleBaseTestCase;
import org.testng.annotations.Test;

/**
 * @author ivan.reffatti
 * @since 2017/02/08
 */
public class UpdateCustomerTest extends ExampleBaseTestCase {

    @Test
    public void update(){
        Customer customer = new Customer("Ivan");

        Ebean.insert(customer);

        customer.setComments("Any comments");
        // Version is now set to NULL
        customer.setVersion(null);

        Ebean.update(customer);
    }
}
