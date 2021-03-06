//package cn.stt.websocket.ex1.controller;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.socket.TextMessage;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
///**
// * @Author shitongtong
// * <p>
// * Created by shitongtong on 2017/10/24.
// */
//@Controller
//public class WebsocketController {
//
//    @Bean//这个注解会从Spring容器拿出Bean
//    public SpringWebSocketHandler infoHandler() {
//        return new SpringWebSocketHandler();
//    }
//
//    @RequestMapping("/websocket/index")
//    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        System.out.println("dsadsdsad");
//        return new ModelAndView("login");
//    }
//
//    @RequestMapping("/websocket/login")
//    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String username = request.getParameter("username");
//        System.out.println(username+"登录");
//        HttpSession session = request.getSession(false);
//        session.setAttribute("SESSION_USERNAME", username);
//        return new ModelAndView("websocket");
//    }
//
//    @RequestMapping("/websocket/send")
//    @ResponseBody
//    public String send(HttpServletRequest request) {
//        String username = request.getParameter("username");
//        infoHandler().sendMessageToUser(username, new TextMessage("你好，测试！！！！"));
//        return null;
//    }
//}
