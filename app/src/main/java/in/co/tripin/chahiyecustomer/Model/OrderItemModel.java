package in.co.tripin.chahiyecustomer.Model;

public class OrderItemModel {

String itemName;
        int amount;
        String itemId;
int quantity;

    public OrderItemModel(String itemName, int amount, String itemId, int quantity) {
        this.itemName = itemName;
        this.amount = amount;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
