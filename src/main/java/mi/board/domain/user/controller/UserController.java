package mi.board.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import mi.board.domain.user.dto.AddUserRequest;
import mi.board.domain.user.service.UserService;
import mi.board.global.dto.ApiResponse;
import mi.board.global.utils.ResponseUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<?> signUp(@RequestBody AddUserRequest request) {
        return ResponseUtils.success(userService.save(request));
    }

    @GetMapping("/logout")
    public ApiResponse<?> logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return ResponseUtils.success();
    }
}
