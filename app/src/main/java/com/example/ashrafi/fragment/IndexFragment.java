package com.example.ashrafi.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Ifta
 */

public class IndexFragment extends Fragment {
    public static final String TAG = "IndexFragmentTag";
    private static final int TOTAL_INDEX = 27;
    ListView lvIndex;
    ListView mainList = null;
    LinearLayoutManager llm = null;

    public IndexFragment() {

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.index_layout, container,false);
        lvIndex = (ListView) v.findViewById(R.id.lvIndex);
        handleIndex();
        return v;
    }

    public void setMainListReference(RecyclerView mainList) {
        this.llm = (LinearLayoutManager) mainList.getLayoutManager();
    }

    private void populateIndexMap(TreeMap<Double, Integer> map, int height) {
        double singleItemHeight = height / TOTAL_INDEX;
        double currentPosition = 0;
        int index = 0;
        while (currentPosition <= height) {
            map.put(currentPosition, index);
            map.put(currentPosition + singleItemHeight - 0.1, null);
            currentPosition += singleItemHeight;
            index++;
        }
    }

    private static <K, V> V mappedValue(TreeMap<K, V> map, K key) {
        Map.Entry<K, V> e = map.floorEntry(key);
        if (e != null && e.getValue() == null) {
            e = map.lowerEntry(key);
        }
        return e == null ? null : e.getValue();
    }

    public void setSectionCharToSectionStartMap(Map<String, Integer> sectionCharToSectionStartMap) {
        this.sectionCharToSectionStart = sectionCharToSectionStartMap;
    }

    public void setMainListReference(ListView list) {
        this.mainList = list;
//        I.log(mainList == null? "null mainlist ": "not null main list");
    }

    class IndexAdapter extends BaseAdapter {
        private static final int TOTAL_INDEX = 27;
        Context context;
        LayoutInflater inflater;
        private String[] indexes;

        private IndexAdapter(Context context) {
            indexes = new String[TOTAL_INDEX];
            for (int i = 0; i < TOTAL_INDEX - 1; i++) {
                indexes[i] = Character.toString((char) ('A' + i));
            }
            indexes[TOTAL_INDEX - 1] = "#";
            this.context = context;
            inflater = ((Activity) context).getLayoutInflater();
        }

        @Override
        public int getCount() {
            return TOTAL_INDEX;
        }

        @Override
        public Object getItem(int position) {
            return indexes[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            IndexAdapter.Holder holder;
            if (convertView == null) {
                v = inflater.inflate(R.layout.index_single_item, parent,false);
                holder = new IndexAdapter.Holder();
                holder.tvIndex = (TextView) v.findViewById(R.id.tvPhoneBookIndex);
                v.setTag(holder);
            } else {
                holder = (IndexAdapter.Holder) v.getTag();
            }
            holder.tvIndex.setText(indexes[position]);
            return v;
        }

        class Holder {
            TextView tvIndex;
        }
    }


    Map<String, Integer> sectionCharToSectionStart = new HashMap<>();
    int previousPosition = 0;

    private void handleIndex() {
        IndexAdapter adapter = new IndexAdapter(getContext());
        lvIndex.setAdapter(adapter);
        final int lvHeight = MeasureMentmanager.getListViewHeight(lvIndex);
        final TreeMap<Double, Integer> indexMap = new TreeMap<>();
        populateIndexMap(indexMap, lvHeight);
//        I.log("height = " + lvHeight);
        lvIndex.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                double y = event.getY();
                if (y > 0 && y <= lvHeight) {
                    Integer position = mappedValue(indexMap, y);
                    if (position != null) {
                        if (position != previousPosition) {
                            previousPosition = position;
                            String sectionChar = Character.toString((char) ('A' + position));
                            if (sectionChar.equals("[")) {
                                sectionChar = "#";
                            }
                            final Integer sectionStart = sectionCharToSectionStart.get(sectionChar);
                            if (sectionStart != null) {
//                                I.log(sectionChar);
//                                I.log("detecting " + (mainList == null) + " : "+sectionChar + " : "+sectionStart);
                                if (mainList == null) {
                                    llm.scrollToPositionWithOffset(sectionStart, 0);
                                } else {
                                    mainList.setSelection(sectionStart);
                                }
                            }
                        }
                    }
                }
                return true;
            }
        });
    }
}
