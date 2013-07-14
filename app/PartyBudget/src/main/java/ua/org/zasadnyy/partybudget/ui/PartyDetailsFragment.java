package ua.org.zasadnyy.partybudget.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

import ua.org.zasadnyy.partybudget.R;

/**
 * Created by vitaliyzasadnyy on 14.07.13.
 */
public class PartyDetailsFragment extends SherlockFragment {

    public static Fragment newInstance() {
        Fragment f = new PartyDetailsFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.party_details_fragment, container, false);
        return view;
    }
}
