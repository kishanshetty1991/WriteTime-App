package com.example.writetime.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.writetime.Models.ModelPdf;
import com.example.writetime.MyApplication;
import com.example.writetime.activities.PdfDetailActivity;
import com.example.writetime.databinding.RowPdfFavouriteBinding;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdapterPdfFavourite extends RecyclerView.Adapter<AdapterPdfFavourite.HolderPdfFavourite> {

    private Context context;
    private ArrayList<ModelPdf> pdfArrayList;

    //view binding for row_pdf_favourite.xml
    private RowPdfFavouriteBinding binding;
    private static final String TAG="FAV_BOOK_TAG";
    //constructor


    public AdapterPdfFavourite(Context context, ArrayList<ModelPdf> pdfArrayList) {
        this.context = context;
        this.pdfArrayList = pdfArrayList;
    }

    @NonNull
    @Override
    public HolderPdfFavourite onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //bind/inflate row_pdf_favourite.xml layout
        binding = RowPdfFavouriteBinding.inflate(LayoutInflater.from(context), parent,false);
        return new HolderPdfFavourite(binding.getRoot());

    }

    @Override
    public void onBindViewHolder(@NonNull HolderPdfFavourite holder, int position) {
        //Get data, set data, handle click
        ModelPdf model = pdfArrayList.get(position);
        loadBookDetails(model, holder);
        //handle click, open pdf details page, already done in previous videos

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PdfDetailActivity.class);
                intent.putExtra("bookID", model.getId()); //pass book id not category id
                context.startActivity(intent);
            }
        });

        //handle click remove from favourites
        holder.removeFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.removeFromFavourite(context, model.getId()); //pass book id not category id
            }
        });
    }

    private void loadBookDetails(ModelPdf model, HolderPdfFavourite holder) {
        String bookID = model.getId();
        Log.d(TAG, "loadBookDetails: Book Details of Book ID: "+bookID);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.child(bookID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //get book info
                        String bookTitle = ""+snapshot.child("title").getValue();
                        String description = ""+snapshot.child("description").getValue();
                        String categoryId = ""+snapshot.child("categoryId").getValue();
                        String bookUrl = ""+snapshot.child("url").getValue();
                        String timestamp = ""+snapshot.child("timestamp").getValue();
                        String uid = ""+snapshot.child("uid").getValue();
                        String viewsCount = ""+snapshot.child("viewsCount").getValue();
                        String downloadsCount = ""+snapshot.child("downloadsCount").getValue();

                        //set to model
                        model.setFavourite(true);
                        model.setTitle(bookTitle);
                        model.setDescription(description);
                        model.setTimestamp(Long.parseLong(timestamp));
                        model.setCategoryId(categoryId);
                        model.setUid(uid);
                        model.setUrl(bookUrl);


                        //format Date
                        String date = MyApplication.formatTimestamp(Long.parseLong(timestamp));

                        MyApplication.loadCategory(categoryId, holder.categoryTv);
                        MyApplication.loadPdfFromUrlSinglePage(""+bookUrl, ""+bookTitle, holder.pdfView, holder.progressBar, null);
                        MyApplication.loadPdfSize(""+bookUrl, ""+bookTitle, holder.sizeTv);
                        //set data to views
                        holder.titleTv.setText(bookTitle);
                        holder.descriptionTv.setText(description);
                        holder.dateTv.setText(date);



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return pdfArrayList.size(); //return list size || number of records
    }

    //ViewHolder class
    class HolderPdfFavourite  extends RecyclerView.ViewHolder{

        PDFView pdfView;
        ProgressBar progressBar;
        TextView titleTv, descriptionTv, categoryTv,sizeTv,dateTv;
        ImageButton removeFavBtn;
        public HolderPdfFavourite(@NonNull View itemView) {
            super(itemView);

            //init ui views of row_pdf_favourite.xml
            pdfView = binding.pdfView;
            progressBar = binding.progressBar;
            titleTv = binding.titleTv;
            removeFavBtn = binding.removeFavBtn;
            descriptionTv = binding.descriptionTv;
            categoryTv = binding.categoryTv;
            sizeTv = binding.sizeTv;
            dateTv = binding.dateTv;


        }
    }
}