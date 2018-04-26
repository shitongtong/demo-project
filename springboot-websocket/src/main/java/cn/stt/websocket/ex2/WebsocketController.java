package cn.stt.websocket.ex2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/10/24.
 */
@Controller
public class WebsocketController {

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("dsadsdsad");
        return new ModelAndView("ex2");
    }
}
