package ua.org.zasadnyy.partybudget.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.List;

import ua.org.zasadnyy.partybudget.dao.Party;
import ua.org.zasadnyy.partybudget.ui.widget.PartyListItem;

/**
 * Created by vitaliyzasadnyy on 08.08.13.
 */
public class PartyListPanelAdapter extends ArrayAdapter<Party>{
    public PartyListPanelAdapter(Context context, List<Party> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PartyListItem partyView;

        if(convertView == null) {
            partyView = PartyListItem.build(getContext());
        } else {
            partyView = (PartyListItem) convertView;
        }

        Party party = getItem(position);
        partyView.bind(party);

        return partyView;
    }
}
