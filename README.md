Uber API client for Android & Java
==================================
[![Build Status](https://travis-ci.org/vsima/uber-java-client.svg?branch=master)](https://travis-ci.org/vsima/uber-java-client)

A java wrapper for Uber's REST API for Android and Java applications. 


Download
--------

Download [the latest JAR][1] or grab via Maven Central:
```xml
<dependency>
  <groupId>com.victorsima</groupId>
  <artifactId>uber-java-client</artifactId>
  <version>0.0.1</version>
</dependency>
```
or Gradle:
```groovy
compile 'com.victorsima:uber-java-client:0.0.1'
```

Usage
-----
```java
UberClient client = new UberClient("v1", "YOUR_OAUTH_ID", "YOUR_OAUTH_SECRET", RestAdapter.LogLevel.BASIC);
client.setServerToken("YOUR_SERVER_TOKEN");
Products products = client.getApiService().getProducts(40.74844,-73.985664);
```


Testing
--------
  * rename src/test/resources/_test.properties to test.properties and populate with your application data
  * run ./gradle test
 

License
-------------

     The MIT License (MIT)
     
     Copyright (c) 2014 Victor Sima <vic.sima@gmail.com>
     
     
     Permission is hereby granted, free of charge, to any person obtaining a copy
     of this software and associated documentation files (the "Software"), to deal
     in the Software without restriction, including without limitation the rights
     to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
     copies of the Software, and to permit persons to whom the Software is
     furnished to do so, subject to the following conditions:
     
     The above copyright notice and this permission notice shall be included in
     all copies or substantial portions of the Software.
     
     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
     IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
     FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
     AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
     LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
     OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
     THE SOFTWARE.
 
 
[1]: http://repo1.maven.org/maven2/com/victorsima/uber-java-client/0.0.1/uber-java-client-0.0.1.jar
 

