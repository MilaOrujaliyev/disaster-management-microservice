package org.afetankanet.disastermanagementmicroservice.exception;

import org.afetankanet.disastermanagementmicroservice.controller.UserController;
import org.afetankanet.disastermanagementmicroservice.util.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<Object> handleMultipartException(MultipartException exc) {
        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(new MessageResponse("Dosya boyutu limiti aşıldı. Lütfen dosya boyutu 5 MB'dan küçük olmalıdır."));
    }


}
