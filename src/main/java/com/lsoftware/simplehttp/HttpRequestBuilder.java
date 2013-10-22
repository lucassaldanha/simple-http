package com.lsoftware.simplehttp;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;

public class HttpRequestBuilder {
    
    private HttpMethod httpMethod = null;
    private HttpMethodParams httpParams = null;
    
    public HttpRequestBuilder(String url, HttpMethodType type) {
        setUrl(url);
        setHttpMethodType(type, url);
    }
    
    public HttpRequestBuilder(String url) {
        this(url, HttpMethodType.GET);
    }
    
    private void setUrl(String url) {
        if(StringUtils.isBlank(url)) {
            throw new IllegalStateException(String.format("Url key can't be blank [url=]", url));
        }
    }
    
    private void setHttpMethodType(HttpMethodType type, String url) {
        switch (type) {
            case GET: {
                this.httpMethod = new GetMethod(url);
                break;
            }
            case POST: {            
                this.httpMethod = new PostMethod(url);
                break;
            }
        }
    }
    
    public HttpRequestBuilder addParam(String key, Object value) {
        if(StringUtils.isBlank(key)) {
            throw new IllegalStateException(String.format("Http paramenter key can't be blank [key=%s, value=%s]", key, value));
        }
        if(value == null) {
            throw new IllegalStateException(String.format("Http paramenter paramenter can't be blank [key=%s, value=%s]", key, value));
        }
        
        if(httpParams == null) {
            httpParams = new HttpMethodParams();
        }
        
        httpParams.setParameter(key, value);
        return this;
    }
    
    public HttpRequestBuilder addHeader(String headerName, String headerValue) {
        if(StringUtils.isBlank(headerName)) {
            throw new IllegalStateException(
                    String.format("Http request header name can't be blank [headerName=%s, headerValue=%s]", headerName, headerValue));
        }
        if(StringUtils.isBlank(headerValue)) {
            throw new IllegalStateException(
                    String.format("Http request header value can't be blank [headerName=%s, headerValue=%s]", headerName, headerValue));
        }
        
        Header h = new Header(headerName, headerValue);
        httpMethod.addRequestHeader(h);
        return this;
    }
    
    public HttpRequest build() {
        HttpClient client = new HttpClient();
        if(httpParams != null){
            httpMethod.setParams(httpParams);
        }
        return new HttpRequest(client, httpMethod);
    }
    
    public static HttpRequestBuilder Get(String url) {
        return new HttpRequestBuilder(url, HttpMethodType.GET);
    }
    
    public static HttpRequestBuilder Post(String url) {
        return new HttpRequestBuilder(url, HttpMethodType.POST);
    }
}
