package kr.co.tj.itemservice.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "items")
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ItemEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(columnDefinition =  "MediumBLOB")// 자료형을 꼭 "MediumBLOB")로 설정. 안 하면 TinyBlob이 되어서 용량이 너무 적어 업로드가 안 됨.
	private byte[] bytes;
	
	@Column(nullable = false)
	private String artist;
	
	@Column(nullable = false, unique = true)
	private String title;
	
	@Column(nullable = false)
	private String itemDescribe;
	
	@Column(nullable = false)
	private Long price;
	
	@Column(nullable = false)
	private Long qty;
	
	private Date createDate;
	
	private Date updateDate;
	
	
//	public String getFileName() {
//		// TODO Auto-generated method stub
//		return getFileName();
//	}
	
}
