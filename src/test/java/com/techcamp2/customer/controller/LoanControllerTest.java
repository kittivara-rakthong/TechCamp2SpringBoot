package com.techcamp2.customer.controller;

import com.techcamp2.customer.api.LoanApi;
import com.techcamp2.customer.model.Response.GetLoanInfoResponse;
import com.techcamp2.customer.support.CustomerSupportTest;
import com.techcamp2.customer.support.LoanSupportTest;
import com.techcamp2.customer.util.Util;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LoanControllerTest {
    @Mock
    LoanApi loanApi;

    @InjectMocks
    LoanController loanController;

    private MockMvc mvc;

    private static final String urlLoan = "/loan";

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.initMocks(this);
        loanController = new LoanController(loanApi);
        mvc = MockMvcBuilders.standaloneSetup(loanController).build();
    }

    @DisplayName("Test get loan info should return loan infomation")
    @Test
    void testGetLoanInfo() throws Exception
    {
        Long reqId = 1L;

        when(loanApi.getLoanInfo(reqId)).thenReturn(LoanSupportTest.getLoanInfo());

        MvcResult mvcResult = mvc.perform(get(urlLoan+"/"+reqId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertEquals(1,jsonObject.get("id"));
        assertEquals("Ok",jsonObject.get("status"));
        assertEquals("102-222-2200",jsonObject.get("account_payable"));
        assertEquals("102-222-2200",jsonObject.get("account_receivable"));
        assertEquals(3000000.0,jsonObject.get("principal_amount"));


    }

}
