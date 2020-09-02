package com.development.allanproject.util;

import android.content.Context;
import android.widget.ImageView;

import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.development.allanproject.R;

public class Util {
        public static  void loadImage(ImageView imageView, String url , CircularProgressDrawable progressDrawable){
            RequestOptions options = new RequestOptions()
                    .placeholder(progressDrawable)
                    .error(R.drawable.no_image_available);

            Glide.with(imageView.getContext())
                    .setDefaultRequestOptions(options)
                    .load(url)
                    .into(imageView);
        }

        public static CircularProgressDrawable getCircularDrawable(Context context){
            CircularProgressDrawable cpd = new CircularProgressDrawable(context);
            cpd.setStrokeWidth(10f);
            cpd.setCenterRadius(50f);
            cpd.start();
            return  cpd;

        }
    }

