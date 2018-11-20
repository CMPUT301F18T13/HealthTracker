package com.example.healthtracker;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by caochenlin on 2018/11/2.
 */


public class CareProviderCommentTest {
    private String comment;
    private Problem parentProblem;
    private CareProviderComment careGiverComment;
    private String title;

    @Before
    public void setUp() {
        title = "Rash";
        comment = "Get a rash after eating some seafood";
        parentProblem = new Problem("Parent", new Date(), "Description for the parent");
        careGiverComment = new CareProviderComment(title, comment,  parentProblem);
    }

    @Test
    public void getComment() {
        assertEquals(careGiverComment.getComment(), comment);
    }

    @Test
    public void setComment() {
        String newComment = "Rash is disappearing";
        careGiverComment.setComment(newComment);
        assertEquals(careGiverComment.getComment(), newComment);
    }

    @Test
    public void getTitle() {
        assertEquals(careGiverComment.getTitle(), title);

    }

    @Test
    public void setTitle() {
        String newTitle = "Rash2";
        careGiverComment.setTitle(newTitle);
        assertEquals(careGiverComment.getTitle(), newTitle);
    }
}



