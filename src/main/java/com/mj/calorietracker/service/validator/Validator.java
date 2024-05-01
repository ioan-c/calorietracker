package com.mj.calorietracker.service.validator;

import com.mj.calorietracker.exception.ResourcesNotFoundException;
import com.mj.calorietracker.exception.model.ErrorInfoForList;
import org.springframework.util.CollectionUtils;

import java.util.List;

abstract class Validator {
    void digestErrorList(List<ErrorInfoForList> errorInfoList) {
        if(!CollectionUtils.isEmpty(errorInfoList)) {
            throw new ResourcesNotFoundException(errorInfoList);
        }
    }
}
