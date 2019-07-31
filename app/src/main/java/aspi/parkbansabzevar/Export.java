package aspi.parkbansabzevar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;


public class Export extends AppCompatActivity {

    boolean BackPage = false;
    OtherMetod om = new OtherMetod();
    DownloadData dd = new DownloadData();
    LinearLayout lcar, lsharj;
    RadioButton user, car, rigaster_car, sharj_car, day, month, all;
    Button save, show;
    FormBody.Builder formBuilder;
    List<Contant_user_all> cua = new ArrayList<Contant_user_all>();
    List<ContantAllCar> cac = new ArrayList<ContantAllCar>();
    List<Contant_sharj> cs = new ArrayList<Contant_sharj>();
    String NET = "";
    RecyclerView recycler;
    PersianCalendar pc = new PersianCalendar();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        config();

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnLayout_car(true, false);
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnLayout_car(false, true);
            }
        });

        rigaster_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnLayoutSharj(true, false);
            }
        });

        sharj_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnLayoutSharj(false, true);
            }
        });

        day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDays(true, false, false);
            }
        });

        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDays(false, true, false);
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDays(false, false, true);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Save_Excll();

            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetNet();
            }
        });

    }

    void GetNet() {

        formBuilder = new FormBody.Builder();

        if (user.isChecked()) {

            NET = "user";
            new NetWork().execute("Get_All_User.php");

        } else if (car.isChecked()) {

            if (rigaster_car.isChecked()) {
                NET = "car";
                new NetWork().execute("Get_car_rigister.php");
            } else if (sharj_car.isChecked()) {

                NET = "sharj";
                String DATE = om.date_miladi();
                //om.TOAST(Export.this, DATE);

                if (day.isChecked()) {
                    formBuilder.add("date", DATE).add("TYPE", "day");
                } else if (month.isChecked()) {
                    DATE = DATE.substring(0, DATE.length() - 2);

                    formBuilder.add("date", DATE).add("TYPE", "month");
                } else if (all.isChecked()) {
                    formBuilder.add("date", "").add("TYPE", "");
                }
                new NetWork().execute("Get_car_sharj.php");
            }

        }

    }

    void Save_Excll() {
        if (user.isChecked()) {
            if (om.Excel_user(cua.size() + 1, 4, "user", "User" + om.date_miladi(), Export.this, cua)) {
                om.TOAST(Export.this, "اطلاعات با موفقیت ثبت شد.");
            } else {
                om.TOAST(Export.this, "خطا در ثبت اطلاعات...");
            }

        } else if (car.isChecked()) {

            if (rigaster_car.isChecked()) {
                if (om.Excel_car(cac.size() + 1, 6, "car", "Car" + om.date_miladi(), Export.this, cac)) {
                    om.TOAST(Export.this, "اطلاعات با موفقیت ثبت شد.");
                } else {
                    om.TOAST(Export.this, "خطا در ثبت اطلاعات...");
                }
            } else if (sharj_car.isChecked()) {
                int ststus = 0;
                if (om.Excel_sharj(cs.size() + 1, 5, "sharj", "Sharj" + om.date_miladi(), Export.this, cs, ststus)) {
                    om.TOAST(Export.this, "اطلاعات با موفقیت ثبت شد.");
                } else {
                    om.TOAST(Export.this, "خطا در ثبت اطلاعات...");
                }
            }

        }

    }

    void ShowDays(boolean Day, boolean Month, boolean All) {

        if (Day) {
            day.setChecked(true);
            month.setChecked(false);
            all.setChecked(false);
        } else if (Month) {
            day.setChecked(false);
            month.setChecked(true);
            all.setChecked(false);
        } else if (All) {
            day.setChecked(false);
            month.setChecked(false);
            all.setChecked(true);
        }
    }

    void OnLayoutSharj(boolean rcar, boolean scar) {

        if (rcar) {
            rigaster_car.setChecked(true);
            sharj_car.setChecked(false);
            lsharj.setVisibility(View.GONE);
        } else if (scar) {
            rigaster_car.setChecked(false);
            sharj_car.setChecked(true);
            lsharj.setVisibility(View.VISIBLE);
        }

    }

    void OnLayout_car(boolean Car, boolean User) {

        if (User) {

            user.setChecked(true);
            car.setChecked(false);
            lcar.setVisibility(View.GONE);
            lsharj.setVisibility(View.GONE);

        } else if (Car) {

            car.setChecked(true);
            user.setChecked(false);
            lcar.setVisibility(View.VISIBLE);

        }
    }

    void config() {

        lcar = (LinearLayout) findViewById(R.id.export_layout_car);
        lsharj = (LinearLayout) findViewById(R.id.export_layout_sharj_car);

        user = (RadioButton) findViewById(R.id.export_user);
        car = (RadioButton) findViewById(R.id.export_car);
        rigaster_car = (RadioButton) findViewById(R.id.export_register_car);
        sharj_car = (RadioButton) findViewById(R.id.export_sharj_car);
        day = (RadioButton) findViewById(R.id.export_day);
        month = (RadioButton) findViewById(R.id.export_month);
        all = (RadioButton) findViewById(R.id.export_all);

        show = (Button) findViewById(R.id.export_show);
        save = (Button) findViewById(R.id.export_save);

        recycler = (RecyclerView) findViewById(R.id.export_recycler);

        lcar.setVisibility(View.GONE);
        lsharj.setVisibility(View.GONE);

    }

    @Override
    public void onBackPressed() {
        if (BackPage) {
            super.onBackPressed();
            startActivity(new Intent(Export.this, Main.class));
            finish();
        } else {
            BackPage = true;
            om.TOAST(Export.this, "برای برگشت دوباره فشار دهید.");
        }
    }

    class NetWork extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... p) {

            return dd.post_Data_(formBuilder, p[0]);

        }

        @Override
        protected void onPostExecute(String result) {

            Log.i("TAG_","result : "+result);

            Gson gson = new Gson();
            LinearLayoutManager LLM = new LinearLayoutManager(Export.this);
            recycler.setLayoutManager(LLM);
            recycler.setHasFixedSize(true);

            if (NET.equals("user")) {

                Type listType = new TypeToken<List<Contant_user_all>>() {
                }.getType();
                cua = gson.fromJson(result.toString(), listType);
                recycler.setAdapter(new Recycler_User_All(cua, Export.this));

            } else if (NET.equals("car")) {

                Type listType = new TypeToken<List<ContantAllCar>>() {
                }.getType();
                cac = gson.fromJson(result.toString(), listType);

                for (int i = 0; i < cac.size(); i++) {
                    String DATE = cac.get(i).getDate();
                    String date = "";
                    Log.i("TAG_","DATE length : "+DATE.length());
                    for (int j = 0, z = 0; j < DATE.length(); j++) {
                        if (j > 2 && j<7 && j % 2 == 1) {
                            date += DATE.substring(z, j+1)+"/";
                            z = j + 1;

                        }else if (j == 7 && j % 2 == 1) {
                            date += DATE.substring(z, j+1);

                        }
                    }
                    String[] D = date.split("/");
                    int year = Integer.parseInt(D[0]), month = Integer.parseInt(D[1]), day = Integer.parseInt(D[2]);
                    date = pc.MiladiToJalali(year,month,day);
                    cac.get(i).setDate(date);
                }

                recycler.setAdapter(new Recycler_car_all(cac, Export.this));
            } else if (NET.equals("sharj")) {

                Type listType = new TypeToken<List<Contant_sharj>>() {
                }.getType();
                cs = gson.fromJson(result.toString(), listType);

                for (int i = 0; i < cs.size(); i++) {
                    String DATE = cs.get(i).getData();
                    String date = "";
                    Log.i("TAG_","DATE length : "+DATE.length());
                    for (int j = 0, z = 0; j < DATE.length(); j++) {
                        if (j > 2 && j<7 && j % 2 == 1) {
                            date += DATE.substring(z, j+1)+"/";
                            z = j + 1;

                        }else if (j == 7 && j % 2 == 1) {
                            date += DATE.substring(z, j+1);

                        }
                    }
                    String[] D = date.split("/");
                    int year = Integer.parseInt(D[0]), month = Integer.parseInt(D[1]), day = Integer.parseInt(D[2]);
                    date = pc.MiladiToJalali(year,month,day);
                    cs.get(i).setData(date);
                }

                recycler.setAdapter(new Recycler_sharj(cs, Export.this));
            }


        }

    }

}
