package com.example.healthtracker;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by caochenlin on 2018/11/2.
 */
public class SimpleRecordTest {
    private String comment;
    private SimpleRecord simpleRecord;
    private String title;
    @Before
    public void setUp() {
        title = "Rash";
        comment = "Get a rash after eating some seafood";
        simpleRecord = new SimpleRecord(title,comment);
    }

    @Test
    public void getComment() {
        assertEquals(simpleRecord.getComment(),comment);
    }

    @Test
    public void setComment() {
        String newComment = "Rash is disappearing";
        simpleRecord.setComment(newComment);
        assertEquals(simpleRecord.getComment(),newComment);
    }

    @Test
    public void getTitle() {
        assertEquals(simpleRecord.getTitle(),title);

    }

    @Test
    public void setTitle() {
        String newTitle = "Rash2";
        simpleRecord.setTitle(newTitle);
        assertEquals(simpleRecord.getTitle(),newTitle);
    }


}