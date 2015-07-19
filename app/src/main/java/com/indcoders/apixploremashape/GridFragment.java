package com.indcoders.apixploremashape;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.etsy.android.grid.StaggeredGridView;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.view.CardViewNative;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GridFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GridFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @Bind(R.id.my_recycler_view)
    ListView recyclerView;

    ProgressDialog pd;

    ArrayList<String> names,descs,prices;
    Bitmap[] imgs;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GridFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GridFragment newInstance(String param1, String param2) {
        GridFragment fragment = new GridFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public GridFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_grid, container, false);
        ButterKnife.bind(this,v);


        pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);

        names = new ArrayList<>();
        descs = new ArrayList<>();
        prices = new ArrayList<>();

        new LoadApi().execute();
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public class LoadApi extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Loading APIs....");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            String url = "https://rokity-mashape-v1.p.mashape.com/?query=explore";
            String jsonStr = null;

            OkHttpClient client = new OkHttpClient();


            Request request = new Request.Builder()
                    .url(url)
                    .header("X-Mashape-Key","tp491p4oyzmshCnzBwZ4w3u8Yuyjp1Pi5cajsnkrBifrOXLcSr")
                    .header("Accept", "application/json")
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();
                jsonStr = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("Response", "Error: " + e);
            }
            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray dataArray = jsonObj.getJSONArray("name");

                    // looping through all results
                    for (int i = 0; i < dataArray.length(); i++) {

                       Log.e("Api no : " + i, "" + dataArray.get(i));
                        names.add(dataArray.get(i).toString());

                    }

                    imgs = new Bitmap[dataArray.length()];
                    dataArray = null;
                    dataArray = jsonObj.getJSONArray("image_api");
                    for (int i = 0; i < dataArray.length(); i++) {

                        Log.e("Api no : " + i, "" + dataArray.get(i));

                        try {
                            imgs[i] = Picasso.with(getActivity()).load(dataArray.get(i).toString()).get();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }


                    dataArray = null;
                    dataArray = jsonObj.getJSONArray("desc");
                    for (int i = 0; i < dataArray.length(); i++) {

                        Log.e("Api no : " + i, "" + dataArray.get(i));
                        descs.add(dataArray.get(i).toString());

                    }

                    dataArray = null;
                    dataArray = jsonObj.getJSONArray("prices");
                    for (int i = 0; i < dataArray.length(); i++) {

                        Log.e("Api no : " + i, "" + dataArray.get(i));
                        prices.add(dataArray.get(i).toString());

                    }


                    try {

                    } catch (Exception e) {

                        //poster = DBBitmapUtility.getImage();
                        e.printStackTrace();

                    }


                } catch (JSONException e) {
                    Log.w("error", e.toString());

                }
            } else {
                Log.w("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            recyclerView.setAdapter(new GridAdapter());
            recyclerView.invalidateViews();
        }
    }

    public class GridAdapter extends BaseAdapter {

        // references to our images


        public GridAdapter() {

        }

        public int getCount() {
            return names.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }


        // create a new ImageView for each item referenced by the Adapter
        public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = null;

            if (convertView == null) {
                // if it's not recycled, initialize some attributes


                LayoutInflater inflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.cardlib_card, parent, false);

            }


            CardViewNative cardView = (CardViewNative) convertView.findViewById(R.id.carddemo_largeimage);


            MaterialLargeImageCard card =
                    MaterialLargeImageCard.with(getActivity())
                            .setTitle(names.get(position))
                            .setSubTitle(descs.get(position))
                            .useDrawableExternal(new MaterialLargeImageCard.DrawableExternal() {
                                @Override
                                public void setupInnerViewElements(ViewGroup viewGroup, View mview) {


                                    Drawable d = new BitmapDrawable(getResources(), imgs[position]);

                                    mview.setBackground(d);

                                }

                            }).build();


            cardView.setCard(card);

            return convertView;

        }

    }
}
