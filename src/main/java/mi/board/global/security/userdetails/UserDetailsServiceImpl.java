package mi.board.global.security.userdetails;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mi.board.domain.user.entity.User;
import mi.board.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static mi.board.global.enums.ErrorCode.USER_NOT_FOUND;

@Service
@Slf4j(topic = "UserDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {

		log.info("username: {}", username);

		User user = userRepository.findByUsername(username).orElseThrow(
			() -> new UsernameNotFoundException(USER_NOT_FOUND.getDetail())
		);

		log.info("username: {}", user.getUsername());

		return new UserDetailsImpl(user);
	}
}

