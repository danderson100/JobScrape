import java.util.*;

/**
 * Author: David Anderson
 * File: Main.java
 *
 * Purpose: This is the main class for the JobScrap application. It takes command-line arguments
 * for the company website the user would like to scrape, the search query, and how to organize them.
 *
 * JOB SITES COMMANDS: Indeed, SimplyHired (more coming soon)
 *
 * SEARCH QUERIES: "Software Engineer Intern" or any job you are searching for.
 *
 * ORGANIZATION COMMANDS: Location, Company, Posted
 *
 * USAGE: java Main companyName "Search Query" command
 *
 * USAGE EXAMPLE: java Main Indeed "Software Engineer Intern" Location
 * -- (this command would scrape Indeed.com for all jobs related to "Software Engineer Intern"
 * and it would organize them alphabetically by location).
 *
 */

public class Main {

    public static void main(String[] args) {
        String selectedCompany, searchQuery, orgMethod;
        selectedCompany = searchQuery = orgMethod = "";

        try {
            selectedCompany = args[0];
            searchQuery = args[1];
            orgMethod = args[2].toLowerCase();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You didn't provide enough arguments.");
        }

        Scraper scraper = null;

        switch (selectedCompany) {
            case "Indeed" -> scraper = new IndeedScraper();
            case "SimplyHired" -> scraper = new SimplyHiredScraper();
        }

        assert scraper != null;
        scraper.scrapeForJobs(searchQuery, selectedCompany);

        List<Job> jobs = scraper.getSiteJobs();

        Map<String, Job> sortedJobs = organize(orgMethod, jobs, scraper);

        printSortedJobs(sortedJobs);

    }

    /**
     * Purpose: This private helper method organizes the job postings
     * by whatever preference the user selected. Sorts in natural ordering,
     * alphabetical by location, company name, or numeric by date posted.
     *
     * @param orgMethod is the user's organization selection.
     * @param jobs is the list of scraped Jobs from the selected site.
     *
     * @return a TreeMap containing the jobs sorted in natural ordering.
     */
    private static Map<String, Job> organize(String orgMethod, List<Job> jobs, Scraper scraper) {
        //organize by location
        Map<String, Job> sortedJobs = new TreeMap<>();
        switch (orgMethod) {
            case "location" -> {
                for (Job job : jobs) {
                    sortedJobs.put(job.getLocation(), job);
                }
            }
            case "company" -> {
                for (Job job : jobs) {
                    sortedJobs.put(job.getCompany(), job);
                }
            }
            case "posted" -> {
                List<Job> sortedByPosted = organizeByPosted(jobs, scraper);
                for (Job job : sortedByPosted) {
                    System.out.println(job.toString());
                }
            }
        }

        return sortedJobs;
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
    private static List<Job> organizeByPosted(List<Job> unsortedJobs, Scraper scraper) {
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

    /**
     * Purpose: This iterates over the Job objects and prints them out
     * using the job's toString method.
     *
     * @param sortedJobs the sorted jobs to be printed to Standard Output.
     */
    private static void printSortedJobs(Map<String, Job> sortedJobs) {

        for (String str : sortedJobs.keySet()) {
            Job job = sortedJobs.get(str);
            System.out.println(job.toString());
        }

    }
}
