package aspi.parkbansabzevar;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class DownloadData {

    OkHttpClient client = new OkHttpClient();
    String allurl="http://parkbanapp.ir/Park/";


    public String GetData_(String URL) {
        try {
            Request request = new Request.Builder().url(allurl+URL).build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            Log.i("TAG_OkHttpe_GetData", "" + e.toString());
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public String post_Data_(FormBody.Builder formBuilder,String URL) {
        try {
            RequestBody formBody = formBuilder.build();
            Request request = new Request.Builder()
                    .url(allurl+URL)
                    .post(formBody)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {
            Log.i("TAG_OkHttpe_p_D_H", "" + e.toString());
            e.printStackTrace();
            return null;
        }
    }



}

