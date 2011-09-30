!SLIDE

_5分_でわかる(?)__モナド__

!SLIDE

＼('.')~ Hi, I'm<br/>
[@yuroyoro](http://twitter.com/yuroyoro)

!SLIDE

ヽ(\*ﾟдﾟ)ノ＜ _モナ丼_の話します

!SLIDE

('A`) 「ﾓﾅﾄﾞ?」

!SLIDE

__モナド__は_デザパタ_

!SLIDE

「_合成可能な計算_を__抽象化__したもの」

!SLIDE

「_unit_と__bind__を持っていて」

!SLIDE

「__ある条件__ (モナド則)を満たす」

!SLIDE

_データ構造_

!SLIDE

* _unit_
* __bind__


!SLIDE

_unit_ はコンストラクタ

!SLIDE

__bind__ は <span>flatMap</span>

<span>class</span> M[A] {
  <span>def</span> <em>flatMap</em>[B](f: A => M[B]) : M[B]
}

!SLIDE

_Option_ はモナド

!SLIDE

_unit_ = Some("おっぱい")

!SLIDE

<code>
<strong>flatMap</strong>=
  Some("おっぱい") <em>flatMap</em> { s => Some( s * 2 ) }
</code>

!SLIDE

__flatMap__が使える = <span>for</span>で書ける

!SLIDE

<code>
<span>for</span>(
  i <strong><-</strong> <em>Some</em>("おっぱい");
  j <strong><-</strong> <em>Some</em>("おっぱい");
  k <strong><-</strong> <em>Some</em>("おっぱい")
) yield{
  i + j + k
}
</code>

!SLIDE

これは__モナド__同士を_flatMap_で合成

!SLIDE

_MZero_ (モナドゼロ)

!SLIDE

_ゼロ_(零元) が定義されてる

!SLIDE

Optionの_零_

__None__

!SLIDE

Listの_零_

__NIL__


!SLIDE

_モナド_と__モナド零__は加算できる


!SLIDE

__None__ orElse Some("おっぱい")

!SLIDE

List("おっぱい") ++ __Nil__

!SLIDE

__Nil__._flatMap_{ s => Some("おっぱい") }


!SLIDE

__まとめ__

!SLIDE

__モナド__は_デザパタ_

!SLIDE


以上

!SLIDE

ここを読んでね

[モナドは象だ](http://dl.dropbox.com/u/261418/Monads_are_Elephants/index.html)
