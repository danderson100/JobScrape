import java.util.List;
import java.util.ArrayList;

public class Job {

    private String title;
    private String company;
    private String location;
    private String postDate;

    public Job(String title, String company, String location, String postDate) {
        this.title = title;
        this.company = company;
        this.location = location;
        this.postDate = postDate;
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

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        String[] fields = new String[4];
        fields[0] = "Title";
        fields[1] = "Company";
        fields[2] = "Location";
        fields[3] = "Date Posted";
        //FIXME make cleaner
        for (String field : fields) {
        }

        str.append("Title: ").append(title).append(" | ");
        str.append("Company: ").append(company).append(" | ");
        str.append("Location: ").append(location).append(" | ");
        str.append("Date Posted: ").append(postDate).append("\n");
        str.append("-----------------------------------------");

        return str.toString();
    }
}
