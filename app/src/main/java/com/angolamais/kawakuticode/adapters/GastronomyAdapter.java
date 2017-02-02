package com.angolamais.kawakuticode.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angolamais.kawakuticode.angola.R;
import com.angolamais.kawakuticode.angola.RecipeActivity;
import com.angolamais.kawakuticode.models.FoodModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by russeliusernestius on 26/01/17.
 */

public class GastronomyAdapter extends RecyclerView.Adapter<GastronomyAdapter.GastronomyHolder> {

    List<FoodModel> food_list;
    private Context context;

    public GastronomyAdapter(List<FoodModel> food_list, Context context) {
        this.food_list = food_list;
        this.context = context;
    }

    @Override
    public GastronomyAdapter.GastronomyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gastronomy_item, parent, false
        );
        return new GastronomyHolder(view, this.getContext());
    }


    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return context;
    }

    @Override
    public void onBindViewHolder(GastronomyAdapter.GastronomyHolder holder, int position) {
        holder.food_name.setText(food_list.get(position).getDish_name());
        holder.n_people.setText(food_list.get(position).getNumber_people());
        holder.time_preparation.setText(food_list.get(position).getTime_preparation());
        holder.food_img.setImageBitmap(food_list.get(position).getDish_img());

    }

    @Override
    public int getItemCount() {

        return food_list.size();
    }


    public class GastronomyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView food_name, n_people, time_preparation;
        public ImageView food_img;
        public FoodModel food;
        private Context hcontext;


        public GastronomyHolder(View itemView, Context hcontext) {
            super(itemView);

            food_name = (TextView) itemView.findViewById(R.id.food_text);
            n_people = (TextView) itemView.findViewById(R.id.n_people_text);
            time_preparation = (TextView) itemView.findViewById(R.id.time_preparation_text);
            food_img = (ImageView) itemView.findViewById(R.id.coverFoodImageView);
            this.hcontext = hcontext;
            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {

            int position = getAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it

                FoodModel food_v = food_list.get(position);
                /// / We can access the data within the views
                Intent food_intent = new Intent(this.hcontext, RecipeActivity.class);
                food_intent.putExtra("dish", food_v);


                try {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    Bitmap bmp = food_v.getDish_img();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                    byte[] bytes = stream.toByteArray();
                    //clean up
                    stream.close();
                    food_intent.putExtra("bitmapbytes", bytes);


                } catch (IOException e) {
                    e.printStackTrace();
                }


                this.hcontext.startActivity(food_intent);

            }
        }
    }


}
