import java.util.List;
import java.util.ArrayList;

public class Job {

    private String title;
    private String company;
    private String location;
    private String postDate;
    private String summary;

    public Job(String title, String company, String location, String postDate, String summary) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.postDate = postDate;
        this.summary = summary;
    }

    public Job() {

    }


    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostDate() {
        return postDate;
    }

    public String getSummary() {
        return summary;
    }

    @Override
    public String toString() {

        return "Title: " + title + " | " +
                "Company: " + company + " | " +
                "Location: " + location + " | " +
                "Date Posted: " + postDate + "\n" +
                "-----------------------------------------";
    }
}
