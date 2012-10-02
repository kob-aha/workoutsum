package com.ka.workoutsum.controller;

import com.ka.workoutsum.domain.WeightWorkout;
import com.ka.workoutsum.domain.repository.WeightWorkoutRepository;
import com.ka.workoutsum.service.WorkoutsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

@Controller
//@RequestMapping("/workouts")
public class WorkoutsController {

    @Autowired
    private WeightWorkoutRepository weightWorkoutRepository;

    @Autowired
    private WorkoutsService workoutsService;

    @RequestMapping("/")
    public String welcomeHandler() {
        return "welcome";
    }

    @RequestMapping(value = "/workouts/{id}",method = RequestMethod.GET)
    public @ResponseBody WeightWorkout getById(@PathVariable String id){
        return workoutsService.getById(Long.parseLong(id));
    }

    @RequestMapping(value = "/workouts/create",method = RequestMethod.GET)
    public @ResponseBody String create(){

        for (int i = 0; i < 10; i++) {
            workoutsService.create("Goal" + i);
        }

        return "SUCCESS";
    }
}
