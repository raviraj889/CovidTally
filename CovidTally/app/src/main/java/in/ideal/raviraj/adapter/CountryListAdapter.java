package in.ideal.raviraj.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import in.ideal.raviraj.R;
import in.ideal.raviraj.models.CountryModel;
import in.ideal.raviraj.models.ResponseModel;
import in.ideal.raviraj.screens.MainActivity;
import in.ideal.raviraj.utils.CommonUtils;
import in.ideal.raviraj.utils.Constants;

public class CountryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context mContext;
    List<CountryModel> mDataList;

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public CountryListAdapter(Context mContext, List<CountryModel> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.list_item, parent, false);

            //view.setOnClickListener(MainActivity.myOnClickListener);

            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }else{
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtCountry, txtCases, txtDeaths, txtRecovered;
        ImageView imgUser;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtCountry = itemView.findViewById(R.id.txtCountry);
            txtCases = itemView.findViewById(R.id.txtCases);
            txtDeaths = itemView.findViewById(R.id.txtDeaths);
            txtRecovered = itemView.findViewById(R.id.txtRecovered);
            imgUser = itemView.findViewById(R.id.imgUser);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        if (holder instanceof MyViewHolder) {
            populateItems((MyViewHolder) holder, listPosition);
        }else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, listPosition);
        }
    }

    private void populateItems(MyViewHolder holder, final int listPosition) {

        CountryModel mData = mDataList.get(listPosition);

        if(mData.getCountry().equalsIgnoreCase(CommonUtils.getString(mContext, Constants.USERS_COUNTRY))){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey_opacity));
            holder.imgUser.setVisibility(View.VISIBLE);
        }else{
            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
            holder.imgUser.setVisibility(View.GONE);
        }

        holder.txtCountry.setText(mData.getCountry());
        holder.txtCases.setText("" + mData.getTotalConfirmed());
        holder.txtDeaths.setText("" + mData.getTotalDeaths());
        holder.txtRecovered.setText("" + mData.getTotalRecovered());

    }


    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

}
