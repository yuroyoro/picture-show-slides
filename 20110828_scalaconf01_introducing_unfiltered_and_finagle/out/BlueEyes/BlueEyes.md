!SLIDE

__ᘀᘐᘠᘰᙀᙐᙠᙰᘁᘑᘡᘱᙁᙑᙡᙱᘂᘒᘢᘲᙂᙒ__
_ᙢᙲᘃᘓᘣᘳᙃᙓᙣᙳᘄᘔᘤᘴᙄᙔᙤᙴᘅᘕᘥ_
<span class="comment">ᙕᙥᙵᘆᘖᘦᘶᙆᙖᙦᙶᚆᘇᘗᘧᘷᙇᙗᙧ,ᘈᘘᘨ</span>
ᘸᙈᙘᙨᘉᘙᘩᘹᙉᙙᙩᘊᘚᘪᘺᙊᙚᙪᘋᘛᘫᘻᙋ
<span class="comment">ᙛᙫᘌᘜᘬᘼᙌᙜᙬᘍᘝᘭᘽᙍᙝ᙭ᘎᘞᘮᘾᙎᙞ᙮ᘏᘟ</span>
_ᘯᘿᙏᙟᙯᔀᔐᔠᔰᕀᕐᕠᕰᖀᖐᖠᖰᗀᗐᗠᗰᔁᔑᔡᔱ_
__ᘀᘐᘠᘰᙀᙐᙠᙰᘁᘑᘡᘱᙁᙑᙡᙱᘂᘒᘢᘲᙂᙒ__

!SLIDE

# _BlueEyes_
[<em class="hc">https://github.com/jdegoes/blueeyes</em>](https://github.com/jdegoes/blueeyes)

!SLIDE

__あばばばばばばばば__

!SLIDE

_(　ﾟ∀ﾟ)o彡°おっぱい！おっぱい！_

!SLIDE

<style type="text/css">
<!--
pre { white-space: pre-wrap; font-family: monospace; color: #000000; background-color: #ffffff; }
body { font-family: monospace; color: #000000; background-color: #ffffff; }
.Constant { color: #ff6060; }
.Special { color: #ff40ff; }
.Statement { color: #ffff00; }
-->
</style>

<code class="small">
<span>trait</span> <em>EmailServices</em> <span>extends</span> <em>BlueEyesServiceBuilder</em> {
 <span>val</span> emailService = service(<span class="Constant">&quot;email&quot;</span>, <span class="Constant">&quot;1.32&quot;</span>) { context <span>=&gt;</span>
   startup {
     loadContactList(context.config(<span class="Constant">&quot;contactFile&quot;</span>))
   } -&gt;
   request { contactList <span>=&gt;</span>
     path(<span class="Constant">&quot;/emails/&quot;</span>) {
       contentType(application/json) {
          get { request <span>=&gt;</span>
            ...
            Future.sync(
                <em>HttpResponse</em>(
                  content = <em>Some</em>(<em>JArray</em>(emailIds))))
          } ~
          path(<em>'emailId</em>) {
            get { request <span>=&gt;</span>
              <span>val</span> emailId = request.parameters(<em>'emailId</em>)
              ...
              Future.sync(
                  <em>HttpResponse</em>(content = <em>Some</em>(emailObj)))
            }
          }
       }
     }
   } -&gt;
   shutdown { contactList <span>=&gt;</span>
     contactList.finalize
   }
 }
}
<span>object</span> <em>EmailServer</em> <span>extends</span> <em>BlueEyesServer</em> <span>with</span> <em>EmailServices</em>
</code>
