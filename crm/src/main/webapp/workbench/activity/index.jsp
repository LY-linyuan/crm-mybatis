<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>
<script type="text/javascript">

			$(function(){
				// 打开添加操作的模态窗口
				$("#addBtn").click(function () {
					$(".time").datetimepicker({
						minView: "month",
						language:  'zh-CN',
						format: 'yyyy-mm-dd',
						autoclose: true,
						todayBtn: true,
						pickerPosition: "bottom-left"
					});
					$.ajax({
						url : "workbench/activity/getUserList.do",
						type: "get",
						dataType: "json",
						success: function (data) {
							var html = "<option></option>";
							$.each(data, function (i, n) {
								html += "<option value='" + n.id + "'> " + n.name + " </option>"
								$("#creat-owner").html(html);
							})
							// 在js中套用EL表达式EL表达式一定要在字符串中
							var id = "${user.id}"
							$("#creat-owner").val(id);
							$("#createActivityModal").modal("show");
						}
					})
				})

				// 为保存按钮绑定事件执行添加操作
				$("#saveBtn").click(function () {
					$.ajax({
						url : "workbench/activity/save.do",
						data: {
							"owner" : $.trim($("#creat-owner").val()),
							"name" : $.trim($("#create-name").val()),
							"startDate" : $.trim($("#create-startDate").val()),
							"endDate" : $.trim($("#create-endDate").val()),
							"cost" : $.trim($("#create-cost").val()),
							"description" : $.trim($("#create-description").val())
						},
						type: "post",
						dataType: "json",
						success: function (data) {
							if(data.success) {
								$("#createActivityModal").modal("hide");
								// 刷新
								// pageList(1, 2);
								/*
										$("#activityPage").bs_pagination('getOption', 'currentPage')
											表示停留在当前页
										$("#activityPage").bs_pagination('getOption', 'rowsPerPage')
										表示操作后维持已经设置设置好的每页展现记录数

										用到哪个写哪个
										直接使用即可

										不需要修改任何操作

								 */
								pageList(1, $("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
							} else {
								alert("添加市场活动失败, 请检查后重新添加");
							}
						}
					})
				})

				// 页面加载完成后触发一个方法
				pageList(1, 2);

				// 为查询按钮绑定一个事件
				$("#searchBtn").click(function () {
					// 每一次查询的时候将搜索框中的信息保存到隐藏域当中
					$("#hidden-name").val($.trim($("#search-name").val()));
					$("#hidden-owner").val($.trim($("#search-owner").val()));
					$("#hidden-startDate").val($.trim($("#search-starDate").val()));
					$("#hidden-endDate").val($.trim($("#search-endDate").val()));
					pageList(1, 2);
				})

				// 为全选复选框绑定事件
				$("#qx").click(function () {
					$("input[name=xz]").prop("checked", this.checked);
				})

				// 以下做法不行  动态生成的元素不能以普通绑定事件的形式来进行操作的
				/*$("input[name=xz]").click(function () {
					alert(123)
				})*/
				/*
				 	动态生成的元素要找到需要绑定的有效的外层元素
				 	以on方法来触发事件
				 	$("需要绑定的有效的外层元素").on(click, $("动态生成的元素"), function () {
				 */
				$("#activityBody").on("click", $("input[name=xz]"), function () {
					$("#qx").prop("checked", $("input[name=xz]").length == $("input[name=xz]:checked").length);
				})

				// 为删除按钮绑定事件
				$("#deleteBtn").click(function () {
					var $xz = $("input[name=xz]:checked");
					if ($xz.length == 0) {
						alert("请选择需要删除的记录");
					} else {
						if (confirm("确定删除所选择的记录吗？")) {
							// 拼接参数
							var param = "";
							for (var i = 0 ; i < $xz.length ; i++) {
								param += "id=" + $($xz[i]).val();
								if (i<$xz.length-1) {
									param += "&";
								}
							}
							$.ajax({
								url : "workbench/activity/delete.do",
								data: param,
								type: "post",
								dataType: "json",
								success: function (data) {
									if (data.success) {
										// pageList(1, 2);
										pageList(1 ,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
									} else {
										alert("删除市场活动失败");
									}
								}
							})
						}

					}

				})

				// 为修改按钮绑定事件  打开修改操作的模态窗口
				$("#editBtn").click(function () {
					var $xz = $("input[name=xz]:checked");
					if ($xz.length == 0) {
						alert("请选择需要修改的记录");
					} else if ($xz.length > 1) {
						alert("每次只能修改一条记录哦");
					} else {
						var id = $xz.val();
						$.ajax({
							url: "workbench/activity/getUserListAndActivity.do",
							data: {
								"id": id
							},
							type: "get",
							dataType: "json",
							success: function (data) {
								// 处理所有者下拉框
								var html = "<option></option>";
								$.each(data.uList, function (i, n) {
									html += "<option value='" + n.id + "'>" + n.name + "</option>";
								})
								$("#edit-owner").html(html);
								// 单挑activity
								$("#edit-id").val(data.a.id);
								$("#edit-name").val(data.a.name);
								$("#edit-owner").val(data.a.owner);
								$("#edit-startDate").val(data.a.startDate);
								$("#edit-endDate").val(data.a.endDate);
								$("#edit-cost").val(data.a.cost);
								$("#edit-description").val(data.a.description);
								// 打开修改操作的模态窗口
								$("#editActivityModal").modal("show");

							}
						})
					}
				});
				// 为更新按钮绑定事件
				$("#updateBtn").click(function () {
					$.ajax({
						url : "workbench/activity/update.do",
						data: {
							"id" : $.trim($("#edit-id").val()),
							"owner" : $.trim($("#edit-owner").val()),
							"name" : $.trim($("#edit-name").val()),
							"startDate" : $.trim($("#edit-startDate").val()),
							"endDate" : $.trim($("#edit-endDate").val()),
							"cost" : $.trim($("#edit-cost").val()),
							"description" : $.trim($("#edit-description").val())
						},
						type: "post",
						dataType: "json",
						success: function (data) {
							if(data.success) {
								$("#editActivityModal").modal("hide");
								// 刷新
								// pageList(1, 2)
								pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
										,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
							} else {
								alert("添加市场活动失败, 请检查后重新添加");
							}
						}
					})
				})

			});





			// pageNo 页码
			// pageSize 每页记录条数
			function pageList(pageNo, pageSize) {
				$("#qx").prop("checked", false);
				// 每一次查询的的时候吧隐藏域中的数据重新赋给搜索框中
				$("#search-name").val($.trim($("#hidden-name").val()));
				$("#search-owner").val($.trim($("#hidden-owner").val()));
				$("#search-starDate").val($.trim($("#hidden-startDate").val()));
				$("#search-endDate").val($.trim($("#hidden-endDate").val()));

				$.ajax({
					url : "workbench/activity/pageList.do",
					data: {
						"pageNo" : pageNo,
						"pageSize" : pageSize,
						"name" : $.trim($("#search-name").val()),
						"owner" : $.trim($("#search-owner").val()),
						"starDate" : $.trim($("#search-starDate").val()),
						"endDate" : $.trim($("#search-endDate").val()),

					},
					type: "get",
					dataType: "json",
					success: function (data) {
						var html = "";
						$.each(data.dataList, function (i, n) {
						html += '<tr class="active">';
						html += '<td><input type="checkbox" name="xz" value="' + n.id + '" /></td>';
						html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail.do?id=' + n.id + '\';">' + n.name + '</a></td>';
						html += '<td>' + n.owner + '</td>';
						html += '<td>' + n.startDate + '</td>';
						html += '<td>' + n.endDate + '</td>';
						html += '</tr>';
						})
						$("#activityBody").html(html);

						// 计算总页数
						var totalPages = data.total % pageSize == 0 ? data.total / pageSize : parseInt(data.total / pageSize) + 1;

						// 分页
						$("#activityPage").bs_pagination({
							currentPage: pageNo, // 页码
							rowsPerPage: pageSize, // 每页显示的记录条数
							maxRowsPerPage: 20, // 每页最多显示的记录条数
							totalPages: totalPages, // 总页数
							totalRows: data.total, // 总记录条数

							visiblePageLinks: 3, // 显示几个卡片

							showGoToPage: true,
							showRowsPerPage: true,
							showRowsInfo: true,
							showRowsDefaultInfo: true,

							// 该回调函数在点击分页组件的时候触发的
							onChangePage : function(event, data){
								pageList(data.currentPage , data.rowsPerPage);
							}
						});
					}
				})
			}
</script>
	</head>
<body>

	<input type="hidden" id="hidden-name">
	<input type="hidden" id="hidden-owner">
	<input type="hidden" id="hidden-startDate">
	<input type="hidden" id="hidden-endDate">
<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" id="createActivityModal" role="dialog">
	<div class="modal-dialog" role="document" style="width: 85%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
			</div>
			<div class="modal-body">

				<form id="activityAddFrom" class="form-horizontal" role="form">

					<div class="form-group">
						<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
						<div class="col-sm-10" style="width: 300px;">
							<select class="form-control" id="creat-owner">

							</select>
						</div>
						<label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="create-name">
						</div>
					</div>

					<div class="form-group">
						<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control time" id="create-startDate" readonly>
						</div>
						<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control time" id="create-endDate" readonly>
						</div>
					</div>
					<div class="form-group">

						<label for="create-cost" class="col-sm-2 control-label">成本</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="create-cost">
						</div>
					</div>
					<div class="form-group">
						<label for="create-describe" class="col-sm-2 control-label">描述</label>
						<div class="col-sm-10" style="width: 81%;">
							<textarea class="form-control" rows="3" id="create-description"></textarea>
						</div>
					</div>

				</form>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
			</div>
		</div>
	</div>
</div>

<!-- 修改市场活动的模态窗口 -->
<div class="modal fade" id="editActivityModal" role="dialog">
	<div class="modal-dialog" role="document" style="width: 85%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
			</div>
			<div class="modal-body">

				<form class="form-horizontal" role="form">
					<input type="hidden" id="edit-id">
					<div class="form-group">
						<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
						<div class="col-sm-10" style="width: 300px;">
							<select class="form-control" id="edit-owner">
								<%--<option>zhangsan</option>
								<option>lisi</option>
								<option>wangwu</option>--%>
							</select>
						</div>
						<label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="edit-name" >
						</div>
					</div>

					<div class="form-group">
						<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control time" id="edit-startDate">
						</div>
						<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control time" id="edit-endDate">
						</div>
					</div>

					<div class="form-group">
						<label for="edit-cost" class="col-sm-2 control-label">成本</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="edit-cost">
						</div>
					</div>

					<div class="form-group">
						<label for="edit-describe" class="col-sm-2 control-label">描述</label>
						<div class="col-sm-10" style="width: 81%;">
							<%--
								文本域textarea一定是标签对
								正常状态下 标签对要紧挨着不能又空格
								属于表单元素范畴
								val()方法
							--%>
							<textarea class="form-control" rows="3" id="edit-description"></textarea>
						</div>
					</div>

				</form>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="updateBtn">更新</button>
			</div>
		</div>
	</div>
</div>




<div>
	<div style="position: relative; left: 10px; top: -10px;">
		<div class="page-header">
			<h3>市场活动列表</h3>
		</div>
	</div>
</div>
<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	<div style="width: 100%; position: absolute;top: 5px; left: 10px;">

		<div class="btn-toolbar" role="toolbar" style="height: 80px;">
			<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">名称</div>
						<input class="form-control" type="text" id="search-name">
					</div>
				</div>

				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">所有者</div>
						<input class="form-control" type="text" id="search-owner">
					</div>
				</div>


				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">开始日期</div>
						<input class="form-control" type="text" id="search-starDate" />
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<div class="input-group-addon">结束日期</div>
						<input class="form-control" type="text" id="search-endDate">
					</div>
				</div>

				<button type="button" class="btn btn-default" id="searchBtn">查询</button>

			</form>
		</div>
		<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
			<div class="btn-group" style="position: relative; top: 18%;">
				<%--
						data-toggle   点击该按钮触发模态窗口
						data-target   触发哪个模态窗口   "#id"
				--%>
				<button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				<button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				<button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
			</div>

		</div>
		<div style="position: relative;top: 10px;">
			<table class="table table-hover">
				<thead>
				<tr style="color: #B3B3B3;">
					<td><input type="checkbox" id="qx" /></td>
					<td>名称</td>
					<td>所有者</td>
					<td>开始日期</td>
					<td>结束日期</td>
				</tr>
				</thead>
				<tbody id="activityBody">
				<%--<tr class="active">
					<td><input type="checkbox" /></td>
					<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
					<td>zhangsan</td>
					<td>2020-10-10</td>
					<td>2020-10-20</td>
				</tr>
				<tr class="active">
					<td><input type="checkbox" /></td>
					<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
					<td>zhangsan</td>
					<td>2020-10-10</td>
					<td>2020-10-20</td>
				</tr>--%>
				</tbody>
			</table>
		</div>

		<div style="height: 50px; position: relative;top: 30px;">
			<div id="activityPage"></div>
			<%--<div>
				<button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>
			</div>
			<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
				<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
				<div class="btn-group">
					<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
						10
						<span class="caret"></span>
					</button>
					<ul class="dropdown-menu" role="menu">
						<li><a href="#">20</a></li>
						<li><a href="#">30</a></li>
					</ul>
				</div>
				<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
			</div>
			<div style="position: relative;top: -88px; left: 285px;">
				<nav>
					<ul class="pagination">
						<li class="disabled"><a href="#">首页</a></li>
						<li class="disabled"><a href="#">上一页</a></li>
						<li class="active"><a href="#">1</a></li>
						<li><a href="#">2</a></li>
						<li><a href="#">3</a></li>
						<li><a href="#">4</a></li>
						<li><a href="#">5</a></li>
						<li><a href="#">下一页</a></li>
						<li class="disabled"><a href="#">末页</a></li>
					</ul>
				</nav>
			</div>--%>
		</div>

	</div>

</div>
</body>
</html>