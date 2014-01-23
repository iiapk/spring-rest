package com.iiapk.rest.utils;

public enum HttpEncoding {
    
    UTF8("UTF-8"),
    GB2312("GB2312"), 
    GBK("GBK");
    
    private String code;
    
    private HttpEncoding(String c){
        this.code = c;
    }
    
    public String getCode(){
        return this.code;
    }
}

