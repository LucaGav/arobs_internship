package com.arobs.internship.library.service;

import com.arobs.internship.library.business.impl.book.TagServiceImpl;
import com.arobs.internship.library.dao.TagDao;
import com.arobs.internship.library.dao.factory.DaoFactory;
import com.arobs.internship.library.dao.factory.hibernate.HibernateDaoFactory;
import com.arobs.internship.library.entities.book.Tag;
import com.arobs.internship.library.util.handler.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @InjectMocks
    private TagServiceImpl tagService;

    @Mock
    private DaoFactory daoFactory;

    @Mock
    private TagDao tagDao;

    @Mock
    private HibernateDaoFactory hibernateDaoFactory;

    @BeforeEach
    void setUp(){
        when(daoFactory.getInstance()).thenReturn(hibernateDaoFactory);
        when(daoFactory.getInstance().getTagDao()).thenReturn(tagDao);
        this.tagService.init();
    }

    @Test
    void whenInsertTag_givenTag_returnTag() throws ValidationException {
        Tag tagMock = new Tag(800,"Probom");
        when(tagDao.insert(any(Tag.class))).thenReturn(tagMock);
        Tag tag = tagService.insertTag(new Tag(800,"Probom"));
        assertEquals(tag,tagMock);
    }
}
