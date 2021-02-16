import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestCases {

    @Test
    public void dummyTest() {
        int x = 1;
        assertEquals(1, x);
    }

    @Test
    public void testScraper() {
        IndeedScraper scraper = new IndeedScraper();
        scraper.scrapeForJobs("Software Engineer Intern", "Indeed");
        List<Job> jobs = scraper.getAllJobs();
        assertNotNull(jobs);
    }
}
