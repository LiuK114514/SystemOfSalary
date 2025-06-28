package com.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.Security;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@WebFilter(urlPatterns = {"/RegisterServlet", "/LoginServlet", "/ChangePasswordServlet", "/StaffServlet", "/FamilyMemberServlet","/StaffInfoServlet", "/SalaryRecordServlet", "/SalaryResultServlet"})
public class SensitiveDataEncryptFilter implements Filter {
    private static final String SM4_KEY = "0123456789abcdef";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Security.addProvider(new BouncyCastleProvider());//注册一个新的加密算法提供者
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String password = req.getParameter("password");
        if (password != null) {
            String encryptedPassword = this.encryptSM3(password);
            request.setAttribute("encrypted_password", encryptedPassword);
        }

        this.encryptAndSetIfPresent(req, "realName");
        this.encryptAndSetIfPresent(req, "idNumber");
        this.encryptAndSetIfPresent(req, "address");
        this.encryptAndSetIfPresent(req, "phone");

        chain.doFilter(request, response);
    }

    private void encryptAndSetIfPresent(HttpServletRequest req, String paramName) {
        String value = req.getParameter(paramName);
        if (value != null && !value.isEmpty()) {
            String encrypted = encryptSM4(value, SM4_KEY.getBytes());
            req.setAttribute("encrypted_" + paramName, encrypted);
        }
    }
    private String encryptSM3(String input) {
        try {
            MessageDigest sm3 = MessageDigest.getInstance("SM3", "BC");
            byte[] hash = sm3.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("SM3加密失败", e);
        }
    }
    private String encryptSM4(String input, byte[] keyBytes) {
        try {
            PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new SM4Engine());
            cipher.init(true, new KeyParameter(keyBytes));
            byte[] inputBytes = input.getBytes("UTF-8");
            byte[] output = new byte[cipher.getOutputSize(inputBytes.length)];
            int len1 = cipher.processBytes(inputBytes, 0, inputBytes.length, output, 0);
            int len2 = cipher.doFinal(output, len1);
            int totalLen = len1 + len2;
            byte[] result = new byte[totalLen];
            System.arraycopy(output, 0, result, 0, totalLen);
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            throw new RuntimeException("SM4加密失败", e);
        }
    }
    public static String decryptSM4(String base64Input) {
        try {
            PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new SM4Engine());
            cipher.init(false, new KeyParameter(SM4_KEY.getBytes()));  // false 表示解密
            byte[] inputBytes = Base64.getDecoder().decode(base64Input);
            byte[] output = new byte[cipher.getOutputSize(inputBytes.length)];
            int len1 = cipher.processBytes(inputBytes, 0, inputBytes.length, output, 0);
            int len2 = cipher.doFinal(output, len1);
            int totalLen = len1 + len2;
            byte[] result = new byte[totalLen];
            System.arraycopy(output, 0, result, 0, totalLen);
            return new String(result, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException("SM4解密失败", e);
        }
    }
}