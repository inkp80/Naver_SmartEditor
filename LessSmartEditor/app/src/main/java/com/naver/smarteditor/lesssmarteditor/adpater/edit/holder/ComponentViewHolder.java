package com.naver.smarteditor.lesssmarteditor.adpater.edit.holder;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.naver.smarteditor.lesssmarteditor.adpater.basic.holder.BasicViewHolder;
import com.naver.smarteditor.lesssmarteditor.listener.OnComponentMenuClickListener;

import static com.naver.smarteditor.lesssmarteditor.MyApplication.COMPONENT_MENU_CANCEL;
import static com.naver.smarteditor.lesssmarteditor.MyApplication.COMPONENT_MENU_DELETE;

/**
 * Created by NAVER on 2017. 5. 29..
 */

abstract public class ComponentViewHolder extends BasicViewHolder implements View.OnCreateContextMenuListener{

    public OnComponentMenuClickListener onComponentMenuClickListener;

    public ComponentViewHolder(View itemView) {
        super(itemView);
        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public int getDataPositionOnAdapter() {
        return 0;
    }

    @Override
    public void setDataPositionOnAdapter(int position) {

    }
    abstract public void setOnComponentContextMenuClickListener(OnComponentMenuClickListener onComponentContextMenuClickListener);

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("컴포넌트 설정");
        MenuItem delete = menu.add(Menu.NONE, COMPONENT_MENU_DELETE, 0, "삭제");
        MenuItem cancel = menu.add(Menu.NONE, COMPONENT_MENU_CANCEL, 1, "취소");
        delete.setOnMenuItemClickListener(onEditMenu);
    }

    private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()) {
                case COMPONENT_MENU_DELETE:
                    onComponentMenuClickListener.OnComponentMenuClick(getDataPositionOnAdapter());
                    break;

                case COMPONENT_MENU_CANCEL:
                    break;
            }
            return true;
        }
    };
}
