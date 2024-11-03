package rw.management.OnlineManagementSystem.security.User;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import rw.management.OnlineManagementSystem.model.User;
import rw.management.OnlineManagementSystem.repository.UserRepository;
@Service
@RequiredArgsConstructor
public class OnlineCourseDetails implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
return CourseDetails.buildUserDetails(user);
}

}
