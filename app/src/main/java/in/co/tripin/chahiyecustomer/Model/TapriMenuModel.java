package in.co.tripin.chahiyecustomer.Model;

import java.util.List;

public class TapriMenuModel {

    List<MenuItemModel> Snacks;
    List<MenuItemModel> Beverages;
    List<MenuItemModel> Extra;
    List<MenuItemModel> Chaihiyeh;

    public TapriMenuModel(List<MenuItemModel> snacks, List<MenuItemModel> beverages, List<MenuItemModel> extra, List<MenuItemModel> chaihiyeh) {
        Snacks = snacks;
        Beverages = beverages;
        Extra = extra;
        Chaihiyeh = chaihiyeh;
    }

    public List<MenuItemModel> getSnacks() {
        return Snacks;
    }

    public void setSnacks(List<MenuItemModel> snacks) {
        Snacks = snacks;
    }

    public List<MenuItemModel> getBeverages() {
        return Beverages;
    }

    public void setBeverages(List<MenuItemModel> beverages) {
        Beverages = beverages;
    }

    public List<MenuItemModel> getExtra() {
        return Extra;
    }

    public void setExtra(List<MenuItemModel> extra) {
        Extra = extra;
    }

    public List<MenuItemModel> getChaihiyeh() {
        return Chaihiyeh;
    }

    public void setChaihiyeh(List<MenuItemModel> chaihiyeh) {
        Chaihiyeh = chaihiyeh;
    }
}
