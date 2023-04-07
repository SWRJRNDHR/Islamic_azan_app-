package com.songs.islamicazan;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.songs.islamicazan.models.SongModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class FirstPage extends AppCompatActivity {
    private TextView tvData;
    private ListView lvSongs;
   // Button play, pause, stop;
    MediaPlayer mp = new MediaPlayer();
    SeekBar seekBar;
    Handler handler;
    Runnable runnable;
    boolean isPaused;
    int length;
    Button rateus,share,info;
    Button closePopupBtn;
    PopupWindow popupWindow;
    LinearLayout linearLayout1;
   /* private AdView mAdView;
    public static int adCounter=0;
    public static final int TIME_COUNTER=2;  //Should be always greater than zero

    private InterstitialAd interstitial;
    private AdView mBannerAd;
*/
    DownloadManager downloadManager;



    private boolean isPausedInCall = false;
    private PhoneStateListener phoneStateListener;
    private TelephonyManager telephonyManager;
    private static final String TAG = "TELESERVICE";
    private String CurrentSongUrl;


    public static boolean isNetworkStatusAvialable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if (netInfos != null)
                if (netInfos.isConnected())
                    return true;
        }
        return false;
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);

      //  adCounter++;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        lvSongs = (ListView) findViewById(R.id.lvSongs);
        rateus  = (Button) findViewById(R.id.rateus);
        share = (Button) findViewById(R.id.share);
        info = (Button) findViewById(R.id.info);

/* Advertise


            MobileAds.initialize(this,"ca-app-pub-2081856024983169~2501892103");


        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(FirstPage.this);
// Insert the Ad Unit ID
        interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));

        interstitial.loadAd(adRequest);
// Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
// Call displayInterstitial() function


                if (adCounter%TIME_COUNTER==0)
                    Toast.makeText(getApplicationContext(), "Ad.", Toast.LENGTH_SHORT).show();
                    interstitial.show();
            }
        });

*/



//popUp
            linearLayout1 = (LinearLayout) findViewById(R.id.activity_main);
            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //instantiate the popup.xml layout file
                    LayoutInflater layoutInflater = (LayoutInflater) FirstPage.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View customView = layoutInflater.inflate(R.layout.popup_window, null);

                    closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);

                    //instantiate popup window
                    popupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

                    //display the popup window
                    popupWindow.showAtLocation(linearLayout1, Gravity.CENTER, 0, 0);

                    //close the popup window on button click
                    closePopupBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            popupWindow.dismiss();
                        }
                    });

                }
            });
//





        if (isNetworkStatusAvialable(getApplicationContext())==false) {
            Toast.makeText(getApplicationContext(), "This Application requires Internet Connection.", Toast.LENGTH_SHORT).show();
        }else
            new FirstPage.JSONTask().execute("http://188.166.91.187/classical/beethoven/beethoven.json");


        //For Seekbar
        handler = new Handler();
     /*    seekBar = (SeekBar) findViewById(R.id.seekbar);

       mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                seekBar.setMax(mp.getDuration());

                playCycle();
            }
        });



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean input) {

                if (input) {
                    mp.seekTo(progress);

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void playCycle() {
        seekBar.setProgress(mp.getCurrentPosition());

        if (mp.isPlaying()) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    playCycle();
                }
            };
            handler.postDelayed(runnable, 1000);

        }


*/


    }


    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(FirstPage.this);
        builder.setMessage("Are you sure want to do this ?");
        builder.setCancelable(true);
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                System.exit(0);

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public class JSONTask extends AsyncTask<String, String, List<SongModel>> {

        @Override
        protected List<SongModel> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("songs");

                List<SongModel> songModelList = new ArrayList<>();

                for (int i = 0; i < parentArray.length(); i++) {

                    JSONObject finalObject = parentArray.getJSONObject(i);
                    SongModel songModel = new SongModel();
                    songModel.setSong(finalObject.getString("song"));
                    songModel.setAddress(finalObject.getString("address"));
                    songModel.setAuthor(finalObject.getString("author"));

                    //Adding the final objects in the list
                    songModelList.add(songModel);
                }

                return songModelList;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;

        }

        @Override
        protected void onPostExecute(List<SongModel> result) {
            super.onPostExecute(result);

            FirstPage.SongAdapter adapter = new FirstPage.SongAdapter(getApplicationContext(), R.layout.row, result);
            lvSongs.setAdapter(adapter);

            //TODO need to set data to the list
        }


    }

    public class SongAdapter extends ArrayAdapter {

        private List<SongModel> songModelList;
        private int resource;
        private LayoutInflater inflater;


        public SongAdapter(Context context, int resource, List<SongModel> objects) {
            super(context, resource, objects);
            songModelList = objects;
            this.resource = resource;


            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(resource, null);
            }

            ImageView ivSongIcon;
            TextView tvSong;
            TextView author;
            Button ring;

            ivSongIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
            tvSong = (TextView) convertView.findViewById(R.id.tvSong);
            author = (TextView) convertView.findViewById(R.id.author);

            tvSong.setText(songModelList.get(position).getSong());
            author.setText(songModelList.get(position).getAuthor());



            tvSong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(FirstPage.this,FirstList.class);
                    i.putExtra("Address", songModelList.get(position).getAddress().toString());
                    i.putExtra("Name", songModelList.get(position).getSong().toString());
                    startActivity(i);
                    //finish();

                }


            });

            ivSongIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(FirstPage.this,FirstList.class);
                    i.putExtra("Address", songModelList.get(position).getAddress().toString());
                    i.putExtra("Name", songModelList.get(position).getSong().toString());
                    startActivity(i);
                    finish();


                }


            });


            author.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(FirstPage.this,FirstList.class);
                    i.putExtra("Address", songModelList.get(position).getAddress().toString());
                    i.putExtra("Name", songModelList.get(position).getSong().toString());
                    startActivity(i);
                    finish();

                }


            });

            rateus.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    launchMarket();
                    finish();
                }
            });
            share.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    shareApp(getApplicationContext());
                }
            });



            return convertView;
        }

    }



    public void shareApp(Context context)
    {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "Mozart:Complete Collection");
            String sAux = "\nTry this new application\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id="+ getPackageName();
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }


    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }
/*
    public void displayInterstitial() {
// If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }
*/
}
