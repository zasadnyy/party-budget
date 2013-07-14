package ua.org.zasadnyy.partybudget;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PartyBudgetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party_budget_activity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.party_budget, menu);
        return true;
    }
    
}
