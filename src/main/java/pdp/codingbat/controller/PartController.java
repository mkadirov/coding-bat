package pdp.codingbat.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pdp.codingbat.payload.ApiResponse;
import pdp.codingbat.payload.PartDto;
import pdp.codingbat.service.PartService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/languageId/part")
public class PartController {

    @Autowired
    PartService partService;

    @GetMapping
    public ResponseEntity<ApiResponse> getPartList(){
        ApiResponse apiResponse = partService.getPartList();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPartById(@PathVariable Integer id){
        ApiResponse apiResponse = partService.getPartById(id);
        return ResponseEntity.status(apiResponse.isSuccess()? 200:404).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addPart(@Valid @RequestBody PartDto partDto){
        ApiResponse apiResponse = partService.addPart(partDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 201:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editPart(@PathVariable Integer id, @Valid @RequestBody PartDto partDto){
        ApiResponse apiResponse = partService.editPartById(id, partDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 202:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deletePart(@PathVariable Integer id){
        ApiResponse apiResponse = partService.deletePart(id);
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
