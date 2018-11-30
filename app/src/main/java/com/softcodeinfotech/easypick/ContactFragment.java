package com.softcodeinfotech.easypick;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softcodeinfotech.easypick.response.ContactusResponse;

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
public class ContactFragment extends Fragment {
    EditText mName, mEmail, mMobile, mComment;
    Button submit;
    View view;
    ProgressBar pBar;
    String contactName, contactEmail, contactMobile, contactComment;


    Retrofit retrofit;
    ConstraintLayout rootlayout;

    ServiceInterface serviceInterface;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.contactus, container, false);
        setUpwidget();
        // getInput();

        //Retrofit
        pBar.setVisibility(View.GONE);

        Gson gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        serviceInterface = retrofit.create(ServiceInterface.class);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pBar.setVisibility(View.GONE);
                getInput();
                if (contactName.equals("") || contactEmail.equals("") || contactMobile.equals("") || contactComment.equals("")) {
                    Snackbar snackbar = Snackbar.make(rootlayout, "  Fill All Field First Than Submit ", Snackbar.LENGTH_LONG);
                    View snackBarView = snackbar.getView();
                    snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    snackbar.show();
                    // Toast.makeText(getActivity(), "Fill all field First", Toast.LENGTH_SHORT).show();
                } else {
                    sendDataReq();
                    pBar.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;


    }

    private void sendDataReq() {

        Call<ContactusResponse> call = serviceInterface.saveContact(convertPlainString(contactName),
                convertPlainString(contactEmail), convertPlainString(contactMobile), convertPlainString(contactComment));

        call.enqueue(new Callback<ContactusResponse>() {
            @Override
            public void onResponse(Call<ContactusResponse> call, Response<ContactusResponse> response) {

                if (response.body() != null && response.isSuccessful()) {
                    if (response.body().getStatus() == 1) {
                        pBar.setVisibility(View.GONE);
                        mName.setText("");
                        mEmail.setText("");
                        mMobile.setText("");
                        mComment.setText("");

                        Snackbar snackbar = Snackbar.make(rootlayout, "  Data Saved Sucessfully... ", Snackbar.LENGTH_LONG);
                        View snackBarView = snackbar.getView();
                        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        snackbar.show();
                        // Toast.makeText(getActivity(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    } else {
                        pBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "data save fail", Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<ContactusResponse> call, Throwable t) {
                pBar.setVisibility(View.GONE);
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
        contactName = mName.getText().toString().trim();
        contactEmail = mEmail.getText().toString().trim();
        contactMobile = mMobile.getText().toString().trim();
        contactComment = mComment.getText().toString().trim();


        //  Toast.makeText(getActivity(), "" + contactName, Toast.LENGTH_SHORT).show();
        Log.i("abc", contactEmail);
        Log.i("abc", contactMobile);
        Log.i("abc", contactComment);

    }

    private void setUpwidget() {
        mName = view.findViewById(R.id.qName);
        mEmail = view.findViewById(R.id.qEmail);
        mMobile = view.findViewById(R.id.qMobile);
        mComment = view.findViewById(R.id.qComment);
        submit = view.findViewById(R.id.submit);
        pBar = view.findViewById(R.id.pBar);

        rootlayout = view.findViewById(R.id.rootlayout);
    }

}
