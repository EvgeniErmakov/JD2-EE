package by.newsportal.news.service.validation;

import by.newsportal.news.bean.RegistrationInfo;
import by.newsportal.news.service.exception.ServiceException;

public class ValidationInformation {

	private static final String CHECK_EMAIL = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
	private static final String CHECK_PASSWORD =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
	private static final String CHECK_NAME_SURNAME = "^[a-zA-Zà-ÿÀ-ß0-9-_\\.]{3,30}$";

	private ValidationInformation() {
	}

	public static boolean validationRegistrationInformation(RegistrationInfo info) throws ServiceException {

		if (!info.getEnteredPassword().equals(info.getRepeatedPassword())) {
			return false;
		}

		return checkEmail(info.getEmail()) && checkPassword(info.getEnteredPassword()) && checkName(info.getName())
				&& checkSurname(info.getSurname());
	}

	public static boolean checkPassword(String password) {
		return password.matches(CHECK_PASSWORD);
	}

	public static boolean checkEmail(String email) {
		return email.matches(CHECK_EMAIL);
	}

	public static boolean checkName(String name) {
		return name.matches(CHECK_NAME_SURNAME);
	}

	public static boolean checkSurname(String surname) {
		return surname.matches(CHECK_NAME_SURNAME);
	}

}
