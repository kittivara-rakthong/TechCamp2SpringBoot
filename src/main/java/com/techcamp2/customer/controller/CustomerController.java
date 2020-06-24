package com.techcamp2.customer.controller;


import com.techcamp2.customer.model.Customer;
import com.techcamp2.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path="/customer")
public class CustomerController {
    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService)
    {
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public List<Customer> customerList()
    {
        return customerService.getCustomerList();
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer body)
    {
        Customer customer = customerService.createCustomer(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id)
    {
        Customer customer = customerService.getCustomerById(id);
        if(customer != null)
        {
            return  ResponseEntity.ok(customer);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

//    @GetMapping("/name/{firstName}")
//    public ResponseEntity<?> getCustomerByFirstName(@PathVariable String firstName)
//    {
//        List<Customer> customer = customerService.getCustomerByFirstName(firstName);
//        if(customer != null)
//        {
//            return  ResponseEntity.ok(customer);
//        }
//        else
//        {
//            return ResponseEntity.notFound().build();
//        }
//    }
    @GetMapping(params = "name")
    public ResponseEntity<?> getCustomerByFirstName(@RequestParam(value = "name") String name){
        List<Customer> customer = customerService.getCustomerByFirstName(name);
        return customer != null && !customer.isEmpty() ? ResponseEntity.ok(customer)
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putCustomer(@PathVariable Long id, @Valid @RequestBody Customer body)
    {
        body.setId(id);
        Customer customer = customerService.updateCustomer(id,body);
        return customer != null ? ResponseEntity.ok(customer)
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id)
    {
        return customerService.deleteById(id) ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }



}
