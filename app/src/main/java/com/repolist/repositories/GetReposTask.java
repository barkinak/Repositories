package com.repolist.repositories;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

public class GetReposTask extends AsyncTask<Void, Void, String> {
    private static final String TAG = "GetReposTask";

    private String URL_str;
    private String githubUserID;

    public GetReposTask(){}

    public void setGithubUserID(String githubUserID) {
        this.githubUserID = githubUserID;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

    @Override
    protected String doInBackground(Void... arg0) {
        Socket socket = null;
        String result = "";
        URL_str = "https://api.github.com/users/" + githubUserID + "/repos";
        try {
            StringBuffer chain = new StringBuffer("");
            try{
                URL url = new URL(URL_str);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestProperty("User-Agent", "");
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = rd.readLine()) != null) {
                    chain.append(line + "\n");
                }
                result = chain.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }finally{
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}