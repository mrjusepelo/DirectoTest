package co.devpenguin.directotest.options;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.List;

import co.devpenguin.directotest.R;
import co.devpenguin.directotest.adapters.AdapterProspectus;
import co.devpenguin.directotest.objects.Prospectus;

public class DashboardFragment extends Fragment {

    Context context;
    View v_fragment;
    AdapterProspectus adapter;
    RecyclerView mRecyclerView;
    EditText ed_search;
    Button btn_sing_up;
//    User user;
    ProgressBar pb_loading;
    private String device_token;
    String fb_email="", fb_id="", fb_name="", fb_image="", session_email, session_username,
            session_user_id, session_token, session_img_profile, session_json_user;
    boolean session_confirmed, session_receive_notifications;
    private static final String TOKEN_NULL = "";
    SharedPreferences settings;
    public SharedPreferences.Editor editor;
//    ArrayList<Book> books = new ArrayList<Book>();
    String tag = "BookLocalFragment";

    //SearchView srv_terms;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
//    public DashboardFragment(ArrayList<Book> books) {
//        this.books = books;
//    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivitiesWorkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        Log.i(tag, "oncrecteview");

        settings = context.getSharedPreferences("USER_DATA", 0);
        editor = settings.edit();

        session_token = settings.getString("TOKEN", TOKEN_NULL);
        session_email = settings.getString("EMAIL", TOKEN_NULL);
        session_username = settings.getString("USERNAME", TOKEN_NULL);
        session_confirmed = settings.getBoolean("CONFIRMED", false);
        session_receive_notifications = settings.getBoolean("NOTIFICATIONS", false);
        session_user_id = settings.getString("USERID", TOKEN_NULL);
        session_json_user = settings.getString("JSON_USER", TOKEN_NULL);



        v_fragment = inflater.inflate(R.layout.fragment_dashboard, container, false);

        init();
        return v_fragment;
    }

    private void init(){
        ed_search = v_fragment.findViewById(R.id.ed_search);

        List<Prospectus> prospectuses = Prospectus.find(Prospectus.class, "edited = ?", "1");
        if (prospectuses.size() > 0) {
            adapter = new AdapterProspectus(context, prospectuses);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, OrientationHelper.VERTICAL, false);
            mRecyclerView = (RecyclerView) v_fragment.findViewById(R.id.recyclerView);
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(adapter);

        }


        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0) {
                    adapter.filter(String.valueOf(s));
                }else{
                    adapter.filter("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }





    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context _context) {
        super.onAttach(_context);
        context = _context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
        context = getActivity();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
