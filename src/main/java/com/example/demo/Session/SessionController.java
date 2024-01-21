package com.example.demo.Session;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
@Controller
public class SessionController {

    @GetMapping("/session")
    public String getSession(HttpSession session, Model model) {
        // 사용자 이름
        Object userName = session.getAttribute("userName");
        model.addAttribute("userName", userName);
        Object userId = session.getAttribute("userId");
        model.addAttribute("userId", userId);

        // 마지막 활동 시간
        LocalDateTime lastActivityTime = (LocalDateTime) session.getAttribute("lastActivityTime");
        model.addAttribute("lastActivityTime", lastActivityTime);

        // 세션 타임아웃
        int maxInactiveInterval = session.getMaxInactiveInterval();
        model.addAttribute("maxInactiveInterval", maxInactiveInterval);

        return "session";
    }
}
