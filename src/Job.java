
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

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }

    public String getPostDate() {
        return postDate;
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
