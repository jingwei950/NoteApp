package com.example.noteapp.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.NoteActivity;
import com.example.noteapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private final List<String> listOfTitles;
    private final List<String> listOfContent;

    //Constructor
    public Adapter(List<String> title, List<String> content){
        //Setting the values passed into this Adapter to the List of this Adapter
        this.listOfTitles = title;
        this.listOfContent = content;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Render the view of each note
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_layout, parent, false);
        return new ViewHolder(view); //1. Pass the view to ViewHolder
    }

    //4. Get the data from ViewHolder and bind it to the view that contains text views declared in ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Assign the color that got generated to the variable
        int color = getRandomColor();

        //ViewHolder's position is equal to List position
        holder.noteTitle.setText(listOfTitles.get(position));    //Get the position and set the text of title in List with the same position
        holder.noteContent.setText(listOfContent.get(position)); //Get the position and set the text of content in List with the same position
        holder.noteCard.setCardBackgroundColor(holder.view.getResources().getColor(color));   //Set the card view with random color

        String number = Integer.toString(position + 1);

        //Set the holder with click listener
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //When the notes is clicked the start a new activity
                Intent noteIntent = new Intent(view.getContext(), NoteActivity.class);                //Pass the context and the activity
                noteIntent.putExtra("title", listOfTitles.get(holder.getAdapterPosition()));    //Pass the title to the new activity
                noteIntent.putExtra("content", listOfContent.get(holder.getAdapterPosition())); //Pass the content to the new activity
                noteIntent.putExtra("color", color);                                            //Pass the color to the new activity
                view.getContext().startActivity(noteIntent);                                          //Start the note activity
            }
        });
    }

    //Function to randomly select a color in colors.xml
    private int getRandomColor() {
        //List array to store the color codes in color.xml
        List<Integer> colors = new ArrayList<Integer>();
        colors.add(R.color.yellow);
        colors.add(R.color.lightGreen);
        colors.add(R.color.pink);
        colors.add(R.color.lightPurple);
        colors.add(R.color.skyblue);
        colors.add(R.color.gray);
        colors.add(R.color.red);
        colors.add(R.color.blue);
        colors.add(R.color.greenlight);
        colors.add(R.color.shockingPink);

        //Random module for generate random number
        Random randomColor = new Random();
        int number = randomColor.nextInt(colors.size());

        return colors.get(number);
    }

    //The number of item we want to display in the recycler view
    @Override
    public int getItemCount() {
        //The total items in the List
        return listOfTitles.size();
    }

    //2. Receive the view from OnCreateViewHolder and get the text view
    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView noteTitle;     //Text view for the title
        TextView noteContent;   //Text view for the content
        CardView noteCard;      //Card view for each note, which contains title and content

        //3. Declare the views
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            //Assign views to variables
            view = itemView;
            noteTitle = itemView.findViewById(R.id.titles);
            noteContent = itemView.findViewById(R.id.content);
            noteCard = itemView.findViewById(R.id.noteCard);
        }
    }
}
