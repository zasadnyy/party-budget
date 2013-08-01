package ua.org.zasadnyy.partybudget.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.Views;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import ua.org.zasadnyy.partybudget.PartyBudgetApplication;
import ua.org.zasadnyy.partybudget.R;
import ua.org.zasadnyy.partybudget.dao.Party;
import ua.org.zasadnyy.partybudget.dao.PartyDao;

/**
 * Created by vitaliyzasadnyy on 14.07.13.
 */
public class PartiesListPanel extends RelativeLayout implements ListView.OnItemClickListener {

    @InjectView(R.id.drawer_parties_list)
    ListView mPartiesList;
    @InjectView(R.id.drawer_settings_button)
    TextView mSettingsButton;
    private boolean mAlreadyInflated = false;
    private ItemSelectedListener mListener;

    public PartiesListPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onFinishInflate() {
        if (!mAlreadyInflated) {
            mAlreadyInflated = true;
            inflate(getContext(), R.layout.parties_list_panel, this);
            Views.inject(this);
            initPanel();
        }
        super.onFinishInflate();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (null != mListener) mListener.onItemSelected();
    }

    private void initPanel() {

        PartyDao partyDao = PartyBudgetApplication.getDaoSession().getPartyDao();
        List<Party> parties = partyDao.loadAll();

        List<String> menuEntries = new ArrayList<String>(parties.size());
        for (Party party : parties) {
            menuEntries.add(party.getName());
        }

        mPartiesList.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, menuEntries));
        mPartiesList.setOnItemClickListener(this);

        mSettingsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mListener) mListener.onItemSelected();
                Crouton.makeText((Activity) getContext(), "Settings", Style.INFO).show();
            }
        });
    }

    public void setItemSelectedListener(ItemSelectedListener listener) {
        mListener = listener;
    }

    public interface ItemSelectedListener {
        void onItemSelected();
    }
}
