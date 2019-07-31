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

import com.goodiebag.pinview.Pinview;

import okhttp3.FormBody;


public class SendCode extends AppCompatActivity {

    EditText phone;
    Pinview code;
    Button ok;
    OtherMetod om = new OtherMetod();
    DownloadData dd = new DownloadData();
    String CODE = "", NET = "";
    FormBody.Builder formBuilder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendcode);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        config();

        code.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                if (code.getValue().equals(CODE)) {
                    Intent intent = new Intent(SendCode.this, Rigster.class);
                    intent.putExtra("phone",phone.getText().toString());
                    startActivity(intent);
                    finish();
                }

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!phone.getText().toString().equals("") && phone.length() == 11) {
                    CODE = om.Pin_Cod(4);
                    NET = "code";
                    formBuilder = new FormBody.Builder()
                            .add("phone", phone.getText().toString())
                            .add("code", CODE)
                            .add("type", "code");
                    new NetWork().execute("SendCodeSms.php");
                } else {
                    om.TOAST(SendCode.this, "لطفا شماره تلفن را با دقت وارد نمایید.");
                }

            }
        });
    }

    class NetWork extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... p) {
            return dd.post_Data_(formBuilder, p[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            // om.TOAST(ForgetPassword.this, result);

            if (NET.equals("code")) {

                if (result.equals("-1")) {
                    om.TOAST(SendCode.this, "این شماره قبلا ثبت شده است.");
                } else if (result.equals("-2")) {
                    om.TOAST(SendCode.this, "شما نمی توانید از این بخش تا 24 ساعت دیگر استفاده کنید.");
                } else {
                    phone.setVisibility(View.GONE);
                    ok.setVisibility(View.GONE);
                    code.setVisibility(View.VISIBLE);
                }

            }


        }

    }

    void config() {
        code = (Pinview) findViewById(R.id.sendcode_pin);
        phone = (EditText) findViewById(R.id.sendcode_phone);
        ok = (Button) findViewById(R.id.sendcode_ok);

        code.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SendCode.this, Login.class));
        finish();
    }

}
