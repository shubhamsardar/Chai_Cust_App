package in.co.tripin.chahiyecustomer.Model.responce;

import in.co.tripin.chahiyecustomer.Model.WalletModel;
import in.co.tripin.chahiyecustomer.dataproviders.CommonResponse;

public class WalletResponse extends CommonResponse {
    WalletModel data;

    public WalletResponse(WalletModel data) {
        this.data = data;
    }

    public WalletModel getData() {
        return data;
    }

    public void setData(WalletModel data) {
        this.data = data;
    }
}
