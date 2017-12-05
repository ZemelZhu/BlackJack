package blackjack.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import blackjack.services.blackService;



@RequestMapping("blackController")
@Controller
public class blackController {
	@Autowired
	private blackService blackService1;
	@RequestMapping("black")
	@ResponseBody
	public String bal() {
//		blackService1.black();
		return "###";
	}
	
}
