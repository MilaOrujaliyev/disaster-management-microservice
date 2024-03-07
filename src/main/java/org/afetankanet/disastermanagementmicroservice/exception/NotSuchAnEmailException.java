package org.afetankanet.disastermanagementmicroservice.exception;

public class NotSuchAnEmailException extends RuntimeException{
    public NotSuchAnEmailException(String message){
        super(message);}
}
