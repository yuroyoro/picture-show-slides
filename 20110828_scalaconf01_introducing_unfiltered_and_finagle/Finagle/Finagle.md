!SLIDE

__ᘀᘐᘠᘰᙀᙐᙠᙰᘁᘑᘡᘱᙁᙑᙡᙱᘂᘒᘢᘲᙂᙒ__
_ᙢᙲᘃᘓᘣᘳᙃᙓᙣᙳᘄᘔᘤᘴᙄᙔᙤᙴᘅᘕᘥ_
<span class="comment">ᙕᙥᙵᘆᘖᘦᘶᙆᙖᙦᙶᚆᘇᘗᘧᘷᙇᙗᙧ,ᘈᘘᘨ</span>
ᘸᙈᙘᙨᘉᘙᘩᘹᙉᙙᙩᘊᘚᘪᘺᙊᙚᙪᘋᘛᘫᘻᙋ
<span class="comment">ᙛᙫᘌᘜᘬᘼᙌᙜᙬᘍᘝᘭᘽᙍᙝ᙭ᘎᘞᘮᘾᙎᙞ᙮ᘏᘟ</span>
_ᘯᘿᙏᙟᙯᔀᔐᔠᔰᕀᕐᕠᕰᖀᖐᖠᖰᗀᗐᗠᗰᔁᔑᔡᔱ_
__ᘀᘐᘠᘰᙀᙐᙠᙰᘁᘑᘡᘱᙁᙑᙡᙱᘂᘒᘢᘲᙂᙒ__

!SLIDE

# _Finagle_
[<em class="hc">https://github.com/twitter/finagle</em>](https://github.com/twitter/finagle)

!SLIDE

__Asynchronous__

_RPC_

<span class="cy">Server/Client</span>

<span>Framework</span>.

!SLIDE

__Finagle__ has been used in _twitter search_ backends.

!SLIDE

![おっぱい](Finagle/Oppai.png)

!SLIDE

### __Finagle__ on

![Relationship between your RCP client or server, Finagle, Netty, and Java Libraries ](Finagle/FinagleRelationship.png)

__JVM__ / _NIO_ / <span>Netty</span>

!SLIDE

### __Protocol Independent__

* HTTP/HTTPS
* HTTP streaming (Comet)
* Thrift
* memcached
* ... more

!SLIDE

### __Features__

* Connection Pooling
* Load Balancing
* Failure Detection
* Failover/Retry
* Distributed Tracing (a la Dapper)
* Service Discovery (e.g.,  via Zookeeper)
* Rich Statistics
* Native OpenSSL Bindings

!SLIDE

### __Primitive__ Objects

* __Futures__
* _Services_
* <span>Filters</span>
* <span class="cy">Codecs</span>

!SLIDE

## __Futures__

<code>
<span>class</span> Future [+A] <span>extends</span> TryLike[A, Future] {
  <span>def</span> apply () : A
}
</code>

!SLIDE

*abstraction* for all __asynchronous computation__.

<code>
<span>val</span> req: HttpRequest =
  <span>new</span> DefaultHttpRequest(HTTP_1_1, GET, "/")
<span>val</span> <em>resFuture: Future</em>[HttpResponse] = client(req)
</code>

!SLIDE

register a _callbacks_.

<code>
<em>resFuture</em> <strong>onSuccess</strong> { responseFuture =>
  println(responseFuture)
} <strong>onFailure</strong> { exception =>
  println(exception)
}
</code>

!SLIDE

_callbacks_ will be invoked __asynchronously__.

!SLIDE

__combine__ _Futures_.

<code>
<span>val</span> authenticatedUser: Future[User] =
  User.authenticate(email, password)
<br/>
<span>val</span> lookupTweets: Future[Seq[Tweet]] =
  authenticatedUser <strong>flatMap</strong> { user =>
    Tweet.findAllByUser(user)
  }
</code>

!SLIDE

using __for expression__ instead of flatMap.

<code>
<span>for</span> {
  user <- User.authenticate(email, password)
  tweets <- Tweet.findAllByUser(user)
} yield tweets
</code>

!SLIDE

other features.

* _Timeouts_
    <code class="small">resFuture.<strong>within(1.second)</strong> onSuccess { response => ... }</code>
* _Promises_

    <code>can be both read and written.</code>
* _Future Pools_

    <code>makes blocking operation asynchronous.</code>

!SLIDE

## __Services__

<code>
<span>class</span> <strong>Service</strong> [-Req, +Rep]
  <span>extends</span> (Req) => Future[Rep] {
  <span>def</span> apply (request: Req) : Future[Rep]
}
</code>

!SLIDE

__asynchronous function__

from <span>Request</span>

to _Future_

!SLIDE

<code>
<span>val</span> service = <span>new</span> <strong>Service</strong>[<span>HttpRequest</span>, <em>HttpResponse</em>] {
  <span>def</span> apply(request: <span>HttpRequest</span>) = {
    <span>val</span> response = <span>new</span> DefaultHttpResponse(HTTP_1_1, OK)
    response.setContent(copiedBuffer("hello world", UTF_8))
    <em>Future.value(response)</em>
  }
}
</code>

!SLIDE

Building _Server_ from __Service__.

<code>
<span>val</span> <em>server: Server</em> = ServerBuilder()
  .codec(Http())
  .bindTo(new InetSocketAddress(8080))
  .name("SimpleHttpServer")
  .build(<strong>service</strong>)
</code>

!SLIDE

Building _Client_ from __Service__.

<code>
<span>val</span> <em>client</em>: <strong>Service[HttpRequest, HttpResponse]</strong>> =
  ClientBuilder()
    .codec(Http())
    .hosts(address)
    .build()
</code>

!SLIDE

## __Filters__

<code class="small">
<span>class</span> <strong>Filter</strong> [-ReqIn, +RepOut, +ReqOut, -RepIn]
  <span>extends</span> (ReqIn, <em>Service</em>[ReqOut,  RepIn]) => <em>Future</em>[RepOut] {
  <span>def</span> apply (request:ReqIn,
      service:<em>Service</em>[ReqOut, RepIn]) : <em>Future</em>[RepOut]
}
</code>

!SLIDE

acts as a __decorator/transformer__ of a _Service_.

!SLIDE

<code class="small">
<span>val</span> authorize =
  <span>new</span> <strong>SimpleFilter</strong>[HttpRequest, HttpResponse] {
  <span>def</span> apply(
    request:<em>HttpRequest</em>,
    continue:<em>Service</em>[HttpRequest, HttpResponse]
  ) = {
    <span>if</span> ("open sesame" == request.getHeader("Authorization")) {
      continue(request)
    } <span>else</span> {
      Future.value(
        <span>new</span> DefaultHttpResponse(HTTP_1_1, FORBIDDEN))
    }
  }
}
</code>

!SLIDE

define _service_ with __filters__.

<code>
<span>val</span> service : Service[HttpRequest, HttpResponse] =
  <strong>authorize</strong> <span>andThen</span> <strong>otherFilter</strong> <span>andThen</span> <em>service</em>
</code>

!SLIDE

## __Codecs__

<code>
<span>trait</span> <strong>Codec</strong> [Req, Rep]
</code>

!SLIDE

_encodes_ and _decodes_ wire protocols

* HTTP/HTTPS
* Thrift
* memcached
* ...

!SLIDE

binds service on _HTTP_.

<code>
<span>val</span> <em>server: Server</em> = ServerBuilder()
  <strong>.codec(Http())</strong>
  .bindTo(new InetSocketAddress(8080))
  .name("SimpleHttpServer")
  .build(service)
</code>

!SLIDE

binds service on _Thrift_.

<code>
<span>val</span> <em>server: Server</em> = ServerBuilder()
  <strong>.codec(ThriftServerFramedCodec())</strong>
  .bindTo(new InetSocketAddress(8080))
  .name("SimpleHttpServer")
  .build(service)
</code>

!SLIDE

## __Servers__

![FiterChain](Finagle/Filters.png)

!SLIDE

RPC servers are built out of a _Service_ and some __Filter__ objects.

!SLIDE

## Threading Model

!SLIDE

__Event Loop__


!SLIDE

<span>while</span>(1){

!SLIDE

<span>for</span>(;;){

!SLIDE

### <span>IMPORTANT!!</span>

Finagle requires __avoid blocking operation__ in the Finagle _event loop_

!SLIDE

Like <span class="cy">Node.js</span>

!SLIDE

But, __Finagle's API__ provides your code _asynchronously_.

* __Futures__
* _Services_
* <span>Filters</span>
* <span class="cy">Codecs</span>

!SLIDE

![Threading](Finagle/ThreadEx.png)


!SLIDE

![Bolcking](Finagle/ThreadExNonBlockingServer.png)

!SLIDE

<span class="l or">DEMO</span>


