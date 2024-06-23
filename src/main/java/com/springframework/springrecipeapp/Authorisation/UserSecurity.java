package com.springframework.springrecipeapp.Authorisation;

import com.springframework.springrecipeapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class UserSecurity implements AuthorizationManager<RequestAuthorizationContext> {
    @Autowired
    private UserService userService;
    @Override
    public AuthorizationDecision check(Supplier authenticationSupplier, RequestAuthorizationContext ctx) {
        Long userId = Long.parseLong(ctx.getVariables().get("userId"));
        Authentication authentication = (Authentication) authenticationSupplier.get();
        return new AuthorizationDecision(hasUserId(authentication, userId));
    }

    public boolean hasUserId(Authentication authentication, Long userId) {
        // do your check(s) here
            if(userId == null) return true;
            Object details = authentication.getDetails();
            return userId.equals(userService.findUserByEmail(authentication.getName()).getId());
    }
}
