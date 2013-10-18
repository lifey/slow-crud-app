package com.jclarity.had_one_dismissal.web;

import static java.lang.Integer.parseInt;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jclarity.had_one_dismissal.domain.JobListing;
import com.jclarity.had_one_dismissal.domain.PermanentAdverts;

@RequestMapping("/search/**")
@Controller
public class Search {

    private static final int NO_KNOWN_SALARY = -1;
    
    @Autowired private PermanentAdverts adverts;

    @RequestMapping(method = RequestMethod.POST)
    public String post(HttpServletRequest request, Model uiModel) {
        String salaryRange = request.getParameter("salaryRange");
        String keywords = request.getParameter("keywords");
        String location = request.getParameter("location");
        String jobTitle = request.getParameter("jobTitle");

        List<JobListing> listings = queryListings(salaryRange, keywords, location, jobTitle);
        uiModel.addAttribute("resultCount", listings.size());
        uiModel.addAttribute("results", listings);
        uiModel.addAttribute("advert", adverts.getAdvert());

        return "search/results";
    }

    private List<JobListing> queryListings(String salaryRange, String keywords, String location, String jobTitle) {
        StringBuilder queryString = new StringBuilder("SELECT o FROM JobListing AS o WHERE 1 = 1");
        addTitleQuery(jobTitle, queryString);
        addLocationQuery(location, queryString);
        addSalaryQuery(salaryRange, queryString);

        EntityManager em = JobListing.entityManager();
        TypedQuery<JobListing> query = em.createQuery(queryString.toString(), JobListing.class);
        setParameters(location, jobTitle, query);

        return query.getResultList();
    }

    private void setParameters(String location, String jobTitle, TypedQuery<JobListing> query) {
        if (hasParameter(jobTitle))
            query.setParameter("title", jobTitle);
        if (hasParameter(location))
            query.setParameter("location", location);
    }

    private void addLocationQuery(String location, StringBuilder queryString) {
        if (hasParameter(location))
            queryString.append(" and o.company.location.name LIKE :location");
    }

    private void addTitleQuery(String jobTitle, StringBuilder queryString) {
        if (hasParameter(jobTitle))
            queryString.append(" and LOWER(o.title) LIKE LOWER(:title)");
    }

    private void addSalaryQuery(String salaryRange, StringBuilder queryString) {
        if (!hasParameter(salaryRange))
            return;

        int salaryLowerBound = findSalaryLowerBound(salaryRange);
        int salaryUpperBound = findSalaryUpperBound(salaryRange);

        if (salaryUpperBound == NO_KNOWN_SALARY) {
            // there is only one salary value
            salaryUpperBound = salaryLowerBound;
        }

        queryString.append(" and o.salaryLowerBound <= " + salaryLowerBound + " and o.salaryUpperBound >= " + salaryUpperBound);
    }

    private int findSalaryLowerBound(String salaryRange) {
        String salary = salaryRange;

        if (salaryRange.contains("-")) {
            salary = salaryRange.substring(0, salaryRange.indexOf("-"));
        }

        return parseInt(salary.trim());
    }

    private int findSalaryUpperBound(String salaryRange) {
        if (!salaryRange.contains("-")) {
            return NO_KNOWN_SALARY;
        }

        String salary = salaryRange.substring(salaryRange.indexOf("-") + 1);
        return parseInt(salary);
    }

    private boolean hasParameter(String parameter) {
        return parameter != null && !parameter.isEmpty();
    }

    @RequestMapping
    public String index(Model uiModel) {
        uiModel.addAttribute("advert", adverts.getAdvert());

        return "search/index";
    }

}
