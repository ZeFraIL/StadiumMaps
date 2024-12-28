package zeev.fraiman.stadiummaps;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvStadiums;

    Stadium stadium;
    ArrayList<Stadium> stadiums;

    ArrayList<String> names;
    ArrayAdapter<String> adapter;

    String info_url="https://github.com/openfootball/worldcup.json/blob/master/2018/worldcup.stadiums.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvStadiums=findViewById(R.id.lvStadiums);

        stadiums=new ArrayList<>();

        names=new ArrayList<>();

        goBuild();

        adapter=new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                names);

        lvStadiums.setAdapter(adapter);

        lvStadiums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                goView(position);
            }
        });
    }

    private void goView(int position) {
        Stadium stadium=stadiums.get(position);
        AlertDialog.Builder adb=new AlertDialog.Builder(this);
        adb.setTitle(stadium.getName());
        String info="This stadium is in the city ";
        info+=stadium.getCity()+"\n";
        info+="Geographic coordinates of the stadium:";
        info+="\n================================\n";
        info+=stadium.getLat()+stadium.getLng();
        info+="================================";
        adb.setMessage(info);
        adb.setCancelable(false);
        adb.setIcon(R.drawable.world_cup);
        adb.setPositiveButton("View map",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String st4=stadium.getLat();
                        String st5=stadium.getLng();
                        String geoUriString = "geo:"+st4+","+st5+"?z=13";
                        Uri geoUri = Uri.parse(geoUriString);
                        Intent map = new Intent(Intent.ACTION_VIEW, geoUri);
                        startActivity(map);
                    }
                });
        adb.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adb.setNegativeButton("Stadium image", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent go=new Intent(MainActivity.this, StaduimImage.class);
                go.putExtra("stadium", stadium);
                startActivity(go);
            }
        });
        adb.create().show();
    }

    private void goBuild() {
        InputStream is=getResources().openRawResource(R.raw.world_cup);
        InputStreamReader isr=new InputStreamReader(is);
        BufferedReader br=new BufferedReader(isr);
        String temp, all="";
        try {
            while ((temp = br.readLine()) != null) {
                all += temp + "\n";
            }
            br.close();
            JSONObject stadion=new JSONObject(all);
            JSONArray jsonArray=stadion.getJSONArray("stadiums");
            String t1,t2,t3,t4, t5;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject arrayElement = jsonArray.getJSONObject(i);
                t1= arrayElement.getString("name") + "\n";
                t2= arrayElement.getString("city") + "\n";
                t3=arrayElement.getString("lat")+"\n";
                t4=arrayElement.getString("lng")+"\n";
                t5=arrayElement.getString("image")+"\n";

                stadium=new Stadium(t1,t2,t3,t4,t5);
                stadiums.add(stadium);

                names.add(stadium.getName());
            }
            Toast.makeText(this, ""+stadiums.size(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

/*

        //new fromNet().execute(info_url);
    }

    class fromNet extends AsyncTask<String, Void, String> {

        String all="", temp;

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream is;
                int status=connection.getResponseCode();
                if (status==HttpURLConnection.HTTP_OK)  {
                    is=connection.getInputStream();
                    InputStreamReader isr=new InputStreamReader(is);
                    BufferedReader br=new BufferedReader(isr);
                    while ((temp=br.readLine())!=null) {
                        all += temp + "\n";
                    }
                    br.close();
                }

            } catch (MalformedURLException e) {
                temp=e.toString();
            } catch (IOException e) {
                temp=e.toString();
            } catch (Exception e) {
                temp=e.toString();
            }
            return all;
        }  //end of DoInBackground

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(MainActivity.this, "all="+all, Toast.LENGTH_SHORT).show();
            try {
                JSONObject stadion=new JSONObject(all);
                JSONArray jsonArray=stadion.getJSONArray("stadiums");
                Toast.makeText(MainActivity.this, "N="+jsonArray.length(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(Start.this, ""+jsonArray.length(), Toast.LENGTH_SHORT).show();
                String t1,t2,t3,t4, t5, all1="";
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject arrayElement = jsonArray.getJSONObject(i);
                    //t1= arrayElement.getString("company-name") + "\n";
                    //t2= arrayElement.getString("company-website") + "\n";
                    //t3= arrayElement.getString("company-location") + "\n";
                    t1= arrayElement.getString("name") + "\n";
                    t2= arrayElement.getString("city") + "\n";
                    t3=arrayElement.getString("lat")+"\n";
                    t4=arrayElement.getString("lng")+"\n";
                    t5=arrayElement.getString("image")+"\n";
                    all1+=t1+t2+t3+t4+t5;
                }
                tv.setText(all1);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }  //end Async class
*/

}