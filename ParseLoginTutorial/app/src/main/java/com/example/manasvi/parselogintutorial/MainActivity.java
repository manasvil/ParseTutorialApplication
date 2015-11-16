package com.example.manasvi.parselogintutorial;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

public class MainActivity extends Activity {
    // UI references.
    private EditText postEditText;
    private Button postButton;
    private ListView postsListView;

    private ParseQueryAdapter<AnywallPost> postsQueryAdapter;

    private Button logoutButton;


    //for setting up query to read from database
    private ParseQuery<AnywallPost> postsQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postEditText = (EditText) findViewById(R.id.post_edittext);
        postButton = (Button) findViewById(R.id.post_button);
        logoutButton = (Button) findViewById(R.id.logout_button);
        postsListView = (ListView) findViewById(R.id.postListView);
        updatePostView();

        postButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                post();
                updatePostView();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // Logout current user
        ParseUser.logOut();
        finish();
    }
});

    }

    @Override
    protected void onDestroy()
    {

        super.onDestroy();
        Intent intent=new Intent(MainActivity.this,WelcomeActivity.class);
        startActivity(intent);
    };

    private void post() {
        String text = postEditText.getText().toString().trim();

        // Set up a progress dialog
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage(getString(R.string.progress_post));
        dialog.show();


        // Create a post object to send to Parse
        AnywallPost post = new AnywallPost();


        post.setText(text);
        post.setUser(ParseUser.getCurrentUser());
        ParseACL acl = new ParseACL();
        // Give public read access
        acl.setPublicReadAccess(true);
        post.setACL(acl);


        // Save the post
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                if (e != null) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Posting Failed", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getApplicationContext(), "Posted Successfully", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updatePostView() {
        // Set up a customized query, Making use of Parse Query Adapter to simplify displaying in a List
        ParseQueryAdapter.QueryFactory<AnywallPost> anywallPostQueryFactory =
                new ParseQueryAdapter.QueryFactory<AnywallPost>() {
                    public ParseQuery<AnywallPost> create() {
                        ParseQuery<AnywallPost> query = AnywallPost.getQuery();
                        query.orderByDescending("createdAt");
                        query.whereEqualTo("user", ParseUser.getCurrentUser());
                        return query;
                    }
                };


        // Set up the query adapter
        postsQueryAdapter = new ParseQueryAdapter<AnywallPost>(this, anywallPostQueryFactory) {
            @Override
            public View getItemView(AnywallPost postObject, View view, ViewGroup parent) {
                if (view == null) {
                    view = View.inflate(getContext(), R.layout.post_row_item, null);
                }
                TextView contentView = (TextView) view.findViewById(R.id.description);
                TextView usernameView = (TextView) view.findViewById(R.id.uname);
                contentView.setText(postObject.getText());
                try {
                    ParseUser user =postObject.getUser().fetchIfNeeded();
                    usernameView.setText(user.getUsername());
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
            return view;
        }};




        // Disable automatic loading when the adapter is attached to a view.
        //    postsQueryAdapter.setAutoload(false);

        // Disable pagination, we'll manage the query limit ourselves
        //   postsQueryAdapter.setPaginationEnabled(false);

            // Attach the query adapter to the view
            ListView postsListView = (ListView) findViewById(R.id.postListView);
            postsListView.setAdapter(postsQueryAdapter);


    }
}
