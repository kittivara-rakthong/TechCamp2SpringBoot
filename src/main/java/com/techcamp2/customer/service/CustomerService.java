package com.techcamp2.customer.service;

import com.techcamp2.customer.model.Customer;
import com.techcamp2.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }


    public List<Customer> getCustomerList()
    {

        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer)
    {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id)
    {
        return customerRepository.findAllById(id);
    }

   public List<Customer> getCustomerByFirstName(String firstName)
   {
       return customerRepository.findByFirstName(firstName);
   }

   public Customer updateCustomer(Long id, Customer customerReq)
   {
       return customerRepository.findAllById(id) != null ?
               customerRepository.save(customerReq) : null;
   }

   public boolean deleteById (Long id)
   {
       try {
            customerRepository.deleteById(id);
            return true;
       }catch(EmptyResultDataAccessException err)
       {
           return false;
       }
   }


}
