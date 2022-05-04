package ma.oncf.sfa.moulinette.security;

import ma.oncf.sfa.moulinette.security.utils.JwtUtil;
import ma.oncf.sfa.moulinette.services.ApplicationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static ma.oncf.sfa.moulinette.security.utils.SecurityContants.HEADER_STRING;

public class SpringSecurityAuditorAware implements AuditorAware<String> {
    @Autowired
    private ApplicationUserDetailsService userDetailsService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Optional<String> getCurrentAuditor() {
        final String authHeader = request.getHeader(HEADER_STRING);
        String jwt = authHeader.substring(7);
        String login = jwtUtil.extractUsername(jwt);
        return Optional.ofNullable(login).filter(s -> !s.isEmpty());
    }
}
