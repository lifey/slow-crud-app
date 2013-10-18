package com.jclarity.had_one_dismissal.domain;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.jclarity.had_one_dismissal.jmx.PerformanceProblems;

@Component
public class PermanentAdverts {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermanentAdverts.class);

    @Autowired private PerformanceProblems problems;

    private final Random random;

    private List<String> adverts;

    public PermanentAdverts() {
        random = new Random();
        adverts = Lists.newArrayList();
    }

    private void loadAdverts() {
        if (haveLoadedAdverts() && cacheEnabled())
            return;

        try {
            File ads = getFile("adverts.csv");
            File buffer = getFile("buffer.csv");
            Files.copy(ads, buffer);
            adverts = Files.readLines(buffer, Charset.defaultCharset());
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private File getFile(String name) throws URISyntaxException {
        return new File(PermanentAdverts.class.getResource(name).toURI());
    }

    private boolean cacheEnabled() {
        return problems.isSavingLoadedData();
    }

    private boolean haveLoadedAdverts() {
        return !adverts.isEmpty();
    }

    public String getAdvert() {
        loadAdverts();
        int index = random.nextInt(adverts.size());
        return adverts.get(index);
    }

}
