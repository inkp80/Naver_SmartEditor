package com.naver.smarteditor.lesssmarteditor.adpater.edit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.TextComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.data.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.TextComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnTextChangeListener;

import java.util.ArrayList;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class EditComponentAdapter extends RecyclerView.Adapter<BasicViewHolder> implements EditComponentAdapterContract.Model, EditComponentAdapterContract.View{

    int idx = 0;

    private final String TAG = "EditComponentAdapter";
    private boolean localLogPermission = true;

    private final int TEXT_COMPONENT = 0;
    private final int IMG_COMPONENT = 1;
    private final int MAP_COMPONENT = 2;


    private Context mContext;
    private OnTextChangeListener onTextChangeListener;
    private ArrayList<BaseComponent> mComponents;

    public EditComponentAdapter(Context context){
        this.mContext = context;
        mComponents = new ArrayList<>();
    }

    @Override
    public BasicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if(viewType == TEXT_COMPONENT){
            EditText et = new EditText(mContext);
            et.setLayoutParams(lp);
            TextComponentViewHolder textComponentViewHolder = new TextComponentViewHolder(et, onTextChangeListener);
            return textComponentViewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(BasicViewHolder holder, int position) {

        int thisComponentType = mComponents.get(position).getComponentType().getTypeValue();

        switch (thisComponentType){
            case TEXT_COMPONENT :
                TextComponent thisTextComponent = (TextComponent) mComponents.get(position);

                TextComponentViewHolder textComponentViewHolder = (TextComponentViewHolder) holder;
                textComponentViewHolder.removeWatcher();
                textComponentViewHolder.setText(thisTextComponent.getText());
                textComponentViewHolder.onBind(position);
                break;
            case IMG_COMPONENT :
                break;
            case MAP_COMPONENT :
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mComponents.size();
    }

    @Override
    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        this.onTextChangeListener = onTextChangeListener;
    }

    @Override
    public void notifyAdapter() {
        MyApplication.LogController.makeLog(TAG, "notifyadapter", localLogPermission);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        int componentType = mComponents.get(position).getComponentType().getTypeValue();
        switch (componentType){
            case TEXT_COMPONENT:
                return TEXT_COMPONENT;
            case IMG_COMPONENT:
                return IMG_COMPONENT;
            case MAP_COMPONENT:
                return MAP_COMPONENT;
            default:
                //throw Exception E
                break;
        }
        //TODO: 에러 / 예외 처리 하기
        return -1;
    }

    @Override
    public void setComponent(ArrayList<BaseComponent> components) {
        mComponents = new ArrayList<>(components);
    }

    @Override
    public void editComponent(CharSequence s, int position) {
    }

    @Override
    public BaseComponent getComponent(int position) {
        return mComponents.get(position);
    }

    @Override
    public void clearComponent() {
        mComponents.clear();
    }
}
