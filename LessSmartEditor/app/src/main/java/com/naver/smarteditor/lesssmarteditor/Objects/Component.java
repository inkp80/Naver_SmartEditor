package com.naver.smarteditor.lesssmarteditor.Objects;

/**
 * Created by NAVER on 2017. 5. 17..
 */

public class Component {
    Object component;
    int componentType;

    public Component(Object component, int componentType) {
        this.component = component;
        this.componentType = componentType;
    }

    public Object getComponent() {
        return component;
    }

    public void setComponent(Object component) {
        this.component = component;
    }

    public int getComponentType() {
        return componentType;
    }

    public void setComponentType(int componentType) {
        this.componentType = componentType;
    }
}
