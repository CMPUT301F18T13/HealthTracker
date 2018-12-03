package com.example.healthtracker;

import com.example.healthtracker.EntityObjects.CareProviderComment;
import com.example.healthtracker.EntityObjects.Problem;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Created by caochenlin on 2018/11/2.
 */
public class CareProviderCommentTest {
    private String comment;
    private CareProviderComment careProviderComment;
    private String title;
    @Before
    public void setUp() {
        title = "Rash";
        comment = "Get a rash after eating some seafood";
        Problem parentProblem = new Problem();
        careProviderComment = new CareProviderComment(title, comment);
    }

    @Test
    public void getComment() {
        assertEquals(careProviderComment.getComment(),comment);
    }

    @Test
    public void setComment() {
        String newComment = "Rash is disappearing";
        careProviderComment.setComment(newComment);
        assertEquals(careProviderComment.getComment(),newComment);
    }

    @Test
    public void getTitle() {
        assertEquals(careProviderComment.getTitle(),title);

    }

    @Test
    public void setTitle() {
        String newTitle = "Rash2";
        careProviderComment.setTitle(newTitle);
        assertEquals(careProviderComment.getTitle(),newTitle);
    }

    @Test
    public void ToString(){
        assertEquals(careProviderComment.toString(),"Title: " + careProviderComment.getTitle() + "\n\nComment: " + careProviderComment.getComment());

    }

    @Test
    public void equals(){
        CareProviderComment careProviderComment_1 = new CareProviderComment("test", "test");
        assertEquals(careProviderComment.equals(careProviderComment),true) ;
        assertEquals(careProviderComment.equals(null),false);
        assertEquals(careProviderComment.equals(careProviderComment_1),false) ;
    }

}
