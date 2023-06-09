package com.example.athena.HelperClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.example.athena.R;
import com.example.athena.common.OnBoarding;

public class SliderAdapter extends RecyclerView.Adapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(OnBoarding onBoarding) {
        this.context = context;
    }

    int images[] = {
            R.drawable.period_tracker,
            R.drawable.health_dictionary,
            R.drawable.sos_alert
    };

    int headings[] ={
            R.string.first_slide_title,
            R.string.second_slide_title,
            R.string.third_slide_title
    };

    int descriptions[]= {
            R.string.first_slide_desc,
            R.string.second_slide_desc,
            R.string.third_slide_desc
    };


    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slides_layout, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SliderViewHolder sliderViewHolder = (SliderViewHolder) holder;
        sliderViewHolder.imageView.setImageResource(images[position]);
        sliderViewHolder.heading.setText(headings[position]);
        sliderViewHolder.desc.setText(descriptions[position]);
    }


    @Override
    public int getItemCount() {
        return headings.length;
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView heading;
        TextView desc;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.slider_image);
            heading = itemView.findViewById(R.id.slider_heading);
            desc = itemView.findViewById(R.id.slider_desc);
        }

        void bind(int position) {
            imageView.setImageResource(images[position]);
            heading.setText(headings[position]);
            desc.setText(descriptions[position]);
        }
    }

}
