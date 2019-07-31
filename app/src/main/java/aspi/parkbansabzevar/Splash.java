package aspi.parkbansabzevar;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.FormBody;


public class Splash extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, PermissionResultCallback {

    int time = 0,cunter=0;
    TextView ver;
    OtherMetod om = new OtherMetod();
    DownloadData dd = new DownloadData();
    boolean Net = false;

    PermissionUtils permissionUtils;
    ArrayList<String> permissions = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        permissionUtils = new PermissionUtils(Splash.this);
        permissions.add(Manifest.permission.INTERNET);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(android.Manifest.permission.ACCESS_NETWORK_STATE);
        permissionUtils.check_permission(permissions, "برای استفاده از برنامه بایستی تمامی مجوزها را تایید نمایید", 1);


        ver = (TextView) findViewById(R.id.splash_ver);

        ver.setText("نسخه "+om.GetVer(Splash.this));


    }

    void TIMER() {
        time = 0;

        final Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.i("TAG_Timer", "T = " + time);
                        if (Net){
                            if (time >= 3) {
                                myTimer.cancel();
                                startActivity(new Intent(Splash.this, Login.class));
                                finish();
                            }
                        }

                        if(time>=9999){
                            myTimer.cancel();
                            finish();
                        }
                        time++;

                    }
                });
            }

        }, 0, 1000);
    }

    class NetWork extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... p) {
            FormBody.Builder formBuilder = new FormBody.Builder();
            return dd.post_Data_(formBuilder, "connect.php");
        }

        @Override
        protected void onPostExecute(String result) {

            Log.i("TAG_Splash",""+result);
            try{
                Gson gson = new Gson();
                Type listType = new TypeToken<Contect_Ver>() {}.getType();
                Contect_Ver ver = gson.fromJson(result.toString(), listType);
                if (!ver.Ver.equals("")){
                    Net = true;
                    TIMER();
                }
            }catch (Exception e){
                new NetWork().execute();
                cunter+=1;
                if (cunter % 10000 == 0)
                    om.TOAST(Splash.this,"دستگاه خود را به اینترنت متصل کنید");

            }
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Boolean isGranted = true;
        for (int i = 0; i < permissions.length; i++) {
            Log.i("TAG_Premissions",""+permissions);
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                isGranted = false;
            }

            permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (!isGranted) {
                om.SetCode("permissions", "1", Splash.this);
                if (om.isNetworkConnected(Splash.this)) {
                    new NetWork().execute();
                }

            }
        }

    }

    @Override
    public void PermissionGranted(int request_code) {
        new NetWork().execute();
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {

    }

    @Override
    public void PermissionDenied(int request_code) {

    }

    @Override
    public void NeverAskAgain(int request_code) {

    }

}
