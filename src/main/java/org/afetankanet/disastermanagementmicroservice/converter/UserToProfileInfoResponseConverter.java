package org.afetankanet.disastermanagementmicroservice.converter;

import org.afetankanet.disastermanagementmicroservice.entity.User;
import org.afetankanet.disastermanagementmicroservice.model.ProfileInfoResponse;

import java.util.Base64;

public class UserToProfileInfoResponseConverter {

    public static ProfileInfoResponse convert(User user) {
        if (user == null) {
            return null;
        }

        ProfileInfoResponse profileInfoResponse = new ProfileInfoResponse();
        profileInfoResponse.setId(user.getId());
        profileInfoResponse.setUsername(user.getUsername());
        profileInfoResponse.setNameSurname(user.getNameSurname());
        profileInfoResponse.setEmail(user.getEmail());


        if (user.getProfilePicture() != null) {
            profileInfoResponse.setProfilePicture(Base64.getEncoder().encodeToString(user.getProfilePicture()));
        } else {
            profileInfoResponse.setProfilePicture(null);
        }

        return profileInfoResponse;
    }
}

