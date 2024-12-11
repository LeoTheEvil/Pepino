package com.pepino.automation;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

//@Suite
@IncludeEngines("cucumber")
@SelectPackages("com.example")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.pepino.automation.Steps.hireKappaSteps")

public class Suite {

}