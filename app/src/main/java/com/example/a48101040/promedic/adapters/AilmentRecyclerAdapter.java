package com.example.a48101040.promedic.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.a48101040.promedic.R;
import com.example.a48101040.promedic.data.Ailment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 48101040 on 2/2/2018.
 */

public class AilmentRecyclerAdapter extends RecyclerView.Adapter<AilmentRecyclerAdapter.CardForAilment>
        implements Filterable{

    private List<Ailment> mAilmentsList = new ArrayList<>();
    private List<Ailment> mFilteredAilmentsList = new ArrayList<>();
    private Context mContext;
    private IListenToClicks mIClickHandler;

    public AilmentRecyclerAdapter(Context context, List<Ailment> ailmentList, IListenToClicks clickHandler){
        this.mContext = context;
        this.mIClickHandler = clickHandler;
        this.mAilmentsList = ailmentList;
        this.mFilteredAilmentsList = ailmentList;
    }

    @Override
    public AilmentRecyclerAdapter.CardForAilment onCreateViewHolder(ViewGroup parent, int viewType) {
        View aCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.ailment_names_card, parent, false);
        return new CardForAilment(aCard);
    }

    @Override
    public void onBindViewHolder(AilmentRecyclerAdapter.CardForAilment holder, int position) {
        final Ailment anAilment = mFilteredAilmentsList.get(position);
        holder.mAilmentNameTextView.setText(anAilment.Name);
        holder.mAilmentSmallNameTextView.setText(anAilment.Name);
        Glide.with(mContext)
                .load(R.drawable.ic_xray)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mThumbnail);
    }

    @Override
    public int getItemCount() {
        return mFilteredAilmentsList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String userTypedString = charSequence.toString();
                if(userTypedString.isEmpty()){
                    mFilteredAilmentsList = mAilmentsList;
                } else {
                    List<Ailment> tempFilteredAilmentsList = new ArrayList<>();
                    for(Ailment anAilment: mAilmentsList){
                        if(anAilment.Name.toLowerCase().contains(userTypedString.toLowerCase())){
                            tempFilteredAilmentsList.add(anAilment);
                        }
                    }

                    mFilteredAilmentsList = tempFilteredAilmentsList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredAilmentsList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredAilmentsList = (List<Ailment>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface IListenToClicks {
        void onAilmentNameClicked(int clickedPos);
    }

    public void swapData(List<Ailment> newAilmentsList){
        this.mAilmentsList.clear();
        this.mAilmentsList = newAilmentsList;
        this.mFilteredAilmentsList.clear();
        this.mFilteredAilmentsList = newAilmentsList;
        notifyDataSetChanged();
    }

    public class CardForAilment extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mAilmentNameTextView;
        public TextView mAilmentSmallNameTextView;
        public ImageView mThumbnail;

        public CardForAilment(View itemView) {
            super(itemView);
            mAilmentNameTextView = (TextView)itemView.findViewById(R.id.txtAilmentName);
            mAilmentSmallNameTextView = (TextView) itemView.findViewById(R.id.txtAilmentSmallName);
            mThumbnail = (ImageView)itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mIClickHandler.onAilmentNameClicked(clickedPosition);
        }
    }
}
