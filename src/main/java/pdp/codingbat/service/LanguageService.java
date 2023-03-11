package pdp.codingbat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdp.codingbat.entity.Language;
import pdp.codingbat.entity.Part;
import pdp.codingbat.payload.ApiResponse;
import pdp.codingbat.payload.LanguageDto;
import pdp.codingbat.repasitory.LanguageRepository;
import pdp.codingbat.repasitory.PartRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class LanguageService {

    @Autowired
    LanguageRepository languageRepository;
    @Autowired
    PartRepository partRepository;

    /**
     * POSt method to add new Language
     * @param languageDto model class for Language class
     * @return message about result and boolean
     */
    public ApiResponse addLanguage(LanguageDto languageDto){
        if(languageRepository.existsByName(languageDto.getName())){
            return new ApiResponse("Language exists", false);
        }
        List<Part> partList = new ArrayList<>();
        for (Integer partId : languageDto.getPartIdList()) {
            Optional<Part> optionalPart = partRepository.findById(partId);
            if (optionalPart.isEmpty()){
                return new ApiResponse("part not found", false);
            }
            partList.add(optionalPart.get());
        }
        Language language = new Language();
        language.setName(languageDto.getName());
        language.setPartList(partList);
        languageRepository.save(language);
        return new ApiResponse("Successfully added", true);
    }

    /**
     * GET method to get List of Language
     * @return message about result, boolean and List of language
     */
    public ApiResponse getLanguageList(){
        return new ApiResponse("Successfully retrieved", true, languageRepository.findAll());
    }

    /**
     * GET method to get Language by id
     * @param id language id
     * @return message about result, boolean and Language by ID
     */
    public ApiResponse getLanguageById(Integer id){
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        return optionalLanguage.map(language ->
                new ApiResponse("Successfully retrieved", true, language)).orElseGet(() ->
                new ApiResponse("Language not fund", false));
    }

    /**
     * PUT method to edit language by id
     * @param id language id
     * @param languageDto model class for Language
     * @return message about result, boolean
     */
    public ApiResponse editLanguage(Integer id, LanguageDto languageDto){
        Optional<Language> optionalLanguage = languageRepository.findById(id);
        if (optionalLanguage.isEmpty()){
            return new ApiResponse("language not found", false);
        }
        Language language = optionalLanguage.get();
        if(languageRepository.existsByNameAndIdNot(languageDto.getName(), id)){
            return new ApiResponse("Language exists", false);
        }
        List<Part> partList = new ArrayList<>();
        for (Integer partId : languageDto.getPartIdList()) {
            Optional<Part> optionalPart = partRepository.findById(partId);
            if (optionalPart.isEmpty()){
                return new ApiResponse("part not found", false);
            }
            partList.add(optionalPart.get());
        }
        language.setName(languageDto.getName());
        language.setPartList(partList);
        languageRepository.save(language);
        return new ApiResponse("Successfully added", true);
    }

    /**
     * DELETE method to delete Language
     * @param id language id
     * @return message about result, boolean
     */
    public ApiResponse deleteLanguage(Integer id){
        if(languageRepository.existsById(id)){
            return new ApiResponse("Successfully deleted", true);
        }
        return new ApiResponse("Language not found", false);
    }
}
