package com.example.writetime.filters;

import android.util.Log;
import android.widget.Filter;
import android.widget.Toast;

import com.example.writetime.Models.ModelPdf;
import com.example.writetime.adapters.AdapterPdfUser;

import java.util.ArrayList;
import java.util.Locale;

public class FilterPdfUser extends Filter {

    //arraylist in which we want to search
    ArrayList<ModelPdf> filterList;
    //adapter in which filter need to be implemented
    AdapterPdfUser adapterPdfUser;
    private static final String TAG = "BOOKS_USER_TAG";
    //constructor
    public FilterPdfUser(ArrayList<ModelPdf> filterList, AdapterPdfUser adapterPdfUser) {
        this.filterList = filterList;
        this.adapterPdfUser = adapterPdfUser;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        FilterResults results = new FilterResults();
        //value to be searched should not be null/empty
        if(constraint!=null || constraint.length() > 0){
            //not null nor empty
            //change to uppercase to avoid case sensitivity

            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelPdf> filteredModels = new ArrayList<>();
            for (int i=0; i<filterList.size(); i++){
                //validate
                Log.d(TAG, "performFiltering: "+filterList.get(i).getTitle());
                if(filterList.get(i).getTitle().toUpperCase().contains(constraint)){
                    //search matches add to list
                    filteredModels.add(filterList.get(i));
                }
            }

            results.count = filteredModels.size();
            results.values = filteredModels;
        }
        else {
            //empty or null, make original list/result
            results.count = filterList.size();
            results.values = filterList;

        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        //apply fiter changes
        adapterPdfUser.pdfArrayList = (ArrayList<ModelPdf>) results.values;
        //notify changes
        adapterPdfUser.notifyDataSetChanged();
    }
}
