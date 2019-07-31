package aspi.parkbansabzevar;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class Chart_Activity extends Activity {


    DownloadData dd = new DownloadData();
    Contact_All_Chart contact_all_chart = new Contact_All_Chart();
    OtherMetod om = new OtherMetod();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);


        new NetWork().execute("chart.php");


    }


    class NetWork extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... p) {

            return dd.GetData_(p[0]);

        }

        @Override
        protected void onPostExecute(String result) {

            try{
                Gson gson = new Gson();

                Type listType = new TypeToken<Contact_All_Chart>() {
                }.getType();

                contact_all_chart = gson.fromJson(result.toString(), listType);
                om.TOAST(getApplication(), String.valueOf(contact_all_chart.CuntCarCreate));
            }catch (Exception e){
                Log.d("TAG_Error",e.toString());
            }


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Chart_Activity.this,Main.class));
        finish();
    }

}
