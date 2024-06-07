package taba.tabaServer.tabaserver.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import taba.tabaServer.tabaserver.domain.User;
import taba.tabaServer.tabaserver.repository.UserRepository;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // 권한 설정, 여기서는 모든 사용자를 "USER" 권한을 가지게 설정
        /**
         * CustomUserDetails로 변경
         */
        return new CustomUserDetails(
                user.getEmail(),
                user.getPassword(), // 비밀번호가 없을 경우 null이 될 수 있음
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
