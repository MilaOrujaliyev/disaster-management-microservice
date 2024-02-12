package org.afetankanet.disastermanagementmicroservice.controller;

import org.afetankanet.disastermanagementmicroservice.exception.DuplicateEmailException;
import org.afetankanet.disastermanagementmicroservice.exception.DuplicateUsernameException;
import org.afetankanet.disastermanagementmicroservice.repository.UserRepository;
import org.afetankanet.disastermanagementmicroservice.service.UserService;
import org.afetankanet.disastermanagementmicroservice.entity.User;
import org.afetankanet.disastermanagementmicroservice.util.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@CrossOrigin(origins = "*") // Tüm originlere izin ver
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(registeredUser);
        } catch (DuplicateUsernameException | DuplicateEmailException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User loginUser) {
        Optional<User> user = userService.loginUser(loginUser);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/uploadProfilePicture")
    public ResponseEntity<?> uploadProfilePicture(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        try {
            userService.uploadProfilePicture(userId, file);
            return ResponseEntity.ok(new MessageResponse("imageSuccess","Profil resmi başarıyla yüklendi!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("imageFormatFail",e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("imageUnsuccess","Profil resmi yüklenemedi!!"));
        }
    }



}
