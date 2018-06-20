package in.co.tripin.chahiyecustomer.Model.responce;

import java.io.Serializable;

import in.co.tripin.chahiyecustomer.dataproviders.CommonResponse;

public class UserAddress extends CommonResponse {


    private UserAddress.Data[] data;

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public UserAddress.Data[] getData ()
    {
        return data;
    }

    public void setData (UserAddress.Data[] data)
    {
        this.data = data;
    }

    public class Data implements Serializable
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
            return getLandmark()+", "
                    +getFlatSociety()+", "
                    +getAddressLine1()+", "
                    +getAddressLine2()+", "
                    +getCity()+", "
                    +getCountry();
        }

    }


    public class Location implements Serializable
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
