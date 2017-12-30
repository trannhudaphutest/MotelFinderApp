package com.example.mrm82.motelfinderapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by mrm82 on 28/12/2017.
 */

public class Post extends AppCompatActivity {

    EditText edtTitle,edtTime,edtContent,edtPrice,edtAddress,edtArea;
    Button btnAdd,btnCancel;
    Spinner spItem;
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat spddate=new SimpleDateFormat("dd/MM/yyyy");
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);
        databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
        AddControl();

        edtTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseTime();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtTitle.getText().toString().isEmpty() || edtAddress.getText().toString().isEmpty()
                        || edtArea.getText().toString().isEmpty() || edtContent.getText().toString().isEmpty()
                        || edtPrice.getText().toString().isEmpty() || edtTime.getText().toString().isEmpty()){
                    Noti("Có vùng còn thiếu!");
                }
                else {
                    AddPost();
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder notice = new AlertDialog.Builder(Post.this);
                notice.setTitle("Hủy bài đăng ?");
                notice.setMessage("Bạn có chắc không?");
                notice.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

                notice.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                notice.create().show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder notice = new AlertDialog.Builder(Post.this);
        notice.setTitle("Are you sure ?");
        notice.setMessage("Thoát mà không lưu bài?");
        notice.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        notice.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        notice.create().show();
    }

    private void AddControl(){
        edtTitle=findViewById(R.id.edtTitle);
        edtTime=findViewById(R.id.edtTime);
        edtContent=findViewById(R.id.edtContent);
        edtPrice=findViewById(R.id.edtPrice);
        edtAddress=findViewById(R.id.edtAddress);
        edtArea=findViewById(R.id.edtArea);
        btnAdd=findViewById(R.id.btnAdd);
        btnCancel=findViewById(R.id.btnCancel);
        spItem=findViewById(R.id.spItem);
    }

    private void ChooseTime(){

        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cal.set(java.util.Calendar.YEAR,year);
                cal.set(java.util.Calendar.MONTH,month);
                cal.set(java.util.Calendar.DATE,dayOfMonth);
                edtTime.setText(spddate.format(cal.getTime()));
            }
        };
        DatePickerDialog datePickerDialog=new DatePickerDialog(
                Post.this,
                callback,
                cal.get(java.util.Calendar.YEAR),
                cal.get(java.util.Calendar.MONTH),
                cal.get(java.util.Calendar.DATE));
        datePickerDialog.show();
    }

    private void AddPost(){

        String ID = databaseReference.push().getKey();
        String Title = edtTitle.getText().toString();
        String Time = edtTime.getText().toString();
        String Content = edtContent.getText().toString();
        String Price = edtPrice.getText().toString();
        String Address = edtAddress.getText().toString();
        String Area = edtArea.getText().toString();
        String postType = spItem.getSelectedItem().toString();

        PostConstruct Post = new PostConstruct(ID,Title,postType,Time,Content,Address,Area,Price);
        databaseReference.child(ID).setValue(Post);
        Noti("Post đã được tạo!");
    }
    private void Noti(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}
