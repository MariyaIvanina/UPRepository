
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utilities;

/**
 *
 * @author Mary
 */


import dbcontext.UserRepository;
import entity.Name;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;
import entity.User;
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
        try {
            validateuser(dto,"email", "registration.email.wrongPattern", "Email is not registered!", errors);
        } catch (SQLException ex) {
            Logger.getLogger(NameValidator.class.getName()).log(Level.SEVERE, null, ex);
        }
               }
 
    public void validateEmailSyntax(String email, String field, String errorCode, String defaultMessage, Errors errors) {
        Pattern p = Pattern.compile(".+@.+\\.[a-zA-z]+");
        if (!p.matcher(email).find()) {
            errors.rejectValue(field, errorCode, defaultMessage);
        }
    }
    
    public void validateuser(Name dto, String field, String errorCode, String defaultMessage, Errors errors) throws SQLException
    {
       User r = new UserRepository().getUser(dto.getEmail());
        if(r == null || (r.getEmail() == null && r.getPassword() == null))
        {
            errors.rejectValue(field, errorCode, defaultMessage);
        }
        else
        {
            if(dto.getPassword()!= null && dto.getPassword().compareTo(r.getPassword().trim())!= 0)
                errors.rejectValue("password", "registration.Password.notEquals", "Password is not right");
    }
    }
}


