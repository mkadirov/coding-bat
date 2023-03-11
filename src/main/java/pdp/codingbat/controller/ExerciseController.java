package pdp.codingbat.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pdp.codingbat.payload.ApiResponse;
import pdp.codingbat.payload.ExerciseDto;
import pdp.codingbat.service.ExerciseService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/languageId/partId/exercise")
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;


    @GetMapping
    public ResponseEntity<ApiResponse> getExerciseList(){
        ApiResponse apiResponse = exerciseService.getExerciseList();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getExerciseByID(@PathVariable Integer id){
        ApiResponse apiResponse = exerciseService.getExerciseById(id);
        return ResponseEntity.status(apiResponse.isSuccess()? 200:404).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addExercise(@Valid @RequestBody ExerciseDto exerciseDto){
        ApiResponse apiResponse = exerciseService.addExercise(exerciseDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 201:409).body(apiResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editExerciseById(@PathVariable Integer id, @Valid @RequestBody ExerciseDto exerciseDto){
        ApiResponse apiResponse = exerciseService.editExercise(id, exerciseDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 202:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteExerciseById(@PathVariable Integer id){
        ApiResponse apiResponse = exerciseService.deleteExercise(id);
        return ResponseEntity.status(apiResponse.isSuccess()? 200:404).body(apiResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handelValidationExceptions(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
