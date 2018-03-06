package com.rjp.baselistviewtest.bean;

import com.rjp.baselistview.pinned.PinnedModel;

import java.util.List;

/**
 * author : Gimpo create on 2018/3/2 15:17
 * email  : jimbo922@163.com
 */

public class ClassRoom extends PinnedModel<Student>{
    public ClassRoom(List<Student> children) {
        super(children);
    }
}
