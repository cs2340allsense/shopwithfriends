package allsense.shopwithfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class SalesActivity extends ActionBarActivity {

    private ArrayAdapter<Item> adapter;
    private List<Item> adapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        // Get all the relevant sales the current user should see
        Log.d("SWF", "All sales: " + Item.allSales());
        adapterList = Item.allRelevantSales(User.currentUser());
        Log.d("SWF", "Relevant sales: " + adapterList);

        adapter = new ArrayAdapter<>(this, R.layout.list_view_cell, adapterList);

        ListView listView = (ListView) findViewById(R.id.sales_list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("SWF", "selected item " + adapterList.get(position));
                displaySale(adapterList.get(position));
            }
        });
    }

    void displaySale(final Item item) {
        Intent intent = new Intent(this, DisplaySaleActivity.class);
        intent.putExtra("id", item.id());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SWF", "refresh sales list");
        refreshList();
    }

    void refreshList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sales, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sales_menu_report_sale:
                startActivity(new Intent(this, ReportSaleActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
