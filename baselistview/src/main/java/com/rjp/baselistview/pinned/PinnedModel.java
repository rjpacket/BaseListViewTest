package com.rjp.baselistview.pinned;

import java.io.Serializable;
import java.util.List;

public class PinnedModel<T> implements Serializable {
    public List<T> children;

    public PinnedModel(List<T> children) {
        this.children = children;
    }

    public List<T> getChildren() {
        return children;
    }
}
