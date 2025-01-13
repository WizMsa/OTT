package tz.co.fasthub.ott.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ott.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class CustomOneTimeTokenService implements OneTimeTokenService {
    private final Map<String, OneTimeToken> oneTimeTokenByToken = new HashMap<>();

    @Override
    public OneTimeToken generate(GenerateOneTimeTokenRequest request) {;
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int tokenValue = random.nextInt(111111, 999999);
        OneTimeToken oneTimeToken = new DefaultOneTimeToken(String.valueOf(tokenValue), request.getUsername(), Instant.now().plusSeconds(300));
        oneTimeTokenByToken.put(String.valueOf(tokenValue), oneTimeToken);
        return oneTimeToken;
    }

    @Override
    public OneTimeToken consume(OneTimeTokenAuthenticationToken authenticationToken) {
        String tokenValue = authenticationToken.getTokenValue();
        return oneTimeTokenByToken.remove(tokenValue);
    }
}
