package ua.org.zasadnyy.partybudget.ui.widget.validator;

import android.app.Activity;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import ua.org.zasadnyy.partybudget.util.UiUtils;

/**
 * Created by vitaliyzasadnyy on 01.08.13.
 */
public class Form {

    private List<Field> mFields = new ArrayList<Field>();
    private Activity mActivity;

    public Form(Activity activity) {
        this.mActivity = activity;
    }

    public void addField(Field field) {
        mFields.add(field);
    }

    public boolean isValid() {
        boolean result = true;
        try {
            for (Field field : mFields) {
                result &= field.isValid();
            }
        } catch (FieldValidationException e) {
            result = false;

            EditText textView = e.getTextView();
            textView.requestFocus();
            textView.selectAll();

            UiUtils.showKeyboard(mActivity, textView);

            showErrorMessage(e.getMessage());
        }
        return result;
    }

    protected void showErrorMessage(String message) {
        Crouton.makeText(mActivity, message, Style.ALERT).show();
    }


}
