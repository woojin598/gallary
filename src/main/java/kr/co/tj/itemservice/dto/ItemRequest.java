package kr.co.tj.itemservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemRequest {
	
	private Long id;

	private String artist;

	private String title;
	
	private String itemDescribe;
	
	private Long price;
	
	private Long qty;
//	private String filePath;
	private byte[] bytes;

//	public void setFilePath(String filePath) {
//		this.filePath = filePath;
//		
//	}
	
}
