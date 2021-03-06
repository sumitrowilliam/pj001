package com.example.williamsumitro.dress.view.view.product.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.williamsumitro.dress.view.model.dress_attribute.Size;

import java.util.List;

/**
 * Created by WilliamSumitro on 29/05/2018.
 */

public class Size_Spinner_Adapter extends ArrayAdapter<Size> {
    private Context context;
    private List<Size> sizeList;
    public Size_Spinner_Adapter(Context context, int textviewResourceId, List<Size> sizeList) {
        super(context, textviewResourceId, sizeList);
        this.context = context;
        this.sizeList = sizeList;
    }
    @Override
    public int getCount(){
        return sizeList.size();
    }

    @Override
    public Size getItem(int position){
        return sizeList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (UserResponse class)
        label.setText(sizeList.get(position).getSizeName());

        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        label.setText(sizeList.get(position).getSizeName());
        return label;
    }
}
