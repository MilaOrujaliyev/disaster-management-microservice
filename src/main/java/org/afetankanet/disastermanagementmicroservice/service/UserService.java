package org.afetankanet.disastermanagementmicroservice.service;

import jakarta.transaction.Transactional;
import org.afetankanet.disastermanagementmicroservice.converter.UserToProfileInfoResponseConverter;
import org.afetankanet.disastermanagementmicroservice.entity.User;
import org.afetankanet.disastermanagementmicroservice.entity.Vote;
import org.afetankanet.disastermanagementmicroservice.exception.DuplicateEmailException;
import org.afetankanet.disastermanagementmicroservice.exception.DuplicateUsernameException;
import org.afetankanet.disastermanagementmicroservice.model.*;
import org.afetankanet.disastermanagementmicroservice.repository.UserRepository;
import org.afetankanet.disastermanagementmicroservice.repository.VoteRepository;
import org.afetankanet.disastermanagementmicroservice.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailClientService emailClientService;

    @Autowired
    private VoteRepository voteRepository;

    @Transactional
    public UserResponse registerUser(UserRegisterRequest userRegisterRequest) throws DuplicateUsernameException, DuplicateEmailException {

            if(userRepository.findByEmail(userRegisterRequest.getEmail()).isPresent()){
                throw new DuplicateEmailException("Bu e-mail adresi zaten kullanımda.");
            }

            if(userRepository.findByUsername(userRegisterRequest.getUsername()).isPresent()){
                throw new DuplicateUsernameException("Bu kullanıcı adı zaten mevcut.");
            }

            User user = new User();
            user.setUsername(userRegisterRequest.getUsername());
            user.setEmail(userRegisterRequest.getEmail());
            user.setPassword(PasswordUtil.hashPassword(userRegisterRequest.getPassword()));

            User registeredUser = userRepository.save(user);

            UserResponse userResponse = new UserResponse();
            userResponse.setId(registeredUser.getId());
            userResponse.setEmail(registeredUser.getEmail());
            userResponse.setUsername(registeredUser.getUsername());

            emailClientService.sendEmail(userResponse,"Hoş Geldiniz!","welcome-email");

            return userResponse;

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

    @Transactional
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

    @Transactional
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

    @Transactional
    public void updatePassword(PasswordUpdateRequest passwordUpdateRequest) {
        User user;
        if (passwordUpdateRequest.getEmail() != null) {
            user = userRepository.findByEmail(passwordUpdateRequest.getEmail()).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));
        } else {
            user = userRepository.findById(passwordUpdateRequest.getUserId()).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı."));
        }

        String hashedPassword = PasswordUtil.hashPassword(passwordUpdateRequest.getNewPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setUsername(user.getUsername());

        emailClientService.sendEmail(userResponse, "Şifreniz Değiştirildi!", "password-change-confirmation");
    }

    @Transactional
    public Optional<ProfileInfoResponse> getUserProfile(Long id) {
        return userRepository.findById(id).map(UserToProfileInfoResponseConverter::convert);
    }

    @Transactional
    public List<ProfileInfoResponse> getAllUserProfiles() {
        return userRepository.findAll().stream()
                .map(UserToProfileInfoResponseConverter::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean isNameSurnameFilled(Long userId){
        Optional<User> userOptional=userRepository.findById(userId);
        return userOptional.isPresent() && userOptional.get().getNameSurname()!= null && !userOptional.get().getNameSurname().trim().isEmpty();
    }

    @Transactional
    public void updateTrustScore(Long votedUserId) {
        User user = userRepository.findById(votedUserId)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        List<Vote> userVotes = voteRepository.findByVotedUserId(votedUserId);
        int newScore = calculateNewTrustScore(userVotes);
        user.setTrustScore(newScore);
        userRepository.save(user);
    }

    private int calculateNewTrustScore(List<Vote> votes) {
        if (votes.isEmpty()) {
            return 0; // Eğer kullanıcıya hiç oy verilmemişse, varsayılan skor= 0
        }

        int totalScore = votes.stream()
                .mapToInt(Vote::getScore)
                .sum();

        return Math.round((float) totalScore / votes.size());
    }
}
