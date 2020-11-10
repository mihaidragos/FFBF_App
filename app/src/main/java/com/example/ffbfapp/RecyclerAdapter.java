package com.example.ffbfapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> implements Filterable {
    //declare the list of `SingleItem` activities/elements
    private ArrayList<SingleItem> mRestaurantsList;
    // We created this second `ArrayList` called `mRestaurantsListFull` in order to modify it by removing and adding `SingleItem` elements depending on search query/text
    private ArrayList<SingleItem> mRestaurantsListFull;

    private OnItemClickListener mListener;

    @Override
    public Filter getFilter() {
        return mRestaurantsFilter;
    }



    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView,mDelIcon;
        public TextView mTitleText, mContentText;

        public RecyclerViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mImageView      = itemView.findViewById(R.id.imageView);
            mTitleText      = itemView.findViewById(R.id.card_title_text);
            mContentText    = itemView.findViewById(R.id.card_content_text);
            mDelIcon        = itemView.findViewById(R.id.delete_icon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            mDelIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    // CONSTRUCTOR
    public RecyclerAdapter(ArrayList<SingleItem> restaurantsList) {
        mRestaurantsList = restaurantsList;
        /*
         Made a copy of the `mRestaurantsList`
         If we would have assigned `mRestaurantsListFull = restaurantsList;` then
         they would have both pointed to the same place in memory so when we would
         have modified one the other would be modified as well
        */
        mRestaurantsListFull = new ArrayList<>(restaurantsList);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent,false);
        return new RecyclerViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        SingleItem currentItem = mRestaurantsList.get(position);

        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTitleText.setText(currentItem.getTitleText());
        holder.mContentText.setText(currentItem.getContentText());
    }

    // this returns the size of our sample list to the Adapter
    @Override
    public int getItemCount() {
        return mRestaurantsList.size();
    }

    private Filter mRestaurantsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SingleItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mRestaurantsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (SingleItem item : mRestaurantsListFull){
                    if (item.getTitleText().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            // this value will be passed onward to `publishResults` method
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // we clear the list of restaurants currently showing/displaing
            mRestaurantsList.clear();
            // we populate the list of items with the items that hav been filtered
            mRestaurantsList.addAll((List) results.values);
            // we send notification that the layout needs to be updated
            notifyDataSetChanged();

        }
    };
}
