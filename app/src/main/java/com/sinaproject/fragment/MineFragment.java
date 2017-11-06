package com.sinaproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sinaproject.R;
import com.sinaproject.activity.StartActivity;
import com.sinaproject.contract.MineContract;
import com.sinaproject.contract.Presenter.MinePresenter;
import com.sinaproject.data.SinaInfo;
import com.sinaproject.data.UserInfo;
import com.sinaproject.util.GlideUtil;
import com.sinaproject.util.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DeMon on 2017/11/4.
 */

public class MineFragment extends Fragment implements MineContract.View {
    private static final String TAG = "MineFragment";
    public static MineFragment instance = null;
    @BindView(R.id.img_icon)
    CircleImageView imgIcon;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_introduction)
    TextView tvIntroduction;
    @BindView(R.id.tv_weibo)
    TextView tvWeibo;
    @BindView(R.id.tv_care)
    TextView tvCare;
    @BindView(R.id.tv_fans)
    TextView tvFans;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_exit)
    Button btnExit;
    @BindView(R.id.toolbar)
    TextView toolbar;
    Unbinder unbinder;
    private MinePresenter minePresenter;
    private SinaInfo sinaInfo;

    public static MineFragment getInstance() {
        if (instance == null) {
            instance = new MineFragment();
        }
        return instance;
    }

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, null);
        unbinder = ButterKnife.bind(this, view);
        toolbar.setText("我");
        init();
        return view;
    }

    private void init() {
        minePresenter = new MinePresenter(getActivity(), this);
        sinaInfo = SinaInfo.getSinaInfo();
        minePresenter.getUserInfo(sinaInfo.getAccess_token(), sinaInfo.getUid());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_weibo, R.id.tv_care, R.id.tv_fans, R.id.btn_cancel, R.id.btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_weibo:
                break;
            case R.id.tv_care:
                break;
            case R.id.tv_fans:
                break;
            case R.id.btn_cancel://注销帐号
                minePresenter.revokeToken(sinaInfo.getAccess_token());
                break;
            case R.id.btn_exit:
                System.exit(0);
                break;
        }
    }

    @Override
    public void result(UserInfo info) {
        GlideUtil.setImage(getActivity(), info.getAvatar_hd(), imgIcon);
        tvName.setText(info.getScreen_name());
        tvIntroduction.setText(info.getDescription());
        tvWeibo.setText("微博:" + info.getStatuses_count());
        tvCare.setText("关注:" + info.getFriends_count());
        tvFans.setText("粉丝:" + info.getFollowers_count());
    }

    @Override
    public void revoke(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            String result = jsonObject.getString("result");
            if (result.equals("true")) {
                SPUtil.clear(getActivity());
                startActivity(new Intent(getActivity(), StartActivity.class));
                getActivity().finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
