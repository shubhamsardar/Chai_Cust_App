package in.co.tripin.chahiyecustomer.Model.responce;

import java.util.List;

import in.co.tripin.chahiyecustomer.dataproviders.CommonResponse;

public class FavTapriResponce extends CommonResponse {

    List<Data> data ;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public  class  Data
    {
        String _id,updatedAt,createdAt,name,_v0;
        HubId hubId;
        String  tapriCode,logoUrlPath,flag,orderCounter,totalOrderCounter;
        Location location;
        List<Users> users;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String get_v0() {
            return _v0;
        }

        public void set_v0(String _v0) {
            this._v0 = _v0;
        }

        public HubId getHubId() {
            return hubId;
        }

        public void setHubId(HubId hubId) {
            this.hubId = hubId;
        }

        public String getTapriCode() {
            return tapriCode;
        }

        public void setTapriCode(String tapriCode) {
            this.tapriCode = tapriCode;
        }

        public String getLogoUrlPath() {
            return logoUrlPath;
        }

        public void setLogoUrlPath(String logoUrlPath) {
            this.logoUrlPath = logoUrlPath;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getOrderCounter() {
            return orderCounter;
        }

        public void setOrderCounter(String orderCounter) {
            this.orderCounter = orderCounter;
        }

        public String getTotalOrderCounter() {
            return totalOrderCounter;
        }

        public void setTotalOrderCounter(String totalOrderCounter) {
            this.totalOrderCounter = totalOrderCounter;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public List<Users> getUsers() {
            return users;
        }

        public void setUsers(List<Users> users) {
            this.users = users;
        }

        public class HubId
        {
            String _id,name;

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

        public class Users
        {
            String _id ;
            PersonId personId;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public PersonId getPersonId() {
                return personId;
            }

            public void setPersonId(PersonId personId) {
                this.personId = personId;
            }

            public class PersonId
            {
                String _id,updatedAt,createdAt,firstName,lastName,fullName,mobile,__v,imageUrlPath,flag;

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public String getUpdatedAt() {
                    return updatedAt;
                }

                public void setUpdatedAt(String updatedAt) {
                    this.updatedAt = updatedAt;
                }

                public String getCreatedAt() {
                    return createdAt;
                }

                public void setCreatedAt(String createdAt) {
                    this.createdAt = createdAt;
                }

                public String getFirstName() {
                    return firstName;
                }

                public void setFirstName(String firstName) {
                    this.firstName = firstName;
                }

                public String getLastName() {
                    return lastName;
                }

                public void setLastName(String lastName) {
                    this.lastName = lastName;
                }

                public String getFullName() {
                    return fullName;
                }

                public void setFullName(String fullName) {
                    this.fullName = fullName;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public String get__v() {
                    return __v;
                }

                public void set__v(String __v) {
                    this.__v = __v;
                }

                public String getImageUrlPath() {
                    return imageUrlPath;
                }

                public void setImageUrlPath(String imageUrlPath) {
                    this.imageUrlPath = imageUrlPath;
                }

                public String getFlag() {
                    return flag;
                }

                public void setFlag(String flag) {
                    this.flag = flag;
                }
            }
        }

    }
}
