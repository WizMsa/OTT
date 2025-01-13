package tz.co.fasthub.ott.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ott.DefaultOneTimeToken;
import org.springframework.security.authentication.ott.GenerateOneTimeTokenRequest;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.authentication.ott.OneTimeTokenAuthenticationToken;
import org.springframework.stereotype.Service;
import tz.co.fasthub.ott.users.User;

import java.time.Instant;
import java.util.Date;
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final CustomOneTimeTokenService oneTimeTokenService;

    public static User user = null;

    public OneTimeToken generate(GenerateOneTimeTokenRequest request) {
        OneTimeToken oneTimeToken = oneTimeTokenService.generate(request);
        user = new User(request.getUsername(),oneTimeToken);
        return oneTimeToken;
    }

    public OneTimeToken validatePassword(OneTimeTokenAuthenticationToken authenticationToken) {
        try {
            OneTimeToken oneTimeToken = oneTimeTokenService.consume(authenticationToken);
            if (oneTimeToken == null) {
                log.error("Invalid one-time token");
                return null;
            }

            String username = oneTimeToken.getUsername();
            String tokenValue = oneTimeToken.getTokenValue();
            if (username.equals(user.getUsername()) && tokenValue.equals(user.getToken().getTokenValue())) {
                if(new Date().toInstant().isBefore(user.getToken().getExpiresAt())) {
                    return new DefaultOneTimeToken(tokenValue,username,Instant.now());
                }
                log.warn("token expired");
                return null;
            }
            log.warn("user not recognized");
            return null;
        }catch (Exception e) {
            log.error("{} found while validating token",e.getMessage(), e);
            return null;
        }
    }
}
