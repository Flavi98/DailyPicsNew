package fstoianov.htlgrieskirchen.at.dailypics;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by fstoianov on 29.05.2015.
 */
public class PicsList extends Activity {

    String url = "http://www.dailypics.16mb.com/get_all_dailypics.php";
    ArrayList<String> data = new ArrayList<String>();
    private ListView list;
    String json;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We'll define a custom screen layout here (the one shown above), but
        // typically, you could just use the standard ListActivity layout.
        setContentView(R.layout.activity_list);

        NetworkThread nt = new NetworkThread(this);
        nt.execute();
        list = (ListView)findViewById(R.id.listView1);
        registerForContextMenu(list);

    }


    public void initList() {


        //String json = readGetFromServer(url);kkk

        try {
            JSONObject jobject = new JSONObject(json);
            JSONArray jarray = jobject.getJSONArray("dailypics");
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject item = jarray.getJSONObject(i);
                String id = item.getString("id");
                name = item.getString("picname");
                Log.i("bildername", name + "");
                data.add(name);
                Log.i("Data file", name);
            }

        } catch (JSONException e) {
            Log.i("fehler", e.getMessage().toString());
            e.printStackTrace();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        list.setAdapter(arrayAdapter);

    }

    public void setJson(String json2)
    {
        json = json2;
        initList();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_list, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch(id)
        {
            case R.id.menuShow:
            {
                Intent newscreen = new Intent(getApplication(), ImageShow.class);
                newscreen.putExtra("name", item.getTitle());
                startActivity(newscreen);


            }

            case R.id.menuSave:
            {

            }
        }
        return super.onContextItemSelected(item);
    }
}