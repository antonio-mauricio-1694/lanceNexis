package br.com.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/*")
public class FilterAutenticacao implements Filter {

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request,
                         jakarta.servlet.ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // ✅ NÃO cria sessão nova
        HttpSession session = req.getSession(false);

        Object usuarioLogado = (session != null)
                ? session.getAttribute("usuarioLogado")
                : null;

        String url = req.getRequestURI();

        boolean isLogin = url.contains("login.xhtml");
        boolean isResource = url.contains("javax.faces.resource")
                || url.contains("/resources/");

        System.out.println("SESSION ID: " + (session != null ? session.getId() : "SEM SESSAO"));
        System.out.println("USUARIO LOGADO: " + usuarioLogado);
        System.out.println("URL: " + url);

        // 🔒 BLOQUEIA acesso sem login
        if (usuarioLogado == null && !isLogin && !isResource) {
            res.sendRedirect(req.getContextPath() + "/login.xhtml");
            return;
        }

        // 🔥 EVITA voltar pro login depois de logado
        if (usuarioLogado != null && isLogin) {
            res.sendRedirect(req.getContextPath() + "/index.xhtml");
            return;
        }

        chain.doFilter(request, response);
    }
}