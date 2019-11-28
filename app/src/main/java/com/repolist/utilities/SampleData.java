package com.repolist.utilities;

import com.repolist.model.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SampleData {
    private static final String SAMPLE_NAME_1 = "A simple repository name";
    private static final String SAMPLE_DESCRIPTION_1 = "Description 1";
    private static final int SAMPLE_STARGAZERS_COUNT_1 = 0;
    private static final int SAMPLE_WATCHERS_COUNT_1 = 1;
    private static final String SAMPLE_LANGUAGE_1 = "Java";

    private static final String SAMPLE_NAME_2 = "A repo name with a\nline feed";
    private static final String SAMPLE_DESCRIPTION_2 = "Description 2";
    private static final int SAMPLE_STARGAZERS_COUNT_2 = 0;
    private static final int SAMPLE_WATCHERS_COUNT_2 = 0;
    private static final String SAMPLE_LANGUAGE_2 = "Javascript";

    private static final String SAMPLE_NAME_3 = "Repository 3";
    private static final String SAMPLE_DESCRIPTION_3 = "Description 3";
    private static final int SAMPLE_STARGAZERS_COUNT_3 = 10;
    private static final int SAMPLE_WATCHERS_COUNT_3 = 10;
    private static final String SAMPLE_LANGUAGE_3 = "Javascript";

    /*
    private static Date getDate(int diff) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.MILLISECOND, diff);
        return cal.getTime();
    }
    */

    public static List<Repository> getRepositories() {
        List<Repository> repositories = new ArrayList<>();
        repositories.add(new Repository(1, SAMPLE_NAME_1, SAMPLE_DESCRIPTION_1, SAMPLE_STARGAZERS_COUNT_1, SAMPLE_WATCHERS_COUNT_1, SAMPLE_LANGUAGE_1));
        repositories.add(new Repository(2, SAMPLE_NAME_2, SAMPLE_DESCRIPTION_2, SAMPLE_STARGAZERS_COUNT_2, SAMPLE_WATCHERS_COUNT_2, SAMPLE_LANGUAGE_2));
        repositories.add(new Repository(3, SAMPLE_NAME_3, SAMPLE_DESCRIPTION_3, SAMPLE_STARGAZERS_COUNT_3, SAMPLE_WATCHERS_COUNT_3, SAMPLE_LANGUAGE_3));
        return repositories;
    }
}