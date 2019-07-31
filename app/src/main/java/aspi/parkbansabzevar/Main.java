package aspi.parkbansabzevar;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andremion.counterfab.CounterFab;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.FormBody;


public class Main extends AppCompatActivity {


    LinearLayout addcar , sharj , empty , newuser,useractive,userdactive,export,manegar,logout,chart;
    public static String[] plakitem={"الف","ب","پ","ت","ث","ج","د","ز","ژ","س","ش","ص","ط","ف",
            "ق","ک","گ","ل","م","ن","و","ه","ی"};
    public static String[] pay={"50,000 ريال","100,000 ريال","200,000 ريال"};
    ArrayAdapter<String> PLAK , PAY;
    FormBody.Builder formBuilder;
    OtherMetod om = new OtherMetod();
    DownloadData dd = new DownloadData();
    String NET = "";
    Contant_Profile contant_login = new Contant_Profile();
    TextView Fulname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        config();

        addcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCar();
            }
        });

        sharj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharjCar();
            }
        });

        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmptySharj();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOut();
            }
        });

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserActivityGo("p");
            }
        });

        useractive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserActivityGo("a");
            }
        });

        userdactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserActivityGo("b");
            }
        });

        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main.this,Export.class));
                finish();
            }
        });

        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main.this,Chart_Activity.class));
                finish();
            }
        });

    }

    void UserActivityGo(String type){
        Intent intent = new Intent(Main.this, UserActivity.class);
        intent.putExtra("type",type);
        startActivity(intent);
        finish();
    }

    void LogOut(){
        om.SetCode("user_login", "-1" , Main.this);
        om.SetCode("user_type", "-1" , Main.this);
        startActivity(new Intent(Main.this,Login.class));
        finish();
    }

    void config(){
        logout = (LinearLayout) findViewById(R.id.main_logout);

        addcar = (LinearLayout) findViewById(R.id.main_addcar);
        sharj = (LinearLayout) findViewById(R.id.main_sharj);
        empty = (LinearLayout) findViewById(R.id.main_empty);

        newuser = (LinearLayout) findViewById(R.id.main_new_user);
        useractive = (LinearLayout) findViewById(R.id.main_user_active);
        userdactive = (LinearLayout) findViewById(R.id.main_user_deactive);
        export = (LinearLayout) findViewById(R.id.main_export);
        manegar = (LinearLayout) findViewById(R.id.main_maneger);
        chart = (LinearLayout) findViewById(R.id.main_chart);

        Fulname = (TextView) findViewById(R.id.main_fulname);

        PLAK=new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,plakitem);
        PAY=new  ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,pay);
        //day_spinner.setAdapter(a);

        String user_type = om.get_Data("user_type","-1",Main.this)
                ,user_Phone = om.get_Data("user_login","-1",Main.this);


        if (!user_type.equals("admin")){
            manegar.setVisibility(View.GONE);
        }

        NET = "profile";
        formBuilder = new FormBody.Builder()
                .add("phone",user_Phone );
        new NetWork().execute("profile.php");

    }

    void AddCar() {
        final Dialog dialog_AddCar = new Dialog(Main.this, R.style.NewDialog);
        dialog_AddCar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_AddCar.setContentView(R.layout.insert_car);
        dialog_AddCar.setCancelable(true);
        dialog_AddCar.setCanceledOnTouchOutside(true);
        dialog_AddCar.show();
        //******************************************************************************************
        final EditText p1 = (EditText) dialog_AddCar.findViewById(R.id.insert_car_plak );
        final Spinner  p2 = (Spinner)  dialog_AddCar.findViewById(R.id.insert_car_plak2);
        final EditText p3 = (EditText) dialog_AddCar.findViewById(R.id.insert_car_plak3);
        final EditText p4 = (EditText) dialog_AddCar.findViewById(R.id.insert_car_plak4);
        //********************************************************************************
        final EditText phone = (EditText) dialog_AddCar.findViewById(R.id.insert_car_phone);
        final EditText name = (EditText) dialog_AddCar.findViewById(R.id.insert_car_name);
        final EditText family = (EditText) dialog_AddCar.findViewById(R.id.insert_car_family);
        final Button ok = (Button) dialog_AddCar.findViewById(R.id.insert_car_ok);
        final Spinner  pay_ = (Spinner)  dialog_AddCar.findViewById(R.id.insert_car_pay);
        //******************************************************************************************
        p2.setAdapter(PLAK);
        pay_.setAdapter(PAY);
        //******************************************************************************************
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!p1.getText().toString().equals("") && !p3.getText().toString().equals("")
                        && !p4.getText().toString().equals("") && !phone.getText().toString().equals("")
                        && !name.getText().toString().equals("") && !family.getText().toString().equals("")){

                    String PlakCar = p1.getText().toString()+plakitem[p2.getSelectedItemPosition()]
                            +p3.getText().toString()+p4.getText().toString();

                    String User = om.get_Data("user_login", "-1", Main.this);
                    NET = "ADD";
                    formBuilder = new FormBody.Builder()
                            .add("number_car", PlakCar)
                            .add("phoe", phone.getText().toString())
                            .add("name", name.getText().toString())
                            .add("family", family.getText().toString())
                            .add("pay", pay[pay_.getSelectedItemPosition()])
                            .add("insert", User);
                    new NetWork().execute("inser_car_park.php");
                }else{
                    Toast.makeText(getApplicationContext(),"اطلاعات را با دقت وارد نمایید",Toast.LENGTH_LONG).show();
                }

                dialog_AddCar.dismiss();
            }
        });


    }

    void SharjCar() {
        final Dialog dialog_AddCar = new Dialog(Main.this, R.style.NewDialog);
        dialog_AddCar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_AddCar.setContentView(R.layout.insert_car);
        dialog_AddCar.setCancelable(true);
        dialog_AddCar.setCanceledOnTouchOutside(true);
        dialog_AddCar.show();
        //******************************************************************************************
        final EditText p1 = (EditText) dialog_AddCar.findViewById(R.id.insert_car_plak );
        final Spinner  p2 = (Spinner)  dialog_AddCar.findViewById(R.id.insert_car_plak2);
        final EditText p3 = (EditText) dialog_AddCar.findViewById(R.id.insert_car_plak3);
        final EditText p4 = (EditText) dialog_AddCar.findViewById(R.id.insert_car_plak4);
        //********************************************************************************
        final EditText phone = (EditText) dialog_AddCar.findViewById(R.id.insert_car_phone);
        final EditText name = (EditText) dialog_AddCar.findViewById(R.id.insert_car_name);
        final EditText family = (EditText) dialog_AddCar.findViewById(R.id.insert_car_family);
        final Button ok = (Button) dialog_AddCar.findViewById(R.id.insert_car_ok);
        final Spinner  pay_ = (Spinner)  dialog_AddCar.findViewById(R.id.insert_car_pay);
        //******************************************************************************************
        p2.setAdapter(PLAK);
        pay_.setAdapter(PAY);
        phone.setVisibility(View.GONE);
        name.setVisibility(View.GONE);
        family.setVisibility(View.GONE);

        //******************************************************************************************
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!p1.getText().toString().equals("") && !p3.getText().toString().equals("") && !p4.getText().toString().equals("") ){

                    String PlakCar = p1.getText().toString()+plakitem[p2.getSelectedItemPosition()]
                            +p3.getText().toString()+p4.getText().toString();

                    String User = om.get_Data("user_login", "-1", Main.this);
                    NET = "sharj";
                    formBuilder = new FormBody.Builder()
                            .add("plak", PlakCar)
                            .add("pay", pay[pay_.getSelectedItemPosition()])
                            .add("user", User);
                    new NetWork().execute("SharjCar.php");
                }else{
                    Toast.makeText(getApplicationContext(),"اطلاعات را با دقت وارد نمایید",Toast.LENGTH_LONG).show();
                }

                dialog_AddCar.dismiss();
            }
        });

    }

    void EmptySharj(){
        final Dialog dialog_AddCar = new Dialog(Main.this, R.style.NewDialog);
        dialog_AddCar.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_AddCar.setContentView(R.layout.insert_car);
        dialog_AddCar.setCancelable(true);
        dialog_AddCar.setCanceledOnTouchOutside(true);
        dialog_AddCar.show();
        //******************************************************************************************
        final EditText p1 = (EditText) dialog_AddCar.findViewById(R.id.insert_car_plak );
        final Spinner  p2 = (Spinner)  dialog_AddCar.findViewById(R.id.insert_car_plak2);
        final EditText p3 = (EditText) dialog_AddCar.findViewById(R.id.insert_car_plak3);
        final EditText p4 = (EditText) dialog_AddCar.findViewById(R.id.insert_car_plak4);
        //********************************************************************************
        final EditText phone = (EditText) dialog_AddCar.findViewById(R.id.insert_car_phone);
        final EditText name = (EditText) dialog_AddCar.findViewById(R.id.insert_car_name);
        final EditText family = (EditText) dialog_AddCar.findViewById(R.id.insert_car_family);
        final Button ok = (Button) dialog_AddCar.findViewById(R.id.insert_car_ok);
        final Spinner  pay_ = (Spinner)  dialog_AddCar.findViewById(R.id.insert_car_pay);
        //******************************************************************************************
        p2.setAdapter(PLAK);
        pay_.setVisibility(View.GONE);
        phone.setVisibility(View.GONE);
        name.setVisibility(View.GONE);
        family.setVisibility(View.GONE);
        //******************************************************************************************
        p1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!p1.getText().toString().equals("") && !p3.getText().toString().equals("") && !p4.getText().toString().equals("")){

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        p4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!p1.getText().toString().equals("") && !p3.getText().toString().equals("") && !p4.getText().toString().equals("")){

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //******************************************************************************************
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!p1.getText().toString().equals("") && !p3.getText().toString().equals("") && !p4.getText().toString().equals("") ){

                    String PlakCar = p1.getText().toString()+plakitem[p2.getSelectedItemPosition()]
                            +p3.getText().toString()+p4.getText().toString();

                    NET = "Empty";
                    formBuilder = new FormBody.Builder()
                            .add("palk", PlakCar)
                            .add("type", "Empty");
                    new NetWork().execute("sharjempety.php");
                }else{
                    Toast.makeText(getApplicationContext(),"اطلاعات را با دقت وارد نمایید",Toast.LENGTH_LONG).show();
                }

                dialog_AddCar.dismiss();
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
            //om.TOAST(Main.this,NET);

            Gson gson = new Gson();

            if (NET.equals("ADD")){

                if (!result.equals("-1")){
                    om.TOAST(Main.this,"ماشین با موفقیت ثبت شد.");
                }else{
                    om.TOAST(Main.this,"این ماشین قبلا ثبت شده است.");
                }

            }else if (NET.equals("sharj")){

                if (!result.equals("-1")){
                    om.TOAST(Main.this,"شارژ خودرو با موفقیت انجام شد.");
                }else{
                    om.TOAST(Main.this,"این ماشین ثبت نشده است.");
                }

            }else if (NET.equals("Empty")){

                if (!result.equals("-1") && !result.equals("-2")){
                    om.TOAST(Main.this,"پیام با موفقیت برای صاحب خودرو ارسال شد.");
                }else{
                    om.TOAST(Main.this,"این ماشین ثبت نشده است.");
                }

            }else if (NET.equals("profile")){

                Type listType = new TypeToken<Contant_Profile>() {}.getType();
                contant_login = gson.fromJson(result.toString(), listType);

                //om.TOAST(Main.this,contant_login.getFamily());

                if (!contant_login.getFamily().equals("-1") && !contant_login.getName().equals("-1")){
                    Fulname.setText(contant_login.getName()+" "+contant_login.getFamily());
                }else{
                    startActivity(new Intent(Main.this,Login.class));
                    finish();
                }

            }
        }

    }

}
