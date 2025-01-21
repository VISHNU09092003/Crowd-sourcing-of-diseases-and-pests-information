package com.example.croudsourcing;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    List<Report> list;

    public MyAdapter(Context context, List<Report> list) {
        this.context = context;
        this.list = list;
    }

    public MyAdapter() {
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.report_cardview, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

//        dbRef = FirebaseDatabase.getInstance().getReference("Reports");

        String title = list.get(position).getTitle();
        String name = list.get(position).getName();
        String report = list.get(position).getReport();
        String solution = list.get(position).getSolution();
        String email = list.get(position).getEmail();
        byte[] biteArray = list.get(position).getImg();

        int sno = list.get(position).getSno();
        holder.title.setText(title);
        holder.report.setText(report);
        holder.name.setText(name);
        holder.solution.setText(solution);

        Dialog dialog = new Dialog(context);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setContentView(R.layout.dialog_report_answer);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);

                TextView tvName = dialog.findViewById(R.id.tvName);
                TextView tvTitle = dialog.findViewById(R.id.tvTitle);
                TextView tvReport = dialog.findViewById(R.id.tvReport);
                EditText etSolution = dialog.findViewById(R.id.etSolution);
                Button submit = dialog.findViewById(R.id.btnSubmit);
                submit.setVisibility(View.INVISIBLE);
                etSolution.setFocusable(false);
                ImageView imageView = dialog.findViewById(R.id.imageView);


                byte[] newByteArray = biteArray;

                if (biteArray != null) {
                    Bitmap img = BitmapFactory.decodeByteArray(biteArray, 0, biteArray.length);
                    imageView.setImageBitmap(img);
                }
                tvName.setText(name);
                tvTitle.setText(title);
                tvReport.setText(report);
                etSolution.setText(solution);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, fullImage.class);
                        i.putExtra("bite", newByteArray);
                        context.startActivity(i);
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView title, report, name, solution;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            report = itemView.findViewById(R.id.tvReport);
            name = itemView.findViewById(R.id.tvName);
            solution = itemView.findViewById(R.id.tvSolution);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

}
