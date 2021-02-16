import com.gargoylesoftware.htmlunit.html.HtmlElement;

import java.util.*;

public abstract class Scraper {

    private final List<Job> allJobs;
    private final String siteName;
    public final Map<String, String> siteQueries;

    public Scraper(String siteName) {
        this.siteName = siteName;
        allJobs = Collections.synchronizedList(new ArrayList<>());
        siteQueries = Collections.synchronizedMap(new HashMap<>());
        siteQueries.put("Indeed", "https://www.indeed.com/jobs?q=");
        siteQueries.put("SimplyHired", "https://www.simplyhired.com/search?q=");
    }

    public abstract void scrapeForJobs(String searchQuery, String site);

    public List<Job> getAllJobs() {
        return allJobs;
    }

    public abstract List<Job> getSiteJobs();

    public String getSiteName() {
        return siteName;
    }

    public void addJob(Job job) {
        allJobs.add(job);
    }

    public Job convertToStringReturnJob(HtmlElement jobTitle, HtmlElement jobCompany,
                                        HtmlElement jobLocation, HtmlElement jobPostDate, HtmlElement jobSummary) {
        String jobName = jobTitle == null ? "Couldn't get title" : jobTitle.asText();

        String summaryStr = jobSummary.asText();
        String company = jobCompany == null ? "No Company Name" : jobCompany.asText();
        String locString = jobLocation == null ? "No Location Found" : jobLocation.asText();
        String postDate = jobPostDate == null ? "Couldn't find date" : jobPostDate.asText();

        return new Job(jobName, company, locString, postDate, summaryStr);
    }
}
