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
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import okhttp3.FormBody;


public class Login extends AppCompatActivity {

    Button login;
    TextView register,forget;
    EditText number,password;
    DownloadData dd = new DownloadData();
    OtherMetod om = new OtherMetod();

    Contant_Login contant_login = new Contant_Login();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        config();
        ChekVer();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoPage(new Intent(Login.this,ForgetPassword.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoPage(new Intent(Login.this,SendCode.class));
            }
        });

    }

    void ChekLogin(){

         String User = om.get_Data("user_login", "-1", Login.this);

        if (!User.equals("-1")){
            GoPage(new Intent(Login.this, Main.class));
        }

    }

    void ChekVer(){
        ChekLogin();
    }

    void GoPage(Intent intent){

        startActivity(intent);
        finish();

    }

    void config(){

        login = (Button) findViewById(R.id.login_login);
        forget = (TextView) findViewById(R.id.login_forget);
        register = (TextView) findViewById(R.id.login_register);

        number = (EditText) findViewById(R.id.login_number);
        password = (EditText) findViewById(R.id.login_password);
    }

    void Login(){

        if (!number.getText().toString().equals("") && number.length()==11){

            if (!password.getText().toString().equals("")){

                new NetWork().execute(number.getText().toString(),password.getText().toString());

            }else{
                om.TOAST(Login.this,"رمز عبور را وارد کنید.");
            }

        }else{
            om.TOAST(Login.this,"شماره تلفن را به درستی وارد نمایید.");
        }

    }

    class NetWork extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("phone", params[0])
                    .add("pass", params[1]);
            return dd.post_Data_(formBuilder, "Login.php");
        }

        @Override
        protected void onPostExecute(String result) {

            Gson gson = new Gson();
            Type listType = new TypeToken<Contant_Login>() {}.getType();
            contant_login = gson.fromJson(result.toString(), listType);

            if (!contant_login.getUser().equals("-1")) {
                om.SetCode("user_login", contant_login.getUser(), Login.this);
                om.SetCode("user_type", contant_login.getType(), Login.this);
                GoPage(new Intent(Login.this, Main.class));
            } else {
                om.TOAST(Login.this, "شماره تلفن یا رمز عبور اشتباه است.");
            }


        }


    }
}
