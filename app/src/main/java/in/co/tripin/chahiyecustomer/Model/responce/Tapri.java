package in.co.tripin.chahiyecustomer.Model.responce;

import in.co.tripin.chahiyecustomer.dataproviders.CommonResponse;

public class Tapri extends CommonResponse {

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

    public class Data
    {
        private String isFixed;

        private String flag;

        private String _id;

        private Location location;

        private String name;

        private String tapriMobile;

        public String getTapriMobile() {
            return tapriMobile;
        }

        public void setTapriMobile(String tapriMobile) {
            this.tapriMobile = tapriMobile;
        }

        public String getIsFixed ()
        {
            return isFixed;
        }

        public void setIsFixed (String isFixed)
        {
            this.isFixed = isFixed;
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

        public Location getLocation ()
        {
            return location;
        }

        public void setLocation (Location location)
        {
            this.location = location;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }


    }

    public class Location
    {
        private String type;

        private String[] coordinates;

        public String getType ()
        {
            return type;
        }

        public void setType (String type)
        {
            this.type = type;
        }

        public String[] getCoordinates ()
        {
            return coordinates;
        }

        public void setCoordinates (String[] coordinates)
        {
            this.coordinates = coordinates;
        }
    }

}
