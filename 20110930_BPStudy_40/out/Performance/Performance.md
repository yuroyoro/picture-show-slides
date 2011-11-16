!SLIDE

# Benchmarking _Finagle_


!SLIDE

A simple http echo application.

```scala
object SimpleHttpServer {
  def main(args: Array[String]) =  {

    val service = new Service[HttpRequest, HttpResponse] {
      def apply(request: HttpRequest) = {
        val response = new DefaultHttpResponse(HTTP_1_1, OK)
        response.addHeader("content-type", "text/plain")
        response.setContent(copiedBuffer("%s at %s" format(request.getUri, new java.util.Date), UTF_8))
        response.setHeader(Names.CONTENT_LENGTH,  response.getContent.readableBytes)
        Future.value(response)
      }
    }

    println("start")
    val server: Server = ServerBuilder()
      .codec(Http())
      .maxConcurrentRequests(5000)
      .bindTo(new InetSocketAddress(8000))
      .name("HttpEchoServer")
      .build(service)
    println("boot")
  }
}
```


!SLIDE

## <span class="cy">Node.js</span>

<code> (v0.5.7)</code>

```js
    var http = require('http'),
        url  = require('url');

    var server = http.createServer(function(req, res) {
      res.writeHead(200, {'content-type': 'text/plain'});
      res.end(url.parse(req.url)['pathname'] + "at" +  new Date);
    });

    server.listen(8000);
    console.log('Server running at http://localhost:8000/');
```

!SLIDE

## __Tornado__

<code> with Python 2.6, gunicorn + gevent </code>

```py
import tornado.httpserver
import tornado.ioloop
import tornado.options
import tornado.wsgi
import datetime

from tornado.options import define, options

define("port", default=8000, help="run on the given port", type=int)

def echo_app(environ, start_response):
    status = "200 OK"
    response_headers = [("Content-type", "text/plain")]
    start_response(status, response_headers)
    return [environ['PATH_INFO'] +" at " +  datetime.datetime.now().strftime(u'%Y/%m/%d %H:%M:%S')]

def main():
    tornado.options.parse_command_line()
    container = tornado.wsgi.WSGIContainer(echo_app)
    http_server = tornado.httpserver.HTTPServer(container)
    http_server.listen(options.port)
    tornado.ioloop.IOLoop.instance().start()

if __name__ == "__main__":
    main()
```

!SLIDE

### <span>EventMachien</span>

<code>with ruby-1.9.2-p290</code>


```rb
#!/usr/bin/env ruby
# -*- coding: utf-8 -*-
require 'rubygems'
require 'eventmachine'
require 'evma_httpserver'

class Handler  < EventMachine::Connection
  include EventMachine::HttpServer

  def process_http_request
    res = EventMachine::DelegatedHttpResponse.new(self)

    res.status = 200
    res.content_type 'text/plain'
    res.content = @http_path_info + " at " + Time.now.to_s
    res.send_response
  end
end

EventMachine::run do
  EventMachine::start_server("0.0.0.0", 8000, Handler)
  puts "http server start, port 8000"
end
```

!SLIDE

On <span>Aamazon EC2.</span>

- _Large Instance_  m1.large (64-bit)

    <div class="small">
    Memory 7.5 GB<br/>
    4 ECU (2 virtual cores with 2 ECU each)<br/>
    High IO<br/>
    </div><br/>

- __High-CPU Extra Large Instance__ c1.xlarge (64-bit)

    <div class="small">
    Memory 7.5 GB<br/>
    20 ECU (8 virtual cores with 2.5 ECU each)<br/>
    High IO<br/>
    </div>
    <br/>

!SLIDE

* _5_ times
* __2000__ concurrency reqeuests
* Total <span>8000</span> reqeuests

!SLIDE

_ab_ -c __2000__ -n <span>8000</span> http://example.com/hoge

!SLIDE

# __Results__

![result](Performance/chart_1.png)

[detail](https://docs.google.com/spreadsheet/ccc?key=0AtXoTuDY71j1dGZ2OUZ1dDcza1hHR0Z2dWZPVDlXeUE&hl=en_US)

!SLIDE

### _Large Instance_  m1.large

<table>
  <thead>
    <tr>
      <th></th>
      <th>Node.js</th>
      <th>Tornado</th>
      <th>EventMachien</th>
      <th>Finagle</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td class="num">1st</td>
      <td>2270.32</td>
      <td>1877.48</td>
      <td>2582.19</td>
      <td>2294.70</td></tr>
    <tr>
      <td class="num">2nd</td>
      <td>1798.75</td>
      <td>1987.68</td>
      <td>1720.71</td>
      <td>2423.77</td></tr>
    <tr>
      <td class="num">3rd</td>
      <td>1304.78</td>
      <td>2010.48</td>
      <td>2435.43</td>
      <td>2415.71</td></tr>
    <tr>
      <td class="num">4th</td>
      <td>1254.55</td>
      <td>1777.23</td>
      <td>2574.30</td>
      <td>2603.82</td></tr>
    <tr>
      <td class="num">5th</td>
      <td>1321.05</td>
      <td>2143.58</td>
      <td>2609.27</td>
      <td>2590.74</td></tr>
    <tr class="ave">
      <td>Ave</td>
      <td>1589.89</td>
      <td>1959.29</td>
      <td>2384.38</td>
      <td>2465.748</td></tr>
  </tbody>
</table>

!SLIDE

### __High-CPU Extra Large Instance__ c1.xlarge

<table>
  <thead>
    <tr>
      <th></th>
      <th>Node.js</th>
      <th>Tornado</th>
      <th>EventMachien</th>
      <th>Finagle</th>
    </tr>
  </thead>
  <tbody>
    <tr><td class="num">1st</td>
      <td>2270.32</td>
      <td>1877.48</td>
      <td>2582.19</td>
      <td>2294.70</td></tr>
    <tr><td class="num">2nd</td>
      <td>1798.75</td>
      <td>1987.68</td>
      <td>1720.71</td>
      <td>2423.77</td></tr>
    <tr><td class="num">3rd</td>
      <td>1304.78</td>
      <td>2010.48</td>
      <td>2435.43</td>
      <td>2415.71</td></tr>
    <tr><td class="num">4th</td>
      <td>1254.55</td>
      <td>1777.23</td>
      <td>2574.30</td>
      <td>2603.82</td></tr>
    <tr><td class="num">5th</td>
      <td>1321.05</td>
      <td>2143.58</td>
      <td>2609.27</td>
      <td>2590.74</td></tr>
    <tr class="ave"><td>Ave</td>
      <td>2640.38</td>
      <td>2778.152</td>
      <td>2602.292</td>
      <td>3311.632</td></tr>
  </tbody>
</table>

