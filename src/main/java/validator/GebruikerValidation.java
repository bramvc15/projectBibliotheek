package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import domain.User;

public class GebruikerValidation implements Validator {
	@Override
	public void validate(Object target, Errors errors) {
		User gebruiker = (User) target;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}
}
