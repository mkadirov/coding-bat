package pdp.codingbat.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pdp.codingbat.payload.ApiResponse;
import pdp.codingbat.payload.LanguageDto;
import pdp.codingbat.service.LanguageService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/language")
public class LanguageController {


    @Autowired
    LanguageService languageService;

    @GetMapping
    public ResponseEntity<ApiResponse> getLanguageList(){
        ApiResponse apiResponse = languageService.getLanguageList();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getLanguageById(@PathVariable Integer id){
        ApiResponse apiResponse = languageService.getLanguageById(id);
        return ResponseEntity.status(apiResponse.isSuccess()? 200:404).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addLanguage(@Valid @RequestBody LanguageDto languageDto){
        ApiResponse apiResponse = languageService.addLanguage(languageDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 201:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editLanguage(@PathVariable Integer id, @Valid @RequestBody LanguageDto languageDto){
        ApiResponse apiResponse = languageService.editLanguage(id, languageDto);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteLanguage(@PathVariable Integer id){
        ApiResponse apiResponse = languageService.deleteLanguage(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:404).body(apiResponse);
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
