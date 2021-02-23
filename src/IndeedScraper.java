import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class IndeedScraper extends Scraper {

    private final List<Job> indeedJobs;

    public IndeedScraper() {
        super("Indeed");
        indeedJobs = new ArrayList<>();
    }

    //returns a List of Jobs from Indeed.com matching criteria
    @Override
    public List<Job> getSiteJobs() {
        return indeedJobs;
    }

    /**
     * Purpose: Overrides abstract method from Scraper.java. Searches
     * the given website for job elements and stores them in indeedJobs.
     *
     * @param searchQuery is the user's search query.
     * @param site the website to be scraped.
     */
    @Override
    public void scrapeForJobs(String searchQuery, String site) {
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        //let's try to get 4 pages
        int pageCount = 0;
        while (pageCount < 45) {

            String searchPage = "&start=" + pageCount;
            String searchUrl = generateUrl(pageCount, searchPage, searchQuery, site);
            try {

                HtmlPage page = client.getPage(searchUrl);
                List<HtmlElement> items =
                        page.getByXPath(".//div[@class='jobsearch-SerpJobCard unifiedRow row result']");

                if (items.isEmpty()) {
                    System.out.println("No items found.");
                } else {
                    for (HtmlElement item : items) {
                        Job webJob = createJob(item);
                        indeedJobs.add(webJob);
                        addJob(webJob);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            pageCount += 10;
        }

    }

    private Job createJob(HtmlElement item) {

        HtmlElement jobTitle = item.getFirstByXPath(".//h2[@class='title']");
        HtmlElement jobCompany = item.getFirstByXPath(".//span[@class='company']");
        HtmlElement jobSummary = item.getFirstByXPath(".//div[@class='summary']");
        HtmlElement jobLocation =
                item.getFirstByXPath(".//span[@class='location accessible-contrast-color-location']");
        HtmlElement jobPostDate = item.getFirstByXPath(".//span[@class='date ']");

        return convertToStringReturnJob(jobTitle, jobCompany, jobLocation, jobPostDate, jobSummary);
    }

    private String generateUrl(int pageCount, String searchPage, String searchQuery, String site) {
        String searchUrl = "";
        if (pageCount < 10) {
            searchUrl = siteQueries.get(site)
                    + URLEncoder.encode(searchQuery, StandardCharsets.UTF_8);
        } else {
            searchUrl = siteQueries.get(site)
                    + URLEncoder.encode(searchQuery, StandardCharsets.UTF_8) + searchPage;
        }

        return searchUrl;
    }

}