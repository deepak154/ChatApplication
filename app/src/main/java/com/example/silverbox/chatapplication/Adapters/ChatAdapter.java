package com.example.silverbox.chatapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.silverbox.chatapplication.Activities.ChatActivity;
import com.example.silverbox.chatapplication.Beans.ChatBean;
import com.example.silverbox.chatapplication.Beans.PersonBean;
import com.example.silverbox.chatapplication.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.silverbox.chatapplication.R.drawable.roundedcornerchatsender;

public class ChatAdapter extends BaseAdapter {
  private  Context context;
  private  List<ChatBean> list= new ArrayList<>();
  private View rowChatView;
  String ownemail;


    public ChatAdapter(Context context, List<ChatBean> list, String ownemail) {
        this.context = context;
        this.list = list;
        this.ownemail=ownemail;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rowChatView = inflater.inflate(R.layout.rowchatlayout, null);
        TextView chatTV;
        LinearLayout ll = rowChatView.findViewById(R.id.ll);
        FrameLayout frame = rowChatView.findViewById(R.id.frame);
        chatTV= rowChatView.findViewById(R.id.chatTV);
        ChatBean bean = list.get(position);
        if(bean.getSenderID().equals(ownemail))
        {
            frame.setBackgroundResource(R.drawable.roundedcornerchatsender);
            ll.setGravity(Gravity.RIGHT);
            chatTV.setText(bean.getMsg());
        }
        else
        {
            frame.setBackgroundResource(R.drawable.roundedcornerchatreciever);
            ll.setGravity(Gravity.LEFT);
            chatTV.setText(bean.getMsg());
        }
        chatTV.setText(bean.getMsg());
        return rowChatView;
    }
}
