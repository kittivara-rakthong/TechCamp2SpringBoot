package com.techcamp2.customer.support;

import com.techcamp2.customer.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerSupportTest {
    public static List<Customer> getCustomerList()
    {
        List<Customer> customerList = new ArrayList<>();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("AAA");
        customer.setLastName("aaa");
        customer.setEmail("aaaa@gmail.com");
        customer.setPhoneNumber("001");
        customer.setAge(11);
        customerList.add(customer);

        customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("BBB");
        customer.setLastName("bbb");
        customer.setEmail("bbbb@gmail.com");
        customer.setPhoneNumber("002");
        customer.setAge(22);
        customerList.add(customer);

        return customerList;

    }

    public static Customer getReqCustomer()
    {

        Customer customerReq = new Customer();
        customerReq.setId(1L);
        customerReq.setFirstName("AAA");
        customerReq.setLastName("aaa");
        customerReq.setEmail("aaaa@gmail.com");
        customerReq.setPhoneNumber("001");
        customerReq.setAge(11);

        return customerReq;
    }

    public static List<Customer> getCustomerListForGetByFirstName()
    {

        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Tiger");
        customer.setLastName("aaa");
        customer.setEmail("aaaa@gmail.com");
        customer.setPhoneNumber("001");
        customer.setAge(11);
        customerList.add(customer);


        customer = new Customer();
        customer.setId(2L);
        customer.setFirstName("Tiger");
        customer.setLastName("bb");
        customer.setEmail("bbbb@gmail.com");
        customer.setPhoneNumber("002");
        customer.setAge(22);
        customerList.add(customer);

        return customerList;
    }

    public static Customer getCustomerForGetByFirstName()
    {


        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Tiger");
        customer.setLastName("aaa");
        customer.setEmail("aaaa@gmail.com");
        customer.setPhoneNumber("001");
        customer.setAge(11);




        return customer;
    }

    public static Customer getReqNewCustomer()
    {
        Customer customerNewReq = new Customer();
        customerNewReq.setFirstName("New Name");
        customerNewReq.setLastName("New Lastname");
        customerNewReq.setEmail("qqqq@gmail.com");
        customerNewReq.setPhoneNumber("004");

        return customerNewReq;

    }

    public static Customer getResNewCustomer()
    {
        Customer customerRes = new Customer();
        customerRes.setId(1L);
        customerRes.setFirstName("New Name");
        customerRes.setLastName("New Lastname");
        customerRes.setEmail("qqqq@gmail.com");
        customerRes.setPhoneNumber("004");
        customerRes.setAge(33);

        return customerRes;
    }

    public static Customer getNewCustomer()
    {
        Customer newCustomer = new Customer();
        newCustomer.setId(2L);
        newCustomer.setFirstName("Noon");
        newCustomer.setLastName("Bow");
        newCustomer.setEmail("bow@gmail.com");
        newCustomer.setPhoneNumber("0002");
        newCustomer.setAge(5);

        return newCustomer;
    }
    public static Customer getOldCustomer()
    {
        Customer oldCustomer = new Customer();
        oldCustomer.setId(2L);
        oldCustomer.setFirstName("OldNoon");
        oldCustomer.setLastName("OldBow");
        oldCustomer.setEmail("old@gmail.com");
        oldCustomer.setPhoneNumber("0002");
        oldCustomer.setAge(20);

        return oldCustomer;
    }



}
