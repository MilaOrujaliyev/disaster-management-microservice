package org.afetankanet.disastermanagementmicroservice.controller;

import org.afetankanet.disastermanagementmicroservice.exception.DuplicateEmailException;
import org.afetankanet.disastermanagementmicroservice.exception.DuplicateUsernameException;
import org.afetankanet.disastermanagementmicroservice.exception.NotSuchAnEmailException;
import org.afetankanet.disastermanagementmicroservice.model.*;
import org.afetankanet.disastermanagementmicroservice.repository.UserRepository;
import org.afetankanet.disastermanagementmicroservice.service.PasswordResetService;
import org.afetankanet.disastermanagementmicroservice.service.UserService;
import org.afetankanet.disastermanagementmicroservice.entity.User;
import org.afetankanet.disastermanagementmicroservice.util.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;


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

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        try {
            UserResponse registeredUser = userService.registerUser(userRegisterRequest);
            return ResponseEntity.ok(registeredUser);
        } catch (DuplicateUsernameException | DuplicateEmailException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> loginUser(@RequestBody User loginUser) {
        Optional<UserLoginResponse> userLoginResponse = userService.loginUser(loginUser);
        return userLoginResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
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

    @PutMapping("/updateUserInfo")
    public ResponseEntity<?> updateUserInfo( @RequestBody UserUpdateRequest userUpdateRequest) {
        try {
            UserResponse userResponse = userService.updateUserInfo(userUpdateRequest);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Kullanıcı bilgileri güncellenemedi: " + e.getMessage()));
        }
    }

    @PutMapping("/updatePassword")
    public ResponseEntity<?> updatePassword( @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        try {
            userService.updatePassword(passwordUpdateRequest);
            return ResponseEntity.ok(new MessageResponse("Şifre başarıyla güncellendi"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Şifre güncellenemedi: " + e.getMessage()));
        }
    }

    @GetMapping("/getProfileInfo/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id) {
        return userService.getUserProfile(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/request/{email}")
    public ResponseEntity<?> requestPasswordReset(@PathVariable String email) {
        try {
            passwordResetService.processPasswordResetRequest(email);
            return ResponseEntity.ok("Şifre sıfırlama kodu gönderildi.");
        }catch (NotSuchAnEmailException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(e.getMessage()));
        }
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@RequestBody VerificationRequest verificationRequest){
        boolean isCodeValid =passwordResetService.verifyCode(verificationRequest.getEmail(),verificationRequest.getPassword());

        if(isCodeValid){
            return ResponseEntity.ok("Kod doğru girildi.");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("yanlış kod girildi.");
        }
    }

    @GetMapping("/checkNameSurname/{id}")
    public ResponseEntity<Void> checkNameSurname(@PathVariable Long id){
        if(userService.isNameSurnameFilled(id)){
            return ResponseEntity.ok().build();
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}
