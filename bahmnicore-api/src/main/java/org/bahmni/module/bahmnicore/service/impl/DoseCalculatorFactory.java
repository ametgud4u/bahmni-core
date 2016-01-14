package org.bahmni.module.bahmnicore.service.impl;

import org.bahmni.module.bahmnicore.service.DoseCalculator;
import org.openmrs.api.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DoseCalculatorFactory {

    @Autowired
    private ApplicationContext appContext;
    public static final Map<String, Class<? extends DoseCalculator>> doseCalculatorMap = new HashMap<String, Class<? extends DoseCalculator>>() {{
        this.put("mg/kg", WeightBasedDoseCalculator.class);
        this.put("mg/m2", BSABasedDoseCalculator.class);
    }};

    public DoseCalculator getCalculator(String doseUnits) {
        Class<? extends DoseCalculator> rule = doseCalculatorMap.get(doseUnits);
        if (null == rule) {
            String errMessage = "Dose Calculator for " + doseUnits + " not found";
            throw new APIException(errMessage);
        }
        return appContext.getBean(rule);
    }
}