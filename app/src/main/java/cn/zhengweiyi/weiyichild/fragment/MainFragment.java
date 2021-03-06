package cn.zhengweiyi.weiyichild.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.bravin.btoast.BToast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.zhengweiyi.weiyichild.DietaryActivity;
import cn.zhengweiyi.weiyichild.GridAdapter;
import cn.zhengweiyi.weiyichild.MyApplication;
import cn.zhengweiyi.weiyichild.PickupActivity;
import cn.zhengweiyi.weiyichild.bean.GridBean;
import cn.zhengweiyi.weiyichild.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements AdapterView.OnItemClickListener {
    private static final String ARG_SCREEN_WIDTH = "screenWidth";
    private static final String ARG_SCREEN_DENSITY = "screenDensity";

    // TODO: Rename and change types of parameters
    private int mScreenWidth;   // 屏幕宽度
    private float mDensity;     // 屏幕密度
    private GridView gridView;
    private List<GridBean> gridDataList;
    private GridBean gridBean;
    private GridAdapter gridAdapter;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // Log.d("getArguments()方法的值为", String.valueOf(getArguments()));
        if (getArguments() != null) {
            mScreenWidth = getArguments().getInt(ARG_SCREEN_WIDTH);
            mDensity = getArguments().getFloat(ARG_SCREEN_DENSITY);
            Log.d("获取到的屏幕宽度和密度分别是 ", "宽: " + String.valueOf(mScreenWidth) + "px, 密度：" + String.valueOf(mDensity));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /* 设置功能按钮GridView */
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = view.findViewById(R.id.gridView);
        gridDataList = new ArrayList<>();
        // 初始化数据
        initData();
        // 实例化适配器
        gridAdapter = new GridAdapter(gridDataList, getContext());
        // 为GridView设置适配器
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(this);
        // Log.d("屏幕的宽度为", String.valueOf(mScreenWidth));
        Log.d("功能按钮列宽为：", String.valueOf((mScreenWidth - 40 * mDensity) / 3));
        gridView.setColumnWidth((int) ((mScreenWidth - 40 * mDensity) / 3));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 功能按钮 GridView item 的点击事件
     *
     * @param position item 的位置
     * @param id       item 的编号
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                Intent intentPickup = new Intent(getActivity(), PickupActivity.class);
                startActivity(intentPickup);
                break;
            case 1:
                Intent intentDietary = new Intent(getActivity(), DietaryActivity.class);
                startActivity(intentDietary);
                break;
            case 2:
                BToast.normal(view.getContext())
                        .animate(true).animationGravity(BToast.ANIMATION_GRAVITY_TOP)
                        .duration(BToast.DURATION_SHORT)
                        .target(view).layoutGravity(BToast.LAYOUT_GRAVITY_BOTTOM).tag(MyApplication.CLICK_MESSAGE)
                        .text(getResources().getString(R.string.click) + " " + getResources().getString(R.string.function_manage_info))
                        .show();
                // Toast.makeText(getActivity(), "点击了信息管理", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                BToast.normal(view.getContext())
                        .animate(true).animationGravity(BToast.ANIMATION_GRAVITY_TOP)
                        .duration(BToast.DURATION_SHORT)
                        .target(view).layoutGravity(BToast.LAYOUT_GRAVITY_BOTTOM).tag(MyApplication.CLICK_MESSAGE)
                        .text(getResources().getString(R.string.click) + " " + getResources().getString(R.string.function_manage_class))
                        .show();
                // Toast.makeText(getActivity(), "点击了班级管理", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                BToast.info(view.getContext())
                        .animate(true).animationGravity(BToast.ANIMATION_GRAVITY_TOP)
                        .duration(BToast.DURATION_SHORT)
                        .target(parent.getChildAt(position)).layoutGravity(BToast.LAYOUT_GRAVITY_BOTTOM).tag(MyApplication.CLICK_MESSAGE)
                        .text(R.string.coming_soon)
                        .show();
                break;
            default:
                break;
        }
    }

    /**
     * 设置 GridView 显示数据
     */
    private void initData() {
        // 图标
        int icon[] = {R.drawable.ic_function_pickup, R.drawable.ic_function_dietary,
                R.drawable.ic_function_manage_info, R.drawable.ic_function_manage_class,
                R.drawable.ic_function_other};
        // 功能名字
        int name[] = {R.string.function_pickup, R.string.function_dietary,
                R.string.function_manage_info, R.string.function_manage_class,
                R.string.function_other};
        for (int i = 0; i < icon.length; i++) {
            gridBean = new GridBean(icon[i], getResources().getString(name[i]));
            gridDataList.add(gridBean);
        }
    }
}
