import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobScraper {

    private final List<Job> allJobs;

    private final Map<String, String> siteQueries;

    public JobScraper() {
        allJobs = new ArrayList<>();
        siteQueries = new HashMap<>();
        siteQueries.put("Indeed", "https://www.indeed.com/jobs?q=");
    }

    public List<Job> getAllJobs() {
        return this.allJobs;
    }
    //TODO: scrape multiple pages; add more websites; add different queries

    public void scrapeForJobs(String searchQuery, String site) {
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try {
            String searchUrl = siteQueries.get(site)
                    + URLEncoder.encode(searchQuery, StandardCharsets.UTF_8);
            HtmlPage page = client.getPage(searchUrl);

            List<HtmlElement> items = page.getByXPath(".//div[@class='jobsearch-SerpJobCard unifiedRow row result']");
            if (items.isEmpty()) {
                System.out.println("No items found.");
            } else {
                for (HtmlElement item : items) {
                    HtmlElement jobTitle = item.getFirstByXPath(".//h2[@class='title']");
                    HtmlElement spanCompany = item.getFirstByXPath(".//span[@class='company']");
                    HtmlElement jobSummary = item.getFirstByXPath(".//div[@class='summary']");
                    HtmlElement jobLocation = item.getFirstByXPath(".//span[@class='location accessible-contrast-color-location']");
                    HtmlElement jobPostDate = item.getFirstByXPath(".//span[@class='date ']");



                    String jobName = jobTitle == null ? "Couldn't get title" : jobTitle.asText();
                    //  String jobUrl = jobTitle.getHrefAttribute();

                    //String summaryStr = jobSummary.asText();
                    String jobCompany = spanCompany == null ? "No Company Name" : spanCompany.asText();
                    String locString = jobLocation == null ? "No Location Found" : jobLocation.asText();
                    String postDate = jobPostDate == null ? "Couldn't find date" : jobPostDate.asText();
                    Job webJob = new Job(jobName, jobCompany, locString, postDate);
                    allJobs.add(webJob);


                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }

}