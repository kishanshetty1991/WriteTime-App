package com.example.writetime.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.writetime.Models.BookModel;
import com.example.writetime.R;
import com.example.writetime.activities.ReadBookActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.viewHolder> {

    ArrayList<BookModel> list;
    Context context;

    public BookAdapter(ArrayList<BookModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_samplebook,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        BookModel model = list.get(position);
        Picasso.get()
                .load(model.getImage())
                .into(holder.bookImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReadBookActivity.class);
                intent.putExtra("url",model.getUrl());
                context.startActivity(intent);
            }
        });

        //holder.bookImage.setImageResource(model.getImage());
        holder.bookText.setText(model.getBookname());
        holder.writer.setText(model.getWriter());
        holder.bookDesc.setText(model.getBookdesc());

   }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        public ImageView bookImage;
        public TextView bookText;;
        public TextView writer;
        public TextView bookDesc;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            bookImage = itemView.findViewById(R.id.imageView2);
            bookText = itemView.findViewById(R.id.textView);
            writer = itemView.findViewById(R.id.textView2);
            bookDesc = itemView.findViewById(R.id.textView3);



        }
    }
}
