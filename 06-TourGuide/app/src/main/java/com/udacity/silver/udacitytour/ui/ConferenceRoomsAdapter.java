package com.udacity.silver.udacitytour.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.silver.udacitytour.R;
import com.udacity.silver.udacitytour.model.ConferenceRoom;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ConferenceRoomsAdapter extends RecyclerView.Adapter<ConferenceRoomsAdapter.ConferenceRoomViewHolder> {


    private Context context;

    private List<ConferenceRoom> rooms;

    public ConferenceRoomsAdapter(Context context) {
        this.context = context;
        rooms = new ArrayList<>();

        rooms.add(new ConferenceRoom(context.getString(R.string.curiosity), 4, R.drawable.curiosity));
        rooms.add(new ConferenceRoom(context.getString(R.string.r2d2), 12, R.drawable.r2d2));
        rooms.add(new ConferenceRoom(context.getString(R.string.optimus_prime), 18, R.drawable.optimus_prime));
        rooms.add(new ConferenceRoom(context.getString(R.string.past), 2, R.drawable.past));
        rooms.add(new ConferenceRoom(context.getString(R.string.megaman), 8, R.drawable.mega_man));


    }

    @Override
    public ConferenceRoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(context).inflate(R.layout.list_item_conference_room, parent, false);
        return new ConferenceRoomViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ConferenceRoomViewHolder holder, int position) {
        ConferenceRoom room = rooms.get(position);
        holder.image.setImageDrawable(context.getDrawable(room.imageId));
        holder.name.setText(room.name);
        holder.seats.setText(context.getString(R.string.seats, room.chairs));
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    class ConferenceRoomViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image)
        ImageView image;

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.chairs)
        TextView seats;


        ConferenceRoomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
