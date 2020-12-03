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

import com.example.ffbfapp.model.FoodVenue;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodVenuesListRecyclerAdapter extends RecyclerView.Adapter<FoodVenuesListRecyclerAdapter.RecyclerViewHolder> implements Filterable {
    private DatabaseReference mRef;
    //declare the list of `FoodVenue` activities/elements
    private ArrayList<FoodVenue> mRestaurantsList;
    // We created this second `ArrayList` called `mRestaurantsListFull` in order to modify it by removing and adding `FoodVenue` elements depending on search query/text
    private ArrayList<FoodVenue> mRestaurantsListFull;
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

    // creating a class in order to interact with the inner parts of a single item (list item)
    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView,mDelIcon;
        public TextView mTitleText, mContentText;
        // RecyclerViewHolder CONSTRUCTOR
        public RecyclerViewHolder(@NonNull View singleItem, OnItemClickListener listener) {
            super(singleItem);
            mImageView      = singleItem.findViewById(R.id.imageView);
            mTitleText      = singleItem.findViewById(R.id.user_card_name_text);
            mContentText    = singleItem.findViewById(R.id.user_card_email_text);

            singleItem.setOnClickListener(new View.OnClickListener() {
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
        }
    }

    // CONSTRUCTOR
    public FoodVenuesListRecyclerAdapter(ArrayList<FoodVenue> restaurantsList) {
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
        FoodVenue currFoodVenue = mRestaurantsList.get(position);

        int maxLength = 120;
        String descriptionText = currFoodVenue.getDescription().substring(0, maxLength);
        SingleItem currentItem = new SingleItem(
                currFoodVenue.getImageResourceReference(),
                currFoodVenue.getName(),
                descriptionText,
                currFoodVenue.getUid()
        );

        Picasso.get().load(currentItem.getImageResourceUri()).into(holder.mImageView);
        holder.mTitleText.setText(currentItem.getName());
        holder.mContentText.setText(currentItem.getDescription());
    }

    // this returns the size of our sample list to the Adapter
    @Override
    public int getItemCount() {
        return mRestaurantsList.size();
    }


    private Filter mRestaurantsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<FoodVenue> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mRestaurantsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (FoodVenue item : mRestaurantsListFull){
                    if (item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

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
