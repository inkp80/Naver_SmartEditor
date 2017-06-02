package com.naver.smarteditor.lesssmarteditor.adpater.edit;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.naver.smarteditor.lesssmarteditor.LogController;
import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.ComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.TextComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.ViewHolderFactory;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentFocusListener;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnEditTextComponentChangeListener;
import com.naver.smarteditor.lesssmarteditor.views.edit.ViewHolderToActivity;
import com.naver.smarteditor.lesssmarteditor.views.edit.ActivityToViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class EditComponentAdapter extends RecyclerView.Adapter<ComponentViewHolder>
        implements EditComponentAdapterContract.Model, EditComponentAdapterContract.View {

    private final String TAG = "EditComponentAdapter";
    private boolean localLogPermission = true;

    private RequestManager requestManager;

    private ViewHolderFactory viewHolderFactory;
    private Context mContext;
    private OnEditTextComponentChangeListener onEditTextComponentChangeListener;
    private List<BaseComponent> mComponents;

    private ComponentFocusListener componentFocusListener;

    private int mFocusedComponentPostion = -1;

    private ViewHolderToActivity viewHolderToActivity;
    private ActivityToViewHolder activityToViewHolder = new ActivityToViewHolder() {
        @Override
        public void clearFocus() {
            mFocusedComponentPostion = -1;
            notifyDataChange();
        }
    };

    public EditComponentAdapter(Context context) {
        this.mContext = context;
        mComponents = new ArrayList<>();
        requestManager = Glide.with(context);
        componentFocusListener = new ComponentFocusListener() {
            @Override
            public void OnComponentFocused(int position, Rect thisRect) {
                viewHolderToActivity.focusing(thisRect);
                mFocusedComponentPostion = position;
                notifyDataChange();
            }
        };
    }

    public void setV2A(ViewHolderToActivity viewHolderToActivity){
        this.viewHolderToActivity = viewHolderToActivity;
    }
    public ActivityToViewHolder getA2V(){
        return activityToViewHolder;
    }


    @Override
    public ComponentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO : viewHolderFactory 메모리 누수?
        //매번 new를 하고 있는데 좋은 방법이 아닌 거 같음..
        viewHolderFactory = new ViewHolderFactory(mContext, requestManager, onEditTextComponentChangeListener, componentFocusListener);

        BaseComponent.Type viewHolderType = BaseComponent.getType(viewType);
        return viewHolderFactory.createViewHolder(viewHolderType);
    }

    @Override
    public void onBindViewHolder(ComponentViewHolder holder, int position) {
        //TODO: 관련 없는 애들도 계속 배경을 그리고 있음 처리 하시오!
        holder.bindView(mComponents.get(position));
        if(position == mFocusedComponentPostion){
            holder.showHighlight();
        } else {
            holder.dismissHighlight();
        }
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
        }
        super.onViewAttachedToWindow(holder);
    }
}
