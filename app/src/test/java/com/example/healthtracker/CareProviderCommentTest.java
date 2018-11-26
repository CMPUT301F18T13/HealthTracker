package com.example.healthtracker;

import com.example.healthtracker.EntityObjects.Problem;

import org.junit.Before;
import org.junit.Test;


import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by caochenlin on 2018/11/2.
 */
public class CareProviderCommentTest {
    private String comment;
    private Problem parentProblem;
    private CareProviderComment careProviderComment;
    private String title;
    @Before
    public void setUp() {
        title = "Rash";
        comment = "Get a rash after eating some seafood";
        parentProblem = new Problem("Problem", new Date(), "comment");
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


}