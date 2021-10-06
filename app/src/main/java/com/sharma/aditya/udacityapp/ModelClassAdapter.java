package com.sharma.aditya.udacityapp;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ModelClassAdapter extends ArrayAdapter<ModelClass> {

    int listColor;
    private static final String TAG = "ModelClassAdapter";

    /**
     * Here we are putting resource Id as 0 because we are implementing our own layout
     * we are not rely on the super class constructor to create listItem View for us
     * @param context
     * @param arrayList
     */
    public ModelClassAdapter(@NonNull Context context, ArrayList<ModelClass> arrayList, int color) {
        super(context, 0, arrayList);
        listColor = color;
    }

    /**
     * For Getting the View And Assigning the value of states to the variable
     * @param position  position in the list of data this layout should represent
     * @param convertView The RecyclerView that needs to be populated
     * @param parent the list item view will be added to this parent ViewGroup as Children
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Object located at particular position in the list
        ModelClass modelClass = getItem(position);

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_text_view,parent,false);
          //  listItemView.setBackgroundColor(listColor);
        }

        TextView txtMain = listItemView.findViewById(R.id.txtMain);
        txtMain.setText(modelClass.getMain());

        TextView txtTranslated = listItemView.findViewById(R.id.txtTranslated);
        txtTranslated.setText(modelClass.getTranslated());

        ImageView imageView = listItemView.findViewById(R.id.imgMainIcon);
        if (modelClass.hasImage()) {
            imageView.setImageResource(modelClass.getImageResource());
        } else{
            imageView.setVisibility(View.GONE);
        }

//        View textContainer = listItemView.findViewById(R.id.text_layout);
//        // Find the color that the resource ID maps to
//        int color = ContextCompat.getColor(getContext(), listColor);
//        textContainer.setBackgroundColor(color);

          return listItemView;
//        return super.getView(position, convertView, parent);

    }
}
