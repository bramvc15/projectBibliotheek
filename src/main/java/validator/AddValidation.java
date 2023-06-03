package validator;

import java.awt.print.Book;
import java.util.List;
import java.util.Set;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import domain.Auteur;
import domain.Boek;
import jakarta.validation.ConstraintViolation;

import jakarta.validation.executable.ExecutableValidator;
import jakarta.validation.metadata.BeanDescriptor;

public class AddValidation implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Book.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Boek boek = (Boek) target;
		String naamBoek = boek.getNaam();
		List<Auteur> auteurList = boek.getAuteurs();
		
		if (naamBoek == null) {

		}

	}

}
