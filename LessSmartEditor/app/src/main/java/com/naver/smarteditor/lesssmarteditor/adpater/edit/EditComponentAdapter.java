package com.naver.smarteditor.lesssmarteditor.adpater.edit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.naver.smarteditor.lesssmarteditor.LogController;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.ComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.TextComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.TitleComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.ViewHolderFactory;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnEditTextComponentChangeListener;
import com.naver.smarteditor.lesssmarteditor.listener.TextCursorListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class EditComponentAdapter extends RecyclerView.Adapter<ComponentViewHolder>
        implements EditComponentAdapterContract.Model, EditComponentAdapterContract.View {

    private final String TAG = "EditComponentAdapter";
    private boolean localLogPermission = true;

    private RequestManager glideImaRequestor;

    private ViewHolderFactory viewHolderFactory;
    private Context mContext;
    private List<BaseComponent> mComponents;
    private OnEditTextComponentChangeListener onEditTextComponentChangeListener;
    private TextCursorListener textCursorListener;

    private int mFocusingPosition = -1;


    public EditComponentAdapter(Context context) {
        this.mContext = context;
        mComponents = new ArrayList<>();
        glideImaRequestor = Glide.with(context);
    }

    public void setTextCursorChangeListener(TextCursorListener textCursorChangeListener){
        this.textCursorListener = textCursorChangeListener;

    }


    @Override
    public ComponentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO : viewHolderFactory 메모리 누수?
        viewHolderFactory = new ViewHolderFactory(mContext, glideImaRequestor, onEditTextComponentChangeListener, textCursorListener);

        BaseComponent.Type viewHolderType = BaseComponent.getType(viewType);
        return viewHolderFactory.createViewHolder(viewHolderType);
    }

    @Override
    public void onBindViewHolder(ComponentViewHolder holder, int position) {
        //TODO: 관련 없는 애들도 계속 배경을 그리고 있음
        holder.bindView(mComponents.get(position));
        holder.initFocusing(mFocusingPosition);
    }

    @Override
    public int getItemCount() {
        return mComponents.size();
    }

    public void setOnEditTextComponentChangeListener(OnEditTextComponentChangeListener onEditTextComponentChangeListener) {
        this.onEditTextComponentChangeListener = onEditTextComponentChangeListener;
    }


    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        BaseComponent thisComponent = mComponents.get(position);
        return thisComponent.getComponentType().getTypeValue();
    }


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
    //////////////////////////////////////////////////////////////////////////////////////////

    //Adapter View

    @Override
    public void notifyDataChange() {
        LogController.makeLog(TAG, "notifyadapter", localLogPermission);
        notifyDataSetChanged();
    }


    @Override
    public void onViewAttachedToWindow(ComponentViewHolder holder) {

        // ERROR MESSAGE : TextView does not support text selection. Action mode cancelled.
        // Bug workaround for losing text selection ability, see:
        // https://code.google.com/p/android/issues/detail?id=208169

        if(holder instanceof TextComponentViewHolder) {
            ((TextComponentViewHolder)holder).itemView.setEnabled(false);
            ((TextComponentViewHolder)holder).itemView.setEnabled(true);
        } else if (holder instanceof TitleComponentViewHolder){
            ((TitleComponentViewHolder)holder).itemView.setEnabled(false);
            ((TitleComponentViewHolder)holder).itemView.setEnabled(true);

        }
        super.onViewAttachedToWindow(holder);
    }

    public void clearFocus(){
        mFocusingPosition = -1;
//        notifyDataChange();
    }

    public void setFocusingPosition(int position){
        mFocusingPosition = position;
//        notifyDataChange();
    }

    public int getFocusingPosition() {
        return mFocusingPosition;
    }

}
