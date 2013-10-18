package com.jclarity.had_one_dismissal.domain;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/applicationContext.xml", "file:src/test/resources/webmvc-test-config.xml" })
public class PermanentAdvertsTest {

    @Autowired private PermanentAdverts object;

    @Test
    public void loadsDataFromFiles() throws Exception {
        assertTrue(object.getAdvert().endsWith("would love to hire you!"));
    }

}
