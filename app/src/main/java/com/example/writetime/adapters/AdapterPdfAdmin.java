package com.example.writetime.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.writetime.Models.ModelPdf;
import com.example.writetime.MyApplication;
import com.example.writetime.activities.PdfDetailActivity;
import com.example.writetime.activities.PdfEditActivity;
import com.example.writetime.databinding.RowPdfAdminBinding;
import com.example.writetime.filters.FilterPdfAdmin;
import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;

public class AdapterPdfAdmin extends RecyclerView.Adapter<AdapterPdfAdmin.HolderPdfAdmin> implements Filterable {
    //context
    private Context context;
    //arraylist to hold list of data of type ModelPdf
    public ArrayList<ModelPdf> pdfArrayList, filterList;

    //view binding row_pdf_admin.xml
    private RowPdfAdminBinding binding;
    private FilterPdfAdmin filter;
    private static final String TAG = "PDF_ADAPTER_TAG";

    //progress
    private ProgressDialog progressDialog;

    //constructor of above
    public AdapterPdfAdmin(Context context, ArrayList<ModelPdf> pdfArrayList) {
        this.context = context;
        this.pdfArrayList = pdfArrayList;
        this.filterList = pdfArrayList;

        //init progress dialog
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @NonNull
    @Override
    public HolderPdfAdmin onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //bind layout using view binding
        binding = RowPdfAdminBinding.inflate(LayoutInflater.from(context), parent, false);


        return new HolderPdfAdmin(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPdfAdmin.HolderPdfAdmin holder, int position) {
        //Get data, set data, handle clicks, etc
        //get data
        ModelPdf model = pdfArrayList.get(position);
        String pdfId = model.getId();
        String categoryId = model.getCategoryId();

        String title = model.getTitle();
        String description = model.getDescription();
        String pdfUrl = model.getUrl();
        long timestamp = model.getTimestamp();

        //we need to convert timestamp to dd/mm/yyyy
        String formattedDate = MyApplication.formatTimestamp(timestamp);

        //set data
        holder.titleTv.setText(title);
        holder.descriptionTv.setText(description);
        holder.dateTv.setText(formattedDate);

        //loads further details like category, pdf from url, pdf size in seperate functions
        MyApplication.loadCategory(
                ""+categoryId,
                holder.categoryTv
                );

        MyApplication.loadPdfFromUrlSinglePage(
                ""+pdfUrl,
                ""+title,
                holder.pdfView,
                holder.progressBar,
                null
        );

        MyApplication.loadPdfSize(
                ""+pdfUrl,
                ""+title,
                holder.sizeTv
        );

        //handle click, show dialog with options
        //1.Edit 2.Delete
        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreOptionsDialog(model, holder);
            }
        });

        //handle click, open pdf details page, pass pdf/book id to get details of it
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(context, PdfDetailActivity.class);
                    intent.putExtra("bookID", pdfId);
                    context.startActivity(intent);
                }
                catch (Exception e){
                    Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: "+e.getMessage());
                }

            }
        });


}

    private void moreOptionsDialog(ModelPdf model, HolderPdfAdmin holder) {
        String bookID = model.getId();
        String bookUrl = model.getUrl();
        String bookTitle = model.getTitle();

        //options to show in dialog
        String[] options = {"Edit", "Delete"};

        //alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Options")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            //handle dialog option click
                            if(which==0){
                                //edit clicked, open pdfEditActivity to edit book info
                                Intent intent = new Intent(context, PdfEditActivity.class);
                                intent.putExtra("bookID", bookID);
                                context.startActivity(intent);
                            }
                            else if(which==1){
                                //delete clicked
                                MyApplication.deleteBook(
                                        context,
                                        ""+bookID,
                                        ""+bookUrl,
                                        ""+bookTitle);
                                //deleteBook(model, holder);
                            }
                    }
                })
                .show();

    }






    @Override
    public int getItemCount() {
        return pdfArrayList.size(); //return number of records | list size
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new FilterPdfAdmin(filterList, this);
        }
        return filter;
    }

    //    View holder class for row_pdf_admin.xml
    class HolderPdfAdmin extends RecyclerView.ViewHolder{

    //UI Views of row_pdf_admin.xml
    PDFView pdfView;
    ProgressBar progressBar;
    TextView titleTv, descriptionTv, categoryTv, sizeTv, dateTv;
    ImageButton moreBtn;

    public HolderPdfAdmin(@NonNull View itemView) {
        super(itemView);
        //init ui views
        pdfView = binding.pdfView;
        progressBar = binding.progressBar;
        titleTv = binding.titleTv;
        descriptionTv = binding.descriptionTv;
        categoryTv = binding.categoryTv;
        sizeTv = binding.sizeTv;
        dateTv = binding.dateTv;
        moreBtn = binding.moreBtn;



    }
}
}
