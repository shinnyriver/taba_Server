package taba.tabaServer.tabaserver.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserDetailsServiceManager {
    private final Map<String, UserDetailsService> userDetailsServiceMap;

    @Autowired
    public UserDetailsServiceManager(Map<String, UserDetailsService> userDetailsServiceMap) {
        this.userDetailsServiceMap = userDetailsServiceMap;
    }

    public UserDetailsService getService(String userType) {
        return userDetailsServiceMap.getOrDefault(userType, userDetailsServiceMap.get("default"));
    }
}
