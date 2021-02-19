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
        Scraper scraper = null;

        switch (selectedCompany) {
            case "Indeed" -> scraper = new IndeedScraper();
            case "SimplyHired" -> scraper = new SimplyHiredScraper();
        }

        assert scraper != null;
        scraper.scrapeForJobs(searchQuery, selectedCompany);

        List<Job> jobs = scraper.getSiteJobs();

//        Scraper anotherScraper = new IndeedScraper();
//        anotherScraper.scrapeForJobs(searchQuery, "Indeed");
//        List<Job> otherJobs = anotherScraper.getSiteJobs();
//        Map<String, Job> moreSorted = organize(orgMethod, otherJobs);
//        printSortedJobs(moreSorted);


        Map<String, Job> sortedJobs = organize(orgMethod, jobs);

        printSortedJobs(sortedJobs);

    }

    private static Map<String, Job> organize(String orgMethod, List<Job> jobs) {
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
                List<Job> sortedByPosted = organizeByPosted(jobs);
                for (Job job : sortedByPosted) {
                    System.out.println(job.toString());
                }
            }
        }

        return sortedJobs;
    }
    //TODO: Clean this method up
    private static List<Job> organizeByPosted(List<Job> unsortedJobs) {
        LinkedList<Job> sortedJobs = new LinkedList<>();
        Map<Integer, Job> sortedJobsMap = new TreeMap<>();
        for (Job job : unsortedJobs) {
            if (job.getPostDate().equals("Just posted") || job.getPostDate().equals("Today")) {
                sortedJobs.addFirst(job);
            } else {
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
            }
        }

        for (Integer key : sortedJobsMap.keySet()) {
            sortedJobs.addLast(sortedJobsMap.get(key));
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
