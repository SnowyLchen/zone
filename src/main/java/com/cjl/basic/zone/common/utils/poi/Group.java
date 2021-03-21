package com.cjl.basic.zone.common.utils.poi;

/**
 * @author huade_zhu
 */
public class Group {
    private String title;
    private Object object;

    public Group(String title, Object object) {
        this.title = title;
        this.object = object;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
