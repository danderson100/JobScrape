import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Author: David Anderson
 * File: Main.java
 *
 * Purpose: This is the main class for the JobScrap application. It takes command-line arguments
 * for the company website the user would like to scrape, the search query, and how to organize them.
 *
 * JOB SITES COMMANDS: Indeed (more coming soon)
 *
 * SEARCH QUERIES: "Software Engineer Intern" (more coming soon)
 *
 * ORGANIZATION COMMANDS: Location, Company, Posted
 *
 * USAGE EXAMPLE: java Main Indeed "Software Engineer Intern" Location  -- (this command would scrape Indeed.com for all jobs
 * related to "Software Engineer Intern" and it would organize them alphabetically by location).
 *
 *
 *
 */

public class Main {

    public static void main(String[] args) {
        String selectedCompany = args[0];

        String searchQuery = args[1];

        String orgMethod = args[2].toLowerCase();

        JobScraper scraper = new JobScraper();
        scraper.scrapeForJobs(searchQuery, selectedCompany);
        List<Job> jobs = scraper.getAllJobs();

        Map<String, Job> sortedJobs = null;

        if (orgMethod.equals("location")) {
            sortedJobs = organize(orgMethod, jobs);
        }

        assert sortedJobs != null;
        printSortedJobs(sortedJobs);

    }

    private static Map<String, Job> organize(String orgMethod, List<Job> jobs) {
        //organize by location
        Map<String, Job> sortedJobs = new TreeMap<>();

        if (orgMethod.equals("location")) {
            for (Job job : jobs) {
                sortedJobs.put(job.getLocation(), job);
            }
        }
        return sortedJobs;
    }

    private static void printSortedJobs(Map<String, Job> sortedJobs) {

        for (String str : sortedJobs.keySet()) {
            Job job = sortedJobs.get(str);
            System.out.println(job.toString());
        }

    }
}
