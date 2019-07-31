package aspi.parkbansabzevar;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;

/**
 * Created by AmIn on 7/18/2018.
 */

public class RecyclerUserActivity extends RecyclerView.Adapter<RecyclerUserActivity.item> {

    List<ContantUser> cl;
    Context context;
    OtherMetod om = new OtherMetod();
    DownloadData dd =new DownloadData();
    String NET ,phone ;


    public RecyclerUserActivity(List<ContantUser> CL, Context context) {
        this.cl = CL;
        this.context = context;
    }

    @Override
    public item onCreateViewHolder(ViewGroup parent, int viewType) {
        View rec= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_recycler,parent,false);
        return new item(rec);
    }

    @Override
    public void onBindViewHolder(item h, int position) {

        final ContantUser cu = cl.get(position);

        h.name.setText(cu.getName()+" "+cu.getFamily());
        h.phone.setText(cu.getPhone());
        h.address.setText(cu.getAddress());

        if (cu.getStatus().equals("a")){
            h.activ.setVisibility(View.GONE);
        }else if (cu.getStatus().equals("b")){
            h.deactiv.setVisibility(View.GONE);
        }

        h.activ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NET = "a";
                new NetWork().execute(cu.getPhone(),NET);
            }
        });

        h.deactiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NET = "b";
                new NetWork().execute(cu.getPhone(),NET);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cl.size();
    }

    public class item extends RecyclerView.ViewHolder {

        TextView name , phone , address , activ , deactiv;

        public item(View iv) {
            super(iv);

            name = (TextView) iv.findViewById(R.id.item_user_name);
            phone = (TextView) iv.findViewById(R.id.item_user_phone);
            address = (TextView) iv.findViewById(R.id.item_user_address);
            activ = (TextView) iv.findViewById(R.id.item_user_activ);
            deactiv = (TextView) iv.findViewById(R.id.item_user_deactiv);
        }

    }

    class NetWork extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... p) {
            FormBody.Builder formBuilder = new FormBody.Builder().add("phone", p[0]).add("status", p[1]);
            phone = p[0];
            return dd.post_Data_(formBuilder, "update_user.php");
        }

        @Override
        protected void onPostExecute(String result) {

            if(result.equals("1")){

                List<ContantUser> CL = new ArrayList<ContantUser>();

                for (int i = 0 ; i<cl.size();i++){
                    ContantUser cu =cl.get(i);
                    if (!cu.getPhone().equals(phone)){
                         CL.add(cu);
                    }
                }
                cl.clear();
                cl = CL;
                notifyDataSetChanged();
            }else{
                om.TOAST(context,"خطا در ثبت اطلاعات لطفا بعدا امتحان کنید...");
            }

        }

    }

}