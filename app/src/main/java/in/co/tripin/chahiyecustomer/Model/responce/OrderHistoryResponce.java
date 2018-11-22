package in.co.tripin.chahiyecustomer.Model.responce;

public class OrderHistoryResponce {
    private String status;

    private Data[] data;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public Data[] getData ()
    {
        return data;
    }

    public void setData (Data[] data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status = "+status+", data = "+data+"]";
    }


    public class Data
    {
        private String paymentType;

        private String __v;

        private String paymentStatus;

        private String type;

        private String orderStatus;

        private AddressId addressId;

        private String updatedAt;

        private String shortId;

        private String flag;

        private Details[] details;

        private String _id;

        private String createdAt;

        private UserId userId;

        private String totalAmount;

        private TapriId tapriId;

        public String getPaymentType ()
        {
            return paymentType;
        }

        public void setPaymentType (String paymentType)
        {
            this.paymentType = paymentType;
        }

        public String get__v ()
        {
            return __v;
        }

        public void set__v (String __v)
        {
            this.__v = __v;
        }

        public String getPaymentStatus ()
        {
            return paymentStatus;
        }

        public void setPaymentStatus (String paymentStatus)
        {
            this.paymentStatus = paymentStatus;
        }

        public String getType ()
        {
            return type;
        }

        public void setType (String type)
        {
            this.type = type;
        }

        public String getOrderStatus ()
        {
            return orderStatus;
        }

        public void setOrderStatus (String orderStatus)
        {
            this.orderStatus = orderStatus;
        }

        public AddressId getAddressId ()
        {
            return addressId;
        }

        public void setAddressId (AddressId addressId)
        {
            this.addressId = addressId;
        }

        public String getUpdatedAt ()
        {
            return updatedAt;
        }

        public void setUpdatedAt (String updatedAt)
        {
            this.updatedAt = updatedAt;
        }

        public String getShortId ()
        {
            return shortId;
        }

        public void setShortId (String shortId)
        {
            this.shortId = shortId;
        }

        public String getFlag ()
        {
            return flag;
        }

        public void setFlag (String flag)
        {
            this.flag = flag;
        }

        public Details[] getDetails ()
        {
            return details;
        }

        public void setDetails (Details[] details)
        {
            this.details = details;
        }

        public String get_id ()
        {
            return _id;
        }

        public void set_id (String _id)
        {
            this._id = _id;
        }

        public String getCreatedAt ()
        {
            return createdAt;
        }

        public void setCreatedAt (String createdAt)
        {
            this.createdAt = createdAt;
        }

        public UserId getUserId ()
        {
            return userId;
        }

        public void setUserId (UserId userId)
        {
            this.userId = userId;
        }

        public String getTotalAmount ()
        {
            return totalAmount;
        }

        public void setTotalAmount (String totalAmount)
        {
            this.totalAmount = totalAmount;
        }

        public TapriId getTapriId ()
        {
            return tapriId;
        }

        public void setTapriId (TapriId tapriId)
        {
            this.tapriId = tapriId;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [paymentType = "+paymentType+", __v = "+__v+", paymentStatus = "+paymentStatus+", type = "+type+", orderStatus = "+orderStatus+", addressId = "+addressId+", updatedAt = "+updatedAt+", shortId = "+shortId+", flag = "+flag+", details = "+details+", _id = "+_id+", createdAt = "+createdAt+", userId = "+userId+", totalAmount = "+totalAmount+", tapriId = "+tapriId+"]";
        }

        public class UserId
        {
            private String _id;

            private String fullName;

            private String mobile;

            public String get_id ()
            {
                return _id;
            }

            public void set_id (String _id)
            {
                this._id = _id;
            }

            public String getFullName ()
            {
                return fullName;
            }

            public void setFullName (String fullName)
            {
                this.fullName = fullName;
            }

            public String getMobile ()
            {
                return mobile;
            }

            public void setMobile (String mobile)
            {
                this.mobile = mobile;
            }

            @Override
            public String toString()
            {
                return "ClassPojo [_id = "+_id+", fullName = "+fullName+", mobile = "+mobile+"]";
            }
        }


        public class TapriId
        {
            private String _id;

            private String name;

            public String get_id ()
            {
                return _id;
            }

            public void set_id (String _id)
            {
                this._id = _id;
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
                return "ClassPojo [_id = "+_id+", name = "+name+"]";
            }
        }

        public class AddressId
        {
            private Location location;

            private String nickname;

            private String __v;

            private String state;

            private String addressLine2;

            private String addressLine1;

            private String country;

            private String city;

            private String updatedAt;

            private String landmark;

            private String flag;

            private String _id;

            private String createdAt;

            private String userId;

            private String flatSociety;

            public Location getLocation ()
            {
                return location;
            }

            public void setLocation (Location location)
            {
                this.location = location;
            }

            public String getNickname ()
            {
                return nickname;
            }

            public void setNickname (String nickname)
            {
                this.nickname = nickname;
            }

            public String get__v ()
            {
                return __v;
            }

            public void set__v (String __v)
            {
                this.__v = __v;
            }

            public String getState ()
            {
                return state;
            }

            public void setState (String state)
            {
                this.state = state;
            }

            public String getAddressLine2 ()
            {
                return addressLine2;
            }

            public void setAddressLine2 (String addressLine2)
            {
                this.addressLine2 = addressLine2;
            }

            public String getAddressLine1 ()
            {
                return addressLine1;
            }

            public void setAddressLine1 (String addressLine1)
            {
                this.addressLine1 = addressLine1;
            }

            public String getCountry ()
            {
                return country;
            }

            public void setCountry (String country)
            {
                this.country = country;
            }

            public String getCity ()
            {
                return city;
            }

            public void setCity (String city)
            {
                this.city = city;
            }

            public String getUpdatedAt ()
            {
                return updatedAt;
            }

            public void setUpdatedAt (String updatedAt)
            {
                this.updatedAt = updatedAt;
            }

            public String getLandmark ()
            {
                return landmark;
            }

            public void setLandmark (String landmark)
            {
                this.landmark = landmark;
            }

            public String getFlag ()
            {
                return flag;
            }

            public void setFlag (String flag)
            {
                this.flag = flag;
            }

            public String get_id ()
            {
                return _id;
            }

            public void set_id (String _id)
            {
                this._id = _id;
            }

            public String getCreatedAt ()
            {
                return createdAt;
            }

            public void setCreatedAt (String createdAt)
            {
                this.createdAt = createdAt;
            }

            public String getUserId ()
            {
                return userId;
            }

            public void setUserId (String userId)
            {
                this.userId = userId;
            }

            public String getFlatSociety ()
            {
                return flatSociety;
            }

            public void setFlatSociety (String flatSociety)
            {
                this.flatSociety = flatSociety;
            }

            @Override
            public String toString()
            {
                return "ClassPojo [location = "+location+", nickname = "+nickname+", __v = "+__v+", state = "+state+", addressLine2 = "+addressLine2+", addressLine1 = "+addressLine1+", country = "+country+", city = "+city+", updatedAt = "+updatedAt+", landmark = "+landmark+", flag = "+flag+", _id = "+_id+", createdAt = "+createdAt+", userId = "+userId+", flatSociety = "+flatSociety+"]";
            }

            public String getFullAddressString(){
                return getNickname()+"\n"+getLandmark()+", "
                        +getFlatSociety()+", "
                        +getAddressLine1()+", "
                        +getAddressLine2()+", "
                        +getCity()+", "
                        +getCountry();
            }

            public class Location
            {
                private String[] coordinates;

                public String[] getCoordinates ()
                {
                    return coordinates;
                }

                public void setCoordinates (String[] coordinates)
                {
                    this.coordinates = coordinates;
                }

                @Override
                public String toString()
                {
                    return "ClassPojo [coordinates = "+coordinates+"]";
                }
            }
        }

        public class Details
        {
            private String amount;

            private String itemName;

            private String _id;

            private String quantity;

            private String itemId;

            public String getAmount ()
            {
                return amount;
            }

            public void setAmount (String amount)
            {
                this.amount = amount;
            }

            public String getItemName ()
            {
                return itemName;
            }

            public void setItemName (String itemName)
            {
                this.itemName = itemName;
            }

            public String get_id ()
            {
                return _id;
            }

            public void set_id (String _id)
            {
                this._id = _id;
            }

            public String getQuantity ()
            {
                return quantity;
            }

            public void setQuantity (String quantity)
            {
                this.quantity = quantity;
            }

            public String getItemId ()
            {
                return itemId;
            }

            public void setItemId (String itemId)
            {
                this.itemId = itemId;
            }

            @Override
            public String toString()
            {
                return "ClassPojo [amount = "+amount+", itemName = "+itemName+", _id = "+_id+", quantity = "+quantity+", itemId = "+itemId+"]";
            }
        }
    }
}
