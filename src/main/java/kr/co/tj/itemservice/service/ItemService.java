package kr.co.tj.itemservice.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.tj.itemservice.dto.ItemDTO;
import kr.co.tj.itemservice.dto.ItemEntity;
import kr.co.tj.itemservice.dto.UploadFileRepository;
import kr.co.tj.itemservice.repository.ItemRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
//	@Autowired
//	private UploadFileRepository uploadFileRepository;
		
//	public ItemDTO createItem(ItemDTO itemDTO) {
//		
//			
//		itemDTO.setCreateDate(new Date());
//		itemDTO.setUpdateDate(new Date());
//		
//		ItemEntity itemEntity = itemDTO.toItemEntity();	
//		
//		itemEntity = itemRepository.save(itemEntity);
//		
//		itemDTO = ItemDTO.toItemDTO(itemEntity);
//		
//		return itemDTO;
//	}
//	
//	@Transactional
//	public ItemDTO update(ItemDTO dto) {
//		Optional<ItemEntity> optioanl = itemRepository.findById(dto.getTitle());
//		
//		
//		ItemEntity entity =optioanl.get();
//		
//		if(entity ==null) {
//			throw new RuntimeException("잘못된 정보입니다.");
//		}
//		
//		entity.setArtist(dto.getArtist());
//		entity.setTitle(dto.getTitle());
//		entity.setItemDescribe(dto.getItemDescribe());
//		entity.setPrice(dto.getPrice());
//				
//		entity.setUpdateDate(new Date());
//		
//		entity = itemRepository.save(entity);
//		dto = ItemDTO.toItemDTO(entity);
//		
//		return dto;
//	}
//	
	//검색기능
	
	public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
	
	public List<ItemDTO> searchItems(String searchTerm) {
        List<ItemEntity> itemList = itemRepository.findByArtistContainingOrTitleContaining(searchTerm, searchTerm);
        return itemList.stream()
                .map(ItemDTO::toItemDTO)
                .collect(Collectors.toList());
    }
		
	

//	public List<ItemDTO> getListByTitle(String title) {
//		List<ItemEntity> dbList = itemRepository.findByArtistContainingOrTitleContainin(title, title);
//		List<ItemDTO> list = new ArrayList<>();
//		
//		for(ItemEntity x : dbList) {
//			ItemDTO itemDTO = ItemDTO.toItemDTO(x);
//			list.add(itemDTO);
//		}
//		
//		return list;
//	}

	
	//페이징
	   @Transactional
	   public Page<ItemDTO> findAll(int page) {
	      List<Sort.Order> sortList = new ArrayList<>();
	      sortList.add(Sort.Order.desc("id"));

	      Pageable pageable = (Pageable) PageRequest.of(page, 9, Sort.by(sortList));
	      Page<ItemEntity> page_member = itemRepository.findAll(pageable);
	      Page<ItemDTO> page_dto = page_member.map(itemEntity -> 
	      new ItemDTO(
	    		  itemEntity.getId(),
	    		  itemEntity.getArtist(), 
	    		  itemEntity.getTitle(), 
	    		  itemEntity.getItemDescribe(),
	    		  itemEntity.getPrice(),
	    		  itemEntity.getQty(),
	    		  itemEntity.getUpdateDate(),
	    		  itemEntity.getCreateDate(),
	    		  itemEntity.getBytes(),
	    		  null));
	      return page_dto;
	   }

	public List<ItemDTO> findAll() {
		List<ItemDTO> list_dto = new ArrayList<>();
		List<ItemEntity> list_entity = itemRepository.findAll();
		
		list_entity.forEach(e ->{
			list_dto.add(new ModelMapper().map(e, ItemDTO.class));
		});
		return list_dto;
	}

	

	@Transactional
	public void delete(String title) {
		itemRepository.deleteByTitle(title);
		
	}


	public ItemEntity getItemByTitle(String title) {
		// TODO Auto-generated method stub
		return itemRepository.findByTitle(title);
	}

//	@Transactional
//	public String updateItemByTitle(ItemResponse itemResponse) {
//		
//		try {
//			itemRepository.save(itemResponse);
//			
//			return "ok";
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "fail";
//		}
//	}



	public ItemDTO findById(Long id) {
	    Optional<ItemEntity> optional = itemRepository.findById(id);
	    if (!optional.isPresent()) {
	        throw new RuntimeException("불러오기 실패");
	    }
	    ItemEntity entity = optional.get();
	    
	    ItemDTO itemDTO = ItemDTO.toItemDTO(entity);
	    itemDTO.setCreateDate(entity.getCreateDate());
	    itemDTO.setUpdateDate(entity.getUpdateDate());
	    
	    return itemDTO;
	}
	
//	public ItemDTO updateItemByTitle(ItemDTO dto) {
//	    Optional<ItemEntity> optional = uploadFileRepository.findById(dto.getId());
//	    
//	    if (optional.isPresent()) {
//	        ItemEntity entity = optional.get();
//	        entity.setId(dto.getId());
//	        entity.setArtist(dto.getArtist());
//	        entity.setTitle(dto.getTitle());
//	        entity.setItemDescribe(dto.getItemDescribe());
//	        entity.setPrice(dto.getPrice());
//	        
//	        // 수정된 부분 시작
//	        byte[] bytes = Base64.getDecoder().decode(dto.getBytes()); // Base64 디코딩
//	        entity.setBytes(bytes);
//	        // 수정된 부분 끝
//	        
//	        entity.setUpdateDate(new Date());
//	        entity = uploadFileRepository.save(entity);
//	        dto = ItemDTO.toItemDTO(entity);
//	        return dto;
//	    }
//	    
//		return dto; 
//	    
//	}
	public void delete(Long id) {
		itemRepository.deleteById(id);
		
	}

}
