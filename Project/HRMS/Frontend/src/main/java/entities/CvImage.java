package entities;

public class CvImage {
    public int id;
    public String url;
    public String publicImageId;
    public String createdDate;

    public CvImage(int id,String url,String publicImageId,String createdDate) {
        this.id = id;
        this.url = url;
        this.publicImageId = publicImageId;
        this.createdDate = createdDate;
    }

}
