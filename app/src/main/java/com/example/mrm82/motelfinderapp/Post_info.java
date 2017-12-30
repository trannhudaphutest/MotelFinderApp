package com.example.mrm82.motelfinderapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by mrm82 on 28/12/2017.
 */

public class Post_info extends AppCompatActivity {

    TextView txtTitle,txtDate,txtContent,txtType,txtArea,txtAddress,txtPrice;
    ImageButton btnBack;
    DatabaseReference postRef;
    FirebaseDatabase database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_info);
        Controls();

        RetrievePostInfo();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(Post_info.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void Controls(){
        txtAddress=findViewById(R.id.txtPostAddress);
        txtTitle=findViewById(R.id.txtPostTitle);
        txtDate=findViewById(R.id.txtPostDate);
        txtContent=findViewById(R.id.txtPostContent);
        txtType=findViewById(R.id.txtPostType);
        txtArea=findViewById(R.id.txtPostArea);
        txtPrice=findViewById(R.id.txtPostPrice);
        btnBack = findViewById(R.id.imgBack);
    }

    private void RetrievePostInfo(){
        database = FirebaseDatabase.getInstance();
        postRef = database.getReference("Posts");

        Intent i = getIntent();
        final String postID = i.getStringExtra("ID");
            postRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            PostConstruct post = postSnapshot.getValue(PostConstruct.class);
                            String ID = postSnapshot.getKey();
                            if (postID.equals(ID)){
                                String address = post.getPostAddress();
                                String title = post.getPostTitle();
                                String date = post.getPostTime();
                                String content = post.getPostContent();
                                String type = post.getPostType();
                                String area = post.getPostArea();
                                String price = post.getPostPrice();

                                txtAddress.setText(address);
                                txtTitle.setText(title);
                                txtDate.setText(date);
                                txtContent.setText(content);
                                txtType.setText(type);
                                txtArea.setText(area);
                                txtPrice.setText(price);
                            }
                        }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

}
