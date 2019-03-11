package in.co.tripin.chahiyecustomer.Model;

public class MenuItemModel {

    String  _id,name,rate,flag;

    public MenuItemModel(String _id, String name, String rate, String flag) {
        this._id = _id;
        this.name = name;
        this.rate = rate;
        this.flag = flag;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
