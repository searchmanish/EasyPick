package com.softcodeinfotech.easypick;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softcodeinfotech.easypick.response.LocationResponse;
import com.softcodeinfotech.easypick.response.QueryFormResponse;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class QueryFragment extends Fragment {
    View view;
    Spinner selectCat, selectPick, selectDrop;
    Button msubmit, click;
    EditText queryComment;
    ProgressBar pBar;
    String category, picklocation, droplocation, comment;
    RelativeLayout rootLayout;

    ArrayList<String> arrayList;

    Retrofit retrofit;
    ConstraintLayout root;
    ServiceInterface serviceInterface;


    public QueryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.query, container, false);
        setUpWidget();
        pBar.setVisibility(View.GONE);

        arrayList = new ArrayList<>();
        arrayList.add("New Delhi");


        msubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInput();

                if (category.equals("") || picklocation.equals("") || droplocation.equals("") || comment.equals("")) {

                    Snackbar snackbar = Snackbar.make(root, "  Fill All Fields First ", Snackbar.LENGTH_LONG);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                    // getlocationReq();


                } else {
                    queryReq();
                    pBar.setVisibility(View.VISIBLE);
                }

            }


        });


        //category Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.selectCat));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectCat.setAdapter(adapter);

        //pickUp spinner
        ArrayAdapter<String> adapterPick = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectPick.setAdapter(adapterPick);

        //Drop spinner
        ArrayAdapter<String> adapterDrop = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_list_item_1, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectDrop.setAdapter(adapterDrop);


        selectCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {


                category = String.valueOf(arg0.getItemAtPosition(position));
                getlocationReq();
                // Toast.makeText(AddNoteActivity.this, ""+item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        selectPick.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {


                picklocation = String.valueOf(arg0.getItemAtPosition(position));
                // Toast.makeText(AddNoteActivity.this, ""+item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        selectDrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int position, long id) {


                droplocation = String.valueOf(arg0.getItemAtPosition(position));
                // Toast.makeText(AddNoteActivity.this, ""+item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        //Retrofit
        pBar.setVisibility(View.GONE);

        Gson gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serviceInterface = retrofit.create(ServiceInterface.class);


        return view;


        // category Spinner item selection

    }

    private void getlocationReq() {

        Call<LocationResponse> call = serviceInterface.getLocation(convertPlainString("1234"));
        call.enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                if (response.body().getStatus() == 1) {
                    for (int i = 0; i < response.body().getInformation().size(); i++)
                        arrayList.add(response.body().getInformation().get(i).getLocation());
                    // Toast.makeText(getActivity(), ""+response.body().getInformation().get(i).getLocation(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {

            }
        });
    }

    private void queryReq() {
        Call<QueryFormResponse> call = serviceInterface.saveQuery(convertPlainString(category), convertPlainString(picklocation)
                , convertPlainString(droplocation), convertPlainString(comment));

        call.enqueue(new Callback<QueryFormResponse>() {
            @Override
            public void onResponse(Call<QueryFormResponse> call, Response<QueryFormResponse> response) {
                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        pBar.setVisibility(View.GONE);

                        queryComment.setText("");


                        Snackbar snackbar = Snackbar.make(root, "  Data Saved Sucessfully ", Snackbar.LENGTH_LONG);
                        View snackBarView = snackbar.getView();
                        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();

                    }
                }
            }


            @Override
            public void onFailure(Call<QueryFormResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "" + t, Toast.LENGTH_SHORT).show();

            }
        });


    }

    // convert aa param into plain text
    public RequestBody convertPlainString(String data) {
        RequestBody plainString = RequestBody.create(MediaType.parse("text/plain"), data);
        return plainString;
    }

    private void getInput() {

        comment = queryComment.getText().toString().trim();


    }

    private void setUpWidget() {
        selectCat = view.findViewById(R.id.selectCat);
        selectPick = view.findViewById(R.id.selectPick);
        selectDrop = view.findViewById(R.id.selectDrop);
        pBar = view.findViewById(R.id.pBar);
        queryComment = view.findViewById(R.id.queryComment);
        msubmit = view.findViewById(R.id.msumbit);
        root = view.findViewById(R.id.rootlayout1);

    }

}
