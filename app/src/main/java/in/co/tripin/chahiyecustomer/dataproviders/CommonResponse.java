package in.co.tripin.chahiyecustomer.dataproviders;


import in.co.tripin.chahiyecustomer.factory.Response;


public abstract class CommonResponse implements Response {
    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}