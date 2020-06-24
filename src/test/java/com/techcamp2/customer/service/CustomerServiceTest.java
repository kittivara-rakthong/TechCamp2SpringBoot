package com.techcamp2.customer.service;

import com.techcamp2.customer.model.Customer;
import com.techcamp2.customer.repository.CustomerRepository;

import com.techcamp2.customer.support.CustomerSupportTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockingDetails;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerService(customerRepository);
    }

    @DisplayName("Test get all customer should return list")
    @Test
    void testGetAllCustomer() {


        when(customerRepository.findAll()).thenReturn(CustomerSupportTest.getCustomerList());

        List<Customer> resp = customerService.getCustomerList();

        assertEquals(1,resp.get(0).getId().intValue());
        assertEquals("AAA",resp.get(0).getFirstName());
        assertEquals("aaa",resp.get(0).getLastName());
        assertEquals("aaaa@gmail.com",resp.get(0).getEmail());
        assertEquals("001",resp.get(0).getPhoneNumber());
        assertEquals(11,resp.get(0).getAge());


        assertEquals(2,resp.get(1).getId().intValue());
        assertEquals("BBB",resp.get(1).getFirstName());
        assertEquals("bbb",resp.get(1).getLastName());
        assertEquals("bbbb@gmail.com",resp.get(1).getEmail());
        assertEquals("002",resp.get(1).getPhoneNumber());
        assertEquals(22,resp.get(1).getAge());


    }
    @DisplayName("Test get customer by id should return customer")
    @Test
    void testGetCustomerById()
    {
        Long reqParam = 1l;


        when(customerRepository.findAllById(reqParam)).thenReturn(CustomerSupportTest.getReqCustomer());
        Customer resp = customerService.getCustomerById(reqParam);

        assertEquals(1,resp.getId().intValue());
        assertEquals("AAA",resp.getFirstName());
        assertEquals("aaa",resp.getLastName());
        assertEquals("aaaa@gmail.com",resp.getEmail());
        assertEquals("001",resp.getPhoneNumber());
        assertEquals(11,resp.getAge());


    }
    @DisplayName("Test get customer by first name should return customer")
    @Test
    void testGetCustomerByFirstName()
    {
        String reqParam = "Tiger";


        when(customerRepository.findByFirstName(reqParam)).thenReturn(CustomerSupportTest.getCustomerListForGetByFirstName());
        List<Customer> resp = customerService.getCustomerByFirstName(reqParam);

        assertEquals(1,resp.get(0).getId().intValue());
        assertEquals("Tiger",resp.get(0).getFirstName());
        assertEquals("aaa",resp.get(0).getLastName());
        assertEquals("aaaa@gmail.com",resp.get(0).getEmail());
        assertEquals("001",resp.get(0).getPhoneNumber());
        assertEquals(11,resp.get(0).getAge());

    }

    @DisplayName("Test create customer should return new customer")
    @Test
    void testCreateCustomer()
    {


        when(customerRepository.save(CustomerSupportTest.getResNewCustomer())).thenReturn(CustomerSupportTest.getResNewCustomer());

        Customer resp = customerService.createCustomer(CustomerSupportTest.getResNewCustomer());
        assertEquals(1,resp.getId().intValue());
        assertEquals("New Name",resp.getFirstName());
        assertEquals("New Lastname",resp.getLastName());
        assertEquals("004",resp.getPhoneNumber());
        assertEquals("qqqq@gmail.com",resp.getEmail());
        assertEquals(33,resp.getAge());

    }

    @DisplayName("Test update customer should return success")
    @Test
    void testUpdateCustomer()
    {
        Long reqId = 2L;

        when(customerRepository.findAllById(reqId)).thenReturn(CustomerSupportTest.getOldCustomer());
        when(customerRepository.save(CustomerSupportTest.getNewCustomer())).thenReturn(CustomerSupportTest.getNewCustomer());

        Customer resp = customerService.updateCustomer(reqId,CustomerSupportTest.getNewCustomer());
        assertEquals(2,resp.getId().intValue());
        assertEquals("Noon",resp.getFirstName());
        assertEquals("Bow",resp.getLastName());
        assertEquals("0002",resp.getPhoneNumber());
        assertEquals("bow@gmail.com",resp.getEmail());
        assertEquals(5,resp.getAge());

    }

    @DisplayName("Test update customer should return fail")
    @Test
    void testUpdateCustomerFail()
    {
        Long reqId = 4L;

        when(customerRepository.findAllById(reqId)).thenReturn(null);
        Customer resp = customerService.updateCustomer(reqId,CustomerSupportTest.getReqCustomer());
        assertEquals(null,resp);
    }

    @DisplayName("Test delete customer should return true")
    @Test
    void testDeleteCustomer()
    {
        Long reqId = 1L;
        doNothing().when(customerRepository).deleteById(reqId);
        boolean resp = customerService.deleteById(reqId);
        assertEquals(true,resp);
        assertTrue(resp);
        assertTrue(customerService.deleteById(reqId));
    }

    @DisplayName("Test delete customer should return fail")
    @Test
    void testDeletexCustomerFail()
    {
        Long reqId = 1L;
        doThrow(EmptyResultDataAccessException.class)
                .when(customerRepository).deleteById(reqId);
        boolean resp = customerService.deleteById(reqId);
        assertEquals(false,resp);
        assertFalse(resp);
        assertFalse(customerService.deleteById(reqId));
    }
}
