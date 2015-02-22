package bg.uni_sofia.fmi.oopjava.project.user.validation;

import bg.uni_sofia.fmi.oopjava.project.exceptions.InvalidCardNumberException;

/**
 *
 * @author Dimitar Panayotov
 */
public interface CardValidation {

    void validateCardNumber() throws InvalidCardNumberException;
    
    default void validateCardNumber(String number) throws InvalidCardNumberException {
        if(number.replaceAll("\\s", "").trim().length() != 16){
            throw new InvalidCardNumberException("Card number must contain exactly 16 digits!");
        }
        if(!validateStartingDigit(number)){
            throw new InvalidCardNumberException("Card number must start with either 3,4,5 or 6!");
        }
        if(!validateLuhn(number)){
            throw new InvalidCardNumberException("Invalid card number!");
        }
    }

    boolean validateStartingDigit(String number);

    boolean validateLuhn(String number);
}