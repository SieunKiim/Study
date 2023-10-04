package billing.sieunojt.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class RemoteAddressInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        String remoteAddress = (null != request.getHeader("X-FORWARDED-FOR")) ? request.getHeader(
                "X-FORWARDED-FOR") : request.getRemoteAddr();
        request.setAttribute("ip-address", remoteAddress);
        return true;
    }
}
