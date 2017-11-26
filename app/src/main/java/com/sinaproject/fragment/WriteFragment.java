package com.sinaproject.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MultiImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sinaproject.R;
import com.sinaproject.activity.MainActivity;
import com.sinaproject.adapter.MyViewHolder;
import com.sinaproject.adapter.TemplateAdapter;
import com.sinaproject.util.DialogUtil;
import com.sinaproject.util.DoDialogView;
import com.sinaproject.util.GlideUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.valuesfeng.picker.Picker;
import io.valuesfeng.picker.engine.GlideEngine;

/**
 * Created by DeMon on 2017/11/4.
 */

public class WriteFragment extends Fragment {
    private static final String TAG = "WriteFragment";
    private static final int REQUEST_CODE_CHOOSE = 123;
    private static final int REQUESTCODE = 234;
    public static WriteFragment instance = null;
    @BindView(R.id.toolbar)
    TextView toolbar;
    Unbinder unbinder;
    @BindView(R.id.publish)
    TextView publish;
    @BindView(R.id.et_status)
    EditText etStatus;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.ib_exp)
    ImageButton ibExp;
    @BindView(R.id.ib_image)
    ImageButton ibImage;
    @BindView(R.id.rv_img)
    RecyclerView rvImg;
    @BindView(R.id.tv_ps)
    TextView tvPs;
    private int len;
    private String status;
    private List<String> imageList = new ArrayList<>();
    private ArrayList<Uri> uriList = new ArrayList<>();
    private TemplateAdapter<String> adapter;
    public static Handler handler;
    private boolean flag;
    private String imagePath;

    public static WriteFragment getInstance() {
        if (instance == null) {
            instance = new WriteFragment();
        }
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_write, null);
        unbinder = ButterKnife.bind(this, view);
        toolbar.setText("写微博");
        init();
        initHandler();
        return view;
    }


    private void init() {
        if (WbSdk.supportMultiImage(getActivity())) {
            flag = true;
            tvPs.setText("当前支持发表多张图片");
        } else {
            flag = false;
            tvPs.setText("当前只支持发表单张图片");
        }
        StaggeredGridLayoutManager sgm = new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL);
        rvImg.setLayoutManager(sgm);

        etStatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                len = s.length();
                tvNum.setText(len + "/140");
                if (len > 140) {
                    tvNum.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        adapter = new TemplateAdapter<String>(getActivity(), R.layout.list_write_image, imageList) {
            @Override
            public void convert(MyViewHolder holder, int position, final String s) {
                ImageView img = holder.getView(R.id.img);
                GlideUtil.setImage(getActivity(), s, img);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (flag) {
                            DialogUtil.doDialog(getContext(), "是否移除该图片？", new DoDialogView() {
                                @Override
                                public void yes() {
                                    imageList.remove(s);
                                    notifyDataSetChanged();
                                }
                            });
                        }
                    }
                });
            }
        };
        rvImg.setAdapter(adapter);
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x001) {
                    uriList.addAll(MainActivity.mSelected);
                    initImagePath();
                } else if (msg.what == 0x002) {
                    etStatus.setText("");
                    imageList.clear();
                    uriList.clear();
                    imagePath = null;
                    adapter.notifyDataSetChanged();
                }
            }

        };
    }

    private void initImagePath() {
        imageList.clear();
        if (flag) {
            for (int i = 0; i < uriList.size(); i++) {
                imageList.add(GlideUtil.getRealFilePath(getActivity(), uriList.get(i)));
            }
        } else {
            imageList.add(GlideUtil.getRealFilePath(getActivity(), MainActivity.mSelected.get(0)));
            imagePath = imageList.get(0);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.publish, R.id.ib_exp, R.id.ib_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.publish:
                if (len > 140) {
                    Toast.makeText(getActivity(), "文字字数不能超过140！", Toast.LENGTH_SHORT).show();
                    return;
                }
                status = etStatus.getText().toString();
                if (TextUtils.isEmpty(status) && TextUtils.isEmpty(imagePath) && imageList.size() == 0) {
                    Toast.makeText(getActivity(), "没有任何发表内容！", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendMultiMessage();
                break;
            case R.id.ib_exp:
                break;
            case R.id.ib_image:
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUESTCODE);
                    return;
                } else {
                    if (flag) {
                        if (imageList.size() == 9) {
                            Toast.makeText(getActivity(), "图片最多9张！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Picker.from(getActivity())
                                .count(9 - imageList.size())
                                .enableCamera(true)
                                .setEngine(new GlideEngine())
                                .forResult(REQUEST_CODE_CHOOSE);
                    } else {
                        Picker.from(getActivity())
                                .count(1)
                                .enableCamera(true)
                                .setEngine(new GlideEngine())
                                .forResult(REQUEST_CODE_CHOOSE);
                    }
                }
                break;
        }
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     */
    private void sendMultiMessage() {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (!TextUtils.isEmpty(status)) {
            weiboMessage.textObject = getTextObj();
        }
        if (flag && imageList.size() > 0) {
            weiboMessage.multiImageObject = getMultiImageObject();
        }

        if (!flag && !TextUtils.isEmpty(imagePath)) {
            weiboMessage.imageObject = getImageObj();

        }

        MainActivity.shareHandler.shareMessage(weiboMessage, false);

    }


    /**
     * 获取分享的文本模板。
     */
    private String getSharedText() {
        return status;
    }

    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = getSharedText();
        textObject.title = "xxxx";
        textObject.actionUrl = "http://www.baidu.com";
        return textObject;
    }

    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//此时返回bm为空
        imageObject.setImageObject(bitmap);
        return imageObject;
    }

    /***
     * 创建多图
     * @return
     */
    private MultiImageObject getMultiImageObject() {
        MultiImageObject multiImageObject = new MultiImageObject();
        //pathList设置的是本地本件的路径,并且是当前应用可以访问的路径，现在不支持网络路径（多图分享依靠微博最新版本的支持，所以当分享到低版本的微博应用时，多图分享失效
        // 可以通过WbSdk.hasSupportMultiImage 方法判断是否支持多图分享,h5分享微博暂时不支持多图）多图分享接入程序必须有文件读写权限，否则会造成分享失败
        ArrayList<Uri> pathList = new ArrayList<>();
        for (int i = 0; i < imageList.size(); i++) {
            pathList.add(Uri.fromFile(new File(imageList.get(i))));
        }
        multiImageObject.setImageList(pathList);
        return multiImageObject;
    }

}
