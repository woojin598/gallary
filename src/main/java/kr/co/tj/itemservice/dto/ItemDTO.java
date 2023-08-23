package kr.co.tj.itemservice.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;

	private String artist;

	private String title;
	
	private String itemDescribe;
	
	private Long price;
	
	private Long qty;
	
	
	private Date createDate;
	
	private Date updateDate;
	private byte[] bytes;
	private String strBytes;
	
	public ItemEntity toItemEntity() {
		 return ItemEntity.builder()
				.id(id)
				.artist(artist)
				.title(title)
				.itemDescribe(itemDescribe)
				.price(price)
				.qty(qty)
				.bytes(bytes)
				.createDate(createDate)
				.updateDate(updateDate)
				.build();
	}
	
	public static ItemDTO toItemDTO(ItemRequest itemRequest) {
		
		   return ItemDTO.builder()
				
				.artist(itemRequest.getArtist())
				.title(itemRequest.getTitle())
				.itemDescribe(itemRequest.getItemDescribe())
				.price(itemRequest.getPrice())
				.build();

       }
	
	public ItemResponse toItemResponse() {
		
		return ItemResponse.builder()
				.id(id)
				.artist(artist)
				.title(title)
				.itemDescribe(itemDescribe)
				.price(price)
				.qty(qty)
				.createDate(createDate)
				.updateDate(updateDate)
				.build();
	
	}
	
	public static ItemDTO toItemDTO(ItemEntity entity) {
	    ItemDTO dto = new ItemDTO();
	    dto.setId(entity.getId());
	    dto.setArtist(entity.getArtist());
	    dto.setTitle(entity.getTitle());
	    dto.setItemDescribe(entity.getItemDescribe());
	    dto.setPrice(entity.getPrice());
	    dto.setQty(entity.getQty());
	    dto.setBytes(entity.getBytes());
	    dto.setCreateDate(entity.getCreateDate());
	    dto.setUpdateDate(entity.getUpdateDate());
	    return dto;
	}

	public ItemDTO toItemDTO2(ItemEntity entity) {
		// TODO Auto-generated method stub
		return ItemDTO.builder()
				.id(entity.getId())
				.artist(entity.getArtist())
				.title(entity.getTitle())
				.itemDescribe(entity.getItemDescribe())
				.price(entity.getPrice())
				.qty(entity.getQty())
				.bytes(entity.getBytes())
				.createDate(entity.getCreateDate())
				.updateDate(entity.getUpdateDate())
				.build();
	}

	public ItemDTO(Long id, String artist, String title, String itemDescribe, Long price, Long qty, byte[] bytes) {
		// TODO Auto-generated constructor stub
		this.id = id;
        this.artist = artist;
        this.title = title;
        this.itemDescribe = itemDescribe;
        this.price = price;
        this.qty = qty;
        this.bytes = bytes;
    }
	}

	 
