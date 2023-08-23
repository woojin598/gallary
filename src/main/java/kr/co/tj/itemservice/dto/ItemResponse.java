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
public class ItemResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String artist;

	private String title;
	
	private String itemDescribe;
	
	private String filePath;
	
	private Long price;
	
	private Long qty;
	
	private Date createDate;
	
	private Date updateDate;

}
