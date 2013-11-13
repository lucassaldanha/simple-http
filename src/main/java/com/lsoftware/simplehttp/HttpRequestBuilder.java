package com.lsoftware.simplehttp;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
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
   
    public HttpRequestBuilder setByteArrayContent(byte[] content) {
        return setContent(new ByteArrayRequestEntity(content));
    }
    
    public HttpRequestBuilder setByteArrayContent(byte[] content, String contentType) {
        return setContent(new ByteArrayRequestEntity(content, contentType));
    }
    
    public HttpRequestBuilder setFileContent(File content, String contentType) {
        return setContent(new FileRequestEntity(content, contentType));
    }
    
    public HttpRequestBuilder setInputStreamContent(InputStream content) {
        return setContent(new InputStreamRequestEntity(content));
    }
    
    public HttpRequestBuilder setInputStreamContent(InputStream content, String contentType) {
        return setContent(new InputStreamRequestEntity(content, contentType));
    }
    
    public HttpRequestBuilder setMultipartContent(Part[] parts, HttpMethodParams params) {
        return setContent(new MultipartRequestEntity(parts, params));
    }
    
    public HttpRequestBuilder setStringContent(String content) {
        return setContent(new StringRequestEntity(content));
    }
    
    public HttpRequestBuilder setStringContent(String content, String contentType, String charset) {
        try {
            return setContent(new StringRequestEntity(content, contentType, charset));
        } catch (UnsupportedEncodingException ex) {
            throw  new RuntimeException(ex);
        }
    }
    
    private HttpRequestBuilder setContent(RequestEntity entity) {
        if (!(httpMethod instanceof PostMethod)) {
            throw new IllegalStateException("Http request must be POST.");
        }
        PostMethod post = (PostMethod) httpMethod;
        if (entity == null) {
            throw new IllegalStateException("Content can't be null.");
        }
        post.setRequestEntity(entity);
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
