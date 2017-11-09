package com.example.ashrafi.fragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.provider.ContactsContract;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by HP on 10/17/2017.
 */

// In this case, the fragment displays simple text based on the page
public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    //for recycler view creation in fragments

    private List<Contact> contactList = new ArrayList<>(); //for contact loading
    private List<Contact> callLog = new ArrayList<>(); //for call log
    private List<Contact> favorite = new ArrayList<>(); //for favorite contacts
    private RecyclerView recyclerView; //for contact loading
    private RecyclerView recyclerView2; //for call log
    private RecyclerView recyclerView3; //for favorite contacts
    private ContactsAdapter cAdapter; //for contact loading
    private ContactsAdapter newAdapter; //for call logs
    private ContactsAdapter favAdapter; //for favorite contacts
    Map<String, Integer> sectionCharToSectionStart = new HashMap<>();

    FrameLayout indexHolder;

    //for JSON work
    JSONObject jsonobject;
    JSONArray jsonarray;
    ListView listview;
    //ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    static String RANK = "rank";
    static String COUNTRY = "country";
    static String POPULATION = "population";
    static String FLAG = "flag";


    //to be used in createView
    View rootView;

    //for asynctask
    String mResult;
    URLFetchTask mTask;


    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().setContentView(R.layout.new_view);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*View view = inflater.inflate(R.layout.fragment_page, container, false); //kon view inflate korbe, eita ekhan theke fix kora
        TextView textView = (TextView) view;
        textView.setText("Hi, it's me Fragment #" + mPage + ":D");*/




        switch (getArguments().getInt(ARG_PAGE)) { //getting the argument which tab should be shown
            case 1: {
                rootView = inflater.inflate(R.layout.fragment1, container, false);





                recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

                cAdapter = new ContactsAdapter(contactList);
                //recyclerView.sethasFixedSize(true);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

                //creating LayoutManager
                //LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator()); //how can we add up animation here? //according to our own choice
                recyclerView.setAdapter(cAdapter);

                //prepareContactData();

                indexHolder = (FrameLayout) rootView.findViewById(R.id.indexFragmentHolderPhoneBook);
//                indexHolder.setVisibility(View.GONE);

                ContentResolver cr = getActivity().getApplicationContext().getContentResolver();
                Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER + "=?", new String[]{"1"}, null);

                cursor.moveToFirst(); //first e niye ashlam ekdom phone book er :D

                //String s = DatabaseUtils.dumpCursorToString(cursor);
                //Log.e("Phone data ",s);

                String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
                String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
                String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
                String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
                //String FAVORITE = ContactsContract.Contacts.STARRED;
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
                    //int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                    String hm = cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)); //1 or 0
                    String contact_id = cursor.getString(cursor.getColumnIndex(Phone_CONTACT_ID)); //ID number like 50/60
                    String number = cursor.getString(cursor.getColumnIndex(NUMBER)); //phone number :D

                    Contact movie = new Contact(name,number,contact_id);
                    contactList.add(movie);

                    //Log.e("contact name ",name);
                    //Contact contact = new Contact(name,hasPhoneNumber);
                    //contactList.add(contact);
                    //mAdapter.notifyDataSetChanged();
                }
                handleIndex();
                Collections.sort(contactList);
                populateSectionCharToSectionStartMap(contactList);

                if(indexFragment != null){
//                    indexHolder.setVisibility(View.VISIBLE);
                    indexFragment.setMainListReference(recyclerView);
                    indexFragment.setSectionCharToSectionStartMap(sectionCharToSectionStart);
                }

                cAdapter.notifyDataSetChanged();

                //this version can show static data in recycler view format in a fragment
                break;
            }

            case 2: {
                rootView = inflater.inflate(R.layout.fragment2, container, false);

                //creating recycler view
                //contactList.clear();
                recyclerView2 = (RecyclerView) rootView.findViewById(R.id.recycler_view_2);
                newAdapter = new ContactsAdapter(callLog);
                //recyclerView.sethasFixedSize(true);
                RecyclerView.LayoutManager cLayoutManager = new LinearLayoutManager(getActivity());

                //creating LayoutManager
                //LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                recyclerView2.setLayoutManager(cLayoutManager);
                recyclerView2.setItemAnimator(new DefaultItemAnimator()); //how can we add up animation here? //according to our own choice
                recyclerView2.setAdapter(newAdapter);


                //retrieving call-logs
                ContentResolver cr = getActivity().getApplicationContext().getContentResolver();

                Cursor cursor = cr.query( CallLog.Calls.CONTENT_URI,null, null,null, "date DESC");

                cursor.moveToFirst();

                while (cursor.moveToNext()) {

                    String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                    String type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
                    String date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                    String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                    String nameid = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));

                    //CallLog callLog = new CallLog(number,type,date,duration,nameid);
                    Contact contact = new Contact(number, type, duration);

                    callLog.add(contact);
                    newAdapter.notifyDataSetChanged();
                }

                break;
            }

            case 3: {
                rootView = inflater.inflate(R.layout.fragment3, container, false);
                break;
            }

        }

        return rootView;


    }

    private void populateSectionCharToSectionStartMap(List<Contact> contactList) {
        String x = "X";
        for(int i = 0; i < contactList.size(); i++){
            Contact contact = contactList.get(i);
            String sectionChar = contact.getName().substring(0,1).toUpperCase();
            if(sectionChar.equals(x) == false){
                sectionCharToSectionStart.put(sectionChar,i);
                x = sectionChar;
            }
        }

    }




    public IndexFragment indexFragment = null;
    private void handleIndex() {
        indexFragment = new IndexFragment();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.indexFragmentHolderPhoneBook, indexFragment, IndexFragment.TAG).commit();
    }

    private void prepareContactData() {
        Contact movie = new Contact("Mad Max: Fury Road", "Action & Adventure", "2015");
        contactList.add(movie);

        movie = new Contact("Inside Out", "Animation, Kids & Family", "2015");
        contactList.add(movie);

        movie = new Contact("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        contactList.add(movie);

        movie = new Contact("Shaun the Sheep", "Animation", "2015");
        contactList.add(movie);

        movie = new Contact("The Martian", "Science Fiction & Fantasy", "2015");
        contactList.add(movie);

        movie = new Contact("Mission: Impossible Rogue Nation", "Action", "2015");
        contactList.add(movie);

        movie = new Contact("Up", "Animation", "2009");
        contactList.add(movie);

        movie = new Contact("Star Trek", "Science Fiction", "2009");
        contactList.add(movie);

        movie = new Contact("The LEGO Movie", "Animation", "2014");
        contactList.add(movie);

        movie = new Contact("Iron Man", "Action & Adventure", "2008");
        contactList.add(movie);

        movie = new Contact("Aliens", "Science Fiction", "1986");
        contactList.add(movie);

        movie = new Contact("Chicken Run", "Animation", "2000");
        contactList.add(movie);

        movie = new Contact("Back to the Future", "Science Fiction", "1985");
        contactList.add(movie);

        movie = new Contact("Raiders of the Lost Ark", "Action & Adventure", "1981");
        contactList.add(movie);

        movie = new Contact("Goldfinger", "Action & Adventure", "1965");
        contactList.add(movie);

        movie = new Contact("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        contactList.add(movie);

        cAdapter.notifyDataSetChanged();
    }
}