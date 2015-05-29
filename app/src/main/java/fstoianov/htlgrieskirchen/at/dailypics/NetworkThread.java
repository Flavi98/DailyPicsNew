package fstoianov.htlgrieskirchen.at.dailypics;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by fstoianov on 29.05.2015.
 */
public class NetworkThread extends AsyncTask<String, Void, String> {
    PicsList picsList;

    public NetworkThread(PicsList picsList)
    {
        this.picsList = picsList;
    }

    String url = "http://www.dailypics.16mb.com/get_all_dailypics.php";
    @Override
    protected String doInBackground(String... params) {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response = null;
        try {
            response = client.execute(request);
            InputStream in = response.getEntity().getContent();
            String sJson = inputStreamToString(in);
            return sJson;
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        picsList.setJson(s);



    }

    private String inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        try {
            while ((line = rd.readLine()) != null){
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total.toString();
    }

}
