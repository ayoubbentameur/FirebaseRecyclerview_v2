package com.example.firebaserecyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// MyAdapter.java
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> dataList;

    public UserAdapter(List<User> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User data = dataList.get(position);
        holder.textViewName.setText(data.getFirstname());
        holder.textViewLastName.setText(data.getLastname());
        holder.textViewAge.setText(String.valueOf(data.getAge()));
        holder.textdateView.setText(data.getTime());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewLastName;
        TextView textViewAge;
        TextView textdateView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.firstname);
            textViewLastName = itemView.findViewById(R.id.lastname);
            textViewAge = itemView.findViewById(R.id.age);
            textdateView=itemView.findViewById(R.id.date_id);
        }
    }
}


