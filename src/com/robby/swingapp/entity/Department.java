package com.robby.swingapp.entity;

/**
 * @author Robby Tan
 */
public final class Department {
    private String id;
    private String name;

    private Department(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static class Builder {
        private String id;
        private String name;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Department build() {
            return new Department(this);
        }
    }

    @Override
    public String toString() {
        return id + " - " + name;
    }
}
