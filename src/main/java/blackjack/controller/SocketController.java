package blackjack.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import blackjack.core.GameRoom;
import blackjack.services.blackService;



@RequestMapping("SocketController")
@Controller
public class SocketController {
//	@Autowired
//	private GameRoom gameRoom;
//	@RequestMapping("test")
//	@ResponseBody
//	public void test(HttpSession session) throws IOException {
//		gameRoom.joinRoom(session);
//	}
//	@RequestMapping("start")
//	@ResponseBody
//	public int start() {
//		return 0;
//	}
}
