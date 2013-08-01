package ua.org.zasadnyy.partybudget.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Views;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import ua.org.zasadnyy.partybudget.PartyBudgetApplication;
import ua.org.zasadnyy.partybudget.R;
import ua.org.zasadnyy.partybudget.dao.Party;
import ua.org.zasadnyy.partybudget.dao.PartyDao;
import ua.org.zasadnyy.partybudget.model.PartyStatus;
import ua.org.zasadnyy.partybudget.ui.widget.validator.Field;
import ua.org.zasadnyy.partybudget.ui.widget.validator.Form;
import ua.org.zasadnyy.partybudget.ui.widget.validator.IsPositiveInteger;
import ua.org.zasadnyy.partybudget.ui.widget.validator.NotEmpty;
import ua.org.zasadnyy.partybudget.util.UiUtils;

/**
 * Created by vitaliyzasadnyy on 14.07.13.
 */
public class PartyDetailsFragment extends SherlockFragment {

    private static final int MIN_PARTY_SIZE = 1;
    @InjectView(R.id.party_details_name)
    EditText mNameTextView;
    @InjectView(R.id.party_details_size)
    EditText mSizeTextView;
    private Form mForm;

    public static Fragment newInstance() {
        Fragment f = new PartyDetailsFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.party_details_fragment, container, false);
        Views.inject(this, view);

        mForm = new Form(getActivity());
        mForm.addField(Field.using(mNameTextView).validate(new NotEmpty()));
        mForm.addField(Field.using(mSizeTextView).validate(new IsPositiveInteger()));

        // submit form when last field is done
        mSizeTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    UiUtils.hideKeyboard(getActivity(), view);
                    submit();
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        Views.reset(this);
        super.onDestroyView();
    }

    @OnClick(R.id.party_details_submit)
    void submit() {
        String partyName = mNameTextView.getText().toString().trim();
        String partySize = mSizeTextView.getText().toString().trim();

        if (mForm.isValid()) {
            long partyId = persistParty(partyName, Integer.parseInt(partySize));
            //switch to next fragment
            Crouton.makeText(getActivity(), "Party id is " + partyId, Style.INFO).show();
        }
    }

    private long persistParty(String name, int size) {
        Party party = new Party(null, name, PartyStatus.IN_PROGRESS.toString(), size);
        PartyDao dao = PartyBudgetApplication.getDaoSession().getPartyDao();
        return dao.insert(party);
    }

}
