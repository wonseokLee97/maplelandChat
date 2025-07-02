package com.mapleland.chatService.shared.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public class IpAddressUtil {
    private static final List<String> IP_HEADERS = Arrays.asList(
            "X-Forwarded-For",
            "X-Real-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_VIA",
            "IPV6_ADR"
    );

    public static String extractIpAddress(HttpServletRequest request) throws UnknownHostException {
        String clientIp = null;
        boolean isIpInHeader = false;

        for (String header : IP_HEADERS) {
            clientIp = request.getHeader(header);
            if (StringUtils.hasText(clientIp) && !"unknown".equalsIgnoreCase(clientIp)) {
                isIpInHeader = true;
                break;
            }
        }

        if (!isIpInHeader) {
            clientIp = request.getRemoteAddr();
        }

        if ("0:0:0:0:0:0:0:1".equals(clientIp) || "127.0.0.1".equals(clientIp)) {
            InetAddress address = InetAddress.getLocalHost();
            clientIp = address.getHostAddress();
        }

        return clientIp;
    }
}