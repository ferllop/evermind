package com.ferllop.evermind.activities;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ferllop.evermind.R;
import com.ferllop.evermind.models.Card;


import java.util.ArrayList;
import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {

    List<Card> cards;

    public CardsAdapter() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card){
        cards.add(card);
        notifyDataSetChanged();
    }

    public void clear(){
        cards.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.view_card_preview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Card card = cards.get(position);
        holder.bind(card);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView author;
        TextView labels;
        TextView question;
        TextView answer;
        Button edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author_textView);
            labels = itemView.findViewById(R.id.labels_textView);
            question = itemView.findViewById(R.id.question_textView);
            answer = itemView.findViewById(R.id.answer_textView);
            edit = itemView.findViewById(R.id.edit_button);
        }

        public void bind(Card card) {
            author.setText(card.getAuthor());
            labels.setText(card.getLabelling().toString());
            question.setText(card.getQuestion());
            answer.setText(card.getAnswer());
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), CardDataActivity.class);
                    v.getContext().startActivity(intent.putExtra("id", card.getId()));
                }
            });
        }
    }
}
