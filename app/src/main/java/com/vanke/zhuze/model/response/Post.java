package com.vanke.zhuze.model.response;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by chenfm01 on 16/1/14.
 */
public class Post implements Serializable {
    public int id;
//    public TagsResponse.Tag tag;
//    public Author author;
    public boolean is_recommend;
    public String title;
    public String content;
    public int up_count;
    public int comment_count;
    public boolean has_upped;
    public String created;
    public String last_replied;
    public ArrayList<String> images;

    @Override
    public boolean equals(Object o){
        Post post= (Post) o;
        return id==post.id;
    }
}
