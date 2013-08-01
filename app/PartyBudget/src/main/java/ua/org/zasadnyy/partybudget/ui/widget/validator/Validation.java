package ua.org.zasadnyy.partybudget.ui.widget.validator;

/**
 * Created by vitaliyzasadnyy on 01.08.13.
 */
public interface Validation {

    int getErrorMessageId();

    boolean isValid(String text);

}
