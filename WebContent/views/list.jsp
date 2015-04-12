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
		<div id="title"></div>
		<div id="wamp" style="margin: 12px auto; width: 80%">
			<div id="search_box" style="width: 50%; margin-left: 15px;">
				<div class="row">
					<div style="width: 100%">
						<div class="input-group">
							<input id="word" type="text" class="form-control"
								placeholder="Search for..." value="${k }"> <span
								class="input-group-btn">
								<button type="button" id="sub_button"
									class="btn btn-default btn-nor">
									<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
								</button>
							</span>
						</div>
					</div>
				</div>
				<span style="font-size: 12px">找到约${count }条结果，用时${cost }秒。</span>
			</div>

			<div style="margin-top: 12px">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>标题</th>
							<th>时间</th>
							<th>来源</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${result }" var="bean">
							<tr style="padding: 20px">
								<td><a href="${bean.url}" target="_blank">${bean.title }</a></td>
								<td>${bean.date }</td>
								<td>${bean.source }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div id="paginatiom" class="ta">
			<ul class="pagination">
				<li><a href="#">&laquo;</a></li>
				<li><a href="#">1</a></li>
				<li><a href="#">2</a></li>
				<li><a href="#">3</a></li>
				<li><a href="#">4</a></li>
				<li><a href="#">5</a></li>
				<li><a href="#">&raquo;</a></li>
			</ul>
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