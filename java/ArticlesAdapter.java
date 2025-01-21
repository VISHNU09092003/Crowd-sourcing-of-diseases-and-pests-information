package com.example.croudsourcing;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.MyViewHolder> {
    Context context;
    List<Article> list;
    //    DatabaseReference dbRef;
    MyDatabaseHelper db;

    public ArticlesAdapter(Context context, List<Article> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ArticlesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.article_cardview, parent, false);
        return new ArticlesAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesAdapter.MyViewHolder holder, int position) {
        db = new MyDatabaseHelper(context);
//        dbRef = FirebaseDatabase.getInstance().getReference("Reports");

        String title = list.get(position).getTitle();
        String name = list.get(position).getName();
        String intro = list.get(position).getIntro();
        String body = list.get(position).getBody();
        String email = list.get(position).getEmail();
        byte[] byteArray = list.get(position).getImg();
        boolean isApproved = list.get(position).isApproved();

        int sno = list.get(position).getSno();
        holder.title.setText(title);
        holder.name.setText(name);
        if(isApproved){
            holder.approve.setVisibility(View.VISIBLE);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, FullArticle.class);
                i.putExtra("name", name);
                i.putExtra("sno", sno);
                i.putExtra("email", email);
                i.putExtra("title", title);
                i.putExtra("intro", intro);
                i.putExtra("body", body);
                i.putExtra("isApproved", isApproved);
                i.putExtra("byteArray", byteArray);
                context.startActivity(i);

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView title, name;
        ImageView approve;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            approve = itemView.findViewById(R.id.visibility);
            title = itemView.findViewById(R.id.tvArticleTitle);
            name = itemView.findViewById(R.id.tvName);
            cardView = itemView.findViewById(R.id.articleCardView);
        }
    }

}
