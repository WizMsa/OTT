package tz.co.fasthub.ott.configs;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.authentication.ott.RedirectOneTimeTokenGenerationSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class CustomOneTimeTokenSuccessHandler implements OneTimeTokenGenerationSuccessHandler {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final RedirectOneTimeTokenGenerationSuccessHandler redirectOneTimeTokenGenerationSuccessHandler = new RedirectOneTimeTokenGenerationSuccessHandler("/ott/sent");


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken) throws IOException, ServletException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(UrlUtils.buildFullRequestUrl(request));
        builder.replacePath(request.getContextPath());
        builder.queryParam("token", oneTimeToken.getTokenValue());
        builder.path("/ott");
        String magicLink = builder.toUriString();
        log.info("the magic link :: {}", magicLink);
        log.info("the oneTimeToken value :: {}", oneTimeToken.getTokenValue());
        log.info("the oneTimeToken username :: {}", oneTimeToken.getUsername());
        log.info("the oneTimeToken expires at :: {}", oneTimeToken.getExpiresAt());
        redirectOneTimeTokenGenerationSuccessHandler.handle(request, response, oneTimeToken);
    }
}
