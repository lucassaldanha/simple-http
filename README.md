Simple HTTP
===========

A library to simplify HTTP methods execution in Java
----------------------------------------------------

We all know that sometimes we need to do a HTTP request but we don't have much time to spent. Java have a lot of awesome HTTP libraries but all of them are so complete that we need to write a lot of lines just to do a GET, for example.

Simple HTTP relies on top of Apache Http Components (http://hc.apache.org/httpclient-3.x/) to provide simple HTTP methods execution.

Usage
-----

Here are some examples of the simplest usage of the library.

**Do a simple Get method**

    HttpRequestBuilder.Get("http://www.google.com").build().execute();
    
**Do a simple Post method**

    HttpRequestBuilder.Post("http://www.google.com")
                      .addParam("user", "mathew")
                      .addParam("password", "ache12#")
                      .build()
                      .execute();

**Do a Get method with required header**

    HttpRequestBuilder.Get("http://www.google.com")
                      .addHeader("Header-Name", "header_value")
                      .build()
                      .execute();

Need more power? You choose! Anytime you need you can get the reference to the **HttpClient** and **HttpMethod** objects from the Apache Http Components library. Let's do some dirty work...

**Getting the reference to HttpClient and HttpMethod objects**

    HttpRequest request = HttpRequestBuilder.Get("http://www.google.com").build();  //Building our HttpRequest object
    HttpClient client = request.getClient();
    HttpMethod method = request.getMethod();
