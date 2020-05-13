package commons;

public class IDRequested {
    private String id;
    private String status;

    public IDRequested(String id, String status){
        super();
        this.id = id;
        this.status = status;
    }

    @Override
    public String toString(){
        return id + ", " + status;
    }

    public String getId(){
        return id;
    }

    public String getStatus(){
        return status;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
