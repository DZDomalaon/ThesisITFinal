package com.example.thesisitfinal;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class test extends AppCompatActivity {
    private static final String url = "jdbc:mysql://192.168.1.100:3306/eqdata";
    private static final String user = "root";
    private static final String pass = "root";
    Button btnFetch,btnClear;
    public static TextView txtData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        txtData = findViewById(R.id.txtData);
        btnFetch = findViewById(R.id.btnFetch);
        btnClear = findViewById(R.id.btnClear);
        /*
        btnFetch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ConnectMySql connectMySql = new ConnectMySql();
                connectMySql.execute("");
            }
        });
        */
        btnFetch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                getData list = new getData();
                list.execute();
            }
        });
    }

    private class ConnectMySql extends AsyncTask<String, Void, String> {
        String res = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(test.this, "Please wait...", Toast.LENGTH_SHORT)
                    .show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pass);
                System.out.println("Database connection success");

                String result = "Database Connection Successful\n";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select name from test");
                ResultSetMetaData rsmd = rs.getMetaData();

                while (rs.next()) {
                    result += rs.getString(1).toString() + "\n";
                }
                res = result;
            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            txtData.setText(result);
        }
    }
}