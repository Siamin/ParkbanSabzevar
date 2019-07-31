package aspi.parkbansabzevar;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class OtherMetod {

    SharedPreferences sp;
    String packages = "aspi.parkbansabzevar";


    boolean Excel_car(int ROW, int FILD, String SheetName, String namefile, Context context, List<ContantAllCar> cua) {

        File Folder = new File(Environment.getExternalStorageDirectory(), "Parkban");

        if (!Folder.exists()) {
            Folder.mkdir();
        }

        boolean success = false;
        //******************************************************************************New Workbook
        Workbook wb = new HSSFWorkbook();
        Cell c = null;
        //*****************************************************************Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //*********************************************************************************New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet(SheetName);
        //******************************************************************Generate column headings
        try {


            for (int Row = 0; Row < ROW; Row++) {
                Row row = sheet1.createRow(Row);
                for (int fild = 0; fild < FILD; fild++) {
                    c = row.createCell(fild);
                    if (Row == 0) {
                        if (fild == 0) {
                            c.setCellValue("نام و نام خانوادگی صاحب خودرو");
                        } else if (fild == 1) {
                            c.setCellValue("شماره تماس");
                        } else if (fild == 2) {
                            c.setCellValue("پلاک");
                        } else if (fild == 3) {
                            c.setCellValue("پارکبان ثبت نام کننده");
                        } else if (fild == 4) {
                            c.setCellValue("تاریخ");
                        } else if (fild == 5) {
                            c.setCellValue("مبلغ شارژ");
                        }
                    } else {
                        if (fild == 0) {
                            c.setCellValue(cua.get(Row - 1).getName() + " " + cua.get(Row - 1).getFamily());
                        } else if (fild == 1) {
                            c.setCellValue(cua.get(Row - 1).getPhoe());
                        } else if (fild == 2) {
                            c.setCellValue(cua.get(Row - 1).getNumber_car());
                        } else if (fild == 3) {
                            c.setCellValue(cua.get(Row - 1).getInsert());
                        } else if (fild == 4) {
                            c.setCellValue(cua.get(Row - 1).getDate());
                        } else if (fild == 5) {
                            c.setCellValue(cua.get(Row - 1).getMoney());
                        }
                    }


                    sheet1.setColumnWidth(fild, (15 * 350));
                }
            }

            File file = new java.io.File(Folder, SheetName + " " + namefile + ".xls");
            FileOutputStream os = null;

            os = new FileOutputStream(file);
            wb.write(os);

            success = true;
        } catch (Exception e) {
            Log.i("TAG_Error", e.toString());
        }
        return success;
    }

    boolean Excel_sharj(int ROW, int FILD, String SheetName, String namefile, Context context, List<Contant_sharj> cua, int status) {

        File Folder = new File(Environment.getExternalStorageDirectory(), "Parkban");

        if (!Folder.exists()) {
            Folder.mkdir();
        }

        boolean success = false;
        //******************************************************************************New Workbook
        Workbook wb = new HSSFWorkbook();
        Cell c = null;
        //*****************************************************************Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //*********************************************************************************New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet(SheetName);
        //******************************************************************Generate column headings
        try {


            for (int Row = 0; Row < ROW; Row++) {
                Row row = sheet1.createRow(Row);
                for (int fild = 0; fild < FILD; fild++) {
                    c = row.createCell(fild);

                    if (Row == 0) {
                        if (fild == 0) {
                            c.setCellValue("نام و نام خانوادگی صاحب خودرو");
                        } else if (fild == 1) {
                            c.setCellValue("مبلغ شارژ");
                        } else if (fild == 2) {
                            c.setCellValue("شماره پلاک");
                        } else if (fild == 3) {
                            c.setCellValue("پارکبان ثبت کننده شارژ");
                        } else if (fild == 4) {
                            c.setCellValue("تاریخ");
                        }
                    } else {
                        if (fild == 0) {
                            c.setCellValue(cua.get(Row - 1).getName() + " " + cua.get(Row - 1).getFamily());
                        } else if (fild == 1) {
                            c.setCellValue(cua.get(Row - 1).getPay());
                        } else if (fild == 2) {
                            c.setCellValue(cua.get(Row - 1).getPlak());
                        } else if (fild == 3) {
                            c.setCellValue(cua.get(Row - 1).getUser());
                        } else if (fild == 4) {
                            c.setCellValue(cua.get(Row - 1).getData());
                        }
                    }

                    sheet1.setColumnWidth(fild, (15 * 350));
                }
            }

            File file = new java.io.File(Folder, SheetName + " " + namefile + ".xls");
            FileOutputStream os = null;

            os = new FileOutputStream(file);
            wb.write(os);

            success = true;
        } catch (Exception e) {
            Log.i("TAG_Error", e.toString());
        }
        return success;
    }

    boolean Excel_user(int ROW, int FILD, String SheetName, String namefile, Context context, List<Contant_user_all> cua) {

        File Folder = new File(Environment.getExternalStorageDirectory(), "Parkban");

        if (!Folder.exists()) {
            Folder.mkdir();
        }

        boolean success = false;
        //******************************************************************************New Workbook
        Workbook wb = new HSSFWorkbook();
        Cell c = null;
        //*****************************************************************Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //*********************************************************************************New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet(SheetName);
        //******************************************************************Generate column headings
        try {


            for (int Row = 0; Row < ROW; Row++) {
                Row row = sheet1.createRow(Row);
                for (int fild = 0; fild < FILD; fild++) {
                    c = row.createCell(fild);

                    if (Row == 0) {
                        if (fild == 0) {
                            c.setCellValue("نام و نام خانوادگی پارکبان");
                        } else if (fild == 1) {
                            c.setCellValue("آدرس");
                        } else if (fild == 2) {
                            c.setCellValue("شماره تماس");
                        } else if (fild == 3) {
                            c.setCellValue("وضعیت");
                        }
                    } else {
                        if (fild == 0) {
                            c.setCellValue(cua.get(Row - 1).getName() + " " + cua.get(Row - 1).getFamily());
                        } else if (fild == 1) {
                            c.setCellValue(cua.get(Row - 1).getAddress());
                        } else if (fild == 2) {
                            c.setCellValue(cua.get(Row - 1).getPhone());
                        } else if (fild == 3) {
                            if (cua.get(Row - 1).getStatus().equals("a")) {
                                c.setCellValue("فعال");
                            } else if (cua.get(Row - 1).getStatus().equals("b")) {
                                c.setCellValue("مسدود");
                            } else {
                                c.setCellValue("در حال انتظار");
                            }

                        }
                    }


                    sheet1.setColumnWidth(fild, (15 * 350));
                }
            }

            File file = new java.io.File(Folder, SheetName + " " + namefile + ".xls");
            FileOutputStream os = null;

            os = new FileOutputStream(file);
            wb.write(os);

            success = true;
        } catch (Exception e) {
            // TOAST(context,e.toString());
        }
        return success;
    }

    public String Pin_Cod(int cunt_pin) {
        Random random = new Random();
        String Resualt = "";
        for (int i = 0; i < cunt_pin; i++) {
            Resualt += (random.nextInt(9) + 0);
        }
        return Resualt;
    }

    public void SetCode(String name, String code, Context context) {
        sp = context.getSharedPreferences(packages, 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(name, code);
        edit.commit();
    }

    public String get_Data(String name, String Null, Context context) {
        sp = context.getSharedPreferences(packages, 0);
        return sp.getString(name, Null);
    }

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE); // 1
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo(); // 2
        return networkInfo != null && networkInfo.isConnected(); // 3
    }

    public String date_miladi() {
        Calendar c = Calendar.getInstance();
        String M, D;
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        M = String.valueOf(month);
        D = String.valueOf(day);

        if (month < 10) {
            M = "0" + String.valueOf(month);
        }

        if (day < 10) {
            D = "0" + String.valueOf(day);
        }

        if (month < 7) {
            if (day > 31) {
                D = "01";
            }
        } else {
            if (day > 30) {
                D = "01";
            }
        }

        return String.valueOf(year) + M + D;
    }

    public String date_shamsi() {
        Calendar c = Calendar.getInstance();
        int year = 0, month = 0, day = 0;
        int y = c.get(Calendar.YEAR);
        int x = c.get(Calendar.DAY_OF_YEAR);
        //*******************************
        if (x >= 0 && x <= 20) {
            year = y - 622;
        } else if (x >= 21 && x <= 50) {
            year = y - 622;
        } else if (x >= 51 && x <= 79) {
            year = y - 622;
        } else if (x >= 80 && x <= 266) {
            year = y - 621;
        } else if (x >= 267 && x <= 365) {
            year = y - 621;
        }
        int mod = year % 33, kabise = 0;
        if (mod == 1 || mod == 5 || mod == 9 || mod == 13 || mod == 17 || mod == 22 || mod == 26 || mod == 30) {
            kabise = 1;

        } else {
            kabise = 0;
        }
        //*******************************
        if (x >= 0 && x <= 20) {
            month = 10;
            day = x + 10;
            Log.i("TAG_DM", "1");
        } else if (x >= 21 && x <= 50) {
            month = 11;
            day = x - 20;
            Log.i("TAG_DM", "2");
        } else if (x >= 51 && x <= 79 && kabise == 0) {
            month = 12;
            day = x - 50;
            Log.i("TAG_DM", "3");
        } else if (x >= 51 && x <= 80 && kabise == 1) {
            month = 12;
            day = x - 49;
            Log.i("TAG_DM", "4");
        } else if (x >= 80 && x <= 266 && kabise == 0) {
            x = x - 80;
            month = (x / 31) + 1;
            day = (x % 31) + 1;
            Log.i("TAG_DM", "5");
        } else if (x >= 81 && x <= 266 && kabise == 1) {
            x = x - 79;
            month = (x / 31) + 1;
            day = (x % 31);
            Log.i("TAG_DM", "6");
        } else if (x >= 267 && x <= 365) {
            x = x - 266;
            month = (x / 30) + 7;
            day = (x % 30) + 1;
            Log.i("TAG_DM", "7");
        }
        String data = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
        return data;
    }

    public Typeface SetFont(Context context, String name) {
        return Typeface.createFromAsset(context.getAssets(), "Font/" + name + ".ttf");
    }

    public Animation GetAnim(Context context, int anim) {
        return AnimationUtils.loadAnimation(context, anim);
    }

    public String GetVer(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(packages, 0);
            return pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String miladi_to_shamsi(String DATE) {
        String[] D = DATE.split("/");
        int year = Integer.parseInt(D[0]), month = Integer.parseInt(D[1]), day = Integer.parseInt(D[2]);

        int y = year;
        int Month = month;
        int Day = day;

        int x = 0;

        for (int i = 1; i <= Month; i++) {
            if (i == 2) {
                x += 28;
            } else if (i == 9 && i == 11) {
                x += 30;
            } else if (i == 8 && i == 10 && i == 12) {
                x += 31;
            } else if (i % 2 == 1) {
                x += 31;
            } else if (i > 6 && i < 12) {
                x += 30;
            }
        }

        Log.i("TAG_", "X = " + x);
        x += Day;
        Log.i("TAG_", "X = " + x);
        //*******************************
        if (x >= 0 && x <= 20) {
            year = y - 622;
        } else if (x >= 21 && x <= 50) {
            year = y - 622;
        } else if (x >= 51 && x <= 79) {
            year = y - 622;
        } else if (x >= 80 && x <= 266) {
            year = y - 621;
        } else if (x >= 267 && x <= 365) {
            year = y - 621;
        }
        int mod = year % 33, kabise = 0;
        if (mod == 1 || mod == 5 || mod == 9 || mod == 13 || mod == 17 || mod == 22 || mod == 26 || mod == 30) {
            kabise = 1;

        } else {
            kabise = 0;
        }
        //*******************************
        if (x >= 0 && x <= 20) {
            month = 10;
            day = x + 10;
            Log.i("TAG_DM", "1");
        } else if (x >= 21 && x <= 50) {
            month = 11;
            day = x - 20;
            Log.i("TAG_DM", "2");
        } else if (x >= 51 && x <= 79 && kabise == 0) {
            month = 12;
            day = x - 50;
            Log.i("TAG_DM", "3");
        } else if (x >= 51 && x <= 80 && kabise == 1) {
            month = 12;
            day = x - 49;
            Log.i("TAG_DM", "4");
        } else if (x >= 80 && x <= 266 && kabise == 0) {
            x = x - 80;
            month = (x / 31) + 1;
            day = (x % 31) + 1;
            Log.i("TAG_DM", "5");
        } else if (x >= 81 && x <= 266 && kabise == 1) {
            x = x - 79;
            month = (x / 31) + 1;
            day = (x % 31);
            Log.i("TAG_DM", "6");
        } else if (x >= 267 && x <= 365) {
            x = x - 266;
            month = (x / 30) + 7;
            day = (x % 30) + 1;
            Log.i("TAG_DM", "7");
        }
        String data = String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day);
        return data;
    }


    public void TOAST(Context context, String TEXT) {
        Toast.makeText(context, "" + TEXT, Toast.LENGTH_LONG).show();
    }


}
