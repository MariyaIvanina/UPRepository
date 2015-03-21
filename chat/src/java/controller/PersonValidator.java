/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import repository.entity.User;
import repository.methods.Repository;
/**
 *
 * @author Mary
 */
@Component
public class PersonValidator implements Validator{
     @Override
    public boolean supports(Class aClass) {
        return aClass.equals(Person.class);
    }
 
    @Override
    public void validate(Object o, Errors errors) {
        Person dto = (Person) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "registration.password.empty", "name is null!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "registration.password.empty", "Password is the mandatory field!");
        validateEmailSyntax(dto.getEmail(), "email", "registration.email.wrongPattern", "Email is wrong!", errors);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "repeatPassword", "registration.repeatPassword.empty", "Repeat the password");
        if (!dto.getPassword().equals(dto.getRepeatPassword())) {
            errors.rejectValue("repeatPassword", "registration.repeatPassword.notEquals", "Passwords are not equals");
        }
        validateuser(dto.getEmail(),"email", "registration.email.wrongPattern", "Email has already been registered!", errors);
               }
 
    public void validateEmailSyntax(String email, String field, String errorCode, String defaultMessage, Errors errors) {
        Pattern p = Pattern.compile(".+@.+\\.[a-zA-z]+");
        if (!p.matcher(email).find()) {
            errors.rejectValue(field, errorCode, defaultMessage);
        }
    }
    public void validateuser(String email, String field, String errorCode, String defaultMessage, Errors errors)
    {
        User r = new UserRepository().getUser(email);
        
        if(r != null) {
            errors.rejectValue(field, errorCode, defaultMessage);
        }
    }
}

