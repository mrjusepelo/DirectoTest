package co.devpenguin.directotest.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.expansionpanel.ExpansionLayout;
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.devpenguin.directotest.ProspectusFormActivity;
import co.devpenguin.directotest.R;
import co.devpenguin.directotest.objects.Prospectus;
import co.devpenguin.directotest.utils.APIRequest;
import co.devpenguin.directotest.utils.RestClient;
import co.devpenguin.directotest.utils.ServerAPIAsyncTask;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by juan on 6/01/18.
 */

public class AdapterProspectus extends RecyclerView.Adapter {

    public ArrayList<Prospectus> dataSet;
    private ArrayList<Prospectus> dataSetCopy = new ArrayList<Prospectus>();
    public Context context;
    int total_types;
    public String session_token;
    private static final String TOKEN_NULL = "";
    SharedPreferences settings;
    public SharedPreferences.Editor editor;
    AdapterProspectus self;
    ServerAPIAsyncTask api_server;
    APIRequest request = new APIRequest(APIRequest.PROSPECTUS, RestClient.RequestMethod.GET);
    String tag = "AdapterProspectus";
    private final ExpansionLayoutCollection expansionsCollection = new ExpansionLayoutCollection();


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_identification, tv_status, tv_updatedAt, tv_telephone, tv_address,
                tv_createdAt, tv_zoneCode, tv_neighborhoodCode, tv_cityCode, tv_sectionCode,
                tv_roleId, tv_appointableId, tv_rejectedObservation, tv_observation, tv_disable,
                tv_visited, tv_callcenter, tv_acceptSearch, tv_campaignCode, tv_userId;
        Button btn_edit, btn_open;
        ExpansionLayout expansionLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            expansionLayout = itemView.findViewById(R.id.expansionLayout);

            this.btn_edit = (Button) itemView.findViewById(R.id.btn_edit);
            this.btn_open = (Button) itemView.findViewById(R.id.btn_open);
            this.tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            this.tv_identification= (TextView) itemView.findViewById(R.id.tv_identification);
            this.tv_status= (TextView) itemView.findViewById(R.id.tv_status);
            this.tv_updatedAt = (TextView) itemView.findViewById(R.id.tv_updatedAt);
            this.tv_telephone = (TextView) itemView.findViewById(R.id.tv_telephone);
            this.tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            this.tv_createdAt = (TextView) itemView.findViewById(R.id.tv_createdAt);
            this.tv_zoneCode = (TextView) itemView.findViewById(R.id.tv_zoneCode);
            this.tv_neighborhoodCode = (TextView) itemView.findViewById(R.id.tv_neighborhoodCode);
            this.tv_cityCode = (TextView) itemView.findViewById(R.id.tv_cityCode);
            this.tv_sectionCode = (TextView) itemView.findViewById(R.id.tv_sectionCode);
            this.tv_roleId = (TextView) itemView.findViewById(R.id.tv_roleId);
            this.tv_appointableId = (TextView) itemView.findViewById(R.id.tv_appointableId);
            this.tv_rejectedObservation = (TextView) itemView.findViewById(R.id.tv_rejectedObservation);
            this.tv_observation = (TextView) itemView.findViewById(R.id.tv_observation);

            this.tv_disable = (TextView) itemView.findViewById(R.id.tv_disable);
            this.tv_visited = (TextView) itemView.findViewById(R.id.tv_visited);
            this.tv_callcenter = (TextView) itemView.findViewById(R.id.tv_callcenter);
            this.tv_acceptSearch = (TextView) itemView.findViewById(R.id.tv_acceptSearch);
            this.tv_campaignCode = (TextView) itemView.findViewById(R.id.tv_campaignCode);
            this.tv_userId = (TextView) itemView.findViewById(R.id.tv_userId);


        }

        public void bind(Object object){
            expansionLayout.collapse(false);
        }
        public ExpansionLayout getExpansionLayout() {
            return expansionLayout;
        }
    }

    public AdapterProspectus(Context context){
        expansionsCollection.openOnlyOne(true);
        this.dataSet = new ArrayList<>();
        this.dataSetCopy.addAll(dataSet);
        this.context = context;
        self = this;
        total_types = 0;

        settings = ((Activity)context).getSharedPreferences("USER_DATA", 0);
        editor = settings.edit();

        session_token = settings.getString("TOKEN", TOKEN_NULL);
        Log.i(tag, "tokenFromAdapter===>"+session_token);

        api_server = new ServerAPIAsyncTask(request);
        api_server.adapterProspectus = AdapterProspectus.this;
        api_server.context = context;
        api_server.init_client();
//        api_server.client.AddParam("token", session_token);
        api_server.execute();

    }


    public AdapterProspectus(Context context, List<Prospectus> data) {
        self = this;
        this.dataSet = (ArrayList<Prospectus>) data;
        this.context = context;
        total_types = dataSet.size();
        this.dataSetCopy.addAll(dataSet);
        self.notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prospectu, parent, false);
        return new ViewHolder(view);

    }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {


        final Prospectus object = (Prospectus) dataSet.get(listPosition);
        if (object != null) {
        ((ViewHolder) holder).bind(object);
        expansionsCollection.add(((ViewHolder) holder).getExpansionLayout());
            ((ViewHolder) holder).tv_name.setText(object.getComplete_name());
            ((ViewHolder) holder).tv_identification.setText(object.getSchProspectIdentification());
            ((ViewHolder) holder).tv_status.setText(object.getStatusName());
            ((ViewHolder) holder).tv_telephone.setText(object.getTelephone());
            ((ViewHolder) holder).tv_address.setText(object.getAddress());
            ((ViewHolder) holder).tv_zoneCode.setText(object.getZoneCode());
            ((ViewHolder) holder).tv_neighborhoodCode.setText(object.getNeighborhoodCode());
            ((ViewHolder) holder).tv_cityCode.setText(object.getCityCode());
            ((ViewHolder) holder).tv_sectionCode.setText(object.getSectionCode());
            ((ViewHolder) holder).tv_roleId.append(String.valueOf(object.getRoleId()));
            ((ViewHolder) holder).tv_appointableId.setText(String.valueOf(object.getAppointableId()));
            ((ViewHolder) holder).tv_observation.append(object.getObservation());
//            note: if status is rejected
            if(object.getRejectedObservation() != null) {
                ((ViewHolder) holder).tv_rejectedObservation.append(object.getRejectedObservation());
            }else{
                ((ViewHolder) holder).tv_rejectedObservation.setText("");
            }

            ((ViewHolder) holder).tv_disable.append(object.disable ? "Si" : "No");
            ((ViewHolder) holder).tv_visited.append(object.visited ? "Si" : "No");
            ((ViewHolder) holder).tv_callcenter.append(object.callcenter ? "Si" : "No");
            ((ViewHolder) holder).tv_acceptSearch.append(object.acceptSearch ? "Si" : "No");
            ((ViewHolder) holder).tv_campaignCode.append(object.getCampaignCode());
            ((ViewHolder) holder).tv_userId.append(String.valueOf(object.getUserId()));

            ((ViewHolder) holder).tv_createdAt.setText(object.createdAt);

            if(object.updatedAt != null) {
                try {
                    SimpleDateFormat formatter_updated = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    Date updated_date = (Date) formatter_updated.parse(object.updatedAt);
                    ((ViewHolder) holder).tv_updatedAt.setText(updated_date.toLocaleString());
                } catch (ParseException e) {
                    ((ViewHolder) holder).tv_updatedAt.setText(object.updatedAt);
                    e.printStackTrace();
                }
            }


//

            ((ViewHolder)holder).btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(context, ProspectusFormActivity.class);
                intent.putExtra("prospectus", object);
                ((Activity)context).startActivity(intent);
                }
            });

            ((ViewHolder)holder).btn_open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ViewHolder) holder).getExpansionLayout().toggle(true);
                }
            });



        }

    }

    public void filter(String text) {
        dataSet.clear();
        if(text.isEmpty()){
            dataSet.addAll(dataSetCopy);
        } else{
            text = text.toLowerCase();
            for(Prospectus item: dataSetCopy){
                String name_unnacent = Normalizer.normalize(item.getComplete_name(), Normalizer.Form.NFD);
                name_unnacent = name_unnacent.replaceAll("[^\\p{ASCII}]", "");
                if(name_unnacent.toLowerCase().contains(text) || item.getSchProspectIdentification().contains(text)){
                    dataSet.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return dataSet.size();

    }


    public void addItem(Prospectus item) {
        dataSet.add(item);
        this.notifyDataSetChanged();
    }




    public void setData(JSONArray jsonArray){
        Log.i(tag, "resultLisProspectus==="+jsonArray);

        for (int i = 0; i < jsonArray.length(); i++) {
            try {

                Prospectus object = new Prospectus(jsonArray.getJSONObject(i));
                this.dataSet.add(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    //        this.dataSet = data;
        this.dataSetCopy.addAll(dataSet);
        total_types = jsonArray.length();
        self.notifyDataSetChanged();

        SugarRecord.saveInTx(dataSet);

    }

}
