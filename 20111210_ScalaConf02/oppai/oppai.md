!SLIDE

# _Scala_を支える<br/>
# __エコシステム__

!SLIDE

＼('.')~ Hi, I am<br/>
[@yuroyoro](http://twitter.com/yuroyoro)

![yuroyoro](img/Background2_copy.png)

!SLIDE

## 今日は<br/>
## _Scala_の<br/>
## __エコシステム__の話

!SLIDE

_(　ﾟ∀ﾟ)o彡°おっぱい！おっぱい！_<br/>
__(　ﾟ∀ﾟ)o彡°おっぱい！おっぱい！__<br/>
<span>(　ﾟ∀ﾟ)o彡°おっぱい！おっぱい！</span><br/>
<span class="cy">(　ﾟ∀ﾟ)o彡°おっぱい！おっぱい！</span><br/>

!SLIDE

# __Simple Build Tool__

https://github.com/harrah/xsbt

!SLIDE

## $ __sbt__
## > _update_
## > _complie_
## > _test_
## > _cosole_

!SLIDE

<span>プログラマブル</span>な<br/>
ビルド定義

```scala
  // specs2
  val specs2 = "org.specs2" %% "specs2" % "1.3"
```

!SLIDE

_プラグイン_いろいろ

- yuicompressor
- Android
- coffeescirpt

!SLIDE

# __conscript__

https://github.com/n8han/conscript

!SLIDE

__コマンドラインアプリケ__を<br/>
セットアップ

!SLIDE

依存性の管理は<br/>
<span>sbt</span>を利用

!SLIDE

## _Picture Show_

```
  cs softprops/picture-show
```

markdownでスライド作れる

!SLIDE

## __Pamflet__

```
  cs n8han/pamflet
```

markdownでドキュメント作れる(Sphinx的な)

!SLIDE

# __giter8__

https://github.com/n8han/giter8

!SLIDE

<span>github</span>にある<br/>
_テンプレート_から<br/>
アプリを作るツール


!SLIDE

maven archtype的な

!SLIDE

_conscript_でインストール

```
  cs n8han/giter8
```

!SLIDE

_Unfiltered_のプロジェクト作る

```
g8 softprops/unfiltered
```


!SLIDE

テンプレートのリスト

```
g8 --list
```

!SLIDE

# __ls.implicit.ly__

http;//ls.implicit.ly

!SLIDE

![ls.implicit.ly](img/ls_ss.png)

!SLIDE

__scala__の<br/>
<span>ライブラリ</span>の<br/>
_カタログサイト_

!SLIDE

__conscript__による<br/>
コマンドラインツール

```
cs softprops/ls
```

!SLIDE

__sbt__の<br/>
依存ライブラリの<br/>
追加を簡単に<br/>

!SLIDE

# __まとめ__

!SLIDE

# __ビルドツール__
## _sbt_

!SLIDE

# __SCM__
## _git/Github_

!SLIDE

# __CLIツール__
## _conscript_

!SLIDE

# __プロジェクトテンプレート__
## _giter8_

!SLIDE

# __ライブラリカタログ__
## _ls.implicit.ly_

!SLIDE

それぞれを<br/>
_連携_させると<br/>
__快適__になる

!SLIDE

- sbt
- git/Github
- conscript
- giter8
- ls.implicit.ly

!SLIDE

時間あったらデモ



