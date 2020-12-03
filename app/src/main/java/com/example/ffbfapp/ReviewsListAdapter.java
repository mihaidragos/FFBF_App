package com.example.ffbfapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ffbfapp.model.Review;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ReviewsListAdapter extends RecyclerView.Adapter<ReviewsListAdapter.RecyclerViewHolder>  {
    private DatabaseReference mRef;
    private ArrayList<Review> reviewsList;

    public ReviewsListAdapter(ArrayList<Review> reviewsList) {
        this.reviewsList = reviewsList;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        public TextView reviewTitle, reviewContent, reviewOwner;
        public RatingBar reviewRating;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewTitle         = itemView.findViewById(R.id.tvSR_reviewTitle);
            reviewContent       = itemView.findViewById(R.id.tvSR_reviewContent);
            reviewRating        = itemView.findViewById(R.id.rbSR_ratingBar);
        }
    }

    @NonNull
    @Override
    public ReviewsListAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_review, parent,false);
        return new ReviewsListAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsListAdapter.RecyclerViewHolder holder, int position) {
        Review review = reviewsList.get(position);

        SingleReview  currentReview = new SingleReview(
                review.getOwner(),
                review.getTitle(),
                review.getContent(),
                review.getRating()
        );

        holder.reviewOwner.setText(currentReview.getUsername());
        holder.reviewTitle.setText(currentReview.getTitle());
        holder.reviewContent.setText(currentReview.getContent());
        holder.reviewRating.setRating(currentReview.getRating());
    }

    @Override
    public int getItemCount() {
            if(reviewsList != null){
                return reviewsList.size();
            } else {
                return 0;
            }
    }
}
