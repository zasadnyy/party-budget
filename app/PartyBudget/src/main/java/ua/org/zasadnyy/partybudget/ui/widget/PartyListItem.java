package ua.org.zasadnyy.partybudget.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.Views;
import ua.org.zasadnyy.partybudget.R;
import ua.org.zasadnyy.partybudget.dao.Party;
import ua.org.zasadnyy.partybudget.model.PartyStatus;

/**
 * Created by vitaliyzasadnyy on 08.08.13.
 */
public class PartyListItem extends FrameLayout {

    private static final Map<String, Integer> PARTY_STATUS_DRAWABLE_MAPPING;

    @InjectView(R.id.party_list_item_text)
    TextView mTextView;
    @InjectView(R.id.party_list_item_icon)
    ImageView mIcon;

    private boolean mAlreadyInflated = false;

    static {
        PARTY_STATUS_DRAWABLE_MAPPING = new HashMap<String, Integer>();
        PARTY_STATUS_DRAWABLE_MAPPING.put(PartyStatus.IN_PROGRESS.toString(), R.drawable.party_status_in_progress);
        PARTY_STATUS_DRAWABLE_MAPPING.put(PartyStatus.CHECKOUT.toString(), R.drawable.party_status_checkout);
        PARTY_STATUS_DRAWABLE_MAPPING.put(PartyStatus.FINISHED.toString(), R.drawable.party_status_finished);
    }

    public PartyListItem(Context context) {
        super(context);
    }

    public PartyListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static PartyListItem build(Context context) {
        PartyListItem instance = new PartyListItem(context);
        instance.onFinishInflate();
        return instance;
    }

    @Override
    public void onFinishInflate() {
        if (!mAlreadyInflated) {
            mAlreadyInflated = true;
            inflate(getContext(), R.layout.parties_list_item, this);
            Views.inject(this);
        }
        super.onFinishInflate();
    }

    public void bind(Party party){
        mTextView.setText(party.getName());
        mIcon.setImageResource(PARTY_STATUS_DRAWABLE_MAPPING.get(party.getStatus()));
    }

}

