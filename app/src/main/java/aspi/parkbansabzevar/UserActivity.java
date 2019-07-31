package aspi.parkbansabzevar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;


public class UserActivity extends AppCompatActivity {

    boolean BackPage = false;
    OtherMetod om = new OtherMetod();
    DownloadData dd = new DownloadData();
    List<ContantUser> user = new ArrayList<ContantUser>();
    String status;
    RecyclerView recycler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        recycler = (RecyclerView) findViewById(R.id.user_activity_recycler);

        Bundle bundle = getIntent().getExtras();
        status = bundle.getString("type");
        new NetWork().execute(status, "get_data_user.php");
    }

    void BACK() {
        startActivity(new Intent(UserActivity.this, Main.class));
        finish();
    }

    @Override
    public void onBackPressed() {

        if (BackPage) {
            super.onBackPressed();
            BACK();
        } else {
            BackPage = true;
            om.TOAST(UserActivity.this, "برای برگشت دوباره فشار دهید.");
        }


    }

    class NetWork extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... p) {
            FormBody.Builder formBuilder = new FormBody.Builder().add("type", p[0]);
            return dd.post_Data_(formBuilder, p[1]);
        }

        @Override
        protected void onPostExecute(String result) {

            Gson gson = new Gson();
            Type listType = new TypeToken<List<ContantUser>>() {
            }.getType();
            user = gson.fromJson(result.toString(), listType);

            if (user.size() > 0) {

                for (int i = 0 ; i < user.size() ; i++) {
                    user.get(i).setStatus(status);
                }

                LinearLayoutManager LLM = new LinearLayoutManager(UserActivity.this);
                recycler.setLayoutManager(LLM);
                recycler.setHasFixedSize(true);
                recycler.setAdapter(new RecyclerUserActivity(user, UserActivity.this));
            } else {
                BACK();
            }


        }

    }


}

