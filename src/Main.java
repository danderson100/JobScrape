import java.util.List;

/**
 * NOTES:
 *
 *
 *
 */

public class Main {

    public static void main(String[] args) {

        JobScraper scraper = new JobScraper();
        scraper.scrapeForJobs("Software Engineer Intern");
        List<Job> allJobs = scraper.getAllJobs();
        for (Job job : allJobs) {
            System.out.println(job.toString());
        }

    }
}
