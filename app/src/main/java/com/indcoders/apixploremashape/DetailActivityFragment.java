package com.indcoders.apixploremashape;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    private static final String name = "param1";
    private static final String author = "param2";
    private static final String desc = "param3";
    private static final String apiThumb = "param4";
    private static final String authorThumb = "param5";
    private static final String price = "param6";
    private static final String link = "param7";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    private String mParam5;
    private String mParam6;
    private String mParam7;

    ImageView ivPoster, ivAuthor;

    Bitmap[] imgs;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param title        Parameter 1.
     * @param mauthor      Parameter 2.
     * @param mdesc        Parameter 3.
     * @param mapiThumb    Parameter 4.
     * @param mauthorThumb Parameter 5.
     * @param mPrice       Parameter 6.
     * @param mLink        Parameter 7.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailActivityFragment newInstance(String title, String mauthor, String mdesc, String mapiThumb, String mauthorThumb, String mPrice, String mLink) {
        DetailActivityFragment fragment = new DetailActivityFragment();
        Bundle args = new Bundle();
        args.putString(name, title);
        args.putString(author, mauthor);
        args.putString(desc, mdesc);
        args.putString(apiThumb, mapiThumb);
        args.putString(authorThumb, mauthorThumb);
        args.putString(price, mPrice);
        args.putString(link, mLink);

        fragment.setArguments(args);
        return fragment;
    }

    public DetailActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(name);
            mParam2 = getArguments().getString(author);
            mParam3 = getArguments().getString(desc);
            mParam4 = getArguments().getString(apiThumb);
            mParam5 = getArguments().getString(authorThumb);
            mParam6 = getArguments().getString(price);
            mParam7 = getArguments().getString(link);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        ivPoster = (ImageView) v.findViewById(R.id.ivPosterDetails);
        TextView tvTitle = (TextView) v.findViewById(R.id.tvPosterLabel);
        ivAuthor = (ImageView) v.findViewById(R.id.ivAuthor);
        TextView tvAuthor = (TextView) v.findViewById(R.id.tvAuthor);
        TextView tvDesc = (TextView) v.findViewById(R.id.description_data);
        TextView tvPrice = (TextView) v.findViewById(R.id.tvPrice);


        tvTitle.setText(mParam1);
        tvAuthor.setText("Owner : " + mParam2);
        tvDesc.setText(mParam3);
        tvPrice.setText("Price : " + mParam6);

        new LoadPics().execute();

        return v;

    }

    public class LoadPics extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... params) {

            imgs = new Bitmap[2];
            try {
                imgs[0] = Picasso.with(getActivity()).load(mParam4).get();
                imgs[1] = Picasso.with(getActivity()).load(mParam5).get();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Error: ", e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ivPoster.setImageBitmap(imgs[0]);
            ivAuthor.setImageBitmap(imgs[1]);
        }
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

}
