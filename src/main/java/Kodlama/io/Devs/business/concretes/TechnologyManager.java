package Kodlama.io.Devs.business.concretes;

import Kodlama.io.Devs.business.abstracts.TechnologyService;
import Kodlama.io.Devs.business.requests.technologyRequests.CreateTechnologyRequest;
import Kodlama.io.Devs.business.requests.technologyRequests.DeleteTechnologyRequests;
import Kodlama.io.Devs.business.requests.technologyRequests.UpdateTechnologyRequests;
import Kodlama.io.Devs.business.responses.technologyResponses.GetAllTechnologiesResponse;
import Kodlama.io.Devs.business.responses.technologyResponses.GetByIdTechnologyResponse;
import Kodlama.io.Devs.dataAccess.abstracts.LanguageRepository;
import Kodlama.io.Devs.dataAccess.abstracts.TechnologyRepository;
import Kodlama.io.Devs.entities.concretes.Language;
import Kodlama.io.Devs.entities.concretes.Technology;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TechnologyManager implements TechnologyService {

    private TechnologyRepository technologyRepository;

    private LanguageRepository languageRepository;

    public TechnologyManager(TechnologyRepository technologyRepository, LanguageRepository languageRepository) {
        this.technologyRepository = technologyRepository;
        this.languageRepository = languageRepository;
    }

    @Override
    public List<GetAllTechnologiesResponse> getAll() {

        List<Technology> technologies = technologyRepository.findAll();
        List<GetAllTechnologiesResponse> gAllResponse = new ArrayList<>();

        for (Technology tc : technologies) {
            GetAllTechnologiesResponse response = new GetAllTechnologiesResponse();
            response.setId(tc.getId());
            response.setName(tc.getName());
            response.setLanguageId(tc.getLanguage().getId());
            response.setLanguageName(tc.getLanguage().getName());

            gAllResponse.add(response);
        }
        return gAllResponse;
    }

    @Override
    public void add(CreateTechnologyRequest createTechnologyRequest) throws Exception {

        if (isNameExist(createTechnologyRequest.getName())) {
            throw new Exception("Eklemek istediğiniz teknoloji zaten mevcut.");
        }

        Technology technology = new Technology();
        technology.setName(createTechnologyRequest.getName());
        Language language = this.languageRepository.findLanguageById(createTechnologyRequest.getLanguageId());
        technology.setLanguage(language);
        this.technologyRepository.save(technology);

    }

    @Override
    public void delete(DeleteTechnologyRequests deleteTechnologyRequests) throws Exception {
        if (!isIdExist(deleteTechnologyRequests.getId())) {
            throw new Exception("Silmek istediğiniz İD ile teknolojiye ait İD uyuşmuyor.");
        }

        Technology technology = new Technology();
        technology.setId(deleteTechnologyRequests.getId());

        technologyRepository.delete(technology);

    }

    @Override
    public void update(UpdateTechnologyRequests updateTechnologyRequests) throws Exception {

        if (isNameExist(updateTechnologyRequests.getName())) {
            throw new Exception("Aynı isimde teknoloji zaten mevcut.");
        }

        if (!isIdExist(updateTechnologyRequests.getId())) {
            throw new Exception("Bu ID ile tanımlanmış teknoloji mevcut değil.");
        }

        Technology technology = technologyRepository.findById(updateTechnologyRequests.getId()).get();
        technology.setId(updateTechnologyRequests.getId());
        technology.setName(updateTechnologyRequests.getName());
        technology.setLanguage(languageRepository.findLanguageById(updateTechnologyRequests.getLanguageId()));

        technologyRepository.save(technology);


    }

    @Override
    public GetByIdTechnologyResponse getByIdTechnology(int id) throws Exception {
        if (!isIdExist(id)) {
            throw new Exception("Sistemde bu ID ile tanımlanmış teknoloji mevcut değil.");
        }

        Technology item = technologyRepository.findById(id).get();

        GetByIdTechnologyResponse getByIdTechnologyResponse = new GetByIdTechnologyResponse();
        getByIdTechnologyResponse.setId(item.getId());
        getByIdTechnologyResponse.setName(item.getName());
        getByIdTechnologyResponse.setLanguageId(item.getLanguage().getId());
        getByIdTechnologyResponse.setLanguageName(item.getLanguage().getName());

        return getByIdTechnologyResponse;
    }

    public boolean isIdExist(int id) {
        for (Technology tc : technologyRepository.findAll()) {
            if (tc.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean isNameExist(String name) {
        for (Technology tc : technologyRepository.findAll()) {
            if (tc.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

}
