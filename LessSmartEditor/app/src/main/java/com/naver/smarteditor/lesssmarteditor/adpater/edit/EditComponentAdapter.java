package com.naver.smarteditor.lesssmarteditor.adpater.edit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.naver.smarteditor.lesssmarteditor.LogController;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.ComponentViewHolder;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.holder.ViewHolderFactory;
import com.naver.smarteditor.lesssmarteditor.adpater.edit.util.ComponentFocusListener;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.listener.OnEditTextComponentChangeListener;

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


    public EditComponentAdapter(Context context) {
        this.mContext = context;
        mComponents = new ArrayList<>();
        requestManager = Glide.with(context);
        componentFocusListener = new ComponentFocusListener() {
            @Override
            public void OnComponentFocused(int position) {
                mFocusedComponentPostion = position;
                notifyDataChange();
            }
        };
    }


    @Override
    public ComponentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //TODO : viewHolderFactory 메모리 누수?
        viewHolderFactory = new ViewHolderFactory(mContext, requestManager, onEditTextComponentChangeListener, componentFocusListener);
        BaseComponent.Type viewHolderType = BaseComponent.getType(viewType);
        return viewHolderFactory.createViewHolder(viewHolderType);
    }

    @Override
    public void onBindViewHolder(ComponentViewHolder holder, int position) {
        holder.bindView(mComponents.get(position));
        if(position == mFocusedComponentPostion){
            holder.setHighlight();
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




    //Adatper Model
    @Override
    public void addDocumentComponent(BaseComponent.Type type, BaseComponent baseComponent) {

    }

    @Override
    public void initDocmentComponents(List<BaseComponent> components) {
        mComponents = new ArrayList<>(components);
    }

    @Override
    public void deleteDocumentComponent(int postion) {

    }

    @Override
    public void updateDocumentComponent(int position, BaseComponent baseComponent) {

    }

    @Override
    public List<BaseComponent> printOutDocument() {
        return null;
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
}
