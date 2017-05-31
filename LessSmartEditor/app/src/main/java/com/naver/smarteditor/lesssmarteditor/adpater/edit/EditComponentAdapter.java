package com.naver.smarteditor.lesssmarteditor.adpater.edit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.naver.smarteditor.lesssmarteditor.MyApplication;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.ComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.ViewHolderFactory;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnComponentLongClickListener;
import com.naver.smarteditor.lesssmarteditor.listener.OnEditTextComponentChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class EditComponentAdapter extends RecyclerView.Adapter<ComponentViewHolder>
        implements EditComponentAdapterContract.Model, EditComponentAdapterContract.View,
        OnComponentLongClickListener {

    private final String TAG = "EditComponentAdapter";
    private boolean localLogPermission = true;

    private RequestManager requestManager;

    private ViewHolderFactory viewHolderFactory;
    private Context mContext;
    private OnEditTextComponentChangeListener onEditTextComponentChangeListener;
    private List<BaseComponent> mComponents;

    private OnComponentLongClickListener onComponentLongClickListener;


    public EditComponentAdapter(Context context) {
        this.mContext = context;
        mComponents = new ArrayList<>();
        requestManager = Glide.with(context);
    }

    @Override
    public ComponentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO : viewHolderFactory 메모리 누수?
        viewHolderFactory = new ViewHolderFactory(mContext, requestManager, onEditTextComponentChangeListener);
        BaseComponent.TypE ViewHolderType = BaseComponent.getType(viewType);
        return viewHolderFactory.createViewHolder(ViewHolderType);
    }

    @Override
    public void onBindViewHolder(ComponentViewHolder holder, int position) {
        holder.setDataPositionOnAdapter(position);
        holder.setOnComponentLongClickListener(this);
        holder.bindView(mComponents.get(position));
    }

    @Override
    public int getItemCount() {
        return mComponents.size();
    }

    public void setOnEditTextComponentChangeListener(OnEditTextComponentChangeListener onEditTextComponentChangeListener) {
        this.onEditTextComponentChangeListener = onEditTextComponentChangeListener;
    }


    @Override
    public void notifyDataChange() {
        MyApplication.LogController.makeLog(TAG, "notifyadapter", localLogPermission);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        BaseComponent thisComponent = mComponents.get(position);
        return thisComponent.getComponentType().getTypeValue();
    }


    //AdapterModel & View
    @Override
    public void initDocmentComponents(List<BaseComponent> components) {
        mComponents = new ArrayList<>(components);
    }

    @Override
    public void clearDocumentComponent() {
        mComponents.clear();
    }

    @Override
    public void swapDocumentComponent(int fromPosition, int toPosition) {
        notifyItemMoved(fromPosition, toPosition);
    }


    @Override
    public void setOnComponentLongClickListener(OnComponentLongClickListener onComponentLongClickListener){
        this.onComponentLongClickListener = onComponentLongClickListener;
    }

    @Override
    public void OnComponentLongClick(int position, View thisView) {
        onComponentLongClickListener.OnComponentLongClick(position, thisView);
    }



}
