package com.udacity.silver.habits.ui;

import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.udacity.silver.habits.R;
import com.udacity.silver.habits.data.Habit;
import com.udacity.silver.habits.data.Storage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {

    public List<Habit> habits;

    private Context context;

    public HabitAdapter(Context context) {
        this.context = context;
        reloadHabits();
    }

    public void reloadHabits() {
        habits = Storage.getHabits(context);
        notifyDataSetChanged();
    }


    @Override
    public HabitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.list_item_habit, parent, false);
        return new HabitViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final HabitViewHolder holder, int position) {

        Habit habit = habits.get(position);

        holder.habitName.setText(habit.name);

        holder.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AnimatedVectorDrawable) holder.doneButton.getDrawable()).start();
            }
        });


    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    class HabitViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.habit_name)
        TextView habitName;

        @BindView(R.id.count)
        TextView count;

        @BindView(R.id.times_per_day)
        TextView timesPerDay;

        @BindView(R.id.done_button)
        ImageButton doneButton;


        public HabitViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);


        }
    }


}
