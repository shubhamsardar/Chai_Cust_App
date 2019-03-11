package in.co.tripin.chahiyecustomer.Model.responce;

import in.co.tripin.chahiyecustomer.Model.TapriMenuModel;

public class TapriMenuResponses {

    TapriMenuModel data;

    public TapriMenuResponses(TapriMenuModel data) {
        this.data = data;
    }

    public TapriMenuModel getData() {
        return data;
    }

    public void setData(TapriMenuModel data) {
        this.data = data;
    }
}
