#  21点游戏双人对战(javaweb形式)
## 功能说明
 * 本项目实现了一个双人在线对战的web21点游戏，进入页面后需要创建房间或者加入房间，等待房间存在两个人准备即可，等待双方准备完毕即可开始游戏，游戏采用轮庄模式，并实时显示服务器在线人数。
## 详细
 * 核心
    > 本项目实现了一个双人在线对战的web21点游戏，进入页面后需要创建房间或者加入房间，等待房间存在两个人准备即可，等待双方准备完毕即可开始游戏，游戏采用轮庄模式，并实时显示服务器在线人数。</br>本项目基于springwebsocket，首先要让所以玩家建立一个链接，即对战平台，建立链接与[聊天室](https://github.com/Vimmone/Chatrum)的方式一样，才能实时信息交互！只有建立的动态链接才能进行下一步！
 * 具体功能模块的实现在[博客](http://blog.csdn.net/qq_35442958/article/details/79188998 "CSDN博客")中有说明
 * 技术栈：spring+springMVC+websocket+maven+bootstrp
## 运行
 * 可以把target下的Blackjack.war放在tomcat下运行，然后访问[http://127.0.0.1:8080/Blackjack/blackjack/blackJack.html](http://127.0.0.1:8080/Chatrum/rum/chat.html)就可以
 * 也可以IDE导入项目，更新maven依赖，然后用maven命令tomcat7:run运行，然后访问[http://127.0.0.1:8080/blackjack/blackJack.html](http://127.0.0.1:8080/rum/chat.html)就可以
 * 也可以访问[http://120.78.164.110:8080/Blackjack/blackjack/blackJack.html](http://120.78.164.110:8080/Blackjack/blackjack/blackJack.html)查看
 ## 预览
   ![BlackJack](http://img.blog.csdn.net/20180128203624896?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMzU0NDI5NTg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast "初始化界面")  
  
