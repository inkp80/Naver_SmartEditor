package com.naver.smarteditor.lesssmarteditor.data.edit.local;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.naver.smarteditor.lesssmarteditor.LogController;
import com.naver.smarteditor.lesssmarteditor.data.Document;
import com.naver.smarteditor.lesssmarteditor.data.component.BaseComponent;
import com.naver.smarteditor.lesssmarteditor.data.edit.local.utils.MyJsonDeserializer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NAVER on 2017. 5. 21..
 */

public class DocumentRepository implements DocumentDataSource.Repository{
    private final String TAG = "DocumentRepository";
    private boolean localLogPermission = true;

    private static DocumentRepository mEditComponentRepository;

    private DocumentLocalDataSource mEditComponentLocalDataSource;

    private DocumentModel mDocumentModel;


    ///////////
    private DocumentRepository(Context mContext){
        mEditComponentLocalDataSource = new DocumentLocalDataSource(mContext);
        mDocumentModel = new DocumentModel();
    }

    public static DocumentRepository getInstance(Context context){
        if(mEditComponentRepository == null){
            mEditComponentRepository = new DocumentRepository(context);
        }
        return mEditComponentRepository;
    } //Singleton


    //////////////////


    @Override
    public void updateDocument(DocumentDataSource.DatabaseUpdateCallback databaseUpdateCallback) {
        mEditComponentLocalDataSource.updateDocumentData(getCurrentDocumentComponents(), databaseUpdateCallback);
    }


    @Override
    public void deleteDocument(DocumentDataSource.DatabaseUpdateCallback databaseUpdateCallback) {

    }

    @Override
    public void createDocument(DocumentDataSource.DatabaseUpdateCallback databaseUpdateCallback) {

    }

    @Override
    public void readDocuments(DocumentDataSource.DatabaseReadCallback databaseReadCallback) {
        mEditComponentLocalDataSource.readDocumentData(databaseReadCallback);
    }

    @Override
    public void getDocumentById(final int documentId, final DocumentDataSource.DatabaseReadCallback databaseReadCallback) {

        mEditComponentLocalDataSource.readDocumentData(new DocumentDataSource.DatabaseReadCallback(){

            @Override
            public void OnSuccess(List<Document> documents) {
                List<Document> foundDocs = new ArrayList<>();
                for(Document document : documents) {
                    if (document.get_id() == documentId) {
                        foundDocs.add(document);
                    }
                }

                Document targetDoc = foundDocs.get(0);

                mDocumentModel.initDocumentComponents(getComponentsFromDocument(targetDoc));
            }

            @Override
            public void OnFail() {

            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




    @Override
    public void addComponent(BaseComponent component) {
        mDocumentModel.addComponentToDocument(component);
    }

    @Override
    public void updateComponent(BaseComponent baseComponent, int position) {
        mDocumentModel.updateDocumentComponent(baseComponent, position);
    }

    @Override
    public void deleteComponent(int position) {
        mDocumentModel.deleteDocumentComponent(position);
    }

    @Override
    public void initComponent(List<BaseComponent> components) {
        mDocumentModel.initDocumentComponents(components);
    }

    @Override
    public void swapComponent(int fromPosition, int toPosition) {
        mDocumentModel.swapDocumentComponent(fromPosition, toPosition);
    }

    @Override
    public void clearComponent() {
        mDocumentModel.clearDocumentComponents();
    }



    //Repository Utils
    public List<BaseComponent> getCurrentDocumentComponents(){
        return mDocumentModel.getComponents();
    }


    private List<BaseComponent> getComponentsFromDocument(Document targetDoc) {
        String componentsInJson = targetDoc.getComponentsJson();

        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(componentsInJson);
        } catch (Exception e){
            LogController.makeLog(TAG, "JSON Error : string to json-array", localLogPermission);
        }

        List<BaseComponent> components = new ArrayList<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(BaseComponent.class, new MyJsonDeserializer());
        Gson gson = gsonBuilder.create();

        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject object = jsonArray.getJSONObject(i);
                components.add(gson.fromJson(object.toString(), BaseComponent.class));
            } catch (Exception e) {
                LogController.makeLog(TAG, "FAIL : load components from json", localLogPermission);
            }
        }
        return components;
    }
}
