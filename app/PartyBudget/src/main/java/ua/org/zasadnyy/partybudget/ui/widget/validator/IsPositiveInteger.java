package ua.org.zasadnyy.partybudget.ui.widget.validator;

import ua.org.zasadnyy.partybudget.R;

/**
 * Created by vitaliyzasadnyy on 01.08.13.
 */
public class IsPositiveInteger implements Validation {

    @Override
    public int getErrorMessageId() {
        return R.string.party_details_not_positive_int_party_size;
    }

    @Override
    public boolean isValid(String text) {
        return text.matches("\\d+");
    }
}
