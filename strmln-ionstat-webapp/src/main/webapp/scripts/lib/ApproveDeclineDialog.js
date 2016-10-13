$(function() {
	$("#approveButton").click(function(button) {
		approveDeclineMethod(true)
		$('#approveDeclineDialog').hide();
		$('#mask').fadeOut();
	});
	$("#declineButton").click(function(button) {
		approveDeclineMethod(false)
		$('#approveDeclineDialog').hide();
		$('#mask').fadeOut();
	});
	$("#cancelApproveDecline").click(function(button) {
		$('#approveDeclineDialog').hide();
		$('#mask').fadeOut();
	});
});
function approveDeclineBtnHandler() {
	if(!this.openApproveDialogFirstTime){
		this.openApproveDialogFirstTime = true;
		$('#approveDeclineDialog').append(
				$('<div><b>Comment:</b></div>')
					.css('margin-left', '30px')
		)
		$('#approveDeclineDialog').append(
				$('<textarea></textarea>')
					.attr('id', 'approveDeclineComment')
					.css('height', '110px')
					.css('resize', 'none')
					.css('margin-left', '30px')
					.css('width', '295px')
		)
	}else{
		$('#approveDeclineComment').val('');
	}
	$('#mask').fadeIn();
	$('#approveDeclineDialog').show();
}
function approveDeclineMethod(approve) {
	var selectedRecord =  $('#mainGridSessions').datagrid('getSelected');
	var sessionId = selectedRecord.sessionId;
	var comment = $('#approveDeclineComment').val();

	var jsonData = {};
	jsonData.taskName = 'approveSession'
	var jsonTaskData = {};
	jsonTaskData.id = sessionId;
	jsonTaskData.comment = comment;
	if(approve){
		jsonTaskData.status = 'approved';
	}else{
		jsonTaskData.status = 'declined';
	}
	$.ajax({
		type : "POST",
		url : 'task.is',
		data : {jsonData:JSON.stringify(jsonData), jsonTaskData:JSON.stringify(jsonTaskData)},
		dataType : "json",
		success : function(data) {
			var node = $("#tree").dynatree("getActiveNode");
		}	
	});
}