package blackjack.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


/**
* Created by zhuqiang on 2015/6/23 0023.
        */
@Configuration
@EnableWebSocket//开启websocket
public class WebSocketConfig implements WebSocketConfigurer {
	@Autowired
	private WebSocketHander webSocketHander;
	@Autowired
	private HandshakeInterceptor handshakeInterceptor;
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHander,"/echo").addInterceptors(
        		handshakeInterceptor); //支持websocket 的访问链接
     
    }
}