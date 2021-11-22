package com.example.mstore.ui.Account.adminOption;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mstore.R;
import com.example.mstore.ui.Store.CompleteProductInfo;
import com.example.mstore.ui.Store.StoreAdapter;
import com.example.mstore.ui.data.entities.User;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.AllUserViewHolder> {
    private List<User> users;
    static User user;

    public AllUserAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @NotNull
    @Override
    public AllUserViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_all_user, parent, false);
        return new AllUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AllUserViewHolder holder, int position) {
        holder.bind(users.get(position));
    }



    @Override
    public int getItemCount() {
        if (users == null)
            return 0;
        else return users.size();
    }
    static class AllUserViewHolder extends RecyclerView.ViewHolder{

        TextView userName,userEmail;
        ImageView userImage;
        View view;

        public AllUserViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.ALL_userName);
            userEmail = itemView.findViewById(R.id.ALL_emailUser);
            userImage = itemView.findViewById(R.id.ALL_imageViewUser);
            view= itemView;
        }
        public void bind(User user){
            AllUserAdapter.user = user;
            userName.setText(user.name);
            userEmail.setText(user.email);
            try {
                File imgFile = new File(user.imagePath);
                Bitmap bitmap;
                bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                userImage.setImageBitmap(bitmap);
            }catch (Exception e){
                Toast.makeText(itemView.getContext(), "Permission denied",Toast.LENGTH_LONG).show();
            }


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AllUserCompInfo.user = user;
                    v.getContext().startActivity(new Intent(v.getContext(), AllUserCompInfo.class));

                }
            });
        }
    }
}
