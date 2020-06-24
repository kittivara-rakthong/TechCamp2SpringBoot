package com.techcamp2.customer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.techcamp2.customer.model.Customer;

import com.techcamp2.customer.service.CustomerService;
import com.techcamp2.customer.support.CustomerSupportTest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    private MockMvc mvc;

    private static final String urlCustomerList = "/customer/list/";
    private static final String urlCustomer = "/customer/";
    private static final String urlCustomerName = "/customer/name/";
    private static final String urlCustomerName1 = "/customer/?name=";

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        customerController = new CustomerController((customerService));
        mvc = MockMvcBuilders.standaloneSetup(customerController)
                .build();
    }

    @DisplayName("Test get customer should return customer list")
    @Test
    void testGetCustomerList() throws Exception
    {
        when(customerService.getCustomerList())
                .thenReturn(CustomerSupportTest.getCustomerList());

        MvcResult mvcResult = mvc.perform(get(urlCustomerList))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        assertEquals("1",jsonArray.getJSONObject(0).get("id").toString());
        assertEquals("AAA",jsonArray.getJSONObject(0).get("firstName").toString());
        assertEquals("aaa",jsonArray.getJSONObject(0).get("lastName").toString());
        assertEquals("001",jsonArray.getJSONObject(0).get("phoneNumber").toString());
        assertEquals("aaaa@gmail.com",jsonArray.getJSONObject(0).get("email").toString());
        assertEquals(11,jsonArray.getJSONObject(0).get("age"));

        assertEquals("2",jsonArray.getJSONObject(1).get("id").toString());
        assertEquals("BBB",jsonArray.getJSONObject(1).get("firstName").toString());
        assertEquals("bbb",jsonArray.getJSONObject(1).get("lastName").toString());
        assertEquals("002",jsonArray.getJSONObject(1).get("phoneNumber").toString());
        assertEquals("bbbb@gmail.com",jsonArray.getJSONObject(1).get("email").toString());
        assertEquals(22,jsonArray.getJSONObject(1).get("age"));
    }

    @DisplayName("Test get customer by id should return customer")
    @Test
    void testCustomerById() throws Exception {
        Long reqId = 1L;
        when(customerService.getCustomerById(reqId)).thenReturn(CustomerSupportTest.getOldCustomer());

        MvcResult mvcResult = mvc.perform(get(urlCustomer+""+reqId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("2",jsonObject.get("id").toString());
        assertEquals("OldNoon",jsonObject.get("firstName"));
        assertEquals("OldBow",jsonObject.get("lastName"));
        assertEquals("old@gmail.com",jsonObject.get("email"));
        assertEquals("0002",jsonObject.get("phoneNumber"));
        assertEquals(20,jsonObject.get("age"));
    }

    @DisplayName("Test get customer by id should return not found")
    @Test
    void testGetCustomerByIdNotFound() throws  Exception
    {
        Long reqId = 5L;
        MvcResult mvcResult = mvc.perform(get(urlCustomer+""+reqId))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @DisplayName("Test create customer should return success")
    @Test
    void testCreateCustomer() throws Exception
    {
        Customer reqCustomer = CustomerSupportTest.getReqNewCustomer();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.createCustomer(reqCustomer))
                .thenReturn(CustomerSupportTest.getResNewCustomer());

        MvcResult mvcResult = mvc.perform(post(urlCustomer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                ).andExpect(status().isCreated())
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("1",jsonObject.get("id").toString());
        assertEquals("New Name",jsonObject.get("firstName"));
        assertEquals("New Lastname",jsonObject.get("lastName"));
        assertEquals("qqqq@gmail.com",jsonObject.get("email"));
        assertEquals("004",jsonObject.get("phoneNumber"));
        assertEquals(33,jsonObject.get("age"));

    }

    @DisplayName("Test create customer with first name is empty")
    @Test
    void testCreateCustomerWithNameIdEmpty() throws Exception
    {
        Customer reqCustomer = CustomerSupportTest.getReqNewCustomer();
        reqCustomer.setFirstName("");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ow  = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.createCustomer(reqCustomer))
                .thenReturn(CustomerSupportTest.getResNewCustomer());

        MvcResult mvcResult = mvc.perform(post(urlCustomer)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                ).andExpect(status().isBadRequest())
                .andReturn();
        assertEquals("Validation failed for argument [0] in public org.springframework.http.ResponseEntity<?> com.techcamp2.customer.controller.CustomerController.createCustomer(com.techcamp2.customer.model.Customer): [Field error in object 'customer' on field 'firstName': rejected value []; codes [Size.customer.firstName,Size.firstName,Size.java.lang.String,Size]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [customer.firstName,firstName]; arguments []; default message [firstName],100,1]; default message [Please enter First name]] ",
                mvcResult.getResolvedException().getMessage());
    }

    @DisplayName("Test get customer by first name should return customer")
    @Test
    void testCustomerByFirstName() throws Exception {
        String reqName = "Tiger";
        when(customerService.getCustomerByFirstName(reqName)).thenReturn(CustomerSupportTest.getCustomerListForGetByFirstName());

        MvcResult mvcResult = mvc.perform(get(urlCustomerName1+""+reqName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONArray jsonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        assertEquals("1",jsonArray.getJSONObject(0).get("id").toString());
        assertEquals("Tiger",jsonArray.getJSONObject(0).get("firstName"));
        assertEquals("aaa",jsonArray.getJSONObject(0).get("lastName"));
        assertEquals("aaaa@gmail.com",jsonArray.getJSONObject(0).get("email"));
        assertEquals("001",jsonArray.getJSONObject(0).get("phoneNumber"));
        assertEquals(11,jsonArray.getJSONObject(0).get("age"));
    }

    @DisplayName("Test get customer by first name should return not found")
    @Test
    void testGetCustomerByFirstNameNotFound() throws  Exception
    {
        String reqName = "Cat";

        MvcResult mvcResult = mvc.perform(get(urlCustomerName1+""+reqName))
                .andExpect(status().isNotFound())
                .andReturn();

    }

    @DisplayName("Test update customer should return success")
    @Test
    void testUpdateCustomer() throws Exception
    {
        Customer reqCustomer = CustomerSupportTest.getOldCustomer();
        Long reqId = 2L;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.updateCustomer(reqId,reqCustomer))
                .thenReturn(CustomerSupportTest.getNewCustomer());

        MvcResult mvcResult = mvc.perform(put(urlCustomer+""+reqId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                ).andExpect(status().isOk())
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals("2",jsonObject.get("id").toString());
        assertEquals("Noon",jsonObject.get("firstName"));
        assertEquals("Bow",jsonObject.get("lastName"));
        assertEquals("bow@gmail.com",jsonObject.get("email"));
        assertEquals("0002",jsonObject.get("phoneNumber"));
        assertEquals(5,jsonObject.get("age"));
    }

    @DisplayName("Test update customer should return id not found")
    @Test
    void testUpdateCustomerIdNotFound() throws Exception
    {
        Customer reqCustomer = CustomerSupportTest.getOldCustomer();
        Long reqId = 2L;

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.updateCustomer(reqId,reqCustomer))
                .thenReturn(null);

        MvcResult mvcResult = mvc.perform(put(urlCustomer+""+reqId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                ).andExpect(status().isNotFound())
                .andReturn();
    }

    @DisplayName("Test delete customer should success")
    @Test
    void testDeleteCustomer() throws Exception
    {
        Long reqId = 4L;
        when(customerService.deleteById(reqId)).thenReturn(true);


        MvcResult mvcResult = mvc.perform(delete(urlCustomer+""+reqId)
                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();

        verify(customerService,times(1)).deleteById(reqId);
    }
    
    @DisplayName("Test delete customer should not found")
    @Test
    void testDeleteCustomerShouldNotFound() throws Exception
    {
        Long reqId = 4L;
        when(customerService.deleteById(reqId)).thenReturn(false);


        MvcResult mvcResult = mvc.perform(delete(urlCustomer+""+reqId)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound())
                .andReturn();

        verify(customerService,times(1)).deleteById(reqId);
    }



}
