
package com.epam.gtc.web.commands;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator for User input validation.
 * 
 * @author Fazliddin Makhsudov
 * 
 */
public final class Validator {

	/**
	 * Check if user entered valid email while registration.
	 * 
	 * @param email
	 *            user input to check
	 * @return true if entered text is valid email, false otherwise
	 */
	public static boolean isValidEmail(final String email) {
		if (email == null || email.isEmpty()) {
			return false;
		}
		Pattern pattern = Pattern
				.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	/**
	 * Check if user entered valid number.
	 * 
	 * @param number
	 *            user input to check
	 * @return true if entered text is valid number, false otherwise
	 */
	public static boolean isValidNumber(final String number) {
		if (number == null || number.isEmpty()) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[0-9.]+$");
		Matcher matcher = pattern.matcher(number);
		return matcher.matches();
	}

	/**
	 * Check if user entered passwords match while registration.
	 * 
	 * @param pass
	 *            entered password
	 * @param verification
	 *            entered password verification
	 * @return true, if passwords match, false otherwise
	 */
	public static boolean isValidPassword(final String pass, final String verification) {
		if (pass == null || verification == null || pass.isEmpty() || verification.isEmpty()) {
			return false;
		}
		if (pass.length() < 4 || verification.length() < 4) {
			return false;
		}
		return pass.equals(verification);
	}

	/**
	 * Check if user entered data has string format.
	 * 
	 * @param string
	 *            string to check
	 * @return true, if string has valid format
	 */
	public static boolean isValidString(final String string) {
		if (string == null || string.isEmpty()) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[a-zA-Z\u0430-\u044F\u0410-\u042F\u0451\u04010-9. _]{2,}$");
		Matcher matcher = pattern.matcher(string);
		return matcher.matches();
	}

	/**
	 * Check if user entered data has date format.
	 * 
	 * @param date
	 *            date to check
	 * @return true, if data has date format
	 */
	public static boolean isValidDate(final String date) {
		if (date == null || date.isEmpty()) {
			return false;
		}
		return date.matches("[\\d]{4}-[\\d]{2}-[\\d]{2}T[\\d]{2}:[\\d]{2}");
	}

	/**
	 * Check whether string length greater than given length param.
	 * 
	 * @param string
	 *            string to check
	 * @param length
	 *            length
	 * @return boolean
	 */
	public static boolean isValidLength(final String string, final int length) {
		if (string == null || string.isEmpty() || length <= 0) {
			return false;
		}
		return string.length() > length;
	}

	/**
	 * Checks if out of date
	 * @param date date
	 * @return boolean
	 */
	public static boolean isValidDateAfterToday(Date date) {
		return new Date().compareTo(date) < 0;
	}

	/**
	 * Checks if out of date
	 * @param date date
	 * @return boolean
	 */
	public static boolean isValidDateAfterToday(LocalDateTime date) {
		return LocalDateTime.now().compareTo(date) < 0;
	}


	/**
	 * Constructor.
	 */
	private Validator() {
		
	}
}
