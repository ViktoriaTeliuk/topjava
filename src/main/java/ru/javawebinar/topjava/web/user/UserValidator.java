package ru.javawebinar.topjava.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;

@Component
public class UserValidator<T> implements Validator {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return true;//.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        T user = (T) target;
        if (user instanceof UserTo) {
            User find = userRepository.getByEmail(((UserTo) user).getEmail());
            if (find != null) {
                UserTo existing = UserUtil.asTo(find);
                if (!existing.getId().equals(((UserTo) user).getId()))
                    errors.rejectValue("email", "exception.duplicateEmail");
            }
        } else {
            User existing = userRepository.getByEmail(((User) user).getEmail());
            if (existing != null)
                if (!existing.getId().equals(((User) user).getId()))
                    errors.rejectValue("email", "exception.duplicateEmail");
        }
    }
}