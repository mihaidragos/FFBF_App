package com.example.ffbfapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UsersRecyclerViewHolder> {
    static ArrayList<SingleUserItem> mUsersList;
    private OnItemClickListener mListener;
    private DatabaseReference mRef;
    private Toast mToast;



    // Set click listener
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(UsersRecyclerAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class UsersRecyclerViewHolder extends RecyclerView.ViewHolder {

        public CheckBox mCheckbox;
        public TextView mNameText, mEmailText;


        // RecyclerViewHolder CONSTRUCTOR
        public UsersRecyclerViewHolder(@NonNull View singleItem, UsersRecyclerAdapter.OnItemClickListener listener) {
            super(singleItem);
            mNameText       = singleItem.findViewById(R.id.user_card_name_text);
            mEmailText      = singleItem.findViewById(R.id.user_card_email_text);
            mCheckbox       = singleItem.findViewById(R.id.checkBox);



//            mCheckbox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listener != null){
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION){
//                            listener.onItemClick(position);
//                            SingleUserItem user;
//                            user = mUsersList.get(position);
//                            if(user.isCheckbox()){
//                                user.setCheckbox();
//
//                            }
//                        }
//                    }
//                }
//            });
        }
    }

    public UsersRecyclerAdapter(ArrayList<SingleUserItem> usersList) {
        mUsersList = usersList;
    }

    @NonNull
    @Override
    public UsersRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user_item, parent,false);
        return new UsersRecyclerViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersRecyclerViewHolder holder, int position) {
        SingleUserItem currentItem = mUsersList.get(position);

        holder.mNameText.setText(currentItem.getUserName());
        holder.mEmailText.setText(currentItem.getUserEmail());
        holder.mCheckbox.setChecked(currentItem.isCritic());
        System.out.println("Is checkbox " + currentItem.getUserName() + " " + currentItem.isCritic());


        holder.mCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = currentItem.getUid();
                mRef = FirebaseDatabase.getInstance().getReference().child("Users");
                boolean checked = holder.mCheckbox.isChecked();

                mRef.child(uid).child("critic").setValue(checked).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        System.out.println(currentItem.getUserName() + " is critic? - " + mRef.child(uid));

                        notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsersList.size();
    }
}
