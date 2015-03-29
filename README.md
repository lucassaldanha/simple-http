Simple HTTP
===========

A library to simplify HTTP methods execution in Java
----------------------------------------------------

We all know that sometimes we need to do a HTTP request but we don't have much time to spent. Java have a lot of awesome HTTP libraries but all of them are so complete that we need to write a lot of lines just to do a GET, for example.

Simple HTTP relies on top of Apache Http Components (http://hc.apache.org/httpclient-3.x/) to provide simple HTTP methods execution.

**IMPORTANT**: note that newer versions of Http Apache Components ([4.2 and over](http://mvnrepository.com/artifact/org.apache.httpcomponents/fluent-hc)) comes with a built-in fluent api. If you are using newer versions of Http Components I recomend you to use their fluent api implementation ([see the docs] (https://hc.apache.org/httpcomponents-client-ga/tutorial/html/fluent.html)).

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
    
Limitations
-----------

In the current version there is support to GET and POST http methods. I'll try to provide more methods on the next versions. Feel free to contribute! :)

License
----------

Copyright [2013] [LSoftware]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
