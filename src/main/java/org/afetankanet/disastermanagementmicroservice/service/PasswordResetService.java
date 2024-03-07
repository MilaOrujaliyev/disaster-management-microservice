package org.afetankanet.disastermanagementmicroservice.service;

import org.afetankanet.disastermanagementmicroservice.entity.User;
import org.afetankanet.disastermanagementmicroservice.exception.NotSuchAnEmailException;
import org.afetankanet.disastermanagementmicroservice.model.UserResponse;
import org.afetankanet.disastermanagementmicroservice.repository.UserRepository;
import org.afetankanet.disastermanagementmicroservice.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordResetService {

    @Autowired
    private EmailClientService emailClientService;

    @Autowired
    private UserRepository userRepository;

    public void processPasswordResetRequest(String email) {

        Optional<User> userOptional =userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            String token = PasswordUtil.generateToken(); // Rastgele şifre sıfırlama token'ı üret
            User user= userOptional.get();
            user.setPassword(token);
            userRepository.save(user); // Token'ı veritabanında saklama işlemleri burada yapılabilir

            emailClientService.sendEmail(new UserResponse(user.getEmail(),user.getUsername()),"Şifre Sıfırlama Kodunuz","password-reset-template");
        }else{
            throw new NotSuchAnEmailException("Sistemde Böyle Bir email Bulunmamaktadır!");
        }

    }


}

