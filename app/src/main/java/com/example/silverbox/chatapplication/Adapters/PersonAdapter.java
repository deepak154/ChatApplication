package com.example.silverbox.chatapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.silverbox.chatapplication.Activities.ChatActivity;
import com.example.silverbox.chatapplication.Beans.PersonBean;
import com.example.silverbox.chatapplication.R;

import java.util.List;

public class PersonAdapter extends BaseAdapter {
    private Context context;
    private List<PersonBean> list;
    private View userRowView;
    public PersonAdapter(Context context, List<PersonBean> list) {
        this.context = context;
        this.list = list;

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         userRowView = inflater.inflate(R.layout.dynamiclistrow, null);
        TextView name, contact;
        name = userRowView.findViewById(R.id.nameTV);
        contact = userRowView.findViewById(R.id.contactTV);
        PersonBean person = list.get(position);
        name.setText(person.getName());
        contact.setText(person.getContact());
        userRowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(context, ChatActivity.class);
                it.putExtra("KeyUserName",list.get(position).getName());
                it.putExtra("KeyUserEmail",list.get(position).getEmail());
                context.startActivity(it);
            }
        });
        return userRowView;
    }
}
