<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="Page Description">
	<meta name="author" content="syaku">
	<title>Validation Check</title>

	<link href="<@spring.url "/resources/bower_components/bootstrap/dist/css/bootstrap.min.css" />" rel="stylesheet">
	<link href="<@spring.url "/resources/bower_components/font-awesome/css/font-awesome.min.css" />" rel="stylesheet">
	<link href="<@spring.url "/resources/bower_components/syaku-jmodal/dist/jquery.syaku.modal.min.css" />" rel="stylesheet">
</head>
<body>

<div class="container">

	<form action="validation" method="post" role="form">
		<legend>유효성 검사 양식</legend>

		<div class="form-group">
			<label for="name">이름</label>
			<input type="text" class="form-control" name="name" id="name" placeholder="">
		</div>

		<div class="form-group">
			<label for="age">나이</label>
			<input type="text" class="form-control" name="age" id="age" placeholder="">
		</div>

		<div class="form-group">
			<label for="sex">성별</label>
			<div class="radio">
				<label for="sex_male">
					<input type="radio" name="sex" id="sex_male" value="M" checked="checked"> 남성
				</label>
				<label for="sex_female">
					<input type="radio" name="sex" id="sex_female" value="F" checked="checked"> 여성
				</label>
			</div>
		</div>

		<button type="submit" class="btn btn-primary">전송</button>
	</form>
</div>







<script src="<@spring.url "/resources/bower_components/jquery/dist/jquery.min.js" />"></script>
<script src="<@spring.url "/resources/bower_components/jquery/dist/jquery.min.js" />"></script>
<script src="<@spring.url "/resources/bower_components/jquery.serializeJSON/jquery.serializejson.min.js" />"></script>

<script src="<@spring.url "/resources/bower_components/jquery-validation/dist/jquery.validate.min.js" />"></script>
<script src="<@spring.url "/resources/bower_components/jquery-validation/dist/additional-methods.min.js" />"></script>
<script src="<@spring.url "/resources/bower_components/syaku-jmodal/dist/jquery.syaku.modal.min.js" />"></script>
</body>
</html>