package divyanshu.approximation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Divyanshu on 1/18/2017.
 */

public class MyBottomSheetDialogFragment extends BottomSheetDialogFragment {

    static MyBottomSheetDialogFragment newInstance(List<Double> roots, String data) {
        MyBottomSheetDialogFragment f = new MyBottomSheetDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("root_list", (Serializable) roots);
        args.putString("data", data);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.data_sheet, container, false);
        List<Double> mRoots = (List<Double>) getArguments().getSerializable("root_list");
        String mData = getArguments().getString("data");
        TextView tv_data = (TextView) view.findViewById(R.id.tv_data);
        tv_data.setText(mData);
        ListView lv_roots = (ListView) view.findViewById(R.id.lv_roots);
        ArrayAdapter<Double> arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,mRoots);
        lv_roots.setAdapter(arrayAdapter);
        return view;
    }
}