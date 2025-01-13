package tz.co.fasthub.ott.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.ott.OneTimeToken;

@Data
@AllArgsConstructor
public class User {
    private String username;
    private OneTimeToken token;
}
