package org.afetankanet.disastermanagementmicroservice.service;

import jakarta.transaction.Transactional;
import org.afetankanet.disastermanagementmicroservice.entity.User;
import org.afetankanet.disastermanagementmicroservice.exception.DuplicateEmailException;
import org.afetankanet.disastermanagementmicroservice.exception.DuplicateUsernameException;
import org.afetankanet.disastermanagementmicroservice.model.*;
import org.afetankanet.disastermanagementmicroservice.repository.UserRepository;
import org.afetankanet.disastermanagementmicroservice.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse registerUser(UserRegisterRequest userRegisterRequest) throws DuplicateUsernameException, DuplicateEmailException {
        try {
            User user = new User();
            user.setUsername(userRegisterRequest.getUsername());
            user.setEmail(userRegisterRequest.getEmail());
            user.setPassword(PasswordUtil.hashPassword(userRegisterRequest.getPassword()));

            User registeredUser = userRepository.save(user);

            UserResponse userResponse = new UserResponse();
            userResponse.setId(registeredUser.getId());
            userResponse.setEmail(registeredUser.getEmail());
            userResponse.setUsername(registeredUser.getUsername());

            return userResponse;
        } catch (Exception e) {
            if(e.getMessage().contains("user_email_key")) {
                throw new DuplicateEmailException("Bu e-mail adresi zaten kullanımda.");
            } else if(e.getMessage().contains("user_username_key")) {
                throw new DuplicateUsernameException("Bu kullanıcı adı zaten mevcut.");
            }
            throw e;
        }
    }
    @Transactional
    public Optional<UserLoginResponse> loginUser(User loginUser) {
        Optional<User> user = userRepository.findByUsername(loginUser.getUsername());

        if (user.isPresent() && PasswordUtil.checkPassword(loginUser.getPassword(), user.get().getPassword())) {
            UserLoginResponse userLoginResponse = new UserLoginResponse();
            userLoginResponse.setId(user.get().getId());
            userLoginResponse.setUsername(user.get().getUsername());
            userLoginResponse.setProfilePicture(user.get().getProfilePicture());
            userLoginResponse.setEmail(user.get().getEmail());

            return Optional.of(userLoginResponse);
        }
        return Optional.empty();
    }

    public void uploadProfilePicture(Long userId, MultipartFile file) throws IOException {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));

        // MIME türü ve dosya boyutu kontrolü
        String contentType = file.getContentType();
        if (contentType == null || (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) ) {
            throw new IllegalArgumentException("Yalnızca JPEG veya PNG formatında resim dosyaları kabul edilmektedir!");
        }

        byte[] imageBytes = file.getBytes();
        user.setProfilePicture(imageBytes);
        userRepository.save(user);
    }

    public UserResponse updateUserInfo(UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(userUpdateRequest.getId()).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));

        if (userUpdateRequest.getEmail() != null && !userUpdateRequest.getEmail().isEmpty()) {
            user.setEmail(userUpdateRequest.getEmail());
        }
        if (userUpdateRequest.getUsername() != null && !userUpdateRequest.getUsername().isEmpty()) {
            user.setUsername(userUpdateRequest.getUsername());
        }
        if (userUpdateRequest.getNameSurname() != null  && !userUpdateRequest.getNameSurname().isEmpty()) {
            user.setNameSurname(userUpdateRequest.getNameSurname());
        }
        User updatedUser = userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(updatedUser.getId());
        userResponse.setEmail(updatedUser.getEmail());
        userResponse.setUsername(updatedUser.getUsername());
        userResponse.setNameSurname(updatedUser.getNameSurname());

        return userResponse;
    }

    public void updatePassword(PasswordUpdateRequest passwordUpdateRequest) {
        User user = userRepository.findById(passwordUpdateRequest.getUserId()).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));

        // PasswordUtil sınıfı kullanılarak şifreyi hashleme
        String hashedPassword = PasswordUtil.hashPassword(passwordUpdateRequest.getNewPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
}
