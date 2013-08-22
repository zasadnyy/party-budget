package ua.org.zasadnyy.partybudget.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.InjectView;
import butterknife.Views;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import de.timroes.swipetodismiss.SwipeDismissList;
import ua.org.zasadnyy.partybudget.PartyBudgetApplication;
import ua.org.zasadnyy.partybudget.R;
import ua.org.zasadnyy.partybudget.dao.DaoSession;
import ua.org.zasadnyy.partybudget.dao.Party;
import ua.org.zasadnyy.partybudget.dao.PartyDao;
import ua.org.zasadnyy.partybudget.dao.Payer;
import ua.org.zasadnyy.partybudget.dao.PayerDao;
import ua.org.zasadnyy.partybudget.dao.Transaction;
import ua.org.zasadnyy.partybudget.dao.TransactionDao;
import ua.org.zasadnyy.partybudget.ui.PartyListPanelAdapter;

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
        SwipeDismissList swipeList = new SwipeDismissList(mPartiesList, new OnDismissCallback());
        swipeList.setSwipeDirection(SwipeDismissList.SwipeDirection.END);

        PartyDao partyDao = PartyBudgetApplication.getDaoSession().getPartyDao();
        List<Party> parties = partyDao.loadAll();

        mPartiesList.setAdapter(new PartyListPanelAdapter(getContext(), parties));
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


    private static class OnDismissCallback implements SwipeDismissList.OnDismissCallback {

        public SwipeDismissList.Undoable onDismiss(AbsListView listView, int position) {
            ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();
            Party party = (Party) adapter.getItem(position);
            adapter.remove(party);
            removeFromDb(party);
            // Return an Undoable object to make action undonable
            return null;
        }

        private void removeFromDb(final Party party) {
            DaoSession daoSession = PartyBudgetApplication.getDaoSession();
            final PartyDao partyDao = daoSession.getPartyDao();
            final PayerDao payerDao = daoSession.getPayerDao();
            final TransactionDao transactionDao = daoSession.getTransactionDao();

            daoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    for(Payer payer : party.getPayers()) {
                        for(Transaction transaction : payer.getTransactions()) {
                            transactionDao.delete(transaction);
                        }
                        payerDao.delete(payer);
                    }
                    partyDao.delete(party);
                }
            });

        }

    }
}
