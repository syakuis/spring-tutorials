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

	<form name="form" action="<@spring.url "/validation/save" />" method="post" role="form">
		<legend>유효성 검사 양식</legend>
		<input type="hidden" name="idx" value="">

		<div class="form-group">
			<label for="userId">아이디</label>
			<input type="text" class="form-control" data-toggle="valid" name="userId" id="userId" placeholder="">
		</div>

		<div class="form-group">
			<label for="password">비밀번호</label>
			<input type="text" class="form-control" data-toggle="valid" name="password" id="password" placeholder="">
		</div>

		<div class="form-group">
			<label for="password2">비밀번호 확인</label>
			<input type="text" class="form-control" data-toggle="valid" name="password2" id="password2" placeholder="">
		</div>

		<div class="form-group">
			<label for="name">이름</label>
			<input type="text" class="form-control" data-toggle="valid" name="name" id="name" placeholder="">
		</div>

		<div class="form-group">
			<label for="age">나이</label>
			<input type="text" class="form-control" data-toggle="valid" name="age" id="age" placeholder="">
		</div>

		<div class="form-group">
			<label for="sex">성별</label>
			<div class="radio">
				<label for="sex_male">
					<input type="radio" data-toggle="valid" name="sex" id="sex_male" value="M" checked="checked"> 남성
				</label>
				<label for="sex_female">
					<input type="radio" data-toggle="valid" name="sex" id="sex_female" value="F" checked="checked"> 여성
				</label>
			</div>
		</div>

		<div class="form-group">
			<label for="birthday">생년월일</label>
			<input type="text" class="form-control" data-toggle="valid" name="birthday" id="birthday" placeholder="">
		</div>

		<div class="form-group">
			<label for="phone">전화번호</label>
			<input type="text" class="form-control" data-toggle="valid" name="phone" id="phone" placeholder="">
		</div>

		<div class="form-group">
			<label for="email">e메일</label>
			<input type="text" class="form-control" data-toggle="valid" name="email" id="email" placeholder="">
		</div>

		<div class="form-group">
			<label for="hobby">취미</label>
			<div class="checkbox" id="hobby" data-toggle="valid">
				<label class="checkbox-inline">
					<input type="checkbox" value="게임" name="hobby"> 게임
				</label>
				<label class="checkbox-inline">
					<input type="checkbox" value="영화" name="hobby" id="hobby_game"> 영화
				</label>
				<label class="checkbox-inline">
					<input type="checkbox" value="음악" name="hobby" id="hobby_game"> 음악
				</label>
				<label class="checkbox-inline">
					<input type="checkbox" value="공부" name="hobby" id="hobby_game"> 공부
				</label>
			</div>
		</div>

	<div>
		<p class="lead">추가</p>

	</div>

		<button type="submit" class="btn btn-primary">전송</button>
	</form>
</div>

<script src="<@spring.url "/resources/bower_components/jquery/dist/jquery.min.js" />"></script>
<script src="<@spring.url "/resources/bower_components/jquery.serializeJSON/jquery.serializejson.min.js" />"></script>
<script src="<@spring.url "/resources/bower_components/bootstrap/dist/js/bootstrap.min.js" />"></script>

<script src="<@spring.url "/resources/bower_components/jquery-validation/dist/jquery.validate.min.js" />"></script>
<script src="<@spring.url "/resources/bower_components/jquery-validation/dist/additional-methods.min.js" />"></script>
<script src="<@spring.url "/resources/bower_components/syaku-jmodal/dist/jquery.syaku.modal.min.js" />"></script>

<#if errors??>
<script type="application/javascript">
	$(function() {
		<#list errors as error>
			$('#' + '${error.field}'.replace( /(:|\.|\[|\]|,|=|@)/g, "\\$1" )).tooltip({
				title: "${error.message}",
				delay: { "show": 500, "hide": 100 }
			});
		</#list>

		$('[data-toggle="valid"]').tooltip('show');
	});
</script>
</#if>
</body>
</html>