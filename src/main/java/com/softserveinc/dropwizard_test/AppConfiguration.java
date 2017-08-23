package com.softserveinc.dropwizard_test;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class AppConfiguration extends Configuration {

    @JsonProperty
    @NotEmpty
    public String mongohost;

    @JsonProperty
    @Min(1)
    @Max(65535)
    public int mongoport;

    @JsonProperty
    @NotEmpty
    public String mongodb;
}
