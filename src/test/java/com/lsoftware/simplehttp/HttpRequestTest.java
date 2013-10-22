package com.lsoftware.simplehttp;

import static org.junit.Assert.*;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class HttpRequestTest {

    @Test
    public void testSimpleGet() {
        HttpRequest request = HttpRequestBuilder.Get("http://www.google.com").build();
        assertNotNull(request);
        
        request.execute();
        
        int status = request.getStatusCode();
        assertEquals(HttpStatus.SC_OK, status);
        
        String body = request.getResponse();
        assertTrue(StringUtils.isNotBlank(body));
    }
    
    @Test
    public void testSimpleGetWithoutExecute() {
        HttpRequest request = HttpRequestBuilder.Get("http://www.google.com").build();
        assertNotNull(request);
        
        int status = request.getStatusCode();
        assertEquals(-1, status);
        
        String body = request.getResponse();
        assertTrue(StringUtils.isBlank(body));
    }
    
    @Test
    public void testSimplePost() {
        //TODO simple post method test
    }
    
    @Test
    public void testSimplePostWithoutExecute() {
        //TODO simple post method without execute test
    }

}
