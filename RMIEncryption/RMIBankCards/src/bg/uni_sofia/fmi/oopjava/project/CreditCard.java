package bg.uni_sofia.fmi.oopjava.project;

import bg.uni_sofia.fmi.oopjava.project.exceptions.InvalidCardNumberException;
import bg.uni_sofia.fmi.oopjava.project.user.validation.CardValidation;

/**
 *
 * @author Dimitar Panayotov
 */
public final class CreditCard implements CardValidation {

    private String number;

    public CreditCard(String number){
        setNumber(number);
    }
    
    public String getNumber() {
        return number;
    }
    
    private void setNumber(String number) {
        this.number = number.replaceAll("\\s", "");
    }
    
    /**
     * Removes the spaces from a number divided into groups of 4 digits
     * @return 16 digit formatted number
     */
    public String getFormattedNumber(){
        StringBuilder result = new StringBuilder();
        for(int i = 0; i<number.length(); i++){
            result.append(number.charAt(i));
            if((i+1)%4 == 0){
                result.append(' ');
            }
        }
        
        return result.toString();
    }
    
    /**
     *
     * @param number
     * @return
     */
    @Override
    public boolean validateStartingDigit(String number) {
        int firstNumber = Integer.parseInt(String.valueOf(number.charAt(0)));
        return firstNumber >= 3 && firstNumber <= 6;
    }

    /**
     *
     * @param number
     * @return
     */
    @Override
    public boolean validateLuhn(String number) {
        int sum1 = 0, sum2 = 0;
        String trimmed = number.replaceAll("\\s", "");
        String reversed = new StringBuilder(trimmed).reverse().toString();
        for (int i = 0; i<reversed.length(); i++){
            int digit = Character.digit(reversed.charAt(i), 10);
            if(i % 2 == 1){
                sum2 += digit * 2;
                if(digit >= 5){
                    sum2-=9;
                }
            }else{
                sum1 += digit;
            }
        }
        return (sum1 + sum2) % 10 == 0;
    }

    /**
     *
     * @throws InvalidCardNumberException
     */
    @Override
    public void validateCardNumber() throws InvalidCardNumberException {
        validateCardNumber(this.number);
    }
}
