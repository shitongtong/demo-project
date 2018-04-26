
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<script type="text/javascript" src="../static/js/jquery.js"></script>
<script type="text/javascript" src="../static/js/sockjs.js"></script>
<style>
textarea {
	height: 300px;
	width: 100%;
	resize: none;
	outline: none;
}

input[type=button] {
	float: right;
	margin: 5px;
	width: 50px;
	height: 35px;
	border: none;
	color: white;
	font-weight: bold;
	outline: none;
}

.clear {
	background: red;
}

.send {
	background: green;
}

.clear:active {
	background: yellow;
}

.send:active {
	background: yellow;
}

.msg {
	width: 100%;
	height: 25px;
	outline: none;
}

#content {
	border: 1px solid gray;
	width: 100%;
	height: 400px;
	overflow-y: scroll;
}

.from {
	background-color: green;
	width: 80%;
	border-radius: 10px;
	height: 30px;
	line-height: 30px;
	margin: 5px;
	float: left;
	color: white;
	padding: 5px;
	font-size: 22px;
}

.to {
	background-color: gray;
	width: 80%;
	border-radius: 10px;
	height: 30px;
	line-height: 30px;
	margin: 5px;
	float: right;
	color: white;
	padding: 5px;
	font-size: 22px;
}

.name {
	color: gray;
	font-size: 12px;
}

.tmsg_text {
	color: white;
	background-color: rgb(47, 47, 47);
	font-size: 18px;
	border-radius: 5px;
	padding: 2px;
}

.fmsg_text {
	color: white;
	background-color: rgb(66, 138, 140);
	font-size: 18px;
	border-radius: 5px;
	padding: 2px;
}

.sfmsg_text {
	color: white;
	background-color: rgb(148, 16, 16);
	font-size: 18px;
	border-radius: 5px;
	padding: 2px;
}

.tmsg {
	clear: both;
	float: right;
	width: 80%;
	text-align: right;
}

.fmsg {
	clear: both;
	float: left;
	width: 80%;
}
</style>
<script>
		var uid = ${userId};
		var from=uid;
		var fromName='xx';
		var to=uid==8?9:8;
		var url = "localhost:8080";
		var websocket;
		if ('WebSocket' in window) {
                websocket = new WebSocket("ws://" + url + "/ws?uid="+uid);
                alert("WebSocket");
            } else if ('MozWebSocket' in window) {
                websocket = new MozWebSocket("ws://" + url + "/ws?uid="+uid);
                alert("MozWebSocket");
            } else {
                websocket = new SockJS("http://" + url + "/ws/sockjs?uid="+uid);
                alert("SockJS");
            }
		
		websocket.onopen = function(event) {
			console.log("WebSocket:已连接");
			console.log(event);
		};
		websocket.onmessage = function(event) {
			var data=JSON.parse(event.data);
			console.log("WebSocket:收到一条消息",data);
			var textCss=data.from==-1?"sfmsg_text":"fmsg_text";
			$("#content").append("<div class='fmsg'><label class='name'>"+data.fromName+"&nbsp;"+data.date+"</label><div class='"+textCss+"'>"+data.text+"</div></div>");
			scrollToBottom();
		};
		websocket.onerror = function(event) {
			console.log("WebSocket:发生错误 ");
			console.log(event);
		};
		websocket.onclose = function(event) {
			console.log("WebSocket:已关闭");
			console.log(event);
		}
			function sendMsg(){
				var v=$("#msg").val();
				if(v==""){
					return;
				}else{
					var data={};
					data["from"]=from;
					data["fromName"]=fromName;
					data["to"]=to;
					data["text"]=v;
					websocket.send(JSON.stringify(data));
					$("#content").append("<div class='tmsg'><label class='name'>我&nbsp;</label><div class='tmsg_text'>"+data.text+"</div></div>");
					scrollToBottom();
					$("#msg").val("");
				}
			}
			
			function scrollToBottom(){
				var div = document.getElementById('content');
				div.scrollTop = div.scrollHeight;
			}
			
			function send(event){
				var code;
				 if(window.event){
					 code = window.event.keyCode; // IE
				 }else{
					 code = event.which; // Firefox
				 }
				if(code==13){ 
					sendMsg();            
				}
			}
			
			function clearAll(){
				$("#content").empty();
			}
		</script>
</head>
<body>
     当前用户:${userId}
	<div id="content"></div>
	<input type="text" placeholder="请输入要发送的信息" id="msg" class="msg" onkeydown="send(event)">
	<input type="button" value="发送" class="send" onclick="sendMsg()" >
	<input type="button" value="清空" class="clear" onclick="clearAll()">
</body>
</html>
