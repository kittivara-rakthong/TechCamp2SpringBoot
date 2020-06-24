package com.techcamp2.customer.support;

import com.techcamp2.customer.model.Customer;
import com.techcamp2.customer.model.Response.GetLoanInfoResponse;

public class LoanSupportTest {

    public static GetLoanInfoResponse getLoanInfo()
    {


        GetLoanInfoResponse loan = new GetLoanInfoResponse();
        loan.setId(1L);
        loan.setStatus("Ok");
        loan.setAccountPayable("102-222-2200");
        loan.setAccountReceivable("102-222-2200");
        loan.setPrincipalAmount(3000000.0);




        return loan;
    }

}
