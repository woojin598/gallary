<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!--업로드한 경로와 파일명 보여주기 -->
	<p>${dbsaveFilename}</p>
	<!--업로드한 파일 정보 출력하기 -->
	<div id="result">여기에 업로드한 파일 정보가 들어감.</div>
	<script type="text/javascript">
		/* 업로드한 파일의 경로와 파일명 */
		let dbsaveFilename = "${dbsaveFilename}";
		// 업로드한 파일을 id가 result인 div에 넣는 작업
		isImageFile(dbsaveFilename);
		function isImageFile(filename) {
			// 확장자명을 알기 위해 확장자명 바로 앞에 있는 .의 인덱스를 구하고 거기에 +1을 함.
			let idx = filename.lastIndexOf(".") + 1;
			// 확장자명 획득
			let formatName = filename.substring(idx);
			// 확장자가 png or gif or jpg or jpeg일 경우에는 이미지까지 같이 출력
			if (formatName === "png" || formatName === "gif"
					|| formatName === "jpg" || formatName === "jpeg") {
				document.getElementById("result").innerHTML = "<a><img alt='안 나옴' src='/attach/showImage?filename="
						+ filename + "'/></a>"
			} else {
				// 확장자가 위 조건에 해당하지 않으면 파일명만 출력...
				document.getElementById("result").innerHTML = "<a href='/attach/download?filename="
						+ filename + "'>" + filename + "</a>"
			}
			return idx;
		}
	</script>
</body>
</html>