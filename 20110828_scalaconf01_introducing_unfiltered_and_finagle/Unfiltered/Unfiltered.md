!SLIDE

__ᘀᘐᘠᘰᙀᙐᙠᙰᘁᘑᘡᘱᙁᙑᙡᙱᘂᘒᘢᘲᙂᙒ__
_ᙢᙲᘃᘓᘣᘳᙃᙓᙣᙳᘄᘔᘤᘴᙄᙔᙤᙴᘅᘕᘥ_
<span class="comment">ᙕᙥᙵᘆᘖᘦᘶᙆᙖᙦᙶᚆᘇᘗᘧᘷᙇᙗᙧ,ᘈᘘᘨ</span>
ᘸᙈᙘᙨᘉᘙᘩᘹᙉᙙᙩᘊᘚᘪᘺᙊᙚᙪᘋᘛᘫᘻᙋ
<span class="comment">ᙛᙫᘌᘜᘬᘼᙌᙜᙬᘍᘝᘭᘽᙍᙝ᙭ᘎᘞᘮᘾᙎᙞ᙮ᘏᘟ</span>
_ᘯᘿᙏᙟᙯᔀᔐᔠᔰᕀᕐᕠᕰᖀᖐᖠᖰᗀᗐᗠᗰᔁᔑᔡᔱ_
__ᘀᘐᘠᘰᙀᙐᙠᙰᘁᘑᘡᘱᙁᙑᙡᙱᘂᘒᘢᘲᙂᙒ__


!SLIDE

## <em>Unfiltered</em>
[<em class="hc">https://github.com/n8han/Unfiltered</em>](https://github.com/n8han/Unfiltered)

!SLIDE

From <span>NYC</span><br/>
[@n8han](http://twitter.com/n8han)

!SLIDE

ヽ(\*ﾟдﾟ)ノ＜

A __toolkit__ for servicing _HTTP requests_

!SLIDE

<span class="comment">NOT</span> __Framework__

!SLIDE

### Who’s Using _Unfiltered_?

* Remember The Milk
* Novus
* Trust Metrics
* ... and more!

!SLIDE

ヽ(\*ﾟдﾟ)ノ＜

This Presentation made by <span>Picture-show</span> based _Unfiltered_ .

!SLIDE

## CONCEPT

* __&hearts;__'s _pattern matching_
* __&hearts;__'s _combinator_
* loosly coupled _components_
* _embed_ servers

!SLIDE

_Request_

__Pattern Matching__

!SLIDE

### METHOD, PATH

<div class='hc'>
<p class="sub comment">GET /hello/yuroyoro</p>
<span>case</span> <strong>GET</strong>(<em>Path</em>(Seg("hello"::name::Nil))) =>
  ResponseString(
    "ヽ(\*ﾟдﾟ)ノ＜  Hello, %s" format name )
</div>

!SLIDE

### Parameters

<div class="hc">
<p class="sub comment">GET /hello?name=yuroyoro</p>
<span>case</span> <strong>GET</strong>(<em>Params</em>(params))) =>
  ResponseString(
    "ヽ(\*ﾟдﾟ)ノ＜  Hello,  %s" format params("name").head )
</div>

!SLIDE

### Basic Auth

<div class="hc">
<em>BasicAuth</em>(<strong>creds</strong>, _ ) => creds <span>match</span> {
  <span>case</span> ("jane", "j@n3") => <span class="comment">// jane stuff</span>
  <span>case</span> ("jim", "j1m") =>   <span class="comment">// jim stuff</span>
  <span>case</span> _ =>                <span class="comment">// default stuff</span>
}
</div>

!SLIDE

### ContentType

<div class="hc">
<em>Accepts</em>(<strong>fmt</strong>, _ ) => fmt <span>match</span> {
  <span>case</span> 'json => <span class="comment">// json stuff</span>
  <span>case</span> 'xml =>  <span class="comment">// xml stuff</span>
  <span>case</span> _ =>     <span class="comment">// otherwise default stuff</span>
}
</div>

!SLIDE

### _Response_ __Combinators__

!SLIDE

# __~>__

!SLIDE
<div class='hc small'>
<span>import</span> javax.servlet.http.{HttpServletResponse => <em>R</em>}
<span>type</span> ResponseFunction = <em>R</em> => <em>R</em>
<span>trait</span> Responder <span>extends</span> ResponseFunction {
  <span>def</span> respond(res: R)
  <span>def</span> <strong>~></strong> (that: ResponseFunction) = <span>new</span> ChainResponse(
    this andThen that
  )
}
<span>class</span> ChainResponse(f: ResponseFunction) <span>extends </span>Responder {
  <span>def</span> respond(res: R) = f(res)
}
</div>

!SLIDE

_ResponseFunction_

__~>__

_ResponseFunction_

!SLIDE

_(R => R)_ __~>__ _(R => R)_

!SLIDE

<div class='hc'>
<span class="comment">// Response Body</span>
<em>ResponseString</em>("Hello")
</div>

!SLIDE

<div class='hc'>
<span class="comment">// Response Body</span>
<em>ResponseString</em>("Hello")
<strong>~></strong>
<span class="comment">// 200 OK</span>
<em>OK</em> <span class="comment"> // or Status(200) </span>
</div>

!SLIDE

<div class='hc'>
<span class="comment">// Response Body</span>
<em>ResponseString</em>("Hello")
<strong>~></strong>
<span class="comment">// 200 OK</span>
<em>OK</em> <span class="comment"> // or Status(200) </span>
<strong>~></strong>
<span class="comment">// Content-Type</span>
<em>PlainTextContent</em>
</div>

!SLIDE

<div class='hc'>
<span class="comment">// Response Body</span>
<em>ResponseString</em>("Hello")
<strong>~></strong>
<span class="comment">// 200 OK</span>
<em>OK</em> <span class="comment"> // or Status(200) </span>
<strong>~></strong>
<span class="comment">// Content-Type</span>
<em>PlainTextContent</em>
<strong>~></strong>
<span class="comment">// other headers </span>
<em>ResponseHeader</em>("x-runtime",
  TimeFrom(start) :: Nil)
</div>

!SLIDE

### <span>Plans</span> & _Intent_

!SLIDE

_Intent_ is a __PartialFunction.__

<div class='hc small'>
<span>val</span> hello = {
  <span>case</span> GET(Path(Seg("hello"::name::Nil))) =>
    OK ~> ResponseString("('.')/~ Hello,  %s" format name )
  <span>case</span> _ =>
    MethodNotAllowed _> ResponseString("GET only.")
}
</div>

!SLIDE

<span>Plan</span> binds a _Intents_ to server (Netty/Jetty...).

<div class='hc small'>
<span class="comment">// This plan binds to Servlet Filter.</span>
<span>object</span> Echo
  <span>extends</span> unfiltered.<strong>filter</strong>.Plan {
  <span>def</span> intent = {
    <span>case</span> Path(Seg(p :: Nil)) => ResponseString(p)
  }
}
</div>

!SLIDE

### __Bindings__ & _Servers_

!SLIDE

__Binding__ is implementaion of Request/Response interface.

* Servlet Filters (unfiltered-filter)
* Netty Channels (unfiltered-netty)

!SLIDE

_Server modules_ define runnable servers.(optional)

* unfiltered-jetty
* unfiltered-jetty-ajp
* unfiltered-netty-server

!SLIDE

### Simple Key-Value Strore

!SLIDE
<div class='hc small'>
<span>import</span> unfiltered.request.\_
<span>import</span> unfiltered.response.\_
<span>object</span> SillyStore <span>extends</span> unfiltered.filter.<span class="cy">Plan</span>{
  @volatile private var store = Map.empty[String, Array[Byte]]
  <span>def</span> <span class="cy">intent</span> = {
    <span>case</span> req @ <em>Path</em>(<em>Seg</em>("record" :: id :: Nil)) => req <span>match</span> {
      <span>case</span> <em>GET</em>(_) =>
        store.get(id).map(ResponseBytes).getOrElse {
          <strong>NotFound</strong> ~> <strong>ResponseString</strong>("No record: " + id)
        }
      <span>case</span> <em>PUT</em>(_) =>
        SillyStore.synchronized {
          store = store + (id -> Body.bytes(req))
        }
        <strong>Created</strong> ~> <strong>ResponseString</strong>("Created record: " + id)
      <span>case</span> _ =>
        <strong>MethodNotAllowed</strong> ~> <strong>ResponseString</strong>("Must be GET or PUT")
    }
  }
}
</div>


!SLIDE

<span class="l or">DEMO</span>
