package com.example.ashrafi.fragment;

import android.os.AsyncTask;

// Runs a long running task in a thread

// Note that all the UI activities are only executed in Pre/Post methods

// Also all UI activities are delegated to the fragment passed in constructor
public class URLFetchTask extends AsyncTask<String, Void, String>{
    //URLDisplayFragment container;
    PageFragment container;

    public URLFetchTask(PageFragment f) {
        this.container = f;
    }



    @Override
    protected String doInBackground(String... params) {
        try {
            // Emulate a long running process
            // In this case we are pretending to fetch the URL content
            Thread.sleep(3000); // This takes 3 seconds
            // If you are implementing actual fetch API, the call would be something like this,
            // API.fetchURL(params[0]);
        }catch(Exception ex) {}

        return "Content from the URL "+params[0];
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //container.showProgressBar();
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // The activity can be null if it is thrown out by Android while task is running!
        if(container!=null && container.getActivity()!=null) {
            //container.populateResult(result);
            //container.hideProgressBar();
            this.container = null;
        }
    }
}
