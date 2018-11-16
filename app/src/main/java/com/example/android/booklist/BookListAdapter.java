package com.example.android.booklist;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sweety Jain on 11/14/2017 at 10:47 AM.
 */

public class BookListAdapter extends ArrayAdapter<BookList> {

    public BookListAdapter(Activity context, ArrayList<BookList> books)
    {
        super(context , 0 , books);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView == null)
        {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item, parent, false) ;

        }

        BookList currentBook = getItem(position);

        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author);
        authorTextView.setText(currentBook.getmAuthor());

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title);
        titleTextView.setText(currentBook.getmTitle());
        View textContainer = listItemView.findViewById(R.id.text_container_id);

        return listItemView;
    }
}
