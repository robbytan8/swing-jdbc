package com.robby.swingapp.entity;

/**
 * @author Robby Tan
 */
public final class Student {
    private String id;
    private String name;
    private String address;
    private boolean male;
    private Department department;

    private Student(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.address = builder.address;
        this.male = builder.male;
        this.department = builder.department;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public boolean isMale() {
        return male;
    }

    public Department getDepartment() {
        return department;
    }

    public static class Builder {
        private String id;
        private String name;
        private String address;
        private boolean male;
        private Department department;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder male(boolean male) {
            this.male = male;
            return this;
        }

        public Builder department(Department department) {
            this.department = department;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }
}
