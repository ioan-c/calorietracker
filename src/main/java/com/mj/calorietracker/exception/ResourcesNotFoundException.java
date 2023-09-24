package com.mj.calorietracker.exception;

import com.mj.calorietracker.exception.model.ErrorInfoForList;
import lombok.Getter;

import java.util.List;

@Getter
public class ResourcesNotFoundException extends RuntimeException {
    private final List<ErrorInfoForList> errorInfoList;
    public ResourcesNotFoundException(List<ErrorInfoForList> errorInfoList) {
        super();
        this.errorInfoList = errorInfoList;
    }
}
