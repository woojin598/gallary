package kr.co.tj.itemservice.dto;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UploadFileService {
	
	@Autowired
	private UploadFileRepository uploadFileRepository;

	public ItemEntity uploadFile(byte[] bytes, String artist,String title, String itemDescribe, Long price,Long qty ) {
		Date createDate = new Date();
		ItemEntity entity = ItemEntity.builder()
				.bytes(bytes)
				.artist(artist)
				.title(title)
				.itemDescribe(itemDescribe)
				.price(price)
				.qty(qty)
				.createDate(createDate)
				.build();
		return uploadFileRepository.save(entity);
	}

	public ItemDTO updateItemByTitle(ItemDTO itemDTO) {
        Long id = itemDTO.getId();
        String artist = itemDTO.getArtist();
        String title = itemDTO.getTitle();
        String itemDescribe = itemDTO.getItemDescribe();
        Long price = itemDTO.getPrice();
        Long qty = itemDTO.getQty();
        byte[] bytes = itemDTO.getBytes();

        if (id == null || artist == null || title == null || itemDescribe == null || price == null || qty == null || bytes == null) {
            throw new IllegalArgumentException("등록되지 않은 작품입니다.");
        }

        try {
            // 업데이트할 아이템을 데이터베이스에서 조회합니다.
            Optional<ItemEntity> optional = uploadFileRepository.findById(id);
            if (!optional.isPresent()) {
                throw new NoSuchElementException("해당 아이템을 찾을 수 없습니다.");
            }

            ItemEntity entity = optional.get();
            // 업데이트할 정보를 설정합니다.
            entity.setArtist(artist);
            entity.setTitle(title);
            entity.setItemDescribe(itemDescribe);
            entity.setPrice(price);
            entity.setQty(qty);
            entity.setBytes(bytes);
            entity.setUpdateDate(new Date());

            // 데이터베이스에 업데이트합니다.
            entity = uploadFileRepository.save(entity);

            // 엔티티로부터 업데이트된 ItemDTO를 생성하여 반환합니다.
            return new ItemDTO(entity.getId(), entity.getArtist(), entity.getTitle(),
                entity.getItemDescribe(), entity.getPrice(), entity.getQty(), entity.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("수정 실패");
        }
    }
}
//	public ItemDTO updateItemByTitle(ItemDTO dto) {
//		 Optional<ItemEntity> optional = uploadFileRepository.findById(dto.getId());
//		    if (!optional.isPresent()) {
//		    throw new RuntimeException("안돼");
//		    }
//		    
//		        ItemEntity entity = optional.get();
//		        if(entity == null) {
//		        	throw new RuntimeException("안돼");
//		        }
//		        entity.setId(dto.getId());
//		        entity.setArtist(dto.getArtist());
//		        entity.setTitle(dto.getTitle());
//		        entity.setItemDescribe(dto.getItemDescribe());
//		        entity.setPrice(dto.getPrice());
//		        entity.setBytes(dto.getBytes());
//		        // byte[] bytes = Base64.getDecoder().decode(dto.getBytes());  
//		        entity.setBytes(dto.getBytes());
//		       
//		        entity.setUpdateDate(new Date());
//		        entity = uploadFileRepository.save(entity);
//		        dto = dto.toItemDTO2(entity);
//		        
//		        return dto;
//		    }  
//		}

