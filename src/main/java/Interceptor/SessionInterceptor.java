package Interceptor;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
public class SessionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(true);
        if (session != null) {

            LocalDateTime lastActivityTime = (LocalDateTime) session.getAttribute("lastActivityTime");
            if (lastActivityTime != null && lastActivityTime.isBefore(LocalDateTime.now().minusWeeks(1))) {
                // 마지막 활동 시간이 일주일 이상 지났으면 세션을 만료시킴
                session.invalidate();
            } else {
                // 마지막 활동 시간을 현재 시간으로 갱신
                session.setAttribute("lastActivityTime", LocalDateTime.now());
            }
        }
        return true;
    }
}