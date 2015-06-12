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
    Bitmap bitmap;

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
        readGetFromServer(url);
    }

    private String readGetFromServer(String url) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response = null;
        try {
            response = client.execute(request);
            InputStream in = response.getEntity().getContent();
            String sJson = inputStreamToString(in);
            initList();
            return sJson;
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }


    }

    private String inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try {
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total.toString();
    }

    public void initList() {


        //String json = readGetFromServer(url);kkk

        try {
            JSONObject jobject = new JSONObject(json);
            JSONArray jarray = jobject.getJSONArray("dailypics");
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject item = jarray.getJSONObject(i);
                String id = item.getString("id");
                String date = item.getString("date");
                String name = item.getString("name");
                String file = item.getString("file");
                Log.i("bildername", name + "");
                BigInteger bigIntege = new BigInteger(file, 2);
                byte[] binaryData = bigIntege.toByteArray();
                byte[] decoded = Base64.decode(binaryData, Base64.DEFAULT);
                bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
                data.add(name);
                Log.i("Data file", name);
            }

        } catch (JSONException e) {
            Log.i("fehler", e.getMessage().toString());
            e.printStackTrace();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        ListView lv = (ListView) findViewById(android.R.id.list);
        lv.setAdapter(arrayAdapter);

    }

    public void setJson(String json2)
    {
        json = json2;
    }

   public void onCreateContextMenu(Context menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        //super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_list, (Menu) menu);
    }

    public boolean onContextItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch(id)
        {
            case R.id.menuShow:
            {
                Intent newscreen = new Intent(getApplication(), ImageShow.class);

            }

            case R.id.menuSave:
            {

            }
        }
        return super.onContextItemSelected(item);
    }
}