/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

/**
 *
 * @author Mary
 */


import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import repository.entity.User;
import repository.methods.Repository;
/**
 *
 * @author Mary
 */
@Component
public class NameValidator implements Validator{
    
    @Override
    public boolean supports(Class aClass) {
        return aClass.equals(Name.class);
    }
 
    @Override
    public void validate(Object o, Errors errors) {
        Name dto = (Name) o;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "registration.password.empty", "Password is the mandatory field!");
        validateEmailSyntax(dto.getEmail(), "email", "registration.email.wrongPattern", "Email is wrong!", errors);
        validateuser(dto,"email", "registration.email.wrongPattern", "Email is not registered!", errors);
               }
 
    public void validateEmailSyntax(String email, String field, String errorCode, String defaultMessage, Errors errors) {
        Pattern p = Pattern.compile(".+@.+\\.[a-zA-z]+");
        if (!p.matcher(email).find()) {
            errors.rejectValue(field, errorCode, defaultMessage);
        }
    }
    
    public void validateuser(Name dto, String field, String errorCode, String defaultMessage, Errors errors)
    {
       User r = new UserRepository().getUser(dto.getEmail());
        if(r == null)
            errors.rejectValue(field, errorCode, defaultMessage);
        else
        {
            if(dto.getPassword().compareTo(r.getPassword().trim())!= 0)
                errors.rejectValue("password", "registration.Password.notEquals", "Password is not right");
    }   }
}


