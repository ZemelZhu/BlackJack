
function disabledinit(){
	$("#idone").addClass('disabled');//状态按键禁用
	$("#idtwo").addClass('disabled');
}
function init() {//游戏结束按钮初始化
	$("#idone").html("未准备");
	$("#idtwo").html("未准备");
	$("#idone").removeClass('btn-success');
	$("#idtwo").removeClass('btn-success disabled');
	$("#idone").addClass('btn-default');
	$("#idtwo").addClass('btn-default');
}
$(function() {
	var websocket;
	var id;//判断谁是玩家，id为1表示为庄家，否则为玩家
	var playerCardNumber;//判断玩家当前状态有多少张牌
	var dealerCardNumber;//判断庄家当前状态有多少张牌
	var gameStatus = 5;//游戏状态判定
	var flat = 1;//判断是否准备
	websocket = new WebSocket("ws://127.0.0.1:8080/echo");
	websocket.onopen = function(evnt) {
	};
	websocket.onmessage = function(evnt) {
		var data = evnt.data;
		if(data[0]=="g") {//游戏中的判定
			var status = data[1];//判断游戏中的哪一步
			var cot = data.slice(2);//判断游戏中传递的信息
			if (status == "1") {
				// 庄家获得一张暗牌
				playerCardNumber=1;//玩家牌数清空
				dealerCardNumber=1;//庄家牌数清空
				id = 1;//表示这局游戏为庄家
				disabledinit();//初始化
				$("#idone").html("玩家");
				$("#idtwo").html("庄家");
				$("#output").html("等待玩家操作");//输出界面显示
				var url = "./image/poker/" + cot + ".jpg";
				$("#myself").children().eq(0).attr('src', url);
				$("#you").children().eq(0).attr('src', "./image/poker/54.jpg");
				gameStatus=1;//标记当前游戏状态为庄家摸牌
			}
			else if (status == "2") {
				// 玩家获得一张暗牌
				playerCardNumber=1;//玩家牌数清空
				dealerCardNumber=1;//庄家牌数清空
				id = 0;//表示这局游戏为玩家
				$("#output").html("玩家回合");
				$("#idone").html("庄家");
				$("#idtwo").html("玩家");
				disabledinit();
				var url = "./image/poker/" + cot + ".jpg";
				$("#myself").children().eq(0).attr('src', url);
				$("#you").children().eq(0).attr('src', "./image/poker/54.jpg");
				gameStatus=1;//标记当前游戏状态为玩家摸牌
			}
			else if (status == "3") {
			// 玩家获得一张牌
				var url = "./image/poker/" + cot + ".jpg";
				if (id == 1) {//我是庄家
					$("#you").children().eq(playerCardNumber).attr('src', url);
				} else {//对方是玩家
					$("#myself").children().eq(playerCardNumber).attr('src', url);
				}
				playerCardNumber++;//玩家牌数+1
			}
			else if (status == "4") {
				// 庄家获得一张牌
				var url = "./image/poker/" + cot + ".jpg";
				if (id == 1) {//我是庄家
					$("#myself").children().eq(dealerCardNumber).attr('src', url);
				} else {//对方是玩家
					$("#you").children().eq(dealerCardNumber).attr('src', url);
				}
				dealerCardNumber++;//庄家牌数+1
			}
			else if (status == "5") {
			// 双方通知，判断输赢
				gameStatus = 5;
				flat = 1;
				var str = data.slice(3);
				if (data[2] == "1") {
					$("#output").html("你赢了");
				} else if (data[2] == "0") {
					$("#output").html("你输了");
				} else {
					$("#output").html("平局,庄家赢");
				}
				var url = "./image/poker/" + str + ".jpg";
				$("#you").children().eq(0).attr('src', url);//显示对方隐藏的暗牌
				init();
			}
		}//游戏中判定结束
		else if(data[0]=="z") {
			//准备系统，用户双方准备与游戏初始化
			var status = data[1];//判断准备状态
			if (status == "6") {//对方进行准备完成
				$("#idone").removeClass('btn-default');
				$("#idone").html("准备");
				$("#idone").addClass('btn-success');
				for (var i = 0; i < 5; i++) {
					$("#you").children().eq(i).attr('src', "./image/poker/54.jpg");
				}
			}
			else if (status == "7") {//对方取消准备
				$("#idone").removeClass('btn-success');
				$("#idone").html("未准备");
				$("#idone").addClass('btn-default');
			}
			else if (status == "8") {//对方连接游戏异常
				$("#output").html("对方断开连接");
				flat = 1;
				$("#idone").html("未准备");
				$("#idtwo").html("未准备");
				$("#idone").removeClass('btn-success');
				$("#idtwo").removeClass('btn-success disabled');
				$("#idone").addClass('btn-default');
				$("#idtwo").addClass('btn-default');
			}
		}//准备系统结束
		else if(data[0]=="o") {
			//用于判断在线人数
			$("#online").html(data.slice(1));//解析服务器在线人数
		}//在线人数系统结束
		else if(data[0]=="a") {
			//用于获得房间号
			$("#roomId").html(data.slice(1));//获得房间号
		
		}//房间号模块结束
		else if(data[0]=="p") {
			//游戏控制台输出信息
			$("#output").html(data.slice(1));
			if(data.slice(1)=="庄家回合") {
			gameStatus=-2;//玩家操作结束，庄家回合
			}
		}//游戏控制台输出模块结束
		else if(data[0]=="b") {
			//中途异常退出模块
			$("#cott").html(data.slice(1));
			$("#output").html("等待操作...");
				flat = 1;
				$("#myModal").modal('show');
				$("#idone").html("未准备");
				$("#idtwo").html("未准备");
				$("#idone").removeClass('btn-success');
				$("#idtwo").removeClass('btn-success disabled');
				$("#idone").addClass('btn-default');
				$("#idtwo").addClass('btn-default');
				for (var i = 0; i < 5; i++) {
					$("#you").children().eq(i).attr('src',
							"./image/poker/54.jpg");
					$("#myself").children().eq(i).attr('src',
							"./image/poker/54.jpg");
				}
		}//中途异常退出模块结束

	};

	websocket.onerror = function(evnt) {
	};
	websocket.onclose = function(evnt) {
	};


$("#joinRoom").bind("click", function() {//加入房间功能
$("#navbar-collapse").collapse('hide');//响应式关闭下拉菜单
if ($("#roomId").html() != "未进入房间") {
	$("#cott").html("你已经加入房间，不能加入其他房间");
	$("#myModal").modal('show');
	return;
}
websocket.send("8" + $("#roomNumber").val());//加入房间指令

});
$("#createRoom").bind("click", function() {//创建房间功能
	$("#navbar-collapse").collapse('hide');//响应式关闭下拉菜单
	if ($("#roomId").html() != "未进入房间") {
		$("#cott").html("你已经加入房间，不能创建其他房间");
		$("#myModal").modal('show');
		return;
	}
	websocket.send("7");//创建房间指令
});




	$("#cot").bind("click", function() {//摸牌事件
		// $("#output").html("等待操作...");
		if (gameStatus == 5) {//游戏还未初始化或者刚结束游戏
			$("#output").html("游戏还未开始，无法摸牌");
			return;
		}
		if (gameStatus == -1) {//当前是玩家已经听牌
			$("#output").html("已经听牌，不能再摸牌了");
			return;
		}
		if (id == 1 && gameStatus != -2) {//当前是庄家并且玩家还未听牌
			$("#output").html("玩家回合还没有结束，请等待");
			return;
		}
		if (id == 1 && gameStatus == -2) {//当前是庄家进行摸牌
			websocket.send("5");//庄家摸牌
			return;
		}
		websocket.send("0");//玩家摸牌
	});
	$("#out").bind("click", function() {//翻牌事件
		// $("#output").html("等待操作...");
		if (gameStatus == 5) {//游戏还未初始化或者刚结束游戏
			$("#output").html("游戏还未开始，无法听牌");
			return;
		}
		if (gameStatus == -1) {//玩家已经听牌
			$("#output").html("已经听牌，不能再听牌了");
			return;
		}
		if (id == 1 && gameStatus != -2) {//当前是庄家并且玩家还未听牌
			$("#output").html("玩家回合还没有结束，请等待");
			return;
		}
		if (id == 1 && gameStatus == -2) {//当前是庄家进行听牌
			// 庄家摸牌
			websocket.send("6");//庄家听牌指令
			return;
		}
		websocket.send("1");//玩家听牌指令
		$("#output").html("等待庄家操作");
		gameStatus=-1;//表示玩家已经听牌
	});

	$("#idtwo").click(//游戏准备模块
		function() {
			if ($("#roomId").html() == "未进入房间") {
				$("#cott").html("请先进入房间");
				$("#myModal").modal('show');
				return;
			}
			if (flat == 1) {//表示未准备
				for (var i = 0; i < 5; i++) {
					$("#myself").children().eq(i).attr('src',
							"./image/poker/54.jpg");
				}
				$("#idtwo").html("准备");
				$("#output").html("游戏准备");
				websocket.send("3");//进行准备
				$("#idtwo").removeClass('btn-default');
				$("#idtwo").addClass('btn-success');
				flat = 0;
			} else {//表示已经准备
				websocket.send("4");//取消准备
				$("#idtwo").html("未准备");
				$("#output").html("游戏未准备");
				$("#idtwo").removeClass('btn-success');
				$("#idtwo").addClass('btn-default');
				flat = 1;
			}
		});
});
