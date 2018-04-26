package cn.stt.websocket.ex3;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/10/24.
 */
@Controller
public class WebsocketEx3Controller {

    @RequestMapping("/index1")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("ex2");
    }

    // 用户登录
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String doLogin(String id, HttpServletRequest request, Model model) {
        request.getSession().setAttribute(Constants.SESSION_USER_ID, id);
        model.addAttribute("userId", id);
        return "talk";
    }
}
