package com.example.a48101040.promedic.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

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
        this.mAilmentsList = ailmentList;
        this.mContext = context;
        this.mIClickHandler = clickHandler;
    }

    @Override
    public AilmentRecyclerAdapter.CardForAilment onCreateViewHolder(ViewGroup parent, int viewType) {
        View aCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.ailment_names_card, parent, false);
        return new CardForAilment(aCard);
    }

    @Override
    public void onBindViewHolder(AilmentRecyclerAdapter.CardForAilment holder, int position) {
        Ailment anAilment = mAilmentsList.get(position);
        holder.mAilmentNameTextView.setText(anAilment.Name);
    }

    @Override
    public int getItemCount() {
        return mAilmentsList.size();
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
                        if(anAilment.Name.toLowerCase().contains(userTypedString)){
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
        notifyDataSetChanged();
    }

    public class CardForAilment extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mAilmentNameTextView;

        public CardForAilment(View itemView) {
            super(itemView);
            mAilmentNameTextView = (TextView)itemView.findViewById(R.id.txtAilmentName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mIClickHandler.onAilmentNameClicked(clickedPosition);
        }
    }
}
