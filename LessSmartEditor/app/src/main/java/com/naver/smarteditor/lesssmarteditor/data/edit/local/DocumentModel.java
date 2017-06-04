package com.naver.smarteditor.lesssmarteditor.data.edit.local;

import com.naver.smarteditor.lesssmarteditor.LogController;
import com.naver.smarteditor.lesssmarteditor.data.DocumentParcelable;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.TextComponent;
import com.naver.smarteditor.lesssmarteditor.data.component.TitleComponent;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.swap;

/**
 * Created by NAVER on 2017. 6. 1..
 */

public class DocumentModel implements DocumentDataSource.LocalModel {
    private final String TAG = "DocumentModel";
    private boolean mLocalLogPermission = true;

    private List<BaseComponent> mComponents;
    private String mCurrentDocumentId;

    public DocumentModel(){
        mComponents = new ArrayList<>();
        mCurrentDocumentId = "New Document";
    }


    @Override
    public void addComponentToDocument(BaseComponent component) {
        mComponents.add(component);
    }

    @Override
    public void updateDocumentComponent(BaseComponent baseComponent, int position) {
        BaseComponent thisComponent = mComponents.get(position);
        thisComponent.updateData(baseComponent);
//        mComponents.set(position, baseComponent);
    }

    @Override
    public void deleteDocumentComponent(int position) {
        mComponents.remove(position);
    }

    @Override
    public void clearDocumentComponents() {
        mComponents.clear();
    }

    @Override
    public void initDocumentComponents(List<BaseComponent> components) {
        mComponents = components;

        LogController.makeLog("init size", String.valueOf(mComponents.size()), true);
        for(int i=0; i<components.size(); i++){
            BaseComponent.Type type = components.get(i).getComponentType();
            switch (type){
                case TITLE:
                    LogController.makeLog("init ", ((TitleComponent)mComponents.get(i)).getTitle(), true);
                    break;
                case TEXT:
                    LogController.makeLog("init ", ((TextComponent)mComponents.get(i)).getText(), true);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void convertParcelToComponents(DocumentParcelable documentParcelable) {
        //do Json things

    }

    @Override
    public void swapDocumentComponent(int from, int to) {

        //TODO : check if is it Title Component;
        if (from < to) {
            for (int i = from; i < to; i++) {
                swap(mComponents, i, i + 1);
            }
        } else {
            for (int i = from; i > to; i--) {
                swap(mComponents, i, i - 1);
            }
        }
    }

    @Override
    public List<BaseComponent> getComponents() {
        return mComponents;
    }


}
