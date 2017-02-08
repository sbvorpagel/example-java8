package org.example.domain;

import io.ebean.Ebean;
import org.example.ExampleBaseTestCase;
import org.testng.annotations.Test;

import java.time.LocalDate;

/**
 * @author ivan.reffatti
 * @since 2017/02/08
 */
public class BetweenOrderDateTest extends ExampleBaseTestCase {

    @Test
    public void betweenProperties() {
        /* will generate select t0.id,
                         t0.status,
                         t0.order_date,
                         t0.ship_date,
                         t0.version,
                         t0.when_created,
                         t0.when_updated,
                         t0.deleted,
                         t0.customer_id,
                         t0.shipping_address_id
                         from o_order t0
                         where  ?
                         between t0.order_date and t0.ship_dateand t0.deleted = false*/
        // SQL is being generated with error, on deleted the "and" is together with the property
        Ebean.find(Order.class).where().betweenProperties("orderDate", "shipDate", new LocalDate[]{}).findList();
    }

}
