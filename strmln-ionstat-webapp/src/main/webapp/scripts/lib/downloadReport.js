$(function() {
	
})
function  downloadReport() {
	var row = $('#mainGridSessions').datagrid('getSelected');
	if (row) {
		var sessionId = row.sessionId;
		var jsonData = {};
		jsonData.taskName = 'downloadReport';
		var jsonTaskData = {};
		jsonTaskData.id = sessionId;
		
		$('<iframe>', {
			src : 'task.is?jsonData=' + JSON.stringify(jsonData) + '&jsonTaskData=' + JSON.stringify(jsonTaskData) + ''
		}).hide().appendTo('body')

	}
}
function deleteSession(){
	var row = $('#mainGridSessions').datagrid('getSelected');
	var sessionId = row.sessionId;
	var jsonData = {};
	jsonData.taskName = 'deleteSession';
	var jsonTaskData = {};
	jsonTaskData.id = sessionId;
	$.ajax({
		type : "POST",
		url : 'task.is',
		data : {jsonData:JSON.stringify(jsonData), jsonTaskData:JSON.stringify(jsonTaskData)},
		dataType : "json",
		success : function(data) {
			var selectedDeviceId = $("#tree").dynatree("getTree").activeNode.data.id;
			var jsonData = {};
			jsonData.taskName = "getDevice";
			var jsonTaskData = {};
			jsonTaskData.id = selectedDeviceId;
			$.ajax({
				type : "GET",
				url : 'task.is',
				data : {jsonData:JSON.stringify(jsonData), jsonTaskData:JSON.stringify(jsonTaskData)},
				dataType : "json",
				success : function(data) {
					$('#previewGridPanelCenter').hide();
					$('#previewGridPanelEast').hide();
					$('#downloadReportButton').hide();
					$('#deleteSession').hide();
					updateNewMainGrid(data);
				}
			})
		}
	})
}