$(function(){
	$("#publishBtn").click(publish);
});

function publish() {
	$("#publishModal").modal("hide");

	//獲取標題和內容
	$()
	var title = $("#recipient-name").val();
	var content = $("#message-text").val();
//發送異步要求（POST）
	$.post(
		CONTEXT_PATH + "/discuss/add",
		{"title":title,"content":content},
		function (data) {
			data = $.parseJSON(data);
			//在提示框中顯示返回消息
			$("#hintBody").text(data.msg);
			//顯示提示框
			$("#hintModal").modal("show");
			//2秒後 自動隱藏提示框
			setTimeout(function(){
				$("#hintModal").modal("hide");
				//刷新頁面
				if (data.code == 0){
					window.location.reload();
				}
			}, 2000);
		}
	);



}