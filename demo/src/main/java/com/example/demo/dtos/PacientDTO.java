package com.example.demo.dtos;

public class PacientDTO {

        private Integer id;
        private String firstName = "";

        private String lastName = "";

        private String email;

        private String phoneNumber;

        private String address;


        private String password;

        private String socialCategory;

        private String username;

        private boolean hasInsurance;



        public PacientDTO(Integer id, String firstName, String lastName, String email, String phoneNumber, String address,  String socialCategory, String username, boolean hasInsurance) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phoneNumber = phoneNumber;
            this.address = address;
            this.socialCategory = socialCategory;
            this.username = username;
            this.hasInsurance = hasInsurance;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSocialCategory() {
            return socialCategory;
        }

        public void setSocialCategory(String socialCategory) {
            this.socialCategory = socialCategory;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public boolean isHasInsurance() {
            return hasInsurance;
        }

        public void setHasInsurance(boolean hasInsurance) {
            this.hasInsurance = hasInsurance;
        }
    }


