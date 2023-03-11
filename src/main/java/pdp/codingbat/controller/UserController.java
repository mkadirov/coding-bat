package pdp.codingbat.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pdp.codingbat.payload.ApiResponse;
import pdp.codingbat.payload.PasswordDto;
import pdp.codingbat.payload.UserDto;
import pdp.codingbat.service.UserService;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse> getUserList(){
        ApiResponse apiResponse = userService.getUserList();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserByID(@Valid  @PathVariable Integer id){
        ApiResponse apiResponse = userService.getUserById(id);
        return ResponseEntity.status(apiResponse.isSuccess()? 200:404).body(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addUser(@RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.addUser(userDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 201:409).body(apiResponse);
    }

    @PutMapping("/editUsername/{id}")
    public ResponseEntity<ApiResponse> editUsername(@PathVariable Integer id, @Valid @RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.editUsername(id, userDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 202:409).body(apiResponse);
    }

    @PutMapping("/editPassword/{id}")
    public ResponseEntity<ApiResponse> editPassword(@PathVariable Integer id, @Valid @RequestBody PasswordDto passwordDto){
        ApiResponse apiResponse = userService.editPassword(id, passwordDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 202:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id, @Valid @RequestBody UserDto userDto){
        ApiResponse apiResponse = userService.deleteUser(id, userDto);
        return ResponseEntity.status(apiResponse.isSuccess()? 200:409).body(apiResponse);
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
