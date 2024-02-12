package org.afetankanet.disastermanagementmicroservice.service;

import org.afetankanet.disastermanagementmicroservice.controller.UserController;
import org.afetankanet.disastermanagementmicroservice.entity.User;
import org.afetankanet.disastermanagementmicroservice.exception.DuplicateEmailException;
import org.afetankanet.disastermanagementmicroservice.exception.DuplicateUsernameException;
import org.afetankanet.disastermanagementmicroservice.model.PasswordUpdateRequest;
import org.afetankanet.disastermanagementmicroservice.repository.UserRepository;
import org.afetankanet.disastermanagementmicroservice.util.PasswordUtil;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) throws DuplicateUsernameException, DuplicateEmailException {
        try {
            user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
            return userRepository.save(user);
        } catch (Exception e) {
            if(e.getMessage().contains("user_email_key")) {
                throw new DuplicateEmailException("Bu e-mail adresi zaten kullanımda.");
            } else if(e.getMessage().contains("user_username_key")) {
                throw new DuplicateUsernameException("Bu kullanıcı adı zaten mevcut.");
            }
            throw e;
        }
    }

    public Optional<User> loginUser(User loginUser) {
        Optional<User> user = userRepository.findByUsername(loginUser.getUsername());
        if (user.isPresent() && PasswordUtil.checkPassword(loginUser.getPassword(), user.get().getPassword())) {
            return user;
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

    public User updateUserInfo( User updatedUserInfo) {
        User user = userRepository.findById(updatedUserInfo.getId()).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));

        if (updatedUserInfo.getEmail() != null && !updatedUserInfo.getEmail().isEmpty()) {
            user.setEmail(updatedUserInfo.getEmail());
        }
        if (updatedUserInfo.getUsername() != null && !updatedUserInfo.getUsername().isEmpty()) {
            user.setUsername(updatedUserInfo.getUsername());
        }

        return userRepository.save(user);
    }

    public void updatePassword(PasswordUpdateRequest passwordUpdateRequest) {
        User user = userRepository.findById(passwordUpdateRequest.getUserId()).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));

        // PasswordUtil sınıfı kullanılarak şifreyi hashleme
        String hashedPassword = PasswordUtil.hashPassword(passwordUpdateRequest.getNewPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
}
