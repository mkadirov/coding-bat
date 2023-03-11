package pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdp.codingbat.entity.Exercise;
import pdp.codingbat.entity.Part;
import pdp.codingbat.payload.ApiResponse;
import pdp.codingbat.payload.PartDto;
import pdp.codingbat.repasitory.ExerciseRepository;
import pdp.codingbat.repasitory.PartRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PartService {

    @Autowired
    PartRepository partRepository;
    @Autowired
    ExerciseRepository exerciseRepository;

    /**
     * POST method to add new Part
     * @param partDto model class for Part
     * @return message about result, boolean
     */
    public ApiResponse addPart(PartDto partDto){
        List<Integer> idList = partDto.getExerciseIdList();
        List<Exercise> exerciseList = new ArrayList<>();
        Part part = new Part();
        for (Integer id : idList) {
            Optional<Exercise> optionalExercise = exerciseRepository.findById(id);
            if (optionalExercise.isEmpty()){
                return new ApiResponse("Exercise not found", false);
            }
            exerciseList.add(optionalExercise.get());
        }
        part.setName(partDto.getName());
        part.setExerciseList(exerciseList);
        partRepository.save(part);
        return new ApiResponse("Successfully added", true);
    }

    /**
     * GET method to get List of Parts
     * @return message about result, boolean, List of Parts
     */
    public ApiResponse getPartList(){
        return new ApiResponse("Successfully retrieved", true, partRepository.findAll());
    }

    /**
     * GET method to get a part by id
     * @param id part id
     * @return message about result, boolean, Part class
     */
    public ApiResponse getPartById(Integer id){
        Optional<Part> optionalPart = partRepository.findById(id);
        return optionalPart.map(part ->
                new ApiResponse("Successfully retrieved", true, part)).orElseGet(() ->
                new ApiResponse("Part not found", false));
    }

    /**
     * PUT method to edit Part by ID
     * @param id Part id
     * @param partDto model class for Part
     * @return message about result, boolean
     */
    public ApiResponse editPartById(Integer id, PartDto partDto){
        Optional<Part> optionalPart = partRepository.findById(id);
        if (optionalPart.isEmpty()){
            return new ApiResponse("Part not found", false);
        }
        Part part = optionalPart.get();
        List<Integer> idList = partDto.getExerciseIdList();
        List<Exercise> exerciseList = new ArrayList<>();
        for (Integer exerciseId : idList) {
            Optional<Exercise> optionalExercise = exerciseRepository.findById(exerciseId);
            if (optionalExercise.isEmpty()){
                return new ApiResponse("Exercise not found", false);
            }
            exerciseList.add(optionalExercise.get());
        }
        part.setName(partDto.getName());
        part.setExerciseList(exerciseList);
        partRepository.save(part);
        return new ApiResponse("Successfully added", true);
    }

    /**
     * DELETE method to delete a part by ID
     * @param id Part id
     * @return message about result, boolean
     */
    public ApiResponse deletePart(Integer id){
        Optional<Part> optionalPart = partRepository.findById(id);
        if (optionalPart.isPresent()){
            return new ApiResponse("Succesfully deleted", true);
        }
        return new ApiResponse("Part not found", false);
    }
}
