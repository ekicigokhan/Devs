package Kodlama.io.Devs.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Kodlama.io.Devs.entities.concretes.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
	
	Language findLanguageById(int id);
}
