package org.afetankanet.disastermanagementmicroservice.service;

import jakarta.transaction.Transactional;
import org.afetankanet.disastermanagementmicroservice.entity.User;
import org.afetankanet.disastermanagementmicroservice.exception.NotSuchAnEmailException;
import org.afetankanet.disastermanagementmicroservice.model.UserResponse;
import org.afetankanet.disastermanagementmicroservice.repository.UserRepository;
import org.afetankanet.disastermanagementmicroservice.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PasswordResetService {

    @Autowired
    private EmailClientService emailClientService;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void processPasswordResetRequest(String email) {

        Optional<User> userOptional =userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            String token = PasswordUtil.generateToken(); // Rastgele şifre sıfırlama token'ı üret
            User user= userOptional.get();
            user.setPassword(token);
            userRepository.save(user); // Token'ı veritabanında saklama işlemleri burada yapılabilir

            setVerificationCode(email, token);

            Map<String, Object> extraData = new HashMap<>();
            extraData.put("token", token);

            emailClientService.sendEmail(
                    new UserResponse(user.getEmail(), user.getUsername()),
                    "Şifre Sıfırlama Kodunuz",
                    "password-reset-template",
                    extraData
            );
        }else{
            throw new NotSuchAnEmailException("Sistemde Böyle Bir email Bulunmamaktadır!");
        }

    }


    private final Map<String, String> userVerificationCodes = new HashMap<>();

    public boolean verifyCode(String email, String enteredCode) {
        //Map<String, String> türündeki bu nesne, e-posta adresini anahtar (key) olarak ve doğrulama kodunu değer (value) olarak saklar.
        String correctCode = userVerificationCodes.get(email); // Kullanıcının e-postasına ait doğru kodu alın

        return enteredCode.equals(correctCode); // karşılaştırma
    }

    public void setVerificationCode(String email, String code) {
        userVerificationCodes.put(email, code); // Kullanıcının e-postasına ait kodu saklama
    }


}

