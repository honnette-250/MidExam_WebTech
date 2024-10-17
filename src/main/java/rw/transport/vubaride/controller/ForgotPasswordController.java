package rw.transport.vubaride.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rw.transport.vubaride.model.User;
import rw.transport.vubaride.repository.UserRepository;
import rw.transport.vubaride.service.EmailService;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ForgotPasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);

    @PostMapping("/forgot-password")
    public ResponseEntity<String> processForgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Check if email is provided
        if (email == null || email.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required.");
        }

        // Fetch the user by email
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (!userOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        User user = userOpt.get();

        // Generate a reset token
        String token = UUID.randomUUID().toString();
        user.setResetPasswordToken(token);
        userRepository.save(user); // Save the user with the reset token

        // Prepare and send the reset password email
        String resetLink = "http://127.0.0.1:5501/reset.html" + token; // Replace with actual URL
        String subject = "Reset Password";
        String body = "To reset your password, click the link below:\n" + resetLink;

        try {
            emailService.sendSimpleEmail(user.getEmail(), subject, body);
            return ResponseEntity.ok("Reset password email sent.");
        } catch (Exception e) {
            logger.error("Failed to send reset email to {}: {}", user.getEmail(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send reset email.");
        }
    }
}
