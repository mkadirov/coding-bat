package pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdp.codingbat.entity.User;
import pdp.codingbat.payload.ApiResponse;
import pdp.codingbat.payload.PasswordDto;
import pdp.codingbat.payload.UserDto;
import pdp.codingbat.repasitory.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * POST method to add new User
     * @param userDto model class for User
     * @return message about result, boolean
     */
    public ApiResponse addUser(UserDto userDto){
        if(userRepository.existsByUserName(userDto.getUsername())) {
            return new ApiResponse("Username exists", false);
        }
        User user = new User(null,userDto.getUsername(), userDto.getPassword());
        return new ApiResponse("Successfully added", true);
    }

    /**
     * GET method to get user List
     * @return message about result, boolean
     */
    public ApiResponse getUserList(){
        return new ApiResponse("Successfully retrieved", true, userRepository.findAll());
    }

    /**
     * GET method to get user by ID
     * @param id user ID
     * @return message about result, boolean, User
     */
    public ApiResponse getUserById(Integer id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(user ->
                new ApiResponse("Successfully retrieved", true, user)).orElseGet(() ->
                new ApiResponse("User not found", false));
    }

    /**
     * PUT method to edit Username
     * @param id user Id
     * @param userDto model class for User
     * @return message about result, boolean, User
     */
    public ApiResponse editUsername(Integer id, UserDto userDto){
        if(userRepository.existsByUserNameAndIdNot(userDto.getUsername(), id)){
            return new  ApiResponse("Username exists", false);
        }
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()){
            return new ApiResponse("Username not found", false);
        }
        User user = optionalUser.get();
        if (!user.getPassword().equals(userDto.getPassword())){
            return new ApiResponse("Password isn't correct", false);
        }
        user.setUserName(userDto.getUsername());
        userRepository.save(user);
        return new ApiResponse("Username successfully edited", true);
    }

    /**
     * PUT method to edit Password
     * @param id user Id
     * @param passwordDto model class for User
     * @return message for result and boolean
     */
    public ApiResponse editPassword(Integer id, PasswordDto passwordDto){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()){
            return new ApiResponse("User not found", false);
        }
        User user = optionalUser.get();

        if(!user.getUserName().equals(passwordDto.getUsername())){
            return new ApiResponse("Username isn't correct", false);
        }
        if(!user.getPassword().equals(passwordDto.getOldPass())){
            return new ApiResponse("Password isn't correct", false);
        }

        if(!passwordDto.getOldPass().equals(passwordDto.getNewPass())){
            return new ApiResponse("old and new passwords must be similar", false);
        }
        user.setPassword(passwordDto.getNewPass());
        userRepository.save(user);
        return new ApiResponse("Password successfully edited", true);
    }

    /**
     * DELETE method to delete User
     * @param id user id
     * @param userDto model class for User
     * @return message for result and boolean
     */
    public ApiResponse deleteUser(Integer id, UserDto userDto){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()){
            return new ApiResponse("User not found", false);
        }
        User user = optionalUser.get();
        if(!user.getUserName().equals(userDto.getUsername())){
            return new ApiResponse("Username isn't correct", false);
        }
        if(!user.getPassword().equals(userDto.getPassword())){
            return new ApiResponse("Password isn't correct", false);
        }
        userRepository.deleteById(id);
        return new ApiResponse("Successfully deleted", true);
    }
}
