package com.udacity.silver.habits.ui;

import android.content.Context;
import android.content.SharedPreferences;
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
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> implements SharedPreferences.OnSharedPreferenceChangeListener {
    public List<Habit> habits;
    private View emptyView;
    private Context context;

    HabitAdapter(Context context, View emptyView) {
        this.context = context;
        this.emptyView = emptyView;
        reloadHabits();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        reloadHabits();
    }

    private void reloadHabits() {

        habits = Storage.getHabits(context);

        if (habits.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }
        notifyDataSetChanged();
    }


    @Override
    public HabitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.list_item_habit, parent, false);
        return new HabitViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final HabitViewHolder holder, int position) {

        final Habit habit = habits.get(position);

        holder.habitName.setText(habit.name);
        holder.count.setText(String.format(Locale.getDefault(), "%d", habit.timesCompleted));
        holder.timesPerDay.setText(context.getString(R.string.times_per_day, habit.timesPerDay));

        holder.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AnimatedVectorDrawable) holder.doneButton.getDrawable()).start();
                Storage.incrementHabit(context, habit.name);
            }
        });


    }

    @Override
    public int getItemCount() {
        return habits.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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


        HabitViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
