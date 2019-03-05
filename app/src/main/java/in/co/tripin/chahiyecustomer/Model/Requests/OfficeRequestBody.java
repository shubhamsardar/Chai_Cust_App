package in.co.tripin.chahiyecustomer.Model.Requests;

import java.util.List;

import in.co.tripin.chahiyecustomer.Model.OfficeDataModel;

public class OfficeRequestBody {


    List<OfficeDataModel> data;

    public OfficeRequestBody(List<OfficeDataModel> data) {
        this.data = data;
    }

    public List<OfficeDataModel> getData() {
        return data;
    }

    public void setData(List<OfficeDataModel> data) {
        this.data = data;
    }
}
