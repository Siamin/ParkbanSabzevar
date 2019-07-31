package aspi.parkbansabzevar;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;

/**
 * Created by AmIn on 7/26/2018.
 */

public class Recycler_User_All extends RecyclerView.Adapter<Recycler_User_All.item> {

    List<Contant_user_all> cl;
    Context context;
    OtherMetod om = new OtherMetod();


    public Recycler_User_All(List<Contant_user_all> CL, Context context) {
        this.cl = CL;
        this.context = context;
    }

    @Override
    public item onCreateViewHolder(ViewGroup parent, int viewType) {
        View rec= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_all,parent,false);
        return new item(rec);
    }

    @Override
    public void onBindViewHolder(item h, int position) {

        final Contant_user_all cu = cl.get(position);

        h.name.setText("نام پارکبان : "+cu.getName()+" "+cu.getFamily());
        h.phone.setText("شماره تلفن : "+cu.getPhone());
        h.address.setText("آدرس : "+cu.getAddress());

        h.activ.setText("در حال انتظار");

        if (cu.getStatus().equals("a")){
            h.activ.setText("پارکبان فعال");
        }else if (cu.getStatus().equals("b")){
            h.activ.setText("پارکبان مسدود");
        }



    }

    @Override
    public int getItemCount() {
        return cl.size();
    }

    public class item extends RecyclerView.ViewHolder {

        TextView name , phone , address , activ ;

        public item(View iv) {
            super(iv);

            name = (TextView) iv.findViewById(R.id.item_user_all_name);
            phone = (TextView) iv.findViewById(R.id.item_user_all_phone);
            address = (TextView) iv.findViewById(R.id.item_user_all_adderse);
            activ = (TextView) iv.findViewById(R.id.item_user_all_status);

        }

    }

}
