package me.sjtumeow.meow.authorization.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class WebAuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private WebAuthUtility webAuthUtility;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();

        if (requestURI.startsWith("/api/web") && !requestURI.equals("/api/web/auth")
                && !webAuthUtility.checkAuth(session)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            webAuthUtility.respondFailureMessage(response, "请先登录");
            return false;
        }

        if (requestURI.startsWith("/api/admin")) {
            if (!webAuthUtility.checkAuth(session)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                webAuthUtility.respondFailureMessage(response, "请先登录");
                return false;
            }
            if (!webAuthUtility.checkAdmin(session)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                webAuthUtility.respondFailureMessage(response, "禁止访问");
                return false;
            }
        }

        return true;
    }

}
