
package cn.stt.websocket.ex3;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Socket建立连接（握手）和断开
 * 
 * @author Goofy
 * @Date 2015年6月11日 下午2:23:09
 */
public class HandShake extends HttpSessionHandshakeInterceptor {

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
								   Map<String, Object> attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = servletRequest.getServletRequest().getSession(false);
			if (session != null) {
				// 使用userName区分WebSocketHandler，以便定向发送消息
				String uid = (String) session.getAttribute(Constants.SESSION_USER_ID);
				attributes.put(Constants.SESSION_USER_ID, uid);
				System.err.println("beforeHandshake uid=" + uid);
			}
		}
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
							   Exception ex) {
		// TODO Auto-generated method stub
		super.afterHandshake(request, response, wsHandler, ex);
	}

	// public boolean beforeHandshake(ServerHttpRequest request,
	// ServerHttpResponse response, WebSocketHandler wsHandler, Map<String,
	// Object> attributes) throws Exception {
	// //System.err.println("Websocket:用户[ID:" + ((ServletServerHttpRequest)
	// request).getServletRequest().getSession(false).getAttribute("uid") +
	// "]已经建立连接");
	//
	// if (request instanceof ServletServerHttpRequest) {
	// ServletServerHttpRequest servletRequest = (ServletServerHttpRequest)
	// request;
	// HttpSession session =
	// servletRequest.getServletRequest().getSession(false);
	// // 标记用户
	// if (null != session) {
	// String uid = (String) session.getAttribute("uid");
	// if(uid!=null){
	// attributes.put("uid", uid);
	// }else{
	// return false;
	// }
	// }
	// }
	// return true;
	// }
	//
	// public void afterHandshake(ServerHttpRequest request, ServerHttpResponse
	// response, WebSocketHandler wsHandler, Exception exception) {
	// //response.getHeaders().set("Access-Control-Allow-Origin", "*");
	// //System.err.println("After Handshake");
	// }

}
