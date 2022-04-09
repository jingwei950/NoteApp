package com.example.noteapp.model;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.MainActivity;
import com.example.noteapp.Note;
import com.example.noteapp.NoteActivity;
import com.example.noteapp.NoteDatabase;
import com.example.noteapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable {

    private ArrayList<Note> notes;
    private ArrayList<Note> notesFilter; //For filtering notes when using the search bar

    //Constructor
    public Adapter(ArrayList<Note> notes){
        //Original notes ArrayList
        this.notes = notes;
        //ArrayList for search filtering
        notesFilter = new ArrayList<>(notes);
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
        holder.noteTitle.setText(notes.get(position).getTitle());     //Get the position and set the text of title in List with the same position
        holder.noteContent.setText(notes.get(position).getContent()); //Get the position and set the text of content in List with the same position
        holder.noteCard.setCardBackgroundColor(holder.view.getResources().getColor(color));   //Set the card view with random color

        //If the title is empty, set the text as <Untitled>
        if (holder.noteTitle.getText().toString().equals("")){
            holder.noteTitle.setText(holder.view.getContext().getString(android.R.string.untitled));
        }

        String number = Integer.toString(position + 1);

        long noteID = notes.get(holder.getAdapterPosition()).getID();
        String noteTitle = notes.get(holder.getAdapterPosition()).getTitle();
        String noteContent = notes.get(holder.getAdapterPosition()).getContent();
        long noteUserID = notes.get(holder.getAdapterPosition()).getUserID();

        //Set the holder with click listener
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //When the notes is clicked the start a new activity
                Intent noteIntent = new Intent(view.getContext(), NoteActivity.class); //Pass the context and the activity
                noteIntent.putExtra("id", noteID);          //Pass the ID to the new activity
                noteIntent.putExtra("title", noteTitle);    //Pass the title to the new activity
                noteIntent.putExtra("content", noteContent);//Pass the content to the new activity
                noteIntent.putExtra("userId", noteUserID);
                noteIntent.putExtra("color", color);        //Pass the color to the new activity
                view.getContext().startActivity(noteIntent);      //Start the note activity
            }
        });

        //Set the on click listener for card menu on each card
        holder.cardMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu menu = new PopupMenu(view.getContext(), view);
                menu.setGravity(Gravity.END); //Show menu directly below instead of right side
                menu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent goNoteActivity = new Intent(view.getContext(), NoteActivity.class);                //Pass the context and the activity
                        goNoteActivity.putExtra("id", noteID);
                        goNoteActivity.putExtra("title", noteTitle);
                        goNoteActivity.putExtra("content", noteContent);
                        goNoteActivity.putExtra("userId", noteUserID);
                        goNoteActivity.putExtra("color", color);
                        view.getContext().startActivity(goNoteActivity);
                        return false;
                    }
                });
                menu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Note note = new Note(noteID, noteTitle, noteContent, noteUserID); //--> not used?
                        NoteDatabase db = new NoteDatabase(view.getContext());
                        db.deleteNote(noteID);
                        notes.remove(holder.getAdapterPosition()); //Delete from List
                        notifyItemRemoved(holder.getAdapterPosition()); //Update the adapter to show deleted item
                        return false;
                    }
                });

                menu.show();

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
//        return listOfTitles.size();

        return notes.size();
//        return listOfTitles != null ? listOfTitles.size() : 0;
    }

    //2. Receive the view from OnCreateViewHolder and get the text view
    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView noteID;
        TextView noteTitle;     //Text view for the title
        TextView noteContent;   //Text view for the content
        CardView noteCard;      //Card view for each note, which contains title and content
        ImageView cardMenu;   //3 dot button Image view on each card for edit and delete

        //3. Declare the views
        public ViewHolder(@NonNull View itemView){
            super(itemView);

            //Assign views to variables
            view = itemView;
            noteID = itemView.findViewById(R.id.listId);
            noteTitle = itemView.findViewById(R.id.titles);
            noteContent = itemView.findViewById(R.id.content);
            noteCard = itemView.findViewById(R.id.noteCard);
            cardMenu = itemView.findViewById(R.id.menuIcon);
        }
    }

    //For filter search
    @Override
    public Filter getFilter() {
        return filterNotes; //Return the filter note
    }

    private Filter filterNotes = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) { //For perform filtering
            //New ArrayList for storing filtered item(s)
            ArrayList<Note> filteredList = new ArrayList<>();

            //If input search field is empty
            if(charSequence == null || charSequence.length() == 0){
                //Show all the results because the input field is empty
                filteredList.addAll(notesFilter);
            }
            else{ //If there was something in the input search field
                //Convert the input text to string and lowercase, trim it in case there's spaces in front of behind of text
                String filterPattern = charSequence.toString().toLowerCase().trim();

                //Loop thru the ArrayList
                for(Note item : notesFilter){
                    //If the note's title contains the filterPattern (the input text in search bar)
                    if(item.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(item); //Add it into the filtered ArrayList
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList; //The results will be the ArrayList filteredList
            //Return the results
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) { //Publish the results of filtering
            notes.clear();//Remove any item in the original notes
            notes.addAll((ArrayList)filterResults.values); //Add all results of filtered restults
            notifyDataSetChanged();//Tell the to refresh the list
        }
    };
}
