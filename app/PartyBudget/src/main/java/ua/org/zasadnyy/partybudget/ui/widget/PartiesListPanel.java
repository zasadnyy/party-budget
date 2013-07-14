package ua.org.zasadnyy.partybudget.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.InjectView;
import butterknife.Views;
import ua.org.zasadnyy.partybudget.R;

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
        mPartiesList.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, new String[]{"Party 1", "Party 2"}));
        mPartiesList.setOnItemClickListener(this);

        mSettingsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mListener) mListener.onItemSelected();
                Toast.makeText(getContext(), "Settings", Toast.LENGTH_SHORT).show();
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
