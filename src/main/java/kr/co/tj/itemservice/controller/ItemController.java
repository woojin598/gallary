package kr.co.tj.itemservice.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.tj.itemservice.dto.ItemDTO;
import kr.co.tj.itemservice.dto.ItemEntity;
import kr.co.tj.itemservice.dto.ItemRequest;
import kr.co.tj.itemservice.dto.ItemResponse;
import kr.co.tj.itemservice.dto.UploadFileService;
import kr.co.tj.itemservice.service.ItemService;

@RestController
@RequestMapping("/item-service")
public class ItemController {

//	private String saveFile(MultipartFile file) throws IOException {
//		// 파일이 저장될 디렉토리 경로
//		String uploadPath = UPLOAD_DIR;
//
//		// 파일 확장자 추출
//		String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
//
//		// 파일 이름을 유니크하게 생성 (파일명 + 현재 시간)
//		String fileName = UUID.randomUUID().toString() + "." + fileExtension;
//
//		// 파일을 저장할 경로 생성
//		String filePath = uploadPath + "/" + fileName;
//
//		// 파일 저장
//		file.transferTo(new File(filePath));
//
//		return filePath;
//	}
//
//	private static String UPLOAD_DIR = "";

	@Autowired
	private UploadFileService uploadFileService;

	@Autowired
	private Environment env;

	@Autowired
	private ItemService itemService;

	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	// 검색기능
	@GetMapping("/search")
	public List<Map<String, Object>> searchItems(@RequestParam(value = "search") String searchTerm) {
		List<ItemDTO> itemList = itemService.searchItems(searchTerm);

		List<Map<String, Object>> result = new ArrayList<>();
		for (ItemDTO dto : itemList) {
			Map<String, Object> item = new HashMap<>();
			item.put("artist", dto.getArtist());
			item.put("title", dto.getTitle());
			item.put("itemDescribe", dto.getItemDescribe());
			item.put("price", dto.getPrice());
			item.put("qty", dto.getQty());
			item.put("createDate", dto.getCreateDate());
			item.put("updateDate", dto.getUpdateDate());
			item.put("id", dto.getId());
			item.put("bytes", dto.getBytes());
			result.add(item);
		}

		return result;
	}

	@GetMapping("/list")
	public ResponseEntity<?> list(int pageNum) {
		Map<String, Object> map = new HashMap<>();
		Page<ItemDTO> page = itemService.findAll(pageNum);
		
		for(ItemDTO iDto: page.getContent()) {
		    byte[] bytes = iDto.getBytes();
		    String strBytes = java.util.Base64.getEncoder().encodeToString(bytes);
		    iDto.setStrBytes(strBytes);
		}
		
//		for(ItemDTO iDto: page.getContent()) {
//			System.out.println("iDto==========");
//			byte[] bytes= iDto.getBytes();
//			bytes = Base64.encodeBase64(bytes);
//			String strBytes;
//			try {
//				strBytes = new String(bytes , "UTF-8");
//				iDto.setStrBytes(strBytes);
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
		map.put("result", page);

		return ResponseEntity.ok().body(map);
	}
	@PutMapping("/items/update/{id}")
	public ResponseEntity<?> updateItemByTitle(MultipartHttpServletRequest mRequest, @PathVariable Long id) {
	    Map<String, Object> map = new HashMap<>();

	    if (mRequest == null || id == null) {
	        map.put("result", "요청이 잘못되었습니다.");
	        return ResponseEntity.badRequest().body(map);
	    }

	    String artist = mRequest.getParameter("artist");
	    String title = mRequest.getParameter("title");
	    String itemDescribe = mRequest.getParameter("itemDescribe");
	    Long price = Long.parseLong(mRequest.getParameter("price"));
	    Long qty = Long.parseLong(mRequest.getParameter("qty"));

	    MultipartFile file1 = mRequest.getFile("file1");

	    if (file1 == null || file1.isEmpty()) {
	        map.put("result", "파일이 없습니다.");
	        return ResponseEntity.badRequest().body(map);
	    }

	    try {
	        byte[] bytes = file1.getBytes();
	        ItemDTO dto = new ItemDTO(id, artist, title, itemDescribe, price,qty, bytes);

	        dto = uploadFileService.updateItemByTitle(dto);
	        map.put("result", dto);
	        return ResponseEntity.ok().body(map);
	    } catch (Exception e) {
	        e.printStackTrace();
	        map.put("result", "수정 실패");
	        return ResponseEntity.badRequest().body(map);
	    }
	}

//	@PutMapping("/items/update/{id}")
//	public ResponseEntity<?> updateItemByTitle(@RequestBody ItemRequest itemRequest) {
//	    Map<String, Object> map = new HashMap<>();
//
//	    if (itemRequest == null ||  itemRequest.getArtist() == null || itemRequest.getTitle() == null ||
//	    		itemRequest.getItemDescribe() == null || itemRequest.getPrice() == null || itemRequest.getBytes() == null  ) {
//	        map.put("result", "등록되지 않은 작품이에요.");
//	        return ResponseEntity.badRequest().body(map);
//	    }
//	    
//	    ItemDTO dto = ItemDTO.toItemDTO(itemRequest);
//
//	    try {
//	    	dto = uploadFileService.updateItemByTitle(dto);
//	        map.put("result", dto);
//	        return ResponseEntity.ok().body(map);
//	    } catch (NoSuchElementException e) {
//	        e.printStackTrace();
//	        map.put("result", "해당 글이 없습니다.");
//	        return ResponseEntity.badRequest().body(map);
//	    }
//	}

	
//	@PutMapping("/items/updateitem")
//	public ResponseEntity<?> updateItemByTitle(@RequestBody ItemResponse itemResponse){
//		Map<String, Object> map = new HashMap<>();
//		
//		ItemEntity itemEntity = itemService.getItemByTitle(itemResponse.getTitle());
//		
//		if(itemEntity == null) {
//			return ResponseEntity.status(HttpStatus.OK).body("등록되지 않은 작품이에요.");
//		}
//		
//		String result;
//		
//		try {
//			result = itemService.updateItemByTitle(itemEntity);
//			
//			if (result.equalsIgnoreCase("ok")) {
//				return ResponseEntity.status(HttpStatus.OK).body("1:성공");
//			} else {
//				return ResponseEntity.status(HttpStatus.OK).body("0:갱신 실패");
//			}
//			
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.OK).body("0:갱신 실패");
//		}
//		
//	}

	@GetMapping("/items/all")
	public ResponseEntity<?> findAll() {

		try {
			List<ItemDTO> list = itemService.findAll();
			
			for(ItemDTO iDto: list) {
				System.out.println("iDto==========");
				byte[] bytes= iDto.getBytes();
				bytes = Base64.encodeBase64(bytes);
				String strBytes = new String(bytes , "UTF-8");
				iDto.setStrBytes(strBytes);
			}
			
			
			return ResponseEntity.ok().body(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.badRequest().body("에러 발생");
		}
	}

	@GetMapping("/items/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		Map<String, Object> map = new HashMap<>();

		if (id == null) {
			map.put("result", "잘못된 정보입니다.");
			return ResponseEntity.badRequest().body(map);
		}
		try {
			
			
			
			ItemDTO dto = itemService.findById(id);
			
			byte[] bytes = Base64.encodeBase64(dto.getBytes());
			
			String strBytes = new String(bytes , "UTF-8");
			
			map.put("bytes", strBytes);
			map.put("result", dto);
			return ResponseEntity.ok().body(map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "가져오기에 실패");

			return ResponseEntity.badRequest().body(map);
		}

//		List<ItemDTO> list = itemService.getListByTitle(title);
//		
//		List<ItemResponse> responseList = new ArrayList<>();
//		
//		for(ItemDTO itemDTO : list) {
//			ItemResponse itemResponse = itemDTO.toItemResponse();
//			responseList.add(itemResponse);
//		}
//		
//		
//		return ResponseEntity.status(HttpStatus.OK).body(responseList);
	}

	@PostMapping("/items")
	public ResponseEntity<?> fileUpload(MultipartHttpServletRequest mRequest) {
	    Map<String, Object> map = new HashMap<>();
	    
	    
	    String artist = mRequest.getParameter("artist");
	    String title = mRequest.getParameter("title");
	    String itemDescribe = mRequest.getParameter("itemDescribe");
	    Long price = Long.parseLong(mRequest.getParameter("price"));
	    Long qty = Long.parseLong(mRequest.getParameter("qty"));
	    
	   

	    MultipartFile file1 = mRequest.getFile("file1");
	    file1.getOriginalFilename();

	    try {
	        byte[] bytes = file1.getBytes();
	        ItemEntity fileEntity = uploadFileService.uploadFile(bytes, artist, title, itemDescribe, price, qty);
	        fileEntity.getCreateDate();
	        
	        map.put("id", fileEntity.getId());
	        map.put("result", "ok");
	        return ResponseEntity.ok().body(map);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.badRequest().body(map);
	    }
	}
//	@PostMapping("/items")
//	public ResponseEntity<?> createitem(@RequestParam("file") MultipartFile file, @RequestBody ItemRequest itemRequest) {
//	    // 이미지 업로드 처리 로직을 추가합니다.
//	    try {
//	        // 파일을 서버에 저장하고 파일 경로를 얻어옵니다.
//	        String filePath = saveFile(file);
//	        itemRequest.setFilePath(filePath); // ItemRequest에 파일 경로를 설정합니다.
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	        return ResponseEntity.badRequest().body("이미지 업로드 실패");
//	    }
//
//	    ItemDTO itemDTO = ItemDTO.toItemDTO(itemRequest);
//
//	    itemDTO = itemService.createItem(itemDTO);
//
//	    ItemResponse itemResponse = itemDTO.toItemResponse();
//
//	    return ResponseEntity.status(HttpStatus.CREATED).body(itemResponse);
//	}

	@DeleteMapping("/items/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {

		try {
			itemService.delete(id);
			return ResponseEntity.ok().body("삭제 성공");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("삭제 실패");
		}
	}

	@GetMapping("/health_check")
	public String status() {
		return "item service입니다" + env.getProperty("local.server.port") + env.getProperty("data.test");
	}

}
