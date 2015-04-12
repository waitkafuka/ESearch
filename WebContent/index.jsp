<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>基于分布式搜索引擎的情报服务系统</title>

<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/main.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div id="content">
		<div id="title" class="mc">
			<h1 class="ta" style="margin-top: 187px">
				<span class="label label-default">基于分布式搜索引擎的情报服务系统</span>
			</h1>
		</div>
		<div id="wamp" style="margin: 16px auto; width: 50%">
			<div class="row">
				<div class="col-lg-6" style="width: 100%">
					<div class="input-group input-group-lg">
						<input id="word" type="text" class="form-control"
							placeholder="Search for..."> <span
							class="input-group-btn">
							<button type="button" id= "sub_button"class="btn btn-default btn-nor">
								<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
							</button>
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<form id="searchForm" action="s" method="post">
		<input type="hidden" name="k" id="k">
	</form>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("em").attr("style", "color:red");
			$("#sub_button").click(function() {
				$("#k").val($("#word").val());
				$("#searchForm").submit();
			});
			window.document.onkeydown = function(e) {
				var evt = window.event || e;
				if (evt.keyCode == 13) {//如果按键为回车
					$("#k").val($("#word").val());
					$("#searchForm").submit();
				}
			};
		});
	</script>
</body>
</html>