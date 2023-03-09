package com.peopulley.core.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class StepCountDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TodaySetpCounts{

        private Integer pd_standard_hour;

        private Integer pd_count;

        private String goodStep;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class HourGoodSetpCounts{

        private Integer pd_standard_hour;

        private Long goodCnt;
    }



    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TodaySetpCountsResponse{

        private Long goodStepCount;

        private List<TodaySetpCounts> todaySetpCounts;

    }

}
