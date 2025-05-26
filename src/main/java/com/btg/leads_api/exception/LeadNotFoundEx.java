package com.btg.leads_api.exception;

import com.btg.leads_api.service.impl.LeadsServiceImpl;

public class LeadNotFoundEx extends RuntimeException{
    public LeadNotFoundEx(String message){
        super(message);
    }
}
