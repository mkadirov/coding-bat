package pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdp.codingbat.entity.Exercise;
import pdp.codingbat.payload.ApiResponse;
import pdp.codingbat.payload.ExerciseDto;
import pdp.codingbat.repasitory.ExerciseRepository;

import java.util.Optional;

@Service
public class ExerciseService {

    @Autowired
    ExerciseRepository exerciseRepository;

    /**
     * POST method to add new Exercise
     * @param exerciseDto model class for Exercise
     * @return message about result and boolean
     */
    public ApiResponse addExercise(ExerciseDto exerciseDto){
       Exercise exercise = new Exercise();
       exercise.setExerciseText(exerciseDto.getExerciseText());
       exercise.setName(exerciseDto.getName());
       exercise.setSolution(exerciseDto.getSolution());
       exercise.setCodeText(exerciseDto.getCodeText());
       exerciseRepository.save(exercise);
       return new ApiResponse("Successfully added", true);
    }

    /**
     * GET method to get List of Exercise
     * @return message about result, boolean and List of exercise
     */
    public ApiResponse getExerciseList(){
        return new ApiResponse("Successfully retrieved", true, exerciseRepository.findAll());
    }

    /**
     * GET method to get Exercise by ID
     * @param id Exercise id
     * @return message about result, boolean and  exercise
     */
    public ApiResponse getExerciseById(Integer id){
        Optional<Exercise> optionalExercise = exerciseRepository.findById(id);
        return optionalExercise.map(exercise ->
                  new ApiResponse("Successfully retrieved", true, exercise)).orElseGet(() ->
                  new ApiResponse("Exercise not found", false));
    }

    /**
     * PUT method to edit Exercise by ID
     * @param id Exercise ID
     * @param exerciseDto model class for Exercise
     * @return message about result, boolean
     */
    public ApiResponse editExercise(Integer id,ExerciseDto exerciseDto){
        Optional<Exercise> optionalExercise = exerciseRepository.findById(id);
        if(optionalExercise.isEmpty()){
            return new ApiResponse("Exercise not found", false);
        }
        Exercise exercise = optionalExercise.get();
        exercise.setExerciseText(exerciseDto.getExerciseText());
        exercise.setName(exerciseDto.getName());
        exercise.setSolution(exerciseDto.getSolution());
        exercise.setCodeText(exerciseDto.getCodeText());
        exerciseRepository.save(exercise);
        return new ApiResponse("Successfully added", true);
    }

    public ApiResponse deleteExercise(Integer id){
        if(exerciseRepository.existsById(id)){
            return new ApiResponse("Successfully deleted", true);
        }
        return new ApiResponse("Exercise not found", false);
    }

}
