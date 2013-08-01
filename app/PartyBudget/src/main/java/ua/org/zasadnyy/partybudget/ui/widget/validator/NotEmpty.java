package ua.org.zasadnyy.partybudget.ui.widget.validator;


import android.text.TextUtils;

import ua.org.zasadnyy.partybudget.R;

/**
 * Created by vitaliyzasadnyy on 01.08.13.
 */
public class NotEmpty implements Validation {

    @Override
    public int getErrorMessageId() {
        return R.string.party_details_empty_party_name_error;
    }

    @Override
    public boolean isValid(String text) {
        return !TextUtils.isEmpty(text);
    }
}
