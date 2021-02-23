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

    /**
     * Purpose: A separate method for sorting by date posted. Had to separate
     * to account for unusual cases like posted "Today" or "Just Now" instead
     * of the usual number of days ago.
     *
     * @param unsortedJobs the list of jobs to be sorted.
     *
     * @return a LinkedList of sorted jobs.
     */
    //TODO: Clean this method up
    public List<Job> organizeByPosted(List<Job> unsortedJobs) {
        LinkedList<Job> sortedJobs = new LinkedList<>();
        Map<Integer, Job> sortedJobsMap = new TreeMap<>();
        for (Job job : unsortedJobs) {
            if (job.getPostDate().equals("Just posted") || job.getPostDate().equals("Today")) {
                sortedJobs.addFirst(job);
            } else if (job.getPostDate().matches("\\d+d")) {
                //organize by the number
                String[] splitPostStr = job.getPostDate().split("\\s+");
                String noPlusSign = splitPostStr[0].replaceAll("\\D", "");
                int number = -1;
                try {
                    number = Integer.parseInt(noPlusSign);
                } catch (NumberFormatException e) {
                    System.out.println("Error: " + e);
                }


                sortedJobsMap.put(number, job);
            } else {
                //no post date found
                sortedJobsMap.put(-1, job);
            }
        }

        for (Integer key : sortedJobsMap.keySet()) {
            sortedJobs.addLast(sortedJobsMap.get(key));
        }

        return sortedJobs;
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
