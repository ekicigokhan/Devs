package Kodlama.io.Devs.webApi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Kodlama.io.Devs.business.abstracts.TechnologyService;
import Kodlama.io.Devs.business.requests.technologyRequests.CreateTechnologyRequest;
import Kodlama.io.Devs.business.requests.technologyRequests.DeleteTechnologyRequests;
import Kodlama.io.Devs.business.requests.technologyRequests.UpdateTechnologyRequests;
import Kodlama.io.Devs.business.responses.technologyResponses.GetAllTechnologiesResponse;
import Kodlama.io.Devs.business.responses.technologyResponses.GetByIdTechnologyResponse;

@RestController
@RequestMapping("/api/technologies")
public class TechnologiesController {
	
	private TechnologyService technologyService;
	
	@Autowired
	public TechnologiesController(TechnologyService technologyService) {
		this.technologyService = technologyService;
	}
	
	@GetMapping("/getall")
	public List<GetAllTechnologiesResponse> getAll(){
		return technologyService.getAll();
	}

	@PostMapping("/add")
	public void add(@RequestBody CreateTechnologyRequest createTechnologyRequest) throws Exception {
		this.technologyService.add(createTechnologyRequest);
	}
	
	@DeleteMapping("/delete")
	public void delete(@RequestBody DeleteTechnologyRequests deleteTechnologyRequests)throws Exception {
		this.technologyService.delete(deleteTechnologyRequests);
	}
	@PutMapping("/update")
	public void update(@RequestBody UpdateTechnologyRequests updateTechnologyRequests) throws Exception {

		this.technologyService.update(updateTechnologyRequests);
	}

	@GetMapping("/{id}")
	public GetByIdTechnologyResponse getByIdTechnology(@RequestParam int id) throws Exception {

		return technologyService.getByIdTechnology(id);
	}
}
