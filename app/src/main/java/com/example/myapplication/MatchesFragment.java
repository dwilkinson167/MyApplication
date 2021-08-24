package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class MatchesFragment extends Fragment {

    private static final String TAG = MatchesFragment.class.getSimpleName();

    private LocationManager locationManager;
    private double longitudeNetwork, latitudeNetwork;

    int namesLength;
    int picturesLength;

    private ArrayList<String> mNames;
    private ArrayList<String> mPictures;

//    private final LocationListener locationListenerNetwork;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view,
                container, false);

        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

//        toggleNetworkUpdates(recyclerView);

        Log.i(TAG, "lat: " + latitudeNetwork);
        Log.i(TAG, "long: " + longitudeNetwork);

        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView picture;
        public TextView name;
        public ImageButton likeButton;


        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_matches, parent, false));

            picture = itemView.findViewById(R.id.card_image);
            name = itemView.findViewById(R.id.card_title);

            likeButton = itemView.findViewById(R.id.like_button);

            likeButton.setOnClickListener(v -> {

                String likeToast = String.format(itemView.getContext().getString(R.string.you_liked),
                        name.getText().toString());

                Toast toast = Toast.makeText(v.getContext(), likeToast, Toast.LENGTH_SHORT);
                toast.show();
            });
        }
    }

    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        private int mLength;
        //        private ArrayList<String> mNames;
//        private ArrayList<String> mPictures;
        private ArrayList<String> mUid;
        private ArrayList<Boolean> mIsLiked;
        private ArrayList<String> mLat;
        private ArrayList<String> mLong;

        double latitudeNetwork;
        double longitudeNetwork;

        public int initMatchesTotal;
        public int finalMatchesTotal;
        double[] distMeters;
        ArrayList<Double> distMiles;

        boolean hasMatches = false;

        int matchCount = 0;

        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            toggleNetworkUpdates();
        }

        private final LocationListener locationListenerNetwork = new LocationListener() {
            @SuppressLint("StringFormatMatches")
            public void onLocationChanged(Location location) {
                longitudeNetwork = location.getLongitude();
                latitudeNetwork = location.getLatitude();

                MatchesViewModel viewModel = new MatchesViewModel();

                viewModel.getMatches(
                        (ArrayList<Match> matches) -> {

                            mLength = matches.size();
                            namesLength = matches.size();
                            picturesLength = matches.size();

                            mNames = new ArrayList<>();
                            mPictures = new ArrayList<>();
                            mUid = new ArrayList<>();
                            mIsLiked = new ArrayList<>();
                            mLat = new ArrayList<>();
                            mLong = new ArrayList<>();

                            for(int i = 0; i < matches.size(); i++) {
                                mNames.add(matches.get(i).getName());
                                mPictures.add(matches.get(i).getImageUrl());
                                mUid.add(matches.get(i).getUid());
                                mIsLiked.add(matches.get(i).getIsLiked());
                                mLat.add(matches.get(i).getLat());
                                mLong.add(matches.get(i).getLongitude());
                            }
                            notifyDataSetChanged();

                            hasMatches = true;
                            initMatchesTotal = mNames.size();

                            if(hasMatches && initMatchesTotal == 6) {

                                distMeters = new double[6];
                                distMiles = new ArrayList<>();

                                for(int i = 0; i < mNames.size(); i++) {

                                    Location matchLocat = new Location("match");

                                    double matchLat = Double.parseDouble(mLat.get(i));
                                    double matchLong = Double.parseDouble(mLong.get(i));

                                    matchLocat.setLatitude(matchLat);
                                    matchLocat.setLongitude(matchLong);

                                    Location userLocat = new Location("user");

                                    userLocat.setLatitude(latitudeNetwork);
                                    userLocat.setLongitude(longitudeNetwork);

                                    distMeters[i] = userLocat.distanceTo(matchLocat);

                                    double miles = (double) Math.abs(distMeters[i]) * 0.00062;

                                    distMiles.add(i, miles);

                                    if(distMiles.get(i) > 10.0) {
                                        mNames.remove(i);
                                        mPictures.remove(i);
                                        mIsLiked.remove(i);
                                        mUid.remove(i);
                                        mLat.remove(i);
                                        mLong.remove(i);

                                        i--;
                                        matchCount++;
                                    }
                                }
                            }
                        }
                );

                getActivity().runOnUiThread(()-> {
                    Toast.makeText(getContext(), R.string.network_provider_update, Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}

            @Override
            public void onProviderEnabled(String s) {}

            @Override
            public void onProviderDisabled(String s) {}
        };

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if(mNames.size() > 0) {
                holder.name.setText(mNames.get(position % mNames.size()));
                Picasso.get().load(mPictures.get(position % mPictures.size())).into(holder.picture);
            }
        }

        @Override
        public int getItemCount() {
            return mLength;
        }

        public void toggleNetworkUpdates() {
            if(!checkLocation()) {
                return;
            }
            locationManager.removeUpdates(locationListenerNetwork);

            if (ActivityCompat.checkSelfPermission(requireContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(requireContext(),
                            android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5 * 1000,
                    10, locationListenerNetwork);

            Toast.makeText(getContext(), getString(R.string.network_provider_started_running), Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkLocation() {
        if(!isLocationEnabled()) {
            showAlert(requireContext());
        }
        return isLocationEnabled();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void showAlert(Context context) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
        dialog.setTitle(R.string.enable_location)
                .setMessage(context.getResources().getString(R.string.location_message))
                .setPositiveButton(R.string.location_settings, (paramDialogInterface, paramInt) -> {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                })
                .setNegativeButton(R.string.cancel, (paramDialogInterface, paramInt) -> {});
        dialog.show();
    }
}
