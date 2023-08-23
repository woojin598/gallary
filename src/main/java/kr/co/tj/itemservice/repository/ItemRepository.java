package kr.co.tj.itemservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.tj.itemservice.dto.ItemEntity;


public interface ItemRepository extends JpaRepository<ItemEntity, Long>{

	
	ItemEntity findByTitle(String title);
	
	Page<ItemEntity> findAll(Pageable pageable);

	//검색기능
	List<ItemEntity> findByArtistContainingOrTitleContaining(String artist, String title);

	void deleteByTitle(String title);

	Optional<ItemEntity> findById(String title);
	
}
