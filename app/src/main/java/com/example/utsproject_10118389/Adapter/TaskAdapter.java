package com.example.utsproject_10118389.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.utsproject_10118389.AddNewTask;
import com.example.utsproject_10118389.MainActivity;
import com.example.utsproject_10118389.Model.TaskModel;
import com.example.utsproject_10118389.R;
import com.example.utsproject_10118389.Utils.DatabaseHelper;

import java.util.List;

//Tanggal Pengerjaan : 5 Juni 2021
//NIM   : 10118389
//Nama  : Muhammad Salman Al-Farisi
//Kelas : IF-09

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private List<TaskModel> mList;
    private MainActivity activity;
    private DatabaseHelper myDB;

    public TaskAdapter(DatabaseHelper myDB , MainActivity activity){
        this.activity = activity;
        this.myDB = myDB;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout , parent ,  false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TaskModel item = mList.get(position);
        holder.mCheckbox.setText(item.getTask());
        holder.mCheckbox.setChecked(toBoolean(item.getStatus()));
        holder.mCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    myDB.updateStatus(item.getId() , 1);
                }else
                    myDB.updateStatus(item.getId() , 0);
            }
        });
    }

    public boolean toBoolean(int num){

        return num!=0;
    }

    public Context getContext(){
        return activity;
    }

    public void setTasks(List<TaskModel> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void deleteTask(int position){
        TaskModel item = mList.get(position);
        myDB.deleteTask(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        TaskModel item = mList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id" , item.getId());
        bundle.putString("task" , item.getTask());

        AddNewTask task = new AddNewTask();
        task.setArguments(bundle);
        task.show(activity.getSupportFragmentManager(), task.getTag());

    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends ViewHolder {
        CheckBox mCheckbox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckbox = itemView.findViewById(R.id.Mcheckbox);
        }
    }
}
