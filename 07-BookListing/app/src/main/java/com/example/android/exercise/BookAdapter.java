package com.example.android.exercise;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BookAdapter extends ArrayAdapter<Book> {


    private final LayoutInflater inflater;
    @BindString(R.string.label_author)
    String labelAuthor;
    @BindString(R.string.label_authors)
    String labelAuthors;
    @BindString(R.string.unknown)
    String unknown;
    @BindString(R.string.and)
    String andString;

    BookAdapter(Activity activity) {
        super(activity, 0);
        inflater = LayoutInflater.from(activity);
        ButterKnife.bind(this, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item_book, parent, false);
        } else {
            view = convertView;
        }

        BookViewHolder viewHolder = (BookViewHolder) view.getTag();
        if (viewHolder == null) {
            viewHolder = new BookViewHolder(view);
            view.setTag(viewHolder);
        }

        Book book = getItem(position);
        viewHolder.title.setText(book.title);

        StringBuilder builder = new StringBuilder();

        switch (book.authors.size()) {
            case 0:
                builder.append(labelAuthor)
                        .append(unknown);
                break;
            case 1:
                builder.append(labelAuthor)
                        .append(book.authors.get(0));
                break;
            case 2:
                builder.append(labelAuthors)
                        .append(book.authors.get(0))
                        .append(andString)
                        .append(book.authors.get(1));
                break;
            default:
                StringBuilder authorBuilder = new StringBuilder();

                for (int i = 0; i < book.authors.size(); i++) {

                    if (i != 0) {
                        authorBuilder.append(", ");
                    }
                    if (i == book.authors.size() - 1) {
                        authorBuilder.append(andString);
                    }

                    authorBuilder.append(book.authors.get(i));
                }

                builder.append(labelAuthors)
                        .append(authorBuilder.toString());
                break;
        }

        viewHolder.authors.setText(builder.toString());


        return view;
    }

    class BookViewHolder {


        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.authors)
        TextView authors;

        public BookViewHolder(View view) {
            ButterKnife.bind(this, view);

        }
    }
}
