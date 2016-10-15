分享者：蔡永强
分享日期：2016年10月15号


http协议简介
--------

超文本传输协议（HTTP，HyperText Transfer Protocol)是互联网上应用最为广泛的一种网络协议。

版本历史
----

超文本传输协议已经演化出了很多版本，它们中的大部分都是向下兼容的。客户端在请求的开始告诉服务器它采用的协议版本号，而后者则在响应中采用相同或者更早的协议版本。

**0.9**　已过时。只接受 GET 一种请求方法

**HTTP/1.0**　这是第一个在通讯中指定版本号的HTTP 协议版本，至今仍被广泛采用，特别是在代理服务器中。

**HTTP/1.1**　当前版本。持久连接被默认采用，并能很好地配合代理服务器工作。还支持以管道方式同时发送多个请求，以便降低线路负载，提高传输速度。（和1.0的主要区别就是是否是持久连接，1.0一个tcp连接只传输一个Web对象；1.1默认使用持久连接，当然也可以配置成使用非持久连接）。
*在持久连接下,不必为每个Web对象的传送建立一个新的连接,一个连接中可以传输多个对象!*

**HTTP 2.0** 是下一代HTTP协议。是自1999年http1.1发布后的首个更新。HTTP 2.0在2013年8月进行首次合作共事性测试。在开放互联网上**HTTP 2.0将只用于https://网址**，而 http://网址将继续使用HTTP/1，目的是在**开放互联网上增加使用加密技术，以提供强有力的保护去遏制主动攻击**。

http协议基础知识
----------

http请求和响应格式

![http请求和响应格式](http://img.blog.csdn.net/20161015163644115)

 HTTP请求分为四个部分（请求行、请求头信息、空行和请求实体） 
① 请求行（请求方式、请求路径和协议版本） 
注：请求方式（GET/POST/HEAD/PUT/DELETE/TRACE/OPTIONS）
 GET请求方式（如URL请求，超链接请求和表单缺省请求等）：在URL请求地址后附带参数，通常数据容量不能超过1K； 
POST请求方式：可以在请求实体中向服务器发送数据，数据量不限大小。
 ② 请求头信息（key:value） 
Accept：text/html,image/*  ——>通知服务器客户端所支持的数据类型
 Accept-Charset:ISO-8859-1  ——>通知服务器客户端所支持的编码方式 Accept-Encoding: gzip,compress  ——>通知服务器客户端所支持的数据压缩格式 
Accept-Language: en-us,zh-cn ——>通知服务器客户端的语言环境 
Host:www.baidu.com ——>通知服务器客户端请求的主机地址 
If-Modified-since:Tue 11 Jul 2014 21:02:37 GMT ——>通知服务器资源的缓存时间 
Referer:http://www.baidu.com/index.html ——>通知服务器客户端从哪个资源访问服务器（用于防盗链） 
User-Agent:Mozilla/4.0(compatible;MSIE 5.5 Windows NT 5.0) ——>通知服务器客户端的软件环境 
Cookie: ——>客户端向服务器请求资源时可以带的数据 
Connection:close/Keep-Alive 客户端请求完毕之后需要断开连接（close）；保持连接（Keep-Alive） 
Date：Tue 11 Jul 2014 21:02:37 GMT 客户端请求资源的当前时间 
③ 空行，请求头和请求实体之间用一个空行隔开，没有请求实体时，空行仍不能
省 
④ 请求实体（可选）：发送信息

 HTTP响应也分为四个部分（响应行、响应头信息、空行和响应实体） 
① 响应行（协议版本、状态码和状态文字）
② 响应头信息（key:value) 
Location:www.baidu.com ——>配合302通知客户端重定向的资源地址
 Server：Apache Tomcat ——>通知客户端服务器的类型 
 Content-Encoding:gzip ——>通知客户端数据的压缩格式 Content-length:80 ——>通知客户端回送数据的长度 
Content-language:zh-cn ——>通知客户端回送数据的语言环境 
Content-type:text/html;charset=GBK2312 ——>通知客户端回送数据的类型 
Last-Modified: Tue 11 Jul 2014 21:02:37 GMT ——>通知客户端资源最后的缓存时间 
Content-Disposition: attachement;filename=aaa.zip ——>通知客户端以下载方式打开请求资源 
Transfer-Encoding:chuncked ——>通知客户端回送数据按照块传送 
Set-Cookie:SS=QO=5Lb;path=/search 
Etag:W/0384384093489023843  ——>通知客户端回送数据生成的唯一标识
 Expires:-1 ——>通知客户端回送数据缓存多长时间 
Cache-Control:no-Cache ——>通知客户端回送数据无需缓存
 Pragma:no-Cache ——>通知客户端回送数据无需缓存 
Connection:close/Keep-Alive 服务器请求完毕之后需要断开连接（close）；保持连接（Keep-Alive） 
Date：Tue 11 Jul 2014 21:02:37 GMT 服务器请求资源的当前时间  
③ 空行，响应头和响应实体之间用一个空行隔开，没有请求实体时，空行仍不能
省 
④ 响应实体（也可能没有）

一个http请求实例

![这里写图片描述](http://img.blog.csdn.net/20161015165620366)

参考文献：
[HTTP协议详解（真的很经典）](http://www.cnblogs.com/li0803/archive/2008/11/03/1324746.html)

Http2.0
-------

HTTP 2.0 的出现，相比于 HTTP 1.x ，大幅度的提升了 web 性能。在与 HTTP/1.1 完全语义兼容的基础上，进一步减少了网络延迟。而对于前端开发人员来说，无疑减少了在前端方面的优化工作。

[Http2的性能演示](https://http2.akamai.com/demo)

**二进制分帧**

在不改动 HTTP/1.x 的语义、方法、状态码、URI 以及首部字段….. 的情况下, HTTP/2 是如何做到「突破 HTTP1.1 的性能限制，改进传输性能，实现低延迟和高吞吐量」的 ?
关键之一就是在 应用层(HTTP/2)和传输层(TCP or UDP)之间增加一个二进制分帧层。

![二进制分帧](http://img.blog.csdn.net/20161015214247126)

HTTP/2 会将所有传输的信息分割为更小的消息和帧（frame）,并对它们采用二进制格式的编码 ，其中 HTTP1.x 的首部信息会被封装到 HEADER frame，而相应的 Request Body 则封装到 DATA frame 里面。

http2.0用binary格式定义了一个一个的frame，和http1.x的格式对比如下图：

![http2.0和http1.0格式对比图](http://img.blog.csdn.net/20161015214543690)

length定义了整个frame的开始到结束，type定义frame的类型（一共10种），flags用bit位定义一些重要的参数，stream id用作流控制，剩下的payload就是request的正文了。
虽然看上去协议的格式和http1.x完全不同了，实际上http2.0并没有改变http1.x的语义，只是把原来http1.x的header和body部分用frame重新封装了一层而已。调试的时候浏览器甚至会把http2.0的frame自动还原成http1.x的格式。

http2.0里的每个stream都可以设置又优先级（Priority）和依赖（Dependency）。优先级高的stream会被server优先处理和返回给客户端，stream还可以依赖其它的sub streams。优先级和依赖都是可以动态调整的。

**多路复用 (Multiplexing)**

多路复用允许同时通过单一的 HTTP/2 连接发起多重的请求-响应消息。

先看下http1.1中多请求的处理方式

![这里写图片描述](http://img.blog.csdn.net/20161015221100950)

可以看到，要么是串行，一个请求完成后再发另一个请求，要么是可以并发，但请求结果的返回过程必须是顺序的，第一个响应完成后，才能响应第二个，即使第二个先处理完成，也要等待第一个，就产生了阻塞

http2.0 的处理方式

![这里写图片描述](http://img.blog.csdn.net/20161015221207904)

只要有了请求结果数据，可以立即返回，不关心顺序问题，因为数据都被组装成了一个个的frame帧，frame中记录了自己所属数据流的ID，客户端把frame都接收到以后，根据数据流ID再进行组装即可。

首部压缩（Header Compression）

HTTP/1.1并不支持 HTTP 首部压缩，为此 SPDY 和 HTTP/2 应运而生， SPDY 使用的是通用的DEFLATE 算法，而 HTTP/2 则使用了专门为首部压缩而设计的 HPACK 算法。

![这里写图片描述](http://img.blog.csdn.net/20161015222726317)

前面提到过http1.x的header由于cookie和user agent很容易膨胀，而且每次都要重复发送。http2.0使用encoder来减少需要传输的header大小，通讯双方各自cache一份header fields表，既避免了重复header的传输，又减小了需要传输的大小。高效的压缩算法可以很大的压缩header，减少发送包的数量从而降低延迟。

**Server Push**

服务端推送是一种在客户端请求之前发送数据的机制。在 HTTP/2 中，服务器可以对客户端的一个请求发送多个响应。Server Push 让 HTTP1.x 时代使用内嵌资源的优化手段变得没有意义；如果一个请求是由你的主页发起的，服务器很可能会响应主页内容、logo 以及样式表，因为它知道客户端会用到这些东西。这相当于在一个 HTML 文档内集合了所有的资源，不过与之相比，服务器推送还有一个很大的优势：可以缓存！也让在遵循同源的情况下，不同页面之间可以共享缓存资源成为可能。

HTTP2.0里的负能量
------------

HTTP2.0对于ssl的依赖使得有些开发者望而生畏。不少开发者对ssl还停留在高延迟，CPU性能损耗，配置麻烦的印象中。其实ssl于http结合对性能的影响已经可以优化到忽略的程度了，网上也有不少文章可以参考。HTTP2.0也可以不走ssl，有些场景确实可能不适合https，比如对代理服务器的cache依赖，对于内容安全性不敏感的get请求可以通过代理服务器缓存来优化体验。

Android下http现状
--------------

http2.0只能在新系统下支持，spdy作为过渡方案仍然有存在的必要。
对于使用webview的app来说，需要基于chrome内核的webview才能支持spdy和http2.0，而android系统的webview是从android4.4（KitKat）才改成基于chrome内核的。
对于使用native api调用的http请求来说，okhttp是同时支持spdy和http2.0的可行方案。如果使用ALPN，okhttp要求android系统5.0+(实际上，android4.4上就有了ALPN的实现，不过有bug，知道5.0才正式修复)，如果使用NPN，可以从android4.0+开始支持，不过NPN也是属于将要被淘汰的协议。








参考文献：
-----

[http2.0 有什么优势](http://www.wtoutiao.com/p/Vc4H2T.html)

[HTTP/2.0 相比1.0有哪些重大改进？](http://www.zhihu.com/question/34074946)

[HTTP/2 对现在的网页访问，有什么大的优化呢？体现在什么地方？](https://www.zhihu.com/question/24774343/answer/96586977)

如果想要看http2的详细内容介绍：
------------------

http://httpwg.org/specs/rfc7540.html （RFC 7540  全英文介绍）

https://www.gitbook.com/book/bagder/http2-explained/details （各种语言）
