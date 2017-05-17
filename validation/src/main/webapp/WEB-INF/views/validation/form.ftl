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

	<form id="form" name="form" action="<@spring.url "/validation/save" />" method="post" role="form">
		<legend>유효성 검사 양식</legend>
		<input type="hidden" name="idx" value="">

		<div class="form-group">
			<label for="userId">아이디</label>
			<input type="text" class="form-control" data-toggle="valid" name="userId" id="userId" placeholder="" required>
		</div>

		<div class="form-group">
			<label for="password">비밀번호</label>
			<input type="text" class="form-control" data-toggle="valid" name="password" id="password" placeholder="" required>
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

		<div class="row">
			<div class="col-sm-6">
				<div class="form-group"><input type="text" data-toggle="valid" class="form-control" name="formExts[0].name" id="formExts[0].name" placeholder="name"></div>
			</div>
			<div class="col-sm-6">
				<div class="form-group"><input type="text" data-toggle="valid" class="form-control" name="formExts[0].value" id="formExts[0].value" placeholder="value"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group"><input type="text" data-toggle="valid" class="form-control" name="formExts[1].name" id="formExts[1].name" placeholder="name"></div>
			</div>
			<div class="col-sm-6">
				<div class="form-group"><input type="text" data-toggle="valid" class="form-control" name="formExts[1].value" id="formExts[1].value" placeholder="value"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="form-group"><input type="text" data-toggle="valid" class="form-control" name="formExts[2].name" id="formExts[2].name" placeholder="name"></div>
			</div>
			<div class="col-sm-6">
				<div class="form-group"><input type="text" data-toggle="valid" class="form-control" name="formExts[2].value" id="formExts[2].value" placeholder="value"></div>
			</div>
		</div>

		<div class="checkbox">
			<label>
				<input type="checkbox" id="client_valid"> 클라이언트 유효성 검사
			</label>
		</div>
	</div>

		<button type="submit" class="btn btn-primary">동기</button>
		<button type="button" class="btn btn-primary" id="async">비동기</button>
	</form>
</div>

<script src="<@spring.url "/resources/bower_components/jquery/dist/jquery.min.js" />"></script>
<script src="<@spring.url "/resources/bower_components/jquery.serializeJSON/jquery.serializejson.min.js" />"></script>
<script src="<@spring.url "/resources/bower_components/form2js/src/form2js.js" />"></script>
<script src="<@spring.url "/resources/bower_components/form2js/src/js2form.js" />"></script>
<script src="<@spring.url "/resources/bower_components/form2js/src/jquery.toObject.js" />"></script>
<script src="<@spring.url "/resources/bower_components/bootstrap/dist/js/bootstrap.min.js" />"></script>

<script src="<@spring.url "/resources/bower_components/jquery-validation/dist/jquery.validate.min.js" />"></script>
<script src="<@spring.url "/resources/bower_components/jquery-validation/dist/additional-methods.min.js" />"></script>
<script src="<@spring.url "/resources/bower_components/syaku-jmodal/dist/jquery.syaku.modal.min.js" />"></script>

<script type="text/javascript">
	$('#userId').tooltip({
		title: "",
		delay: { "show": 500, "hide": 100 },
		trigger: 'manual'
	});
	$('[data-toggle="valid"]').tooltip('show');
	$('[data-toggle="valid"]').tooltip('destroy');
	$('#userId').tooltip({
		title: "",
		delay: { "show": 500, "hide": 100 },
		trigger: 'manual'
	});
	$('[data-toggle="valid"]').tooltip('show');
	var timerId = 0;

	function showMessage() {
		$('[data-toggle="valid"]').tooltip('show');

		//if (timerId != 0) clearInterval(timerId);
/*
		timerId = setInterval(function() {
			$('[data-toggle="valid"]').tooltip('destroy');
		}, 5000);*/
	}

	function addMessage(field, message) {
		$('#' + field.replace( /(:|\.|\[|\]|,|=|@)/g, "\\$1" )).tooltip({
			title: message,
			delay: { "show": 500, "hide": 100 },
			trigger: 'manual'
		});
	}
	$(function() {
		$("#form").validate({
			invalidHandler: function(event, validator) {
				//validator.focusInvalid();
			},
			errorPlacement: function(error, element) {
				for (var i = 0; i < error.length; i++) {
					addMessage(element[i].id, error[i].textContent);
				}
			}
		});

		$('#async').click(function() {
			var data = $("#form").toObject({
				skipEmpty: false
			});

			if ($("#client_valid").prop("checked") === true) {
				$('[data-toggle="valid"]').tooltip('destroy');

				if (!$("#form").valid()) {
					showMessage();
					return;
				}
			}

			$.ajax({
				'url' : '<@spring.url "/validation/save" />',
				'type': 'post',
				'dataType' : 'json',
				'processData': false,
				'contentType': 'application/json',
				'data': JSON.stringify(data)
			}).done(function(response) {
				var errors = response.content;
				for (var i in errors) {
					addMessage(errors[i].field, errors[i].message);
				}
				showMessage();
			});
		});

		<#if errors??>
		<#list errors as error>
			showMessage('${error.field}', '${error.message}');
		</#list>

			showMessage();
		</#if>
	});
</script>

</body>
</html>