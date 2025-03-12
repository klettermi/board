package mi.board.domain.user.service;

import lombok.RequiredArgsConstructor;
import mi.board.domain.user.dto.AddUserRequest;
import mi.board.domain.user.entity.User;
import mi.board.domain.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequest request) {
        return userRepository.save(
                User.builder()
                        .username(request.getUsername())
                        .password(bCryptPasswordEncoder.encode(request.getPassword()))
                        .build()
                ).getId();
    }
}
