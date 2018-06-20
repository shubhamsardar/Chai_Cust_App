package in.co.tripin.chahiyecustomer.Model.responce;

import java.io.Serializable;

public class TapriMenuResponce {

    private String status;

    private Data data;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public Data getData ()
    {
        return data;
    }

    public void setData (Data data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+status+", data = "+data+"]";
    }


    public class Data implements Serializable
    {
        private Item[] Extra;

        private Item[] Beverages;

        private Item[] Snacks;

        private Item[] Chaihiyeh;

        public Item[] getChaihiyeh() {
            return Chaihiyeh;
        }

        public void setChaihiyeh(Item[] chaihiyeh) {
            Chaihiyeh = chaihiyeh;
        }

        public Item[] getExtra() {
            return Extra;
        }

        public void setExtra(Item[] extra) {
            Extra = extra;
        }

        public Item[] getBeverages() {
            return Beverages;
        }

        public void setBeverages(Item[] beverages) {
            Beverages = beverages;
        }

        public Item[] getSnacks() {
            return Snacks;
        }

        public void setSnacks(Item[] snacks) {
            Snacks = snacks;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [Extra = "+Extra+", Beverages = "+Beverages+", Snacks = "+Snacks+"]";
        }

        public class Item implements Serializable
        {
            private String category;

            private String rate;

            private String _id;

            private String status;

            private String name;

            private int quantity = 0;

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getCategory ()
            {
                return category;
            }

            public void setCategory (String category)
            {
                this.category = category;
            }

            public String getRate ()
            {
                return rate;
            }

            public void setRate (String rate)
            {
                this.rate = rate;
            }

            public String get_id ()
            {
                return _id;
            }

            public void set_id (String _id)
            {
                this._id = _id;
            }

            public String getStatus ()
            {
                return status;
            }

            public void setStatus (String status)
            {
                this.status = status;
            }

            public String getName ()
            {
                return name;
            }

            public void setName (String name)
            {
                this.name = name;
            }

            @Override
            public String toString()
            {
                return "ClassPojo [category = "+category+", rate = "+rate+", _id = "+_id+", status = "+status+", name = "+name+"]";
            }
        }
    }

}
