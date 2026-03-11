module com.godfrey.vehicle {

    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.web;
    requires spring.beans;
    requires spring.data.jpa;
    requires jakarta.persistence;
    requires jakarta.validation;
    requires java.sql;
    requires static lombok;
    requires org.mapstruct;
    requires java.compiler;

    // Open packages for reflection (Spring / JPA)
    opens com.godfrey.vehicle to spring.beans, spring.context, spring.boot;
    opens com.godfrey.vehicle.model to spring.beans, spring.context, spring.boot, spring.data.jpa, jakarta.persistence;
    opens com.godfrey.vehicle.controller to spring.beans, spring.context, spring.boot;
    opens com.godfrey.vehicle.service to spring.beans, spring.context, spring.boot;

    // Exports
    exports com.godfrey.vehicle;
    exports com.godfrey.vehicle.model;
    exports com.godfrey.vehicle.controller;
    exports com.godfrey.vehicle.service;
    exports com.godfrey.vehicle.dto;
}
