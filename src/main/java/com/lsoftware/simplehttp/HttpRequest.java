package com.lsoftware.simplehttp;

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.CharStreams;

public class HttpRequest {
    
    private static final Logger LOG = LoggerFactory.getLogger(HttpRequest.class);
    
    private HttpClient client = null;
    private HttpMethod method = null;

    protected HttpRequest(HttpClient client, HttpMethod method) {
        this.client = client;
        this.method = method;
    }
    
    /**
     * Executes the configured HTTP method in the configured HTTP client.
     * @return the HttpRequest object which made the request
     */
    public HttpRequest execute() {
        if(client != null && method != null) {
            try {
                if(!method.isRequestSent()) {
                    LOG.debug(String.format("Executing http method [url=%s, method=%s]", method.getURI().toString(), method.getClass().getSimpleName()));
                    client.executeMethod(method);
                }
            } catch (IOException e) {
                LOG.error("Error executing http method", e);
            }
        }
        return this;
    }
    
    /**
     * Returns the resulting HTTP status code of the request.
     * @return an integer representing the request status code. If the request wasn't sent to the server returns -1.
     */
    public int getStatusCode() {
        if(method.isRequestSent()) {
            return method.getStatusCode();
        } else {
            return -1;
        }
    }
    
    /**
     * Returns the resulting response body of the request as a String.
     * @return a String with the content of the response body of the request. 
     * If the request wasn't sent or any error ocurred during the retrieving of the response body, returns null     
     */
    public String getResponse() {
        try {
            if(method.isRequestSent()) {
                return CharStreams.toString(new InputStreamReader(method.getResponseBodyAsStream()));
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }
    
    /**
     * Returns the {@link HttpClient} object used in the request.
     * @return a {@link HttpClient} object
     */
    public HttpClient getClient() {
        return client;
    }
    
    /**
     * Returns the {@link HttpMethod} object used in the request.
     * @return a {@link HttpMethod} object
     */
    public HttpMethod getMethod() {
        return method;
    }
}
