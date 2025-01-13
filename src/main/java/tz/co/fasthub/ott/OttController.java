package tz.co.fasthub.ott;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ott.GenerateOneTimeTokenRequest;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.authentication.ott.OneTimeTokenAuthenticationToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.web.bind.annotation.*;
import tz.co.fasthub.ott.configs.CustomOneTimeTokenSuccessHandler;
import tz.co.fasthub.ott.services.AuthService;

import java.io.IOException;

@RestController
@RequestMapping("/ott")
@RequiredArgsConstructor
public class OttController {
    private final AuthService authService;
    private final CustomOneTimeTokenSuccessHandler tokenGenerationSuccessHandler;

    @GetMapping("/sent")
    public String ottSent(){
        return "OttSent";
    }

    @PostMapping("/auth")
    public OneTimeToken ottAuth(HttpServletRequest request, HttpServletResponse response,@RequestParam String username) throws ServletException, IOException {
        OneTimeToken token = authService.generate(new GenerateOneTimeTokenRequest(username));
        tokenGenerationSuccessHandler.handle(request,response,token);
        return token;
    }

    @PostMapping("/validation")
    public OneTimeToken validate(@RequestParam String token){
        OneTimeTokenAuthenticationToken unauthenticated = OneTimeTokenAuthenticationToken.unauthenticated(token);
        return authService.validatePassword(unauthenticated);
    }
}
