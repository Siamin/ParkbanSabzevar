package aspi.parkbansabzevar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import okhttp3.FormBody;


public class Rigster extends AppCompatActivity {

    EditText name,family,addres,pass1,pass2;
    Button ok;

    OtherMetod om = new OtherMetod();
    DownloadData dd = new DownloadData();
    FormBody.Builder formBuilder;

    String phone= "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        config();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });

    }

    void Register(){

        if (!name.getText().toString().equals("")  && !family.getText().toString().equals("")
                && !addres.getText().toString().equals("") && !pass1.getText().toString().equals("")
                && !pass2.getText().toString().equals("")){

            if (pass1.getText().toString().equals(pass2.getText().toString())){
                formBuilder = new FormBody.Builder()
                        .add("phone", phone)
                        .add("name", name.getText().toString())
                        .add("family", family.getText().toString())
                        .add("address", addres.getText().toString())
                        .add("password", pass1.getText().toString());
                new NetWork().execute("insert_user_park.php");
            }else{
                om.TOAST(Rigster.this,"لطفا گذرواژه را بادقت وارد کنید.");
            }

        }else{
            om.TOAST(Rigster.this,"لطفا اطلاعات را با دقت وارد کنید .");
        }

    }

    class NetWork extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... p) {
            return dd.post_Data_(formBuilder, p[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("1")){
                om.TOAST(Rigster.this,"ثبت نام شما با موفقیت انجام شد برای ورود باید توسط مدیریت تایید شوید.");
            }else {
                om.TOAST(Rigster.this,"خطا در ثبت اطلاعات");
            }

            startActivity(new Intent(Rigster.this, Login.class));
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Rigster.this, Login.class));
        finish();
    }

    void config(){
        name = (EditText) findViewById(R.id.register_name);
        family = (EditText) findViewById(R.id.register_family);
        addres = (EditText) findViewById(R.id.register_address);
        pass1 = (EditText) findViewById(R.id.register_p1);
        pass2 = (EditText) findViewById(R.id.register_p2);

        ok = (Button) findViewById(R.id.register_ok);

        Bundle bundle = getIntent().getExtras();

        phone = bundle.getString("phone");
    }
}
